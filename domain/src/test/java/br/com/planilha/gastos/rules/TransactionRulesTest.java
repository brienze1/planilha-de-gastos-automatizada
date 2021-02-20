package br.com.planilha.gastos.rules;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.exception.TransactionException;

@RunWith(SpringRunner.class)
public class TransactionRulesTest {

	@InjectMocks
	private TransactionRules transactionRules;
	
	private Transaction transaction;
	
	@Before
	public void init() {
		transaction = new Transaction();
		transaction.setValor(new BigDecimal(1.99));
		transaction.setMeioDePagamento("Cartao");
		transaction.setLocalizacao("Mercado");
		transaction.setTipo("Sent");
		transaction.setDescricao("Compras do mes");
		transaction.setData(LocalDateTime.now());
	}
	
	@Test
	public void validateTest() {
		Boolean isValid = transactionRules.validate(transaction);
		
		Assert.assertTrue(isValid);
	}
	
	@Test
	public void validateNullFieldsTest() {
		transaction.setData(null);
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
	}
	
	@Test
	public void validateEmptyFieldsTest() {
		transaction.setData(null);
		transaction.setDescricao(" ");
		transaction.setLocalizacao(" ");
		transaction.setMeioDePagamento(" ");
		transaction.setTipo(" ");
		
		Boolean isValid = transactionRules.validate(transaction);
		
		Assert.assertTrue(isValid);
		Assert.assertEquals("Undefined", transaction.getDescricao());
		Assert.assertEquals("Unknown", transaction.getLocalizacao());
		Assert.assertEquals("Unknown", transaction.getMeioDePagamento());
		Assert.assertEquals("Sent", transaction.getTipo());
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
