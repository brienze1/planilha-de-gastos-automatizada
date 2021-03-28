package br.com.planilha.gastos.parse;

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

@ExtendWith(SpringExtension.class)
public class UserIntegrationParseTest {

	@InjectMocks
	private UserIntegrationParse userIntegrationParse;
	
	@Mock
	private DeviceIntegrationParse deviceIntegrationParse;
	
	private UserEntity userEntity;
	private User user;
	private Set<DeviceEntity> devicesEntity;
	private List<Device> devices;
	private List<UserEntity> userEntityList;
	
	@BeforeEach
	public void init() {
		devicesEntity = new HashSet<>();
		devicesEntity.add(new DeviceEntity());
		devices = new ArrayList<>();
		devices.add(new Device());
		devices.get(0).setDeviceId(UUID.randomUUID().toString());
		
		Mockito.when(deviceIntegrationParse.toDevices(devicesEntity)).thenReturn(devices);
		Mockito.when(deviceIntegrationParse.toDevicesEntity(devices)).thenReturn(devicesEntity);
		
		userEntityList = new ArrayList<>();
		for(int i=0; i<5; i++) {
			UserEntity userEntityNew = new UserEntity();
			userEntityNew.setAutoLogin(true);
			userEntityNew.setDevices(devicesEntity);
			userEntityNew.setEmail(UUID.randomUUID().toString());
			userEntityNew.setFirstName(UUID.randomUUID().toString());
			userEntityNew.setId(new Random().nextInt(100));
			userEntityNew.setLastName(UUID.randomUUID().toString());
			userEntityNew.setPassword(UUID.randomUUID().toString());
			userEntityNew.setSecret(UUID.randomUUID().toString());
			userEntityNew.setValidEmail(false);
			
			userEntityList.add(userEntityNew);
			userEntity = userEntityNew;
		}
		
		user = new User();
		user.setAutoLogin(true);
		user.setDevices(devices);
		user.setEmail(UUID.randomUUID().toString());
		user.setFirstName(UUID.randomUUID().toString());
		user.setId(String.valueOf(new Random().nextInt(100)));
		user.setInUseDevice(devices.get(0).getDeviceId());
		user.setLastName(UUID.randomUUID().toString());
		user.setPassword(UUID.randomUUID().toString());
		user.setSecret(UUID.randomUUID().toString());
		user.setValidEmail(false);
		
	}
	
	private void assertAll(UserEntity userEntity, User user) {
		Assertions.assertEquals(userEntity.getEmail(), user.getEmail());
		Assertions.assertEquals(userEntity.getFirstName(), user.getFirstName());
		Assertions.assertEquals(userEntity.getLastName(), user.getLastName());
		Assertions.assertEquals(userEntity.getPassword(), user.getPassword());
		Assertions.assertEquals(userEntity.getSecret(), user.getSecret());
		Assertions.assertEquals(String.valueOf(userEntity.getId()), user.getId());
		Assertions.assertFalse(userEntity.isValidEmail());
		Assertions.assertFalse(user.isValidEmail());
		Assertions.assertTrue(userEntity.isAutoLogin());
		Assertions.assertTrue(user.isAutoLogin());
		Assertions.assertNotNull(userEntity.getDevices());
		Assertions.assertNotNull(user.getDevices());
		Assertions.assertFalse(userEntity.getDevices().isEmpty());
		Assertions.assertFalse(user.getDevices().isEmpty());
	}
	
	private void assertAll(List<UserEntity> userEntityList, List<User> users) {
		for(int i=0; i<users.size(); i++) {
			assertAll(userEntityList.get(i), users.get(i));
		}
	}

	@Test
	public void toUserTest() {
		User user = userIntegrationParse.toUser(userEntity);
		
		assertAll(userEntity, user);
	}
	

	@Test
	public void toUserNullTest() {
		userEntity = null;
		
		User user = userIntegrationParse.toUser(userEntity);
		
		Assertions.assertNotNull(user);
	}
	
	@Test
	public void toUsersTest() {
		List<User> users = userIntegrationParse.toUsers(userEntityList);
		
		assertAll(userEntityList, users);
	}

	@Test
	public void toUsersNullTest() {
		userEntityList = null;
		
		List<User> users = userIntegrationParse.toUsers(userEntityList);
		
		Assertions.assertNotNull(users);
		Assertions.assertTrue(users.isEmpty());
	}
	
	@Test
	public void toUserOptionalTest() {
		Optional<User> user = userIntegrationParse.toUser(Optional.of(userEntity));
		
		Assertions.assertTrue(user.isPresent());
		assertAll(userEntity, user.get());
	}
	
	@Test
	public void toUserOptionalNullTest() {
		Optional<User> user = userIntegrationParse.toUser(Optional.ofNullable(null));
		
		Assertions.assertFalse(user.isPresent());
	}
	
	@Test
	public void toUserEntityTest() {
		UserEntity userEntity = userIntegrationParse.toUserEntity(user);
		
		assertAll(userEntity, user);
	}
	
	@Test
	public void toUserEntityIdStringTest() {
		user.setId(UUID.randomUUID().toString());
		
		UserEntity userEntity = userIntegrationParse.toUserEntity(user);
		
		Assertions.assertNotNull(userEntity);
	}
	
	@Test
	public void toUserEntityNullTest() {
		user = null;
		UserEntity userEntity = userIntegrationParse.toUserEntity(user);
		
		Assertions.assertNotNull(userEntity);
	}
	
}
