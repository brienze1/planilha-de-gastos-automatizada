package br.com.planilha.gastos.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.DeviceEntity;
import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.entity.TransactionEntity;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.entity.UserEntity;
import br.com.planilha.gastos.exception.TransactionException;
import br.com.planilha.gastos.parse.TransactionIntegrationParse;
import br.com.planilha.gastos.repository.TransactionRepository;

@ExtendWith(SpringExtension.class)
public class TransactionPersistenceTest {

	@InjectMocks
	private TransactionPersistence transactionPersistence;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@Mock
	private TransactionIntegrationParse transactionParse;
	
	@Mock
	private UserPersistence userPersistence;
	
	private Transaction transaction;
	private User user;
	private TransactionEntity transactionEntity;
	private UserEntity userEntity;
	private List<TransactionEntity> transactionsEntity;
	private List<Transaction> transactions;
	private Optional<TransactionEntity> transactionEntityOptional;
	private LocalDateTime date;
	private Integer quantity;
	private Integer page;
	
	@BeforeEach
	public void init() {
		transaction = new Transaction();
		transaction.setData(LocalDateTime.now());
		transaction.setDescricao(UUID.randomUUID().toString());
		transaction.setId(UUID.randomUUID().toString());
		transaction.setLocalizacao(UUID.randomUUID().toString());
		transaction.setMeioDePagamento(UUID.randomUUID().toString());
		transaction.setTipo(UUID.randomUUID().toString());
		transaction.setValor(new BigDecimal(new Random().nextDouble()));
		
		List<Device> devices = new ArrayList<>();
		devices.add(new Device(String.valueOf(new Random().nextInt(1000))));
		
		user = new User();
		user.setAutoLogin(true);
		user.setDevices(devices);
		user.setEmail(UUID.randomUUID().toString());
		user.setFirstName(UUID.randomUUID().toString());
		user.setId(String.valueOf(new Random().nextInt(1000)));
		user.setInUseDevice(devices.get(0).getDeviceId());
		user.setLastName(UUID.randomUUID().toString());
		user.setPassword(UUID.randomUUID().toString());
		user.setSecret(UUID.randomUUID().toString());
		user.setValidEmail(true);

		DeviceEntity deviceEntity = new DeviceEntity();
		deviceEntity.setId(Integer.valueOf(devices.get(0).getDeviceId()));
		Set<DeviceEntity> devicesEntity = new HashSet<>();
		devicesEntity.add(deviceEntity);
		
		userEntity = new UserEntity();
		userEntity.setAutoLogin(user.isAutoLogin());
		userEntity.setDevices(devicesEntity);
		userEntity.setFirstName(user.getFirstName());
		userEntity.setId(Integer.valueOf(user.getId()));
		userEntity.setLastName(user.getLastName());
		userEntity.setPassword(user.getPassword());
		userEntity.setSecret(user.getSecret());
		userEntity.setValidEmail(user.isValidEmail());
		userEntity.setEmail(user.getEmail().toLowerCase());
		
		transactionEntity = new TransactionEntity();
		transactionEntity.setId(transaction.getId());
		transactionEntity.setData(transaction.getData());
		transactionEntity.setDescricao(transaction.getDescricao());
		transactionEntity.setLocalizacao(transaction.getLocalizacao());
		transactionEntity.setMeioDePagamento(transaction.getMeioDePagamento());
		transactionEntity.setTipo(transaction.getTipo());
		transactionEntity.setValor(transaction.getValor());
		
		transactionsEntity = new ArrayList<>();
		transactionsEntity.add(transactionEntity);
		transactionsEntity.add(transactionEntity);
	
		transactions = new ArrayList<>();
		transactions.add(transaction);
		transactions.add(transaction);
		
		transactionEntityOptional = Optional.of(transactionEntity);
		
		date = LocalDateTime.now();
		quantity = new Random().nextInt(1000);
		page = 2;
	}
	
	@Test
	public void saveTest() {
		Mockito.when(transactionParse.toTransactionEntity(transaction)).thenReturn(transactionEntity);
		Mockito.when(userPersistence.findUserEntity(user.getId())).thenReturn(userEntity);
		
		transactionEntity.setUser(userEntity);
		
		Mockito.when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);
		Mockito.when(transactionParse.toTransaction(transactionEntity)).thenReturn(transaction);
		
		Transaction savedTransaction = transactionPersistence.save(transaction, user);
		
