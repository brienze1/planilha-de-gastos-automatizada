package br.com.planilha.gastos.service;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.builder.MessageBuilder;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.EmailWebServiceAdapter;

@ExtendWith(SpringExtension.class)
public class EmailServiceTest {

	@InjectMocks
	private EmailService emailService;
	
	@Mock
	private MessageBuilder messageBuilder;
	
	@Mock
	private EmailWebServiceAdapter emailWebServiceAdapter;
	
	private User user;
	private Device device;
	private String message;
	
	@BeforeEach
	public void init() {
		user = new User();
		
		device = new Device();
		
		message = UUID.randomUUID().toString();
	}
	
	@Test
	public void sendDeviceVerificationEmailTest() {
		Mockito.when(messageBuilder.buildDeviceVerificationMessage(user, device)).thenReturn(message);
		
		boolean isSent = emailService.sendDeviceVerificationEmail(user, device);
		
		Assertions.assertTrue(isSent);
		Mockito.verify(emailWebServiceAdapter).send(user, message);
	}
	
}
