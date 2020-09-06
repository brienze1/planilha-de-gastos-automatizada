package br.com.planilha.gastos.port;

public interface JwtTokenUtilsAdapter {

	String generate(String userId, String secret, Object payload);

	<T> T decodeJWT(String jwtToken, String secret, Class<T> clazz);

}
