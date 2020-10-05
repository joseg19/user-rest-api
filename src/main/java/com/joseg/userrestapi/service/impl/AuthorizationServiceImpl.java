package com.joseg.userrestapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joseg.userrestapi.dto.ErrorResponse;
import com.joseg.userrestapi.exception.PropertyNotFoundException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorizationServiceImpl extends OncePerRequestFilter {

    @Autowired
    private Environment env;

    private static final String PROP_JWT_SECRET = "jwt.secret";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            if (jwtTokenIsPresent(request, response)) {
                Claims claims = tokenValidate(request);
                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
                if(request.getServletPath().startsWith("/v")){
                    LOGGER.warn("Token is not present");
                    makeErrorResponse(response, "Token is not present", HttpStatus.UNAUTHORIZED);
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException ex) {
            LOGGER.warn(ex.getMessage());
            makeErrorResponse(response, "Invalid token or expired", HttpStatus.UNAUTHORIZED);
        }catch (PropertyNotFoundException ex){
            LOGGER.warn(ex.getMessage());
            makeErrorResponse(response, ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private Claims tokenValidate(HttpServletRequest request) {
        String jwtToken = request.getHeader(AUTHORIZATION).replace(BEARER+" ", "");
        String secret = env.getProperty(PROP_JWT_SECRET);
        if(secret == null || secret.isEmpty()){throw new PropertyNotFoundException(PROP_JWT_SECRET);}
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean jwtTokenIsPresent(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(AUTHORIZATION);
        return authenticationHeader != null && authenticationHeader.startsWith(BEARER+" ");
    }

    private void makeErrorResponse(HttpServletResponse response, String message, HttpStatus httpStatus) throws IOException{
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper()
                .writeValueAsString(new ErrorResponse(message)));

    }



}
