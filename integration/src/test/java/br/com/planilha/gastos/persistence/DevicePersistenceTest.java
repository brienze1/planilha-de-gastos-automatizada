package br.com.planilha.gastos.persistence;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.DeviceEntity;
import br.com.planilha.gastos.repository.DeviceRepository;

@RunWith(SpringRunner.class)
public class DevicePersistenceTest {

	@InjectMocks
	private DevicePersistence devicePersistence;
	
	@Mock
	private DeviceRepository deviceRepository;
	
	private Set<DeviceEntity> devicesEntity;
	private Iterable<DeviceEntity> devicesEntitySaved;
	
	@Before
	public void init() {
		devicesEntity = new HashSet<>();
		devicesEntitySaved = new HashSet<>();
		
		for(int i=0; i<10; i++) {
			DeviceEntity deviceEntity = new DeviceEntity();
			deviceEntity.setDeviceId(UUID.randomUUID().toString());
			deviceEntity.setId(new Random().nextInt(1000));
			deviceEntity.setVerificationCode(UUID.randomUUID().toString());
			deviceEntity.setInUse(true);
			deviceEntity.setVerified(true);
			
			devicesEntity.add(deviceEntity);
			((Set<DeviceEntity>) devicesEntitySaved).add(deviceEntity);
		}
		
	}
	
	@Test
	public void saveTest() {
		Mockito.when(deviceRepository.saveAll(devicesEntity)).thenReturn(devicesEntitySaved);
		
		Set<DeviceEntity> response = devicePersistence.save(devicesEntity);
		
		Assert.assertNotNull(response);
		Assert.assertFalse(response.isEmpty());
		Assert.assertEquals(devicesEntity.size(), response.size());
		for (DeviceEntity deviceEntityResponse : response) {
			for (DeviceEntity deviceEntity : devicesEntity) {
				if(deviceEntityResponse.getDeviceId().equals(deviceEntity.getDeviceId())) {
					Assert.assertEquals(deviceEntity.getDeviceId(), deviceEntityResponse.getDeviceId());
					Assert.assertEquals(deviceEntity.getId(), deviceEntityResponse.getId());
					Assert.assertEquals(deviceEntity.getVerificationCode(), deviceEntityResponse.getVerificationCode());
					Assert.assertEquals(deviceEntity.isInUse(), deviceEntityResponse.isInUse());
					Assert.assertEquals(deviceEntity.isVerified(), deviceEntityResponse.isVerified());
				}
			}
		}
	}
	
}
