package br.com.planilha.gastos.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.exception.TransactionException;

@Component
public class DateValidator {

	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
    public LocalDateTime isValid(String dateStr) {
        LocalDate data = null;
    	try {
            data = LocalDate.parse(dateStr, this.dateFormatter);
        } catch (DateTimeParseException e) {
        	throw new TransactionException("Not a valid date (try using dd/MM/yyyy)");
        }
        return LocalDateTime.of(data.getYear(), data.getMonthValue(), data.getDayOfMonth(), 0, 0);
    }
	
}
