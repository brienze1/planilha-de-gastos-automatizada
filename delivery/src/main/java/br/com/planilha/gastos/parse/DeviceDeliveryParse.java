package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.dto.DeviceDto;
import br.com.planilha.gastos.entity.Device;

@Component
public class DeviceDeliveryParse {

	public Device toDevice(DeviceDto deviceDto) {
		Device device = new Device();
		
		if(deviceDto != null) {
			device.setDeviceId(deviceDto.getDeviceId());
			device.setVerificationCode(deviceDto.getVerificationCode());
			device.setVerified(deviceDto.isVerified());
			device.setInUse(deviceDto.isInUse());	
		}

		return device;
	}

	public List<Device> toDevices(DeviceDto deviceDto) {
		List<Device> devices = new ArrayList<>();

		if(deviceDto != null) {
			devices.add(toDevice(deviceDto));
		}
		
		return devices;
	}
	
	
	
}
