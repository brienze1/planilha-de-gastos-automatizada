package br.com.planilha.gastos.rules;

import java.util.ArrayList;
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
import br.com.planilha.gastos.entity.Login;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.AutoLoginException;
import br.com.planilha.gastos.exception.DeviceNotVerifiedException;
import br.com.planilha.gastos.exception.LoginException;
import br.com.planilha.gastos.port.PasswordUtilsAdapter;
import br.com.planilha.gastos.service.DeviceService;

@ExtendWith(SpringExtension.class)
public class LoginRulesTest {

	@InjectMocks
	private LoginRules loginRules;

	@Mock
	private PasswordUtilsAdapter passwordUtils;

	@Mock
	private DeviceService deviceService;

	private Login login;
	private User user;
	private String deviceId;
	private String password;
	
	@BeforeEach
	public void init() {
		deviceId = UUID.randomUUID().toString();
		password = UUID.randomUUID().toString();
		
		login = new Login();
		login.setDeviceId(deviceId);
		login.setEmail(UUID.randomUUID().toString());
		login.setPassword(password);
	
		user = new User();
		user.setAutoLogin(true);
		user.setDevices(new ArrayList<>());
		user.getDevices().add(new Device(UUID.randomUUID().toString()));
		user.getDevices().add(new Device(deviceId));
		user.getDevices().get(1).setVerified(true);
		user.getDevices().add(new Device(UUID.randomUUID().toString()));
		user.setEmail(UUID.randomUUID().toString());
		user.setFirstName(UUID.randomUUID().toString());
		user.setId(UUID.randomUUID().toString());
		user.setInUseDevice(deviceId);
		user.setLastName(UUID.randomUUID().toString());
		user.setPassword(password);
		user.setSecret(UUID.randomUUID().toString());
		user.setValidEmail(true);
	}

	@Test
	public void validateTest() {
		Mockito.when(passwordUtils.verifyPassword(login.getPassword(), user.getPassword(), user.getSecret())).thenReturn(true);
		
		boolean isValid = loginRules.validate(login, user);
		
		Assertions.assertTrue(isValid);
	}
	
	@Test
	public void validateNewDeviceTest() {
		Mockito.when(passwordUtils.verifyPassword(login.getPassword(), user.getPassword(), user.getSecret())).thenReturn(true);
		Mockito.when(deviceService.registerNewDevice(user.getId(), login.getDeviceId())).thenReturn(new Device(login.getDeviceId()));
		
		user.getDevices().get(1).setDeviceId(UUID.randomUUID().toString());
		
		boolean isValid = loginRules.validate(login, user);

		Mockito.verify(deviceService).registerNewDevice(user.getId(), login.getDeviceId());
		Assertions.assertTrue(isValid);
	}
	
	@Test
	public void validateLoginNullTest() {
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validate(null, user), 
				"Login can't be null");
	}
	
	@Test
	public void validateUserNullTest() {
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validate(login, null), 
				"User can't be null");
	}
	
	@Test
	public void validateEmailNullTest() {
		login.setEmail(null);
		
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validate(login, user), 
				"Email can't be null or blank");
	}
	
	@Test
	public void validateEmailBlankTest() {
		login.setEmail(" ");
		
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validate(login, user), 
				"Email can't be null or blank");
	}
	
	@Test
	public void validatePasswordNullTest() {
		login.setPassword(null);
		
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validate(login, user), 
				"Password can't be null or blank");
	}
	
	@Test
	public void validatePasswordBlankTest() {
		login.setPassword(" ");
		
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validate(login, user), 
				"Password can't be null or blank");
	}
	
	@Test
	public void validatePasswordWrongTest() {
		login.setPassword(UUID.randomUUID().toString());
		
		Mockito.when(passwordUtils.verifyPassword(login.getPassword(), user.getPassword(), user.getSecret())).thenReturn(false);
		
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validate(login, user), 
				"Password does not match");
	}
	
	@Test
	public void validateDeviceIdNullTest() {
		login.setDeviceId(null);
		
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validate(login, user), 
				"DeviceId can't be null or blank");
	}
	
	@Test
	public void validateDeviceIdBlankTest() {
		login.setDeviceId(" ");
		
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validate(login, user), 
				"DeviceId can't be null or blank");
	}
	
	@Test
	public void validateAutoLoginTest() {
		boolean isValid = loginRules.validateAutoLogin(login, user);
		
		Assertions.assertTrue(isValid);
	}
	
	@Test
	public void validateAutoLoginLoginNullTest() {
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validateAutoLogin(null, user), 
				"Login can't be null");
	}
	
	@Test
	public void validateAutoLoginUserNullTest() {
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validateAutoLogin(login, null), 
				"User can't be null");
	}
	
	@Test
	public void validateAutoLoginEmailNullTest() {
		login.setEmail(null);
		
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validateAutoLogin(login, user), 
				"Email can't be null or blank");
	}
	
	@Test
	public void validateAutoLoginEmailBlankTest() {
		login.setEmail(" ");
		
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validateAutoLogin(login, user), 
				"Email can't be null or blank");
	}
	
	@Test
	public void validateAutoLoginDeviceIdNullTest() {
		login.setDeviceId(null);

		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validateAutoLogin(login, user), 
				"DeviceId can't be null or blank");
	}
	
	@Test
	public void validateAutoLoginDeviceIdBlankTest() {
		login.setDeviceId(" ");
		
		Assertions.assertThrows(
				LoginException.class, 
				() -> loginRules.validateAutoLogin(login, user), 
				"DeviceId can't be null or blank");
	}
	
	@Test
	public void validateAutoLoginNotAllowedTest() {
		user.setAutoLogin(false);

		Assertions.assertThrows(
				AutoLoginException.class, 
				() -> loginRules.validateAutoLogin(login, user), 
				"User configuration doesn't allow auto-login");
	}
	
	@Test
	public void validateAutoLoginDeviceNotVerifiedTest() {
		user.getDevices().get(1).setVerified(false);
		
		Assertions.assertThrows(
				DeviceNotVerifiedException.class, 
				() -> loginRules.validateAutoLogin(login, user), 
				"Device not verified");

		Mockito.verify(deviceService).sendDeviceVerificationEmail(user.getId(), user.getDevices().get(1));
	}
	
	@Test
	public void validateAutoLoginDeviceNewDeviceTest() {
		Device newDevice = new Device(login.getDeviceId());
		
		Mockito.when(deviceService.registerNewDevice(user.getId(), login.getDeviceId())).thenReturn(newDevice);
		
		user.getDevices().get(1).setDeviceId(UUID.randomUUID().toString());
		
		Assertions.assertThrows(
				DeviceNotVerifiedException.class, 
				() -> loginRules.validateAutoLogin(login, user), 
				"Unknown device");
	}

}
