package br.com.planilha.gastos.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.planilha.gastos.parse.AccessTokenIntegrationParse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RunWith(SpringRunner.class)
public class JwtTokenUtilsTest {

	@InjectMocks
	private JwtTokenUtils jwtTokenUtils;
	
	@Mock
	private MapperUtils mapper;
	
	@Mock
	private AccessTokenIntegrationParse accessTokenIntegrationParse;
	
	@Captor
	private ArgumentCaptor<TypeReference<Map<String, Object>>> typeCaptor;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private Map<String, Object> jwtBody;
	private Map<String, String> data; 
	private String userId;
	private String secret;
	private long expirationSeconds;
	
	@Before
	public void init() {
		userId = UUID.randomUUID().toString();
		secret = UUID.randomUUID().toString();
		expirationSeconds = new Random().nextInt(100);
		
		data = new HashMap<>();
		data.put("dataTeste1", UUID.randomUUID().toString());
		data.put("dataTeste2", UUID.randomUUID().toString());
		data.put("dataTeste3", UUID.randomUUID().toString());
		data.put("dataTeste4", UUID.randomUUID().toString());

		jwtBody = new HashMap<>();
		jwtBody.put("teste1", UUID.randomUUID().toString());
		jwtBody.put("teste2", UUID.randomUUID().toString());
		jwtBody.put("teste3", UUID.randomUUID().toString());
		jwtBody.put("teste4", UUID.randomUUID().toString());
		jwtBody.put("data", data);

	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void generateTest() throws JsonMappingException, JsonProcessingException {
		Mockito.when(mapper.map(Mockito.any(HashMap.class), Mockito.any(TypeReference.class))).thenReturn(jwtBody);
		
		String token = jwtTokenUtils.generate(userId, secret, jwtBody, expirationSeconds);
		
		Assert.assertNotNull(token);
		Assert.assertFalse(token.isBlank());

		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
		Object jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(token).getBody();
		
		Map<String, Object> object = objectMapper.readValue(objectMapper.writeValueAsString(jwt), new TypeReference<Map<String, Object>>() {});
		Mockito.when(mapper.map(Mockito.any(Object.class), Mockito.any(TypeReference.class))).thenReturn(object);
		Mockito.when(mapper.readValue(object.get("payload"), HashMap.class)).thenReturn((HashMap<String, Object>) object.get("payload"));	
		
		Map<String, Object> body = jwtTokenUtils.decode(token, secret, HashMap.class);
		
		Assert.assertNotNull(body);
		Assert.assertEquals(jwtBody.get("teste1"), body.get("teste1"));
		Assert.assertEquals(jwtBody.get("teste2"), body.get("teste2"));
		Assert.assertEquals(jwtBody.get("teste3"), body.get("teste3"));
		Assert.assertEquals(jwtBody.get("teste4"), body.get("teste4"));
		Assert.assertEquals(((Map<String, String>) jwtBody.get("data")).get("dataTeste1"), ((Map<String, String>) body.get("data")).get("dataTeste1"));
		Assert.assertEquals(((Map<String, String>) jwtBody.get("data")).get("dataTeste2"), ((Map<String, String>) body.get("data")).get("dataTeste2"));
		Assert.assertEquals(((Map<String, String>) jwtBody.get("data")).get("dataTeste3"), ((Map<String, String>) body.get("data")).get("dataTeste3"));
		Assert.assertEquals(((Map<String, String>) jwtBody.get("data")).get("dataTeste4"), ((Map<String, String>) body.get("data")).get("dataTeste4"));
		
	}
	
	@Test
	public void generateAccessTokenTest() {
		
	}
	
	@Test
	public void decodeTest() {
		
	}
	
	@Test
	public void decodeJwtNoVerificationTest() {
		
	}
	
	@Test
	public void getAcessTokenTest() {
		
	}
	
	@Test
	public void isValidTokenTest() {
		
	}
	
}
