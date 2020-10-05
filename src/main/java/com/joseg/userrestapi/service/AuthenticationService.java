package com.joseg.userrestapi.service;

import com.joseg.userrestapi.dto.UserAuthenticationRequest;
import com.joseg.userrestapi.dto.UserAuthenticationResponse;
import org.springframework.stereotype.Service;

public interface AuthenticationService {

    UserAuthenticationResponse authenticateUser(UserAuthenticationRequest userAuthenticationRequest);

    String getJWTToken(String userId);
}
