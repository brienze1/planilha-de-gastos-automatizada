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
			user.setDevices(deviceParce.toDevices(userDto.getDevice()));
			if(userDto.getDevice() != null) {
				user.setInUseDevice(userDto.getDevice().getId());
			}
			user.setEmail(userDto.getEmail());
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setPassword(userDto.getPassword());
			user.setSecret(userDto.getSecret());
			user.setValidEmail(userDto.isValidEmail());
		}
		
		return user;
	}

}
