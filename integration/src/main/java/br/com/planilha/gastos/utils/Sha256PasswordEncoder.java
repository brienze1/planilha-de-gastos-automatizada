package br.com.planilha.gastos.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.port.PasswordEncoderAdapter;

@Component
public class Sha256PasswordEncoder implements PasswordEncoderAdapter {

	@Override
	public String encode(final String str) {
		return DigestUtils.sha256Hex(str);
	}
}
