package br.com.planilha.gastos.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import br.com.planilha.gastos.exception.DeviceNotFoundException;
import br.com.planilha.gastos.exception.DeviceNotVerifiedException;
import br.com.planilha.gastos.exception.UserAlreadyExistsException;

@ControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler({UserAlreadyExistsException.class})
	public ResponseEntity<Object> userAlreadyExistsError(Exception ex) {
		throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
	}
	
	@ExceptionHandler({DeviceNotFoundException.class})
	public ResponseEntity<Object> deviceNotFoundException(Exception ex) {
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This device isn't registered in our database, please check your email to confirm your new device");
	}
	
	@ExceptionHandler({DeviceNotVerifiedException.class})
	public ResponseEntity<Object> deviceNotVerifiedException(Exception ex) {
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This device isn't verified, please check your email to verify your device");
	}
	
	
}
