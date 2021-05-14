package com.udacity.jwdnd.course1.cloudstorage.models;


import lombok.Data;

@Data
public class Credential {

    private int credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private int userId;
}
