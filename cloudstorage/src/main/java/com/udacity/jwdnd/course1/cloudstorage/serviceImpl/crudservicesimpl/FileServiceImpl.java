package com.udacity.jwdnd.course1.cloudstorage.serviceImpl.crudservicesimpl;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.FileResponse;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileMapper fileMapper;
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public FileServiceImpl(FileMapper fileMapper, AuthService authService, UserService userService){
        this.fileMapper = fileMapper;
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public int uploadNewFile(MultipartFile file) throws Exception{
        try {
            if (file == null){
                throw new FileNotFoundException("please attach a file");
            }
            else {
                int userId = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();

                if(file.getOriginalFilename() == null || file.getOriginalFilename().equals("")){
                    throw new FileNotFoundException("please attach a file");
                }
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                File fileToSave = File.builder()
                        .fileName(fileName)
                        .contentType(file.getContentType())
                        .fileSize(file.getSize())
                        .userId(userId)
                        .fileData(file.getBytes())
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .build();
                return fileMapper.insertFile(fileToSave);
            }
        }
        catch (Exception e){
            logger.error("something went wrong while uploading  file {}", e.getMessage());
            throw new Exception("file could not be uploaded "+e.getMessage());
        }
    }

    @Override
    public FileResponse getFileByFileName(String filename) throws ResourceNotFoundException{
        int userid;
        try {
         userid  = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
         File file =  fileMapper.getFileByFileName(userid, filename);
            logger.info("{} file fetched from DB", file.getFileName());
            return new FileResponse(file);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("could not fetch file {} ", e.getMessage());
            throw new ResourceNotFoundException("could not fetch file "+e.getMessage());
        }
    }

    @Override
    public void updateFileName(File file) throws Exception{
        try{
            fileMapper.updateFile(file);
            logger.info("file updated");
        }
        catch (Exception e){
            logger.error("something went wrong while updating {} file", file.getFileName());
            throw new Exception("something went wrong while updating file " + e.getMessage());
        }
    }

    @Override
    public List<FileResponse> getAllUserFiles() throws ResourceNotFoundException {
        List<FileResponse> files = new ArrayList<>();
        try{
           int userid  = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
            files = fileMapper.getAllFiles(userid).
                    stream().map(FileResponse::new).collect(Collectors.toList());
            logger.info("{} files fetched from DB for user with id {}", files.size(), userid);
            System.out.println("fies from DB "+files);
            return files;
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("could not fetch files from DB {}", e.getMessage());
            throw new ResourceNotFoundException("could not fetch files from DB " +e.getMessage());
        }
    }

    @Override
    public void deleteFIle(String fileName) throws Exception {
        try{
            int userid  = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
           fileMapper.deleteFile(fileName, userid);
            logger.info("{} files has been deleted", fileName);
        }
        catch (Exception e){
            throw new Exception("something went wrong, file could not be deleted "+e.getMessage());
        }
    }
}
