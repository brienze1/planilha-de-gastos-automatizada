package br.com.planilha.gastos.builder;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;

@ExtendWith(SpringExtension.class)
public class JwtBuilderTest {

	@InjectMocks
	private JwtBuilder jwtBuilder;
	
	private Device device;
	private User user;
	
	@BeforeEach
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
		
		Assertions.assertNotNull(jwt);
		Assertions.assertEquals(device.getDeviceId(), ((Map<String, String>) jwt.get("device")).get("device_id"));
		Assertions.assertEquals(device.getVerificationCode(), ((Map<String, String>) jwt.get("device")).get("verification_code"));
		Assertions.assertEquals(device.isInUse(), ((Map<String, String>) jwt.get("device")).get("in_use"));
		Assertions.assertEquals(device.isVerified(), ((Map<String, String>) jwt.get("device")).get("verified"));
		Assertions.assertEquals(user.getEmail(), jwt.get("email"));
		Assertions.assertEquals(user.getFirstName(), jwt.get("first_name"));
		Assertions.assertEquals(user.getId(), jwt.get("id"));
		Assertions.assertEquals(user.getLastName(), jwt.get("last_name"));
		Assertions.assertEquals(user.getPassword(), jwt.get("password"));
		Assertions.assertEquals(user.getSecret(), jwt.get("secret"));
		Assertions.assertEquals(user.isAutoLogin(), jwt.get("auto_login"));
		Assertions.assertEquals(user.isValidEmail(), jwt.get("valid_email"));
	}
	
}
