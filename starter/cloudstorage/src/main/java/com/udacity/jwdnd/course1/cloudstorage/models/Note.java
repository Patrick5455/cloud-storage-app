package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Note {
    private int noteId;
    @NotBlank(message = "note must have a title, cannot be blank")
    private String noteTitle;
    @NotBlank(message = "note must have a description, cannot be blank")
    private String noteDescription;
    private int userId;

}
