package br.com.planilha.gastos.endpoint;

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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.dto.TransactionDto;
import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.parse.TransactionDeliveryParse;
import br.com.planilha.gastos.service.TransactionService;

@ExtendWith(SpringExtension.class)
public class TransactionControllerTest {

	@InjectMocks
	private TransactionController transactionController;
	
	@Mock
	private TransactionService transactionService;
	
	@Mock
	private TransactionDeliveryParse transactionParse;
	
	private String token;
	private String idTransacao;
	private Transaction transaction;
	private Transaction registeredTransaction;
	private TransactionDto transactionDto;
	private TransactionDto transactionDtoResponse;
	private List<Transaction> transactions;
	private List<TransactionDto> transactionsDto;
	private String date;
	private Integer quantity;
	private Integer page;
	
	@BeforeEach
	public void init() {
		token = UUID.randomUUID().toString();
		
		idTransacao = UUID.randomUUID().toString();
		
		transactions = new ArrayList<>();
		transactionsDto = new ArrayList<>();
		
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
			
			transactionDto = new TransactionDto();
			transactionDto.setData(transaction.getData());
			transactionDto.setDescricao(transaction.getDescricao());
			transactionDto.setId(transaction.getId());
			transactionDto.setLocalizacao(transaction.getLocalizacao());
			transactionDto.setMeioDePagamento(transaction.getMeioDePagamento());
			transactionDto.setTipo(transaction.getTipo());
			transactionDto.setValor(transaction.getValor());
			
			transactionsDto.add(transactionDto);
		}
		
		registeredTransaction = new Transaction();
		registeredTransaction.setData(transaction.getData());
		registeredTransaction.setDescricao(transaction.getDescricao());
		registeredTransaction.setId(transaction.getId());
		registeredTransaction.setLocalizacao(transaction.getLocalizacao());
		registeredTransaction.setMeioDePagamento(transaction.getMeioDePagamento());
		registeredTransaction.setTipo(transaction.getTipo());
		registeredTransaction.setValor(transaction.getValor());
		
		transactionDtoResponse = new TransactionDto();
		transactionDtoResponse.setData(transaction.getData());
		transactionDtoResponse.setDescricao(transaction.getDescricao());
		transactionDtoResponse.setId(transaction.getId());
		transactionDtoResponse.setLocalizacao(transaction.getLocalizacao());
		transactionDtoResponse.setMeioDePagamento(transaction.getMeioDePagamento());
		transactionDtoResponse.setTipo(transaction.getTipo());
		transactionDtoResponse.setValor(transaction.getValor());
		
		date = "02/10/2021";
		
		quantity = 10;
		
