package br.com.planilha.gastos.parse;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.dto.DeviceDto;
import br.com.planilha.gastos.entity.Device;

@RunWith(SpringRunner.class)
public class DeviceDeliveryParseTest {

	@InjectMocks
	private DeviceDeliveryParse deviceDeliveryParse;
	
	private List<DeviceDto> devicesDto;
	private Device device;
	private DeviceDto deviceDto;
	
	@Before
	public void init() {
		devicesDto = new ArrayList<>();
		
		for(int i=0; i<10; i++) {
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
			
			devicesDto.add(deviceDto);
		}
		
	}
	
	@Test
	public void toDeviceTest() {
		Device device = deviceDeliveryParse.toDevice(deviceDto);
		
		Assert.assertNotNull(device);
		Assert.assertNull(device.getId());
		Assert.assertEquals(deviceDto.getVerificationCode(), device.getVerificationCode());
		Assert.assertEquals(deviceDto.getDeviceId(), device.getDeviceId());
		Assert.assertEquals(deviceDto.isInUse(), device.isInUse());
		Assert.assertEquals(deviceDto.isVerified(), device.isVerified());
	}
	
	@Test
	public void toDeviceNullTest() {
		deviceDto = null;
		
		Device device = deviceDeliveryParse.toDevice(deviceDto);
		
		Assert.assertNotNull(device);
		Assert.assertNull(device.getDeviceId());
		Assert.assertNull(device.getId());
		Assert.assertNull(device.getVerificationCode());
		Assert.assertFalse(device.isInUse());
		Assert.assertFalse(device.isVerified());
	}
	
	@Test
	public void toDevicesFromDeviceTest() {
		List<Device> devices = deviceDeliveryParse.toDevices(deviceDto);
		
		Assert.assertNotNull(devices);
		Assert.assertFalse(devices.isEmpty());
		for (Device device : devices) {
			for (DeviceDto deviceDto : devicesDto) {
				if(device.getDeviceId().equals(deviceDto.getDeviceId())) {
					Assert.assertNull(device.getId());
					Assert.assertEquals(deviceDto.getVerificationCode(), device.getVerificationCode());
					Assert.assertEquals(deviceDto.getDeviceId(), device.getDeviceId());
					Assert.assertEquals(deviceDto.isInUse(), device.isInUse());
					Assert.assertEquals(deviceDto.isVerified(), device.isVerified());
				}
			}
		}
	}
	
	@Test
	public void toDevicesFromDeviceNullTest() {
		deviceDto = null;
		
		List<Device> devices = deviceDeliveryParse.toDevices(deviceDto);
		
		Assert.assertNotNull(devices);
		Assert.assertTrue(devices.isEmpty());
	}
	
}
