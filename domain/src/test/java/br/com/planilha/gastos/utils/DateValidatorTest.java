package br.com.planilha.gastos.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class DateValidatorTest {

	@InjectMocks
	private DateValidator dateValidator;
	
	private String validDate;
	private String dia;
	private String mes;
	private String ano;
	private String invalidDate;
	
	@BeforeEach
	public void init() {
		dia = "30";
		mes = "10";
		ano = "2020";
		
		validDate = dia + "/" + mes + "/" + ano;
		invalidDate = UUID.randomUUID().toString();
	}
	
	@Test
	public void isValidTest() {
		LocalDateTime date = dateValidator.isValid(validDate);
		
		Assertions.assertEquals(dia, String.valueOf(date.getDayOfMonth()));
		Assertions.assertEquals(mes, String.valueOf(date.getMonth().getValue()));
		Assertions.assertEquals(ano, String.valueOf(date.getYear()));
	}
	
	@Test
	public void isInValidTest() {
		Assertions.assertThrows(
				DateTimeParseException.class, 
				() -> dateValidator.isValid(invalidDate), 
				"Not a valid date (try using dd/MM/yyyy)");
	}
	
}
