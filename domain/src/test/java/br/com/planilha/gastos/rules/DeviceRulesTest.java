package br.com.planilha.gastos.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.exception.DeviceException;

@RunWith(SpringRunner.class)
public class DeviceRulesTest {

	@InjectMocks
	private DeviceRules deviceRules;
	
	private List<Device> devices;
	
	@Before
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
		
		Assert.assertTrue(isValid);
	}
	
	@Test(expected = DeviceException.class)
	public void validateDevicesNullTest() {
		try {
			deviceRules.validate(null);
		} catch (DeviceException e) {
			Assert.assertEquals("List of devices can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = DeviceException.class)
	public void validateDevicesEmptyTest() {
		try {
			deviceRules.validate(new ArrayList<>());
		} catch (DeviceException e) {
			Assert.assertEquals("List of devices can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = DeviceException.class)
	public void validateDeviceNullTest() {
		Device device = null;
		devices.set(0, device);
		
		try {
			deviceRules.validate(devices);
		} catch (DeviceException e) {
			Assert.assertEquals("Device can't be null", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = DeviceException.class)
	public void validateDeviceIdNullTest() {
		devices.get(0).setDeviceId(null);
		
		try {
			deviceRules.validate(devices);
		} catch (DeviceException e) {
			Assert.assertEquals("Device Id can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = DeviceException.class)
	public void validateDeviceIdBlankTest() {
		devices.get(0).setDeviceId("");
		
		try {
			deviceRules.validate(devices);
		} catch (DeviceException e) {
			Assert.assertEquals("Device Id can't be null or empty", e.getMessage());
			
			throw e;
		}
	}
	
	@Test
	public void validateDeviceRegistrationTest() {
		List<Device> validDevice = new ArrayList<>();
		validDevice.add(devices.get(0));
		
		boolean isValid = deviceRules.validateDeviceRegistration(validDevice);
		
		Assert.assertTrue(isValid);
	}
	
	@Test(expected = DeviceException.class)
	public void validateDeviceRegistrationSizeErrorTest() {
		try {
			deviceRules.validateDeviceRegistration(devices);
		} catch (DeviceException e) {
			Assert.assertEquals("Can't register more than one device at a time", e.getMessage());
			
			throw e;
		}
	}
	
}
