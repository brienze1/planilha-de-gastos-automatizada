package br.com.planilha.gastos.utils;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.port.IdGeneratorAdapter;

@Component
public class IdGenerator implements IdGeneratorAdapter {

	private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz12345674890";
	private static final Integer TAMANHO_ID_VERIFICACAO = 5;
	
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
		StringBuilder builder = new StringBuilder();
		
        for(int i = 0; i < TAMANHO_ID_VERIFICACAO; i++) {
            builder.append(ALFABETO.charAt(new Random().nextInt(ALFABETO.length())));
        }
        
	    return builder.toString();
	}
}
