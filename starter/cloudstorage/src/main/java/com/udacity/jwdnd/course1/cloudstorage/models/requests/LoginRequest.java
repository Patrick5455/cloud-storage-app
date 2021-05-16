package com.udacity.jwdnd.course1.cloudstorage.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "username filed cannot be blank")
    private String username;
    @NotBlank(message = "password field cannot be blank")
    private String password;
}
