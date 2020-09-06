package br.com.planilha.gastos.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.planilha.gastos.port.JwtTokenUtilsAdapter;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtils implements JwtTokenUtilsAdapter {
	
	private static final String ISSUER = "brienze_api";
	private static final String PAYLOAD = "payload";
	private static final long EXPIRATION_MILLIS = 600000;
	
	@Autowired
	private MapperUtils mapper;

	@Override
	public String generate(String userId, String secret, Object payload) {
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
        long expMillis = nowMillis + EXPIRATION_MILLIS;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
	  
	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return builder.compact();
	}
	
	@Override
	public <T> T decodeJWT(String jwtToken, String secret, Class<T> clazz) {
		//We will sign our JWT with our ApiKey secret
	    SecretKey key = Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(secret));
		
	    //This line will throw an exception if it is not a signed JWS (as expected)
		 Object body = Jwts.parserBuilder()
				 .setSigningKey(key)
				 .build()
				 .parse(jwtToken)
				 .getBody();
			 
	    return mapper.readValue(body, clazz);
	}
	
}
