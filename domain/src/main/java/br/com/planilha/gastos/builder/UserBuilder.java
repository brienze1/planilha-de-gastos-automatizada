package br.com.planilha.gastos.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.UserValidationException;
import br.com.planilha.gastos.port.IdGeneratorAdapter;
import br.com.planilha.gastos.port.PasswordUtilsAdapter;

@Component
public class UserBuilder {

	@Autowired
	private PasswordUtilsAdapter passwordUtils;
	
	@Autowired
	private IdGeneratorAdapter idGenerator;
	
	@Autowired
	private DeviceBuilder deviceBuilder;
	
	public User build(User user) {
		user.setId(idGenerator.generateId());
		user.setValidEmail(false);
		user.setAutoLogin(false);
		user.setSecret(idGenerator.generateSecret());
		user.setPassword(passwordUtils.encode(user.getPassword(), user.getSecret()));

		for (Device device : user.getDevices()) {
			user.getDevices().remove(device);
			user.getDevices().add(deviceBuilder.build(device.getId()));
			user.setInUseDevice(device.getId());
		}
		
		return user;
	}

	public User buildChanges(User user, User userSalvo) {
		if(userSalvo != null && user != null) {
			if(user.getFirstName() != null && !user.getFirstName().isBlank() && user.getFirstName().equalsIgnoreCase(user.getFirstName()) ) {
				userSalvo.setFirstName(user.getFirstName());
			}
			
			if(user.getLastName() != null && !user.getLastName().isBlank() && user.getLastName().equalsIgnoreCase(user.getLastName())) {
				userSalvo.setLastName(user.getLastName());
			}
			
			if(user.getPassword() != null && !user.getPassword().isBlank() && user.getPassword().equalsIgnoreCase(user.getPassword())) {
				userSalvo.setPassword(user.getPassword());
			}
			
			userSalvo.setAutoLogin(user.isAutoLogin());
			
			return userSalvo;
		}
		
		throw new UserValidationException("Usuario nao pode ser nulo");		
	}

}
