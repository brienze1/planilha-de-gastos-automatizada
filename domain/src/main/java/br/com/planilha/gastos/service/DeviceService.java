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
	private JwtService jwtService;
	
	@Autowired
	private DeviceBuilder deviceBuilder;
	
	@Autowired
	private EmailService emailService;
	
	public Device registerNewDevice(String userId, String deviceId) {
		User user = userService.findById(userId);
		
		Device device = deviceBuilder.build(deviceId);

		for(int i=0; i<user.getDevices().size(); i++) {
			if(user.getDevices().get(i).getDeviceId().equals(device.getDeviceId())) {
				device.setId(user.getDevices().get(i).getId());
				user.getDevices().remove(i);
			}
		}
		
		user.getDevices().add(device);
		
		userService.update(user);
		
		sendDeviceVerificationEmail(user.getId(), device);
		
		return device;
	}

	public Device sendDeviceVerificationEmail(String userId, Device device) {
		User user = userService.findById(userId);

		for (Device userDevice : user.getDevices()) {
			if(userDevice.getDeviceId().equals(device.getDeviceId())) {
				if(userDevice.isVerified()) {
					return userDevice;
				}
				
				emailService.sendDeviceVerificationEmail(user, userDevice);
				
				return userDevice;
			}
		}
		
		throw new DeviceException("Unkown device");
	}

	public Device validateDevice(String token, Device device) {
		User user = jwtService.verifyAcessToken(token);
		
		for (Device deviceBase : user.getDevices()) {
			if(deviceBase.getDeviceId().equals(device.getDeviceId()) && deviceBase.getVerificationCode().equals(device.getVerificationCode())) {
				deviceBase.setVerified(true);
				userService.update(user);
				
				return deviceBase;
			}
		}

		throw new DeviceException("Unkown device");
	}

}
