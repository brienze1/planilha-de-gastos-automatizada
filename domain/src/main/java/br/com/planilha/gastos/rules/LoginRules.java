package br.com.planilha.gastos.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.Login;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.AutoLoginException;
import br.com.planilha.gastos.exception.DeviceNotVerifiedException;
import br.com.planilha.gastos.exception.LoginException;
import br.com.planilha.gastos.port.PasswordUtilsAdapter;
import br.com.planilha.gastos.service.DeviceService;

@Component
public class LoginRules {
	
	@Autowired
	private PasswordUtilsAdapter passwordUtils;
	
	@Autowired
	private DeviceService deviceService;

	public boolean validate(Login login, User user) {
		validate(login);
		
		if(user == null) {
			throw new LoginException("User can't be null");
		}
		
		if(login.getPassword() == null || login.getPassword().isBlank()) {
			throw new LoginException("Password can't be null or blank");
		} else if(!passwordUtils.verifyPassword(login.getPassword(), user.getPassword(), user.getSecret())) {
			throw new LoginException("Password does not match");
		}
		
		for (Device device : user.getDevices()) {
			if(login.getDeviceId().equals(device.getDeviceId())) {
				return true;
			}
		}
		
		//Cadastra novo dispositivo
		deviceService.registerNewDevice(user.getId(), login.getDeviceId());
		
		return true;
	}

	public boolean validateAutoLogin(Login login, User user) {
		validate(login);
		
		if(user == null) {
			throw new AutoLoginException("User can't be null");
		}
		
		if(login.getEmail() == null || login.getEmail().isBlank()) {
			throw new AutoLoginException("Email can't be null or blank");
		}
		
		if(login.getDeviceId() == null || login.getDeviceId().isBlank()) {
			throw new AutoLoginException("DeviceId can't be null or blank");
		} 
		
		if(!user.isAutoLogin()) {
			throw new AutoLoginException("User configuration doesn't allow auto-login");
		}
		
		for (Device device : user.getDevices()) {
			if(login.getDeviceId().equals(device.getDeviceId())) {
				if(!device.isVerified()) {
					deviceService.sendDeviceVerificationEmail(user.getId(), device);
					throw new DeviceNotVerifiedException("Device not verified");
				}
				return true;
			}
		}
		
		throw new DeviceNotVerifiedException("Unknown device");
	}

	public boolean validate(Login login) {
		if(login == null) {
			throw new LoginException("Login can't be null");
		}

		if(login.getEmail() == null || login.getEmail().isBlank()) {
			throw new LoginException("Email can't be null or blank");
		}
		
		if(login.getDeviceId() == null || login.getDeviceId().isBlank()) {
			throw new LoginException("DeviceId can't be null or blank");
		} 

		return true;		
	}
	
}
