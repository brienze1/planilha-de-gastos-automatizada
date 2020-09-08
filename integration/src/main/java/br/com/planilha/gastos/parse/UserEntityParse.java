package br.com.planilha.gastos.parse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.entity.UserEntity;
import br.com.planilha.gastos.utils.MapperUtils;

@Component
public class UserEntityParse {
	
	@Autowired
	private MapperUtils mapper;
	
	public UserEntity parse(User user) {
		UserEntity userEntity = mapper.map(user, UserEntity.class);
		
		userEntity.setEmail(userEntity.getEmail().toLowerCase());
		
		return userEntity;
	}
	
}
