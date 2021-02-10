package br.com.planilha.gastos.parse;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.dto.AccessTokenDtoi;
import br.com.planilha.gastos.entity.AccessToken;

@Component
public class AccessTokenIntegrationParse {

	public AccessToken toAccessToken(AccessTokenDtoi accessTokenDtoi) {
		AccessToken accessToken = new AccessToken();
		
		if(accessTokenDtoi != null) {
			accessToken.setDeviceId(accessTokenDtoi.getDeviceId());
			accessToken.setName(accessTokenDtoi.getName());
			accessToken.setUserId(accessTokenDtoi.getUserId());
		}
		
		return accessToken;
	}

}
