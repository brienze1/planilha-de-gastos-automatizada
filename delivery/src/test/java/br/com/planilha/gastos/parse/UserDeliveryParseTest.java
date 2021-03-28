package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.dto.DeviceDto;
import br.com.planilha.gastos.dto.UserDto;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;

@ExtendWith(SpringExtension.class)
public class UserDeliveryParseTest {

	@InjectMocks
	private UserDeliveryParse userDeliveryParse;
	
	@Mock
	private DeviceDeliveryParse deviceParce;
	
	private UserDto userDto;
	private List<Device> devices;
	
	@BeforeEach
	public void init() {
		userDto = new UserDto();
		userDto.setAutoLogin(true);
		userDto.setDevice(new DeviceDto());
		userDto.getDevice().setDeviceId(UUID.randomUUID().toString());
		userDto.getDevice().setInUse(true);
		userDto.getDevice().setVerificationCode(UUID.randomUUID().toString());
		userDto.getDevice().setVerified(true);
		userDto.setEmail(UUID.randomUUID().toString());
		userDto.setFirstName(UUID.randomUUID().toString());
		userDto.setLastName(UUID.randomUUID().toString());
		userDto.setPassword(UUID.randomUUID().toString());
		userDto.setSecret(UUID.randomUUID().toString());
		userDto.setValidEmail(true);
		
		devices = new ArrayList<>();
		devices.add(new Device());
		devices.get(0).setDeviceId(userDto.getDevice().getDeviceId());
		devices.get(0).setInUse(userDto.getDevice().isInUse());
		devices.get(0).setVerificationCode(userDto.getDevice().getVerificationCode());
		devices.get(0).setVerified(userDto.getDevice().isVerified());
	}
	
	@Test
	public void toUserTest() {
		Mockito.when(deviceParce.toDevices(userDto.getDevice())).thenReturn(devices);
		
		User user = userDeliveryParse.toUser(userDto);
		
		Mockito.verify(deviceParce).toDevices(userDto.getDevice());
		
		Assertions.assertNotNull(user);
		Assertions.assertEquals(userDto.getEmail(), user.getEmail());
		Assertions.assertEquals(userDto.getFirstName().substring(0,1).toUpperCase() + userDto.getFirstName().substring(1).toLowerCase(), user.getFirstName());
		Assertions.assertEquals(userDto.getLastName().substring(0,1).toUpperCase() + userDto.getLastName().substring(1).toLowerCase(), user.getLastName());
		Assertions.assertEquals(userDto.getPassword(), user.getPassword());
		Assertions.assertEquals(userDto.getSecret(), user.getSecret());
		Assertions.assertEquals(userDto.isAutoLogin(), user.isAutoLogin());
		Assertions.assertEquals(userDto.isValidEmail(), user.isValidEmail());
		Assertions.assertEquals(userDto.getDevice().getDeviceId(), user.getDevices().get(0).getDeviceId());
		Assertions.assertEquals(userDto.getDevice().getVerificationCode(), user.getDevices().get(0).getVerificationCode());
		Assertions.assertEquals(userDto.getDevice().isInUse(), user.getDevices().get(0).isInUse());
		Assertions.assertEquals(userDto.getDevice().isVerified(), user.getDevices().get(0).isVerified());
	}
	
	@Test
	public void toUserEmailNullTest() {
		userDto.setEmail(null);
		
		Mockito.when(deviceParce.toDevices(userDto.getDevice())).thenReturn(devices);
		
		User user = userDeliveryParse.toUser(userDto);
		
		Mockito.verify(deviceParce).toDevices(userDto.getDevice());
		
		Assertions.assertNotNull(user);
		Assertions.assertNull(user.getEmail());
		Assertions.assertEquals(userDto.getFirstName().substring(0,1).toUpperCase() + userDto.getFirstName().substring(1).toLowerCase(), user.getFirstName());
		Assertions.assertEquals(userDto.getLastName().substring(0,1).toUpperCase() + userDto.getLastName().substring(1).toLowerCase(), user.getLastName());
		Assertions.assertEquals(userDto.getPassword(), user.getPassword());
		Assertions.assertEquals(userDto.getSecret(), user.getSecret());
		Assertions.assertEquals(userDto.isAutoLogin(), user.isAutoLogin());
		Assertions.assertEquals(userDto.isValidEmail(), user.isValidEmail());
		Assertions.assertEquals(userDto.getDevice().getDeviceId(), user.getDevices().get(0).getDeviceId());
		Assertions.assertEquals(userDto.getDevice().getVerificationCode(), user.getDevices().get(0).getVerificationCode());
		Assertions.assertEquals(userDto.getDevice().isInUse(), user.getDevices().get(0).isInUse());
		Assertions.assertEquals(userDto.getDevice().isVerified(), user.getDevices().get(0).isVerified());
	}
	
