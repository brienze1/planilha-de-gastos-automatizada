package br.com.planilha.gastos.parse;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.dto.DeviceDto;
import br.com.planilha.gastos.entity.Device;

@ExtendWith(SpringExtension.class)
public class DeviceDeliveryParseTest {

	@InjectMocks
	private DeviceDeliveryParse deviceDeliveryParse;
	
	private List<DeviceDto> devicesDto;
	private Device device;
	private DeviceDto deviceDto;
	
	@BeforeEach
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
		
		Assertions.assertNotNull(device);
		Assertions.assertNull(device.getId());
		Assertions.assertEquals(deviceDto.getVerificationCode(), device.getVerificationCode());
		Assertions.assertEquals(deviceDto.getDeviceId(), device.getDeviceId());
		Assertions.assertEquals(deviceDto.isInUse(), device.isInUse());
		Assertions.assertEquals(deviceDto.isVerified(), device.isVerified());
	}
	
	@Test
	public void toDeviceNullTest() {
		deviceDto = null;
		
		Device device = deviceDeliveryParse.toDevice(deviceDto);
		
		Assertions.assertNotNull(device);
		Assertions.assertNull(device.getDeviceId());
		Assertions.assertNull(device.getId());
		Assertions.assertNull(device.getVerificationCode());
		Assertions.assertFalse(device.isInUse());
		Assertions.assertFalse(device.isVerified());
	}
	
	@Test
	public void toDevicesFromDeviceTest() {
		List<Device> devices = deviceDeliveryParse.toDevices(deviceDto);
		
		Assertions.assertNotNull(devices);
		Assertions.assertFalse(devices.isEmpty());
		for (Device device : devices) {
			for (DeviceDto deviceDto : devicesDto) {
				if(device.getDeviceId().equals(deviceDto.getDeviceId())) {
					Assertions.assertNull(device.getId());
					Assertions.assertEquals(deviceDto.getVerificationCode(), device.getVerificationCode());
					Assertions.assertEquals(deviceDto.getDeviceId(), device.getDeviceId());
					Assertions.assertEquals(deviceDto.isInUse(), device.isInUse());
					Assertions.assertEquals(deviceDto.isVerified(), device.isVerified());
				}
			}
		}
	}
	
	@Test
	public void toDevicesFromDeviceNullTest() {
		deviceDto = null;
		
		List<Device> devices = deviceDeliveryParse.toDevices(deviceDto);
		
		Assertions.assertNotNull(devices);
		Assertions.assertTrue(devices.isEmpty());
	}
	
}
