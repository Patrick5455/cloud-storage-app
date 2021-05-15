package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.SignUpException;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.requests.SignupRequest;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/sessions")
public class SessionController {

    AuthService authService;
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    public SessionController(AuthService authService, UserService userService) {
        this.userService = userService;
        this.authService = authService;
    }


    @GetMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("signup", new SignupRequest());
        return "signup";
    }

    @PostMapping("/process_signup")
    public String processSignUp(Model model, @ModelAttribute("signup") SignupRequest signupRequest){
        try{
            userService.createUser(signupRequest);
        }
        catch (SignUpException exception){
            logger.error("signup error {}",exception.getMessage());
            model.addAttribute("errorMessage", exception.getMessage());
            return "signup";
        }
        logger.info("signup successful");
        return "signup-success";
    }


    @GetMapping("/login")
    public String getLogin(@ModelAttribute String username, String password){

        return "login";
    }


    @PostMapping("/perform_login")
    public String login(@ModelAttribute User user){
        return "home";
    }
}
