package br.com.planilha.gastos.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.JwtAdapter;

@Service
public class JwtService {
	
	private static final String SUBJECT = "sub";
	
	@Autowired
	private JwtAdapter jwtAdapter;
	
	@Autowired
	private UserService userService;

	public String generate(String subject, String secret, User user) {
		return jwtAdapter.generate(subject, secret, user, 0);
	}

	public <T> T decodeAndVerify(String jwt, Class<T> clazz) {
		//Decodifica o body do jwt
		Map<String, Object> jwtBody = jwtAdapter.decodeJwtNoVerification(jwt);
		
		//Busca o id do usuario que gerou o jwt
		String userId = String.valueOf(jwtBody.get(SUBJECT));
		
		//Busca o usuario 
		User user = userService.findById(userId);
		
		//Decodifica, valida e retorna o payload solicitado na forma do objeto clazz
		return jwtAdapter.decode(jwt, user.getSecret(), clazz);
	}

	public String generateAcessToken(User user) {
		return jwtAdapter.generateAccessToken(user, 300);
	}

	
}
