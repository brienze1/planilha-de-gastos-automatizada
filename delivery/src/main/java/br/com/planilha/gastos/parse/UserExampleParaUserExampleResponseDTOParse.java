package br.com.planilha.gastos.parse;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.planilha.gastos.dto.UserExampleResponseDTO;
import br.com.planilha.gastos.entity.UserExample;

@Component
public class UserExampleParaUserExampleResponseDTOParse {
	
	@Autowired
	private ModelMapper mapper;
	
	public UserExampleResponseDTO parse(UserExample userExample) {
		return mapper.map(userExample, UserExampleResponseDTO.class);
	}

	public List<UserExampleResponseDTO> parseList(List<UserExample> userExampleList) {
		List<UserExampleResponseDTO> userExampleDTOList = new ArrayList<>();
		
		for (UserExample userExample : userExampleList) {
			userExampleDTOList.add(parse(userExample));
		}
		
		return userExampleDTOList;
	}

}
