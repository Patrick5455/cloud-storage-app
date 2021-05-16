package com.udacity.jwdnd.course1.cloudstorage.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credential {

    private int credentialId;
    @NotBlank(message = "please provide a url, url cannot be blank")
    private String url;
    @NotBlank(message = "please provide a username, username cannot be blank")
    private String username;
    private String key;
    @NotBlank(message = "please provide a password, password cannot be blank")
    private String password;
    private int userId;
}
