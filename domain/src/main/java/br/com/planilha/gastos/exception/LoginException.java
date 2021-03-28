package br.com.planilha.gastos.exception;

public class LoginException extends RuntimeException {

	private static final long serialVersionUID = -2558211593732904551L;

	public LoginException(String msg) {
		super(msg);
	}
}