		Assertions.assertNotNull(savedTransaction);
		Assertions.assertEquals(transaction.getDescricao(), savedTransaction.getDescricao());
		Assertions.assertEquals(transaction.getId(), savedTransaction.getId());
		Assertions.assertEquals(transaction.getLocalizacao(), savedTransaction.getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), savedTransaction.getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), savedTransaction.getTipo());
		Assertions.assertEquals(transaction.getData(), savedTransaction.getData());
		Assertions.assertEquals(transaction.getValor(), savedTransaction.getValor());
	}
	
	@Test
	public void findAllTest() {
		Mockito.when(userPersistence.findUserEntity(user.getId())).thenReturn(userEntity);
		Mockito.when(transactionRepository.findByUser(userEntity)).thenReturn(transactionsEntity);
		Mockito.when(transactionParse.toTransactions(transactionsEntity)).thenReturn(transactions);
		
		List<Transaction> savedTransactions = transactionPersistence.findAll(user);
		
		Assertions.assertNotNull(savedTransactions);
		Assertions.assertFalse(savedTransactions.isEmpty());
		Assertions.assertEquals(transaction.getDescricao(), savedTransactions.get(0).getDescricao());
		Assertions.assertEquals(transaction.getId(), savedTransactions.get(0).getId());
		Assertions.assertEquals(transaction.getLocalizacao(), savedTransactions.get(0).getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), savedTransactions.get(0).getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), savedTransactions.get(0).getTipo());
		Assertions.assertEquals(transaction.getData(), savedTransactions.get(0).getData());
		Assertions.assertEquals(transaction.getValor(), savedTransactions.get(0).getValor());
		Assertions.assertEquals(transaction.getDescricao(), savedTransactions.get(1).getDescricao());
		Assertions.assertEquals(transaction.getId(), savedTransactions.get(1).getId());
		Assertions.assertEquals(transaction.getLocalizacao(), savedTransactions.get(1).getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), savedTransactions.get(1).getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), savedTransactions.get(1).getTipo());
		Assertions.assertEquals(transaction.getData(), savedTransactions.get(1).getData());
		Assertions.assertEquals(transaction.getValor(), savedTransactions.get(1).getValor());
	}
	
	@Test
	public void findTest() {
		Mockito.when(userPersistence.findUserEntity(user.getId())).thenReturn(userEntity);
		Mockito.when(transactionRepository.findByUserAndId(userEntity, transaction.getId())).thenReturn(transactionEntityOptional);
		Mockito.when(transactionParse.toTransaction(transactionEntityOptional.get())).thenReturn(transaction);
		
		Transaction savedTransaction = transactionPersistence.find(user, transaction.getId());
		
		Assertions.assertNotNull(savedTransaction);
		Assertions.assertEquals(transaction.getDescricao(), savedTransaction.getDescricao());
		Assertions.assertEquals(transaction.getId(), savedTransaction.getId());
		Assertions.assertEquals(transaction.getLocalizacao(), savedTransaction.getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), savedTransaction.getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), savedTransaction.getTipo());
		Assertions.assertEquals(transaction.getData(), savedTransaction.getData());
		Assertions.assertEquals(transaction.getValor(), savedTransaction.getValor());
	}
	
	@Test
	public void findNullTest() {
		Mockito.when(userPersistence.findUserEntity(user.getId())).thenReturn(userEntity);
		Mockito.when(transactionRepository.findByUserAndId(userEntity, transaction.getId())).thenReturn(Optional.ofNullable(null));
		
		try {
			transactionPersistence.find(user, transaction.getId());
		} catch (TransactionException e) {
			Assertions.assertEquals("Transaction does not exist", e.getMessage());
		}
	}
	
	@Test
	public void isInvalidIdTest() {
		Mockito.when(transactionRepository.findById(transaction.getId())).thenReturn(transactionEntityOptional);
		
		boolean isValidId = transactionPersistence.isValidId(transaction.getId());
		
		Assertions.assertFalse(isValidId);
	}
	
	@Test
	public void isValidIdTest() {
		Mockito.when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.ofNullable(null));
		
		boolean isValidId = transactionPersistence.isValidId(transaction.getId());
		
		Assertions.assertTrue(isValidId);
	}
	
	@Test
	public void isInvalidIdErrorTest() {
		Mockito.when(transactionRepository.findById(transaction.getId())).thenThrow(new TransactionException("error"));
		
		boolean isValidId = transactionPersistence.isValidId(transaction.getId());
		
		Assertions.assertFalse(isValidId);
	}
	
	@Test
	public void findSinceDateByQuantityTest() {
		Mockito.when(userPersistence.findUserEntity(user.getId())).thenReturn(userEntity);
		Mockito.when(transactionRepository.findByUserAndDataGreaterThanEqualOrderByDataDesc(userEntity, date, PageRequest.of(page, quantity))).thenReturn(transactionsEntity);
		Mockito.when(transactionParse.toTransactions(transactionsEntity)).thenReturn(transactions);
	
		List<Transaction> savedTransactions = transactionPersistence.findSinceDateByQuantity(user, date, quantity, page);
		
		Assertions.assertNotNull(savedTransactions);
		Assertions.assertFalse(savedTransactions.isEmpty());
		Assertions.assertEquals(transaction.getDescricao(), savedTransactions.get(0).getDescricao());
		Assertions.assertEquals(transaction.getId(), savedTransactions.get(0).getId());
		Assertions.assertEquals(transaction.getLocalizacao(), savedTransactions.get(0).getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), savedTransactions.get(0).getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), savedTransactions.get(0).getTipo());
		Assertions.assertEquals(transaction.getData(), savedTransactions.get(0).getData());
		Assertions.assertEquals(transaction.getValor(), savedTransactions.get(0).getValor());
		Assertions.assertEquals(transaction.getDescricao(), savedTransactions.get(1).getDescricao());
		Assertions.assertEquals(transaction.getId(), savedTransactions.get(1).getId());
		Assertions.assertEquals(transaction.getLocalizacao(), savedTransactions.get(1).getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), savedTransactions.get(1).getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), savedTransactions.get(1).getTipo());
		Assertions.assertEquals(transaction.getData(), savedTransactions.get(1).getData());
		Assertions.assertEquals(transaction.getValor(), savedTransactions.get(1).getValor());
	}
	
	@Test
	public void findSinceDateTest() {
		Mockito.when(userPersistence.findUserEntity(user.getId())).thenReturn(userEntity);
		Mockito.when(transactionRepository.findByUserAndDataGreaterThanEqualOrderByDataDesc(userEntity, date)).thenReturn(transactionsEntity);
		Mockito.when(transactionParse.toTransactions(transactionsEntity)).thenReturn(transactions);
	
		List<Transaction> savedTransactions = transactionPersistence.findSinceDate(user, date);
		
		Assertions.assertNotNull(savedTransactions);
		Assertions.assertFalse(savedTransactions.isEmpty());
		Assertions.assertEquals(transaction.getDescricao(), savedTransactions.get(0).getDescricao());
		Assertions.assertEquals(transaction.getId(), savedTransactions.get(0).getId());
		Assertions.assertEquals(transaction.getLocalizacao(), savedTransactions.get(0).getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), savedTransactions.get(0).getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), savedTransactions.get(0).getTipo());
		Assertions.assertEquals(transaction.getData(), savedTransactions.get(0).getData());
		Assertions.assertEquals(transaction.getValor(), savedTransactions.get(0).getValor());
		Assertions.assertEquals(transaction.getDescricao(), savedTransactions.get(1).getDescricao());
		Assertions.assertEquals(transaction.getId(), savedTransactions.get(1).getId());
		Assertions.assertEquals(transaction.getLocalizacao(), savedTransactions.get(1).getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), savedTransactions.get(1).getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), savedTransactions.get(1).getTipo());
		Assertions.assertEquals(transaction.getData(), savedTransactions.get(1).getData());
		Assertions.assertEquals(transaction.getValor(), savedTransactions.get(1).getValor());
	}
	
	@Test
	public void findByQuantityTest() {
		Mockito.when(userPersistence.findUserEntity(user.getId())).thenReturn(userEntity);
		Mockito.when(transactionRepository.findByUserOrderByDataDesc(userEntity, PageRequest.of(page, quantity))).thenReturn(transactionsEntity);
		Mockito.when(transactionParse.toTransactions(transactionsEntity)).thenReturn(transactions);
	
		List<Transaction> savedTransactions = transactionPersistence.findByQuantity(user, quantity, page);
		
		Assertions.assertNotNull(savedTransactions);
		Assertions.assertFalse(savedTransactions.isEmpty());
		Assertions.assertEquals(transaction.getDescricao(), savedTransactions.get(0).getDescricao());
		Assertions.assertEquals(transaction.getId(), savedTransactions.get(0).getId());
		Assertions.assertEquals(transaction.getLocalizacao(), savedTransactions.get(0).getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), savedTransactions.get(0).getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), savedTransactions.get(0).getTipo());
		Assertions.assertEquals(transaction.getData(), savedTransactions.get(0).getData());
		Assertions.assertEquals(transaction.getValor(), savedTransactions.get(0).getValor());
		Assertions.assertEquals(transaction.getDescricao(), savedTransactions.get(1).getDescricao());
		Assertions.assertEquals(transaction.getId(), savedTransactions.get(1).getId());
		Assertions.assertEquals(transaction.getLocalizacao(), savedTransactions.get(1).getLocalizacao());
		Assertions.assertEquals(transaction.getMeioDePagamento(), savedTransactions.get(1).getMeioDePagamento());
		Assertions.assertEquals(transaction.getTipo(), savedTransactions.get(1).getTipo());
		Assertions.assertEquals(transaction.getData(), savedTransactions.get(1).getData());
		Assertions.assertEquals(transaction.getValor(), savedTransactions.get(1).getValor());
	}
	
}
