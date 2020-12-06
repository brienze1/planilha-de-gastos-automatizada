package br.com.planilha.gastos.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import br.com.planilha.gastos.entity.TransactionEntity;
import br.com.planilha.gastos.entity.UserEntity;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {

	Optional<TransactionEntity> findByUserAndId(UserEntity userEntity, String transactionId);

	List<TransactionEntity> findByUser(UserEntity userEntity);

	List<TransactionEntity> findByUserAndDataGreaterThanEqualOrderByDataDesc(UserEntity userEntity, LocalDateTime date, Pageable page);

	List<TransactionEntity> findByUserAndDataGreaterThanEqualOrderByDataDesc(UserEntity userEntity, LocalDateTime date);

	List<TransactionEntity> findByUserOrderByDataDesc(UserEntity userEntity, Pageable page);

}
