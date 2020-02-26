package br.com.planilha.gastos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.port.IdGeneratorAdapter;
import br.com.planilha.gastos.port.PasswordEncoderAdapter;
import br.com.planilha.gastos.port.UserRepositoryAdapter;
import br.com.planilha.gastos.port.UserServiceAdapter;
import br.com.planilha.gastos.rules.UserValidatorExample;

@Component
public class UserService implements UserServiceAdapter {

	@Autowired
	private UserValidatorExample userValidatorExample;
	
	@Autowired
	private UserRepositoryAdapter repository;
	
	@Autowired
	private PasswordEncoderAdapter passwordEncoder;
	
	@Autowired
	private IdGeneratorAdapter idGenerator;
	
	public String create(UserExample user) {
		userValidatorExample.validateCreateUser(user);
		
		user.setId(idGenerator.generate());
		user.setPassword(passwordEncoder.encode(user.getEmail()+user.getPassword()));
		
		repository.create(user);
		
		return user.getId();
	}
	
	public Optional<UserExample> findById(final String id){
		return repository.findById(id);
	}
	
	public List<UserExample> findAllUsers(){
		return repository.findAllUsers();
	}
	
	public Optional<UserExample> findByEmail(String email){
		return repository.findByEmail(email);
	}
	
}
