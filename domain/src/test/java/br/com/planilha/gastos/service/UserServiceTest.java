package br.com.planilha.gastos.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.builder.UserBuilder;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.Login;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.PasswordUtilsAdapter;
import br.com.planilha.gastos.port.UserRepositoryAdapter;
import br.com.planilha.gastos.rules.LoginRules;
import br.com.planilha.gastos.rules.UserRules;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRules userRules;
	
	@Mock
	private UserRepositoryAdapter userRepository;
	
	@Mock
	private UserBuilder userBuilder;

	@Mock
	private JwtService jwtService;
	
	@Mock
	private LoginRules loginRules;
	
	@Mock
	private DeviceService deviceService;
	
	@Mock
	private PasswordUtilsAdapter passwordUtils;
	
	private User newUser;
	private User user;
	private User savedUser;
	private String token;
	private String id;
	private String email;
	private Login login;
	
	@BeforeEach
	public void init() {
		id = UUID.randomUUID().toString();
		email = UUID.randomUUID().toString();
		
		newUser = new User();
		newUser.setId(id);
		newUser.setPassword(UUID.randomUUID().toString());
		newUser.setEmail(email);
		newUser.setFirstName(UUID.randomUUID().toString());
		newUser.setLastName(UUID.randomUUID().toString());
		newUser.setDevices(new ArrayList<>());
		newUser.getDevices().add(new Device(UUID.randomUUID().toString()));
		
		user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setValidEmail(false);
		user.setAutoLogin(false);
		user.setSecret(UUID.randomUUID().toString());
		user.setPassword(passwordUtils.encode(user.getPassword(), user.getSecret()));
		user.setDevices(new ArrayList<>());
		user.getDevices().add(new Device(newUser.getDevices().get(0).getDeviceId()));
		
		savedUser = new User();
		savedUser.setId(id);
		savedUser.setEmail(email);
		savedUser.setValidEmail(false);
		savedUser.setAutoLogin(false);
		savedUser.setSecret(UUID.randomUUID().toString());
		savedUser.setPassword(passwordUtils.encode(user.getPassword(), user.getSecret()));
		savedUser.setDevices(new ArrayList<>());
		savedUser.getDevices().add(new Device(newUser.getDevices().get(0).getDeviceId()));
		
		token = UUID.randomUUID().toString();
		
		login = new Login();
		login.setDeviceId(user.getDevices().get(0).getDeviceId());
		login.setEmail(email);
		login.setPassword(user.getPassword());
	}
	
	@Test
	public void registerTest() {
		Mockito.when(userBuilder.build(newUser)).thenReturn(user);
		Mockito.when(userRepository.save(user)).thenReturn(savedUser);
		Mockito.when(jwtService.generate(savedUser)).thenReturn(token);
		
		String token = userService.register(newUser);
		
		Mockito.verify(userRules).validateUserRegistrationData(newUser);
		Mockito.verify(userBuilder).build(newUser);
		Mockito.verify(userRepository).save(user);
		Mockito.verify(deviceService).sendDeviceVerificationEmail(savedUser.getId(), savedUser.getDevices().get(0));
		Mockito.verify(jwtService).generate(savedUser);
		
		Assertions.assertNotNull(token);
		Assertions.assertFalse(token.isBlank());
	}
	
	@Test
	public void updateTest() {
		Mockito.when(userRepository.save(user)).thenReturn(savedUser);
		
		User returnedUser = userService.update(user);
		
		Mockito.verify(userRules).validate(user);
		Mockito.verify(userRepository).save(user);
		
		Assertions.assertNotNull(returnedUser);
		Assertions.assertEquals(savedUser.getId(), returnedUser.getId());
	}
	
	@Test
	public void findByIdTest() {
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(savedUser));
		
		User returnedUser = userService.findById(id);
		
		Mockito.verify(userRepository).findById(id);
		Mockito.verify(userRules).validate(Optional.of(savedUser));
		
		Assertions.assertNotNull(returnedUser);
		Assertions.assertEquals(savedUser.getId(), returnedUser.getId());
	}
	
	@Test
	public void findByEmailTest() {
		Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(savedUser));
		
		User returnedUser = userService.findByEmail(email);
		
		Mockito.verify(userRepository).findByEmail(email);
		Mockito.verify(userRules).validate(Optional.of(savedUser));
		
		Assertions.assertNotNull(returnedUser);
		Assertions.assertEquals(savedUser.getEmail(), returnedUser.getEmail());
	}
	
	@Test
	public void autoLoginTest() {
		Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(savedUser));
		Mockito.when(jwtService.generateAcessToken(savedUser, login.getDeviceId())).thenReturn(token);
		
		String returnedtoken = userService.autoLogin(login);
		
		Mockito.verify(userRepository).findByEmail(email);
		Mockito.verify(userRules).validate(Optional.of(savedUser));
		Mockito.verify(loginRules).validateAutoLogin(login, savedUser);
		Mockito.verify(jwtService).generateAcessToken(savedUser, login.getDeviceId());
		
		Assertions.assertNotNull(returnedtoken);
		Assertions.assertFalse(returnedtoken.isBlank());
		Assertions.assertEquals(token, returnedtoken);
	}
	
	@Test
	public void loginTest() {
		Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(savedUser));
		Mockito.when(jwtService.generateAcessToken(savedUser, login.getDeviceId())).thenReturn(token);
		
		String returnedtoken = userService.login(login);
		
		Mockito.verify(userRepository, Mockito.times(2)).findByEmail(email);
		Mockito.verify(userRules, Mockito.times(2)).validate(Optional.of(savedUser));
		Mockito.verify(loginRules).validate(login, savedUser);
		Mockito.verify(loginRules).validate(login);
		Mockito.verify(jwtService).generateAcessToken(savedUser, login.getDeviceId());
		
		Assertions.assertNotNull(returnedtoken);
		Assertions.assertFalse(returnedtoken.isBlank());
		Assertions.assertEquals(token, returnedtoken);
	}
	
	@Test
	public void configureUserTest() {
		Mockito.when(jwtService.verifyAcessToken(token)).thenReturn(savedUser);
		Mockito.when(userBuilder.buildChanges(user, savedUser)).thenReturn(newUser);
		Mockito.when(userRepository.save(newUser)).thenReturn(newUser);
		
		User returnedUser = userService.configureUser(token, user);
		
		Mockito.verify(jwtService).verifyAcessToken(token);
		Mockito.verify(userBuilder).buildChanges(user, savedUser);
		Mockito.verify(userRepository).save(newUser);
		
		Assertions.assertNotNull(returnedUser);
		Assertions.assertEquals(user.getId(), returnedUser.getId());
	}
	
}
