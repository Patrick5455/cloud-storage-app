package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller("/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @GetMapping()
    public String getFileUpload(){
        return "home";
    }

    @PostMapping
    public String uploadFile(Model model, @ModelAttribute("file")MultipartFile file){

        return "result";
    }
}
