package br.com.planilha.gastos.persistence;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.DeviceEntity;
import br.com.planilha.gastos.repository.DeviceRepository;

@Component
public class DevicePersistence {

	@Autowired
	private DeviceRepository deviceRepository;

	public void save(Set<DeviceEntity> devicesEntity) {
		deviceRepository.saveAll(devicesEntity);
	}

}
