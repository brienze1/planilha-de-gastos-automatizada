package br.com.planilha.gastos.entity;

import java.util.List;

import br.com.planilha.gastos.exception.DeviceException;

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
	public String inUseDeviceId() {
		for (Device device : devices) {
			if(device.isInUse()) {
				return device.getDeviceId();
			}
		}
		return null;
	}
	public Device inUseDevice() {
		for (Device device : devices) {
			if(device.isInUse()) {
				return device;
			}
		}
		return null;
	}
	public Device setInUseDevice(String deviceId) {
		Device returnDevice = null;
		boolean deviceExists = false;
		for (Device device : devices) {
			if(device.getDeviceId().equals(deviceId)) {
				device.setInUse(true);
				returnDevice = device;
				deviceExists = true;
			} else if(device.isInUse()) {
				device.setInUse(false);
			}
		}		
		
		if(!deviceExists) {
			throw new DeviceException("Dispositivo nao cadastrado para este usuario");
		}
		
		return returnDevice;
	}
	
}
