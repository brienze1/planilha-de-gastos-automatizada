package br.com.planilha.gastos.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.builder.JwtBuilder;
import br.com.planilha.gastos.entity.AccessToken;
import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.UserValidationException;
import br.com.planilha.gastos.port.JwtAdapter;

@RunWith(SpringRunner.class)
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
	
	@Before
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
		
		Assert.assertNotNull(token);
		Assert.assertFalse(token.isBlank());
		Assert.assertEquals(jwt, token);
	}
	
	@Test
	public void generateAcessTokenTest() {
		Mockito.when(jwtAdapter.generateAccessToken(user, 300)).thenReturn(jwt);
		
		String token = jwtService.generateAcessToken(user, user.inUseDeviceId());
		
		Assert.assertNotNull(token);
		Assert.assertFalse(token.isBlank());
		Assert.assertEquals(jwt, token);
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
		
		Assert.assertEquals(verified.getId(), user.getId());
	}
	
	@Test(expected = UserValidationException.class)
	public void verifyAcessTokenTokenInvalidoErrorTest() {
		Mockito.when(jwtAdapter.getAcessToken(jwt)).thenReturn(accessToken);
		Mockito.when(userService.findById(accessToken.getUserId())).thenReturn(user);
		Mockito.when(jwtAdapter.isValidToken(jwt, user.getSecret())).thenReturn(false);
		
		try {
			jwtService.verifyAcessToken(jwt);
		} catch (UserValidationException e) {
			Assert.assertEquals("Token Invalido.", e.getMessage());
			
			throw e;
		}
		
		Mockito.verify(jwtAdapter).getAcessToken(jwt);
		Mockito.verify(userService).findById(accessToken.getUserId());
		Mockito.verify(jwtAdapter).isValidToken(jwt, user.getSecret());
	}
	
	@Test(expected = UserValidationException.class)
	public void verifyAcessTokenDispositivoNaoLogadoErrorTest() {
		String newDeviceId = UUID.randomUUID().toString();
		user.getDevices().add(new Device(newDeviceId));
		user.setInUseDevice(newDeviceId);
		
		Mockito.when(jwtAdapter.getAcessToken(jwt)).thenReturn(accessToken);
		Mockito.when(userService.findById(accessToken.getUserId())).thenReturn(user);
		Mockito.when(jwtAdapter.isValidToken(jwt, user.getSecret())).thenReturn(true);
		
		try {
			jwtService.verifyAcessToken(jwt);
		} catch (UserValidationException e) {
			Assert.assertEquals("Dispositivo que fez a requisicao nao esta logado.", e.getMessage());
			
			throw e;
		}
		
		Mockito.verify(jwtAdapter).getAcessToken(jwt);
		Mockito.verify(userService).findById(accessToken.getUserId());
		Mockito.verify(jwtAdapter).isValidToken(jwt, user.getSecret());
	}
	
}
