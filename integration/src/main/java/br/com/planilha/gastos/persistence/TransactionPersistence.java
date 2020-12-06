package br.com.planilha.gastos.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.entity.TransactionEntity;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.entity.UserEntity;
import br.com.planilha.gastos.exception.TransactionException;
import br.com.planilha.gastos.parse.TransactionIntegrationParse;
import br.com.planilha.gastos.port.TransactionPersistenceAdapter;
import br.com.planilha.gastos.repository.TransactionRepository;

@Component
public class TransactionPersistence implements TransactionPersistenceAdapter {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionIntegrationParse transactionParse;
	
	@Autowired
	private UserPersistence userPersistence;
	
	@Override
	public Transaction save(Transaction transaction, User user) {
		TransactionEntity transactionEntity = transactionParse.toTransactionEntity(transaction);
		
		transactionEntity.setUser(userPersistence.findUserEntity(user.getId()));
		
		TransactionEntity savedTransaction = transactionRepository.save(transactionEntity);
		
		return transactionParse.toTransaction(savedTransaction);
	}

	@Override
	public List<Transaction> findAll(User user) {
		UserEntity userEntity = userPersistence.findUserEntity(user.getId());
		
		List<TransactionEntity> transactionsEntity = transactionRepository.findByUser(userEntity);
		
		return transactionParse.toTransactions(transactionsEntity);
	}

	@Override
	public Transaction find(User user, String transactionId) {
		UserEntity userEntity = userPersistence.findUserEntity(user.getId());
		
		Optional<TransactionEntity> transactionEntity = transactionRepository.findByUserAndId(userEntity, transactionId);
		
		if(transactionEntity.isPresent()) {
			return transactionParse.toTransaction(transactionEntity.get());
		}
		
		throw new TransactionException("Transaction does not exist");
	}

	public boolean isValidId(String transactionId) {
		try {
			Integer id = Integer.valueOf(transactionId);
			return !transactionRepository.findById(id).isPresent();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Transaction> findSinceDateByQuantity(User user, LocalDateTime date, Integer quantity, Integer page) {
		UserEntity userEntity = userPersistence.findUserEntity(user.getId());
		
		List<TransactionEntity> transactionsEntity = transactionRepository.findByUserAndDataGreaterThanEqualOrderByDataDesc(userEntity, date, PageRequest.of(page, quantity));
		
		return transactionParse.toTransactions(transactionsEntity);
	}

	@Override
	public List<Transaction> findSinceDate(User user, LocalDateTime date) {
		UserEntity userEntity = userPersistence.findUserEntity(user.getId());
		
		List<TransactionEntity> transactionsEntity = transactionRepository.findByUserAndDataGreaterThanEqualOrderByDataDesc(userEntity, date);
		
		return transactionParse.toTransactions(transactionsEntity);
	}

	@Override
	public List<Transaction> findByQuantity(User user, Integer quantity, Integer page) {
		UserEntity userEntity = userPersistence.findUserEntity(user.getId());
		
		List<TransactionEntity> transactionsEntity = transactionRepository.findByUserOrderByDataDesc(userEntity, PageRequest.of(page, quantity));
		
		return transactionParse.toTransactions(transactionsEntity);
	}


}
