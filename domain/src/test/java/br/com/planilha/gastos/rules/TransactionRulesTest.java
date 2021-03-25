package br.com.planilha.gastos.rules;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.exception.TransactionException;
import br.com.planilha.gastos.port.IdGeneratorAdapter;
import br.com.planilha.gastos.port.TransactionPersistenceAdapter;

@RunWith(SpringRunner.class)
public class TransactionRulesTest {

	@InjectMocks
	private TransactionRules transactionRules;
	
	@Mock
	private TransactionPersistenceAdapter transactionPersistence;
	
	@Mock
	private IdGeneratorAdapter idGenerator;
	
	private Transaction transaction;
	
	@Before
	public void init() {
		transaction = new Transaction();
		transaction.setId(UUID.randomUUID().toString());
		transaction.setValor(new BigDecimal(1.99));
		transaction.setMeioDePagamento("Cartao");
		transaction.setLocalizacao("Mercado");
		transaction.setTipo("Sent");
		transaction.setDescricao("Compras do mes");
		transaction.setData(LocalDateTime.now());

		Mockito.when(transactionPersistence.isValidId(transaction.getId())).thenReturn(true);
	}
	
	@Test
	public void validateTest() {
		Boolean isValid = transactionRules.validate(transaction);
		
		Assert.assertTrue(isValid);
	}
	
	@Test
	public void validateInvalidIdTest() {
		Mockito.when(transactionPersistence.isValidId(transaction.getId())).thenReturn(false);
		
		Boolean isValid = transactionRules.validate(transaction);
		
		Assert.assertTrue(isValid);
	}
	
	@Test
	public void validateNullFieldsTest() {
		String id = UUID.randomUUID().toString();
		
		Mockito.when(idGenerator.generateTransactionId()).thenReturn(id);
		
		transaction.setData(null);
		transaction.setId(null);
		transaction.setDescricao(null);
		transaction.setLocalizacao(null);
		transaction.setMeioDePagamento(null);
		transaction.setTipo(null);
		
		Boolean isValid = transactionRules.validate(transaction);
		
		Assert.assertTrue(isValid);
		Assert.assertEquals("Undefined", transaction.getDescricao());
		Assert.assertEquals("Unknown", transaction.getLocalizacao());
		Assert.assertEquals("Unknown", transaction.getMeioDePagamento());
		Assert.assertEquals("Sent", transaction.getTipo());
		Assert.assertEquals(id, transaction.getId());
	}
	
	@Test
	public void validateEmptyFieldsTest() {
		String id = UUID.randomUUID().toString();
		
		Mockito.when(idGenerator.generateTransactionId()).thenReturn(id);
		
		transaction.setData(null);
		transaction.setDescricao(" ");
		transaction.setLocalizacao(" ");
		transaction.setMeioDePagamento(" ");
		transaction.setTipo(" ");
		transaction.setId(" ");
		
		Boolean isValid = transactionRules.validate(transaction);
		
		Assert.assertTrue(isValid);
		Assert.assertEquals("Undefined", transaction.getDescricao());
		Assert.assertEquals("Unknown", transaction.getLocalizacao());
		Assert.assertEquals("Unknown", transaction.getMeioDePagamento());
		Assert.assertEquals("Sent", transaction.getTipo());
		Assert.assertEquals(id, transaction.getId());
	}
	
	@Test(expected = TransactionException.class)
	public void validateTransactionNullTest() {
		try {
			transactionRules.validate(null);
		} catch (TransactionException e) {
			Assert.assertEquals("Transaction can't be null", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = TransactionException.class)
	public void validateValorNullTest() {
		transaction.setValor(null);
		
		try {
			transactionRules.validate(transaction);
		} catch (TransactionException e) {
			Assert.assertEquals("Value can't be null, zero or less than zero", e.getMessage());
			
			throw e;
		}
	}
	
	@Test(expected = TransactionException.class)
	public void validateValorLessThenZeroTest() {
		transaction.setValor(new BigDecimal(-1));
		
		try {
			transactionRules.validate(transaction);
		} catch (TransactionException e) {
			Assert.assertEquals("Value can't be null, zero or less than zero", e.getMessage());
			
			throw e;
		}
	}
	
}
