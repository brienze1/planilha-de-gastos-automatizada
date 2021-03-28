package br.com.planilha.gastos.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.planilha.gastos.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	
	Optional<UserEntity> findByEmail(String email);

}
