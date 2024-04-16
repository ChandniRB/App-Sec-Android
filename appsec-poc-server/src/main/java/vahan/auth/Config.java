package vahan.auth;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${VAHAN_CONFIG}")
    private String configFilePath;

    @Bean
    public JSONObject configObject() throws IOException, ParseException {
        return (JSONObject) new JSONParser().parse(new FileReader(configFilePath));
    }
}
