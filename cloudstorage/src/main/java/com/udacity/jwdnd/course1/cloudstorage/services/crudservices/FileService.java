package com.udacity.jwdnd.course1.cloudstorage.services.crudservices;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.response.FileResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileService {

    public int uploadNewFile(MultipartFile file) throws Exception;

    public FileResponse getFileByFileName(String filename) throws ResourceNotFoundException;

    public FileResponse getFileById(int id) throws Exception;

    public void updateFileName(File file) throws Exception;

    public List<FileResponse> getAllUserFiles() throws ResourceNotFoundException;

    public void deleteFIleByName(String fileName) throws Exception;

    public void deleteFIleById(int id) throws Exception;

    public File getDownloadableFile(int id) throws Exception;

}
