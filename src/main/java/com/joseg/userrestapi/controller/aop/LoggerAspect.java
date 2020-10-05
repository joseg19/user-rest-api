package com.joseg.userrestapi.controller.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    @Before("execution(public * com.joseg.userrestapi.controller.*.*(..))")         //point-cut expression
    public void logBeforeController(JoinPoint joinPoint){
        Class clazz = joinPoint.getSignature().getDeclaringType();
        String method = ( (MethodSignature)joinPoint.getSignature()).getMethod().getName();
        Object[] args = joinPoint.getArgs();
        LoggerFactory.getLogger(clazz).info(String.format("Before %s with args {}", method), args);
    }

    @AfterReturning(pointcut = "execution(public * com.joseg.userrestapi.controller.*.*(..))", returning = "retVal")         //point-cut expression
    public void logAfterController(JoinPoint joinPoint, Object retVal){
        Class clazz = joinPoint.getSignature().getDeclaringType();
        String method = ( (MethodSignature)joinPoint.getSignature()).getMethod().getName();
        LoggerFactory.getLogger(clazz).info(String.format("After %s with return {}", method), retVal);
    }


}
