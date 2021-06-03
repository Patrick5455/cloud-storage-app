package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);


    private final AuthService authService;
    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public NoteController(AuthService authService, NoteService noteService, UserService userService) {
        this.authService = authService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String createOrUpdateNote(@Valid @ModelAttribute("note")Note note, BindingResult validator, Model model)   {
        logger.info("note description "+ note.toString());
        if(validator.hasErrors()){
            logger.error("error while attempting to create note {}", validator.getFieldError());
            model.addAttribute("errorMessage","please provide a title and/or description");
            return "result";
        }
        try {
            int userId = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
            if (note.getNoteId() > 0) {
                noteService.updateANote(note, userId); }
            else{   note.setUserId(userId);
                noteService.createNote(note);}
        } catch (Exception e){
            logger.error("unexpected error {}", e.getMessage());
            model.addAttribute("errorMessage", "something went wrong, try again");
            return "result";
        }
        model.addAttribute("errorMessage",null);
        return "result";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteNote(@RequestParam("noteId") long noteId, Model model){
        try {
            int userId = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
            noteService.deleteANote(userId, noteId);
            logger.info("note with id {} successfully deleted", noteId);
        } catch (ResourceNotFoundException e){
            logger.error("error while deleting note {}", e.getMessage());
            model.addAttribute("errorMessage", "note could not be deleted, please try again");
            return "result";
        }
        model.addAttribute("errorMessage",null);
        return "result";
    }

    
    


}
