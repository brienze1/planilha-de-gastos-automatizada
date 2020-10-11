package br.com.planilha.gastos.entity;

import java.util.List;

public class User {

	private String id;
	private String email;
	private String password;
	private String lastName;
	private String firstName;
	private String secret;
	private List<Device> devices;
	private boolean validEmail;
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
	public boolean isValidEmail() {
		return validEmail;
	}
	public void setValidEmail(boolean validEmail) {
		this.validEmail = validEmail;
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
	public List<Device> getDevices() {
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	public boolean isAutoLogin() {
		return autoLogin;
	}
	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}
	public String getInUseDeviceId() {
		for (Device device : devices) {
			if(device.isInUse()) {
				return device.getId();
			}
		}
		return null;
	}
	public Device getInUseDevice() {
		for (Device device : devices) {
			if(device.isInUse()) {
				return device;
			}
		}
		return null;
	}
	public Device setInUseDevice(String deviceId) {
		for (Device device : devices) {
			if(device.getId().equals(deviceId)) {
				device.setInUse(true);
				return device;
			} else if(device.isInUse()) {
				device.setInUse(false);
			}
		}			
		return null;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", secret=");
		builder.append(secret);
		builder.append(", devices=");
		builder.append(devices);
		builder.append(", validEmail=");
		builder.append(validEmail);
		builder.append(", autoLogin=");
		builder.append(autoLogin);
		builder.append("]");
		return builder.toString();
	}
	
}
