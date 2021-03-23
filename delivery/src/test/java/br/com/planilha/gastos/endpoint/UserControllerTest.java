package br.com.planilha.gastos.endpoint;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.dto.DataDto;
import br.com.planilha.gastos.dto.DeviceDto;
import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.dto.UserDto;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.Login;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.parse.DeviceDeliveryParse;
import br.com.planilha.gastos.parse.LoginDeliveryParse;
import br.com.planilha.gastos.parse.UserDeliveryParse;
import br.com.planilha.gastos.service.DeviceService;
import br.com.planilha.gastos.service.UserService;

@RunWith(SpringRunner.class)
public class UserControllerTest {

	@InjectMocks
	private UserController userController;
	
	@Mock
	private LoginDeliveryParse loginParse;
	
	@Mock
	private UserService userService;
	
	@Mock
	private DeviceService deviceService;

	@Mock
	private UserDeliveryParse userParse;
	
	@Mock
	private DeviceDeliveryParse deviceParse;
	
	private User user;
	private UserDto userDto;
	private String token;
	private Login login;
	private LoginDto loginDto;
	private Device device;
	private DeviceDto deviceDto;
	
	@Before
	public void init() {
		user = new User();
		user.setAutoLogin(false);
		user.setDevices(new ArrayList<>());
		user.setEmail(UUID.randomUUID().toString());
		user.setFirstName(UUID.randomUUID().toString());
		user.setId(UUID.randomUUID().toString());
		user.setLastName(UUID.randomUUID().toString());
		user.setPassword(UUID.randomUUID().toString());
		user.setSecret(UUID.randomUUID().toString());
		user.setValidEmail(true);
		
		userDto = new UserDto();
		userDto.setAutoLogin(false);
		userDto.setDevice(new DeviceDto());
		userDto.setEmail(UUID.randomUUID().toString());
		userDto.setFirstName(UUID.randomUUID().toString());
		userDto.setLastName(UUID.randomUUID().toString());
		userDto.setPassword(UUID.randomUUID().toString());
		userDto.setSecret(UUID.randomUUID().toString());
		userDto.setValidEmail(true);
		
		token = UUID.randomUUID().toString();
		
		login = new Login();
		login.setDeviceId(UUID.randomUUID().toString());
		login.setEmail(UUID.randomUUID().toString());
		login.setPassword(UUID.randomUUID().toString());
		
		loginDto = new LoginDto();
		loginDto.setDeviceId(UUID.randomUUID().toString());
		loginDto.setEmail(UUID.randomUUID().toString());
		loginDto.setPassword(UUID.randomUUID().toString());
		
		device = new Device();
		device.setDeviceId(UUID.randomUUID().toString());
		device.setId(UUID.randomUUID().toString());
		device.setInUse(true);
		device.setVerificationCode(UUID.randomUUID().toString());
		device.setVerified(false);
		
		deviceDto = new DeviceDto();
		deviceDto.setDeviceId(UUID.randomUUID().toString());
		deviceDto.setInUse(true);
		deviceDto.setVerificationCode(UUID.randomUUID().toString());
		deviceDto.setVerified(false);
	}
	
	@Test
	public void registerTest() {
		Mockito.when(userParse.toUser(userDto)).thenReturn(user);
		Mockito.when(userService.register(user)).thenReturn(token);
		
		DataDto response = userController.register(userDto);
		
		Mockito.verify(userParse).toUser(userDto);
		Mockito.verify(userService).register(user);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getJwtDataToken());
		Assert.assertFalse(response.getJwtDataToken().isBlank());
	}
	
	@Test
	public void loginTest() {
		Mockito.when(loginParse.toLogin(loginDto)).thenReturn(login);
		Mockito.when(userService.login(login)).thenReturn(token);
		
		DataDto response = userController.login(loginDto);
		
		Mockito.verify(loginParse).toLogin(loginDto);
		Mockito.verify(userService).login(login);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getJwtAcessToken());
		Assert.assertFalse(response.getJwtAcessToken().isBlank());
	}
	
	@Test
	public void autoLoginTest() {
		Mockito.when(loginParse.toLogin(loginDto)).thenReturn(login);
		Mockito.when(userService.autoLogin(login)).thenReturn(token);
		
		DataDto response = userController.autoLogin(loginDto);
		
		Mockito.verify(loginParse).toLogin(loginDto);
		Mockito.verify(userService).autoLogin(login);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getJwtAcessToken());
		Assert.assertFalse(response.getJwtAcessToken().isBlank());
	}
	
	@Test
	public void configureUserTest() {
		Mockito.when(userParse.toUser(userDto)).thenReturn(user);
		
		ResponseEntity<Void> response = userController.configureUser(token, userDto);
		
		Mockito.verify(userParse).toUser(userDto);
		Mockito.verify(userService).configureUser(token, user);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void validateDeviceTest() {
		Mockito.when(deviceParse.toDevice(deviceDto)).thenReturn(device);
		
		ResponseEntity<Void> response = userController.validateDevice(token, deviceDto);
		
		Mockito.verify(deviceParse).toDevice(deviceDto);
		Mockito.verify(deviceService).validateDevice(token, device);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
//	@Test
//	public void registerDeviceTest() {
//		Mockito.when(userParse.toUser(userDto)).thenReturn(user);
//		Mockito.when(userService.registerDevice(user)).thenReturn(token);
//		
//		DataDto response = userController.registerDevice(userDto);
//		
//		Mockito.verify(userParse).toUser(userDto);
//		Mockito.verify(userService).registerDevice(user);
//		
//		Assert.assertNotNull(response);
//		Assert.assertNotNull(response.getJwtDataToken());
//		Assert.assertFalse(response.getJwtDataToken().isBlank());
//	}
	
}
