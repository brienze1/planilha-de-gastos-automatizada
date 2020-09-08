package br.com.planilha.gastos.exception;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -3788766278090345347L;
	
	 public UserAlreadyExistsException(String errorMessage) {
	        super(errorMessage);
    }
	
}
