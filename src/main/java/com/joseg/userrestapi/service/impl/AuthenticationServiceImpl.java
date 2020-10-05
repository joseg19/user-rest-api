package com.joseg.userrestapi.service.impl;

import com.joseg.userrestapi.dto.UserAuthenticationRequest;
import com.joseg.userrestapi.dto.UserAuthenticationResponse;
import com.joseg.userrestapi.entity.User;
import com.joseg.userrestapi.exception.PropertyNotFoundException;
import com.joseg.userrestapi.exception.UnauthorizedException;
import com.joseg.userrestapi.repository.UserRepository;
import com.joseg.userrestapi.service.AuthenticationService;
import com.joseg.userrestapi.service.HashingService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashingService hashingService;

    @Autowired
    private Environment env;

    private static final String PROP_JWT_SECRET = "jwt.secret";
    private static final String PROP_WRONG_USER_PASS_MSG = "jwt.wrongemailorpassmsg";

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Override
    public UserAuthenticationResponse authenticateUser(UserAuthenticationRequest userAuthenticationRequest){

        User user = userRepository.findByEmail(userAuthenticationRequest.getEmail());

        if(user == null || !hashingService.matches(userAuthenticationRequest.getPassword(),user.getPassword())) {
            String wrongUserOrPassMessage = env.getProperty(PROP_WRONG_USER_PASS_MSG);
            throw ( wrongUserOrPassMessage == null || wrongUserOrPassMessage.isEmpty() )
                    ? new PropertyNotFoundException(PROP_WRONG_USER_PASS_MSG)
                    : new UnauthorizedException(wrongUserOrPassMessage);
        }

        String token = getJWTToken(userAuthenticationRequest.getEmail());

        user.setToken(token);
        userRepository.save(user);
        LOGGER.info("Token updated on DB succesfully");

        UserAuthenticationResponse userAuthenticationResponse = new UserAuthenticationResponse();
        userAuthenticationResponse.setEmail(userAuthenticationRequest.getEmail());
        userAuthenticationResponse.setToken(token);
        return userAuthenticationResponse;
    }

    @Override
    public String getJWTToken(String userId) {
        String secretKey = env.getProperty(PROP_JWT_SECRET);
        if(secretKey == null || secretKey.isEmpty()){throw new PropertyNotFoundException(PROP_JWT_SECRET);}

        LOGGER.info("Getting a JWT...");
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setSubject(userId)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        LOGGER.info("Token created succesfully");
        return "Bearer " + token;
    }
}
