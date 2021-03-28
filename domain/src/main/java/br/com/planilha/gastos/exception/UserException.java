package br.com.planilha.gastos.exception;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 751195490207306763L;

	UserException(String msg) {
		super(msg);
	}
	
}
