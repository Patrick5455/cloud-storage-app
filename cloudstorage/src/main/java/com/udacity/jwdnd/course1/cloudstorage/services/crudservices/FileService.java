package com.udacity.jwdnd.course1.cloudstorage.services.crudservices;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FileService {

    public int uploadNewFile(File file);

    public File getFileByFileName(String filename);

    public void updateFileName(String currentFileName, String newFileName);

    public List<File> getAllUserFiles(int userId);

    public void deleteFIle(String fileName);
}
