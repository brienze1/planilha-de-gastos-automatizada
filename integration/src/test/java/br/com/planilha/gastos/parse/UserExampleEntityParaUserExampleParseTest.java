package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;
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
public class UserExampleEntityParaUserExampleParseTest {

	@InjectMocks
	private UserExampleEntityParaUserExampleParse userExampleEntityParaUserExampleParse;
	
	@Spy
	private ModelMapper mapper;
	
	private UserExampleEntity userExampleEntity;
	private List<UserExampleEntity> userExampleEntityList;
	
	@Before
	public void init() {
		userExampleEntity = new UserExampleEntity();
		userExampleEntity.setEmail("jhonmartson@email.com");
		userExampleEntity.setFirstName("Jhon");
		userExampleEntity.setId(UUID.randomUUID().toString());
		userExampleEntity.setLastName("Marston");
		userExampleEntity.setPassword(UUID.randomUUID().toString());
		
		userExampleEntityList = new ArrayList<>();
		userExampleEntityList.add(userExampleEntity);
		
	}
	
	@Test
	public void testParse() {
		UserExample userExample = userExampleEntityParaUserExampleParse.parse(userExampleEntity);
		
		Assert.assertEquals(userExampleEntity.getEmail(), userExample.getEmail());
		Assert.assertEquals(userExampleEntity.getFirstName(), userExample.getFirstName());
		Assert.assertEquals(userExampleEntity.getId(), userExample.getId());
		Assert.assertEquals(userExampleEntity.getLastName(), userExample.getLastName());
		Assert.assertEquals(userExampleEntity.getPassword(), userExample.getPassword());
	}
	
	@Test
	public void testList() {
		List<UserExample> userExampleList = userExampleEntityParaUserExampleParse.parseList(userExampleEntityList);
		
		Assert.assertEquals(userExampleEntity.getEmail(), userExampleList.get(0).getEmail());
		Assert.assertEquals(userExampleEntity.getFirstName(), userExampleList.get(0).getFirstName());
		Assert.assertEquals(userExampleEntity.getId(), userExampleList.get(0).getId());
		Assert.assertEquals(userExampleEntity.getLastName(), userExampleList.get(0).getLastName());
		Assert.assertEquals(userExampleEntity.getPassword(), userExampleList.get(0).getPassword());
	}
	
}

