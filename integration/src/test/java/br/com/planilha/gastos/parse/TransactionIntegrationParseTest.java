package br.com.planilha.gastos.parse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.entity.TransactionEntity;

@RunWith(SpringRunner.class)
public class TransactionIntegrationParseTest {

	@InjectMocks
	private TransactionIntegrationParse transactionIntegrationParse;
	
	private Transaction transaction;
	private TransactionEntity transactionEntity;
	private List<TransactionEntity> transactionsEntity;
	
	@Before
	public void init() {
		transaction = new Transaction();
		transaction.setData(LocalDateTime.now());
		transaction.setDescricao(UUID.randomUUID().toString());
		transaction.setId(String.valueOf(new Random().nextInt(100)));
		transaction.setLocalizacao(UUID.randomUUID().toString());
		transaction.setMeioDePagamento(UUID.randomUUID().toString());
		transaction.setTipo(UUID.randomUUID().toString());
		transaction.setValor(BigDecimal.valueOf(new Random().nextInt(200)));
		
		transactionsEntity = new ArrayList<>();
		for(int i=0; i<5; i++) {
			TransactionEntity newTransactionEntity = new TransactionEntity();
			newTransactionEntity.setData(LocalDateTime.now());
			newTransactionEntity.setDescricao(UUID.randomUUID().toString());
			newTransactionEntity.setId(UUID.randomUUID().toString());
			newTransactionEntity.setLocalizacao(UUID.randomUUID().toString());
			newTransactionEntity.setMeioDePagamento(UUID.randomUUID().toString());
			newTransactionEntity.setTipo(UUID.randomUUID().toString());
			newTransactionEntity.setValor(BigDecimal.valueOf(new Random().nextInt(200)));
			
			transactionsEntity.add(newTransactionEntity);
			transactionEntity = newTransactionEntity;
		}
	}
	
	private void assertAll(Transaction transaction, TransactionEntity transactionEntity) {
		Assert.assertNotNull(transaction);
		Assert.assertNotNull(transactionEntity);
		Assert.assertEquals(transaction.getDescricao(), transactionEntity.getDescricao());
		Assert.assertEquals(transaction.getId(), String.valueOf(transactionEntity.getId()));
		Assert.assertEquals(transaction.getLocalizacao(), transactionEntity.getLocalizacao());
		Assert.assertEquals(transaction.getMeioDePagamento(), transactionEntity.getMeioDePagamento());
		Assert.assertEquals(transaction.getTipo(), transactionEntity.getTipo());
		Assert.assertEquals(transaction.getData(), transactionEntity.getData());
		Assert.assertEquals(transaction.getValor(), transactionEntity.getValor());
	}
	
	@Test
	public void toTransactionEntityTest() {
		TransactionEntity transactionEntity = transactionIntegrationParse.toTransactionEntity(transaction);
		
		assertAll(transaction, transactionEntity);
	}
	
	@Test
	public void toTransactionEntityNullTest() {
		TransactionEntity transactionEntity = transactionIntegrationParse.toTransactionEntity(null);
		
		Assert.assertNotNull(transactionEntity);
	}
	
	@Test
	public void toTransactionEntityIdStringTest() {
		transaction.setId(UUID.randomUUID().toString());
		
		TransactionEntity transactionEntity = transactionIntegrationParse.toTransactionEntity(transaction);
		
		Assert.assertNotNull(transactionEntity);
	}
	
	@Test
	public void toTransactionTest() {
		Transaction transaction = transactionIntegrationParse.toTransaction(transactionEntity);
		
		assertAll(transaction, transactionEntity);
	}
	
	@Test
	public void toTransactionNullTest() {
		Transaction transaction = transactionIntegrationParse.toTransaction(null);
		
		Assert.assertNotNull(transaction);
	}
	
	@Test
	public void toTransactionsTest() {
		List<Transaction> transactions = transactionIntegrationParse.toTransactions(transactionsEntity);
		
		for(int i=0; i<transactions.size(); i++) {
			assertAll(transactions.get(i), transactionsEntity.get(i));
		}
	}
	
	@Test
	public void toTransactionsNullTest() {
		List<Transaction> transactions = transactionIntegrationParse.toTransactions(null);
		
		Assert.assertNotNull(transactions);
		Assert.assertTrue(transactions.isEmpty());
	}
	
}
