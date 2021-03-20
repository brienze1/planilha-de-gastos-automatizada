package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.DeviceEntity;

@Component
public class DeviceIntegrationParse {

	public Set<DeviceEntity> toDevicesEntity(List<Device> devices) {
		Set<DeviceEntity> devicesEntity = new HashSet<>();
		
		if(devices != null) {
			for (Device device : devices) {
				devicesEntity.add(toDeviceEntity(device));
			}
		}
		
		return devicesEntity;
	}

	private DeviceEntity toDeviceEntity(Device device) {
		DeviceEntity deviceEntity = new DeviceEntity();
		
		if(device != null) {
			try {
				deviceEntity.setId(Integer.valueOf(device.getId()));
			}catch (Exception e) {
			}
			deviceEntity.setDeviceId(device.getDeviceId());
			deviceEntity.setInUse(device.isInUse());
			deviceEntity.setVerificationCode(device.getVerificationCode());
			deviceEntity.setVerified(device.isVerified());
		}
	
		return deviceEntity;
	}

	public List<Device> toDevices(Iterable<DeviceEntity> devicesEntity) {
		List<Device> devices = new ArrayList<>();
		
		if(devicesEntity != null) {
			for (DeviceEntity deviceEntity : devicesEntity) {
				devices.add(toDevice(deviceEntity));
			}
		}
		
		return devices;
	}

	private Device toDevice(DeviceEntity deviceEntity) {
		Device device = new Device();
		
		if(deviceEntity != null) {
			device.setId(String.valueOf(deviceEntity.getId()));
			device.setDeviceId(deviceEntity.getDeviceId());
			device.setInUse(deviceEntity.isInUse());
			device.setVerificationCode(deviceEntity.getVerificationCode());
			device.setVerified(deviceEntity.isVerified());
		}
	
		return device;
	}

}
