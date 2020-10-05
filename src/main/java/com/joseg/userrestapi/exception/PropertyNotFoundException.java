package com.joseg.userrestapi.exception;

public class PropertyNotFoundException extends RuntimeException{
    public PropertyNotFoundException(String propertySource){
        super("Could not find property: "+propertySource);
    }
}
