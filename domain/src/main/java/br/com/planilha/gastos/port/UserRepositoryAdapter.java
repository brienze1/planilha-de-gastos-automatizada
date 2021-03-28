package br.com.planilha.gastos.port;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;

@Component
public interface UserRepositoryAdapter {
	
	public Optional<User> findById(String id);
	
	public User save(User user);

	public Optional<User> findByEmail(String email);

}
