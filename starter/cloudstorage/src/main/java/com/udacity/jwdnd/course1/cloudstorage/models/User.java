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
            message = "password must be at least 8 characters in size." +
            "\nAnd contain at least one of digits, upper and lower case, a special character like ^\nNo whitespace is allowed also")
    private String password;
    private String hashedPassword;


}
