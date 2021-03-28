package br.com.planilha.gastos.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.persistence.TransactionPersistence;

@ExtendWith(SpringExtension.class)
public class IdGeneratorTest {

	@InjectMocks
	private IdGenerator idGenerator;
	
	@Mock
	private TransactionPersistence transactionPersistence;
	
	@Test
	public void generateIdTest() {
		String response = idGenerator.generateId();
		
		Assertions.assertNotNull(response);
		Assertions.assertTrue(!response.isBlank());
	}
	
	@Test
	public void generateSecretTest() {
		String response = idGenerator.generateSecret();
		
		Assertions.assertNotNull(response);
		Assertions.assertTrue(!response.isBlank());
	}
	
	@Test
	public void generateVerificationCodeTest() {
		String response = idGenerator.generateVerificationCode();
		
		Assertions.assertNotNull(response);
		Assertions.assertTrue(!response.isBlank());
	}
	
	@Test
	public void generateTransactionIdSucessTest() {
		Mockito.when(transactionPersistence.isValidId(Mockito.anyString())).thenReturn(true);
		
		String response = idGenerator.generateTransactionId();
		
		Assertions.assertNotNull(response);
		Assertions.assertTrue(!response.isBlank());
	}

}
