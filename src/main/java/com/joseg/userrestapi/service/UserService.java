package com.joseg.userrestapi.service;

import com.joseg.userrestapi.dto.UserRequest;
import com.joseg.userrestapi.dto.UserResponse;

public interface UserService {

    UserResponse createUser (UserRequest userRequest);

}
