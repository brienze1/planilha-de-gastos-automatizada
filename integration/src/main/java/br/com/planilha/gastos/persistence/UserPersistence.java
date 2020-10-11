package br.com.planilha.gastos.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.entity.UserEntity;
import br.com.planilha.gastos.parse.UserIntegrationParse;
import br.com.planilha.gastos.port.UserRepositoryAdapter;
import br.com.planilha.gastos.repository.UserRepository;

@Component
public class UserPersistence implements UserRepositoryAdapter {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DevicePersistence devicePersistence;

	@Autowired
	private UserIntegrationParse userIntegrationParse;
	
	@Override
	public Optional<User> findById(String id) {
		Optional<UserEntity> userEntity;
		userEntity = userRepository.findById(id);

		return userIntegrationParse.toUser(userEntity);
	}

	@Override
	public List<User> findAllUsers() {
		List<UserEntity> userEntityList;
		userEntityList = userRepository.findAll();

		return userIntegrationParse.toUsers(userEntityList);
	}

	@Override
	public void save(User user) {
		UserEntity userEntity = userIntegrationParse.toUserEntity(user);
		
		devicePersistence.save(userEntity.getDevices());
		
		userRepository.save(userEntity);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		Optional<UserEntity> userEntity = userRepository.findByEmail(email.toLowerCase());

		return userIntegrationParse.toUser(userEntity);
	}
	
}
