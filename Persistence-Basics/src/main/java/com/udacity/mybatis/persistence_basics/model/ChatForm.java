package com.udacity.mybatis.persistence_basics.model;

public class ChatForm {
    private String username,message,type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String chatMessage) {
        this.message = chatMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

