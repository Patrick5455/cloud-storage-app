package com.udacity.jwdnd.course1.cloudstorage.services.crudservices;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.dto.FileResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileService {

    public int uploadNewFile(MultipartFile file) throws Exception;

    public FileResponse getFileByFileName(String filename) throws ResourceNotFoundException;

    public void updateFileName(File file) throws Exception;

    public List<FileResponse> getAllUserFiles() throws ResourceNotFoundException;

    public void deleteFIle(String fileName) throws Exception;

    public File getFileById(int id) throws Exception;
}
