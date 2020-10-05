package com.joseg.userrestapi.controller;

import com.joseg.userrestapi.dto.UserRequest;
import com.joseg.userrestapi.dto.UserResponse;
import com.joseg.userrestapi.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping(value = "", consumes = "application/json", produces = "application/json" )
    public UserResponse createUser(@RequestBody @Valid UserRequest userRequest){
        return userServiceImpl.createUser(userRequest);
    }


}
