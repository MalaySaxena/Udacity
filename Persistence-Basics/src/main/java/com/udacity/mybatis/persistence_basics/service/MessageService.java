package com.udacity.mybatis.persistence_basics.service;

import com.udacity.mybatis.persistence_basics.mapper.MessageMapper;
import com.udacity.mybatis.persistence_basics.model.ChatForm;
import com.udacity.mybatis.persistence_basics.model.ChatMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class MessageService {
    private MessageMapper messageMapper;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("Creating message service bean");
    }


    public List<ChatMessage> getAllMessages() {
        return messageMapper.getMessages();
    }

    public void addMessage(ChatForm form) {
        ChatMessage newMessage = new ChatMessage();
        newMessage.setUserName(form.getUsername());
        String message = "";
        switch (form.getType()){
            case "Say": message = form.getMessage();
                break;
            case "Shout": message = form.getMessage().toUpperCase();
                break;
            case "Whisper": message = form.getMessage().toLowerCase();
                break;
        }
        newMessage.setMessageText(message);
        messageMapper.addMessage(newMessage);
    }
}
