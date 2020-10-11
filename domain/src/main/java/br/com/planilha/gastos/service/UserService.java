package br.com.planilha.gastos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.builder.UserBuilder;
import br.com.planilha.gastos.entity.Device;
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
	
	@Autowired
	private DeviceService deviceService;
	
	public String register(User user) {
		userRules.validateUserRegistrationData(user);
		
		user = userBuilder.build(user);

		userRepository.save(user);
		
		for (Device device : user.getDevices()) {
			deviceService.sendDeviceVerificationEmail(user.getId(), device);
		}
		
		String jwtToken = jwtService.generate(user.getId(), user.getSecret(), user);
		
		return jwtToken;
	}

	public User update(User user) {
		userRules.validate(user);
		
		return userRepository.save(user);
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

	public String autoLogin(Login login) {
		User user = findByEmail(login.getEmail());
		
		loginRules.validateAutoLogin(login, user);
		
		user.setInUseDevice(login.getDeviceId());
		
		return jwtService.generateAcessToken(user);
	}

	public String login(Login login) {
		User user = findByEmail(login.getEmail());
		
		loginRules.validate(login, user);
		
		return jwtService.generateAcessToken(user);
	}

	public void configureUser(String token, User user) {
		User userBase = jwtService.verifyAcessToken(token);
		
		User novoUser = userBuilder.buildChanges(user, userBase);
		
		userRepository.save(novoUser);
	}

	public void validateDevice(String token, Device device) {
		User userBase = jwtService.verifyAcessToken(token);
		
		deviceService.validateDevice(device, userBase);
	}

}
