package br.com.planilha.gastos.rules;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.exception.DeviceException;

@Component
public class DeviceRules {

	public void validate(List<Device> devices) {
		if(devices == null || devices.isEmpty()) {
			throw new DeviceException("Devices can't be null");
		}
		
		for (Device device : devices) {
			if(device == null) {
				throw new DeviceException("Device can't be null");
			}
			
			if(device.getDeviceId() == null || device.getDeviceId().isBlank()) {
				throw new DeviceException("Device Id can't be null");
			}
		}
	}

	public void validateDeviceRegistration(List<Device> devices) {
		validate(devices);
		
		if(devices.size() > 1) {
			throw new DeviceException("Can't register more than one device at a time");
		}
	}

}
