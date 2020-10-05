package com.joseg.userrestapi.controller;

import com.joseg.userrestapi.dto.UserAuthenticationRequest;
import com.joseg.userrestapi.dto.UserAuthenticationResponse;
import com.joseg.userrestapi.service.impl.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;

    @PostMapping("token")
    public UserAuthenticationResponse getToken(@RequestBody @Valid UserAuthenticationRequest userAuthenticationRequest) {
        return authenticationServiceImpl.authenticateUser(userAuthenticationRequest);
    }

}
