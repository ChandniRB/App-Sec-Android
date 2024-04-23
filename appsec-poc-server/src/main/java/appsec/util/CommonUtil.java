package appsec.util;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import appsec.model.*;

@Component
public class CommonUtil {

    
    
    @SuppressWarnings("unused")
    private JSONObject configObject;

    private static String basePath;
    private static String secret;
    private static String googleCredentialPath;
    private static String kongUrl;
    private static String cloudProjectNumber;
    private static String assetLinkPath;
    private static String apiKey;
    
    public static String getApiKey() {
        return apiKey;
    }

    public static String getAssetLinkPath() {
        return assetLinkPath;
    }

    public static String getCloudProjectNumber() {
        return cloudProjectNumber;
    }

    public static String getKongUrl() {
        return kongUrl;
    }

    public static String getGoogleCredentialPath() {
        return googleCredentialPath;
    }

    public static String getSecret() {
        return secret;
    }

    

    public static String getBasePath() {
        return basePath;
    }

    
    @Autowired
    public CommonUtil(JSONObject configObject) {
        this.configObject = configObject;
        CommonUtil.basePath = configObject.get("basePath").toString();
        CommonUtil.googleCredentialPath = configObject.get("googleCredentialPath").toString();
        CommonUtil.kongUrl = configObject.get("kongUrl").toString();
        CommonUtil.cloudProjectNumber = configObject.get("cloudProjectNumber").toString();
        CommonUtil.assetLinkPath = configObject.get("assetLinksPath").toString();
        CommonUtil.apiKey = configObject.get("apiKey").toString();
        
    }
    


    public static ApiResponse createResponse(String api) {
        ApiResponse response = new ApiResponse();
        response.setId(api);
        response.setVer("1.0");
        response.setParams(new ApiResponseParams());
        response.getParams().setStatus("success");
        response.setResponseCode(HttpStatus.OK);
        response.setTs(LocalDateTime.now().toString());
        return response;
    }

    

    public static ApiResponse setResponse(String apiID, HttpStatus responseCode, Map<String, Object> result,
            String status,
            String errMsg) {

        ApiResponse response = CommonUtil.createResponse(apiID);

        response.setResponseCode(responseCode);
        response.setResult(result);
        response.getParams().setMessage(errMsg);
        response.getParams().setStatus(status);
        return response;
    }

    public static boolean isMatch(String value, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();

    }

}
