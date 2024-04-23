package appsec.controller;

import java.io.FileReader;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import appsec.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/.well-known")
public class WellKnownController {
    
    @GetMapping(value = "/assetlinks.json")
    public HttpServletResponse readUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

        Object assetLinksObj =  new JSONParser().parse(new FileReader(CommonUtil.getAssetLinkPath()));

        JSONArray assetLinks = (JSONArray) assetLinksObj;
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.setContentType("application/json");
        PrintWriter out = httpResponse.getWriter();
        out.print(assetLinks);
        out.flush();

        return httpResponse;
    }

}
