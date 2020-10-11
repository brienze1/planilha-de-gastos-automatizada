package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.entity.UserEntity;

@Component
public class UserIntegrationParse {

	@Autowired
	private DeviceIntegrationParse deviceIntegrationParse;
	
	public Optional<User> toUser(Optional<UserEntity> userEntity) {
		User user = null;
		
		if(userEntity.isPresent()) {
			user = toUser(userEntity.get());
		}
		
		return Optional.ofNullable(user);
	}

	public List<User> toUsers(List<UserEntity> userEntityList) {
		List<User> users = new ArrayList<>();
		
		if(userEntityList != null && !userEntityList.isEmpty()) {
			for (UserEntity userEntity : userEntityList) {
				users.add(toUser(userEntity));
			}
		}
		
		return users;
	}

	private User toUser(UserEntity userEntity) {
		User user = new User();
		
		if(userEntity != null) {
			user.setAutoLogin(userEntity.isAutoLogin());
			user.setDevices(deviceIntegrationParse.toDevices(userEntity.getDevices()));
			user.setFirstName(userEntity.getFirstName());
			user.setId(userEntity.getId());
			user.setLastName(userEntity.getLastName());
			user.setPassword(userEntity.getPassword());
			user.setSecret(userEntity.getSecret());
			user.setValidEmail(userEntity.isValidEmail());
			user.setEmail(userEntity.getEmail().toLowerCase());
		}
		
		return user;
	}
	
	public UserEntity toUserEntity(User user) {
		UserEntity userEntity = new UserEntity();
		
		if(user != null) {
			userEntity.setAutoLogin(user.isAutoLogin());
			userEntity.setDevices(deviceIntegrationParse.toDevicesEntity(user.getDevices()));
			userEntity.setFirstName(user.getFirstName());
			userEntity.setId(user.getId());
			userEntity.setLastName(user.getLastName());
			userEntity.setPassword(user.getPassword());
			userEntity.setSecret(user.getSecret());
			userEntity.setValidEmail(user.isValidEmail());
			userEntity.setEmail(user.getEmail().toLowerCase());
		}
		
		return userEntity;
	}

}
