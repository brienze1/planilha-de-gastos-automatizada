package br.com.planilha.gastos.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.planilha.gastos.adapter.UserControllerAdapter;
import br.com.planilha.gastos.dto.DataDto;
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
	
	@PostMapping("/user/new")
	@Override
	public DataDto register(@RequestBody UserDto userDto) {
		
		User user = mapper.map(userDto, User.class);
		
		DataDto dataDto = new DataDto();
		dataDto.setJwtDataToken(userService.register(user));
		
		return dataDto;
	}
	
	@GetMapping("user/login")
	public Object login(@RequestBody DataDto dataDto) {
		return userService.login(dataDto.getJwtDataToken());	
	}
	
	@GetMapping("user/auto-login")
	public Object autoLogin(@RequestBody DataDto dataDto) {
		
		return null;
	}
	
	@GetMapping("user/refresh-login")
	public Object refreshLogin(@RequestBody @JsonProperty("jwt") String jwtToken) {
		return null;		
	}
	
}
