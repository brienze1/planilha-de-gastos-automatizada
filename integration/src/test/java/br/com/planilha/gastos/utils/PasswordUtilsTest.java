package br.com.planilha.gastos.utils;

import java.util.UUID;

import javax.crypto.SecretKeyFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PasswordUtilsTest {

	@InjectMocks
	private PasswordUtils passwordUtils;
	
	@Mock
	private SecretKeyFactory skf;
	
	private String password;
	private String secret;
	
	@Before
	public void init() {
		password = UUID.randomUUID().toString();
		secret = UUID.randomUUID().toString();
	}
	
	@Test
	public void encodeTest() {
		String encoded = passwordUtils.encode(password, secret);
		
		Assert.assertNotNull(encoded);
		Assert.assertTrue(encoded.length()>0);
	}
	
	@Test
	public void verifyPasswordTest() {
		String encoded = passwordUtils.encode(password, secret);
		
		Assert.assertNotNull(encoded);
		Assert.assertTrue(encoded.length()>0);
		
		Boolean verify = passwordUtils.verifyPassword(password, encoded, secret);
		
		Assert.assertNotNull(verify);
		Assert.assertTrue(verify);
	}
	
	@Test
	public void verifyWrongPasswordTest() {
		String encoded = passwordUtils.encode(password, secret);
		
		Assert.assertNotNull(encoded);
		Assert.assertTrue(encoded.length()>0);
		
		Boolean verify = passwordUtils.verifyPassword(UUID.randomUUID().toString(), encoded, secret);
		
		Assert.assertNotNull(verify);
		Assert.assertFalse(verify);
	}
	
}
