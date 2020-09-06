package br.com.planilha.gastos.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.UserAlreadyExistsExceptionExample;
import br.com.planilha.gastos.port.UserRepositoryAdapter;

@Component
public class UserValidator {
	
	@Autowired
	private UserRepositoryAdapter repository;
	
	public void validateUserRegistrationData(User user) {
		//Valida se o usuario nao esta nulo
		if (user == null) {
			throw new UserAlreadyExistsExceptionExample("User can't be null");
		}
		
		//Valida se a senha nao esta nula
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new UserAlreadyExistsExceptionExample("Password can't be null or empty");
		}
		
		//Valida se o email nao esta nulo
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new UserAlreadyExistsExceptionExample("Email can't be null or empty");
		}
		
		//Valida se o nome nao esta nulo
		if (user.getFirstName() == null || user.getFirstName().isBlank()) {
			throw new UserAlreadyExistsExceptionExample("First name can't be null or empty");
		}
		
		//Valida se o sobrenome nao esta nulo
		if (user.getLastName() == null || user.getLastName().isBlank()) {
			throw new UserAlreadyExistsExceptionExample("Last name can't be null or empty");
		}

		//Valida se ja existe um usuario cadastrado com o mesmo email
		if (repository.findByEmail(user.getEmail()).isPresent()) {
			throw new UserAlreadyExistsExceptionExample("User " + user.getEmail() + " already exists");
		}
	}

}
