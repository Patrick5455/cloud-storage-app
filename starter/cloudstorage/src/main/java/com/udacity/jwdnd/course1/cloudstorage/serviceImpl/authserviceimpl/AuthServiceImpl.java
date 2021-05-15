package com.udacity.jwdnd.course1.cloudstorage.serviceImpl.authserviceimpl;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.utils.HashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;

@Service
public class AuthServiceImpl implements AuthService, AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final HashService hashService;
    private final UserService userService;

    @Autowired
   public AuthServiceImpl(HashService hashService, UserService userService){
        this.hashService = hashService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String providedPassword = authentication.getCredentials().toString();
        User userFromDB;
        try{
            userFromDB = userService.getUserByUserName(username);
        } catch (ResourceNotFoundException | IllegalArgumentException e){
            return  null;
        }
        String salt = userFromDB.getSalt();
        String hashedPassword = hashService.getHashedValue(providedPassword, salt);
        if(userFromDB.getPassword().equals(hashedPassword)) {
            logger.info("user authenticated @{}", Calendar.getInstance());
            return new UsernamePasswordAuthenticationToken(username, hashedPassword, new ArrayList<>());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
