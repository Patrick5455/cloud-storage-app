package com.udacity.jwdnd.course1.cloudstorage.services.securityservices;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
public interface AuthService extends AuthenticationProvider {

    @Override
     Authentication authenticate(Authentication authentication) throws AuthenticationException;

    @Override
     boolean supports(Class<?> auth);

     default Authentication getLoggedInUser(){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        return authenticatedUser;
    }
}
