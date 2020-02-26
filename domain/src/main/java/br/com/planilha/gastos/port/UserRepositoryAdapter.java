package br.com.planilha.gastos.port;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.UserExample;

@Component
public interface UserRepositoryAdapter {
	
	public Optional<UserExample> findById(String id);
	
	public List<UserExample> findAllUsers();

	public void create(UserExample user);

	public Optional<UserExample> findByEmail(String email);
	
}
