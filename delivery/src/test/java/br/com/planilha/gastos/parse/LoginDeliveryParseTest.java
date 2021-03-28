package br.com.planilha.gastos.parse;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.entity.Login;

@ExtendWith(SpringExtension.class)
public class LoginDeliveryParseTest {

	@InjectMocks
	private LoginDeliveryParse loginDeliveryParse;
	
	private LoginDto loginDto;	
	
	@BeforeEach
	public void init() {
		loginDto = new LoginDto();
		loginDto.setDeviceId(UUID.randomUUID().toString());
		loginDto.setEmail(UUID.randomUUID().toString());
		loginDto.setPassword(UUID.randomUUID().toString());
	}
	
	@Test
	public void toLoginTest() {
		Login login = loginDeliveryParse.toLogin(loginDto);
		
		Assertions.assertNotNull(login);
		Assertions.assertEquals(loginDto.getDeviceId(), login.getDeviceId());
		Assertions.assertEquals(loginDto.getEmail(), login.getEmail());
		Assertions.assertEquals(loginDto.getPassword(), login.getPassword());
	}
	
	@Test
	public void toLoginNullTest() {
		Login login = loginDeliveryParse.toLogin(null);
		
		Assertions.assertNotNull(login);
		Assertions.assertNull(login.getDeviceId());
		Assertions.assertNull(login.getEmail());
		Assertions.assertNull(login.getPassword());
	}
	
}
