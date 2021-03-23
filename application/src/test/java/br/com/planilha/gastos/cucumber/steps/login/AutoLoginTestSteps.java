package br.com.planilha.gastos.cucumber.steps.login;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.planilha.gastos.dto.DataDto;
import br.com.planilha.gastos.dto.DeviceDto;
import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.dto.UserDto;
import br.com.planilha.gastos.repository.DeviceRepository;
import br.com.planilha.gastos.repository.TransactionRepository;
import br.com.planilha.gastos.repository.UserRepository;
import br.com.planilha.gastos.utils.JwtTokenUtils;
import br.com.planilha.gastos.utils.MapperUtils;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import io.cucumber.datatable.DataTable;

public class AutoLoginTestSteps {
	
	@LocalServerPort
	private int serverPort;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MapperUtils mapper;
	
	private LoginDto loginDto;
	private UserDto userDto;
	private DeviceDto deviceDto;
	private ResponseEntity<DataDto> response;
	private HttpStatusCodeException e;
	private TypeReference<Map<String, Object>> typeReference;
	private String jwtDataToken;
	private String jwtAccessToken;
	
	@PostConstruct
	public void init() {
		userRepository.deleteAll();
		deviceRepository.deleteAll();
		transactionRepository.deleteAll();
		
		e = null;
		
		response = null;
		
		typeReference = new TypeReference<Map<String, Object>>() {};
	}
	
