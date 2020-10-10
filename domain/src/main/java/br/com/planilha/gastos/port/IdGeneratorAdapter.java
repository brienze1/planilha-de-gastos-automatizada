package br.com.planilha.gastos.port;

import org.springframework.stereotype.Component;

@Component
public interface IdGeneratorAdapter {

	String generateId();

	String generateSecret();

	String generateVerificationCode();

}
