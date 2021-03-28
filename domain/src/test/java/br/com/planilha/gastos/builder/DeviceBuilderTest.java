package br.com.planilha.gastos.builder;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.port.IdGeneratorAdapter;

@ExtendWith(SpringExtension.class)
public class DeviceBuilderTest {

	@InjectMocks
	private DeviceBuilder deviceBuilder;
	
	@Mock
	private IdGeneratorAdapter idGenerator;
	
	private String id;
	private String code;
	
	@BeforeEach
	public void init() {
		id = UUID.randomUUID().toString();
		code = UUID.randomUUID().toString();
	}
	
	@Test
	public void buildTest() {
		Mockito.when(idGenerator.generateVerificationCode()).thenReturn(code);
		
		Device device = deviceBuilder.build(id);
		
		Assertions.assertNotNull(device);
		Assertions.assertEquals(id, device.getDeviceId());
		Assertions.assertEquals(code, device.getVerificationCode());
	}
	
}
