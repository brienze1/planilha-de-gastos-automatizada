package br.com.planilha.gastos.exception;

public class MapperUtilsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MapperUtilsException(String msg){
		super(msg);
	}

	public MapperUtilsException(String msg, Exception e) {
		super(msg, e);
	}
	
}
