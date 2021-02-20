package br.com.planilha.gastos.rules;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.exception.TransactionException;

@Component
public class TransactionRules {

	public boolean validate(Transaction transaction) {
		if(transaction == null) {
			throw new TransactionException("Transaction can't be null");
		}
		if(transaction.getValor() == null || transaction.getValor().compareTo(BigDecimal.ZERO) <= 0) {
			throw new TransactionException("Value can't be null, zero or less than zero");
		}
		if(transaction.getMeioDePagamento() == null || transaction.getMeioDePagamento().isBlank()) {
			transaction.setMeioDePagamento("Unknown");
		}
		if(transaction.getLocalizacao() == null || transaction.getLocalizacao().isBlank()) {
			transaction.setLocalizacao("Unknown");
		}
		if(transaction.getTipo() == null || transaction.getTipo().isBlank()) {
			transaction.setTipo("Sent");
		}
		if(transaction.getData() == null) {
			transaction.setData(LocalDateTime.now());
		}
		if(transaction.getDescricao() == null || transaction.getDescricao().isBlank()) {
			transaction.setDescricao("Undefined");
		}
		
		return true;
	}

}
