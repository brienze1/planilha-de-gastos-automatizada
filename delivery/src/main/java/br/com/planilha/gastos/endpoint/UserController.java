package br.com.planilha.gastos.endpoint;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.planilha.gastos.adapter.UserControllerAdapter;
import br.com.planilha.gastos.dto.DataDto;
import br.com.planilha.gastos.dto.DeviceDto;
import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.dto.UserDto;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.Login;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.parse.DeviceDeliveryParse;
import br.com.planilha.gastos.parse.LoginDeliveryParse;
import br.com.planilha.gastos.parse.UserDeliveryParse;
import br.com.planilha.gastos.service.UserService;

@RestController
@RequestMapping("/v1/user")
public class UserController implements UserControllerAdapter {
	
	@Autowired
	private LoginDeliveryParse loginParse;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserDeliveryParse userParse;
	
	@Autowired
	private DeviceDeliveryParse deviceParse;
	
	/*
	 * done
	 */
	@PostMapping("/new")
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
	@PostMapping("/login")
	public DataDto login(@RequestBody LoginDto loginDto) {
		Login login = loginParse.toLogin(loginDto);
		
		System.out.println(LocalDateTime.now());
		
		DataDto dataDto = new DataDto();
		dataDto.setJwtAcessToken(userService.login(login));
		
		return dataDto;	
	}
	
	/*
	 * done
	 */
	@Override
	@PostMapping("/auto-login")
	public DataDto autoLogin(@RequestBody LoginDto loginDto) {
		Login login = loginParse.toLogin(loginDto);
		
		DataDto dataDto = new DataDto();
		dataDto.setJwtAcessToken(userService.autoLogin(login));
		
		return dataDto;	
	}

	/*
	 * done
	 */
	@PatchMapping("/config")
	public ResponseEntity<Void> configureUser(@RequestHeader(name = "Authorization", required = true) String accessToken, @RequestBody UserDto userDto) {
		User user = userParse.toUser(userDto);
		
		userService.configureUser(accessToken, user);
		
		return ResponseEntity.ok().build();
	}
	
	/*
	 * done
	 */
	@PatchMapping("/validate-device")
	public ResponseEntity<Void> validateDevice(@RequestHeader(name = "Authorization", required = true) String accessToken, @RequestBody DeviceDto deviceDto) {
		Device device = deviceParse.toDevice(deviceDto);
		
		userService.validateDevice(accessToken, device);
		
		return ResponseEntity.ok().build();
	}
	
	/*
	 * done
	 */
	@PostMapping("/register-device")
	public DataDto registerDevice(@RequestBody UserDto userDto) {
		User user = userParse.toUser(userDto);
		
		DataDto dataDto = new DataDto();
		dataDto.setJwtDataToken(userService.registerDevice(user));
		
		return dataDto;
	}
	
}
