package br.com.planilha.gastos.rules;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.exception.TransactionException;
import br.com.planilha.gastos.port.IdGeneratorAdapter;
import br.com.planilha.gastos.port.TransactionPersistenceAdapter;

@ExtendWith(SpringExtension.class)
public class TransactionRulesTest {

	@InjectMocks
	private TransactionRules transactionRules;
	
	@Mock
	private TransactionPersistenceAdapter transactionPersistence;
	
	@Mock
	private IdGeneratorAdapter idGenerator;
	
	private Transaction transaction;
	
	@BeforeEach
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
		
		Assertions.assertTrue(isValid);
	}
	
	@Test
	public void validateInvalidIdTest() {
		Mockito.when(transactionPersistence.isValidId(transaction.getId())).thenReturn(false);
		
		Boolean isValid = transactionRules.validate(transaction);
		
		Assertions.assertTrue(isValid);
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
		
		Assertions.assertTrue(isValid);
		Assertions.assertEquals("Undefined", transaction.getDescricao());
		Assertions.assertEquals("Unknown", transaction.getLocalizacao());
		Assertions.assertEquals("Unknown", transaction.getMeioDePagamento());
		Assertions.assertEquals("Sent", transaction.getTipo());
		Assertions.assertEquals(id, transaction.getId());
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
		
		Assertions.assertTrue(isValid);
		Assertions.assertEquals("Undefined", transaction.getDescricao());
		Assertions.assertEquals("Unknown", transaction.getLocalizacao());
		Assertions.assertEquals("Unknown", transaction.getMeioDePagamento());
		Assertions.assertEquals("Sent", transaction.getTipo());
		Assertions.assertEquals(id, transaction.getId());
	}
	
	@Test
	public void validateTransactionNullTest() {
		Assertions.assertThrows(
				TransactionException.class, 
				() -> transactionRules.validate(null), 
				"Transaction can't be null");
	}
	
	@Test
	public void validateValorNullTest() {
		transaction.setValor(null);
		
		Assertions.assertThrows(
				TransactionException.class, 
				() -> transactionRules.validate(transaction), 
				"Value can't be null, zero or less than zero");
	}
	
	@Test
	public void validateValorLessThenZeroTest() {
		transaction.setValor(new BigDecimal(-1));
		
		Assertions.assertThrows(
				TransactionException.class, 
				() -> transactionRules.validate(transaction), 
				"Value can't be null, zero or less than zero");
	}
	
}
