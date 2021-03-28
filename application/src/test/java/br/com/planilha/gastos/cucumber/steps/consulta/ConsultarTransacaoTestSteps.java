package br.com.planilha.gastos.cucumber.steps.consulta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import br.com.planilha.gastos.dto.TransactionDto;
import br.com.planilha.gastos.dto.UserDto;
import br.com.planilha.gastos.repository.TransactionRepository;
import br.com.planilha.gastos.repository.UserRepository;
import br.com.planilha.gastos.utils.MapperUtils;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import io.cucumber.datatable.DataTable;

public class ConsultarTransacaoTestSteps {

	@LocalServerPort
	private int serverPort;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MapperUtils mapper;
	
	private DateTimeFormatter formatter;
	private TransactionDto transactionDto;
	private TransactionDto transactionDtoResponse;
	private ResponseEntity<String> response;
	private HttpStatusCodeException e;
	private TypeReference<List<TransactionDto>> transactiontTypeReference;
	private TypeReference<Map<String, Object>> typeReference;
	private String jwtAccessToken;
	
	@PostConstruct
	public void init() {
		transactionRepository.deleteAll();
		userRepository.deleteAll();
		
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnnnn"); 
		
		e = null;
		
		response = null;
		
		transactiontTypeReference = new TypeReference<List<TransactionDto>>() {};
		
		typeReference = new TypeReference<Map<String, Object>>() {};
	}
	
	@Dado("que um usuario tenha sido cadastrado com os dados abaixo anteriormente")
	public void que_um_usuario_tenha_sido_cadastrado_com_os_dados_abaixo_anteriormente(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		UserDto userDto = new UserDto();
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
		
		ResponseEntity<DataDto> responseRgistration = exchangePost("/v1/user/new", null, userDto, DataDto.class);
		
		Assert.assertNotNull(responseRgistration);
		Assert.assertEquals(200, responseRgistration.getStatusCodeValue());
		Assert.assertNotNull(responseRgistration.getBody().getJwtDataToken());
	}

	@Dado("que este usuario ja realizou o login com os dados abaixo")
	public void que_este_usuario_ja_realizou_o_login_com_os_dados_abaixo(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);

		LoginDto loginDto = new LoginDto();
		loginDto.setDeviceId(mapa.get("device_id"));
		loginDto.setEmail(mapa.get("email"));
		loginDto.setPassword(mapa.get("password"));
		
		Assert.assertNotNull(loginDto.getPassword());
		Assert.assertNotNull(loginDto.getEmail());
		Assert.assertNotNull(loginDto.getDeviceId());
	
		ResponseEntity<DataDto> responseLogin = exchangePost("/v1/user/login", null, loginDto, DataDto.class);
		
		Assert.assertNotNull(responseLogin);
		Assert.assertEquals(200, responseLogin.getStatusCodeValue());
		Assert.assertNotNull(responseLogin.getBody().getJwtAcessToken());

