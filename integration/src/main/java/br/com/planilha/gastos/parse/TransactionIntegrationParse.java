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
			transactionEntity.setId(transaction.getId());
			transactionEntity.setData(transaction.getData());
			transactionEntity.setDescricao(transaction.getDescricao());
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
			transaction.setId(transactionEntity.getId());
			transaction.setData(transactionEntity.getData());
			transaction.setDescricao(transactionEntity.getDescricao());
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
