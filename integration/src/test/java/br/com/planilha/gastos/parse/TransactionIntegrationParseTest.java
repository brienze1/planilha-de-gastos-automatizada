package br.com.planilha.gastos.parse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.entity.TransactionEntity;

@ExtendWith(SpringExtension.class)
public class TransactionIntegrationParseTest {

	@InjectMocks
	private TransactionIntegrationParse transactionIntegrationParse;
	
	private Transaction transaction;
	private TransactionEntity transactionEntity;
	private List<TransactionEntity> transactionsEntity;
	
	@BeforeEach
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
		Assertions.assertNotNull(transaction);
		Assertions.assertNotNull(transactionEntity);
		Assertions.assertEquals(transaction.getDescricao(), transactionEntity.getDescricao());
		Assertions.assertEquals(transaction.getId(), String.valueOf(transactionEntity.getId()));
		Assertions.assertEquals(transaction.getLocalizacao(), transactionEntity.getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), transactionEntity.getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), transactionEntity.getTipo());
		Assertions.assertEquals(transaction.getData(), transactionEntity.getData());
		Assertions.assertEquals(transaction.getValor(), transactionEntity.getValor());
	}
	
	@Test
	public void toTransactionEntityTest() {
		TransactionEntity transactionEntity = transactionIntegrationParse.toTransactionEntity(transaction);
		
		assertAll(transaction, transactionEntity);
	}
	
	@Test
	public void toTransactionEntityNullTest() {
		TransactionEntity transactionEntity = transactionIntegrationParse.toTransactionEntity(null);
		
		Assertions.assertNotNull(transactionEntity);
	}
	
	@Test
	public void toTransactionEntityIdStringTest() {
		transaction.setId(UUID.randomUUID().toString());
		
		TransactionEntity transactionEntity = transactionIntegrationParse.toTransactionEntity(transaction);
		
		Assertions.assertNotNull(transactionEntity);
	}
	
	@Test
	public void toTransactionTest() {
		Transaction transaction = transactionIntegrationParse.toTransaction(transactionEntity);
		
		assertAll(transaction, transactionEntity);
	}
	
	@Test
	public void toTransactionNullTest() {
		Transaction transaction = transactionIntegrationParse.toTransaction(null);
		
		Assertions.assertNotNull(transaction);
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
		
		Assertions.assertNotNull(transactions);
		Assertions.assertTrue(transactions.isEmpty());
	}
	
}
