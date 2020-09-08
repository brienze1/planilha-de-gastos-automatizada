package br.com.planilha.gastos.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Login;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.LoginException;
import br.com.planilha.gastos.port.PasswordUtilsAdapter;

@Component
public class LoginRules {
	
	@Autowired
	private PasswordUtilsAdapter passwordUtils;

	public void validate(Login login, User user) {
		if(login == null) {
			throw new LoginException("Login can't be null");
		}
		
		if(login.getEmail() == null || login.getEmail().isBlank()) {
			throw new LoginException("Email can't be null");
		}
		
		if(login.getPassword() == null || login.getPassword().isBlank()) {
			throw new LoginException("Password can't be null");
		}
		
		if(!user.getPassword().equals(passwordUtils.encode(login.getPassword(), user.getSecret()))) {
			throw new LoginException("Password does not match");
		}
	}

	
	
}
