package br.com.planilha.gastos.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

import br.com.planilha.gastos.port.PasswordUtilsAdapter;

@Component
public class PasswordUtils implements PasswordUtilsAdapter {

	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;
	private static final String SECRET_KEY_FACTORY_INSTANCE = "PBKDF2WithHmacSHA1";
	
	@Override
	public String encode(String password, String secret) {
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), secret.getBytes(), ITERATIONS, KEY_LENGTH);
        Arrays.fill(password.toCharArray(), Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_INSTANCE);
            return Base64.getEncoder().encodeToString(skf.generateSecret(spec).getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
	}
	
	@Override
	public boolean verifyPassword(String password, String encryptedPassword, String secret) {
		//Gerar nova senha encryptada com o mesmo secret
		String newEncryptedPassword = encode(password, secret);
		
		//valida se as senhas encryptadas sao iguais
		return newEncryptedPassword.equalsIgnoreCase(encryptedPassword);
	}
	
}
