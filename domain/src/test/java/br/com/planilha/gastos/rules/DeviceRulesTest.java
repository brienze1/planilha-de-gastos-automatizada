package br.com.planilha.gastos.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.exception.DeviceException;

@ExtendWith(SpringExtension.class)
public class DeviceRulesTest {

	@InjectMocks
	private DeviceRules deviceRules;
	
	private List<Device> devices;
	
	@BeforeEach
	public void init() {
		devices = new ArrayList<>();

		for(int i=0; i<10; i++) {
			Device device = new Device();
			device.setDeviceId(UUID.randomUUID().toString());
			device.setId(UUID.randomUUID().toString());
			device.setInUse(false);
			device.setVerificationCode(UUID.randomUUID().toString());
			device.setVerified(true);
			
			devices.add(device);
		}
		
	}
	
	@Test
	public void validateTest() {
		boolean isValid = deviceRules.validate(devices);
		
		Assertions.assertTrue(isValid);
	}
	
	@Test
	public void validateDevicesNullTest() {
		Assertions.assertThrows(
				DeviceException.class, 
				() -> deviceRules.validate(null), 
				"List of devices can't be null or empty");
	}
	
	@Test
	public void validateDevicesEmptyTest() {
		Assertions.assertThrows(
				DeviceException.class, 
				() -> deviceRules.validate(new ArrayList<>()), 
				"List of devices can't be null or empty");
	}
	
	@Test
	public void validateDeviceNullTest() {
		Device device = null;
		devices.set(0, device);
		
		Assertions.assertThrows(
				DeviceException.class, 
				() -> deviceRules.validate(devices), 
				"Device can't be null");
	}
	
	@Test
	public void validateDeviceIdNullTest() {
		devices.get(0).setDeviceId(null);
		
		Assertions.assertThrows(
				DeviceException.class, 
				() -> deviceRules.validate(devices), 
				"Device Id can't be null or empty");
	}
	
	@Test
	public void validateDeviceIdBlankTest() {
		devices.get(0).setDeviceId("");
		
		Assertions.assertThrows(
				DeviceException.class, 
				() -> deviceRules.validate(devices), 
				"Device Id can't be null or empty");
	}
	
	@Test
	public void validateDeviceRegistrationTest() {
		List<Device> validDevice = new ArrayList<>();
		validDevice.add(devices.get(0));
		
		boolean isValid = deviceRules.validateDeviceRegistration(validDevice);
		
		Assertions.assertTrue(isValid);
	}
	
	@Test
	public void validateDeviceRegistrationSizeErrorTest() {
		Assertions.assertThrows(
				DeviceException.class, 
				() -> deviceRules.validateDeviceRegistration(devices), 
				"Can't register more than one device at a time");
	}
	
}
