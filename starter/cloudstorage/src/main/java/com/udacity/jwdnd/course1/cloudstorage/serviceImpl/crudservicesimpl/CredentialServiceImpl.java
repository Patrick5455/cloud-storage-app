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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialServiceImpl implements CredentialService {

    private static final Logger logger = LoggerFactory.getLogger(CredentialServiceImpl.class);
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    @Autowired
    public CredentialServiceImpl(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @Override
    @SneakyThrows
    public int createCredential(Credential credential) throws IllegalArgumentException {
        int lastInsertedId;
        try{
            credential.setPassword(encryptPassword(credential));
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
        List<Credential> userCredentials = new ArrayList<>();
        try{
            userCredentials = credentialMapper.getAllCredentialsByUserId(userId)
            .stream().peek(cred -> cred.setRawPassword(decryptPassword(cred))).collect(Collectors.toList());
            return  userCredentials;
        } catch (Exception e){
            logger.error("error occurred while fetching user credentials from DB -> message: {}", e.getMessage());
            throw new ResourceNotFoundException("user credentials could not be fetched from the DB"); }
    }

    @Override
    public void updateCredential(Credential credential) throws Exception {
        try {
            credential.setPassword(encryptPassword(credential));
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

    public String encryptPassword( Credential credential){
        return encryptionService.encryptValue(credential.getRawPassword(), credential.getKey());
    };

    public String decryptPassword(Credential credential){
        return encryptionService.decryptValue(credential.getPassword(),credential.getKey());
    }


}
