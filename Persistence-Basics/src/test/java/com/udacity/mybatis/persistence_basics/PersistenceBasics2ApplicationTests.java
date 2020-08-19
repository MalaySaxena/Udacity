package com.udacity.mybatis.persistence_basics;

import com.udacity.mybatis.persistence_basics.model.ChatMessage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersistenceBasics2ApplicationTests {
	class SignupUser{
		private String firstName;
		private String lastName;
		private String username;
		private String password;
		private String currMessage;

		public SignupUser(String firstName, String lastName, String username, String password, String currMessage) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.username = username;
			this.password = password;
			this.currMessage = currMessage;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getCurrMessage() {
			return currMessage;
		}

		public void setCurrMessage(String currMessage) {
			this.currMessage = currMessage;
		}
	}

	@LocalServerPort
	public int port;

	public static WebDriver driver;

	public String baseURL;

	public SignupPage signupPage;
	public LoginPage loginPage;

	@BeforeAll
	public static void beforeAll(){
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll(){
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEachURL(){
		baseURL = baseURL = "http://localhost:" + port;
	}


	@Test
	public void testUserActions() throws InterruptedException {
		SignupUser user = new SignupUser("Max","Jose","MJ","mjpassword","Hi");

		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signup(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());

		driver.get(baseURL + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(user.getUsername(), user.getPassword());

		ChatPage chatPage = new ChatPage(driver);
		chatPage.sendChatMessage(user.getCurrMessage());

		List<ChatMessage> sentMessages = chatPage.getMessage();

		ChatMessage sentMessage = sentMessages.get(0);

		chatPage.logout();

		//driver.wait(2000);
		assertEquals(user.getUsername(),sentMessage.getUserName());
		assertEquals(user.getCurrMessage(),sentMessage.getMessageText());
	}

	@Test
	public void testPersistedMessage(){
		SignupUser user1 = new SignupUser("Max","Jose","MJ","mjpassword","Hi");
		SignupUser user2 = new SignupUser("Neel","Jose","NJ","njpassword","Hello");

		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signup(user1.getFirstName(), user1.getLastName(), user1.getUsername(), user1.getPassword());

		driver.get(baseURL + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(user1.getUsername(), user1.getPassword());

		ChatPage chatPage = new ChatPage(driver);
		chatPage.sendChatMessage(user1.getCurrMessage());
		chatPage.sendChatMessage(user1.getCurrMessage());

		List<ChatMessage> messagesSeenByUser1 = chatPage.getMessage();

		chatPage.logout();

		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signup(user2.getFirstName(), user2.getLastName(), user2.getUsername(), user2.getPassword());

		driver.get(baseURL + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(user1.getUsername(), user1.getPassword());

		chatPage = new ChatPage(driver);

		List<ChatMessage> messagesSeenByUser2 = chatPage.getMessage();

		assertEquals(messagesSeenByUser1.size(),messagesSeenByUser2.size());

		for(int i=0;i<messagesSeenByUser1.size();i++){
			assertEquals(messagesSeenByUser1.get(i).getUserName(),messagesSeenByUser2.get(i).getUserName());
			assertEquals(messagesSeenByUser1.get(i).getMessageText(),messagesSeenByUser2.get(i).getMessageText());
		}

	}

}
