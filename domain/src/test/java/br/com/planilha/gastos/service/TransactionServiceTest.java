package br.com.planilha.gastos.service;

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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.TransactionPersistenceAdapter;
import br.com.planilha.gastos.rules.TransactionRules;
import br.com.planilha.gastos.utils.DateValidator;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

	@InjectMocks
	private TransactionService transactionService;
	
	@Mock
	private JwtService jwtService;

	@Mock
	private TransactionRules transactionRules;

	@Mock
	private TransactionPersistenceAdapter transactionPersistence;

	@Mock
	private DateValidator dateValidator;

	private String token;
	private Transaction transaction;
	private Transaction transactionSaved;
	private User user;
	private List<Transaction> transactions;
	private String dateString; 
	private Integer quantity;
	private Integer page;
	
	@Before
	public void init() {
		token = UUID.randomUUID().toString();
		
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
		
		transactionSaved = new Transaction();
		transactionSaved.setData(transaction.getData());
		transactionSaved.setDescricao(transaction.getDescricao());
		transactionSaved.setId(transaction.getId());
		transactionSaved.setLocalizacao(transaction.getLocalizacao());
		transactionSaved.setMeioDePagamento(transaction.getMeioDePagamento());
		transactionSaved.setTipo(transaction.getTipo());
		transactionSaved.setValor(transaction.getValor());
		
		user = new User();
		user.setId(UUID.randomUUID().toString());
		
		dateString = LocalDateTime.now().toString();
		
		quantity = 10;
		
		page = 1;
	}
	
	@Test
	public void registerTest() {
		Mockito.when(jwtService.verifyAcessToken(token)).thenReturn(user);
		Mockito.when(transactionPersistence.save(transaction, user)).thenReturn(transactionSaved);
		
		Transaction returnedTransaction = transactionService.register(token, transaction);
		
		Mockito.verify(jwtService).verifyAcessToken(token);
		Mockito.verify(transactionRules).validate(transaction);
		Mockito.verify(transactionPersistence).save(transaction, user);

		Assert.assertNotNull(returnedTransaction);
		Assert.assertEquals(transaction.getDescricao(), returnedTransaction.getDescricao());
		Assert.assertEquals(transaction.getId(), returnedTransaction.getId());
		Assert.assertEquals(transaction.getLocalizacao(), returnedTransaction.getLocalizacao());
		Assert.assertEquals(transaction.getMeioDePagamento(), returnedTransaction.getMeioDePagamento());
		Assert.assertEquals(transaction.getTipo(), returnedTransaction.getTipo());
		Assert.assertEquals(transaction.getData(), returnedTransaction.getData());
		Assert.assertEquals(transaction.getValor(), returnedTransaction.getValor());
	}
	
	@Test
	public void findAllTest() {
		Mockito.when(jwtService.verifyAcessToken(token)).thenReturn(user);
		Mockito.when(transactionPersistence.findAll(user)).thenReturn(transactions);
		
		List<Transaction> returnedTransactions = transactionService.findAll(token);
		
		Mockito.verify(jwtService).verifyAcessToken(token);
		Mockito.verify(transactionPersistence).findAll(user);
		
		Assert.assertNotNull(returnedTransactions);
		Assert.assertFalse(returnedTransactions.isEmpty());
		for (Transaction returnedTransaction : returnedTransactions) {
			for (Transaction transaction : transactions) {
				if(returnedTransaction.getId().equals(transaction.getId())) {
					Assert.assertNotNull(returnedTransaction);
					Assert.assertEquals(transaction.getDescricao(), returnedTransaction.getDescricao());
					Assert.assertEquals(transaction.getId(), returnedTransaction.getId());
					Assert.assertEquals(transaction.getLocalizacao(), returnedTransaction.getLocalizacao());
					Assert.assertEquals(transaction.getMeioDePagamento(), returnedTransaction.getMeioDePagamento());
					Assert.assertEquals(transaction.getTipo(), returnedTransaction.getTipo());
					Assert.assertEquals(transaction.getData(), returnedTransaction.getData());
					Assert.assertEquals(transaction.getValor(), returnedTransaction.getValor());
				}
			}
		}
	}
	
	@Test
	public void findTest() {
		Mockito.when(jwtService.verifyAcessToken(token)).thenReturn(user);
		Mockito.when(transactionPersistence.find(user, transaction.getId())).thenReturn(transaction);
		
		Transaction returnedTransaction = transactionService.find(token, transaction.getId());
		
		Mockito.verify(jwtService).verifyAcessToken(token);
		Mockito.verify(transactionPersistence).find(user, transaction.getId());
		
		Assert.assertNotNull(returnedTransaction);
		Assert.assertEquals(transaction.getDescricao(), returnedTransaction.getDescricao());
		Assert.assertEquals(transaction.getId(), returnedTransaction.getId());
		Assert.assertEquals(transaction.getLocalizacao(), returnedTransaction.getLocalizacao());
		Assert.assertEquals(transaction.getMeioDePagamento(), returnedTransaction.getMeioDePagamento());
		Assert.assertEquals(transaction.getTipo(), returnedTransaction.getTipo());
		Assert.assertEquals(transaction.getData(), returnedTransaction.getData());
		Assert.assertEquals(transaction.getValor(), returnedTransaction.getValor());
	}
	
	@Test
	public void findSinceDateByQuantityTest() {
		LocalDateTime date = LocalDateTime.now();
		
		Mockito.when(jwtService.verifyAcessToken(token)).thenReturn(user);
		Mockito.when(dateValidator.isValid(dateString)).thenReturn(date);
		Mockito.when(transactionPersistence.findSinceDateByQuantity(user, date, quantity, page)).thenReturn(transactions);

		List<Transaction> returnedTransactions = transactionService.findSinceDateByQuantity(token, dateString, quantity, page);
		
		Mockito.verify(jwtService).verifyAcessToken(token);
		Mockito.verify(dateValidator).isValid(dateString);
		Mockito.verify(transactionPersistence).findSinceDateByQuantity(user, date, quantity, page);
		
		Assert.assertNotNull(returnedTransactions);
		Assert.assertFalse(returnedTransactions.isEmpty());
		for (Transaction returnedTransaction : returnedTransactions) {
			for (Transaction transaction : transactions) {
				if(returnedTransaction.getId().equals(transaction.getId())) {
					Assert.assertNotNull(returnedTransaction);
					Assert.assertEquals(transaction.getDescricao(), returnedTransaction.getDescricao());
					Assert.assertEquals(transaction.getId(), returnedTransaction.getId());
					Assert.assertEquals(transaction.getLocalizacao(), returnedTransaction.getLocalizacao());
					Assert.assertEquals(transaction.getMeioDePagamento(), returnedTransaction.getMeioDePagamento());
					Assert.assertEquals(transaction.getTipo(), returnedTransaction.getTipo());
					Assert.assertEquals(transaction.getData(), returnedTransaction.getData());
					Assert.assertEquals(transaction.getValor(), returnedTransaction.getValor());
				}
			}
		}
	}
	
	@Test
	public void findSinceDateTest() {
		LocalDateTime date = LocalDateTime.now();
		
		Mockito.when(jwtService.verifyAcessToken(token)).thenReturn(user);
		Mockito.when(dateValidator.isValid(dateString)).thenReturn(date);
		Mockito.when(transactionPersistence.findSinceDate(user, date)).thenReturn(transactions);

		List<Transaction> returnedTransactions = transactionService.findSinceDate(token, dateString);
		
		Mockito.verify(jwtService).verifyAcessToken(token);
		Mockito.verify(dateValidator).isValid(dateString);
		Mockito.verify(transactionPersistence).findSinceDate(user, date);
		
		Assert.assertNotNull(returnedTransactions);
		Assert.assertFalse(returnedTransactions.isEmpty());
		for (Transaction returnedTransaction : returnedTransactions) {
			for (Transaction transaction : transactions) {
				if(returnedTransaction.getId().equals(transaction.getId())) {
					Assert.assertNotNull(returnedTransaction);
					Assert.assertEquals(transaction.getDescricao(), returnedTransaction.getDescricao());
					Assert.assertEquals(transaction.getId(), returnedTransaction.getId());
					Assert.assertEquals(transaction.getLocalizacao(), returnedTransaction.getLocalizacao());
					Assert.assertEquals(transaction.getMeioDePagamento(), returnedTransaction.getMeioDePagamento());
					Assert.assertEquals(transaction.getTipo(), returnedTransaction.getTipo());
					Assert.assertEquals(transaction.getData(), returnedTransaction.getData());
					Assert.assertEquals(transaction.getValor(), returnedTransaction.getValor());
				}
			}
		}
	}
	
	@Test
	public void findByQuantityTest() {
		Mockito.when(jwtService.verifyAcessToken(token)).thenReturn(user);
		Mockito.when(transactionPersistence.findByQuantity(user, quantity, page)).thenReturn(transactions);

		List<Transaction> returnedTransactions = transactionService.findByQuantity(token, quantity, page);
		
		Mockito.verify(jwtService).verifyAcessToken(token);
		Mockito.verify(transactionPersistence).findByQuantity(user, quantity, page);
		
		Assert.assertNotNull(returnedTransactions);
		Assert.assertFalse(returnedTransactions.isEmpty());
		for (Transaction returnedTransaction : returnedTransactions) {
			for (Transaction transaction : transactions) {
				if(returnedTransaction.getId().equals(transaction.getId())) {
					Assert.assertNotNull(returnedTransaction);
					Assert.assertEquals(transaction.getDescricao(), returnedTransaction.getDescricao());
					Assert.assertEquals(transaction.getId(), returnedTransaction.getId());
					Assert.assertEquals(transaction.getLocalizacao(), returnedTransaction.getLocalizacao());
					Assert.assertEquals(transaction.getMeioDePagamento(), returnedTransaction.getMeioDePagamento());
					Assert.assertEquals(transaction.getTipo(), returnedTransaction.getTipo());
					Assert.assertEquals(transaction.getData(), returnedTransaction.getData());
					Assert.assertEquals(transaction.getValor(), returnedTransaction.getValor());
				}
			}
		}
	}
	
}
