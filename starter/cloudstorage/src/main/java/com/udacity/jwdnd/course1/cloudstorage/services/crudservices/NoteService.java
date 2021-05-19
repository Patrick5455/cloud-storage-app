package com.udacity.jwdnd.course1.cloudstorage.services.crudservices;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;

import java.util.List;

public interface NoteService {

    int createNote(Note note) throws IllegalArgumentException;

    List<Note> getAllNotesByUserId(long userId) throws ResourceNotFoundException;

    void updateANote(Note note, long userId) throws Exception;

    void deleteANote(long userid, long noteId);
}
