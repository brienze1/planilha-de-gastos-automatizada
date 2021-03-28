package br.com.planilha.gastos.rules;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.exception.DeviceException;

@Component
public class DeviceRules {

	public boolean validate(List<Device> devices) {
		if(devices == null || devices.isEmpty()) {
			throw new DeviceException("List of devices can't be null or empty");
		}
		
		for (Device device : devices) {
			if(device == null) {
				throw new DeviceException("Device can't be null");
			}
			
			if(device.getDeviceId() == null || device.getDeviceId().isBlank()) {
				throw new DeviceException("Device Id can't be null or empty");
			}
		}
		
		return true;
	}

	public boolean validateDeviceRegistration(List<Device> devices) {
		validate(devices);
		
		if(devices.size() > 1) {
			throw new DeviceException("Can't register more than one device at a time");
		}
		
		return true;
	}

}
