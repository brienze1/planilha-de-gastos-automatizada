package br.com.planilha.gastos.utils;

import java.util.UUID;

import javax.crypto.SecretKeyFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PasswordUtilsTest {

	@InjectMocks
	private PasswordUtils passwordUtils;
	
	@Mock
	private SecretKeyFactory skf;
	
	private String password;
	private String secret;
	
	@BeforeEach
	public void init() {
		password = UUID.randomUUID().toString();
		secret = UUID.randomUUID().toString();
	}
	
	@Test
	public void encodeTest() {
		String encoded = passwordUtils.encode(password, secret);
		
		Assertions.assertNotNull(encoded);
		Assertions.assertTrue(encoded.length()>0);
	}
	
	@Test
	public void verifyPasswordTest() {
		String encoded = passwordUtils.encode(password, secret);
		
		Assertions.assertNotNull(encoded);
		Assertions.assertTrue(encoded.length()>0);
		
		Boolean verify = passwordUtils.verifyPassword(password, encoded, secret);
		
		Assertions.assertNotNull(verify);
		Assertions.assertTrue(verify);
	}
	
	@Test
	public void verifyWrongPasswordTest() {
		String encoded = passwordUtils.encode(password, secret);
		
		Assertions.assertNotNull(encoded);
		Assertions.assertTrue(encoded.length()>0);
		
		Boolean verify = passwordUtils.verifyPassword(UUID.randomUUID().toString(), encoded, secret);
		
		Assertions.assertNotNull(verify);
		Assertions.assertFalse(verify);
	}
	
}
