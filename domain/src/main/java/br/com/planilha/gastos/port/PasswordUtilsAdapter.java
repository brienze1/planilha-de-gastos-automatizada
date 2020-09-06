package br.com.planilha.gastos.port;

import org.springframework.stereotype.Component;

@Component
public interface PasswordUtilsAdapter {

	String encode(String password, String secret);

	boolean verifyPassword(String password, String encryptedPassword, String secret);

}
