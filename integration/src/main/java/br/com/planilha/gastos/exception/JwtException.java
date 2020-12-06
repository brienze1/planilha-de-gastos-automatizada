package br.com.planilha.gastos.exception;

public class JwtException extends RuntimeException {

	private static final long serialVersionUID = -1L;

	JwtException(String msg) {
		super(msg);
	}
}
