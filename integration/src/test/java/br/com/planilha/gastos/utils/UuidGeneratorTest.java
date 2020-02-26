package br.com.planilha.gastos.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UuidGeneratorTest {

	@InjectMocks
	private UuidGenerator uuidGenerator;
	
	@Test
	public void test() {
		String response = uuidGenerator.generate();
		
		Assert.assertNotNull(response);
		Assert.assertTrue(!response.isBlank());
		
	}
}
