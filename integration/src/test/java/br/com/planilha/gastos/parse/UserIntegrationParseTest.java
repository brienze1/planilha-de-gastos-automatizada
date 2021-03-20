package br.com.planilha.gastos.parse;

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

@RunWith(SpringRunner.class)
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
	
	@Before
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
		Assert.assertEquals(userEntity.getEmail(), user.getEmail());
		Assert.assertEquals(userEntity.getFirstName(), user.getFirstName());
		Assert.assertEquals(userEntity.getLastName(), user.getLastName());
		Assert.assertEquals(userEntity.getPassword(), user.getPassword());
		Assert.assertEquals(userEntity.getSecret(), user.getSecret());
		Assert.assertEquals(String.valueOf(userEntity.getId()), user.getId());
		Assert.assertFalse(userEntity.isValidEmail());
		Assert.assertFalse(user.isValidEmail());
		Assert.assertTrue(userEntity.isAutoLogin());
		Assert.assertTrue(user.isAutoLogin());
		Assert.assertNotNull(userEntity.getDevices());
		Assert.assertNotNull(user.getDevices());
		Assert.assertFalse(userEntity.getDevices().isEmpty());
		Assert.assertFalse(user.getDevices().isEmpty());
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
		
		Assert.assertNotNull(user);
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
		
		Assert.assertNotNull(users);
		Assert.assertTrue(users.isEmpty());
	}
	
	@Test
	public void toUserOptionalTest() {
		Optional<User> user = userIntegrationParse.toUser(Optional.of(userEntity));
		
		Assert.assertTrue(user.isPresent());
		assertAll(userEntity, user.get());
	}
	
	@Test
	public void toUserOptionalNullTest() {
		Optional<User> user = userIntegrationParse.toUser(Optional.ofNullable(null));
		
		Assert.assertFalse(user.isPresent());
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
		
		Assert.assertNotNull(userEntity);
	}
	
	@Test
	public void toUserEntityNullTest() {
		user = null;
		UserEntity userEntity = userIntegrationParse.toUserEntity(user);
		
		Assert.assertNotNull(userEntity);
	}
	
}
