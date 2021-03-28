package br.com.planilha.gastos.port;

import java.util.Map;

import br.com.planilha.gastos.entity.AccessToken;
import br.com.planilha.gastos.entity.User;

public interface JwtAdapter {

	<T> T decode(String jwt, String secret, Class<T> clazz);

	Map<String, Object> decodeJwtNoVerification(String jwt);

	String generate(String userId, String secret, Object payload, long expirationSeconds);

	String generateAccessToken(User user, long expirationSeconds);

	AccessToken getAcessToken(String token);

	boolean isValidToken(String token, String secret);

}
