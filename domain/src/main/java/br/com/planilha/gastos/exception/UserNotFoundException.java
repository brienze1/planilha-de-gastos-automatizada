package br.com.planilha.gastos.exception;

public class UserNotFoundException extends UserException {

	private static final long serialVersionUID = -3141551548951661598L;

	public UserNotFoundException(String msg){
		super(msg);
	}
}
