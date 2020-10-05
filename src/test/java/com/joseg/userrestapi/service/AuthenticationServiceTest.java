package com.joseg.userrestapi.service;


import com.joseg.userrestapi.dto.UserAuthenticationRequest;
import com.joseg.userrestapi.entity.User;
import com.joseg.userrestapi.exception.UnauthorizedException;
import com.joseg.userrestapi.repository.UserRepository;
import com.joseg.userrestapi.service.AuthenticationService;
import com.joseg.userrestapi.service.HashingService;
import com.joseg.userrestapi.service.impl.AuthenticationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {


    @InjectMocks
    private AuthenticationService authenticationService = new AuthenticationServiceImpl();
    @Mock
    private UserRepository userRepository;
    @Mock
    private Environment env;
    @Mock
    private HashingService hashingService;

    @Before
    public void init(){
        Mockito.when(userRepository.findByEmail("notExist@email.com"))
                .thenReturn(null);
        Mockito.when(userRepository.findByEmail("valid@email.com"))
                .thenReturn(new User(){{
                    setEmail("valid@email.com");
                    setPassword("fsdfggfd.sgdg");
                }});

        Mockito.when(env.getProperty("jwt.wrongemailorpassmsg")).thenReturn("Invalid email or password");
        Mockito.when(env.getProperty("jwt.secret")).thenReturn("123456789");

        Mockito.when(hashingService.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(true);
    }


    @Test
    public void wrongEmailOrPass(){
        Assertions.assertThrows(UnauthorizedException.class, () -> {
                    authenticationService.authenticateUser(new UserAuthenticationRequest(){{
                        setEmail("notExist@email.com");
                    }});
        });
    }

    @Test
    public void correctEmailAndPassword(){
        Assert.assertEquals("valid@email.com",
            authenticationService.authenticateUser(
                    new UserAuthenticationRequest(){{
                        setEmail("valid@email.com");
                        setPassword("gdfgfdgdfg");
                    }}).getEmail());
    }



}
