package br.com.planilha.gastos.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.planilha.gastos.entity.DeviceEntity;

public interface DeviceRepository extends CrudRepository<DeviceEntity, String> {

}
