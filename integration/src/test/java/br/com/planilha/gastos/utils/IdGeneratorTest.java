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
public class IdGeneratorTest {

	@InjectMocks
	private IdGenerator idGenerator;
	
	@Mock
	private TransactionPersistence transactionPersistence;
	
	@Test
	public void generateIdTest() {
		String response = idGenerator.generateId();
		
		Assert.assertNotNull(response);
		Assert.assertTrue(!response.isBlank());
	}
	
	@Test
	public void generateSecretTest() {
		String response = idGenerator.generateSecret();
		
		Assert.assertNotNull(response);
		Assert.assertTrue(!response.isBlank());
	}
	
	@Test
	public void generateVerificationCodeTest() {
		String response = idGenerator.generateVerificationCode();
		
		Assert.assertNotNull(response);
		Assert.assertTrue(!response.isBlank());
	}
	
	@Test
	public void generateTransactionIdSucessTest() {
		Mockito.when(transactionPersistence.isValidId(Mockito.anyString())).thenReturn(true);
		
		String response = idGenerator.generateTransactionId();
		
		Assert.assertNotNull(response);
		Assert.assertTrue(!response.isBlank());
	}

}
