package com.udacity.jwdnd.course1.cloudstorage.serviceImpl.crudservicesimpl;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.EncryptionService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {

    private static final Logger logger = LoggerFactory.getLogger(CredentialServiceImpl.class);
    private  final UserService userService;
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    @Autowired
    public CredentialServiceImpl(UserService userService, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @Override
    @SneakyThrows
    public int createCredential(Credential credential) throws IllegalArgumentException {
        int lastInsertedId;
        try{
            String unencryptedPass = credential.getPassword();
            String encryptedPass = encryptionService.encryptValue(unencryptedPass, credential.getKey());
            credential.setPassword(encryptedPass);
            lastInsertedId = credentialMapper.insertCredential(credential);
        }
        catch (Exception e){
            logger.error("credential db insertion error, error message: {}", e.getMessage());
            throw new Exception("an error occurred while storing a new credential ->"+e.getMessage());
        }
        return lastInsertedId;
    }

    @Override
    public List<Credential> getAllUserCredentials(int userId) throws ResourceNotFoundException {
        List<Credential> userCredentials;
        try{userCredentials = credentialMapper.getAllCredentialsByUserId(userId); }
        catch (Exception e){
            logger.error("error occurred while fetching user credentials from DB -> message: {}", e.getMessage());
            throw new ResourceNotFoundException("user credentials could not be fetched from the DB");
        }
        return userCredentials;
    }

    @Override
    public void updateCredential(Credential credential) throws Exception {
        try {
            String unencryptedPass = credential.getPassword();
            String encryptedPass = encryptionService.encryptValue(unencryptedPass, credential.getKey());
            credential.setPassword(encryptedPass);
            credentialMapper.updateCredential(credential);
        }
        catch (Exception e){
            logger.error("an error occurred while updating the credentials {}", e.getMessage());
            throw  new Exception("error updating credentials "+e.getMessage());};
    }

    @Override
    @SneakyThrows
    public void deleteCredential(int userId, int credentialId){
        try {
            credentialMapper.deleteCredential(userId, credentialId);
        }
        catch (Exception e){
            logger.error("an error occurred while deleting  the credentials {}", e.getMessage());
            throw  new Exception("error deleting  credentials "+e.getMessage());};
    }


}
