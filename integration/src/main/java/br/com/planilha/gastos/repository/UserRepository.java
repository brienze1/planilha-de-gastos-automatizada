package br.com.planilha.gastos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.planilha.gastos.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
	
	List<UserEntity> findAllByEmail(String email);
	
	List<UserEntity> findAll();
	
}
