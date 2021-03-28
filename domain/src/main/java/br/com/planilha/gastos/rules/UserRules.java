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

	@Autowired
	private DeviceRules deviceRules;

	public boolean validateUserRegistrationData(User user) {
		if (user == null) {
			throw new UserValidationException("User can't be null");
		}

		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new UserValidationException("Password can't be null or empty");
		}

		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new UserValidationException("Email can't be null or empty");
		}

		if (user.getFirstName() == null || user.getFirstName().isBlank()) {
			throw new UserValidationException("First name can't be null or empty");
		}

		if (user.getLastName() == null || user.getLastName().isBlank()) {
			throw new UserValidationException("Last name can't be null or empty");
		}

		deviceRules.validateDeviceRegistration(user.getDevices());

		if (repository.findByEmail(user.getEmail()).isPresent()) {
			throw new UserValidationException("User " + user.getEmail() + " already exists");
		}
		
		return true;
	}

	public boolean validate(Optional<User> user) {
		if (!user.isPresent()) {
			throw new UserNotFoundException("User not found");
		}

		return validate(user.get());
	}

	public boolean validate(User user) {
		if (user == null) {
			throw new UserValidationException("User can't be null");
		}

		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new UserValidationException("Password can't be null or empty");
		}

		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new UserValidationException("Email can't be null or empty");
		}

		if (user.getFirstName() == null || user.getFirstName().isBlank()) {
			throw new UserValidationException("First name can't be null or empty");
		}

		if (user.getLastName() == null || user.getLastName().isBlank()) {
			throw new UserValidationException("Last name can't be null or empty");
		}

		if (user.getDevices() == null || user.getDevices().isEmpty()) {
			throw new UserValidationException("Devices can't be null or empty");
		}

		deviceRules.validate(user.getDevices());

		if (user.getId() == null || user.getId().isBlank()) {
			throw new UserValidationException("ID can't be null or empty");
		}

		if (user.getSecret() == null || user.getSecret().isBlank()) {
			throw new UserValidationException("Secret can't be null or empty");
		}
		
		return true;
	}

}
