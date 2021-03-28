package br.com.planilha.gastos.parse;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.planilha.gastos.dto.AccessTokenDtoi;
import br.com.planilha.gastos.entity.AccessToken;

@ExtendWith(SpringExtension.class)
public class AccessTokenIntegrationParseTest {

	@InjectMocks
	private AccessTokenIntegrationParse accessTokenIntegrationParse;
	
	@Test
	public void toAccessTokenTest() {
		AccessTokenDtoi acessTokenDtoi = new AccessTokenDtoi();
		acessTokenDtoi.setDeviceId(UUID.randomUUID().toString());
		acessTokenDtoi.setName(UUID.randomUUID().toString());
		acessTokenDtoi.setUserId(UUID.randomUUID().toString());
		
		AccessToken accessToken = accessTokenIntegrationParse.toAccessToken(acessTokenDtoi);
		
		Assertions.assertEquals(acessTokenDtoi.getDeviceId(), accessToken.getDeviceId());
		Assertions.assertEquals(acessTokenDtoi.getName(), accessToken.getName());
		Assertions.assertEquals(acessTokenDtoi.getUserId(), accessToken.getUserId());
	}
	
	@Test
	public void toAccessTokenNullTest() {
		AccessToken accessToken = accessTokenIntegrationParse.toAccessToken(null);
		
		Assertions.assertNotNull(accessToken);
	}
	
}
