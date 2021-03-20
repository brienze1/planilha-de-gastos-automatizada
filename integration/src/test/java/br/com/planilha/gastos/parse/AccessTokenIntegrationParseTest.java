package br.com.planilha.gastos.parse;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.dto.AccessTokenDtoi;
import br.com.planilha.gastos.entity.AccessToken;

@RunWith(SpringRunner.class)
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
		
		Assert.assertEquals(acessTokenDtoi.getDeviceId(), accessToken.getDeviceId());
		Assert.assertEquals(acessTokenDtoi.getName(), accessToken.getName());
		Assert.assertEquals(acessTokenDtoi.getUserId(), accessToken.getUserId());
	}
	
	@Test
	public void toAccessTokenNullTest() {
		AccessToken accessToken = accessTokenIntegrationParse.toAccessToken(null);
		
		Assert.assertNotNull(accessToken);
	}
	
}