		page = 0;
	}
	
	@Test
	public void cadastrarTransacaoTest() {
		Mockito.when(transactionParse.toTransaction(transactionDto)).thenReturn(transaction);
		Mockito.when(transactionService.register(token, transaction)).thenReturn(registeredTransaction);
		Mockito.when(transactionParse.toTransactionDto(registeredTransaction)).thenReturn(transactionDtoResponse);
		
		TransactionDto response = transactionController.cadastrarTransacao(token, transactionDto);
		
		Mockito.verify(transactionParse).toTransaction(transactionDto);
		Mockito.verify(transactionService).register(token, transaction);
		Mockito.verify(transactionParse).toTransactionDto(registeredTransaction);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(transactionDto.getDescricao(), response.getDescricao());
		Assertions.assertEquals(transactionDto.getId(), response.getId());
		Assertions.assertEquals(transactionDto.getLocalizacao(), response.getLocalizacao());
		Assertions.assertEquals(transactionDto.getMeioDePagamento(), response.getMeioDePagamento());
		Assertions.assertEquals(transactionDto.getTipo(), response.getTipo());
		Assertions.assertEquals(transactionDto.getData(), response.getData());
		Assertions.assertEquals(transactionDto.getValor(), response.getValor());
	}
	
	@Test
	public void buscarTransacoesDateQuantityPageTest() {
		page = null;
		
		Mockito.when(transactionService.findSinceDateByQuantity(token, date, quantity, 0)).thenReturn(transactions);
		Mockito.when(transactionParse.toTransactionsDto(transactions)).thenReturn(transactionsDto);
		
		List<TransactionDto> response = transactionController.buscarTransacoes(token, date, page, quantity);
		
		Mockito.verify(transactionService).findSinceDateByQuantity(token, date, quantity, 0);
		Mockito.verify(transactionParse).toTransactionsDto(transactions);
		
		Assertions.assertNotNull(response);
		Assertions.assertFalse(response.isEmpty());
		
		for (TransactionDto transactionDto : response) {
			for (Transaction transaction : transactions) {
				if(transactionDto.getId().equals(transaction.getId())) {
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
	public void buscarTransacoesPageLessThenZeroTest() {
		page = -1;
		
		Mockito.when(transactionService.findSinceDateByQuantity(token, date, quantity, 0)).thenReturn(transactions);
		Mockito.when(transactionParse.toTransactionsDto(transactions)).thenReturn(transactionsDto);
		
		List<TransactionDto> response = transactionController.buscarTransacoes(token, date, page, quantity);
		
		Mockito.verify(transactionService).findSinceDateByQuantity(token, date, quantity, 0);
		Mockito.verify(transactionParse).toTransactionsDto(transactions);
		
		Assertions.assertNotNull(response);
		Assertions.assertFalse(response.isEmpty());
		
		for (TransactionDto transactionDto : response) {
			for (Transaction transaction : transactions) {
				if(transactionDto.getId().equals(transaction.getId())) {
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
	public void buscarTransacoesDateNullTest() {
		date = null;
		
		Mockito.when(transactionService.findByQuantity(token, quantity, page)).thenReturn(transactions);
		Mockito.when(transactionParse.toTransactionsDto(transactions)).thenReturn(transactionsDto);
		
		List<TransactionDto> response = transactionController.buscarTransacoes(token, date, page, quantity);
		
		Mockito.verify(transactionService).findByQuantity(token, quantity, page);
		Mockito.verify(transactionParse).toTransactionsDto(transactions);
		
		Assertions.assertNotNull(response);
		Assertions.assertFalse(response.isEmpty());
		
		for (TransactionDto transactionDto : response) {
			for (Transaction transaction : transactions) {
				if(transactionDto.getId().equals(transaction.getId())) {
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
	public void buscarTransacoesDateBlankTest() {
		date = " ";
		
		Mockito.when(transactionService.findByQuantity(token, quantity, page)).thenReturn(transactions);
		Mockito.when(transactionParse.toTransactionsDto(transactions)).thenReturn(transactionsDto);
		
		List<TransactionDto> response = transactionController.buscarTransacoes(token, date, page, quantity);
		
		Mockito.verify(transactionService).findByQuantity(token, quantity, page);
		Mockito.verify(transactionParse).toTransactionsDto(transactions);
		
		Assertions.assertNotNull(response);
		Assertions.assertFalse(response.isEmpty());
		
		for (TransactionDto transactionDto : response) {
			for (Transaction transaction : transactions) {
				if(transactionDto.getId().equals(transaction.getId())) {
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
	public void buscarTransacoesQauntityNullTest() {
		quantity = null;
		
		Mockito.when(transactionService.findSinceDate(token, date)).thenReturn(transactions);
		Mockito.when(transactionParse.toTransactionsDto(transactions)).thenReturn(transactionsDto);
		
		List<TransactionDto> response = transactionController.buscarTransacoes(token, date, page, quantity);
		
		Mockito.verify(transactionService).findSinceDate(token, date);
		Mockito.verify(transactionParse).toTransactionsDto(transactions);
		
		Assertions.assertNotNull(response);
		Assertions.assertFalse(response.isEmpty());
		
		for (TransactionDto transactionDto : response) {
			for (Transaction transaction : transactions) {
				if(transactionDto.getId().equals(transaction.getId())) {
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
	public void buscarTransacoesQauntityLessThanZeroTest() {
		quantity = -1;
		
		Mockito.when(transactionService.findSinceDate(token, date)).thenReturn(transactions);
		Mockito.when(transactionParse.toTransactionsDto(transactions)).thenReturn(transactionsDto);
		
		List<TransactionDto> response = transactionController.buscarTransacoes(token, date, page, quantity);
		
		Mockito.verify(transactionService).findSinceDate(token, date);
		Mockito.verify(transactionParse).toTransactionsDto(transactions);
		
		Assertions.assertNotNull(response);
		Assertions.assertFalse(response.isEmpty());
		
		for (TransactionDto transactionDto : response) {
			for (Transaction transaction : transactions) {
				if(transactionDto.getId().equals(transaction.getId())) {
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
	public void buscarTransacoesPageNullTest() {
		Mockito.when(transactionService.findSinceDateByQuantity(token, date, quantity, page)).thenReturn(transactions);
		Mockito.when(transactionParse.toTransactionsDto(transactions)).thenReturn(transactionsDto);
		
		List<TransactionDto> response = transactionController.buscarTransacoes(token, date, page, quantity);
		
		Mockito.verify(transactionService).findSinceDateByQuantity(token, date, quantity, page);
		Mockito.verify(transactionParse).toTransactionsDto(transactions);
		
		Assertions.assertNotNull(response);
		Assertions.assertFalse(response.isEmpty());
		
		for (TransactionDto transactionDto : response) {
			for (Transaction transaction : transactions) {
				if(transactionDto.getId().equals(transaction.getId())) {
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
	public void buscarTransacoesQauntityLessThanZeroDateNullTest() {
		quantity = -1;
		date = null;
		
		Mockito.when(transactionService.findAll(token)).thenReturn(transactions);
		Mockito.when(transactionParse.toTransactionsDto(transactions)).thenReturn(transactionsDto);
		
		List<TransactionDto> response = transactionController.buscarTransacoes(token, date, page, quantity);
		
		Mockito.verify(transactionService).findAll(token);
		Mockito.verify(transactionParse).toTransactionsDto(transactions);
		
		Assertions.assertNotNull(response);
		Assertions.assertFalse(response.isEmpty());
		
		for (TransactionDto transactionDto : response) {
			for (Transaction transaction : transactions) {
				if(transactionDto.getId().equals(transaction.getId())) {
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
	public void buscarTransacoesQauntityNullDateNullTest() {
		quantity = null;
		date = null;
		
		Mockito.when(transactionService.findAll(token)).thenReturn(transactions);
		Mockito.when(transactionParse.toTransactionsDto(transactions)).thenReturn(transactionsDto);
		
		List<TransactionDto> response = transactionController.buscarTransacoes(token, date, page, quantity);
		
		Mockito.verify(transactionService).findAll(token);
		Mockito.verify(transactionParse).toTransactionsDto(transactions);
		
		Assertions.assertNotNull(response);
		Assertions.assertFalse(response.isEmpty());
		
		for (TransactionDto transactionDto : response) {
			for (Transaction transaction : transactions) {
				if(transactionDto.getId().equals(transaction.getId())) {
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
	public void buscarTransacaoTest() {
		Mockito.when(transactionService.find(token, idTransacao)).thenReturn(transaction);
		Mockito.when(transactionParse.toTransactionDto(transaction)).thenReturn(transactionDto);
		
		TransactionDto response = transactionController.buscarTransacao(token, idTransacao);
		
		Mockito.verify(transactionService).find(token, idTransacao);
		Mockito.verify(transactionParse).toTransactionDto(transaction);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(transactionDto.getDescricao(), response.getDescricao());
		Assertions.assertEquals(transactionDto.getId(), response.getId());
		Assertions.assertEquals(transactionDto.getLocalizacao(), response.getLocalizacao());
		Assertions.assertEquals(transactionDto.getMeioDePagamento(), response.getMeioDePagamento());
		Assertions.assertEquals(transactionDto.getTipo(), response.getTipo());
		Assertions.assertEquals(transactionDto.getData(), response.getData());
		Assertions.assertEquals(transactionDto.getValor(), response.getValor());
	}
	
}
