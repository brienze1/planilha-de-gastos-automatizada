package br.com.planilha.gastos.rules;

import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.exception.UserAlreadyExistsExceptionExample;
import br.com.planilha.gastos.port.UserRepositoryAdapter;

@RunWith(SpringRunner.class)
public class UserValidatorExampleTest {

	@InjectMocks
	private UserValidatorExample userValidatorExample;
	
	@Mock
	private UserRepositoryAdapter repository;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	private UserExample userExample;
	
	@Before
	public void init() {
		userExample = new UserExample();
		userExample.setEmail("johnmarston@email.com");
		userExample.setFirstName("Jhon");
		userExample.setId(UUID.randomUUID().toString());
		userExample.setLastName("Marston");
		userExample.setPassword("12345");
	}
	
	@Test
	public void test() {
		Mockito.when(repository.findByEmail(userExample.getEmail())).thenReturn(Optional.ofNullable(null));
	
		userValidatorExample.validateCreateUser(userExample);
	}
	
	@Test
	public void testException() {
		expectedException.expect(UserAlreadyExistsExceptionExample.class);
		expectedException.expectMessage(userExample.getEmail());
		
		Mockito.when(repository.findByEmail(userExample.getEmail())).thenReturn(Optional.of(userExample));
		
		userValidatorExample.validateCreateUser(userExample);
	}
}
