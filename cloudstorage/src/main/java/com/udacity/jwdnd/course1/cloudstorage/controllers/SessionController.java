package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.SignUpException;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.requests.LoginRequest;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.requests.SignupRequest;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

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

    @PostMapping("/signup")
    public String processSignUp(Model model,
                                @Valid @ModelAttribute("signup")
                                        SignupRequest signupRequest,
                                BindingResult validator){
        if(validator.hasErrors()){
            String passwordError = "password format not correct";
            logger.error("signup error {}", Objects.requireNonNull(Objects.requireNonNull(validator.getFieldError()).getArguments())[0].toString()
                    .contains("password") ? passwordError : "check fields are not blank");
            model.addAttribute("errorMessage", Objects.requireNonNull(Objects.requireNonNull(validator.getFieldError()).getArguments())[0].toString()
                    .contains("password") ? passwordError : "check fields are not blank");
            model.addAttribute("message", null);
            return "signup";
        }

        try{
            userService.createUser(signupRequest);
        }
        catch (SignUpException exception){
            logger.error("signup error {}",exception.getMessage());
            model.addAttribute("errorMessage", exception.getMessage());
            model.addAttribute("message", null);
            return "signup";
        }
        logger.info("signup successful");
        model.addAttribute("message", "You have signed up successfully.");
        return "signup";
        //  return"redirect:/login?success=true";
    }


    @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("login", new LoginRequest());
        return "login";
    }


    @GetMapping("/login-error")
    public String loginError(HttpServletRequest request, Model model) {
            HttpSession httpSession = request.getSession(false);
            String errorMessage = "incorrect login";
            if (httpSession != null) {
                AuthenticationException e = (AuthenticationException) httpSession.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                if (e != null) {
                    errorMessage = e.getMessage();
                }
            }
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/login";
    }


}
