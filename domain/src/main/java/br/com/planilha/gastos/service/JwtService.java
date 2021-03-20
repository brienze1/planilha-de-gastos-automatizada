package br.com.planilha.gastos.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.planilha.gastos.builder.JwtBuilder;
import br.com.planilha.gastos.entity.AccessToken;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.UserValidationException;
import br.com.planilha.gastos.port.JwtAdapter;

@Service
public class JwtService {
	
	@Autowired
	private JwtAdapter jwtAdapter;
	
	@Autowired
	private JwtBuilder jwtBuilder;
	
	@Autowired
	private UserService userService;

	public String generate(User user) {
		Map<String, Object> payload = jwtBuilder.build(user);
		
		return jwtAdapter.generate(user.getId(), user.getSecret(), payload, 0);
	}

	public String generateAcessToken(User user, String deviceId) {
		user.setInUseDevice(deviceId);
		
		userService.update(user);
		
		return jwtAdapter.generateAccessToken(user, 300);
	}

	public User verifyAcessToken(String token) {
		AccessToken accessToken = jwtAdapter.getAcessToken(token);
		
		User user = userService.findById(accessToken.getUserId());
		
		if(!jwtAdapter.isValidToken(token, user.getSecret())) {
			throw new UserValidationException("Token Invalido.");
		} else if(!user.inUseDeviceId().equals(accessToken.getDeviceId())) {
			throw new UserValidationException("Dispositivo que fez a requisicao nao esta logado.");
		}
 
		return user;
	}

	
}
