package br.com.planilha.gastos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.IdGeneratorAdapter;
import br.com.planilha.gastos.port.TransactionPersistenceAdapter;
import br.com.planilha.gastos.rules.TransactionRules;

@Component
public class TransactionService {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private TransactionRules transactionRules;

	@Autowired
	private TransactionPersistenceAdapter transactionPersistence;
	
	@Autowired
	private IdGeneratorAdapter idGenerator;
	
	public Transaction register(String token, Transaction transaction) {
		User user = jwtService.verifyAcessToken(token);
		
		transactionRules.validate(transaction);

		transaction.setId(idGenerator.generateTransactionId());
		
		Transaction transactionSaved = transactionPersistence.save(transaction, user);
		
		return transactionSaved;
	}

	public List<Transaction> find(String token) {
		User user = jwtService.verifyAcessToken(token);
		
		return transactionPersistence.findAll(user);
	}

	public Transaction find(String token, String transactionId) {
		User user = jwtService.verifyAcessToken(token);
		
		return transactionPersistence.find(user, transactionId);
	}

}
