package br.com.planilha.gastos.rules;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.UserNotFoundException;
import br.com.planilha.gastos.exception.UserValidationException;
import br.com.planilha.gastos.port.UserRepositoryAdapter;

@Component
public class UserRules {

	@Autowired
	private UserRepositoryAdapter repository;

	public void validateUserRegistrationData(User user) {
		// Valida se o usuario nao esta nulo
		if (user == null) {
			throw new UserValidationException("User can't be null");
		}

		// Valida se a senha nao esta nula
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new UserValidationException("Password can't be null or empty");
		}

		// Valida se o email nao esta nulo
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new UserValidationException("Email can't be null or empty");
		}

		// Valida se o nome nao esta nulo
		if (user.getFirstName() == null || user.getFirstName().isBlank()) {
			throw new UserValidationException("First name can't be null or empty");
		}

		// Valida se o sobrenome nao esta nulo
		if (user.getLastName() == null || user.getLastName().isBlank()) {
			throw new UserValidationException("Last name can't be null or empty");
		}

		// Valida se ja existe um usuario cadastrado com o mesmo email
		if (repository.findByEmail(user.getEmail()).isPresent()) {
			throw new UserValidationException("User " + user.getEmail() + " already exists");
		}
	}

	public void validate(Optional<User> user) {
		if (!user.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		
		validate(user.get());
	}

	public void validate(User user) {
		// Valida se o usuario nao esta nulo
		if (user == null) {
			throw new UserValidationException("User can't be null");
		}

		// Valida se a senha nao esta nula
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new UserValidationException("Password can't be null or empty");
		}

		// Valida se o email nao esta nulo
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new UserValidationException("Email can't be null or empty");
		}

		// Valida se o nome nao esta nulo
		if (user.getFirstName() == null || user.getFirstName().isBlank()) {
			throw new UserValidationException("First name can't be null or empty");
		}

		// Valida se o sobrenome nao esta nulo
		if (user.getLastName() == null || user.getLastName().isBlank()) {
			throw new UserValidationException("Last name can't be null or empty");
		}

		if(user.getDeviceId() == null || user.getDeviceId().isBlank()) {
			throw new UserValidationException("Device ID can't be null or empty");
		}
		
		if(user.getId() == null || user.getId().isBlank()) {
			throw new UserValidationException("ID can't be null or empty");
		}
		
		if(user.getSecret() == null || user.getSecret().isBlank()) {
			throw new UserValidationException("Secret can't be null or empty");
		}
	}

}
