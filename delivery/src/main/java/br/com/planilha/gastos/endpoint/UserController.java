package br.com.planilha.gastos.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.planilha.gastos.adapter.UserControllerAdapter;
import br.com.planilha.gastos.dto.UserDto;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.service.UserService;
import br.com.planilha.gastos.utils.MapperUtils;

@RestController
public class UserController implements UserControllerAdapter {
	
	@Autowired
	private MapperUtils mapper;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/new")
	public @JsonProperty("jwt") String register(UserDto userDto) {
		
		User user = mapper.map(userDto, User.class);
		
		return userService.register(user);
	}
	
	@GetMapping("user/login")
	public void login(@JsonProperty("jwt") String jwtToken) {
		
	}
	
}
