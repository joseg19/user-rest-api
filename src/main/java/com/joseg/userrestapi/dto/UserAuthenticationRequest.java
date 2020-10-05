package com.joseg.userrestapi.dto;

import javax.validation.constraints.NotNull;

public class UserAuthenticationRequest {

    @NotNull
    private String email;
    @NotNull
    private String password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAuthenticationRequest {" +
                "email='" + email + '\'' +
                ", password='" + "*****" + '\'' +
                '}';
    }
}
