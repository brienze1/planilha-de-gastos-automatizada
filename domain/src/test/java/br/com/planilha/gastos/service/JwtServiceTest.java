package br.com.planilha.gastos.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.builder.JwtBuilder;
import br.com.planilha.gastos.entity.AccessToken;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.UserValidationException;
import br.com.planilha.gastos.port.JwtAdapter;

@ExtendWith(SpringExtension.class)
public class JwtServiceTest {

	@InjectMocks
	private JwtService jwtService;
	
	@Mock
	private JwtBuilder jwtBuilder;

	@Mock
	private JwtAdapter jwtAdapter;
	
	@Mock
	private UserService userService;
	
	private Map<String, Object> payload;
	private String jwt;
	private User user;
	private AccessToken accessToken;
	
	@BeforeEach
	public void init() {
		jwt = UUID.randomUUID().toString();
		payload = new HashMap<>();
		
		accessToken = new AccessToken();
		accessToken.setDeviceId(UUID.randomUUID().toString());
		accessToken.setName(UUID.randomUUID().toString());
		accessToken.setUserId(UUID.randomUUID().toString());
		
		user = new User();
		user.setDevices(new ArrayList<>());
		user.getDevices().add(new Device(accessToken.getDeviceId()));
		user.setInUseDevice(accessToken.getDeviceId());
		user.setId(accessToken.getUserId());
		user.setSecret(UUID.randomUUID().toString());
	}
	
	@Test
	public void generateTest() {
		Mockito.when(jwtBuilder.build(user)).thenReturn(payload);
		Mockito.when(jwtAdapter.generate(user.getId(), user.getSecret(), payload, 0)).thenReturn(jwt);
		
		String token = jwtService.generate(user);
		
		Assertions.assertNotNull(token);
		Assertions.assertFalse(token.isBlank());
		Assertions.assertEquals(jwt, token);
	}
	
	@Test
	public void generateAcessTokenTest() {
		Mockito.when(jwtAdapter.generateAccessToken(user, 300)).thenReturn(jwt);
		
		String token = jwtService.generateAcessToken(user, user.inUseDeviceId());
		
		Assertions.assertNotNull(token);
		Assertions.assertFalse(token.isBlank());
		Assertions.assertEquals(jwt, token);
	}
	
	@Test
	public void verifyAcessTokenTest() {
		Mockito.when(jwtAdapter.getAcessToken(jwt)).thenReturn(accessToken);
		Mockito.when(userService.findById(accessToken.getUserId())).thenReturn(user);
		Mockito.when(jwtAdapter.isValidToken(jwt, user.getSecret())).thenReturn(true);
		
		User verified = jwtService.verifyAcessToken(jwt);
		
		Mockito.verify(jwtAdapter).getAcessToken(jwt);
		Mockito.verify(userService).findById(accessToken.getUserId());
		Mockito.verify(jwtAdapter).isValidToken(jwt, user.getSecret());
		
		Assertions.assertEquals(verified.getId(), user.getId());
	}
	
	@Test
	public void verifyAcessTokenTokenInvalidoErrorTest() {
		Mockito.when(jwtAdapter.getAcessToken(jwt)).thenReturn(accessToken);
		Mockito.when(userService.findById(accessToken.getUserId())).thenReturn(user);
		Mockito.when(jwtAdapter.isValidToken(jwt, user.getSecret())).thenReturn(false);
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> jwtService.verifyAcessToken(jwt), 
				"Token Invalido.");
		
		Mockito.verify(jwtAdapter).getAcessToken(jwt);
		Mockito.verify(userService).findById(accessToken.getUserId());
		Mockito.verify(jwtAdapter).isValidToken(jwt, user.getSecret());
	}
	
	@Test
	public void verifyAcessTokenDispositivoNaoLogadoErrorTest() {
		String newDeviceId = UUID.randomUUID().toString();
		user.getDevices().add(new Device(newDeviceId));
		user.setInUseDevice(newDeviceId);
		
		Mockito.when(jwtAdapter.getAcessToken(jwt)).thenReturn(accessToken);
		Mockito.when(userService.findById(accessToken.getUserId())).thenReturn(user);
		Mockito.when(jwtAdapter.isValidToken(jwt, user.getSecret())).thenReturn(true);
		
		Assertions.assertThrows(
				UserValidationException.class, 
				() -> jwtService.verifyAcessToken(jwt), 
				"Dispositivo que fez a requisicao nao esta logado.");
		
		Mockito.verify(jwtAdapter).getAcessToken(jwt);
		Mockito.verify(userService).findById(accessToken.getUserId());
		Mockito.verify(jwtAdapter).isValidToken(jwt, user.getSecret());
	}
	
}
