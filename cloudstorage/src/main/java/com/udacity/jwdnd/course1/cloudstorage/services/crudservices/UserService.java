package com.udacity.jwdnd.course1.cloudstorage.services.crudservices;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.exceptions.SignUpException;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.requests.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    int createUser(SignupRequest request) throws SignUpException;

    boolean userNameExists(String username);

    User getUserByUserName(String username) throws ResourceNotFoundException;
}
