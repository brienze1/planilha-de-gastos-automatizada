package br.com.planilha.gastos.entity;

public class Device {

	private String id;
	private String deviceId;
	private boolean verified;
	private boolean inUse;
	private String verificationCode;
	
	public Device(String id){
		this.deviceId = id;
		this.verified = false;
	}
	public Device(){
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public boolean isVerified() {
		return verified;
	}
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Device [id=");
		builder.append(id);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append(", verified=");
		builder.append(verified);
		builder.append(", inUse=");
		builder.append(inUse);
		builder.append(", verificationCode=");
		builder.append(verificationCode);
		builder.append("]");
		return builder.toString();
	}
	
}