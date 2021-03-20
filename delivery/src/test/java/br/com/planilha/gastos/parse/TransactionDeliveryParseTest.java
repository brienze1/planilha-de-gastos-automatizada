package br.com.planilha.gastos.parse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.dto.TransactionDto;
import br.com.planilha.gastos.entity.Transaction;

@RunWith(SpringRunner.class)
public class TransactionDeliveryParseTest {

	@InjectMocks
	private TransactionDeliveryParse transactionDeliveryParse;
	
	private TransactionDto transactionDto;
	private Transaction transaction;
	private List<Transaction> transactions;
	
	@Before
	public void init() {
		transactionDto = new TransactionDto();
		transactionDto.setData(LocalDateTime.now());
		transactionDto.setDescricao(UUID.randomUUID().toString());
		transactionDto.setId(UUID.randomUUID().toString());
		transactionDto.setLocalizacao(UUID.randomUUID().toString());
		transactionDto.setMeioDePagamento(UUID.randomUUID().toString());
		transactionDto.setTipo(UUID.randomUUID().toString());
		transactionDto.setValor(BigDecimal.valueOf(1000.00));
		
		transactions = new ArrayList<>();
		for(int i=0; i<10; i++) {
			transaction = new Transaction();
			transaction.setData(LocalDateTime.now());
			transaction.setDescricao(UUID.randomUUID().toString());
			transaction.setId(UUID.randomUUID().toString());
			transaction.setLocalizacao(UUID.randomUUID().toString());
			transaction.setMeioDePagamento(UUID.randomUUID().toString());
			transaction.setTipo(UUID.randomUUID().toString());
			transaction.setValor(BigDecimal.valueOf(1000.00));
			
			transactions.add(transaction);
		}
	}
	
	@Test
	public void toTransactionTest() {
		Transaction transaction = transactionDeliveryParse.toTransaction(transactionDto);
		
		Assert.assertNotNull(transaction);
		Assert.assertEquals(transactionDto.getDescricao(), transaction.getDescricao());
		Assert.assertEquals(transactionDto.getId(), transaction.getId());
		Assert.assertEquals(transactionDto.getLocalizacao(), transaction.getLocalizacao());
		Assert.assertEquals(transactionDto.getMeioDePagamento(), transaction.getMeioDePagamento());
		Assert.assertEquals(transactionDto.getTipo(), transaction.getTipo());
		Assert.assertEquals(transactionDto.getData(), transaction.getData());
		Assert.assertEquals(transactionDto.getValor(), transaction.getValor());
	}
	
	@Test
	public void toTransactionNullTest() {
		Transaction transaction = transactionDeliveryParse.toTransaction(null);
		
		Assert.assertNotNull(transaction);
	}
	
	@Test
	public void toTransactionDtoTest() {
		TransactionDto transactionDto = transactionDeliveryParse.toTransactionDto(transaction);
		
		Assert.assertNotNull(transactionDto);
		Assert.assertEquals(transaction.getDescricao(), transactionDto.getDescricao());
		Assert.assertEquals(transaction.getId(), transactionDto.getId());
		Assert.assertEquals(transaction.getLocalizacao(), transactionDto.getLocalizacao());
		Assert.assertEquals(transaction.getMeioDePagamento(), transactionDto.getMeioDePagamento());
		Assert.assertEquals(transaction.getTipo(), transactionDto.getTipo());
		Assert.assertEquals(transaction.getData(), transactionDto.getData());
		Assert.assertEquals(transaction.getValor(), transactionDto.getValor());
	}
	
	@Test
	public void toTransactionDtoNullTest() {
		TransactionDto transactionDto = transactionDeliveryParse.toTransactionDto(null);
		
		Assert.assertNotNull(transactionDto);
	}
	
	@Test
	public void toTransactionsDtoTest() {
		List<TransactionDto> transactionsDto = transactionDeliveryParse.toTransactionsDto(transactions);
		
		Assert.assertNotNull(transactionsDto);
		Assert.assertFalse(transactionsDto.isEmpty());
		for (Transaction transaction : transactions) {
			for (TransactionDto transactionDto : transactionsDto) {
				if(transaction.getId().equals(transactionDto.getId())) {
					Assert.assertNotNull(transactionDto);
					Assert.assertEquals(transaction.getDescricao(), transactionDto.getDescricao());
					Assert.assertEquals(transaction.getId(), transactionDto.getId());
					Assert.assertEquals(transaction.getLocalizacao(), transactionDto.getLocalizacao());
					Assert.assertEquals(transaction.getMeioDePagamento(), transactionDto.getMeioDePagamento());
					Assert.assertEquals(transaction.getTipo(), transactionDto.getTipo());
					Assert.assertEquals(transaction.getData(), transactionDto.getData());
					Assert.assertEquals(transaction.getValor(), transactionDto.getValor());
				}
			}
		}
	}
	
	@Test
	public void toTransactionsDtoNullTest() {
		List<TransactionDto> transactionsDto = transactionDeliveryParse.toTransactionsDto(null);

		Assert.assertNotNull(transactionsDto);
		Assert.assertTrue(transactionsDto.isEmpty());
	}
	
}
