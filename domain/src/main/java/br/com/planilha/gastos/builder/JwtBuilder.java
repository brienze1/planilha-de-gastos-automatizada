package br.com.planilha.gastos.builder;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.entity.User;

@Component
public class JwtBuilder {

	public Map<String, Object> build(User user) {
		Map<String, Object> devicePayload = new HashMap<>();
		devicePayload.put("device_id", user.inUseDevice().getDeviceId());
		devicePayload.put("verification_code", user.inUseDevice().getVerificationCode());
		devicePayload.put("in_use", user.inUseDevice().isInUse());
		devicePayload.put("verified", user.inUseDevice().isVerified());
		
		Map<String, Object> payload = new HashMap<>();
		payload.put("id", user.getId());
		payload.put("email", user.getEmail());
		payload.put("password", user.getPassword());
		payload.put("last_name", user.getLastName());
		payload.put("first_name", user.getFirstName());
		payload.put("secret", user.getSecret());
		payload.put("device", devicePayload);
		payload.put("valid_email", user.isValidEmail());
		payload.put("auto_login", user.isAutoLogin());
		
		return payload;
	}
	
}
