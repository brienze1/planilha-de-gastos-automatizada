package br.com.planilha.gastos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TB_TEMPLATE_DEVICE")
public class DeviceEntity {

	@Id
	@Column(name="DEVICE_ID")
	private String id;
	
	@Column(name="VERIFIED")
	private boolean verified;

	@Column(name="IN_USE")
	private boolean inUse;
	
	@Column(name="VERIFICATION_CODE")
	private String verificationCode;

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
