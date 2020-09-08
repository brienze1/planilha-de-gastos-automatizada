package br.com.planilha.gastos.endpoint;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.com.planilha.gastos.dto.DataDto;
import br.com.planilha.gastos.port.JwtAdapter;

@RestController
public class JwtController {
	
	@Autowired
	private JwtAdapter jwtTokenUtils;
	
	//classe para gerar jwt com payload desejado (utilizado para testes)
	@PostMapping("jwt/encode")
	public DataDto encode(@RequestBody Map<String, Object> mapaDados, 
			@RequestHeader(name = "user-id", required = true) String userId, 
			@RequestHeader(name = "secret", required = true) String secret) {
		
		DataDto dataDto = new DataDto();
		dataDto.setJwtDataToken(jwtTokenUtils.generate(userId, secret, mapaDados, 0));
		
		return dataDto;
	}
	
	//classe para decodificar jwt com payload desejado (utilizado para testes)
	@PostMapping("jwt/decode/no-verification")
	public Object decodeNoVerification(@RequestBody DataDto dataDto) {
		return jwtTokenUtils.decodeJwtNoVerification(dataDto.getJwtDataToken());
	}
	
	//classe para decodificar jwt com payload desejado (utilizado para testes)
	@PostMapping("jwt/decode/with-verification")
	public Object decodeWithVerification(@RequestBody DataDto dataDto, @RequestHeader(name = "secret", required = true) String secret) {
		return jwtTokenUtils.decode(dataDto.getJwtDataToken(), secret, Object.class);
	}
	
}
