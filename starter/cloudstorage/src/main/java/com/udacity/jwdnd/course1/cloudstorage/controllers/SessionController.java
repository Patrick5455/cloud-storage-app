package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
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

    @Autowired
    public SessionController(AuthService authService){
        this.authService = authService;
    }


    @GetMapping("/signup")
    public String signUp(@ModelAttribute("user")User user){

        return "signup";
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
