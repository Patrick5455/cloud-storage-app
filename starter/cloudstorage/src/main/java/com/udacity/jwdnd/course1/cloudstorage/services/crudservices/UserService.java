package com.udacity.jwdnd.course1.cloudstorage.services.crudservices;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.SignUpException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.requests.SignupRequest;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.HashService;
import com.udacity.jwdnd.course1.cloudstorage.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    UserMapper userMapper;
    HashService hashService;


    @Autowired
    public UserService(UserMapper userMapper, HashService hashService){
        this.userMapper = userMapper;
    }

    public int createUser(SignupRequest request) throws SignUpException {

        Validator.validateSignUpRequest(request);

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String salt = Base64.getEncoder().encodeToString(key);
        String hashedPassword = hashService
                .getHashedValue(request.getPassword(), salt);

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setSalt(salt);
        user.setHashedPassword(hashedPassword);

        return userMapper.insertUser(user);
    }
}
