package br.com.planilha.gastos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.builder.MessageBuilder;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.EmailWebServiceAdapter;

@Component
public class EmailService {

	@Autowired
	private MessageBuilder messageBuilder;
	
	@Autowired
	private EmailWebServiceAdapter emailWebServiceAdapter;
	
	public boolean sendDeviceVerificationEmail(User user, Device device) {
		String message = messageBuilder.buildDeviceVerificationMessage(user, device);
		
		emailWebServiceAdapter.send(user, message);
		
		return true;
	}

}
