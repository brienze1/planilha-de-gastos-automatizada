package br.com.planilha.gastos.parse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.dto.UserDto;
import br.com.planilha.gastos.entity.User;

@Component
public class UserDeliveryParse {
	
	@Autowired
	private DeviceDeliveryParse deviceParce;

	public User toUser(UserDto userDto) {
		User user = new User();
		
		if(userDto != null) {
			user.setAutoLogin(userDto.isAutoLogin());
			user.setPassword(userDto.getPassword());
			user.setSecret(userDto.getSecret());
			user.setValidEmail(userDto.isValidEmail());
			user.setDevices(deviceParce.toDevices(userDto.getDevice()));
			if(userDto.getDevice() != null) {
				user.setInUseDevice(userDto.getDevice().getDeviceId());
			}
			if(userDto.getEmail() != null) {
				user.setEmail(userDto.getEmail().toLowerCase());
			}
			if(userDto.getFirstName() != null) {
				user.setFirstName(userDto.getFirstName().substring(0,1).toUpperCase() + userDto.getFirstName().substring(1).toLowerCase());
			}
			if(userDto.getLastName() != null) {
				user.setLastName(userDto.getLastName().substring(0,1).toUpperCase() + userDto.getLastName().substring(1).toLowerCase());
			}
		}
		
		return user;
	}

}
