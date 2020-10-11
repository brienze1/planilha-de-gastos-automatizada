package br.com.planilha.gastos.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;
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
		
		for(int i=0; i<user.getDevices().size(); i++) {
			user.getDevices().add(i, deviceBuilder.build(user.getDevices().get(i).getId()));
		}
		
		return user;
	}

}
