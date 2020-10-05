package com.joseg.userrestapi.dto;


public class UserAuthenticationResponse {

    private String email;
    private String token;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserAuthenticationResponse {" +
                "email='" + email + '\'' +
                ", token='" + "*****" + '\'' +
                '}';
    }
}
