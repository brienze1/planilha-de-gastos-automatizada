package br.com.planilha.gastos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_TEMPLATE_TRANSACTION")
public class TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TB_TEMPLATE_TRANSACTION_ID")
	private int id;
	
	@Column(name="TYPE")
	private String tipo;
	
	@Column(name="VALUE")
	private BigDecimal valor;
	
	@Column(name="DATE")
	private LocalDateTime data;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="WAY_OF_PAYMENT")
	private String meioDePagamento;
	
	@Column(name="LOCATION")
	private String localizacao;
	
	@ManyToOne
	private UserEntity user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	
}
