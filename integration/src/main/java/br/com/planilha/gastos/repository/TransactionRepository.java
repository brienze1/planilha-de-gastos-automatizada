package br.com.planilha.gastos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.planilha.gastos.entity.TransactionEntity;
import br.com.planilha.gastos.entity.UserEntity;

public interface TransactionRepository extends CrudRepository<TransactionEntity, String> {

	Optional<TransactionEntity> findByUserAndId(UserEntity userEntity, String transactionId);

	List<TransactionEntity> findByUser(UserEntity userEntity);

}
