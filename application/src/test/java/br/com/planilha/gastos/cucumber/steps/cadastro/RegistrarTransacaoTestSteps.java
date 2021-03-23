package br.com.planilha.gastos.cucumber.steps.cadastro;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.dto.TransactionDto;
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

public class RegistrarTransacaoTestSteps {

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
	
	private DateTimeFormatter formatter;
	private TransactionDto transactionDto;
	private TransactionDto transactionDtoResponse;
	private ResponseEntity<TransactionDto> response;
	private HttpStatusCodeException e;
	private TypeReference<Map<String, Object>> typeReference;
	private String jwtDataToken;
	private String jwtAccessToken;
	
	@PostConstruct
	public void init() {
		userRepository.deleteAll();
		deviceRepository.deleteAll();
		transactionRepository.deleteAll();
		
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		
		e = null;
		
		response = null;
		
		typeReference = new TypeReference<Map<String, Object>>() {};
	}
	
	@Dado("que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao") 
	public void que_o_dispositivo_do_usuario_informou_os_seguintes_dados_na_criacao_de_uma_transacao(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		transactionDto = new TransactionDto();
		transactionDto.setData(LocalDateTime.parse(mapa.get("data"), formatter));
		transactionDto.setDescricao(mapa.get("descricao"));
		transactionDto.setLocalizacao(mapa.get("localizacao"));
		transactionDto.setMeioDePagamento(mapa.get("meio_de_pagamento"));
		transactionDto.setTipo(mapa.get("tipo"));
		transactionDto.setValor(BigDecimal.valueOf(Long.valueOf(mapa.get("valor"))));
	}

	@Dado("que um usuario ja foi cadastrado com os dados abaixo anteriormente") 
	public void que_um_usuario_ja_foi_cadastrado_com_os_dados_abaixo_anteriormente(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		UserDto userDto = new UserDto();
		userDto.setEmail(mapa.get("email"));
		userDto.setFirstName(mapa.get("first_name"));
		userDto.setLastName(mapa.get("last_name"));
		userDto.setPassword(mapa.get("password"));
		
		Assert.assertNotNull(userDto.getPassword());
		Assert.assertNotNull(userDto.getEmail());
		Assert.assertNotNull(userDto.getFirstName());
		Assert.assertNotNull(userDto.getLastName());
		
		ResponseEntity<DataDto> responseRgistration = exchange("/v1/user/new", null, userDto, DataDto.class);
		
		Assert.assertNotNull(responseRgistration);
		Assert.assertEquals(200, responseRgistration.getStatusCodeValue());
		Assert.assertNotNull(responseRgistration.getBody().getJwtDataToken());
	}

	@Dado("que o usuario ja realizou o login com os dados abaixo") 
	public void que_o_usuario_ja_realizou_o_login_com_os_dados_abaixo(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		LoginDto loginDto = new LoginDto();
		loginDto.setDeviceId(mapa.get("device_id"));
		loginDto.setEmail(mapa.get("email"));
		loginDto.setPassword(mapa.get("password"));
		
		Assert.assertNotNull(loginDto.getPassword());
		Assert.assertNotNull(loginDto.getEmail());
		Assert.assertNotNull(loginDto.getDeviceId());
	
		ResponseEntity<DataDto> responseLogin = exchange("/v1/user/login", null, loginDto, DataDto.class);
		
		Assert.assertNotNull(responseLogin);
		Assert.assertEquals(200, responseLogin.getStatusCodeValue());
		Assert.assertNotNull(responseLogin.getBody().getJwtAcessToken());

		jwtAccessToken = responseLogin.getBody().getJwtAcessToken();
	}

	@Quando("for solicitado a criacao de uma transacao") 
	public void for_solicitado_a_criacao_de_uma_transacao() {
		response = exchange("/v1/transactions/register", jwtAccessToken, transactionDto, TransactionDto.class);
		
		Assert.assertNotNull(response);
	}

	@Então("deve ser retornado a transacao criada") 
	public void deve_ser_retornado_a_transacao_criada() {
		Assert.assertNotNull(response.getBody());

		transactionDtoResponse = response.getBody();
	}

	@Então("retornado o status {int}") 
	public void retornado_o_status(Integer statusCode) {
		Assert.assertEquals(String.valueOf(statusCode), String.valueOf(response.getStatusCodeValue()));
	}

	@Então("a transacao recebida deve conter os campos abaixo preenchidos") 
	public void a_transacao_recebida_deve_conter_os_campos_abaixo_preenchidos(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
		Assert.assertEquals(mapa.get("xxxxxxx"), transactionDtoResponse.get);
	}
	
	private <T> ResponseEntity<T> exchange(String path, String accessToken, Object request,  Class<T> clazz){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", accessToken);
		headers.add("Content-Type", "application/json");
		
		HttpEntity<?> httpEntity = new HttpEntity<>(request, headers);
		
		ResponseEntity<T> response = restTemplate.exchange("http://localhost:" + serverPort + path, 
				HttpMethod.POST, 
				httpEntity, 
				clazz);
		
		return response;
	}
	
}
