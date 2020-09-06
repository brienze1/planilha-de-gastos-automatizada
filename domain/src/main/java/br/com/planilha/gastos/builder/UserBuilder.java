package br.com.planilha.gastos.builder;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.IdGeneratorAdapter;
import br.com.planilha.gastos.port.PasswordUtilsAdapter;

@Component
public class UserBuilder {

	@Autowired
	private PasswordUtilsAdapter passwordUtils;
	
	@Autowired
	private IdGeneratorAdapter idGenerator;
	
	public void build(User user) {
		//Gera o id do usuario
		user.setId(idGenerator.generate());

		//Seta a variavel de email valido como falso
		user.setValidEmail(false);
	
		//Variavel de configuracao para autorizar login automatico
		user.setAutoLogin(false);
		
		//Id do dispositivo verificado
		user.setDeviceId(UUID.randomUUID().toString());
		
		//Secret para criar e decodificar jwts
		user.setSecret(UUID.randomUUID().toString());
		
		user.setPassword(passwordUtils.encode(user.getPassword(), user.getSecret()));
	}

}
