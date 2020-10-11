package br.com.planilha.gastos.port;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;

@Component
public interface UserRepositoryAdapter {
	
	public Optional<User> findById(String id);
	
	public List<User> findAllUsers();

	public User save(User user);

	public Optional<User> findByEmail(String email);

}
