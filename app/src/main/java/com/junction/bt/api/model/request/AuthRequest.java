package com.junction.bt.api.model.request;

/**
 * Created by sibirsky on 25.11.17.
 */

public class AuthRequest {

    private String login;
    private String password;

    public AuthRequest() { }

    public AuthRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
