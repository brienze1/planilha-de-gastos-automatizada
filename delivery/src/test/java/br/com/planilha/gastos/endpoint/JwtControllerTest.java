package br.com.planilha.gastos.endpoint;

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

import br.com.planilha.gastos.dto.DataDto;
import br.com.planilha.gastos.port.JwtAdapter;

@ExtendWith(SpringExtension.class)
public class JwtControllerTest {

	@InjectMocks
	private JwtController jwtController;
	
	@Mock
	private JwtAdapter jwtTokenUtils;
	
	private String userId;
	private String secret;
	private Map<String, Object> mapaDados;
	private String token;
	private DataDto dataDto;
	
	@BeforeEach
	public void init() {
		userId = UUID.randomUUID().toString();

		secret = UUID.randomUUID().toString();
		
		mapaDados = new HashMap<>();
		mapaDados.put("teste", UUID.randomUUID().toString());
		
		token = UUID.randomUUID().toString();
		
		dataDto = new DataDto();
		dataDto.setJwtDataToken(token);
	}
	
	@Test
	public void encodeTest() {
		Mockito.when(jwtTokenUtils.generate(userId, secret, mapaDados, 0)).thenReturn(token);
		
		DataDto dataDto = jwtController.encode(mapaDados, userId, secret);
		
		Mockito.verify(jwtTokenUtils).generate(userId, secret, mapaDados, 0);
		
		Assertions.assertNotNull(dataDto);
		Assertions.assertNotNull(dataDto.getJwtDataToken());
		Assertions.assertFalse(dataDto.getJwtDataToken().isBlank());
		Assertions.assertEquals(token, dataDto.getJwtDataToken());
	}
	
	@Test
	public void decodeNoVerificationTest() {
		Mockito.when(jwtTokenUtils.decodeJwtNoVerification(dataDto.getJwtDataToken())).thenReturn(mapaDados);
		 
		Map<String, Object> response = jwtController.decodeNoVerification(dataDto);
		
		Mockito.verify(jwtTokenUtils).decodeJwtNoVerification(dataDto.getJwtDataToken());
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(mapaDados.get("teste"), response.get("teste"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void decodeWithVerificationTest() {
		Mockito.when(jwtTokenUtils.decode(dataDto.getJwtDataToken(), secret, Object.class)).thenReturn(mapaDados);
		 
		Map<String, Object> response = (Map<String, Object>) jwtController.decodeWithVerification(dataDto, secret);
		
		Mockito.verify(jwtTokenUtils).decode(dataDto.getJwtDataToken(), secret, Object.class);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(mapaDados.get("teste"), response.get("teste"));
	}
	
}
