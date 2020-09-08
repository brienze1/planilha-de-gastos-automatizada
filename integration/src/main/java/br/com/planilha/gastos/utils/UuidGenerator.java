package br.com.planilha.gastos.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.port.IdGeneratorAdapter;

@Component
public class UuidGenerator implements IdGeneratorAdapter {

	@Override
	public String generateId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String generateSecret() {
		return UUID.randomUUID().toString() + UUID.randomUUID().toString();
	}
}
