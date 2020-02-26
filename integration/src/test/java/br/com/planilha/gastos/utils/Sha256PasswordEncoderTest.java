package br.com.planilha.gastos.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class Sha256PasswordEncoderTest {

	@InjectMocks
	private Sha256PasswordEncoder sha256PasswordEncoder;
	
	@Test
	public void test() {
		String encoded = sha256PasswordEncoder.encode("12345");
		
		Assert.assertFalse(encoded.equals("12345"));
	}
	
}
