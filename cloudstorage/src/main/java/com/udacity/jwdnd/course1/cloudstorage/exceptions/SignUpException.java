package com.udacity.jwdnd.course1.cloudstorage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SignUpException extends Exception{

    public SignUpException(String message, Throwable throwable){
        super(message, throwable);
    }

    public SignUpException(String message){
        super(message);
    }

    public SignUpException(){
        super("Signup Error");
    }
}
