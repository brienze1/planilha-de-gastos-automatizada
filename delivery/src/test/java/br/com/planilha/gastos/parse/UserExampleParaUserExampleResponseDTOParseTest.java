package br.com.planilha.gastos.parse;

import java.util.Arrays;
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

import br.com.planilha.gastos.dto.UserExampleResponseDTO;
import br.com.planilha.gastos.entity.UserExample;

@RunWith(SpringRunner.class)
public class UserExampleParaUserExampleResponseDTOParseTest {

	@InjectMocks
	private UserExampleParaUserExampleResponseDTOParse userExampleParaUserExampleResponseDTOParse;
	
	@Spy
	private ModelMapper mapper;
	
	private UserExample userExample;
	
	@Before
	public void init() {
		userExample = new UserExample();
		userExample.setEmail("jhon.marston@email.com");
		userExample.setFirstName("Jhon");
		userExample.setLastName("Marston");
		userExample.setId(UUID.randomUUID().toString());
		userExample.setPassword("12345");
	}
	
	@Test
	public void test() {
		UserExampleResponseDTO response = userExampleParaUserExampleResponseDTOParse.parse(userExample);
		
		Assert.assertEquals(userExample.getEmail(), response.getEmail());
		Assert.assertEquals(userExample.getFirstName(), response.getFirstName());
		Assert.assertEquals(userExample.getLastName(), response.getLastName());
		Assert.assertEquals(userExample.getId(), response.getId());
		Assert.assertEquals(userExample.getPassword(), response.getPassword());
	}
	
	@Test
	public void testList() {
		List<UserExampleResponseDTO> response = userExampleParaUserExampleResponseDTOParse.parseList(Arrays.asList(userExample));
		
		Assert.assertEquals(userExample.getEmail(), response.get(0).getEmail());
		Assert.assertEquals(userExample.getFirstName(), response.get(0).getFirstName());
		Assert.assertEquals(userExample.getLastName(), response.get(0).getLastName());
		Assert.assertEquals(userExample.getId(), response.get(0).getId());
		Assert.assertEquals(userExample.getPassword(), response.get(0).getPassword());
	}
	
}

