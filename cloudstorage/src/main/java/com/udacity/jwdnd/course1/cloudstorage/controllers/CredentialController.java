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



@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private static final Logger logger = LoggerFactory.getLogger(CredentialController.class);
    private final CredentialService credentialService;
    private final AuthService authService;
    private final UserService userService;
    

    @Autowired
    public CredentialController(CredentialService credentialService,
                                AuthService authService, UserService userService) {
        this.credentialService = credentialService;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping
    public String CreateOrUpdateCredential(
            @Valid @ModelAttribute("private") Credential credential,
            BindingResult validator, Model model){
        if(validator.hasErrors()){
            model.addAttribute("errorMessage", "invalid fields: fill all required fields correctly");
            validator.getFieldErrors().forEach(System.out::println);
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
                credential.setKey(EncryptionService.getKey());
               int lastInsertedId =  credentialService.createCredential(credential);
                logger.info("credential with id {} successfully created", lastInsertedId);
                model.addAttribute("errorMessage", null);
                return "redirect:/";
            } catch (Exception e){
                credential.setUserId(userId);
                model.addAttribute("errorMessage", "something went wrong, credential could not be created");
                return "result";
            }
        }

        try{ credential.setUserId(userId);
            credential.setKey(EncryptionService.getKey());
            credentialService.updateCredential(credential);
            logger.info("credential with id {} successfully updated", credential.getCredentialId());
            model.addAttribute("errorMessage", null);
            return "redirect:/";
        } catch (Exception e){
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
            return "redirect:/";
        }  catch (ResourceNotFoundException e){
        logger.error("error while deleting credential {}", e.getMessage());
        model.addAttribute("errorMessage", "credential could not be deleted, please try again");
        return "result";
    }
        
    }
}
