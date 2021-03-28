package br.com.planilha.gastos.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.UserValidationException;
import br.com.planilha.gastos.port.IdGeneratorAdapter;
import br.com.planilha.gastos.port.PasswordUtilsAdapter;

@ExtendWith(SpringExtension.class)
public class UserBuilderTest {

	@InjectMocks
	private UserBuilder userBuilder;
	
	@Mock
	private PasswordUtilsAdapter passwordUtils;
	
	@Mock
	private IdGeneratorAdapter idGenerator;
	
	@Mock
	private DeviceBuilder deviceBuilder;
	
	private User user;
	private User userSalvo;
	private Device device;
	private List<Device> devices;
	private String secret;
	private String password1;
	private String password2;
	private String firstName1;
	private String firstName2;
	private String lastName1;
	private String lastName2;
	
	@BeforeEach
	public void init() {
		secret = UUID.randomUUID().toString();
		password1 = UUID.randomUUID().toString();
		password2 = UUID.randomUUID().toString();
		firstName1 = UUID.randomUUID().toString();
		firstName2= UUID.randomUUID().toString();
		lastName1 = UUID.randomUUID().toString();
		lastName2 = UUID.randomUUID().toString();
		
		device = new Device();
		device.setDeviceId(UUID.randomUUID().toString());
		
		devices = new ArrayList<>();	
		devices.add(device);
		
		user = new User();
		user.setDevices(devices);
		user.setFirstName(firstName1);
		user.setLastName(lastName1);
		user.setPassword(password1);
		user.setAutoLogin(false);
		
		userSalvo = new User();
		userSalvo.setSecret(secret);
		userSalvo.setFirstName(firstName1);
		userSalvo.setLastName(lastName1);
		userSalvo.setPassword(password1);
		user.setAutoLogin(false);
	}
	
	@Test
	public void buildTest() {
		Mockito.when(idGenerator.generateSecret()).thenReturn(secret);
		Mockito.when(passwordUtils.encode(user.getPassword(), secret)).thenReturn(UUID.randomUUID().toString());
		Mockito.when(deviceBuilder.build(device.getDeviceId())).thenReturn(device);
		
		User newUser = userBuilder.build(user);
		
		Assertions.assertNotNull(newUser);
		Assertions.assertEquals(devices.get(0).getDeviceId(), newUser.inUseDeviceId());
		Assertions.assertEquals(user.getPassword(), newUser.getPassword());
	}
	
