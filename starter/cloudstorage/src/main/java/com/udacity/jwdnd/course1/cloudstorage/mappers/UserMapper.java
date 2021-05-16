package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper{


    @Insert("INSERT INTO USERS(username, salt, password, firstname, lastname) " +
            "VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    @Select("SELECT * FROM USERS WHERE username=#{username}")
    User getUserByUserName(String username);

    @Update("UPDATE USERS SET firstname=#{firstName}, lastname=#{lastName}, password=#{password} " +
            "WHERE userid=#{userId}")
    void updateUser(User user);

    @Delete("DELETE FROM USERS WHERE username=#{username}")
    void deleteUser(String username);







}
