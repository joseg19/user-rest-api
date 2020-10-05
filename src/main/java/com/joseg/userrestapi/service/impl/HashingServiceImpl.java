package com.joseg.userrestapi.service.impl;

import com.joseg.userrestapi.service.HashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class HashingServiceImpl implements HashingService {

    private static final int STRENGTH = 10;

    @Override
    public String encode(String plainText) {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(STRENGTH, new SecureRandom());
        return bCryptPasswordEncoder.encode(plainText);
    }

    @Override
    public boolean matches(String plainText, String encodedText) {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(STRENGTH, new SecureRandom());
        return bCryptPasswordEncoder.matches(plainText,encodedText);
    }



}
