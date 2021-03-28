package br.com.planilha.gastos.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.port.IdGeneratorAdapter;

@Component
public class DeviceBuilder {
	
	@Autowired
	private IdGeneratorAdapter idGenerator;

	public Device build(String deviceId) {
		Device device = new Device(deviceId);
		device.setVerified(false);
		device.setInUse(false);
		device.setVerificationCode(idGenerator.generateVerificationCode()); 
		
		return device;
	}

}
