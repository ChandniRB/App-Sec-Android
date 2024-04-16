package vahan.util;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import vahan.model.*;

@Component
public class CommonUtil {

    
    
    @SuppressWarnings("unused")
    private JSONObject configObject;

    private static String mailHost;
    private static String host;
    private static String basePath;
    private static String uiPath;
    private static String secret;
    private static String googleCredentialPath;
    private static String kongUrl;
    private static String cloudProjectNumber;
    
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

    public static String getUiPath() {
        return uiPath;
    }

    public static void setUiPath(String uiPath) {
        CommonUtil.uiPath = uiPath;
    }

    public static String getBasePath() {
        return basePath;
    }

    public static String getHost() {
        return host;
    }

    public static String getMailHost() {
        return mailHost;
    }

    @Autowired
    public CommonUtil(JSONObject configObject) {
        this.configObject = configObject;
        CommonUtil.mailHost = configObject.get("mailHost").toString();
        CommonUtil.host = configObject.get("host").toString();
        CommonUtil.basePath = configObject.get("basePath").toString();
        CommonUtil.uiPath = configObject.get("uiPath").toString();
        CommonUtil.googleCredentialPath = configObject.get("googleCredentialPath").toString();
        CommonUtil.kongUrl = configObject.get("kongUrl").toString();
        CommonUtil.cloudProjectNumber = configObject.get("cloudProjectNumber").toString();
        
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
