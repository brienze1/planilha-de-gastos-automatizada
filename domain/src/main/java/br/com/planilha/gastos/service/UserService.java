package br.com.planilha.gastos.service;

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
		
		User generatedUser = userBuilder.build(user);

		User savedUser = userRepository.save(generatedUser);
		
		for (Device device : savedUser.getDevices()) {
			deviceService.sendDeviceVerificationEmail(savedUser.getId(), device);
		}
		
		String jwtToken = jwtService.generate(savedUser);
		
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
	
	public User findByEmail(String email){
		Optional<User> user = userRepository.findByEmail(email);
		
		userRules.validate(user);
		
		return user.get();
	}

	public String autoLogin(Login login) {
		loginRules.validate(login);
		
		User user = findByEmail(login.getEmail());
		
		loginRules.validateAutoLogin(login, user);
		
		return jwtService.generateAcessToken(user, login.getDeviceId());
	}

	public String login(Login login) {
		loginRules.validate(login);
		
		User user = findByEmail(login.getEmail());
		
		loginRules.validate(login, user);

		User updatedUser = findByEmail(login.getEmail());
		
		return jwtService.generateAcessToken(updatedUser, login.getDeviceId());
	}

	public User configureUser(String token, User user) {
		User userBase = jwtService.verifyAcessToken(token);
		
		User novoUser = userBuilder.buildChanges(user, userBase);
		
		return userRepository.save(novoUser);
	}

//	public String registerDevice(User user) {
//		userRules.validateDeviceRegistration(user);
//		
//		User registeredUser = findByEmail(user.getEmail());
//
//		//Verifica se a senha bate
//		if(!passwordUtils.verifyPassword(user.getPassword(), registeredUser.getPassword(), registeredUser.getSecret())) {
//			throw new LoginException("Password does not match");
//		}
//		
//		Device registeredDevice = deviceService.registerNewDevice(registeredUser.getId(), user.getDevices().get(0).getDeviceId());
//		
//		registeredUser.getDevices().clear();
//		registeredUser.getDevices().add(registeredDevice);
//		registeredUser.setInUseDevice(registeredDevice.getDeviceId());
//		
//		String jwtToken = jwtService.generate(registeredUser);
//		
//		return jwtToken;
//	}

}
