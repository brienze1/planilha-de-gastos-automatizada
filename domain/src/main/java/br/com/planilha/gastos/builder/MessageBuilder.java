package br.com.planilha.gastos.builder;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;

@Component
public class MessageBuilder {

	public String buildDeviceVerificationMessage(User user, Device device) {
		return user.getFirstName() + " to verify \"" + user.getEmail()
				+ "\" as a valid email adress for the MeMoney account enter the code \"" + device.getVerificationCode()
				+ "\" in the device registration screen.";
	}

}
