package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.entity.UserExampleEntity;

@Component
public class UserExampleEntityParaUserExampleParse {

	@Autowired
	private ModelMapper mapper;
	
	public UserExample parse(UserExampleEntity userExampleEntity) {
		return mapper.map(userExampleEntity  , UserExample.class);
	}

	public List<UserExample> parseList(List<UserExampleEntity> userExampleEntityList) {
		List<UserExample> userExampleList = new ArrayList<>();
		
		for (UserExampleEntity userExampleEntity : userExampleEntityList) {
			userExampleList.add(parse(userExampleEntity));
		}
		
		return userExampleList;
	}
	
}
