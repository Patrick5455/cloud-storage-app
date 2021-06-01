package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int insertFile(File file);

    @Select("SELECT * FROM FILES WHERE filename=#{fileName} AND userid=#{userId}")
    public File getFileByFileName(int userId, String fileName);

    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
    public List<File> getAllFiles(int userId);

    @Delete("DELETE * FROM FILES WHERE filename=#{fileName} AND userid=#{userId}")
    public void deleteFile(int fileName, int userId);
}
