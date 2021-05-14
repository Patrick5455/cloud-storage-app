package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Data;

@Data
public class File {
    private int fileId;
    private String fileName;
    private String contentType;
    private Integer fileSize;
    private int userId;
    private Object fileData;
}
