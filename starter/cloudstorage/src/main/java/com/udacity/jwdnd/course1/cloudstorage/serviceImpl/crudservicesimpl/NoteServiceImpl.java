package com.udacity.jwdnd.course1.cloudstorage.serviceImpl.crudservicesimpl;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.ResourceNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.crudservices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import lombok.SneakyThrows;
import org.h2.pagestore.db.PageDataIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
    private final NoteMapper noteMapper;
    private final AuthService authService;

    @Autowired
    public NoteServiceImpl(NoteMapper noteMapper, AuthService authService) {
        this.noteMapper = noteMapper;
        this.authService = authService;
    }

    @SneakyThrows
    @Override
    public int createNote(Note note) throws IllegalArgumentException {
        int lastInsertedId;
        if (note.getNoteTitle() == null) {
            logger.error("note must have a title, please provide one");
            throw new IllegalArgumentException("note must have a title, please provide one");
        }
        try{
            lastInsertedId = noteMapper.insertNote(note);
        } catch (Exception e){
            logger.error("error occurred while creating note {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return lastInsertedId;
    }

    @Override
    @SneakyThrows
    public List<Note> getAllNotesByUserId(long userId) {
        List<Note> userNotes;

        try{userNotes = noteMapper.getNotesByUserId(userId).stream()
        .peek(note -> {
            if (note.getNoteDescription().length() > 10) {
                note.setNoteDescription(note.getNoteDescription().substring(0, 11));
            }
        }).collect(Collectors.toList());}
        catch (Exception e){
            logger.error("an error occurred while retrieving user notes {}", e.getMessage());
            throw new SQLException("an error occurred while retrieving user notes "+e.getMessage());
        }
        return userNotes;
    }

    @Override
    public void updateANote(Note note, long userId) throws Exception {
        try{noteMapper.updateNote(note, userId);}
        catch (Exception e){
            logger.error("an error occurred while updating  the notes {}", e.getMessage());
            throw new Exception("an error occurred while updating the note "+e.getMessage());
        }
        logger.info("note updated successfully");
    }

    @Override
    @SneakyThrows
    public void deleteANote(long userid, long noteId) {
        try{
            noteMapper.deleteNote(userid, noteId);
        }catch (Exception e){
            logger.error("something went wrong while deleting note {}", e.getMessage());
            throw  new Exception("something went wrong while deleting note" + e.getMessage());
        }
    }
}
