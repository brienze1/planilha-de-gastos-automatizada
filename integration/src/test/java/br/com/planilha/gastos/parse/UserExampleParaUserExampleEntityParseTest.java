package br.com.planilha.gastos.parse;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.entity.UserExampleEntity;

@RunWith(SpringRunner.class)
public class UserExampleParaUserExampleEntityParseTest {

	@InjectMocks
	private UserExampleParaUserExampleEntityParse userExampleParaUserExampleEntityParse;
	
	@Spy
	private ModelMapper mapper;

	private UserExample userExample;
	
	@Before
	public void init() {
		userExample = new UserExample();
		userExample.setEmail("jhonmarston@email.com");
		userExample.setFirstName("Jhon");
		userExample.setId(UUID.randomUUID().toString());
		userExample.setLastName("Marston");
		userExample.setPassword("12345");
	}
	
	@Test
	public void test() {
		UserExampleEntity userExampleEntity = userExampleParaUserExampleEntityParse.parse(userExample);
	
		Assert.assertEquals(userExample.getEmail(), userExampleEntity .getEmail());
		Assert.assertEquals(userExample.getFirstName(), userExampleEntity .getFirstName());
		Assert.assertEquals(userExample.getId(), userExampleEntity .getId());
		Assert.assertEquals(userExample.getLastName(), userExampleEntity .getLastName());
		Assert.assertEquals(userExample.getPassword(), userExampleEntity .getPassword());
	
	}
	
}
