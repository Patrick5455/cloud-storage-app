package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final NoteService noteService;
    private final CredentialService credentialService;
    private final FileService fileService;
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public HomeController(NoteService noteService, CredentialService credentialService,
                          FileService fileService, AuthService authService, UserService userService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.authService = authService;
        this.userService = userService;
    }


    @GetMapping
    public String home(Model model) throws ResourceNotFoundException{
        int userId;

        try {
             userId = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
        } catch (ResourceNotFoundException e){
            logger.error("error getting userid->  {}",e.getMessage());
            return "home";
        }

        model.addAttribute("files", new ArrayList<File>());
        model.addAttribute("notes", Optional.ofNullable(noteService.getAllNotesByUserId(userId)).orElse(new ArrayList<>()));
        model.addAttribute("privates", new ArrayList<Credential>());
        model.addAttribute("note", new Note());
       model.addAttribute("private", new Credential());
        return "home";
    }

}

