package com.udacity.jwdnd.course1.cloudstorage.services.crudservices;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

public interface FileService {

    public int uploadNewFile(File file) throws Exception;

    public File getFileByFileName(String filename) throws ResourceNotFoundException;

    public void updateFileName(File file) throws Exception;

    public List<File> getAllUserFiles() throws ResourceNotFoundException;

    public void deleteFIle(String fileName) throws Exception;
}
