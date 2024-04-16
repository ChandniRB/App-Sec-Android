package vahan.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vahan.model.ApiResponse;
import vahan.util.*;

import java.net.URI;
import java.util.UUID;

import vahan.dao.paidnrv.UserDAO;

@RestController
@CrossOrigin
@RequestMapping("/apis/auth")
public class JwtAuthenticationController {
    // private static final Logger logger = (Logger)
    // LoggerFactory.getLogger(StakeholderService.class);

   
    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthService authService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequest,
            HttpServletResponse httpResponse, HttpServletRequest request, @RequestHeader(Constants.CLIENT) String client)
            throws Exception {

        ApiResponse response = CommonUtil.createResponse(Constants.API_USER_LOGIN);

        if (authService.isValidCurrentPassword(authenticationRequest)) {
            String token = UUID.randomUUID().toString();

            authService.createVerificationToken(
                    userDAO.getUserById(authenticationRequest.getUserId()).get(), token,
                    Constants.PASSWORD_RESET_TOKEN_EXPIRATION);

            response = CommonUtil.setResponse(Constants.API_USER_LOGIN, HttpStatus.FOUND, null,
                    Constants.FAILED,
                    Constants.MSG_PASSWORD_RESET);
            URI location = ServletUriComponentsBuilder
                    .fromContextPath(request)
                    .path(Constants.PASSWORD_RESET_PATH)
                    .queryParam("token", token)
                    .build()
                    .toUri();
            httpResponse.setHeader("Location", location.toString());
            

        } else {
            response = authService.createAuthenticationToken(authenticationRequest, client);

            if (response.getResult() != null) {
                ResponseCookie cookie = ResponseCookie
                        .from("accessToken",
                                response.getResult().get(Constants.ACCESS_TOKEN)
                                        .toString())
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .sameSite("Lax")
                        .build();

                httpResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            }
            
        }

        return new ResponseEntity<>(response, response.getResponseCode());

    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader(Constants.AUTHORIZATION) String token,
            HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {

        ApiResponse response = CommonUtil.createResponse(Constants.API_REFRESH_TOKEN);

        response = authService.generateAccessTokenFromRefreshToken(token);

        if (response.getParams().getMessage() != null
                && response.getParams().getMessage().equalsIgnoreCase(Constants.MSG_EXPIRED_TOKEN)) {
            URI location = ServletUriComponentsBuilder
                    .fromContextPath(request)
                    .path(Constants.LOGOUT_PATH)
                    .build()
                    .toUri();

            httpResponse.setHeader("Location", location.toString());

            

        } 
        return new ResponseEntity<>(response, response.getResponseCode());

    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse httpResponse, HttpServletRequest request,
            @RequestHeader(Constants.AUTHORIZATION) String token) throws Exception {

        ApiResponse response = CommonUtil.setResponse(Constants.API_USER_LOGOUT, HttpStatus.FOUND, null,
                Constants.SUCCESS, null);
       URI location = ServletUriComponentsBuilder.fromHttpUrl(CommonUtil.getUiPath() + Constants.LOGIN_PAGE)
                .build()
                .toUri();
        httpResponse.setHeader("Location", location.toString());

        return new ResponseEntity<>(response, response.getResponseCode());

    }

}