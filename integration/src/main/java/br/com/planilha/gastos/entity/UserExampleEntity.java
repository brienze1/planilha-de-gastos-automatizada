package br.com.planilha.gastos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="TB_TEMPLATE_USER_EXAMPLE")
public class UserExampleEntity {

	@Id 
	@Column(name="USER_EX_ID")
	private String id;
	
	@NotNull
	@Length(min=10, max=100, message="O tamanho do nome deve ser entre {min} e {max} caracteres.")
	private String email;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="FIRST_NAME")
	private String firstName;
	
	public UserExampleEntity() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
}
