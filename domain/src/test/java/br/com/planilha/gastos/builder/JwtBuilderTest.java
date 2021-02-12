package br.com.planilha.gastos.builder;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;

@RunWith(SpringRunner.class)
public class JwtBuilderTest {

	@InjectMocks
	private JwtBuilder jwtBuilder;
	
	private Device device;
	private User user;
	
	@Before
	public void init() {
		device = new Device();
		device.setDeviceId(UUID.randomUUID().toString());
		device.setId(String.valueOf(new Random().nextInt(1000)));
		device.setInUse(true);
		device.setVerificationCode(UUID.randomUUID().toString());
		device.setVerified(true);
		
		user = new User();
		user.setDevices(new ArrayList<>());
		user.getDevices().add(device);
		user.setInUseDevice(device.getDeviceId());
		user.setAutoLogin(true);
		user.setEmail(UUID.randomUUID().toString());
		user.setFirstName(UUID.randomUUID().toString());
		user.setId(String.valueOf(new Random().nextInt(1000)));
		user.setLastName(UUID.randomUUID().toString());
		user.setPassword(UUID.randomUUID().toString());
		user.setSecret(UUID.randomUUID().toString());
		user.setValidEmail(true);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void Test() {
		Map<String, Object> jwt = jwtBuilder.build(user);
		
		Assert.assertNotNull(jwt);
		Assert.assertEquals(device.getDeviceId(), ((Map<String, String>) jwt.get("device")).get("device_id"));
		Assert.assertEquals(device.getVerificationCode(), ((Map<String, String>) jwt.get("device")).get("verification_code"));
		Assert.assertEquals(device.isInUse(), ((Map<String, String>) jwt.get("device")).get("in_use"));
		Assert.assertEquals(device.isVerified(), ((Map<String, String>) jwt.get("device")).get("verified"));
		Assert.assertEquals(user.getEmail(), jwt.get("email"));
		Assert.assertEquals(user.getFirstName(), jwt.get("first_name"));
		Assert.assertEquals(user.getId(), jwt.get("id"));
		Assert.assertEquals(user.getLastName(), jwt.get("last_name"));
		Assert.assertEquals(user.getPassword(), jwt.get("password"));
		Assert.assertEquals(user.getSecret(), jwt.get("secret"));
		Assert.assertEquals(user.isAutoLogin(), jwt.get("auto_login"));
		Assert.assertEquals(user.isValidEmail(), jwt.get("valid_email"));
	}
	
}
