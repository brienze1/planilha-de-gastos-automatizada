package br.com.planilha.gastos.parse;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.dto.UserExampleDTO;
import br.com.planilha.gastos.entity.UserExample;

@Component
public class UserExampleDTOParaUserExampleParse {
	
	public UserExample parse(UserExampleDTO userExampleDTO) {
		UserExample userExample = new UserExample();
		
		if(userExampleDTO != null) {
			userExample.setEmail(userExampleDTO.getEmail());
			userExample.setFirstName(userExampleDTO.getFirstName());
			userExample.setLastName(userExampleDTO.getLastName());
			userExample.setPassword(userExampleDTO.getPassword());
		}
		
		return userExample;
	}

}
