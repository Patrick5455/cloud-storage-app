package com.udacity.jwdnd.course1.cloudstorage.serviceImpl.crudservicesimpl;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public int uploadNewFile(File file) {
        return 0;
    }

    @Override
    public File getFileByFileName(String filename) {
        return null;
    }

    @Override
    public void updateFileName(String currentFileName, String newFileName) {

    }

    @Override
    public List<File> getAllUserFiles(int userId) {
        return null;
    }

    @Override
    public void deleteFIle(String fileName) {

    }
}
