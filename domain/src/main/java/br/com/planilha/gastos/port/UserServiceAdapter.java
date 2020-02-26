package br.com.planilha.gastos.port;

import java.util.List;
import java.util.Optional;

import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.exception.UserAlreadyExistsExceptionExample;

public interface UserServiceAdapter {

	public String create(UserExample user) throws UserAlreadyExistsExceptionExample;
	
	public Optional<UserExample> findById(final String id);
	
	public List<UserExample> findAllUsers();
	
	public Optional<UserExample> findByEmail(String email);
}
