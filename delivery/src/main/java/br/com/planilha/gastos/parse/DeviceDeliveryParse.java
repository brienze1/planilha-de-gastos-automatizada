package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.dto.DeviceDto;
import br.com.planilha.gastos.entity.Device;

@Component
public class DeviceDeliveryParse {

	public List<Device> toDevices(List<DeviceDto> devicesDto) {
		List<Device> devices = new ArrayList<>();
		
		if(devicesDto != null && !devicesDto.isEmpty()) {
			for (DeviceDto deviceDto : devicesDto) {
				devices.add(toDevice(deviceDto));
			}
		}
		
		return devices;
	}

	public Device toDevice(DeviceDto deviceDto) {
		Device device = new Device();
		
		if(deviceDto != null) {
			device.setId(deviceDto.getId());
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
