package com.udacity.jwdnd.course1.cloudstorage.validation;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.SignUpException;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.requests.SignupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {

    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    public static void validateSignUpRequest(SignupRequest request) throws SignUpException {

        if (request.getFirstName().isEmpty()){
            logger.error("firstname field cannot be empty");
            throw new SignUpException("firstname field cannot be empty");
        }
        if(request.getLastName().isEmpty()){
            logger.error("lastname field cannot be empty");
            throw new SignUpException("lastname field cannot be empty");
        }

        if (request.getUsername().isEmpty()){
            logger.error("username filed cannot be empty");
            throw new SignUpException("username filed cannot be empty");
        }

        String pattern = "\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[@#$%^&+=])\\S{8,}\\z";
        if (!request.getPassword().matches(pattern)){
            logger.error("password not supported");
            throw new SignUpException("password must contain at least one digit, upper and lower case, and a  special character and no space");
        }

    }
}
