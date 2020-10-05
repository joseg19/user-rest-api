package com.joseg.userrestapi.service;


import com.joseg.userrestapi.entity.User;
import com.joseg.userrestapi.service.HashingService;
import com.joseg.userrestapi.service.impl.HashingServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HashingServiceTest {

    private HashingService hashingService;

    @Before
    public void init(){
        hashingService = new HashingServiceImpl();
    }


    @Test
    public void encodeAndMatch(){
        String encoded = hashingService.encode("texto");
        Assert.assertTrue(hashingService.matches("texto",encoded));
    }


}
