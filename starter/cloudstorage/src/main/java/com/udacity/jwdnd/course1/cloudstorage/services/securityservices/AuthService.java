package com.udacity.jwdnd.course1.cloudstorage.services.securityservices;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

public interface AuthService extends AuthenticationProvider {

    @Override
     Authentication authenticate(Authentication authentication) throws AuthenticationException;

    @Override
     boolean supports(Class<?> auth);

     static String getPrincipal(){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        return authenticatedUser.getName();
    }
}
