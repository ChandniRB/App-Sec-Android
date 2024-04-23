package appsec.controller;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import appsec.auth.AuthService;
import appsec.auth.JwtTokenUtil;
import appsec.dao.paidnrv.UserDAO;
import appsec.model.ApiResponse;
import appsec.service.UserService;
import appsec.util.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/apis/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    AuthService authService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    KongUtil kongUtil;
    
    @GetMapping(value = "/read/{userId}")
    public ResponseEntity<?> readUser(@PathVariable("userId") String userId, HttpServletRequest httpRequest,
            @RequestHeader(Constants.AUTHORIZATION) String token, @RequestHeader(Constants.INTEGRITY_TOKEN) String integrityToken, 
            @RequestHeader(Constants.CLIENT) String client, @RequestHeader(Constants.X_API_KEY) String apiKey) throws Exception {

        String id = jwtTokenUtil.getUsernameFromToken(token.substring(7));

        ApiResponse response = userService.readUser(userId, id, integrityToken, client);

        return new ResponseEntity<>(response, response.getResponseCode());
    }


    @GetMapping(value = "/key/{consumer}")
    public ResponseEntity<?> getApiKey(@PathVariable String consumer,HttpServletRequest request) throws IOException, InterruptedException, ParseException {
        
        ApiResponse response = kongUtil.fetchAPIKey(consumer);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

}