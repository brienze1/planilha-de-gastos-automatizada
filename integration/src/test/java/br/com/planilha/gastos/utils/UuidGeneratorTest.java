package br.com.planilha.gastos.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.persistence.TransactionPersistence;

@RunWith(SpringRunner.class)
public class UuidGeneratorTest {

	@InjectMocks
	private IdGenerator uuidGenerator;
	
	@Mock
	private TransactionPersistence transactionPersistence;
	
	@Test
	public void generateIdTest() {
		String response = uuidGenerator.generateId();
		
		Assert.assertNotNull(response);
		Assert.assertTrue(!response.isBlank());
	}
	
	@Test
	public void generateSecretTest() {
		String response = uuidGenerator.generateSecret();
		
		Assert.assertNotNull(response);
		Assert.assertTrue(!response.isBlank());
	}
	
	@Test
	public void generateVerificationCodeTest() {
		String response = uuidGenerator.generateVerificationCode();
		
		Assert.assertNotNull(response);
		Assert.assertTrue(!response.isBlank());
	}
	
	@Test
	public void generateTransactionIdSucessTest() {
		Mockito.when(transactionPersistence.isValidId(Mockito.anyString())).thenReturn(true);
		
		String response = uuidGenerator.generateTransactionId();
		
		Assert.assertNotNull(response);
		Assert.assertTrue(!response.isBlank());
	}

}
