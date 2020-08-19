package com.udacity.mybatis.persistence_basics;

import com.udacity.mybatis.persistence_basics.model.ChatMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class ChatPage {
    @FindBy(id="messageText")
    private WebElement textField;

    @FindBy(id="messageType")
    private WebElement type;

    @FindBy(id="submitPage")
    private WebElement submitButton;

    @FindBy(className = "chatMessageUsername")
    private List<WebElement> messageUsernames;

    @FindBy(className = "chatMessageText")
    private List<WebElement> messageTexts;

    @FindBy(id="logout")
    private WebElement logoutButton;

    public ChatPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void sendChatMessage(String text) {
        this.textField.sendKeys(text);
        this.submitButton.click();
    }

    public List<ChatMessage> getMessage() {
        List<ChatMessage> allMessage = new ArrayList<>();
        for(int i=0;i<messageUsernames.size();i++){
            WebElement firstMessageText = messageTexts.get(i);
            WebElement firstMessageUsername = messageUsernames.get(i);
            ChatMessage result = new ChatMessage();
            result.setMessageText(firstMessageText.getText());
            result.setUserName(firstMessageUsername.getText());
            allMessage.add(result);
        }
        return allMessage;
    }

    public void logout(){
        logoutButton.click();
    }
}
