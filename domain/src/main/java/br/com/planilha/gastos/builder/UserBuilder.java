package br.com.planilha.gastos.builder;

import java.util.HashMap;
import java.util.Map;

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
		user.setValidEmail(false);
		user.setAutoLogin(false);
		user.setSecret(idGenerator.generateSecret());
		user.setPassword(passwordUtils.encode(user.getPassword(), user.getSecret()));

		for (Device device : user.getDevices()) {
			user.getDevices().remove(device);
			user.getDevices().add(deviceBuilder.build(device.getDeviceId()));
			user.setInUseDevice(device.getDeviceId());
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

	public Map<String, Object> buildJwtPayload(User user) {
		Map<String, Object> devicePayload = new HashMap<>();
		devicePayload.put("device_id", user.getDevices().get(0).getDeviceId());
		devicePayload.put("verification_code", user.getDevices().get(0).getVerificationCode());
		devicePayload.put("in_use", user.getDevices().get(0).isInUse());
		devicePayload.put("verified", user.getDevices().get(0).isVerified());
		
		Map<String, Object> payload = new HashMap<>();
		payload.put("id", user.getId());
		payload.put("email", user.getEmail());
		payload.put("password", user.getPassword());
		payload.put("last_name", user.getLastName());
		payload.put("first_name", user.getFirstName());
		payload.put("secret", user.getSecret());
		payload.put("device", devicePayload);
		payload.put("valid_email", user.isValidEmail());
		payload.put("auto_login", user.isAutoLogin());
		
		return payload;
	}

}
