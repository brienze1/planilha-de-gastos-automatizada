package br.com.planilha.gastos.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TB_TEMPLATE_USER_EXAMPLE")
public class UserEntity {

	@Id 
	@Column(name="USER_ID")
	private String id;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="SECRET")	
	private String secret;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DeviceEntity> devices;
	
	@Column(name="VALID_EMAIL")
	private boolean validEmail;
	
	@Column(name="AUTO_LOGIN")
	private boolean autoLogin;
	
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
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public Set<DeviceEntity> getDevices() {
		return devices;
	}
	public void setDevices(Set<DeviceEntity> devices) {
		this.devices = devices;
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