	@Test
	public void toUserFirstNameNullTest() {
		userDto.setFirstName(null);
		
		Mockito.when(deviceParce.toDevices(userDto.getDevice())).thenReturn(devices);
		
		User user = userDeliveryParse.toUser(userDto);
		
		Mockito.verify(deviceParce).toDevices(userDto.getDevice());
		
		Assertions.assertNotNull(user);
		Assertions.assertNull(user.getFirstName());
		Assertions.assertEquals(userDto.getEmail(), user.getEmail());
		Assertions.assertEquals(userDto.getLastName().substring(0,1).toUpperCase() + userDto.getLastName().substring(1).toLowerCase(), user.getLastName());
		Assertions.assertEquals(userDto.getPassword(), user.getPassword());
		Assertions.assertEquals(userDto.getSecret(), user.getSecret());
		Assertions.assertEquals(userDto.isAutoLogin(), user.isAutoLogin());
		Assertions.assertEquals(userDto.isValidEmail(), user.isValidEmail());
		Assertions.assertEquals(userDto.getDevice().getDeviceId(), user.getDevices().get(0).getDeviceId());
		Assertions.assertEquals(userDto.getDevice().getVerificationCode(), user.getDevices().get(0).getVerificationCode());
		Assertions.assertEquals(userDto.getDevice().isInUse(), user.getDevices().get(0).isInUse());
		Assertions.assertEquals(userDto.getDevice().isVerified(), user.getDevices().get(0).isVerified());
	}
	
	@Test
	public void toUserLastNameNullTest() {
		userDto.setLastName(null);
		
		Mockito.when(deviceParce.toDevices(userDto.getDevice())).thenReturn(devices);
		
		User user = userDeliveryParse.toUser(userDto);
		
		Mockito.verify(deviceParce).toDevices(userDto.getDevice());
		
		Assertions.assertNotNull(user);
		Assertions.assertNull(user.getLastName());
		Assertions.assertEquals(userDto.getEmail(), user.getEmail());
		Assertions.assertEquals(userDto.getFirstName().substring(0,1).toUpperCase() + userDto.getFirstName().substring(1).toLowerCase(), user.getFirstName());
		Assertions.assertEquals(userDto.getPassword(), user.getPassword());
		Assertions.assertEquals(userDto.getSecret(), user.getSecret());
		Assertions.assertEquals(userDto.isAutoLogin(), user.isAutoLogin());
		Assertions.assertEquals(userDto.isValidEmail(), user.isValidEmail());
		Assertions.assertEquals(userDto.getDevice().getDeviceId(), user.getDevices().get(0).getDeviceId());
		Assertions.assertEquals(userDto.getDevice().getVerificationCode(), user.getDevices().get(0).getVerificationCode());
		Assertions.assertEquals(userDto.getDevice().isInUse(), user.getDevices().get(0).isInUse());
		Assertions.assertEquals(userDto.getDevice().isVerified(), user.getDevices().get(0).isVerified());
	}
	
	@Test
	public void toUserDeviceNullTest() {
		userDto.setDevice(null);
		
		Mockito.when(deviceParce.toDevices(userDto.getDevice())).thenReturn(new ArrayList<>());
		
		User user = userDeliveryParse.toUser(userDto);
		
		Mockito.verify(deviceParce).toDevices(userDto.getDevice());
		
		Assertions.assertNotNull(user);
		Assertions.assertTrue(user.getDevices().isEmpty());
		Assertions.assertEquals(userDto.getEmail(), user.getEmail());
		Assertions.assertEquals(userDto.getFirstName().substring(0,1).toUpperCase() + userDto.getFirstName().substring(1).toLowerCase(), user.getFirstName());
		Assertions.assertEquals(userDto.getLastName().substring(0,1).toUpperCase() + userDto.getLastName().substring(1).toLowerCase(), user.getLastName());
		Assertions.assertEquals(userDto.getPassword(), user.getPassword());
		Assertions.assertEquals(userDto.getSecret(), user.getSecret());
		Assertions.assertEquals(userDto.isAutoLogin(), user.isAutoLogin());
		Assertions.assertEquals(userDto.isValidEmail(), user.isValidEmail());
	}	
	
	@Test
	public void toUserUserDtoNullTest() {
		User user = userDeliveryParse.toUser(null);
		
		Assertions.assertNotNull(user);
	}
	
	
}
