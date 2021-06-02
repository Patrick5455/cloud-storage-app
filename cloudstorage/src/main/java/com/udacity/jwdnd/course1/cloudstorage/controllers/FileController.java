package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import org.apache.catalina.manager.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;
    private final AuthService authService;
    private final UserService userService;

    public FileController(FileService fileService, AuthService authService, UserService userService) {
        this.fileService = fileService;
        this.authService = authService;
        this.userService = userService;

    }

    @PostMapping
    public String uploadFile(Model model, @RequestParam("fileUpload") MultipartFile file) {
        try {
            fileService.uploadNewFile(file);
            model.addAttribute("errorMessage", null);
            return "result";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("an error occurred while uploading file {}", e.getMessage());
            if (e.getMessage().contains("please attach a file")) model.addAttribute("errorMessage", e.getMessage());
            else model.addAttribute("errorMessage", "an error occurred while uploading file");
            return "result";
        }
    }

}
