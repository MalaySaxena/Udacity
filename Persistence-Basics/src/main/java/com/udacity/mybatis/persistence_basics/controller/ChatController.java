package com.udacity.mybatis.persistence_basics.controller;

import com.udacity.mybatis.persistence_basics.model.ChatForm;
import com.udacity.mybatis.persistence_basics.service.MessageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {
    MessageService messageService;

    public ChatController(MessageService messageService){
        this.messageService = messageService;
    }

    @GetMapping
    public String getMessages(@ModelAttribute("chatForm") ChatForm form, Model model){
        model.addAttribute("chatMessages",messageService.getAllMessages());
        return "chat";
    }
    @PostMapping
    public String addMessage(Authentication authentication,@ModelAttribute("chatForm") ChatForm form, Model model){
        form.setUsername(authentication.getName());
        messageService.addMessage(form);
        model.addAttribute("chatMessages",messageService.getAllMessages());
        form.setMessage("");
        return "chat";
    }

    @ModelAttribute("types")
    public String[] allTypes(){
        return new String[] {"Say","Shout","Whisper"};
    }
}
