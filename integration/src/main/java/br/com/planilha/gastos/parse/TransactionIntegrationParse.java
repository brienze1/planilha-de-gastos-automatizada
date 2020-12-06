package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.entity.TransactionEntity;

@Component
public class TransactionIntegrationParse {

	public TransactionEntity toTransactionEntity(Transaction transaction) {
		TransactionEntity transactionEntity = new TransactionEntity();

		if (transaction != null) {
			try {
				transactionEntity.setId(Integer.valueOf(transaction.getId()));
			}catch (Exception e) {
			}
			transactionEntity.setData(transaction.getData());
			transactionEntity.setLocalizacao(transaction.getLocalizacao());
			transactionEntity.setMeioDePagamento(transaction.getMeioDePagamento());
			transactionEntity.setTipo(transaction.getTipo());
			transactionEntity.setValor(transaction.getValor());
		}

		return transactionEntity;
	}

	public Transaction toTransaction(TransactionEntity transactionEntity) {
		Transaction transaction = new Transaction();

		if (transactionEntity != null) {
			transaction.setId(String.valueOf(transactionEntity.getId()));
			transaction.setData(transactionEntity.getData());
			transaction.setLocalizacao(transactionEntity.getLocalizacao());
			transaction.setMeioDePagamento(transactionEntity.getMeioDePagamento());
			transaction.setTipo(transactionEntity.getTipo());
			transaction.setValor(transactionEntity.getValor());
		}

		return transaction;
	}

	public List<Transaction> toTransactions(List<TransactionEntity> transactionsEntity) {
		List<Transaction> transactions = new ArrayList<>();
		
		if(transactionsEntity != null) {
			for (TransactionEntity transactionEntity : transactionsEntity) {
				transactions.add(toTransaction(transactionEntity));
			}
		}
		
		return transactions;
	}

}
