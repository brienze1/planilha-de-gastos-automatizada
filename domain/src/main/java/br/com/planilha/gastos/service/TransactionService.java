package br.com.planilha.gastos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.TransactionPersistenceAdapter;
import br.com.planilha.gastos.rules.TransactionRules;
import br.com.planilha.gastos.utils.DateValidator;

@Component
public class TransactionService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private TransactionRules transactionRules;

	@Autowired
	private TransactionPersistenceAdapter transactionPersistence;

	@Autowired
	private DateValidator dateValidator;

	public Transaction register(String token, Transaction transaction) {
		User user = jwtService.verifyAcessToken(token);

		transactionRules.validate(transaction);

		Transaction transactionSaved = transactionPersistence.save(transaction, user);

		return transactionSaved;
	}

	public List<Transaction> findAll(String token) {
		User user = jwtService.verifyAcessToken(token);

		return transactionPersistence.findAll(user);
	}

	public Transaction find(String token, String transactionId) {
		User user = jwtService.verifyAcessToken(token);

		return transactionPersistence.find(user, transactionId);
	}

	public List<Transaction> findSinceDateByQuantity(String token, String dateString, Integer quantity, Integer page) {
		User user = jwtService.verifyAcessToken(token);
		
		LocalDateTime date = dateValidator.isValid(dateString);
		
		return transactionPersistence.findSinceDateByQuantity(user, date, quantity, page);
	}
	
	public List<Transaction> findSinceDate(String token, String dateString) {
		User user = jwtService.verifyAcessToken(token);
		
		LocalDateTime date = dateValidator.isValid(dateString);
		
		return transactionPersistence.findSinceDate(user, date);
	}

	public List<Transaction> findByQuantity(String token, Integer quantity, Integer page) {
		User user = jwtService.verifyAcessToken(token);
		
		return transactionPersistence.findByQuantity(user, quantity, page);
	}

}
