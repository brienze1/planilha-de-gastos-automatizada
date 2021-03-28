package br.com.planilha.gastos.parse;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.entity.Login;

@Component
public class LoginDeliveryParse {

	public Login toLogin(LoginDto loginDto) {
		Login login = new Login();
		
		if(loginDto != null) {
			if(loginDto.getEmail() != null) {
				login.setEmail(loginDto.getEmail().toLowerCase());
			}
			login.setDeviceId(loginDto.getDeviceId());
			login.setPassword(loginDto.getPassword());
		}
		
		return login;
	}

}
