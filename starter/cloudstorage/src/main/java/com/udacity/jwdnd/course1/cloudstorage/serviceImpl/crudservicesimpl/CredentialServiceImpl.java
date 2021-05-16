package com.udacity.jwdnd.course1.cloudstorage.serviceImpl.crudservicesimpl;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.CredentialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {


    @Override
    public int createCredential(Credential credential) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public List<Credential> getAllUserCredentials(int userId) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public int updateCredential(Credential credential, int userId) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public void deleteCredential(int userId, int credentialId) {

    }
}
