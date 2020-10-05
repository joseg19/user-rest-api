package com.joseg.userrestapi.service;

import com.joseg.userrestapi.dto.UserAuthenticationRequest;
import com.joseg.userrestapi.dto.UserRequest;
import com.joseg.userrestapi.dto.UserResponse;
import com.joseg.userrestapi.entity.User;
import com.joseg.userrestapi.exception.EmailAlreadyRegisteredException;
import com.joseg.userrestapi.exception.UnauthorizedException;
import com.joseg.userrestapi.repository.UserRepository;
import com.joseg.userrestapi.service.AuthenticationService;
import com.joseg.userrestapi.service.HashingService;
import com.joseg.userrestapi.service.impl.AuthenticationServiceImpl;
import com.joseg.userrestapi.service.impl.UserServiceImpl;
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
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();
    @Mock
    private UserRepository userRepository;
    @Mock
    private Environment env;
    @Mock
    private HashingService hashingService;
    @Mock
    private AuthenticationService authenticationService;

    @Before
    public void init(){
        Mockito.when(userRepository.findByEmail("nuevo@email.com"))
                .thenReturn(null);
        Mockito.when(userRepository.findByEmail("valid.email@email.com"))
                .thenReturn(new User(){{setEmail("valid.email@email.com");}});

        Mockito.when(userRepository.save(ArgumentMatchers.any()))
                .thenReturn(new User(){{
                    setEmail("nuevo@email.com");
                    setPassword("Password123");
                }});
        Mockito.when(authenticationService.getJWTToken(ArgumentMatchers.anyString())).thenReturn("token");
        //Mockito.when(env.getProperty("jwt.wrongemailorpassmsg")).thenReturn("Invalid email or password");
        //Mockito.when(env.getProperty("jwt.secret")).thenReturn("123456789");

        //Mockito.when(hashingService.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(true);
    }



    @Test
    public void createUserSucces(){
        Assert.assertEquals("nuevo@email.com",
                userService.createUser(
                        new UserRequest(){{
                            setEmail("nuevo@email.com");
                            setPassword("Password123");
                        }}).getEmail());
    }


    @Test
    public void emailAlreadyRegistered() throws Exception {
        Exception exception = Assertions.assertThrows(EmailAlreadyRegisteredException.class, () -> {
            userService.createUser(new UserRequest(){{
                setEmail("valid.email@email.com");
            }});
        });

    }
}
