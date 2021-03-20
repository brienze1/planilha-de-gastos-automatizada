package br.com.planilha.gastos.parse;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.entity.Login;

@RunWith(SpringRunner.class)
public class LoginDeliveryParseTest {

	@InjectMocks
	private LoginDeliveryParse loginDeliveryParse;
	
	private LoginDto loginDto;	
	
	@Before
	public void init() {
		loginDto = new LoginDto();
		loginDto.setDeviceId(UUID.randomUUID().toString());
		loginDto.setEmail(UUID.randomUUID().toString());
		loginDto.setPassword(UUID.randomUUID().toString());
	}
	
	@Test()
	public void toLoginTest() {
		Login login = loginDeliveryParse.toLogin(loginDto);
		
		Assert.assertNotNull(login);
		Assert.assertEquals(loginDto.getDeviceId(), login.getDeviceId());
		Assert.assertEquals(loginDto.getEmail(), login.getEmail());
		Assert.assertEquals(loginDto.getPassword(), login.getPassword());
	}
	
	@Test()
	public void toLoginNullTest() {
		Login login = loginDeliveryParse.toLogin(null);
		
		Assert.assertNotNull(login);
		Assert.assertNull(login.getDeviceId());
		Assert.assertNull(login.getEmail());
		Assert.assertNull(login.getPassword());
	}
	
}
