package com.udacity.jwdnd.course1.cloudstorage.models.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
