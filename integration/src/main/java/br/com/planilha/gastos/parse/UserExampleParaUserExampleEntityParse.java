package br.com.planilha.gastos.parse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.entity.UserExampleEntity;

@Component
public class UserExampleParaUserExampleEntityParse {
	
	@Autowired
	private ModelMapper mapper;
	
	public UserExampleEntity parse(UserExample userExample) {
		return mapper.map(userExample, UserExampleEntity.class);
	}

}
