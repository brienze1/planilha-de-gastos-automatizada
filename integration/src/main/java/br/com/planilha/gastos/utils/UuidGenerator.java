package br.com.planilha.gastos.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.port.IdGeneratorAdapter;

@Component
public class UuidGenerator implements IdGeneratorAdapter {

	@Override
	public String generate() {
		return UUID.randomUUID().toString();
	}
}
