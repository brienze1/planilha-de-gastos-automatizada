package br.com.planilha.gastos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.builder.UserBuilder;
import br.com.planilha.gastos.entity.Login;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.UserRepositoryAdapter;
import br.com.planilha.gastos.rules.LoginRules;
import br.com.planilha.gastos.rules.UserRules;

@Component
public class UserService {

	@Autowired
	private UserRules userRules;
	
	@Autowired
	private UserRepositoryAdapter userRepository;
	
	@Autowired
	private UserBuilder userBuilder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private LoginRules loginRules;
	
	public String register(User user) {
		//Valida dados do novo usuario
		userRules.validateUserRegistrationData(user);
		
		//Gera Ids e configuracoes default do usuario
		userBuilder.build(user);

		//Registra o usuario na base de dados
		userRepository.register(user);
		
		//Gera jwt de resposta utilizando o email como secret 
		String jwtToken = jwtService.generate(user.getId(), user.getSecret(), user);
		
		//retorna o id gerado
		return jwtToken;
	}

	public void update(User user) {
		//Valida dados do usuario
		userRules.validateUserRegistrationData(user);
		
		//Atualiza usuario na base de dados
		userRepository.update(user);
	}
	
	public User findById(String id){
		Optional<User> user = userRepository.findById(id);
		
		userRules.validate(user);

		return user.get();
	}
	
	public List<User> findAllUsers(){
		return userRepository.findAllUsers();
	}
	
	public User findByEmail(String email){
		Optional<User> user = userRepository.findByEmail(email);
		
		userRules.validate(user);
		
		return user.get();
	}

	public String autoLogin(String jwtDataToken) {
		//Busca dados do payload do jwt
		Login login = jwtService.decodeAndVerify(jwtDataToken, Login.class);
		
		//Encontra o usuario na base de dados
		User user = findByEmail(login.getEmail());
		
		//Valida dados de login
		loginRules.validateAutoLogin(login, user);
		
		//Gera token de acesso para o usuario
		return jwtService.generateAcessToken(user);
	}

	public Object login(Login login) {
		//Encontra o usuario na base de dados
		User user = findByEmail(login.getEmail());
		
		//Valida dados de login
		loginRules.validate(login, user);
		
		//Gera token de acesso para o usuario
		return jwtService.generateAcessToken(user);
	}

}
