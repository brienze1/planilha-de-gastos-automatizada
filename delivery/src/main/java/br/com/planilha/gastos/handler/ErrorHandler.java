package br.com.planilha.gastos.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import br.com.planilha.gastos.exception.UserAlreadyExistsException;

@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler({UserAlreadyExistsException.class})
	public ResponseEntity<Object> errorHandler(Exception ex) {
		throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
	}
	
}
