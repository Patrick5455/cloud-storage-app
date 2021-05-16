package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private int noteId;
    @NotBlank(message = "note must have a title, cannot be blank")
    private String noteTitle;
    private String noteDescription;
    private int userId;

}
