package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.DeviceEntity;

@ExtendWith(SpringExtension.class)
public class DeviceIntegrationParseTest {

	@InjectMocks
	private DeviceIntegrationParse deviceIntegrationParse;
	
	private List<Device> devices;
	private Set<DeviceEntity> devicesEntity;
	
	@BeforeEach
	public void init() {
		devices = new ArrayList<>();
		for(int i=0; i<5; i++) {
			Device newDevice = new Device();
			newDevice.setDeviceId(UUID.randomUUID().toString());
			newDevice.setId(String.valueOf(new Random().nextInt(100)));
			newDevice.setInUse(true);
			newDevice.setVerificationCode(UUID.randomUUID().toString());
			newDevice.setVerified(true);
			
			devices.add(newDevice);
		}
		
		devicesEntity = new HashSet<>();
		for(int i=0; i<5; i++) {
			DeviceEntity newDeviceEntity = new DeviceEntity();
			newDeviceEntity.setDeviceId(UUID.randomUUID().toString());
			newDeviceEntity.setId(new Random().nextInt(100));
			newDeviceEntity.setInUse(true);
			newDeviceEntity.setVerificationCode(UUID.randomUUID().toString());
			newDeviceEntity.setVerified(true);
			
			devicesEntity.add(newDeviceEntity);
		}
	}
	
	private void assertAll(Device device, DeviceEntity deviceEntity) {
		Assertions.assertNotNull(deviceEntity);
		Assertions.assertNotNull(device);
		Assertions.assertEquals(device.getDeviceId(), deviceEntity.getDeviceId());
		Assertions.assertEquals(device.getId(), String.valueOf(deviceEntity.getId()));
		Assertions.assertEquals(device.getVerificationCode(), deviceEntity.getVerificationCode());
		Assertions.assertEquals(device.isInUse(), deviceEntity.isInUse());
		Assertions.assertEquals(device.isVerified(), deviceEntity.isVerified());
	}
	
	@Test
	public void toDevicesEntityTest() {
		Set<DeviceEntity> devicesEntity = deviceIntegrationParse.toDevicesEntity(devices);
		
		int i = 0;
		for (DeviceEntity deviceEntity : devicesEntity) {
			for (Device device : devices) {
				if(deviceEntity.getDeviceId().equals(device.getDeviceId())) {
					assertAll(device, deviceEntity);
					i++;
				}
			}
		}
		Assertions.assertEquals(devicesEntity.size(), i);
	}
	
	@Test
	public void toDevicesEntityNullTest() {
		Set<DeviceEntity> devicesEntity = deviceIntegrationParse.toDevicesEntity(null);
		
		Assertions.assertNotNull(devicesEntity);
		Assertions.assertTrue(devicesEntity.isEmpty());
	}
	
	@Test
	public void toDevicesEntityNullEntityTest() {
		devices.set(0, null);
		
		Set<DeviceEntity> devicesEntity = deviceIntegrationParse.toDevicesEntity(devices);
		
		Assertions.assertNotNull(devicesEntity);
		Assertions.assertFalse(devicesEntity.isEmpty());
		Assertions.assertEquals(devices.size(), devicesEntity.size());
	}
	
	@Test
	public void toDevicesEntityIdStringTest() {
		devices.get(0).setId(UUID.randomUUID().toString());
		
		Set<DeviceEntity> devicesEntity = deviceIntegrationParse.toDevicesEntity(devices);
		
		Assertions.assertNotNull(devicesEntity);
		Assertions.assertFalse(devicesEntity.isEmpty());
		Assertions.assertEquals(devices.size(), devicesEntity.size());
	}
	
	@Test
	public void toDevicesTest() {
		List<Device> devices = deviceIntegrationParse.toDevices(devicesEntity);
		
		int i = 0;
		for (DeviceEntity deviceEntity : devicesEntity) {
			for (Device device : devices) {
				if(deviceEntity.getDeviceId().equals(device.getDeviceId())) {
					assertAll(device, deviceEntity);
					i++;
				}
			}
		}
		Assertions.assertEquals(devices.size(), i);
	}
	
	@Test
	public void toDevicesNullTest() {
		List<Device> devices = deviceIntegrationParse.toDevices(null);
		
		Assertions.assertNotNull(devices);
		Assertions.assertTrue(devices.isEmpty());
	}
	
}
