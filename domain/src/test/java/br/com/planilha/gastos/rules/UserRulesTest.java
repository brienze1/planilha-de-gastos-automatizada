package br.com.planilha.gastos.rules;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.DeviceException;
import br.com.planilha.gastos.exception.UserNotFoundException;
import br.com.planilha.gastos.exception.UserValidationException;
import br.com.planilha.gastos.port.UserRepositoryAdapter;

@RunWith(SpringRunner.class)
public class UserRulesTest {

	@InjectMocks
	private UserRules userRules;

	@Mock
	private UserRepositoryAdapter repository;

	@Mock
	private DeviceRules deviceRules;

	private User user;

	@Before
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

		Assert.assertTrue(isValid);
	}
	
	@Test(expected = UserValidationException.class)
	public void validateUserRegistrationDataUserNullErrorTest() {
		user = null;
		
		try {
			userRules.validateUserRegistrationData(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("User can't be null", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = UserValidationException.class)
	public void validateUserRegistrationDataPasswordNullErrorTest() {
		user.setPassword(null);
		
		try {
			userRules.validateUserRegistrationData(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Password can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = UserValidationException.class)
	public void validateUserRegistrationDataPasswordBlankErrorTest() {
		user.setPassword(" ");
		
		try {
			userRules.validateUserRegistrationData(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Password can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = UserValidationException.class)
	public void validateUserRegistrationDataEmailNullErrorTest() {
		user.setEmail(null);
		
		try {
			userRules.validateUserRegistrationData(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Email can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = UserValidationException.class)
	public void validateUserRegistrationDataEmailBlankErrorTest() {
		user.setEmail(" ");
	
		try {
			userRules.validateUserRegistrationData(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Email can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = UserValidationException.class)
	public void validateUserRegistrationDataFirstNameNullErrorTest() {
		user.setFirstName(null);
		
		try {
			userRules.validateUserRegistrationData(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("First name can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = UserValidationException.class)
	public void validateUserRegistrationDataFirstNameBlankErrorTest() {
		user.setFirstName(" ");
		
		try {
			userRules.validateUserRegistrationData(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("First name can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = UserValidationException.class)
	public void validateUserRegistrationDataLastNameNullErrorTest() {
		user.setLastName(null);
		
		try {
			userRules.validateUserRegistrationData(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Last name can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = UserValidationException.class)
	public void validateUserRegistrationDataLastNameBlankErrorTest() {
		user.setLastName(" ");
		
		try {
			userRules.validateUserRegistrationData(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Last name can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = DeviceException.class)
	public void validateUserRegistrationDataDeviceExceptionErrorTest() {
		Mockito.when(deviceRules.validateDeviceRegistration(user.getDevices())).thenThrow(new DeviceException("Can't register more than one device at a time"));
		
		try {
			userRules.validateUserRegistrationData(user);
		} catch (DeviceException e) {
			Assert.assertEquals("Can't register more than one device at a time", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = UserValidationException.class)
	public void validateUserRegistrationDataUserEmailAlreadyExistsErrorTest() {
		Mockito.when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		
		try {
			userRules.validateUserRegistrationData(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("User " + user.getEmail() + " already exists", e.getMessage());
			
			throw e;
		}
	}
	
	@Test
	public void validateTest() {
		boolean isValid = userRules.validate(user);

		Assert.assertTrue(isValid);
	}
	
	@Test
	public void validateOptionalTest() {
		boolean isValid = userRules.validate(Optional.of(user));
		
		Assert.assertTrue(isValid);
	}

	@Test(expected = UserNotFoundException.class)
	public void validateUserNotPresentErrorTest() {
		try {
			userRules.validate(Optional.ofNullable(null));
		} catch (UserNotFoundException e) {
			Assert.assertEquals("User not found", e.getMessage());

			throw e;
		}

	}

	@Test(expected = UserValidationException.class)
	public void validateUserNullErrorTest() {
		user = null;

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("User can't be null", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validatePasswordNullErrorTest() {
		user.setPassword(null);

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Password can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validatePasswordBlankErrorTest() {
		user.setPassword(" ");

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Password can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateEmailNullErrorTest() {
		user.setEmail(null);

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Email can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateEmailBlankErrorTest() {
		user.setEmail(" ");

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Email can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateFirstNameNullErrorTest() {
		user.setFirstName(null);

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("First name can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateFirstNameBlankErrorTest() {
		user.setFirstName(" ");

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("First name can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateLastNameNullErrorTest() {
		user.setLastName(null);

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Last name can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateLastNameBlankErrorTest() {
		user.setLastName(" ");

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Last name can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateDevicesNullErrorTest() {
		user.setDevices(null);

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Devices can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateDevicesEmptyErrorTest() {
		user.setDevices(new ArrayList<>());

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Devices can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = DeviceException.class)
	public void validateDeviceExceptionErrorTest() {
		Mockito.when(deviceRules.validate(user.getDevices())).thenThrow(new DeviceException("Device Id can't be null or empty"));

		try {
			userRules.validate(user);
		} catch (DeviceException e) {
			Assert.assertEquals("Device Id can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateIdNullErrorTest() {
		user.setId(null);

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("ID can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateIdBlankErrorTest() {
		user.setId(" ");

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("ID can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateSecretNullErrorTest() {
		user.setSecret(null);

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Secret can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateSecretBlankErrorTest() {
		user.setSecret(" ");

		try {
			userRules.validate(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Secret can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test
	public void validateDeviceRegistrationTrueTest() {
		Mockito.when(deviceRules.validate(user.getDevices())).thenReturn(true);

		boolean isValid = userRules.validateDeviceRegistration(user);

		Assert.assertTrue(isValid);
	}

	@Test
	public void validateDeviceRegistrationFalseTest() {
		Mockito.when(deviceRules.validate(user.getDevices())).thenReturn(false);

		boolean isValid = userRules.validateDeviceRegistration(user);

		Assert.assertFalse(isValid);
	}

	@Test(expected = UserValidationException.class)
	public void validateDeviceRegistrationUserNullErrorTest() {
		user = null;

		try {
			userRules.validateDeviceRegistration(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("User can't be null", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateDeviceRegistrationPasswordNullErrorTest() {
		user.setPassword(null);

		try {
			userRules.validateDeviceRegistration(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Password can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateDeviceRegistrationPasswordBlankErrorTest() {
		user.setPassword(" ");

		try {
			userRules.validateDeviceRegistration(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Password can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateDeviceRegistrationEmailNullErrorTest() {
		user.setEmail(null);

		try {
			userRules.validateDeviceRegistration(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Email can't be null or empty", e.getMessage());

			throw e;
		}
	}

	@Test(expected = UserValidationException.class)
	public void validateDeviceRegistrationEmailBlankErrorTest() {
		user.setEmail(" ");

		try {
			userRules.validateDeviceRegistration(user);
		} catch (UserValidationException e) {
			Assert.assertEquals("Email can't be null or empty", e.getMessage());

			throw e;
		}
	}

}
