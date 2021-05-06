package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Data;

@Data
public class Note {
    private int noteId;
    private String noteTitle;
    private String noteDescription;
    private int userId;

}
