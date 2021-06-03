package com.udacity.jwdnd.course1.cloudstorage.models.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignupRequest {
    @NotBlank(message = "username should not be empty")
    private String username;
    @NotBlank(message = "firstname should not be empty")
    private String firstName;
    @NotBlank(message = "lastname should not be blank")
    private String lastName;
    @Pattern(regexp = "\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[@#$%^&+=])\\S{8,}\\z",
            message = "password must contain at least one digit, upper and lower case, and a  special character and no space")
    private String password;
}
