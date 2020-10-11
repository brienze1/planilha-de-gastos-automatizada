package br.com.planilha.gastos.handler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import br.com.planilha.gastos.exception.UserAlreadyExistsException;

@RunWith(SpringRunner.class)
public class ErrorHandlerTest {

	@InjectMocks
	private ErrorHandler errorHandler;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none(); 
	
	@Test
	public void test() {
		expectedException.expect(ResponseStatusException.class);
		expectedException.expectMessage("User already exists.");
		
		errorHandler.userAlreadyExistsError(new UserAlreadyExistsException("User already exists."));
	}
}
