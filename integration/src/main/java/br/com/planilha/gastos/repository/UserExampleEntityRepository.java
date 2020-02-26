package br.com.planilha.gastos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.planilha.gastos.entity.UserExampleEntity;

@Repository
public interface UserExampleEntityRepository extends CrudRepository<UserExampleEntity, String> {
	
	List<UserExampleEntity> findAllByEmail(String email);
	
	List<UserExampleEntity> findAll();
	
}
