package br.com.planilha.gastos.port;

import org.springframework.stereotype.Component;

@Component
public interface PasswordEncoderAdapter {

	String encode(String string);

}
