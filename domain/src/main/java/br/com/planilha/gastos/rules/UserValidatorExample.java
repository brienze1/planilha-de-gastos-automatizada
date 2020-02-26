package br.com.planilha.gastos.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.exception.UserAlreadyExistsExceptionExample;
import br.com.planilha.gastos.port.UserRepositoryAdapter;

@Component
public class UserValidatorExample {
	
	@Autowired
	private UserRepositoryAdapter repository;
	
	public void validateCreateUser(UserExample user) {
		if (repository.findByEmail(user.getEmail()).isPresent()) {
			throw new UserAlreadyExistsExceptionExample("User " + user.getEmail() + " already exists");
		}
	}

}
