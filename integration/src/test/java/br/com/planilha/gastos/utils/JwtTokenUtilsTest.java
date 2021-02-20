package br.com.planilha.gastos.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import br.com.planilha.gastos.dto.AccessTokenDtoi;
import br.com.planilha.gastos.entity.AccessToken;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
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
	private User user; 
	private Map<String, Object> accessTokenDtoiMap;
	private AccessTokenDtoi accessTokenDtoi;
	private AccessToken accessToken;
	
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

		List<Device> devices = new ArrayList<>();
		devices.add(new Device(UUID.randomUUID().toString()));
		
		user = new User();
		user.setFirstName(UUID.randomUUID().toString());
		user.setLastName(UUID.randomUUID().toString());
		user.setDevices(devices);
		user.setId(UUID.randomUUID().toString());
		user.setSecret(secret);
		
		accessTokenDtoiMap = new HashMap<>();
		accessTokenDtoiMap.put("name", user.getFirstName() + " " + user.getLastName());
		accessTokenDtoiMap.put("device_id", user.inUseDeviceId());
		accessTokenDtoiMap.put("user_id", user.getId());
		
		accessTokenDtoi = new AccessTokenDtoi();
		accessTokenDtoi.setName(user.getFirstName() + " " + user.getLastName());
		accessTokenDtoi.setDeviceId(user.inUseDeviceId());
		accessTokenDtoi.setUserId(user.getId());
		
		accessToken = new AccessToken();
		accessToken.setDeviceId(accessTokenDtoi.getDeviceId());
		accessToken.setName(accessTokenDtoi.getName());
		accessToken.setUserId(accessTokenDtoi.getUserId());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void generateTest() throws JsonMappingException, JsonProcessingException {
		Mockito.when(mapper.map(Mockito.any(HashMap.class), Mockito.any(TypeReference.class))).thenReturn(jwtBody);
		
		String token = jwtTokenUtils.generate(userId, secret, jwtBody, expirationSeconds);
		
		Assert.assertNotNull(token);
		Assert.assertFalse(token.isBlank());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void generateAccessTokenTest() {
		Mockito.when(mapper.map(Mockito.any(AccessTokenDtoi.class), Mockito.any(TypeReference.class))).thenReturn(accessTokenDtoiMap);
		
		String token = jwtTokenUtils.generateAccessToken(user, 0);
		
		Assert.assertNotNull(token);
		Assert.assertFalse(token.isBlank());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void decodeTest() throws JsonMappingException, JsonProcessingException {
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void decodeJwtNoVerificationTest() throws JsonMappingException, JsonProcessingException {
		Mockito.when(mapper.map(Mockito.any(HashMap.class), Mockito.any(TypeReference.class))).thenReturn(jwtBody);
		
		String token = jwtTokenUtils.generate(userId, secret, jwtBody, expirationSeconds);
		
		Assert.assertNotNull(token);
		Assert.assertFalse(token.isBlank());

		int i = token.lastIndexOf('.');
		String jwtWithoutSignature = token.substring(0, i + 1);
		Object jwt = Jwts.parserBuilder().build().parse(jwtWithoutSignature).getBody();
		
		Map<String, Object> object = objectMapper.readValue(objectMapper.writeValueAsString(jwt), new TypeReference<Map<String, Object>>() {});
		Mockito.when(mapper.map(Mockito.any(Object.class), Mockito.any(TypeReference.class))).thenReturn(object);
		
		Map<String, Object> body = jwtTokenUtils.decodeJwtNoVerification(token);
		
		Assert.assertNotNull(body);
		Assert.assertEquals(jwtBody.get("teste1"), ((Map<String, Object>) body.get("payload")).get("teste1"));
		Assert.assertEquals(jwtBody.get("teste2"), ((Map<String, Object>) body.get("payload")).get("teste2"));
		Assert.assertEquals(jwtBody.get("teste3"), ((Map<String, Object>) body.get("payload")).get("teste3"));
		Assert.assertEquals(jwtBody.get("teste4"), ((Map<String, Object>) body.get("payload")).get("teste4"));
		Assert.assertEquals(((Map<String, String>) jwtBody.get("data")).get("dataTeste1"), ((Map<String, String>) ((Map<String, Object>) body.get("payload")).get("data")).get("dataTeste1"));
		Assert.assertEquals(((Map<String, String>) jwtBody.get("data")).get("dataTeste2"), ((Map<String, String>) ((Map<String, Object>) body.get("payload")).get("data")).get("dataTeste2"));
		Assert.assertEquals(((Map<String, String>) jwtBody.get("data")).get("dataTeste3"), ((Map<String, String>) ((Map<String, Object>) body.get("payload")).get("data")).get("dataTeste3"));
		Assert.assertEquals(((Map<String, String>) jwtBody.get("data")).get("dataTeste4"), ((Map<String, String>) ((Map<String, Object>) body.get("payload")).get("data")).get("dataTeste4"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAcessTokenTest() throws JsonMappingException, JsonProcessingException {
		Mockito.when(mapper.map(Mockito.any(AccessTokenDtoi.class), Mockito.any(TypeReference.class))).thenReturn(accessTokenDtoiMap);
		
		String token = jwtTokenUtils.generateAccessToken(user, 0);
		
		Assert.assertNotNull(token);
		Assert.assertFalse(token.isBlank());
		
		int i = token.lastIndexOf('.');
		String jwtWithoutSignature = token.substring(0, i + 1);
		Object jwt = Jwts.parserBuilder().build().parse(jwtWithoutSignature).getBody();
		
		Map<String, Object> object = objectMapper.readValue(objectMapper.writeValueAsString(jwt), new TypeReference<Map<String, Object>>() {});
		Mockito.when(mapper.map(Mockito.any(Object.class), Mockito.any(TypeReference.class))).thenReturn(object);
		Mockito.when(mapper.readValue(object.get("payload"), AccessTokenDtoi.class)).thenReturn(accessTokenDtoi);
		Mockito.when(accessTokenIntegrationParse.toAccessToken(accessTokenDtoi)).thenReturn(accessToken);
		
		AccessToken acessTokenResponse = jwtTokenUtils.getAcessToken(token);
		
		Assert.assertEquals(user.getId(), acessTokenResponse.getUserId());
		Assert.assertEquals(user.inUseDeviceId(), acessTokenResponse.getDeviceId());
		Assert.assertEquals(user.getFirstName() + " " + user.getLastName(), acessTokenResponse.getName());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void isValidTokenSuccessTest() {
		Mockito.when(mapper.map(Mockito.any(AccessTokenDtoi.class), Mockito.any(TypeReference.class))).thenReturn(accessTokenDtoiMap);
		
		String token = jwtTokenUtils.generateAccessToken(user, 0);
		
		Assert.assertNotNull(token);
		Assert.assertFalse(token.isBlank());

		boolean isValidToken = jwtTokenUtils.isValidToken(token, user.getSecret());
		
		Assert.assertTrue(isValidToken);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void isValidTokenFailiureTest() {
		Mockito.when(mapper.map(Mockito.any(AccessTokenDtoi.class), Mockito.any(TypeReference.class))).thenReturn(accessTokenDtoiMap);
		
		String token = jwtTokenUtils.generateAccessToken(user, 0);
		
		Assert.assertNotNull(token);
		Assert.assertFalse(token.isBlank());
		
		boolean isValidToken = jwtTokenUtils.isValidToken(token, UUID.randomUUID().toString());
				
		Assert.assertFalse(isValidToken);
	}
	
}
