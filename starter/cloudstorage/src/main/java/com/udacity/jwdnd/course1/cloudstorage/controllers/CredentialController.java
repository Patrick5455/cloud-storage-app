package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.utils.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;


@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private static final Logger logger = LoggerFactory.getLogger(CredentialController.class);


    private final CredentialService credentialService;
    private final AuthService authService;
    private final EncryptionService encryptionService;
    private final UserService userService;
    

    @Autowired
    public CredentialController(CredentialService credentialService, AuthService authService,
                                EncryptionService encryptionService, UserService userService) {
        this.credentialService = credentialService;
        this.authService = authService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }


    @PostMapping
    public String CreateOrUpdateCredential(
            @Valid @ModelAttribute("private") Credential credential,
            BindingResult validator, Model model){
        if(validator.hasErrors()){
            validator.getFieldErrors().forEach(System.out::println);
            model.addAttribute("errorMessage", "invalid fields: fill all required fields correctly");
            return "result";
        }
        int userId;
        try{
         userId = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
        } catch (ResourceNotFoundException e){
            logger.error("user not found");
            model.addAttribute("errorMessage", "something went wrong, please try again later");
            return "result";
        }

        if (credential.getCredentialId() < 1){
            try{
                credential.setUserId(userId);
                byte[] bytes = new byte[16];
                SecureRandom random = new SecureRandom();
                random.nextBytes(bytes);
                String key = Base64.getEncoder().encodeToString(bytes);
                credential.setKey(key);
                credentialService.createCredential(credential);
                model.addAttribute("errorMessage", null);
                return "result";
            }
            catch (Exception e){
                credential.setUserId(userId);
                model.addAttribute("errorMessage", "something went wrong, credential could not be created");
                return "result";
            }
        }

        try{ credential.setUserId(userId);
            byte[] bytes = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(bytes);
            String key = Base64.getEncoder().encodeToString(bytes);
            credential.setKey(key);
            credentialService.updateCredential(credential);
            logger.info("credential with id {} successfully updated", credential.getCredentialId());
            model.addAttribute("errorMessage", null);
            return "result";
        }
        catch (Exception e){
            model.addAttribute("errorMessage", "something went wrong, credential could not be updated");
            return "result";
        }
    }


    @RequestMapping(method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteCredential(@RequestParam("credentialId") int credentialId, Model model){
        try{
            int userId = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
            logger.info("credential with id {} successfully deleted", credentialId);
            credentialService.deleteCredential(userId, credentialId);
            model.addAttribute("errorMessage",null);
            return "result";
        }  catch (ResourceNotFoundException e){
        logger.error("error while deleting credential {}", e.getMessage());
        model.addAttribute("errorMessage", "credential could not be deleted, please try again");
        return "result";
    }
        
    }
}
