package br.com.planilha.gastos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class DataDto {

	@JsonProperty("data")
	private String jwtDataToken;
	
	@JsonProperty("access_token")
	private String jwtAcessToken;

	public String getJwtDataToken() {
		return jwtDataToken;
	}
	public void setJwtDataToken(String jwtDataToken) {
		this.jwtDataToken = jwtDataToken;
	}
	public String getJwtAcessToken() {
		return jwtAcessToken;
	}
	public void setJwtAcessToken(String jwtAcessToken) {
		this.jwtAcessToken = jwtAcessToken;
	}
	
}
