package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.response.FileResponse;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.net.http.HttpResponse;


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
            logger.error("an error occurred while uploading file {}", e.getMessage());
            if (e.getMessage().contains("user-error")) model.addAttribute("errorMessage", e.getMessage());
            else model.addAttribute("errorMessage", "an error occurred while uploading file");
            return "result";
        }
    }

    @GetMapping
    public String viewFile(Model model, int fileId) {
        try {
            FileResponse fileResponse = fileService.getFileById(fileId);
            model.addAttribute("message", fileResponse);
            return "home";
        } catch (Exception e) {
            logger.error("an error occurred while getting file {}", e.getMessage());
            model.addAttribute("errorMessage", "an error occurred while getting file");
            return "home";
        }
    }


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE}, value = "/delete")
    public String deleteFile(Model model, @RequestParam int fileId) {
        try {
            fileService.deleteFIleById(fileId);
            logger.info("file with id {} successfully deleted", fileId);
            return "redirect:/";
        } catch (Exception e) {
            logger.error("an error occurred while deleting file {}", e.getMessage());
            model.addAttribute("errorMessage", "an error occurred while deleting file");
            return "result";
        }
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam int fileId, Model model) {
        try {
            File file = fileService.getDownloadableFile(fileId);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .contentLength(file.getFileSize())
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(new ByteArrayResource(file.getFileData()));

        } catch (Exception e) {
            model.addAttribute("errorMessage", "file could not be downloaded, try later");
            return ResponseEntity.notFound()
                    .build();
        }
    }
}

