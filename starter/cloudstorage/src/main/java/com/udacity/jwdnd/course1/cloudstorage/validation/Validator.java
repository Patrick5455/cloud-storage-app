package com.udacity.jwdnd.course1.cloudstorage.validation;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.SignUpException;
import com.udacity.jwdnd.course1.cloudstorage.models.requests.SignupRequest;

public class Validator {

    public static void validateSignUpRequest(SignupRequest request) throws SignUpException {

        if (request.getFirstName().isEmpty()){
            throw new SignUpException("firstname field cannot be empty");
        }
        if(request.getLastName().isEmpty()){
            throw new SignUpException("lastname field cannot be empty");
        }

        if (request.getUsername().isEmpty()){
            throw new SignUpException("username filed cannot be empty");
        }

        String pattern = "\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[@#$%^&+=])\\S{8,}\\z";
        if (!request.getPassword().matches(pattern)){
            throw new SignUpException("password must be at least 8 charcters in size.\nAnd contain at least one of digits, upper and lower case, a special character like ^\nNo whitespace is allowed also");
        }

    }
}
