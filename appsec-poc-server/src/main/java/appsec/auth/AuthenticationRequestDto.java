package appsec.auth;

import java.io.Serializable;

public class AuthenticationRequestDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String userId;
    private String password;

    //need default constructor for JSON Parsing
    public AuthenticationRequestDto() {

    }

    public AuthenticationRequestDto(String username, String password) {
        this.setUserId(username);
        this.setPassword(password);
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String username) {
        this.userId = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}