package br.com.planilha.gastos.parse;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.dto.AccessTokenDtoi;
import br.com.planilha.gastos.entity.AccessToken;

@Component
public class AccessTokenIntegrationParse {

	public AccessToken toAccessToken(AccessTokenDtoi acessTokenDtoi) {
		AccessToken accessToken = new AccessToken();
		
		if(acessTokenDtoi != null) {
			accessToken.setDeviceId(acessTokenDtoi.getDeviceId());
			accessToken.setName(acessTokenDtoi.getName());
			accessToken.setUserId(acessTokenDtoi.getUserId());
		}
		
		return accessToken;
	}

}
