package br.com.planilha.gastos.service;

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

import br.com.planilha.gastos.builder.DeviceBuilder;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.DeviceException;

@ExtendWith(SpringExtension.class)
public class DeviceServiceTest {

	@InjectMocks
	private DeviceService deviceService;
	
	@Mock
	private UserService userService;

	@Mock
	private JwtService jwtService;
	
	@Mock
	private DeviceBuilder deviceBuilder;
	
	@Mock
	private EmailService emailService;
	
	private String userId;
	private String deviceId;
	private User user;
	private Device device;
	private String token;
	
	@BeforeEach
	public void init() {
		userId = UUID.randomUUID().toString();

		deviceId = UUID.randomUUID().toString();
		
		device = new Device(deviceId);
		device.setVerified(false);
		device.setInUse(false);
		device.setVerificationCode(UUID.randomUUID().toString()); 

		user = new User();
		user.setId(userId);
		user.setDevices(new ArrayList<>());
		user.getDevices().add(new Device(UUID.randomUUID().toString()));
		user.getDevices().add(device);
		user.getDevices().add(new Device(UUID.randomUUID().toString()));
		
		token = UUID.randomUUID().toString();
	}
	
	@Test
	public void registerNewDeviceTest() {
		Mockito.when(userService.findById(userId)).thenReturn(user);
		Mockito.when(deviceBuilder.build(deviceId)).thenReturn(device);
		
		Device returnedDevice = deviceService.registerNewDevice(userId, deviceId);
		
		Mockito.verify(userService, Mockito.times(2)).findById(userId);
		Mockito.verify(deviceBuilder).build(deviceId);
		Mockito.verify(userService).update(user);
		Mockito.verify(emailService).sendDeviceVerificationEmail(user, device);
		
		Assertions.assertNotNull(returnedDevice);
		Assertions.assertEquals(device.getDeviceId(), returnedDevice.getDeviceId());
		Assertions.assertEquals(device.getId(), returnedDevice.getId());
		Assertions.assertEquals(device.getVerificationCode(), returnedDevice.getVerificationCode());
		Assertions.assertEquals(device.isInUse(), returnedDevice.isInUse());
		Assertions.assertEquals(device.isVerified(), returnedDevice.isVerified());
	}
	
	@Test
	public void sendDeviceVerificationEmailTest() {
		Mockito.when(userService.findById(userId)).thenReturn(user);
		
		Device returnedDevice = deviceService.sendDeviceVerificationEmail(userId, device);
		
		Mockito.verify(userService).findById(userId);
		Mockito.verify(emailService).sendDeviceVerificationEmail(user, device);
		
		Assertions.assertNotNull(returnedDevice);
		Assertions.assertEquals(device.getDeviceId(), returnedDevice.getDeviceId());
		Assertions.assertEquals(device.getId(), returnedDevice.getId());
		Assertions.assertEquals(device.getVerificationCode(), returnedDevice.getVerificationCode());
		Assertions.assertEquals(device.isInUse(), returnedDevice.isInUse());
		Assertions.assertEquals(device.isVerified(), returnedDevice.isVerified());
	}
	
	@Test
	public void sendDeviceVerificationEmailDeviceAlreadyVerifiedTest() {
		Mockito.when(userService.findById(userId)).thenReturn(user);
		
		device.setVerified(true);
		
		Device returnedDevice = deviceService.sendDeviceVerificationEmail(userId, device);
		
		Mockito.verify(userService).findById(userId);
		
		Assertions.assertNotNull(returnedDevice);
		Assertions.assertEquals(device.getDeviceId(), returnedDevice.getDeviceId());
		Assertions.assertEquals(device.getId(), returnedDevice.getId());
		Assertions.assertEquals(device.getVerificationCode(), returnedDevice.getVerificationCode());
		Assertions.assertEquals(device.isInUse(), returnedDevice.isInUse());
		Assertions.assertEquals(device.isVerified(), returnedDevice.isVerified());
	}
	
	@Test
	public void sendDeviceVerificationEmailDeviceNotRegisteredErrorTest() {
		user.getDevices().remove(device);
		
		Mockito.when(userService.findById(userId)).thenReturn(user);
		
		Assertions.assertThrows(
				DeviceException.class, 
				() -> deviceService.sendDeviceVerificationEmail(userId, device), 
				"Unkown device");

		Mockito.verify(userService).findById(userId);
	}
	
	@Test
	public void validateDeviceTest() {
		Mockito.when(jwtService.verifyAcessToken(token)).thenReturn(user);
		
		device.setVerified(false);
		
		Device returnedDevice = deviceService.validateDevice(token, device);
		
		Mockito.verify(jwtService).verifyAcessToken(token);
		Mockito.verify(userService).update(user);
		
		Assertions.assertNotNull(returnedDevice);
		Assertions.assertTrue(returnedDevice.isVerified());
		Assertions.assertEquals(device.getDeviceId(), returnedDevice.getDeviceId());
		Assertions.assertEquals(device.getId(), returnedDevice.getId());
		Assertions.assertEquals(device.getVerificationCode(), returnedDevice.getVerificationCode());
		Assertions.assertEquals(device.isInUse(), returnedDevice.isInUse());
	}
	
	@Test
	public void validateDeviceDeviceNotFoundErrorTest() {
		user.getDevices().remove(device);

		Mockito.when(jwtService.verifyAcessToken(token)).thenReturn(user);
		
		Assertions.assertThrows(
				DeviceException.class, 
				() -> deviceService.validateDevice(token, device), 
				"Unkown device");
		
		Mockito.verify(jwtService).verifyAcessToken(token);
	}
	
}
