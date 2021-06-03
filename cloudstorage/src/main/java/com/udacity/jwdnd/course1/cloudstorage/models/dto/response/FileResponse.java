package com.udacity.jwdnd.course1.cloudstorage.models.dto.response;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;
import java.util.OptionalLong;

@Data
@Builder
@AllArgsConstructor
public class FileResponse {
    private long id;
    private String name;
    private String size;
    private String createdAt;

    public FileResponse(File file){
        id = OptionalLong.of(file.getFileId()).orElse(0L);
        name = Optional.of(file.getFileName()).orElse("no file name");
        size = Optional.of(String.format("%.2f KB", Math.ceil(file.getFileSize()/1024.0))).orElse("0 MB");
        createdAt = file.getCreatedAt() == null ? "not found" : file.getCreatedAt().toString();
    }

}
