package br.com.planilha.gastos.service;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;

@Component
public class EmailService {

	public void sendDeviceVerificationEmail(User user, Device device) {
		// TODO Auto-generated method stub
		System.out.println("email enviado");
	}

}
