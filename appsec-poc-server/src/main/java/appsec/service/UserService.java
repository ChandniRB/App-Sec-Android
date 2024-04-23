package appsec.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.services.playintegrity.v1.PlayIntegrityScopes;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import appsec.auth.AuthService;
import appsec.auth.JwtAuthenticationController;
import appsec.auth.JwtTokenUtil;
import appsec.dao.paidnrv.StakeholderDAO;
import appsec.dao.paidnrv.UserDAO;
import appsec.dto.paidnrv.UserModel;
import appsec.model.ApiResponse;
import appsec.util.CommonUtil;
import appsec.util.Constants;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Service
public class UserService {
    ApiResponse response = CommonUtil.createResponse(Constants.API_USER_CREATE);

    @Autowired
    UserDAO userDAO;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtAuthenticationController authController;

    @Autowired
    AuthService authService;

    @Autowired
    StakeholderDAO stakeholderDAO;

    
    public boolean authorizedUser(String adminId, String id) {
        Optional<UserModel> user = userDAO.getUserById(id);
        return (!user.isEmpty() && (id == adminId));

    }

    public boolean authorizedUser(Map<String, String> request, String id, String token) {
        Optional<UserModel> admin = userDAO.getUserById(id);
        String userType = request.get("userType");

        String adminUserType = admin.get().getUserType();

        // Check if request is made by Superadmin/MORTH or admin
        if (!adminUserType.equalsIgnoreCase(Constants.SUPERADMIN) && !adminUserType.equalsIgnoreCase(Constants.MORTH)) {

            /*
             * If request by admin,
             * check whether userType in request is subuser of admin's user type
             * and stakeCode of admin is same as stakeCode in request
             */

            return (Constants.ADMIN_SUBUSER.get(adminUserType).contains(userType)
                    && admin.get().getStakeCode().equals(request.get(Constants.STAKE_CODE)));
        } else {
            /*
             * If request by Superadmin/MORTH,
             * check whether userType in request is subuser of Superadmin/MORTH
             */
            return (Constants.ADMIN_SUBUSER.get(adminUserType).contains(userType));
        }

    }

    @SuppressWarnings("unchecked")
    public ApiResponse readUser(String userId, String id, String integrityToken, String client)
            throws IllegalArgumentException, IOException, InterruptedException, ParseException,
            NoSuchAlgorithmException {

        if (integrityCheckPassed(integrityToken, client, userId)) {
            UserModel user = userDAO.getUserById(userId).get();
            Map<String, Object> result = new HashMap<>();
            if (user != null) {
                if (userId.equalsIgnoreCase(id)) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    result = mapper.convertValue(user, Map.class);
                } else {
                    result = fetchPublicData(user);
                }
                return CommonUtil.setResponse(Constants.API_USER_READ, HttpStatus.OK, result,
                        Constants.SUCCESS, null);

            } else {
                return CommonUtil.setResponse(Constants.API_USER_READ, HttpStatus.BAD_REQUEST, null,
                        Constants.FAILED, Constants.MSG_INVALID_USERID);

            }
        } else {
            return CommonUtil.setResponse(Constants.API_USER_READ, HttpStatus.UNAUTHORIZED, null,
                    Constants.FAILED, Constants.MSG_UNAUTHORIZED_USER);

        }

    }

    @SuppressWarnings("unchecked")
    private boolean integrityCheckPassed(String integrityToken, String client, String userId)
            throws IOException, InterruptedException, ParseException, NoSuchAlgorithmException {

        String playIntegrityUrl = "https://playintegrity.googleapis.com/v1/com.example.poc4:decodeIntegrityToken";
        String jsonBody = "{\"integrity_token\": \"" + integrityToken + "\"}";

        FileInputStream serviceAccountStream = new FileInputStream(CommonUtil.getGoogleCredentialPath());
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
        credentials = credentials.createScoped(PlayIntegrityScopes.PLAYINTEGRITY);
        AccessToken accessToken = credentials.refreshAccessToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(playIntegrityUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .header(Constants.AUTHORIZATION, "Bearer " + accessToken.getTokenValue())
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        // mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        Map<String, Object> responseBody = mapper.convertValue(obj, Map.class);
        // TokenPayloadExternal tokenPayloadExternal = mapper.convertValue(responseBody,
        // TokenPayloadExternal.class);
        String hashedUserId = findHash(userId);
        Map<String, Object> tokenPayloadExternal = mapper.convertValue(responseBody.get("tokenPayloadExternal"),
                Map.class);
        Map<String, Object> requestDetails = mapper.convertValue(tokenPayloadExternal.get("requestDetails"), Map.class);
        Map<String, Object> appIntegrity = mapper.convertValue(tokenPayloadExternal.get("appIntegrity"), Map.class);
        Map<String, List<String>> deviceIntegrity = mapper.convertValue(tokenPayloadExternal.get("deviceIntegrity"),
                Map.class);
        Map<String, Object> accountDetails = mapper.convertValue(tokenPayloadExternal.get("accountDetails"), Map.class);

        return requestDetails.get("requestPackageName").equals(client)
                && requestDetails.get("requestHash").equals(hashedUserId)
                && appIntegrity.get("appRecognitionVerdict").equals("PLAY_RECOGNIZED")
                && deviceIntegrity.get("deviceRecognitionVerdict").contains("MEETS_DEVICE_INTEGRITY")
                && accountDetails.get("appLicensingVerdict").equals("LICENSED");

    }

    private String findHash(String userId) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = digest.digest(userId.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> fetchPublicData(UserModel user) {
        Map<String, Object> userMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        userMap = mapper.convertValue(user, Map.class);
        userMap.remove(Constants.PASSWORD);
        userMap.remove(Constants.ADDRESS);
        userMap.remove(Constants.PHONE_NO);
        userMap.remove(Constants.MOBILE_NO);

        return userMap;
    }

}
