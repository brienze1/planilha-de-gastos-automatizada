package br.com.planilha.gastos.cucumber.steps.cadastro;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.planilha.gastos.Application;
import br.com.planilha.gastos.dto.DataDto;
import br.com.planilha.gastos.dto.DeviceDto;
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

@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrarNovoUsuarioTestSteps {

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
	
	private UserDto userDto;
	private ResponseEntity<DataDto> response;
	private HttpStatusCodeException e;
	private TypeReference<Map<String, Object>> typeReference;
	
	@PostConstruct
	public void init() {
		userRepository.deleteAll();
		deviceRepository.deleteAll();
		transactionRepository.deleteAll();
		
		typeReference = new TypeReference<Map<String, Object>>() {};
		
		e = null;
		response = null;
	}
	
	@Dado("que o usario digitou os seguintes dados")
	public void que_o_usario_digitou_os_seguintes_dados(DataTable dataTable) {
		Map<String, String> mapa = dataTable.asMap(String.class, String.class);
		
		userDto = new UserDto();
		userDto.setEmail(mapa.get("email"));
		userDto.setFirstName(mapa.get("first_name"));
		userDto.setLastName(mapa.get("last_name"));
		userDto.setPassword(mapa.get("password"));
		
		if(mapa.containsKey("password")) {
			Assert.assertNotNull(userDto.getPassword());
		} else {
			Assert.assertNull(userDto.getPassword());
		}
		if(mapa.containsKey("email")) {
			Assert.assertNotNull(userDto.getEmail());
		} else {
			Assert.assertNull(userDto.getEmail());
		}
		if(mapa.containsKey("first_name")) {
			Assert.assertNotNull(userDto.getFirstName());
		} else {
			Assert.assertNull(userDto.getFirstName());
		}
		if(mapa.containsKey("last_name")) {
			Assert.assertNotNull(userDto.getLastName());
		} else {
			Assert.assertNull(userDto.getLastName());
		}
	}

	@Dado("que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio")
	public void que_o_dispositivo_utilizado_pelo_usuario_tenha_gerado_um_id_de_dispositivo_aleatorio() {
		userDto.setDevice(new DeviceDto());
		userDto.getDevice().setDeviceId(UUID.randomUUID().toString());
		
		Assert.assertNotNull(userDto.getDevice());
		Assert.assertNotNull(userDto.getDevice().getDeviceId());
	}

	@Quando("for solicitado o cadastramento")
	public void for_solicitado_o_cadastramento() {
		HttpEntity<?> httpEntity = new HttpEntity<>(userDto);
		
		try {
			response = restTemplate.exchange("http://localhost:" + serverPort + "/v1/user/new", 
					HttpMethod.POST, 
					httpEntity, 
					DataDto.class);

			Assert.assertNotNull(response);
		} catch(HttpStatusCodeException ex) {
			e = ex;
		}
	}

	@Então("deve ser retornado um token de dados")
	public void deve_ser_retornado_um_token_de_dados() {
		Assert.assertNotNull(response.getBody().getJwtDataToken());
	}

	@Então("o status retornado deve ser {int}")
	public void o_status_retornado_deve_ser(Integer statusCode) {
		if(e != null) {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(e.getRawStatusCode()));
		} else {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(response.getStatusCodeValue()));
		}
		
	}

	@SuppressWarnings("unchecked")
	@Então("o token deve conter os campos abaixo preenchidos dentro do payload")
	public void o_token_deve_conter_os_campos_abaixo_preenchidos_dentro_do_payload(DataTable dataTable) {
		Map<String, Object> mapaResponse = jwtTokenUtils.decodeJwtNoVerification(response.getBody().getJwtDataToken());
		
		List<String> listaCampos = dataTable.asList();
		
		for (String campo : listaCampos) {
			Assert.assertNotNull(((Map<String, Object>) mapaResponse.get("payload")).get(campo));
		}
	}

	@SuppressWarnings("unchecked")
	@Então("o objeto {string} deve ter o campo {string} preenchido")
	public void o_objeto_deve_ter_o_campo_preenchido(String objeto, String campo) {
		Map<String, Object> mapaResponse = jwtTokenUtils.decodeJwtNoVerification(response.getBody().getJwtDataToken());
		Map<String, Object> payload = (Map<String, Object>) mapaResponse.get("payload");
		
		Assert.assertNotNull(((Map<String, Object>) payload.get(objeto)).get(campo));
	}

	@Dado("que ja exista um usuario cadastrado com os mesmos dados")
	public void que_ja_exista_um_usuario_cadastrado_com_os_mesmos_dados() {
		HttpEntity<?> httpEntity = new HttpEntity<>(userDto);
		
		response = restTemplate.exchange("http://localhost:" + serverPort + "/v1/user/new", 
				HttpMethod.POST, 
				httpEntity, 
				DataDto.class);
		
		Assert.assertNotNull(response);
	}

	@Então("deve ser retornado uma excessao com a mensagem {string}")
	public void deve_ser_retornado_uma_excessao_com_a_mensagem(String mensagem) {
		Map<String, Object> errorMap = mapper.map(e.getResponseBodyAsString(), typeReference);
		
		Assert.assertEquals(mensagem, errorMap.get("message"));
	}
	
}
