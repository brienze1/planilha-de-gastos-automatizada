package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.DeviceEntity;

@RunWith(SpringRunner.class)
public class DeviceIntegrationParseTest {

	@InjectMocks
	private DeviceIntegrationParse deviceIntegrationParse;
	
	private List<Device> devices;
	private Set<DeviceEntity> devicesEntity;
	
	@Before
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
		Assert.assertNotNull(deviceEntity);
		Assert.assertNotNull(device);
		Assert.assertEquals(device.getDeviceId(), deviceEntity.getDeviceId());
		Assert.assertEquals(device.getId(), String.valueOf(deviceEntity.getId()));
		Assert.assertEquals(device.getVerificationCode(), deviceEntity.getVerificationCode());
		Assert.assertEquals(device.isInUse(), deviceEntity.isInUse());
		Assert.assertEquals(device.isVerified(), deviceEntity.isVerified());
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
		Assert.assertEquals(devicesEntity.size(), i);
	}
	
	@Test
	public void toDevicesEntityNullTest() {
		Set<DeviceEntity> devicesEntity = deviceIntegrationParse.toDevicesEntity(null);
		
		Assert.assertNotNull(devicesEntity);
		Assert.assertTrue(devicesEntity.isEmpty());
	}
	
	@Test
	public void toDevicesEntityNullEntityTest() {
		devices.set(0, null);
		
		Set<DeviceEntity> devicesEntity = deviceIntegrationParse.toDevicesEntity(devices);
		
		Assert.assertNotNull(devicesEntity);
		Assert.assertFalse(devicesEntity.isEmpty());
		Assert.assertEquals(devices.size(), devicesEntity.size());
	}
	
	@Test
	public void toDevicesEntityIdStringTest() {
		devices.get(0).setId(UUID.randomUUID().toString());
		
		Set<DeviceEntity> devicesEntity = deviceIntegrationParse.toDevicesEntity(devices);
		
		Assert.assertNotNull(devicesEntity);
		Assert.assertFalse(devicesEntity.isEmpty());
		Assert.assertEquals(devices.size(), devicesEntity.size());
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
		Assert.assertEquals(devices.size(), i);
	}
	
	@Test
	public void toDevicesNullTest() {
		List<Device> devices = deviceIntegrationParse.toDevices(null);
		
		Assert.assertNotNull(devices);
		Assert.assertTrue(devices.isEmpty());
	}
	
}
