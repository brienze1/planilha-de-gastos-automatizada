package br.com.planilha.gastos.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DateValidatorTest {

	@InjectMocks
	private DateValidator dateValidator;
	
	private String validDate;
	private String dia;
	private String mes;
	private String ano;
	private String invalidDate;
	
	@Before
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
		
		Assert.assertEquals(dia, String.valueOf(date.getDayOfMonth()));
		Assert.assertEquals(mes, String.valueOf(date.getMonth().getValue()));
		Assert.assertEquals(ano, String.valueOf(date.getYear()));
	}
	
	@Test(expected = DateTimeParseException.class)
	public void isInValidTest() {
		try {
			dateValidator.isValid(invalidDate);
		} catch (DateTimeParseException e) {
			Assert.assertEquals("Not a valid date (try using dd/MM/yyyy)", e.getMessage());
			Assert.assertEquals(invalidDate, e.getParsedString());
			
			throw e;
		}
	}
	
}