	@Dado("que o dispositivo do usuario informou os seguintes dados no auto login")
	public void que_o_dispositivo_do_usuario_informou_os_seguintes_dados_no_auto_login(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		loginDto = new LoginDto();
		loginDto.setDeviceId(mapa.get("device_id"));
		loginDto.setEmail(mapa.get("email"));
		
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

	@Dado("que um usuario foi cadastrado com os dados abaixo anteriormente")
	public void que_um_usuario_foi_cadastrado_com_os_dados_abaixo_anteriormente(DataTable dataTable) {
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
		
		ResponseEntity<DataDto> responseRegisrtration = exchangePost("/v1/user/new", null, userDto, DataDto.class);
		
		Assert.assertNotNull(responseRegisrtration);
		Assert.assertEquals(200, responseRegisrtration.getStatusCodeValue());
		Assert.assertNotNull(responseRegisrtration.getBody().getJwtDataToken());

		jwtDataToken = responseRegisrtration.getBody().getJwtDataToken();
	}

	@SuppressWarnings("unchecked")
	@Dado("que o dispositivo {string} ja foi verificado") 
	public void que_o_dispositivo_ja_foi_verificado(String deviceId) {
		LoginDto loginDto = new LoginDto();
		loginDto.setDeviceId(deviceId);
		loginDto.setEmail(userDto.getEmail());
		loginDto.setPassword(userDto.getPassword());
		
		ResponseEntity<DataDto> responseLogin = exchangePost("/v1/user/login", null, loginDto, DataDto.class);
		
		Assert.assertNotNull(responseLogin);
		Assert.assertEquals(200, responseLogin.getStatusCodeValue());
		Assert.assertNotNull(responseLogin.getBody().getJwtAcessToken());

		jwtAccessToken = responseLogin.getBody().getJwtAcessToken();
		
		Map<String, Object> mapaResponse = jwtTokenUtils.decodeJwtNoVerification(jwtDataToken);
		Map<String, Object> payload = (Map<String, Object>) mapaResponse.get("payload");
		
		deviceDto = new DeviceDto();
		deviceDto.setDeviceId(deviceId);
		deviceDto.setVerificationCode(((Map<String, String>) payload.get("device")).get("verification_code"));
		
		ResponseEntity<Void> responseValidation = exchangePatch("/v1/user/validate-device", jwtAccessToken, deviceDto, Void.class);
		
		Assert.assertNotNull(responseValidation);
		Assert.assertEquals(200, responseValidation.getStatusCodeValue());
	}

	@Dado("que o auto login foi setado como {string} nas configuracoes desse usuario")
	public void que_o_auto_login_foi_setado_como_nas_configuracoes_desse_usuario(String isAutoLogin) {
		LoginDto loginDto = new LoginDto();
		loginDto.setDeviceId(userDto.getDevice().getDeviceId());
		loginDto.setEmail(userDto.getEmail());
		loginDto.setPassword(userDto.getPassword());
		
		ResponseEntity<DataDto> responseLogin = exchangePost("/v1/user/login", null, loginDto, DataDto.class);
		
		Assert.assertNotNull(responseLogin);
		Assert.assertEquals(200, responseLogin.getStatusCodeValue());
		Assert.assertNotNull(responseLogin.getBody().getJwtAcessToken());

		jwtAccessToken = responseLogin.getBody().getJwtAcessToken();
		
		UserDto userDto = new UserDto();
		userDto.setAutoLogin(Boolean.valueOf(isAutoLogin));
		
		ResponseEntity<Void> responseConfig = exchangePatch("/v1/user/config", jwtAccessToken, userDto, Void.class);
		
		Assert.assertNotNull(responseConfig);
		Assert.assertEquals(200, responseConfig.getStatusCodeValue());
	}

	@Quando("for solicitado o auto login")
	public void for_solicitado_o_auto_login() {
		try {
			response = exchangePost("/v1/user/auto-login", jwtAccessToken, loginDto, DataDto.class);
			
			Assert.assertNotNull(response);
		} catch(HttpStatusCodeException ex) {
			e = ex;
		}
	}

	@Então("deve ser retornado o token de acesso")
	public void deve_ser_retornado_o_token_de_acesso() {
		Assert.assertNotNull(response.getBody().getJwtAcessToken());
	}

	@Então("o status da chamada retornado deve ser {int}")
	public void o_status_da_chamada_retornado_deve_ser(Integer statusCode) {
		if(e != null) {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(e.getRawStatusCode()));
		} else {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(response.getStatusCodeValue()));
		}
	}

	@SuppressWarnings("unchecked")
	@Então("o token de acesso recebido deve conter os campos abaixo preenchidos dentro do payload")
	public void o_token_de_acesso_recebido_deve_conter_os_campos_abaixo_preenchidos_dentro_do_payload(DataTable dataTable) {
		Map<String, Object> mapaResponse = jwtTokenUtils.decodeJwtNoVerification(response.getBody().getJwtAcessToken());
		
		List<String> listaCampos = dataTable.asList();
		
		for (String campo : listaCampos) {
			Assert.assertNotNull(((Map<String, Object>) mapaResponse.get("payload")).get(campo));
		}
	}

	@Então("deve conter uma data de expiracao valida")
	public void deve_conter_uma_data_de_expiracao_valida() {
		Map<String, Object> mapaResponse = jwtTokenUtils.decodeJwtNoVerification(response.getBody().getJwtAcessToken());
		
		Assert.assertTrue(new Date(System.currentTimeMillis()).before(new Date(Long.valueOf(Long.valueOf((Integer) mapaResponse.get("exp"))) * 1000)));
	}

	@Então("deve ser retornado uma exception com a seguinte mensagem {string}")
	public void deve_ser_retornado_uma_exception_com_a_seguinte_mensagem(String mensagem) {
		Map<String, Object> errorMap = mapper.map(e.getResponseBodyAsString(), typeReference);
		
		Assert.assertEquals(mensagem, errorMap.get("message"));
	}

	private <T> ResponseEntity<T> exchangePost(String path, String accessToken, Object request, Class<T> clazz){
		return exchange(path, accessToken, request, HttpMethod.POST, clazz);
	}
	
	private <T> ResponseEntity<T> exchangePatch(String path, String accessToken, Object request, Class<T> clazz){
		return exchange(path, accessToken, request, HttpMethod.PATCH, clazz);
	}
	
	private <T> ResponseEntity<T> exchange(String path, String accessToken, Object request, HttpMethod httpMethod,  Class<T> clazz){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", accessToken);
		headers.add("Content-Type", "application/json");
		
		HttpEntity<?> httpEntity = new HttpEntity<>(request, headers);
		
		ResponseEntity<T> response = restTemplate.exchange("http://localhost:" + serverPort + path, 
				httpMethod, 
				httpEntity, 
				clazz);
		
		return response;
	}
	
}
