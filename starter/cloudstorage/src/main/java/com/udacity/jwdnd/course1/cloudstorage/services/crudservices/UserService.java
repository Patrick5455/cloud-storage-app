package com.udacity.jwdnd.course1.cloudstorage.services.crudservices;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.exceptions.SignUpException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.requests.SignupRequest;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.HashService;
import com.udacity.jwdnd.course1.cloudstorage.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    UserMapper userMapper;
    HashService hashService;

    @Autowired
    public UserService(UserMapper userMapper, HashService hashService){
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public int createUser(SignupRequest request) throws SignUpException {

//        Validator.validateSignUpRequest(request);

        if (userNameExists(request.getUsername())){
            throw  new SignUpException("username already exists. Try another username ");
        }

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
        user.setPassword(hashedPassword);
        user.setSalt(salt);

        int lastInsertedId = userMapper.insertUser(user);
        logger.info("new user id {}", lastInsertedId);
        return lastInsertedId;
    }

    public boolean userNameExists(String username){
       User user =  userMapper.getUserByUserName(username);
       return user != null;
    }

    public User getUserByUserName(String username) throws ResourceNotFoundException {
        User user =  userMapper.getUserByUserName(username);
        if (user == null){
            throw  new ResourceNotFoundException("no user with that username");
        }
        return user;
    }
}
