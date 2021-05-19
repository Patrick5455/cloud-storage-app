package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid)" +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
     int insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
    List<Credential> getAllCredentialsByUserId(int userId);

    @Update("UPDATE CREDENTIALS SET url=#{url}, " +
            "username=#{username}, key=#{key}, " +
            "password=#{password} " +
            "WHERE credentialid=#{credentialId} AND userid=#{userId};")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE userid=#{userId} AND credentialid=#{credentialId}")
    void deleteCredential(int userId, int credentialId);



}
