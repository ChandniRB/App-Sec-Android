package vahan.controller;

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
import jakarta.servlet.http.HttpServletRequest;
import vahan.auth.AuthService;
import vahan.auth.JwtTokenUtil;
import vahan.dao.paidnrv.UserDAO;
import vahan.model.ApiResponse;
import vahan.service.UserService;
import vahan.util.*;

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
            @RequestHeader(Constants.CLIENT) String client) throws Exception {

        String id = jwtTokenUtil.getUsernameFromToken(token.substring(7));

        ApiResponse response = userService.readUser(userId, id, integrityToken, client);

        return new ResponseEntity<>(response, response.getResponseCode());
    }


    @GetMapping(value = "/key/{consumer}")
    public ResponseEntity<?> getApiKey(@PathVariable String consumer,HttpServletRequest request) throws IOException, InterruptedException, ParseException {
        
        ApiResponse response = kongUtil.createAPIKey(consumer);
        return new ResponseEntity<>(response, response.getResponseCode());
    }

}