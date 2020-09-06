package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.entity.UserEntity;
import br.com.planilha.gastos.utils.MapperUtils;

@Component
public class UserParse {
	
	@Autowired
	private MapperUtils mapper;
	
	public List<User> parse(List<UserEntity> userEntityList) {
		List<User> userList = new ArrayList<>();
		
		userEntityList.forEach(userEntity -> userList.add(parse(userEntity)));
		
		return userList;
	}

	public User parse(UserEntity userEntity) {
		User user = mapper.map(userEntity, User.class);
				
		return user;
	}

}
