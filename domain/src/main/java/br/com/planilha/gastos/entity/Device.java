package br.com.planilha.gastos.entity;

public class Device {

	private String id;
	private boolean verified;
	private boolean inUse;
	private String verificationCode;
	
	public Device(String id){
		this.id = id;
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
