package com.udacity.jwdnd.course1.cloudstorage.services.crudservices;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CredentialService {

    int createCredential(Credential credential) throws IllegalArgumentException;

    List<Credential> getAllUserCredentials(int userId) throws ResourceNotFoundException;

    int updateCredential(Credential credential, int userId) throws IllegalArgumentException;

    void deleteCredential(int userId, int credentialId);



}
