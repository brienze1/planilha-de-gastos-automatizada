package br.com.planilha.gastos.dto;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class UserExampleDTO {
	
	@JsonProperty("email")
	@Email(regexp = ".@.\\..*", message = "Email should be valid")
	private String email;

	@JsonProperty("password")
	private String password;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("first_name")
	private String firstName;

	@ApiModelProperty(example = "example@email.com")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@ApiModelProperty(example = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@ApiModelProperty(example = "last name example")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@ApiModelProperty(example = "first name example")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
}
