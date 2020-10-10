package br.com.planilha.gastos.entity;

public class Device {

	private String id;
	private boolean verified;
	private String verificationCode;
	
	public Device(String id){
		this.id = id;
		this.verified = false;
		
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
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
}
