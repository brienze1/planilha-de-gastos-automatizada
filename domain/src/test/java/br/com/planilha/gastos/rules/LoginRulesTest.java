package br.com.planilha.gastos.rules;

import java.util.ArrayList;
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
import br.com.planilha.gastos.entity.Login;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.DeviceNotVerifiedException;
import br.com.planilha.gastos.exception.LoginException;
import br.com.planilha.gastos.port.PasswordUtilsAdapter;
import br.com.planilha.gastos.service.DeviceService;

@RunWith(SpringRunner.class)
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
	
	@Before
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
		
		Assert.assertTrue(isValid);
	}
	
	@Test
	public void validateNewDeviceTest() {
		Mockito.when(passwordUtils.verifyPassword(login.getPassword(), user.getPassword(), user.getSecret())).thenReturn(true);
		Mockito.when(deviceService.registerNewDevice(user.getId(), login.getDeviceId())).thenReturn(new Device(login.getDeviceId()));
		
		user.getDevices().get(1).setDeviceId(UUID.randomUUID().toString());
		
		boolean isValid = loginRules.validate(login, user);

		Mockito.verify(deviceService).registerNewDevice(user.getId(), login.getDeviceId());
		Assert.assertTrue(isValid);
	}
	
	@Test(expected = LoginException.class)
	public void validateLoginNullTest() {
		try {
			loginRules.validate(null, user);
		} catch (LoginException e) {
			Assert.assertEquals("Login can't be null", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateUserNullTest() {
		try {
			loginRules.validate(login, null);
		} catch (LoginException e) {
			Assert.assertEquals("User can't be null", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateEmailNullTest() {
		login.setEmail(null);
		
		try {
			loginRules.validate(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("Email can't be null or blank", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateEmailBlankTest() {
		login.setEmail(" ");
		
		try {
			loginRules.validate(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("Email can't be null or blank", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validatePasswordNullTest() {
		login.setPassword(null);
		
		try {
			loginRules.validate(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("Password can't be null or blank", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validatePasswordBlankTest() {
		login.setPassword(" ");
		
		try {
			loginRules.validate(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("Password can't be null or blank", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validatePasswordWrongTest() {
		login.setPassword(UUID.randomUUID().toString());
		
		Mockito.when(passwordUtils.verifyPassword(login.getPassword(), user.getPassword(), user.getSecret())).thenReturn(false);
		
		try {
			loginRules.validate(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("Password does not match", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateDeviceIdNullTest() {
		login.setDeviceId(null);
		
		try {
			loginRules.validate(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("DeviceId can't be null or blank", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateDeviceIdBlankTest() {
		login.setDeviceId(" ");
		
		try {
			loginRules.validate(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("DeviceId can't be null or blank", e.getMessage());
			
			throw e;
		}
	}
	
	@Test
	public void validateAutoLoginTest() {
		boolean isValid = loginRules.validateAutoLogin(login, user);
		
		Assert.assertTrue(isValid);
	}
	
	@Test(expected = LoginException.class)
	public void validateAutoLoginLoginNullTest() {
		try {
			loginRules.validateAutoLogin(null, user);
		} catch (LoginException e) {
			Assert.assertEquals("Login can't be null", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateAutoLoginUserNullTest() {
		try {
			loginRules.validateAutoLogin(login, null);
		} catch (LoginException e) {
			Assert.assertEquals("User can't be null", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateAutoLoginEmailNullTest() {
		login.setEmail(null);
		
		try {
			loginRules.validateAutoLogin(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("Email can't be null or blank", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateAutoLoginEmailBlankTest() {
		login.setEmail(" ");
		
		try {
			loginRules.validateAutoLogin(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("Email can't be null or blank", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateAutoLoginDeviceIdNullTest() {
		login.setDeviceId(null);
		
		try {
			loginRules.validateAutoLogin(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("DeviceId can't be null or blank", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateAutoLoginDeviceIdBlankTest() {
		login.setDeviceId(" ");
		
		try {
			loginRules.validateAutoLogin(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("DeviceId can't be null or blank", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = LoginException.class)
	public void validateAutoLoginNotAllowedTest() {
		user.setAutoLogin(false);
		
		try {
			loginRules.validateAutoLogin(login, user);
		} catch (LoginException e) {
			Assert.assertEquals("User configuration doesn't allow auto-login", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = DeviceNotVerifiedException.class)
	public void validateAutoLoginDeviceNotVerifiedTest() {
		user.getDevices().get(1).setVerified(false);
		
		try {
			loginRules.validateAutoLogin(login, user);
		} catch (DeviceNotVerifiedException e) {
			Mockito.verify(deviceService).sendDeviceVerificationEmail(user.getId(), user.getDevices().get(1));
			
			Assert.assertEquals("Device not verified", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = DeviceNotVerifiedException.class)
	public void validateAutoLoginDeviceNewDeviceTest() {
		Device newDevice = new Device(login.getDeviceId());
		
		Mockito.when(deviceService.registerNewDevice(user.getId(), login.getDeviceId())).thenReturn(newDevice);
		
		user.getDevices().get(1).setDeviceId(UUID.randomUUID().toString());
		
		try {
			loginRules.validateAutoLogin(login, user);
		} catch (DeviceNotVerifiedException e) {
			Assert.assertEquals("Device not verified", e.getMessage());
			
			throw e;
		}
	}

}
