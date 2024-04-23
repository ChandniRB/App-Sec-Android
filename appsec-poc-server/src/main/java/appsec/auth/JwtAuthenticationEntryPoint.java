package appsec.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import appsec.model.ApiResponse;
import appsec.util.CommonUtil;
import appsec.util.Constants;
import ch.qos.logback.classic.Logger;

import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        logger.error(authException.getMessage());
        ApiResponse apiResponse = CommonUtil.setResponse(null, HttpStatus.UNAUTHORIZED, null, Constants.FAILED, Constants.MSG_AUTH_EXCEPTION);
        response.getWriter().print(new ObjectMapper().convertValue(apiResponse, JSONObject.class));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}