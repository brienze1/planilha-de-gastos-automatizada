package br.com.planilha.gastos.persistence;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.DeviceEntity;
import br.com.planilha.gastos.repository.DeviceRepository;

@Component
public class DevicePersistence {

	@Autowired
	private DeviceRepository deviceRepository;

	public Set<DeviceEntity> save(Set<DeviceEntity> devicesEntity) {
		Iterable<DeviceEntity> devicesEntitySaved = deviceRepository.saveAll(devicesEntity);
		
		Set<DeviceEntity> devices = new HashSet<>();
		for (DeviceEntity deviceEntity : devicesEntitySaved) {
			devices.add(deviceEntity);
		}
		
		return devices;
	}

}
