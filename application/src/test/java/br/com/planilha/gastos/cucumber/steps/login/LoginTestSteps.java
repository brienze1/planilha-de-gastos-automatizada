package br.com.planilha.gastos.cucumber.steps.login;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.planilha.gastos.dto.DataDto;
import br.com.planilha.gastos.dto.DeviceDto;
import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.dto.UserDto;
import br.com.planilha.gastos.repository.TransactionRepository;
import br.com.planilha.gastos.repository.UserRepository;
import br.com.planilha.gastos.utils.JwtTokenUtils;
import br.com.planilha.gastos.utils.MapperUtils;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import io.cucumber.datatable.DataTable;

public class LoginTestSteps {

	@LocalServerPort
	private int serverPort;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MapperUtils mapper;
	
	private LoginDto loginDto;
	private UserDto userDto;
	private ResponseEntity<DataDto> response;
	private HttpStatusCodeException e;
	private TypeReference<Map<String, Object>> typeReference;
	
	@PostConstruct
	public void init() {
		transactionRepository.deleteAll();
		userRepository.deleteAll();
		
		e = null;
		
		response = null;
		
		typeReference = new TypeReference<Map<String, Object>>() {};
	}
	
	@Dado("que o dispositivo do usuario informou os seguintes dados no login")
	public void que_o_dispositivo_do_usuario_informou_os_seguintes_dados_no_login(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		loginDto = new LoginDto();
		loginDto.setDeviceId(mapa.get("device_id"));
		loginDto.setEmail(mapa.get("email"));
		loginDto.setPassword(mapa.get("password"));
		
		if(mapa.containsKey("password")) {
			Assert.assertNotNull(loginDto.getPassword());
		} else {
			Assert.assertNull(loginDto.getPassword());
		}
		if(mapa.containsKey("email")) {
			Assert.assertNotNull(loginDto.getEmail());
		} else {
			Assert.assertNull(loginDto.getEmail());
		}
		if(mapa.containsKey("device_id")) {
			Assert.assertNotNull(loginDto.getDeviceId());
		} else {
			Assert.assertNull(loginDto.getDeviceId());
		}
	}

	@E("que ja exista um usuario cadastrado com os dados abaixo")
	public void que_ja_exista_um_usuario_cadastrado_com_os_dados_abaixo(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		userDto = new UserDto();
		userDto.setEmail(mapa.get("email"));
		userDto.setFirstName(mapa.get("first_name"));
		userDto.setLastName(mapa.get("last_name"));
		userDto.setPassword(mapa.get("password"));
		userDto.setDevice(new DeviceDto());
		userDto.getDevice().setDeviceId(mapa.get("device_id"));
		
		Assert.assertNotNull(userDto.getPassword());
		Assert.assertNotNull(userDto.getEmail());
		Assert.assertNotNull(userDto.getFirstName());
		Assert.assertNotNull(userDto.getLastName());
		Assert.assertNotNull(userDto.getDevice());
		Assert.assertNotNull(userDto.getDevice().getDeviceId());
		
		HttpEntity<?> httpEntity = new HttpEntity<>(userDto);
		
		response = restTemplate.exchange("http://localhost:" + serverPort + "/v1/user/new", 
				HttpMethod.POST, 
				httpEntity, 
				DataDto.class);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatusCodeValue());
	}

	@Quando("for solicitado o login")
	public void for_solicitado_o_login() {
		HttpEntity<?> httpEntity = new HttpEntity<>(loginDto);
		
		try {
			response = restTemplate.exchange("http://localhost:" + serverPort + "/v1/user/login", 
					HttpMethod.POST, 
					httpEntity, 
					DataDto.class);
			
			Assert.assertNotNull(response);
		} catch(HttpStatusCodeException ex) {
			e = ex;
		}
	}

	@Então("deve ser retornado um token de acesso") 
	public void deve_ser_retornado_um_token_de_acesso() {
		Assert.assertNotNull(response.getBody().getJwtAcessToken());
	}

	@E("o status que foi retornado deve ser {int}")
	public void o_status_que_foi_retornado_deve_ser(Integer statusCode) {
		if(e != null) {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(e.getRawStatusCode()));
		} else {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(response.getStatusCodeValue()));
		}
	}

	@SuppressWarnings("unchecked")
	@E("o token de acesso deve conter os campos abaixo preenchidos dentro do payload")
	public void o_token_de_acesso_deve_conter_os_campos_abaixo_preenchidos_dentro_do_payload(DataTable dataTable) {
		Map<String, Object> mapaResponse = jwtTokenUtils.decodeJwtNoVerification(response.getBody().getJwtAcessToken());
		
		List<String> listaCampos = dataTable.asList();
		
		for (String campo : listaCampos) {
			Assert.assertNotNull(((Map<String, Object>) mapaResponse.get("payload")).get(campo));
		}
	}

	@E("o token deve conter uma data de expiracao valida")
	public void o_token_deve_conter_uma_data_de_expiracao_valida() {
		Map<String, Object> mapaResponse = jwtTokenUtils.decodeJwtNoVerification(response.getBody().getJwtAcessToken());
		
		Assert.assertTrue(new Date(System.currentTimeMillis()).before(new Date(Long.valueOf(Long.valueOf((Integer) mapaResponse.get("exp"))) * 1000)));
	}

	@Então("deve ser retornado uma exception com a mensagem {string}")
	public void deve_ser_retornado_uma_exception_com_a_mensagem(String mensagem) {
		Map<String, Object> errorMap = mapper.map(e.getResponseBodyAsString(), typeReference);
		
		Assert.assertEquals(mensagem, errorMap.get("message"));
	}
	
}
