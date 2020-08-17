package com.udacity.mybatis.persistence_basics.mapper;

import com.udacity.mybatis.persistence_basics.model.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("SELECT * FROM MESSAGES")
    List<ChatMessage> getMessages();

    @Insert("INSERT INTO MESSAGES(username,messagetext) VALUES(#{username},#{messageText})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addMessage(ChatMessage chatMessage);
}

