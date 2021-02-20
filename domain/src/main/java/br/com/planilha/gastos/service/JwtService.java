package br.com.planilha.gastos.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.planilha.gastos.entity.AccessToken;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.UserValidationException;
import br.com.planilha.gastos.port.JwtAdapter;

@Service
public class JwtService {
	
	private static final String SUBJECT = "sub";
	
	@Autowired
	private JwtAdapter jwtAdapter;
	
	@Autowired
	private UserService userService;

	public String generate(String subject, String secret, Object payload) {
		return jwtAdapter.generate(subject, secret, payload, 0);
	}

	public <T> T decodeAndVerify(String jwt, Class<T> clazz) {
		Map<String, Object> jwtBody = jwtAdapter.decodeJwtNoVerification(jwt);
		
		String userId = String.valueOf(jwtBody.get(SUBJECT));
		
		User user = userService.findById(userId);
		
		return jwtAdapter.decode(jwt, user.getSecret(), clazz);
	}

	public String generateAcessToken(User user) {
		return jwtAdapter.generateAccessToken(user, 300);
	}

	public User verifyAcessToken(String token) {
		AccessToken accessToken = jwtAdapter.getAcessToken(token);
		
		User user = userService.findById(accessToken.getUserId());
		
		user.setInUseDevice(accessToken.getDeviceId());
		
		if(jwtAdapter.isValidToken(token, user.getSecret()) && user.inUseDeviceId().equals(accessToken.getDeviceId())) {
			return user;
		}
 
		throw new UserValidationException("Dispositivo que fez a requisicao nao esta logado");
	}

	
}
