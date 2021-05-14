package com.udacity.jwdnd.course1.cloudstorage.models.requests;

import lombok.Data;

@Data
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
