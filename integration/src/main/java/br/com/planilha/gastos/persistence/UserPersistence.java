package br.com.planilha.gastos.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.entity.UserEntity;
import br.com.planilha.gastos.exception.UserNotFoundException;
import br.com.planilha.gastos.parse.UserEntityParse;
import br.com.planilha.gastos.parse.UserParse;
import br.com.planilha.gastos.port.UserRepositoryAdapter;
import br.com.planilha.gastos.repository.UserRepository;

@Component
public class UserPersistence implements UserRepositoryAdapter {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserParse userParse;
	
	@Autowired
	private UserEntityParse userEntityParse;

	@Override
	public Optional<User> findById(String id) {
		Optional<UserEntity> userEntity;
		userEntity = userRepository.findById(id);

		if (!userEntity.isPresent()) {
			return Optional.ofNullable(null);
		}

		return Optional.of(userParse.parse(userEntity.get()));
	}

	@Override
	public List<User> findAllUsers() {
		List<UserEntity> userEntityList;
		userEntityList = userRepository.findAll();

		return userParse.parse(userEntityList);
	}

	@Override
	public void register(User user) {
		UserEntity userEntity = userEntityParse.parse(user);
		
		//Salva na base de dados
		userRepository.save(userEntity);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		List<UserEntity> userEntityList;
		userEntityList = userRepository.findAllByEmail(email.toLowerCase());

		if (userEntityList.isEmpty()) {
			return Optional.ofNullable(null);
		} else {
			return Optional.of(userParse.parse(userEntityList.get(0)));
		}
	}
	
	@Override
	public void update(User user) {
		UserEntity userEntity = userEntityParse.parse(user);
		
		if(userRepository.existsById(userEntity.getId())) {
			userRepository.save(userEntity);
		}
		
		throw new UserNotFoundException("Usuario nao cadastrado na base de dados");
	}

}
