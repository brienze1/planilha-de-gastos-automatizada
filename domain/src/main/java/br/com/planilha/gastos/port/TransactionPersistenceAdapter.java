package br.com.planilha.gastos.port;

import java.time.LocalDateTime;
import java.util.List;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.entity.User;

public interface TransactionPersistenceAdapter {

	Transaction save(Transaction transaction, User user);

	List<Transaction> findAll(User user);

	Transaction find(User user, String transactionId);

	List<Transaction> findSinceDateByQuantity(User user, LocalDateTime date, Integer quantity, Integer page);

	List<Transaction> findSinceDate(User user, LocalDateTime date);

	List<Transaction> findByQuantity(User user, Integer quantity, Integer page);

	boolean isValidId(String transactionId);

}
