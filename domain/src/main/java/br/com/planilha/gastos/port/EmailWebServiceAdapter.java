package br.com.planilha.gastos.port;

import br.com.planilha.gastos.entity.User;

public interface EmailWebServiceAdapter {

	boolean send(User user, String message);

}
