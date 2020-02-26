package br.com.planilha.gastos.exception;

public class UserAlreadyExistsExceptionExample extends RuntimeException {

	private static final long serialVersionUID = -3788766278090345347L;
	
	 public UserAlreadyExistsExceptionExample(String errorMessage) {
	        super(errorMessage);
    }
	
}
