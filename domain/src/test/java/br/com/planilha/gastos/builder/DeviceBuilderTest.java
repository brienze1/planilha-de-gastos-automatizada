package br.com.planilha.gastos.builder;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.port.IdGeneratorAdapter;

@RunWith(SpringRunner.class)
public class DeviceBuilderTest {

	@InjectMocks
	private DeviceBuilder deviceBuilder;
	
	@Mock
	private IdGeneratorAdapter idGenerator;
	
	private String id;
	private String code;
	
	@Before
	public void init() {
		id = UUID.randomUUID().toString();
		code = UUID.randomUUID().toString();
	}
	
	@Test
	public void buildTest() {
		Mockito.when(idGenerator.generateVerificationCode()).thenReturn(code);
		
		Device device = deviceBuilder.build(id);
		
		Assert.assertNotNull(device);
		Assert.assertEquals(id, device.getDeviceId());
		Assert.assertEquals(code, device.getVerificationCode());
	}
	
}
