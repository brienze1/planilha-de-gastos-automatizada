package br.com.planilha.gastos.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.planilha.gastos.adapter.UserControllerAdapter;
import br.com.planilha.gastos.dto.DataDto;
import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.dto.UserDto;
import br.com.planilha.gastos.entity.Login;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.parse.LoginParse;
import br.com.planilha.gastos.parse.UserParse;
import br.com.planilha.gastos.service.UserService;

@RestController
public class UserController implements UserControllerAdapter {
	
	@Autowired
	private LoginParse loginParse;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserParse userParse;
	
	/*
	 * done
	 */
	@PostMapping("/user/new")
	@Override
	public DataDto register(@RequestBody UserDto userDto) {
		User user = userParse.toUser(userDto);
		
		DataDto dataDto = new DataDto();
		dataDto.setJwtDataToken(userService.register(user));
		
		return dataDto;
	}
	
	/*
	 * done
	 */
	@Override
	@GetMapping("user/login")
	public DataDto login(@RequestBody LoginDto loginDto) {
		Login login = loginParse.toLogin(loginDto);
		
		DataDto dataDto = new DataDto();
		dataDto.setJwtAcessToken(userService.login(login));
		
		return dataDto;	
	}
	
	/*
	 * done
	 */
	@Override
	@GetMapping("user/auto-login")
	public DataDto autoLogin(@RequestBody LoginDto loginDto) {
		Login login = loginParse.toLogin(loginDto);
		
		DataDto dataDto = new DataDto();
		dataDto.setJwtAcessToken(userService.autoLogin(login));
		
		return dataDto;	
	}

	/*
	 * not done
	 * maybe not needed
	 */
	@GetMapping("user/refresh-login")
	public Object refreshLogin(@RequestBody DataDto dataDto) {
		return null;		
	}
	
}
