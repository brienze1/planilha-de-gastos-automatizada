package br.com.planilha.gastos.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.DeviceEntity;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.entity.UserEntity;
import br.com.planilha.gastos.parse.UserIntegrationParse;
import br.com.planilha.gastos.repository.UserRepository;

@RunWith(SpringRunner.class)
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
	
	@Before
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
		
		Assert.assertNotNull(userResponse);
		Assert.assertTrue(userResponse.isPresent());
		Assert.assertEquals(id, userResponse.get().getId());
		Assert.assertEquals(user.getEmail(), userResponse.get().getEmail());
		Assert.assertEquals(user.getFirstName(), userResponse.get().getFirstName());
		Assert.assertEquals(user.getId(), userResponse.get().getId());
		Assert.assertEquals(user.inUseDeviceId(), userResponse.get().inUseDeviceId());
		Assert.assertEquals(user.getLastName(), userResponse.get().getLastName());
		Assert.assertEquals(user.getPassword(), userResponse.get().getPassword());
		Assert.assertEquals(user.getSecret(), userResponse.get().getSecret());
		Assert.assertEquals(user.inUseDevice(), userResponse.get().inUseDevice());
	}
	
	@Test
	public void findUserEntityTest() {
		Mockito.when(userRepository.findById(Integer.valueOf(id))).thenReturn(userEntityOptional);
		
		UserEntity userEntityResponse = userPersistence.findUserEntity(id);
		
		Assert.assertNotNull(userEntityResponse);
		Assert.assertEquals(id, String.valueOf(userEntityResponse.getId()));
		Assert.assertEquals(userEntity.getEmail(), userEntityResponse.getEmail());
		Assert.assertEquals(userEntity.getFirstName(), userEntityResponse.getFirstName());
		Assert.assertEquals(userEntity.getId(), userEntityResponse.getId());
		Assert.assertEquals(userEntity.getLastName(), userEntityResponse.getLastName());
		Assert.assertEquals(userEntity.getPassword(), userEntityResponse.getPassword());
		Assert.assertEquals(userEntity.getSecret(), userEntityResponse.getSecret());
		Assert.assertEquals(userEntity.isAutoLogin(), userEntityResponse.isAutoLogin());
		Assert.assertEquals(userEntity.isValidEmail(), userEntityResponse.isValidEmail());
	}
	
	@Test
	public void saveTest() {
		Mockito.when(userIntegrationParse.toUserEntity(user)).thenReturn(userEntity);
		Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);
		Mockito.when(userIntegrationParse.toUser(userEntity)).thenReturn(user);
		
		User userResponse = userPersistence.save(user);
		
		Assert.assertNotNull(userResponse);
		Assert.assertEquals(user.getEmail(), userResponse.getEmail());
		Assert.assertEquals(user.getFirstName(), userResponse.getFirstName());
		Assert.assertEquals(user.getId(), userResponse.getId());
		Assert.assertEquals(user.inUseDeviceId(), userResponse.inUseDeviceId());
		Assert.assertEquals(user.getLastName(), userResponse.getLastName());
		Assert.assertEquals(user.getPassword(), userResponse.getPassword());
		Assert.assertEquals(user.getSecret(), userResponse.getSecret());
		Assert.assertEquals(user.inUseDevice(), userResponse.inUseDevice());
	}
	
	@Test
	public void findByEmailTest() {
		Mockito.when(userRepository.findByEmail(email.toLowerCase())).thenReturn(userEntityOptional);
		Mockito.when(userIntegrationParse.toUser(userEntityOptional)).thenReturn(userOptional);
		
		Optional<User> userResponse = userPersistence.findByEmail(email);
		
		Assert.assertNotNull(userResponse);
		Assert.assertTrue(userResponse.isPresent());
		Assert.assertEquals(id, userResponse.get().getId());
		Assert.assertEquals(user.getEmail(), userResponse.get().getEmail());
		Assert.assertEquals(user.getFirstName(), userResponse.get().getFirstName());
		Assert.assertEquals(user.getId(), userResponse.get().getId());
		Assert.assertEquals(user.inUseDeviceId(), userResponse.get().inUseDeviceId());
		Assert.assertEquals(user.getLastName(), userResponse.get().getLastName());
		Assert.assertEquals(user.getPassword(), userResponse.get().getPassword());
		Assert.assertEquals(user.getSecret(), userResponse.get().getSecret());
		Assert.assertEquals(user.inUseDevice(), userResponse.get().inUseDevice());
	}
	
}
