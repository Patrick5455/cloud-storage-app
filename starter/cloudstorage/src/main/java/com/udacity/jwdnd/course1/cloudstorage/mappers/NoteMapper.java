package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO notes (notetitle, notedescription, userid, )")
    @Options(useGeneratedKeys = true, keyProperty = "noteID")
     int insertNote();

    @Select("SELECT * FROM notes WHERE userid = #{userId}")
     Note getNoteByUserId(String userId);

    @Update("UPDATE notes SET ")
     void updateNote();

     void deleteNote();
}