		jwtAccessToken = responseLogin.getBody().getJwtAcessToken();
	}

	@Dado("que foi solicitado a criacao da transacao com os dados abaixo")
	public void que_foi_solicitado_a_criacao_da_transacao_com_os_dados_abaixo(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setDescricao(mapa.get("descricao"));
		transactionDto.setLocalizacao(mapa.get("localizacao"));
		transactionDto.setMeioDePagamento(mapa.get("meio_de_pagamento"));
		transactionDto.setTipo(mapa.get("tipo"));
		transactionDto.setId(mapa.get("id"));
		if(mapa.containsKey("valor")) {
			transactionDto.setValor(BigDecimal.valueOf(Double.valueOf(mapa.get("valor"))));
		}
		if(mapa.containsKey("data")) {
			transactionDto.setData(LocalDateTime.parse(mapa.get("data"), formatter));
		}
		
		ResponseEntity<TransactionDto> responseTransaction = exchangePost("/v1/transactions/register", jwtAccessToken, transactionDto, TransactionDto.class);
			
		Assert.assertNotNull(responseTransaction);
		Assert.assertEquals(200, responseTransaction.getStatusCodeValue());
		Assert.assertNotNull(responseTransaction.getBody());
	}
	
	@Dado("que foram cadastradas as transacoes com os dados abaixo")
	public void que_foram_cadastradas_as_transacoes_com_os_dados_abaixo(DataTable dataTable) {
		List<String> listaTransacoesString = dataTable.asList();
		
		for (String stringTransaciton : listaTransacoesString) {
			ResponseEntity<TransactionDto> responseTransaction = exchangePost(
					"/v1/transactions/register", 
					jwtAccessToken, 
					mapper.readValue(stringTransaciton, TransactionDto.class),
					TransactionDto.class);
			
			Assert.assertNotNull(responseTransaction);
			Assert.assertEquals(200, responseTransaction.getStatusCodeValue());
			Assert.assertNotNull(responseTransaction.getBody());
		}
	}

	@Quando("for solicitado a consulta da transacao com o id {string}")
	public void for_solicitado_a_consulta_da_transacao_com_o_id(String transactionId) {
		try {
			response = exchangeGet("/v1/transactions/find/" + transactionId, jwtAccessToken, transactionDto, String.class);
			
			Assert.assertNotNull(response);
		} catch (HttpStatusCodeException ex) {
			e = ex;
		}
	}
	
	@Quando("for solicitado a consulta de transacoes com os dados abaixo")
	public void for_solicitado_a_consulta_de_transacoes_com_os_dados_abaixo(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);

		String query = montaQuery(mapa);
		
		try {
			response = exchangeGet("/v1/transactions/find" + query, jwtAccessToken, transactionDto, String.class);
			
			Assert.assertNotNull(response);
		} catch (HttpStatusCodeException ex) {
			e = ex;
		}
	}

	@Então("deve ser retornado a transacao com os dados abaixo")
	public void deve_ser_retornado_a_transacao_com_os_dados_abaixo(DataTable dataTable) {
		transactionDtoResponse = mapper.readValue(response.getBody(), TransactionDto.class);
		
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		Assert.assertEquals(mapa.get("tipo"), transactionDtoResponse.getTipo());
		Assert.assertEquals(mapa.get("valor"), String.valueOf(transactionDtoResponse.getValor()));
		Assert.assertEquals(mapa.get("descricao"), transactionDtoResponse.getDescricao());
		Assert.assertEquals(mapa.get("localizacao"), transactionDtoResponse.getLocalizacao());
		Assert.assertEquals(mapa.get("meio_de_pagamento"), transactionDtoResponse.getMeioDePagamento());
		if(mapa.containsKey("id")) {
			Assert.assertEquals(mapa.get("id"), transactionDtoResponse.getId());
		} else {
			Assert.assertNotNull(transactionDtoResponse.getId());
		}
		if(mapa.containsKey("data")) {
			Assert.assertEquals(mapa.get("data"), transactionDtoResponse.getData().toString());
		} else {
			Assert.assertNotNull(transactionDtoResponse.getData());
		}
	}

	@Então("o status retornado da chamada deve ser {int}")
	public void o_status_retornado_da_chamada_deve_ser(Integer statusCode) {
		if(e != null) {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(e.getStatusCode().value()));
		} else {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(response.getStatusCodeValue()));
		}
	}
	
	@Então("deve ser retornado uma resposta de erro com a seguinte mensagem {string}")
	public void o_status_retornado_da_chamada_deve_ser(String mensagem) {
		Map<String, Object> errorMap = mapper.map(e.getResponseBodyAsString(), typeReference);
		
		Assert.assertEquals(mensagem, errorMap.get("message"));
	}

	@Então("deve ser retornado as transacoes com os dados abaixo")
	public void deve_ser_retornado_as_transacoes_com_os_dados_abaixo(DataTable dataTable) {
		List<String> transactionsDtoString = dataTable.asList();

		List<TransactionDto> transactionsDto = new ArrayList<>();
		for (String transactionDtoString : transactionsDtoString) {
			transactionsDto.add(mapper.readValue(transactionDtoString, TransactionDto.class));
		}
		
		List<TransactionDto> transactionsDtoResponse = mapper.map(response.getBody(), transactiontTypeReference);

		Assert.assertFalse(transactionsDtoResponse.isEmpty());
		Assert.assertEquals(transactionsDtoString.size(), transactionsDtoResponse.size());
		
		for (TransactionDto transactionDtoResponse : transactionsDtoResponse) {
			for (TransactionDto transactionDto : transactionsDto) {
				if(transactionDtoResponse.getId().equals(transactionDto.getId())) {
					Assert.assertEquals(transactionDto.getTipo(), transactionDtoResponse.getTipo());
					Assert.assertEquals(transactionDto.getValor(), transactionDtoResponse.getValor());
					Assert.assertEquals(transactionDto.getDescricao(), transactionDtoResponse.getDescricao());
					Assert.assertEquals(transactionDto.getLocalizacao(), transactionDtoResponse.getLocalizacao());
					Assert.assertEquals(transactionDto.getMeioDePagamento(), transactionDtoResponse.getMeioDePagamento());
					Assert.assertEquals(transactionDto.getId(), transactionDtoResponse.getId());
					Assert.assertEquals(transactionDto.getData(), transactionDtoResponse.getData());
				}
			}
		}
	}
	
	private String montaQuery(Map<String, String> mapa) {
		String query = "";
		
		if(mapa.get("since") != null && !mapa.get("since").isBlank()) {
			if(query.isBlank()) {
				query = "?";
			} else {
				query += "&";
			}
			query += "since=" + mapa.get("since");
		}
		if(mapa.get("quantity") != null && !mapa.get("quantity").isBlank()) {
			if(query.isBlank()) {
				query = "?";
			} else {
				query += "&";
			}
			query += "quantity=" + mapa.get("quantity");
		}
		if(mapa.get("page") != null && !mapa.get("page").isBlank()) {
			if(query.isBlank()) {
				query = "?";
			} else {
				query += "&";
			}
			query += "page=" + mapa.get("page");
		}
		
		return query;
	}

	private <T> ResponseEntity<T> exchangePost(String path, String accessToken, Object request, Class<T> clazz){
		return exchange(path, accessToken, request, HttpMethod.POST, clazz);
	}
	
	private <T> ResponseEntity<T> exchangeGet(String path, String accessToken, Object request, Class<T> clazz){
		return exchange(path, accessToken, request, HttpMethod.GET, clazz);
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
