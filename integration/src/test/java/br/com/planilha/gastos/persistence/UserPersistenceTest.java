package br.com.planilha.gastos.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
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
import br.com.planilha.gastos.entity.DeviceEntity;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.entity.UserEntity;
import br.com.planilha.gastos.parse.UserIntegrationParse;
import br.com.planilha.gastos.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserPersistenceTest {

	@InjectMocks
	private UserPersistence userPersistence;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserIntegrationParse userIntegrationParse;
	
	private String id;
	private String email;
	private User user;
	private Optional<User> userOptional;
	private List<User> users;
	private UserEntity userEntity;
	private Optional<UserEntity> userEntityOptional;
	private Iterable<UserEntity> userEntityList;
	
	@BeforeEach
	public void init() {
		users = new ArrayList<>();
		userEntityList = new HashSet<>();
		
		for(int i=0; i<10; i++) {
			id = String.valueOf(new Random().nextInt(1000));
			email = UUID.randomUUID().toString();
			
			Device device = new Device();
			device.setDeviceId(UUID.randomUUID().toString());
			
			List<Device> devices = new ArrayList<>();
			devices.add(device);
			
			user = new User();
			user.setAutoLogin(true);
			user.setDevices(devices);
			user.setEmail(email);
			user.setFirstName(UUID.randomUUID().toString());
			user.setId(id);
			user.setInUseDevice(device.getDeviceId());
			user.setLastName(UUID.randomUUID().toString());
			user.setPassword(UUID.randomUUID().toString());
			user.setSecret(UUID.randomUUID().toString());
			user.setValidEmail(true);
			
			DeviceEntity deviceEntity = new DeviceEntity();
			deviceEntity.setDeviceId(device.getDeviceId());
			
			Set<DeviceEntity> devicesEntity = new HashSet<>();
			devicesEntity.add(deviceEntity);
			
			userEntity = new UserEntity();
			userEntity.setAutoLogin(true);
			userEntity.setDevices(devicesEntity);
			userEntity.setEmail(email);
			userEntity.setFirstName(user.getFirstName());
			userEntity.setId(Integer.valueOf(id));
			userEntity.setLastName(user.getLastName());
			userEntity.setPassword(user.getPassword());
			userEntity.setSecret(user.getSecret());
			userEntity.setValidEmail(true);
			
			users.add(user);
			((HashSet<UserEntity>) userEntityList).add(userEntity);
		}
		
		userOptional = Optional.of(user);
		
		userEntityOptional = Optional.of(userEntity);
		
	}
	
	@Test
	public void findByIdTest() {
		Mockito.when(userRepository.findById(Integer.valueOf(id))).thenReturn(userEntityOptional);
		Mockito.when(userIntegrationParse.toUser(userEntityOptional)).thenReturn(userOptional);
		
		Optional<User> userResponse = userPersistence.findById(id);
		
		Assertions.assertNotNull(userResponse);
		Assertions.assertTrue(userResponse.isPresent());
		Assertions.assertEquals(id, userResponse.get().getId());
		Assertions.assertEquals(user.getEmail(), userResponse.get().getEmail());
		Assertions.assertEquals(user.getFirstName(), userResponse.get().getFirstName());
		Assertions.assertEquals(user.getId(), userResponse.get().getId());
		Assertions.assertEquals(user.inUseDeviceId(), userResponse.get().inUseDeviceId());
		Assertions.assertEquals(user.getLastName(), userResponse.get().getLastName());
		Assertions.assertEquals(user.getPassword(), userResponse.get().getPassword());
		Assertions.assertEquals(user.getSecret(), userResponse.get().getSecret());
		Assertions.assertEquals(user.inUseDevice(), userResponse.get().inUseDevice());
	}
	
	@Test
	public void findUserEntityTest() {
		Mockito.when(userRepository.findById(Integer.valueOf(id))).thenReturn(userEntityOptional);
		
		UserEntity userEntityResponse = userPersistence.findUserEntity(id);
		
		Assertions.assertNotNull(userEntityResponse);
		Assertions.assertEquals(id, String.valueOf(userEntityResponse.getId()));
		Assertions.assertEquals(userEntity.getEmail(), userEntityResponse.getEmail());
		Assertions.assertEquals(userEntity.getFirstName(), userEntityResponse.getFirstName());
		Assertions.assertEquals(userEntity.getId(), userEntityResponse.getId());
		Assertions.assertEquals(userEntity.getLastName(), userEntityResponse.getLastName());
		Assertions.assertEquals(userEntity.getPassword(), userEntityResponse.getPassword());
		Assertions.assertEquals(userEntity.getSecret(), userEntityResponse.getSecret());
		Assertions.assertEquals(userEntity.isAutoLogin(), userEntityResponse.isAutoLogin());
		Assertions.assertEquals(userEntity.isValidEmail(), userEntityResponse.isValidEmail());
	}
	
	@Test
	public void saveTest() {
		Mockito.when(userIntegrationParse.toUserEntity(user)).thenReturn(userEntity);
		Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);
		Mockito.when(userIntegrationParse.toUser(userEntity)).thenReturn(user);
		
		User userResponse = userPersistence.save(user);
		
		Assertions.assertNotNull(userResponse);
		Assertions.assertEquals(user.getEmail(), userResponse.getEmail());
		Assertions.assertEquals(user.getFirstName(), userResponse.getFirstName());
		Assertions.assertEquals(user.getId(), userResponse.getId());
		Assertions.assertEquals(user.inUseDeviceId(), userResponse.inUseDeviceId());
		Assertions.assertEquals(user.getLastName(), userResponse.getLastName());
		Assertions.assertEquals(user.getPassword(), userResponse.getPassword());
		Assertions.assertEquals(user.getSecret(), userResponse.getSecret());
		Assertions.assertEquals(user.inUseDevice(), userResponse.inUseDevice());
	}
	
	@Test
	public void findByEmailTest() {
		Mockito.when(userRepository.findByEmail(email.toLowerCase())).thenReturn(userEntityOptional);
		Mockito.when(userIntegrationParse.toUser(userEntityOptional)).thenReturn(userOptional);
		
		Optional<User> userResponse = userPersistence.findByEmail(email);
		
		Assertions.assertNotNull(userResponse);
		Assertions.assertTrue(userResponse.isPresent());
		Assertions.assertEquals(id, userResponse.get().getId());
		Assertions.assertEquals(user.getEmail(), userResponse.get().getEmail());
		Assertions.assertEquals(user.getFirstName(), userResponse.get().getFirstName());
		Assertions.assertEquals(user.getId(), userResponse.get().getId());
		Assertions.assertEquals(user.inUseDeviceId(), userResponse.get().inUseDeviceId());
		Assertions.assertEquals(user.getLastName(), userResponse.get().getLastName());
		Assertions.assertEquals(user.getPassword(), userResponse.get().getPassword());
		Assertions.assertEquals(user.getSecret(), userResponse.get().getSecret());
		Assertions.assertEquals(user.inUseDevice(), userResponse.get().inUseDevice());
	}
	
}
