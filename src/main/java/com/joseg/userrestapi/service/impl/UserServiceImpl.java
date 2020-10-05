package com.joseg.userrestapi.service.impl;

import com.joseg.userrestapi.dto.UserRequest;
import com.joseg.userrestapi.dto.UserResponse;
import com.joseg.userrestapi.entity.Phone;
import com.joseg.userrestapi.entity.User;
import com.joseg.userrestapi.exception.EmailAlreadyRegisteredException;
import com.joseg.userrestapi.repository.UserRepository;
import com.joseg.userrestapi.service.AuthenticationService;
import com.joseg.userrestapi.service.HashingService;
import com.joseg.userrestapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private HashingService hashingService;

    @Autowired
    private AuthenticationService authenticationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserResponse createUser (UserRequest userRequest){

        if(userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new EmailAlreadyRegisteredException(userRequest.getEmail());
        }

        ModelMapper modelMapper = new ModelMapper();
        LocalDateTime now = LocalDateTime.now();

        User user = modelMapper.map(userRequest, User.class);
        user.setId(UUID.randomUUID());
        user.setCreated(now);
        user.setToken(authenticationService.getJWTToken(userRequest.getEmail()));
        user.setLastLogin(now);
        user.isActive(true);
        user.setPassword(hashingService.encode(userRequest.getPassword()));

        User userp = new User(){{setId(user.getId());}};
        if(user.getPhones() != null){
            user.getPhones().stream().forEach(p -> p.setUser(userp));
        }

        LOGGER.info("Saving User...");
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

}
