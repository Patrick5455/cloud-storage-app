package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata, createdat)" +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int insertFile(File file);

    @Update("UPDATE FILES SET filename=#{fileName} WHERE userid=#{userId} AND filename=#{fileName};")
    public void updateFile(File file);

    @Select("SELECT * FROM FILES WHERE filename=#{fileName} AND userid=#{userId}")
    public File getFileByFileName(int userId, String fileName);

    @Select("SELECT fileId, filename, contenttype, filesize, userid, createdat FROM FILES WHERE userid=#{userId}")
    public List<File> getAllFiles(int userId);

    @Delete("DELETE FROM FILES WHERE filename=#{fileName} AND userid=#{userId}")
    public void deleteFile(String fileName, int userId);
}
