package vahan.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;
import vahan.model.ApiResponse;

@Component
public class KongUtil {
private static final Logger logger = (Logger) LoggerFactory.getLogger(KongUtil.class);

    public ApiResponse createAPIKey(String consumer) throws IOException, InterruptedException, ParseException {
        logger.info(String.format("\n\n\nCalling DL Test Service API: /dl/key-auth\n\n\n"));
        String testServiceUrl = CommonUtil.getKongUrl() + consumer + "/key-auth";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(testServiceUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .header(Constants.ACCEPT, "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> httpresponse = HttpClient.newHttpClient().send(request,
                HttpResponse.BodyHandlers.ofString());
        ApiResponse response = CommonUtil.createResponse(Constants.API_ADMIN_TYPES_LIST);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(httpresponse.body());
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        obj = jsonArray.get(0);
        jsonObject = (JSONObject) obj;
        String key = jsonObject.get("key").toString();

        Map<String, Object> result = new HashMap<>();
        result.put("key", key);
        result.put("project", CommonUtil.getCloudProjectNumber());
        
        response.setResult(result);

        return response;

    }

}
