package com.microservice.userservice.jwt;

public class LoginResponse {
    private String type;
    private String token;

    public LoginResponse(String token) {
        this.token = token;
        this.type = "Bearer ";
    }

    public LoginResponse(String type, String token) {
        this.type = type;
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
