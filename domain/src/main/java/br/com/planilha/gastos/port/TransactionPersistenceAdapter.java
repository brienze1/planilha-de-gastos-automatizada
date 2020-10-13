package br.com.planilha.gastos.port;

import java.util.List;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.entity.User;

public interface TransactionPersistenceAdapter {

	Transaction save(Transaction transaction, User user);

	List<Transaction> findAll(User user);

	Transaction find(User user, String transactionId);

}
