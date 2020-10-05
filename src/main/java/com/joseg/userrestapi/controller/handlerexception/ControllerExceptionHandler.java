package com.joseg.userrestapi.controller.handlerexception;

import com.joseg.userrestapi.dto.ErrorResponse;
import com.joseg.userrestapi.exception.PropertyNotFoundException;
import com.joseg.userrestapi.exception.UnauthorizedException;
import com.joseg.userrestapi.exception.EmailAlreadyRegisteredException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exception(Exception ex){
        LOGGER.error(ex.getMessage());
        return new ErrorResponse("Internal error");
    }

    @ExceptionHandler(value = PropertyNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponse propertyNotFoundException(PropertyNotFoundException ex){
        String sourceEx = String.join(".",ex.getStackTrace()[0].getClassName(),
                                                            ex.getStackTrace()[0].getMethodName());
        LOGGER.warn("propertyNotFoundException on {}: {} ", sourceEx, ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex){
        String sourceEx = String.join(".",ex.getStackTrace()[0].getClassName(),
                                                            ex.getStackTrace()[0].getMethodName());
        LOGGER.warn("methodArgumentNotValidException on {}: {} ", sourceEx, ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ErrorResponse(ex.getBindingResult().getFieldError().getField() +
                " " +
                ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(value = EmailAlreadyRegisteredException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse emailAlreadyRegisteredException(EmailAlreadyRegisteredException ex){
        String sourceEx = String.join(".", ex.getStackTrace()[0].getClassName(),
                                                    ex.getStackTrace()[0].getMethodName());
        LOGGER.warn("emailAlreadyRegisteredException on {}: {} ", sourceEx, ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorResponse unauthorizedException(UnauthorizedException ex){
        String sourceEx = String.join(".",ex.getStackTrace()[0].getClassName(),
                                                            ex.getStackTrace()[0].getMethodName());
        LOGGER.warn("unauthorizedException on {}: {} ", sourceEx, ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }



}
