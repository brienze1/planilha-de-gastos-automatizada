package br.com.planilha.gastos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.builder.DeviceBuilder;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.DeviceException;

@Component
public class DeviceService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DeviceBuilder deviceBuilder;
	
	@Autowired
	private EmailService emailService;
	
	public Device registerNewDevice(String userId, String deviceId) {
		User user = userService.findById(userId);
		
		for (Device userDevice : user.getDevices()) {
			if(userDevice.getId().equals(deviceId)) {
				return userDevice;
			}
		}

		Device device = deviceBuilder.build(deviceId);

		user.getDevices().add(device);
		
		userService.update(user);
		
		return device;
	}

	public Device sendDeviceVerificationEmail(String userId, Device device) {
		User user = userService.findById(userId);

		for (Device userDevice : user.getDevices()) {
			if(userDevice.getId().equals(device.getId())) {
				if(userDevice.isVerified()) {
					return userDevice;
				}
				
				emailService.sendDeviceVerificationEmail(user, device);
				
				return userDevice;
			}
		}
		
		throw new DeviceException("Usuario nao possui esse dispoisivo cadastrado");
	}

	public Device validateDevice(Device device, User userBase) {
		for (Device deviceBase : userBase.getDevices()) {
			if(deviceBase.getId().equals(device.getId()) && deviceBase.getVerificationCode().equals(device.getVerificationCode())) {
				deviceBase.setVerified(true);
				
				userService.update(userBase);
				
				return deviceBase;
			}
		}

		throw new DeviceException("Dispositivo nao encontrado na base");
	}

}
