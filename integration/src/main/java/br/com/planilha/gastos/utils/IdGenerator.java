package br.com.planilha.gastos.utils;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.persistence.TransactionPersistence;
import br.com.planilha.gastos.port.IdGeneratorAdapter;

@Component
public class IdGenerator implements IdGeneratorAdapter {

	private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz12345674890";
	private static final Integer TAMANHO_ID_VERIFICACAO = 5;
	private static final Integer TAMANHO_TRANSACTION_ID = 10;
	
	@Autowired
	private TransactionPersistence transactionPersistence;
	
	@Override
	public String generateId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String generateSecret() {
		return UUID.randomUUID().toString() + UUID.randomUUID().toString();
	}

	@Override
	public String generateVerificationCode() {
		return randomCode(TAMANHO_ID_VERIFICACAO);
	}

	@Override
	public String generateTransactionId() {
		String transactionId = randomCode(TAMANHO_TRANSACTION_ID);
		
		if(!transactionPersistence.isValidId(transactionId)) {
			return generateTransactionId();
		}
		
		return transactionId;
	}
	
	private String randomCode(Integer size) {
		StringBuilder builder = new StringBuilder();
		
        for(int i = 0; i < TAMANHO_ID_VERIFICACAO; i++) {
            builder.append(ALFABETO.charAt(new Random().nextInt(ALFABETO.length())));
        }
        
	    return builder.toString();
	}
}
