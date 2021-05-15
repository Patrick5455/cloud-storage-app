package com.udacity.jwdnd.course1.cloudstorage.services.securityservices;


import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.HashService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private HashService hashService;
    private UserService userService;

    @Autowired
    private void setHashService(HashService hashService, UserService userService){
        this.hashService = hashService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String username = authentication.getName();
       String password = authentication.getCredentials().toString();
        User user;
       try {
            user = userService.getUserByUserName(username);
       } catch (ResourceNotFoundException e){
           logger.error("error confirming user exists: {}", e.getMessage());
           return null;
       }
       String hashedPassword = hashService.getHashedValue(password, user.getSalt());
       if (hashedPassword.equals(user.getPassword())){
            return new UsernamePasswordAuthenticationToken(username, hashedPassword, new ArrayList<>());
        }
        logger.error("auth error: user authentication failed");
        return null;
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

    public static String getPrincipal(){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        logger.info("authenticated user {}", authenticatedUser.getPrincipal().toString());
        return authenticatedUser.getName();
    }
}
