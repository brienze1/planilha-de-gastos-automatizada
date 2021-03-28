package br.com.planilha.gastos.builder;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;


@ExtendWith(SpringExtension.class)
public class MessageBuilderTest {

	@InjectMocks
	private MessageBuilder messageBuilder;
	
	private User user;
	private Device device;
	private String message;
	
	@BeforeEach
	public void init() {
		user = new User();
		user.setFirstName(UUID.randomUUID().toString());
		user.setEmail(UUID.randomUUID().toString());
		
		device = new Device();
		device.setVerificationCode(UUID.randomUUID().toString());
		
		message = user.getFirstName() + " to verify \"" + user.getEmail()
		+ "\" as a valid email adress for the MeMoney account enter the code \"" + device.getVerificationCode()
		+ "\" in the device registration screen.";
	}
	
	@Test
	public void buildDeviceVerificationMessageTest() {
		String returnedMessage = messageBuilder.buildDeviceVerificationMessage(user, device);
		
		Assertions.assertEquals(message, returnedMessage);
	}
	
}
