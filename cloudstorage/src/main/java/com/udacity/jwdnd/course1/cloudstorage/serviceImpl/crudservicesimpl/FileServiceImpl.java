package com.udacity.jwdnd.course1.cloudstorage.serviceImpl.crudservicesimpl;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.response.FileResponse;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final int MAX_FILE_SIZE = 1048576;

    private final FileMapper fileMapper;
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public FileServiceImpl(FileMapper fileMapper, AuthService authService, UserService userService) {
        this.fileMapper = fileMapper;
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public int uploadNewFile(MultipartFile file) throws Exception {
        try {

            int userId = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();

            if (file == null) {
                logger.error("cannot upload file | no file attachment found");
                throw new FileNotFoundException(" user-error:please attach a file");
            }

            if (file.getOriginalFilename() == null || file.getOriginalFilename().equals("")) {
                logger.error("cannot upload file | no file attachment found");
                throw new FileNotFoundException("user-error: please attach a file");
            }

            if (exceedsAllowedFIleSize(file)) {
                logger.error("file size limit exceeded");
                throw new FileSizeLimitExceededException("file size limit exceeded", file.getSize(), MAX_FILE_SIZE);
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            if (isExistingFile(fileName)) {
                logger.error("cannot upload file | file already exists");
                throw new IllegalArgumentException("user-error: that file already exists | name conflicts with an existing file");
            }
            File fileToSave = File.builder()
                    .fileName(fileName)
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .userId(userId)
                    .fileData(file.getBytes())
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            return fileMapper.insertFile(fileToSave);
        } catch (Exception e) {
            logger.error("something went wrong while uploading  file {}", e.getMessage());
            throw new Exception("file could not be uploaded " + e.getMessage());
        }
    }


    private boolean exceedsAllowedFIleSize(MultipartFile file) {
        return file.getSize() > MAX_FILE_SIZE;
    }

    private boolean isExistingFile(String fileName) throws Exception {
        int userid;
        try {
            userid = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
            File file = fileMapper.getFileByFileName(userid, fileName);
            logger.info("is not existing file : {}", file != null);
            return file != null;
        } catch (Exception e) {
            logger.error("could not fetch file {} ", e.getMessage());
            throw new Exception("an error occurred while fetching file from DB");
        }

    }

    @Override
    public FileResponse getFileByFileName(String filename) throws ResourceNotFoundException {
        int userid;
        try {
            userid = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
            File file = fileMapper.getFileByFileName(userid, filename);
            if (file == null) {
                logger.info("no file with name {} found", filename);
                return null;
            }
            logger.info("{} file fetched from DB", file.getFileName());
            return new FileResponse(file);
        } catch (Exception e) {
            logger.error("could not fetch file {} ", e.getMessage());
            throw new ResourceNotFoundException("could not fetch file " + e.getMessage());
        }
    }

    @Override
    public FileResponse getFileById(int id) throws Exception {
        try {
            File file = fileMapper.getFileById(id);
            if (file == null) {
                logger.error("file with id {} does not exist", id);
                return null;
            }
            else {
                logger.info("file fetched");
                return new FileResponse(file);
            }
        } catch (Exception e) {
            logger.error("could not fetch file {} ", e.getMessage());
            throw new ResourceNotFoundException("could not fetch file " + e.getMessage());
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
        List<FileResponse> files;
        try{
           int userid  = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
            files = fileMapper.getAllFiles(userid).
                    stream().map(FileResponse::new).collect(Collectors.toList());
            logger.info("{} files fetched from DB for user with id {}", files.size(), userid);

            return files;
        }
        catch (Exception e){
            logger.error("could not fetch files from DB {}", e.getMessage());
            throw new ResourceNotFoundException("could not fetch files from DB " +e.getMessage());
        }
    }

    @Override
    public void deleteFIleByName(String fileName) throws Exception {
        try{
            int userid  = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
           fileMapper.deleteFileByName(fileName, userid);
            logger.info("{} files has been deleted", fileName);
        }
        catch (Exception e){
            throw new Exception("something went wrong, file could not be deleted "+e.getMessage());
        }
    }

    @Override
    public void deleteFIleById(int id) throws Exception {
        try{
            int userid  = userService.getUserByUserName(authService.getLoggedInUser().getName()).getUserId();
            fileMapper.deleteFileById(id);
            logger.info("file with id {} has been deleted", id);
        }
        catch (Exception e){
            throw new Exception("something went wrong, file could not be deleted "+e.getMessage());
        }
    }

    @Override
    public File getDownloadableFile(int id) throws Exception{
        try{
            File fileData = fileMapper.getFileById(id);
            logger.info("downloadable data of file with id {} fetched", id);
            return fileData;
        }
        catch (Exception e){
            logger.error("file not found | file to download not found");
            throw new ResourceNotFoundException("file not found");
        }
    }



}
