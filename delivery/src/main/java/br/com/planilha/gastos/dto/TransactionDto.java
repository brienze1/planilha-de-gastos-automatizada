package br.com.planilha.gastos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionDto {

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("tipo")
	private String tipo;
	
	@JsonProperty("valor")
	private BigDecimal valor;
	
	@JsonProperty("data")
	private LocalDateTime data;
	
	@JsonProperty("meio_de_pagamento")
	private String meioDePagamento;
	
	@JsonProperty("localizacao")
	private String localizacao;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public String getMeioDePagamento() {
		return meioDePagamento;
	}
	public void setMeioDePagamento(String meioDePagamento) {
		this.meioDePagamento = meioDePagamento;
	}
	public String getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	
}
