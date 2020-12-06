package br.com.planilha.gastos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDto {

	@JsonProperty("device_id")
	private String deviceId;
	
	@JsonProperty("verified")
	private boolean verified;
	
	@JsonProperty("in_use")
	private boolean inUse;
	
	@JsonProperty("verification_code")
	private String verificationCode;
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String id) {
		this.deviceId = id;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
}
