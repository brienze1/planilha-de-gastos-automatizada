package br.com.planilha.gastos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class UserDto {
	
	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("device")
	private DeviceDto device;

	@JsonProperty("auto_login")
	private boolean autoLogin;

	private String secret;
	private boolean validEmail;
	
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
	public DeviceDto getDevice() {
		return device;
	}
	public void setDevice(DeviceDto device) {
		this.device = device;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public boolean isValidEmail() {
		return validEmail;
	}
	public void setValidEmail(boolean validEmail) {
		this.validEmail = validEmail;
	}
	public boolean isAutoLogin() {
		return autoLogin;
	}
	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}
	
}
