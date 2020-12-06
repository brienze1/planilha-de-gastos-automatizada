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
	private UserIntegrationParse userIntegrationParse;
	
	@Override
	public Optional<User> findById(String id) {
		Optional<UserEntity> userEntity;
		userEntity = userRepository.findById(Integer.valueOf(id));

		return userIntegrationParse.toUser(userEntity);
	}
	
	public UserEntity findUserEntity(String id) {
		Optional<UserEntity> userEntity;
		userEntity = userRepository.findById(Integer.valueOf(id));
		
		return userEntity.get();
	}

	@Override
	public List<User> findAllUsers() {
		List<UserEntity> userEntityList;
		userEntityList = userRepository.findAll();

		return userIntegrationParse.toUsers(userEntityList);
	}

	@Override
	public User save(User user) {
		UserEntity userEntity = userIntegrationParse.toUserEntity(user);
		
		return userIntegrationParse.toUser(userRepository.save(userEntity));
	}

	@Override
	public Optional<User> findByEmail(String email) {
		Optional<UserEntity> userEntity = userRepository.findByEmail(email.toLowerCase());

		return userIntegrationParse.toUser(userEntity);
	}
	
}
