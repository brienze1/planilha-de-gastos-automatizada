package br.com.planilha.gastos.webservice;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.EmailWebServiceAdapter;

@Component
public class EmailWebService implements EmailWebServiceAdapter {

	@Override
	public boolean send(User user, String message) {
		return true;
	}

}
