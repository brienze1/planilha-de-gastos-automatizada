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

	public void validate(Login login, User user) {
		if(login == null) {
			throw new LoginException("Login can't be null");
		}
		
		if(login.getEmail() == null || login.getEmail().isBlank()) {
			throw new LoginException("Email can't be null");
		}
		
		if(login.getPassword() == null || login.getPassword().isBlank()) {
			throw new LoginException("Password can't be null");
		} 
		
		if(login.getDeviceId() == null || login.getDeviceId().isBlank()) {
			throw new LoginException("DeviceId can't be null");
		} 

		for (Device device : user.getDevices()) {
			if(login.getDeviceId().equals(device.getId())) {
				//Verifica se a senha bate
				if(!passwordUtils.verifyPassword(login.getPassword(), user.getPassword(), user.getSecret())) {
					throw new LoginException("Password does not match");
				}
				
				return;
			}
		}
		
		//Cadastra novo dispositivo
		deviceService.registerNewDevice(user.getId(), login.getDeviceId());
		
		//Verifica se a senha bate
		if(!user.getPassword().equals(passwordUtils.encode(login.getPassword(), user.getSecret()))) {
			throw new LoginException("Password does not match");
		}
	}

	public void validateAutoLogin(Login login, User user) {
		if(login == null) {
			throw new AutoLoginException("Login can't be null");
		}
		
		if(login.getEmail() == null || login.getEmail().isBlank()) {
			throw new AutoLoginException("Email can't be null");
		}
		
		if(login.getDeviceId() == null || login.getDeviceId().isBlank()) {
			throw new AutoLoginException("DeviceId can't be null");
		} 
		
		if(!user.isAutoLogin()) {
			throw new AutoLoginException("User configuration doesn't allow auto-login");
		}
		
		for (Device device : user.getDevices()) {
			if(login.getDeviceId().equals(device.getId())) {
				if(!device.isVerified()) {
					deviceService.sendDeviceVerificationEmail(user.getId(), device);
					throw new DeviceNotVerifiedException("Device not verified");
				}
				return;
			}
		}
		
		Device device = deviceService.registerNewDevice(user.getId(), login.getDeviceId());
		
		deviceService.sendDeviceVerificationEmail(user.getId(), device);
		
		throw new DeviceNotVerifiedException("Device not verified");
	}

	
	
}
