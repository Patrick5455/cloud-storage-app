package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {

    private int fileId;
    @NotBlank(message = "file name must be specified")
    private String fileName;
    @NotBlank(message = "file content type must be specified")
    private String contentType;
    private Integer fileSize;
    private int userId;
    private byte[] fileData;
}
