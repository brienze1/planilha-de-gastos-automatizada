package br.com.planilha.gastos.rules;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.DeviceException;
import br.com.planilha.gastos.exception.UserNotFoundException;
import br.com.planilha.gastos.exception.UserValidationException;
import br.com.planilha.gastos.port.UserRepositoryAdapter;

@ExtendWith(SpringExtension.class)
public class UserRulesTest {

	@InjectMocks
	private UserRules userRules;

	@Mock
	private UserRepositoryAdapter repository;

	@Mock
	private DeviceRules deviceRules;

	private User user;

	@BeforeEach
	public void init() {
		user = new User();
		user.setPassword(UUID.randomUUID().toString());
		user.setEmail(UUID.randomUUID().toString());
		user.setDevices(new ArrayList<>());
		user.getDevices().add(new Device());
		user.setAutoLogin(true);
		user.setFirstName(UUID.randomUUID().toString());
		user.setId(UUID.randomUUID().toString());
		user.setLastName(UUID.randomUUID().toString());
		user.setSecret(UUID.randomUUID().toString());
		user.setValidEmail(true);
	}

	@Test
	public void validateUserRegistrationDataTest() {
		boolean isValid = userRules.validateUserRegistrationData(user);

		Assertions.assertTrue(isValid);
	}
	
	@Test
	public void validateUserRegistrationDataUserNullErrorTest() {
		user = null;
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"User can't be null");
	}
	
	@Test
	public void validateUserRegistrationDataPasswordNullErrorTest() {
		user.setPassword(null);
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"Password can't be null or empty");
	}
	
	@Test
	public void validateUserRegistrationDataPasswordBlankErrorTest() {
		user.setPassword(" ");
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"Password can't be null or empty");
	}
	
	@Test
	public void validateUserRegistrationDataEmailNullErrorTest() {
		user.setEmail(null);
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"Email can't be null or empty");
	}
	
	@Test
	public void validateUserRegistrationDataEmailBlankErrorTest() {
		user.setEmail(" ");
	
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"Email can't be null or empty");
	}
	
	@Test
	public void validateUserRegistrationDataFirstNameNullErrorTest() {
		user.setFirstName(null);
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"First name can't be null or empty");
	}
	
	@Test
	public void validateUserRegistrationDataFirstNameBlankErrorTest() {
		user.setFirstName(" ");
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"First name can't be null or empty");
	}
	
	@Test
	public void validateUserRegistrationDataLastNameNullErrorTest() {
		user.setLastName(null);
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"Last name can't be null or empty");
	}
	
	@Test
	public void validateUserRegistrationDataLastNameBlankErrorTest() {
		user.setLastName(" ");
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"Last name can't be null or empty");
	}
	
	@Test
	public void validateUserRegistrationDataDeviceExceptionErrorTest() {
		Mockito.when(deviceRules.validateDeviceRegistration(user.getDevices())).thenThrow(new DeviceException("Can't register more than one device at a time"));
		
		Assertions.assertThrows(
				DeviceException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"Can't register more than one device at a time");
	}
	
	@Test
	public void validateUserRegistrationDataUserEmailAlreadyExistsErrorTest() {
		Mockito.when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validateUserRegistrationData(user), 
				"User " + user.getEmail() + " already exists");
	}
	
	@Test
	public void validateTest() {
		boolean isValid = userRules.validate(user);

		Assertions.assertTrue(isValid);
	}
	
	@Test
	public void validateOptionalTest() {
		boolean isValid = userRules.validate(Optional.of(user));
		
		Assertions.assertTrue(isValid);
	}

	@Test
	public void validateUserNotPresentErrorTest() {
		Assertions.assertThrows(
				UserNotFoundException.class, 
				() -> userRules.validate(Optional.ofNullable(null)), 
				"User not found");
	}

	@Test
	public void validateUserNullErrorTest() {
		user = null;

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"User can't be null");
	}

	@Test
	public void validatePasswordNullErrorTest() {
		user.setPassword(null);

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"Password can't be null or empty");
	}

	@Test
	public void validatePasswordBlankErrorTest() {
		user.setPassword(" ");

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"Password can't be null or empty");
	}

	@Test
	public void validateEmailNullErrorTest() {
		user.setEmail(null);

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"Email can't be null or empty");
	}

	@Test
	public void validateEmailBlankErrorTest() {
		user.setEmail(" ");

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"Email can't be null or empty");
	}

	@Test
	public void validateFirstNameNullErrorTest() {
		user.setFirstName(null);

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"First name can't be null or empty");
	}

	@Test
	public void validateFirstNameBlankErrorTest() {
		user.setFirstName(" ");

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"First name can't be null or empty");
	}

	@Test
	public void validateLastNameNullErrorTest() {
		user.setLastName(null);

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"Last name can't be null or empty");
	}

	@Test
	public void validateLastNameBlankErrorTest() {
		user.setLastName(" ");

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"Last name can't be null or empty");
	}

	@Test
	public void validateDevicesNullErrorTest() {
		user.setDevices(null);

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"Devices can't be null or empty");
	}

	@Test
	public void validateDevicesEmptyErrorTest() {
		user.setDevices(new ArrayList<>());

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"Devices can't be null or empty");
	}

	@Test
	public void validateDeviceExceptionErrorTest() {
		Mockito.when(deviceRules.validate(user.getDevices())).thenThrow(new DeviceException("Device Id can't be null or empty"));

		Assertions.assertThrows(
				DeviceException.class, 
				() -> userRules.validate(user), 
				"Device Id can't be null or empty");
	}

	@Test
	public void validateIdNullErrorTest() {
		user.setId(null);

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"ID can't be null or empty");
	}

	@Test
	public void validateIdBlankErrorTest() {
		user.setId(" ");

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"ID can't be null or empty");
	}

	@Test
	public void validateSecretNullErrorTest() {
		user.setSecret(null);

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"Secret can't be null or empty");
	}

	@Test
	public void validateSecretBlankErrorTest() {
		user.setSecret(" ");

		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userRules.validate(user), 
				"Secret can't be null or empty");
	}

}
