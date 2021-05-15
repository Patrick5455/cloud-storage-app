package com.udacity.jwdnd.course1.cloudstorage.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    private int userId;
    @NotBlank(message = "username should not be empty")
    private String username;
    @NotBlank(message = "firstname should not be empty")
    private String firstName;
    @NotBlank(message = "lastname should not be blank")
    private String lastName;
    private String salt;
    @Pattern(regexp = "\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[@#$%^&+=])\\S{8,}\\z",
            message = "password must contain at least one digit, upper and lower case, and a  special character and no space")
    private String password;
    private String hashedPassword;


}
