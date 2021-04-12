package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO notes (notetitle, notedescription, userid, )")
     int insertNote();


     Note getNoteByUserId();

     void updateNote();

     void deleteNote();
}
