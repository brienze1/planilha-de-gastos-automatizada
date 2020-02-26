package br.com.planilha.gastos.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.entity.UserExampleEntity;
import br.com.planilha.gastos.parse.UserExampleEntityParaUserExampleParse;
import br.com.planilha.gastos.parse.UserExampleParaUserExampleEntityParse;
import br.com.planilha.gastos.port.UserRepositoryAdapter;
import br.com.planilha.gastos.repository.UserExampleEntityRepository;

@Component
public class UserExamplePersistence implements UserRepositoryAdapter {

	@Autowired
	private UserExampleEntityRepository userExampleEntityRepository;

	@Autowired
	private UserExampleEntityParaUserExampleParse userExampleEntityParaUserExampleParse;

	@Autowired
	private UserExampleParaUserExampleEntityParse userExampleParaUserExampleEntityParse;

	@Override
	public Optional<UserExample> findById(String id) {
		Optional<UserExampleEntity> userExampleEntity;
		userExampleEntity = userExampleEntityRepository.findById(id);

		if (!userExampleEntity.isPresent()) {
			return Optional.ofNullable(null);
		}

		return Optional.of(userExampleEntityParaUserExampleParse.parse(userExampleEntity.get()));
	}

	@Override
	public List<UserExample> findAllUsers() {
		List<UserExampleEntity> userExampleEntityList;
		userExampleEntityList = userExampleEntityRepository.findAll();

		return userExampleEntityParaUserExampleParse.parseList(userExampleEntityList);
	}

	@Override
	public void create(UserExample userExample) {
		userExampleEntityRepository.save(userExampleParaUserExampleEntityParse.parse(userExample));
	}

	@Override
	public Optional<UserExample> findByEmail(String email) {
		List<UserExampleEntity> userExampleEntityList;
		userExampleEntityList = userExampleEntityRepository.findAllByEmail(email);

		if (userExampleEntityList.isEmpty()) {
			return Optional.ofNullable(null);
		} else {
			return Optional.of(userExampleEntityParaUserExampleParse.parse(userExampleEntityList.get(0)));
		}
	}

}
