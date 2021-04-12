package com.udacity.jwdnd.course1.cloudstorage.models;


import lombok.Data;

@Data
public class User {
    private int userId;
    private String username;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;

}