	@Test
	public void buildChangesErrorUserNullTest() {
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userBuilder.buildChanges(null, userSalvo), 
				"Usuario nao pode ser nulo");
	}
	
	@Test
	public void buildChangesErroUserSalvoNullTest() {
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> userBuilder.buildChanges(user, null), 
				"Usuario nao pode ser nulo");
	}
	
	@Test
	public void buildChangesFirstNameTest() {
		Mockito.when(passwordUtils.encode(password1, secret)).thenReturn(password1);
		
		user.setFirstName(firstName2);
		
		User userResponse = userBuilder.buildChanges(user, userSalvo);
		
		Assertions.assertEquals(firstName2, userResponse.getFirstName());
		Assertions.assertEquals(lastName1, userResponse.getLastName());
		Assertions.assertEquals(password1, userResponse.getPassword());
		Assertions.assertEquals(secret, userResponse.getSecret());
		Assertions.assertEquals(false, userResponse.isAutoLogin());
	}
	
	@Test
	public void buildChangesFirstNameNullTest() {
		Mockito.when(passwordUtils.encode(password1, secret)).thenReturn(password1);
		
		user.setFirstName(null);
		
		User userResponse = userBuilder.buildChanges(user, userSalvo);
		
		Assertions.assertEquals(firstName1, userResponse.getFirstName());
		Assertions.assertEquals(lastName1, userResponse.getLastName());
		Assertions.assertEquals(password1, userResponse.getPassword());
		Assertions.assertEquals(secret, userResponse.getSecret());
		Assertions.assertEquals(false, userResponse.isAutoLogin());
	}
	
	@Test
	public void buildChangesFirstNameBlankTest() {
		Mockito.when(passwordUtils.encode(password1, secret)).thenReturn(password1);
		
		user.setFirstName(" ");
		
		User userResponse = userBuilder.buildChanges(user, userSalvo);
		
		Assertions.assertEquals(firstName1, userResponse.getFirstName());
		Assertions.assertEquals(lastName1, userResponse.getLastName());
		Assertions.assertEquals(password1, userResponse.getPassword());
		Assertions.assertEquals(secret, userResponse.getSecret());
		Assertions.assertEquals(false, userResponse.isAutoLogin());
	}
	
	@Test
	public void buildChangesLastNameTest() {
		Mockito.when(passwordUtils.encode(password1, secret)).thenReturn(password1);
		
		user.setLastName(lastName2);
		
		User userResponse = userBuilder.buildChanges(user, userSalvo);
		
		Assertions.assertEquals(firstName1, userResponse.getFirstName());
		Assertions.assertEquals(lastName2, userResponse.getLastName());
		Assertions.assertEquals(password1, userResponse.getPassword());
		Assertions.assertEquals(secret, userResponse.getSecret());
		Assertions.assertEquals(false, userResponse.isAutoLogin());
	}
	
	@Test
	public void buildChangesLastNameNullTest() {
		Mockito.when(passwordUtils.encode(password1, secret)).thenReturn(password1);
		
		user.setLastName(null);
		
		User userResponse = userBuilder.buildChanges(user, userSalvo);
		
		Assertions.assertEquals(firstName1, userResponse.getFirstName());
		Assertions.assertEquals(lastName1, userResponse.getLastName());
		Assertions.assertEquals(password1, userResponse.getPassword());
		Assertions.assertEquals(secret, userResponse.getSecret());
		Assertions.assertEquals(false, userResponse.isAutoLogin());
	}
	
	@Test
	public void buildChangesLastNameBlankTest() {
		Mockito.when(passwordUtils.encode(password1, secret)).thenReturn(password1);
		
		user.setLastName(" ");
		
		User userResponse = userBuilder.buildChanges(user, userSalvo);
		
		Assertions.assertEquals(firstName1, userResponse.getFirstName());
		Assertions.assertEquals(lastName1, userResponse.getLastName());
		Assertions.assertEquals(password1, userResponse.getPassword());
		Assertions.assertEquals(secret, userResponse.getSecret());
		Assertions.assertEquals(false, userResponse.isAutoLogin());
	}
	
	@Test
	public void buildChangesPasswordTest() {
		Mockito.when(passwordUtils.encode(password2, secret)).thenReturn(password2);
		
		user.setPassword(password2);
		
		User userResponse = userBuilder.buildChanges(user, userSalvo);
		
		Assertions.assertEquals(firstName1, userResponse.getFirstName());
		Assertions.assertEquals(lastName1, userResponse.getLastName());
		Assertions.assertEquals(password2, userResponse.getPassword());
		Assertions.assertEquals(secret, userResponse.getSecret());
		Assertions.assertEquals(false, userResponse.isAutoLogin());
	}
	
	@Test
	public void buildChangesPasswordNullTest() {
		user.setPassword(null);
		
		User userResponse = userBuilder.buildChanges(user, userSalvo);
		
		Assertions.assertEquals(firstName1, userResponse.getFirstName());
		Assertions.assertEquals(lastName1, userResponse.getLastName());
		Assertions.assertEquals(password1, userResponse.getPassword());
		Assertions.assertEquals(secret, userResponse.getSecret());
		Assertions.assertEquals(false, userResponse.isAutoLogin());
	}
	
	@Test
	public void buildChangesPasswordBlankTest() {
		user.setPassword(" ");
		
		User userResponse = userBuilder.buildChanges(user, userSalvo);
		
		Assertions.assertEquals(firstName1, userResponse.getFirstName());
		Assertions.assertEquals(lastName1, userResponse.getLastName());
		Assertions.assertEquals(password1, userResponse.getPassword());
		Assertions.assertEquals(secret, userResponse.getSecret());
		Assertions.assertEquals(false, userResponse.isAutoLogin());
	}
	
	@Test
	public void buildChangesAutoLoginTest() {
		Mockito.when(passwordUtils.encode(password1, secret)).thenReturn(password1);
		
		user.setAutoLogin(true);
		
		User userResponse = userBuilder.buildChanges(user, userSalvo);
		
		Assertions.assertEquals(firstName1, userResponse.getFirstName());
		Assertions.assertEquals(lastName1, userResponse.getLastName());
		Assertions.assertEquals(password1, userResponse.getPassword());
		Assertions.assertEquals(secret, userResponse.getSecret());
		Assertions.assertEquals(true, userResponse.isAutoLogin());
	}
	
}
