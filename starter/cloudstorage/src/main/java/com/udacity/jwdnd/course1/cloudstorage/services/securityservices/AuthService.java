package com.udacity.jwdnd.course1.cloudstorage.services.securityservices;


import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService implements AuthenticationProvider {


    private HashService hashService;

    @Autowired
    private void setHashService(HashService hashService){
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String username = authentication.getName();
       String password = authentication.getCredentials().toString();

       String hashedPassword = hashService.getHashedValue(password, "salt");

       if (hashedPassword.equals(password)){
            return new UsernamePasswordAuthenticationToken(username, hashedPassword, new ArrayList<>());
        }
       return null;
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
