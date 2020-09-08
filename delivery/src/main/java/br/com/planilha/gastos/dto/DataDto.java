package br.com.planilha.gastos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataDto {

	@JsonProperty("data")
	private String jwtDataToken;

	public String getJwtDataToken() {
		return jwtDataToken;
	}
	public void setJwtDataToken(String jwtDataToken) {
		this.jwtDataToken = jwtDataToken;
	}
	
}
