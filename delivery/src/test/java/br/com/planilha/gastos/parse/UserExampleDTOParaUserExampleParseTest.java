package br.com.planilha.gastos.parse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.dto.UserExampleDTO;
import br.com.planilha.gastos.entity.UserExample;

@RunWith(SpringRunner.class)
public class UserExampleDTOParaUserExampleParseTest {

	@InjectMocks
	private UserExampleDTOParaUserExampleParse userExampleDTOParaUserExampleParse;
	
	@Test
	public void test() {
		UserExampleDTO userExampleDTO = new UserExampleDTO();
		userExampleDTO.setEmail("jhonmarston@hotmail.com");
		userExampleDTO.setFirstName("john");
		userExampleDTO.setLastName("marston");
		userExampleDTO.setPassword("12345");
		
		UserExample userExample = userExampleDTOParaUserExampleParse.parse(userExampleDTO);
		
		Assert.assertEquals(userExampleDTO.getEmail(), userExample.getEmail());
		Assert.assertEquals(userExampleDTO.getFirstName(), userExample.getFirstName());
		Assert.assertEquals(userExampleDTO.getLastName(), userExample.getLastName());
		Assert.assertEquals(userExampleDTO.getPassword(), userExample.getPassword());
	}
	
	@Test
	public void testNull() {
		UserExample userExample = userExampleDTOParaUserExampleParse.parse(null);

		Assert.assertTrue(userExample != null);
	}
}
