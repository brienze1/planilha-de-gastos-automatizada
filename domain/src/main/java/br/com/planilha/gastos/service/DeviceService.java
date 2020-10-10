package br.com.planilha.gastos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.builder.DeviceBuilder;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;

@Component
public class DeviceService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DeviceBuilder deviceBuilder;
	
	@Autowired
	private EmailService emailService;
	
	public void registerNewDevice(String userId, String deviceId) {
		//Busca usuario
		User user = userService.findById(userId);
		
		//Gera novo device com o id informado
		Device device = deviceBuilder.build(deviceId);
		
		//Verifica se o usuario ja possui o dispositivo informado
		boolean deviceFound = false;
		user.getDevices().add(device);
		for(int i=0; i<user.getDevices().size(); i++) {
			if(user.getDevices().get(i).getId().equals(deviceId)) {
				if(deviceFound) {
					user.getDevices().remove(i);
				}
				
				deviceFound = true;
				user.getDevices().set(i, device);
			}
		}
		
		//Salva o usuario na base de dados com o novo device
		userService.update(user);
		
		//Envia email para verificar o novo device
		emailService.sendDeviceVerificationEmail(user, device);
	}

}
