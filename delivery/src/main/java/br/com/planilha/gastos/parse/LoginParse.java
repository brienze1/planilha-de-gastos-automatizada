package br.com.planilha.gastos.parse;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.entity.Login;

@Component
public class LoginParse {

	public Login toLogin(LoginDto loginDto) {
		Login login = new Login();
		
		if(loginDto != null) {
			login.setDeviceId(loginDto.getDeviceId());
			login.setEmail(loginDto.getEmail());
			login.setPassword(loginDto.getPassword());
		}
		
		return login;
	}

}
