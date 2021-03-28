package br.com.planilha.gastos.parse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.dto.TransactionDto;
import br.com.planilha.gastos.entity.Transaction;

@ExtendWith(SpringExtension.class)
public class TransactionDeliveryParseTest {

	@InjectMocks
	private TransactionDeliveryParse transactionDeliveryParse;
	
	private TransactionDto transactionDto;
	private Transaction transaction;
	private List<Transaction> transactions;
	
	@BeforeEach
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
		
		Assertions.assertNotNull(transaction);
		Assertions.assertEquals(transactionDto.getDescricao(), transaction.getDescricao());
		Assertions.assertEquals(transactionDto.getId(), transaction.getId());
		Assertions.assertEquals(transactionDto.getLocalizacao(), transaction.getLocalizacao());
		Assertions.assertEquals(transactionDto.getMeioDePagamento(), transaction.getMeioDePagamento());
		Assertions.assertEquals(transactionDto.getTipo(), transaction.getTipo());
		Assertions.assertEquals(transactionDto.getData(), transaction.getData());
		Assertions.assertEquals(transactionDto.getValor(), transaction.getValor());
	}
	
	@Test
	public void toTransactionNullTest() {
		Transaction transaction = transactionDeliveryParse.toTransaction(null);
		
		Assertions.assertNotNull(transaction);
	}
	
	@Test
	public void toTransactionDtoTest() {
		TransactionDto transactionDto = transactionDeliveryParse.toTransactionDto(transaction);
		
		Assertions.assertNotNull(transactionDto);
		Assertions.assertEquals(transaction.getDescricao(), transactionDto.getDescricao());
		Assertions.assertEquals(transaction.getId(), transactionDto.getId());
		Assertions.assertEquals(transaction.getLocalizacao(), transactionDto.getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), transactionDto.getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), transactionDto.getTipo());
		Assertions.assertEquals(transaction.getData(), transactionDto.getData());
		Assertions.assertEquals(transaction.getValor(), transactionDto.getValor());
	}
	
	@Test
	public void toTransactionDtoNullTest() {
		TransactionDto transactionDto = transactionDeliveryParse.toTransactionDto(null);
		
		Assertions.assertNotNull(transactionDto);
	}
	
	@Test
	public void toTransactionsDtoTest() {
		List<TransactionDto> transactionsDto = transactionDeliveryParse.toTransactionsDto(transactions);
		
		Assertions.assertNotNull(transactionsDto);
		Assertions.assertFalse(transactionsDto.isEmpty());
		for (Transaction transaction : transactions) {
			for (TransactionDto transactionDto : transactionsDto) {
				if(transaction.getId().equals(transactionDto.getId())) {
					Assertions.assertNotNull(transactionDto);
					Assertions.assertEquals(transaction.getDescricao(), transactionDto.getDescricao());
					Assertions.assertEquals(transaction.getId(), transactionDto.getId());
					Assertions.assertEquals(transaction.getLocalizacao(), transactionDto.getLocalizacao());
					Assertions.assertEquals(transaction.getMeioDePagamento(), transactionDto.getMeioDePagamento());
					Assertions.assertEquals(transaction.getTipo(), transactionDto.getTipo());
					Assertions.assertEquals(transaction.getData(), transactionDto.getData());
					Assertions.assertEquals(transaction.getValor(), transactionDto.getValor());
				}
			}
		}
	}
	
	@Test
	public void toTransactionsDtoNullTest() {
		List<TransactionDto> transactionsDto = transactionDeliveryParse.toTransactionsDto(null);

		Assertions.assertNotNull(transactionsDto);
		Assertions.assertTrue(transactionsDto.isEmpty());
	}
	
}
