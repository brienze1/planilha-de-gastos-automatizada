package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.dto.DeviceDto;
import br.com.planilha.gastos.dto.UserDto;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;

@RunWith(SpringRunner.class)
public class UserDeliveryParseTest {

	@InjectMocks
	private UserDeliveryParse userDeliveryParse;
	
	@Mock
	private DeviceDeliveryParse deviceParce;
	
	private UserDto userDto;
	private List<Device> devices;
	
	@Before
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
		
		Assert.assertNotNull(user);
		Assert.assertEquals(userDto.getEmail(), user.getEmail());
		Assert.assertEquals(userDto.getFirstName().substring(0,1).toUpperCase() + userDto.getFirstName().substring(1).toLowerCase(), user.getFirstName());
		Assert.assertEquals(userDto.getLastName().substring(0,1).toUpperCase() + userDto.getLastName().substring(1).toLowerCase(), user.getLastName());
		Assert.assertEquals(userDto.getPassword(), user.getPassword());
		Assert.assertEquals(userDto.getSecret(), user.getSecret());
		Assert.assertEquals(userDto.isAutoLogin(), user.isAutoLogin());
		Assert.assertEquals(userDto.isValidEmail(), user.isValidEmail());
		Assert.assertEquals(userDto.getDevice().getDeviceId(), user.getDevices().get(0).getDeviceId());
		Assert.assertEquals(userDto.getDevice().getVerificationCode(), user.getDevices().get(0).getVerificationCode());
		Assert.assertEquals(userDto.getDevice().isInUse(), user.getDevices().get(0).isInUse());
		Assert.assertEquals(userDto.getDevice().isVerified(), user.getDevices().get(0).isVerified());
	}
	
	@Test
	public void toUserEmailNullTest() {
		userDto.setEmail(null);
		
		Mockito.when(deviceParce.toDevices(userDto.getDevice())).thenReturn(devices);
		
		User user = userDeliveryParse.toUser(userDto);
		
		Mockito.verify(deviceParce).toDevices(userDto.getDevice());
		
		Assert.assertNotNull(user);
		Assert.assertNull(user.getEmail());
		Assert.assertEquals(userDto.getFirstName().substring(0,1).toUpperCase() + userDto.getFirstName().substring(1).toLowerCase(), user.getFirstName());
		Assert.assertEquals(userDto.getLastName().substring(0,1).toUpperCase() + userDto.getLastName().substring(1).toLowerCase(), user.getLastName());
		Assert.assertEquals(userDto.getPassword(), user.getPassword());
		Assert.assertEquals(userDto.getSecret(), user.getSecret());
		Assert.assertEquals(userDto.isAutoLogin(), user.isAutoLogin());
		Assert.assertEquals(userDto.isValidEmail(), user.isValidEmail());
		Assert.assertEquals(userDto.getDevice().getDeviceId(), user.getDevices().get(0).getDeviceId());
		Assert.assertEquals(userDto.getDevice().getVerificationCode(), user.getDevices().get(0).getVerificationCode());
		Assert.assertEquals(userDto.getDevice().isInUse(), user.getDevices().get(0).isInUse());
		Assert.assertEquals(userDto.getDevice().isVerified(), user.getDevices().get(0).isVerified());
	}
	
	@Test
	public void toUserFirstNameNullTest() {
		userDto.setFirstName(null);
		
		Mockito.when(deviceParce.toDevices(userDto.getDevice())).thenReturn(devices);
		
		User user = userDeliveryParse.toUser(userDto);
		
		Mockito.verify(deviceParce).toDevices(userDto.getDevice());
		
		Assert.assertNotNull(user);
		Assert.assertNull(user.getFirstName());
		Assert.assertEquals(userDto.getEmail(), user.getEmail());
		Assert.assertEquals(userDto.getLastName().substring(0,1).toUpperCase() + userDto.getLastName().substring(1).toLowerCase(), user.getLastName());
		Assert.assertEquals(userDto.getPassword(), user.getPassword());
		Assert.assertEquals(userDto.getSecret(), user.getSecret());
		Assert.assertEquals(userDto.isAutoLogin(), user.isAutoLogin());
		Assert.assertEquals(userDto.isValidEmail(), user.isValidEmail());
		Assert.assertEquals(userDto.getDevice().getDeviceId(), user.getDevices().get(0).getDeviceId());
		Assert.assertEquals(userDto.getDevice().getVerificationCode(), user.getDevices().get(0).getVerificationCode());
		Assert.assertEquals(userDto.getDevice().isInUse(), user.getDevices().get(0).isInUse());
		Assert.assertEquals(userDto.getDevice().isVerified(), user.getDevices().get(0).isVerified());
	}
	
	@Test
	public void toUserLastNameNullTest() {
		userDto.setLastName(null);
		
		Mockito.when(deviceParce.toDevices(userDto.getDevice())).thenReturn(devices);
		
		User user = userDeliveryParse.toUser(userDto);
		
		Mockito.verify(deviceParce).toDevices(userDto.getDevice());
		
		Assert.assertNotNull(user);
		Assert.assertNull(user.getLastName());
		Assert.assertEquals(userDto.getEmail(), user.getEmail());
		Assert.assertEquals(userDto.getFirstName().substring(0,1).toUpperCase() + userDto.getFirstName().substring(1).toLowerCase(), user.getFirstName());
		Assert.assertEquals(userDto.getPassword(), user.getPassword());
		Assert.assertEquals(userDto.getSecret(), user.getSecret());
		Assert.assertEquals(userDto.isAutoLogin(), user.isAutoLogin());
		Assert.assertEquals(userDto.isValidEmail(), user.isValidEmail());
		Assert.assertEquals(userDto.getDevice().getDeviceId(), user.getDevices().get(0).getDeviceId());
		Assert.assertEquals(userDto.getDevice().getVerificationCode(), user.getDevices().get(0).getVerificationCode());
		Assert.assertEquals(userDto.getDevice().isInUse(), user.getDevices().get(0).isInUse());
		Assert.assertEquals(userDto.getDevice().isVerified(), user.getDevices().get(0).isVerified());
	}
	
	@Test
	public void toUserDeviceNullTest() {
		userDto.setDevice(null);
		
		Mockito.when(deviceParce.toDevices(userDto.getDevice())).thenReturn(new ArrayList<>());
		
		User user = userDeliveryParse.toUser(userDto);
		
		Mockito.verify(deviceParce).toDevices(userDto.getDevice());
		
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getDevices().isEmpty());
		Assert.assertEquals(userDto.getEmail(), user.getEmail());
		Assert.assertEquals(userDto.getFirstName().substring(0,1).toUpperCase() + userDto.getFirstName().substring(1).toLowerCase(), user.getFirstName());
		Assert.assertEquals(userDto.getLastName().substring(0,1).toUpperCase() + userDto.getLastName().substring(1).toLowerCase(), user.getLastName());
		Assert.assertEquals(userDto.getPassword(), user.getPassword());
		Assert.assertEquals(userDto.getSecret(), user.getSecret());
		Assert.assertEquals(userDto.isAutoLogin(), user.isAutoLogin());
		Assert.assertEquals(userDto.isValidEmail(), user.isValidEmail());
	}	
	
	@Test
	public void toUserUserDtoNullTest() {
		User user = userDeliveryParse.toUser(null);
		
		Assert.assertNotNull(user);
	}
	
	
}
