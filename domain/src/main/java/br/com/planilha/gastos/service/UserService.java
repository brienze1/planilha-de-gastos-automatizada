package br.com.planilha.gastos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.builder.UserBuilder;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.JwtTokenUtilsAdapter;
import br.com.planilha.gastos.port.UserRepositoryAdapter;
import br.com.planilha.gastos.rules.UserValidator;

@Component
public class UserService {

	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private UserRepositoryAdapter repository;
	
	@Autowired
	private UserBuilder userBuilder;
	
	@Autowired
	private JwtTokenUtilsAdapter jwtTokenUtils;
	
	public String register(User user) {
		//Valida dados do novo usuario
		userValidator.validateUserRegistrationData(user);
		
		//Gera Ids e configuracoes default do usuario
		userBuilder.build(user);

		//Registra o usuario na base de dados
		repository.register(user);
		
		//Gera jwt de resposta utilizando o email como secret 
		String jwtToken = jwtTokenUtils.generate(user.getId(), user.getEmail(), user);
		
		//retorna o id gerado
		return jwtToken;
	}
	
	public Optional<User> findById(final String id){
		return repository.findById(id);
	}
	
	public List<User> findAllUsers(){
		return repository.findAllUsers();
	}
	
	public Optional<User> findByEmail(String email){
		return repository.findByEmail(email);
	}

}
