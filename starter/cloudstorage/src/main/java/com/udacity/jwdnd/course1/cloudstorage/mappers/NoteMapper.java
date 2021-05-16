package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)" +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
     int insertNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId}")
    List<Note> getNotesByUserId(long userId);

    @Update("UPDATE NOTES SET notetitle=#{note.noteTitle}, " +
            "notedescription=#{note.noteDescription} WHERE noteid=#{note.noteId} AND userid=#{userId}")
     void updateNote(Note note, long userId);

    @Delete("DELETE FROM NOTES WHERE userid = #{userId} AND noteid=#{noteId}")
     void deleteNote(long userId, long noteId);
}

