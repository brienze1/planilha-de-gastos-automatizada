package br.com.planilha.gastos.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.planilha.gastos.dto.AccessTokenDtoi;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.port.JwtAdapter;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtils implements JwtAdapter {
	
	private static final String ISSUER = "brienze_api";
	private static final String PAYLOAD = "payload";
	
	@Autowired
	private MapperUtils mapper;

	@Override
	public String generate(String userId, String secret, Object payload, long expirationSeconds) {
		//Transforma o objeto em um mapa chave valor, que depois sera transofmrado em um Json
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
		Map<String, Object> claims = mapper.map(payload, typeReference);
		
		long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);

	    //We will sign our JWT with our ApiKey secret
	    SecretKey key = Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(secret));
	    
	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
	            .setIssuedAt(now)
	            .setSubject(userId)
	            .setIssuer(ISSUER)
	            .claim(PAYLOAD, claims)
	            .signWith(key, SignatureAlgorithm.HS256);
	  
		//if it has been specified, let's add the expiration
		if(expirationSeconds != 0) {
			long expMillis = nowMillis + TimeUnit.SECONDS.toMillis(expirationSeconds);
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
	    
	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return builder.compact();
	}
	
	@Override
	public String generateAccessToken(User user, long expirationSeconds) {
		AccessTokenDtoi accessTokenDtoi = new AccessTokenDtoi();
		accessTokenDtoi.setName(user.getFirstName() + " " + user.getLastName());
		accessTokenDtoi.setDeviceId(user.getInUseDeviceId());
		accessTokenDtoi.setUserId(user.getId());
		
		return generate(user.getId(), user.getSecret(), accessTokenDtoi, expirationSeconds);
	}
	
	@Override
	public <T> T decode(String jwt, String secret, Class<T> clazz) {
		//Cria chave a partir do secret
	    SecretKey key = Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(secret));
		
	    //Abre o token verificando a assinatura
		 Object body = Jwts.parserBuilder()
				 .setSigningKey(key)
				 .build()
				 .parse(jwt)
				 .getBody();
			 
		//Transforma o objeto em um mapa chave valor, que depois sera transofmrado em um Json
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
		Map<String, Object> mapaJwt = mapper.map(body, typeReference); 
		 
	    return mapper.readValue(mapaJwt.get("payload"), clazz);
	}
	
	@Override
	public Map<String, Object> decodeJwtNoVerification(String jwt) {
		//Pega a posicao do ultimo separador (".")
		int i = jwt.lastIndexOf('.');
		
		//Retira a assinatura do jwt (a parte depois do ultimo ".")
		String jwtWithoutSignature = jwt.substring(0, i+1);
		
		//Abre o token
		Object body = Jwts.parserBuilder()
				 .build()
				 .parse(jwtWithoutSignature)
				 .getBody();
		
		//Transforma o body em um mapa com os dados do objeto
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
		Map<String, Object> jwtBody = mapper.map(body, typeReference);

		//Retorna
		return jwtBody;
	}
	
}
