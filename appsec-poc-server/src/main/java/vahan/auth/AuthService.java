package vahan.auth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.xml.bind.DatatypeConverter;
import vahan.dao.paidnrv.RefreshTokenDAO;
import vahan.dao.paidnrv.UserDAO;
import vahan.dao.paidnrv.VerificationTokenRepository;
import vahan.dto.paidnrv.RefreshToken;
import vahan.dto.paidnrv.UserModel;
import vahan.dto.paidnrv.VerificationToken;
import vahan.model.ApiResponse;
import vahan.util.CommonUtil;
import vahan.util.Constants;
import vahan.util.KongUtil;

@Service
public class AuthService {

    private static final Log logger = LogFactory.getLog(AuthService.class);

    ApiResponse response = CommonUtil.createResponse(Constants.API_USER_CREATE);

    
    @Autowired
    UserDAO userDAO;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    RefreshTokenDAO refreshTokenDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    private KongUtil kongUtil;

    public void createVerificationToken(UserModel user, String token, int expiry) {
        final VerificationToken useroken = new VerificationToken(token, user, expiry);
        tokenRepository.save(useroken);
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return Constants.TOKEN_INVALID;
        }

        final UserModel user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime()
                - cal.getTime()
                        .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return Constants.TOKEN_EXPIRED;
        }

        user.setUserStatus(Constants.USER_ACTIVE);
        userDAO.createUser(user);
        return Constants.TOKEN_VALID;
    }

    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public UserModel getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    public ApiResponse createAuthenticationToken(AuthenticationRequestDto authenticationRequest, String client)
            throws FileNotFoundException, IOException, ParseException, InterruptedException {

        
        UserModel user = userDAO.getUserById(authenticationRequest.getUserId()).get();

        try {

            // User ID not found
            if (user == null) {
                return CommonUtil.setResponse(Constants.API_USER_LOGIN, HttpStatus.BAD_REQUEST, null,
                        Constants.FAILED, Constants.MSG_UNAUTHORIZED_USER);
            }

            // User is locked due to 3 login attempts in last 1 hour => Return error to user
            if (user.getUserStatus().equalsIgnoreCase(Constants.USER_LOCKED) && user.getLastUnsuccessfulLogin()
                    .isAfter(LocalDateTime.now().minusHours(Constants.MAX_LOGIN_ATTEMPTS_HOUR))) {
                return CommonUtil.setResponse(Constants.API_USER_LOGIN, HttpStatus.UNAUTHORIZED, null,
                        Constants.FAILED, Constants.MSG_MAX_TRIES);
            }

            // User is locked, but more that 1 hour elapsed after last unsuccessful login =>
            // Unlock user and reset noOfTries
            else if (user.getLastUnsuccessfulLogin() != null && user.getLastUnsuccessfulLogin()
                    .isBefore(LocalDateTime.now().minusHours(Constants.MAX_LOGIN_ATTEMPTS_HOUR))) {
                user.setUserStatus(Constants.USER_ACTIVE);
                user.setNoOfTries(0);
                userDAO.saveRegisteredUser(user);
            }

            // User has tried maximum login attempts in last 1 hour => Lock user
            if (user.getNoOfTries() != null && user.getNoOfTries() >= Constants.MAX_LOGIN_TRIES
                    && user.getLastUnsuccessfulLogin()
                            .isAfter(LocalDateTime.now().minusHours(Constants.MAX_LOGIN_ATTEMPTS_HOUR))) {

                user.setUserStatus(Constants.USER_LOCKED);
                user.setNoOfTries(user.getNoOfTries() + 1);
                userDAO.saveRegisteredUser(user);
                return CommonUtil.setResponse(Constants.API_USER_LOGIN, HttpStatus.UNAUTHORIZED, null,
                        Constants.FAILED, Constants.MSG_MAX_TRIES);
            }

            // User is not locked and not tried maximum attempts in last 1 hour => attempt
            // login
            else if (!user.getUserStatus().equalsIgnoreCase(Constants.USER_INACTIVE)) {

                Authentication authentication = authenticate(authenticationRequest.getUserId(),
                        authenticationRequest.getPassword());

                if (authentication != null && authentication.isAuthenticated()) {

                    final String token = jwtTokenUtil.generateToken(authentication, Constants.JWT_TOKEN_VALIDITY);
                    String refreshToken = jwtTokenUtil.generateToken(authentication, Constants.REFRESH_TOKEN_VALIDITY);

                    RefreshToken refresh = new RefreshToken(refreshToken,
                            LocalDateTime.now().plusSeconds(Constants.REFRESH_TOKEN_VALIDITY));
                    refreshTokenDAO.saveToken(refresh);
                    
                    // ApiResponse kongResponse = kongUtil.createAPIKey(client);
                    
                    Map<String, Object> tokenMap = new HashMap<>();
                    tokenMap.put(Constants.ACCESS_TOKEN, token);
                    tokenMap.put(Constants.REFRESH_TOKEN, refreshToken);
                    // tokenMap.put(Constants.API_KEY, kongResponse.getResult().get("key"));
                    tokenMap.put(Constants.PROJECT, CommonUtil.getCloudProjectNumber());

                    return CommonUtil.setResponse(Constants.API_USER_LOGIN, HttpStatus.OK, tokenMap,
                            Constants.SUCCESS, null);
                } else {
                    return CommonUtil.setResponse(Constants.API_USER_LOGIN, HttpStatus.UNAUTHORIZED, null,
                            Constants.FAILED, Constants.MSG_UNAUTHORIZED_USER);

                }

            } else {
                return CommonUtil.setResponse(Constants.API_USER_LOGIN, HttpStatus.UNAUTHORIZED, null,
                        Constants.FAILED, Constants.MSG_UNAUTHORIZED_USER);
            }

        } catch (Exception e) {
            if (user.getNoOfTries() == null)
                user.setNoOfTries(0);
            else
                user.setNoOfTries(user.getNoOfTries() + 1);

            user.setLastUnsuccessfulLogin(LocalDateTime.now());
            userDAO.saveRegisteredUser(user);
            return CommonUtil.setResponse(Constants.API_USER_LOGIN, HttpStatus.UNAUTHORIZED, null,
                    Constants.FAILED, Constants.MSG_LOGIN_FAILED);

        }
    }

    public Authentication authenticate(String username, String password) throws Exception {
        UserModel user = userDAO.getUserById(username).get();
        Authentication authentication = null;
        try {

            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            if (user != null) {

                // User is authenticated => Reset noOfTries
                user.setNoOfTries(0);
                user.setLastSuccessfulLogin(LocalDateTime.now());
                userDAO.saveRegisteredUser(user);
            }

        } catch (Exception e) {

            // Authentication failed more than once in last 1 hour => Increment noOfTries
            if (user.getLastUnsuccessfulLogin()
                    .isAfter(LocalDateTime.now().minusHours(Constants.MAX_LOGIN_ATTEMPTS_HOUR))) {
                user.setNoOfTries(user.getNoOfTries() + 1);
            }
            // Authentication failed, but first time in last 1 hour => Set noOfTries = 1
            else {
                user.setNoOfTries(1);
            }
            user.setLastUnsuccessfulLogin(LocalDateTime.now());
            userDAO.saveRegisteredUser(user);

        }
        return authentication;
    }

    public boolean isValidCurrentPassword(AuthenticationRequestDto authenticationRequest)
            throws NoSuchAlgorithmException {

        UserModel user = userDAO.getUserById(authenticationRequest.getUserId()).get();
        if (user != null) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(authenticationRequest.getPassword().getBytes());
            byte[] digest = md.digest();
            String hashedPassword = DatatypeConverter.printHexBinary(digest);
            return hashedPassword.equalsIgnoreCase(user.getPassword());
        }
        return false;
    }

    public ApiResponse generateAccessTokenFromRefreshToken(String token) throws Exception {

        String username = null;
        String jwtToken = null;
        RefreshToken refresh = null;

        if (token != null && token.startsWith("Bearer ")) {
            jwtToken = token.substring(7);
            refresh = refreshTokenDAO.getToken(jwtToken);

            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                if (refresh.getExpiry().isBefore(LocalDateTime.now())) {
                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        information.expireNow();
                    }
                    logger.error("JWT Token has expired");
                    return CommonUtil.setResponse(Constants.API_REFRESH_TOKEN, HttpStatus.FOUND, null,
                            Constants.FAILED, Constants.MSG_EXPIRED_TOKEN);

                }

            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token");
                return CommonUtil.setResponse(Constants.API_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED, null,
                        Constants.FAILED, Constants.MSG_INVALID_TOKEN);

            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired");
                return CommonUtil.setResponse(Constants.API_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED, null,
                        Constants.FAILED, Constants.MSG_INVALID_TOKEN);

            }
        }
        if (username != null) {
            refresh.setExpiry(LocalDateTime.now());
            refreshTokenDAO.saveToken(refresh);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String accessToken = jwtTokenUtil.generateToken(authentication, Constants.JWT_TOKEN_VALIDITY);
            String refreshToken = jwtTokenUtil.generateToken(authentication, Constants.REFRESH_TOKEN_VALIDITY);

            refresh = new RefreshToken(refreshToken, LocalDateTime.now().plusSeconds(Constants.REFRESH_TOKEN_VALIDITY));
            refreshTokenDAO.saveToken(refresh);

            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put(Constants.ACCESS_TOKEN, accessToken);
            tokenMap.put(Constants.REFRESH_TOKEN, refreshToken);

            return CommonUtil.setResponse(Constants.API_REFRESH_TOKEN, HttpStatus.OK, tokenMap,
                    Constants.SUCCESS, null);

        } else {
            logger.error("Invalid Refresh Token");
            return CommonUtil.setResponse(Constants.API_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED, null,
                    Constants.FAILED, Constants.MSG_INVALID_TOKEN);

        }

    }

    // public ApiResponse login(AuthenticationRequestDto authenticationRequest)
    // throws IOException, InterruptedException, ParseException {
    // ApiResponse response = CommonUtil.createResponse(Constants.API_USER_LOGIN);
    // String keycloakUrl = keycloakHost + "/protocol/openid-connect/token";

    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    // // Prepare body
    // MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    // body.add("client_id", "paidnr");
    // body.add("username", authenticationRequest.getUserId());
    // body.add("password", authenticationRequest.getPassword());
    // body.add("grant_type", "password");

    // HttpEntity<MultiValueMap<String, String>> requestEntity = new
    // HttpEntity<>(body, headers);

    // ResponseEntity<Map> responseEntity = restTemplate.exchange(keycloakUrl,
    // HttpMethod.POST, requestEntity, Map.class, "PaidNR");

    // ObjectMapper mapper = new ObjectMapper();
    // Map<String, Object> responseMap = mapper.convertValue(responseEntity,
    // Map.class);
    // Map<String, Object> responseBodyMap =
    // mapper.convertValue(responseMap.get("body"), Map.class);
    // response.setResult(responseBodyMap);

    // return response;

    // }
}
