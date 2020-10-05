package com.joseg.userrestapi.service;

import org.springframework.stereotype.Service;

public interface HashingService {

    String encode(String plainText);

    boolean matches(String plainText, String encodedText);

}
