package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.dto.TransactionDto;
import br.com.planilha.gastos.entity.Transaction;

@Component
public class TransactionDeliveryParse {

	public Transaction toTransaction(TransactionDto transactionDto) {
		Transaction transaction = new Transaction();

		if (transactionDto != null) {
			transaction.setId(transactionDto.getId());
			transaction.setData(transactionDto.getData());
			transaction.setLocalizacao(transactionDto.getLocalizacao());
			transaction.setMeioDePagamento(transactionDto.getMeioDePagamento());
			transaction.setTipo(transactionDto.getTipo());
			transaction.setValor(transactionDto.getValor());
		}

		return transaction;
	}

	public TransactionDto toTransactionDto(Transaction transaction) {
		TransactionDto transactionDto = new TransactionDto();

		if (transaction != null) {
			transactionDto.setId(transaction.getId());
			transactionDto.setData(transaction.getData());
			transactionDto.setLocalizacao(transaction.getLocalizacao());
			transactionDto.setMeioDePagamento(transaction.getMeioDePagamento());
			transactionDto.setTipo(transaction.getTipo());
			transactionDto.setValor(transaction.getValor());
		}

		return transactionDto;
	}

	public List<TransactionDto> toTransactionsDto(List<Transaction> transactions) {
		List<TransactionDto> transactionsDto = new ArrayList<>();
		
		if(transactions != null) {
			for (Transaction transaction : transactions) {
				transactionsDto.add(toTransactionDto(transaction));
			}
		}
		
		return transactionsDto;
	}

}
