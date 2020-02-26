package br.com.planilha.gastos.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import br.com.planilha.gastos.dto.UserExampleDTO;
import br.com.planilha.gastos.dto.UserExampleResponseDTO;
import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.parse.UserExampleDTOParaUserExampleParse;
import br.com.planilha.gastos.parse.UserExampleParaUserExampleResponseDTOParse;
import br.com.planilha.gastos.port.UserServiceAdapter;

@RunWith(SpringRunner.class)
public class ControllerExampleTest {

	@InjectMocks
	private ControllerExample controllerExample;
	
	@Mock
	private UserExampleDTOParaUserExampleParse userExampleDTOParaUserExampleParse;
	
	@Mock
	private UserExampleParaUserExampleResponseDTOParse userExampleParaUserExampleResponseDTOParse;
	
	@Mock
	private UserServiceAdapter userServiceAdapter;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none(); 
	
	private String id;
	private UserExample userExample;
	private Optional<UserExample> userExampleOptional;
	private UserExampleResponseDTO userExampleResponseDTO;
	private List<UserExample> userExampleList;
	private List<UserExampleResponseDTO> userExampleResponseDTOList;
	private UserExampleDTO userExampleDTO;
	
	@Before
	public void init() {
		id = UUID.randomUUID().toString();
	
		userExample = new UserExample();
		userExample.setEmail("teste@email.com");
		userExample.setFirstName("John");
		userExample.setLastName("Marston");
		userExample.setPassword("12345");

		userExampleOptional = Optional.of(userExample);
		
		userExampleResponseDTO = new UserExampleResponseDTO();
		userExampleResponseDTO.setEmail(userExample.getEmail());
		userExampleResponseDTO.setFirstName(userExample.getFirstName());
		userExampleResponseDTO.setLastName(userExample.getLastName());
		userExampleResponseDTO.setPassword(userExample.getPassword());
		userExampleResponseDTO.setId(id);
		
		userExampleList = new ArrayList<>();
		userExampleList.add(userExample);
		
		userExampleResponseDTOList = new ArrayList<>();
		userExampleResponseDTOList.add(userExampleResponseDTO);
		
		userExampleDTO = new UserExampleDTO();
		userExampleDTO.setEmail(userExample.getEmail());
		userExampleDTO.setFirstName(userExample.getFirstName());
		userExampleDTO.setLastName(userExample.getLastName());
		userExampleDTO.setPassword(userExample.getPassword());
	}
	
	@Test
	public void findByIdTest() {
		Mockito.when(userServiceAdapter.findById(id)).thenReturn(userExampleOptional);
		Mockito.when(userExampleParaUserExampleResponseDTOParse.parse(userExampleOptional.get())).thenReturn(userExampleResponseDTO);
		
		ResponseEntity<UserExampleResponseDTO> response = controllerExample.findById(id);
		
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(id, response.getBody().getId());
		Assert.assertEquals(userExample.getEmail(), response.getBody().getEmail());
		Assert.assertEquals(userExample.getFirstName(), response.getBody().getFirstName());
		Assert.assertEquals(userExample.getLastName(), response.getBody().getLastName());
		Assert.assertEquals(userExample.getPassword(), response.getBody().getPassword());
	}
	
	@Test
	public void findByIdNotFoundTest() {
		expectedException.expect(ResponseStatusException.class);
		expectedException.expectMessage("User Not Found");
		
		Mockito.when(userServiceAdapter.findById(id)).thenReturn(Optional.ofNullable(null));
		
		try {
			@SuppressWarnings("unused")
			ResponseEntity<UserExampleResponseDTO> response = controllerExample.findById(id);
		} catch (ResponseStatusException e) {
			Assert.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
			throw e;
		}
	}
	
	@Test
	public void findAllUsersTest() {
		Mockito.when(userServiceAdapter.findAllUsers()).thenReturn(userExampleList);
		Mockito.when(userExampleParaUserExampleResponseDTOParse.parseList(userExampleList)).thenReturn(userExampleResponseDTOList);
		
		ResponseEntity<List<UserExampleResponseDTO>> response = controllerExample.findAllUsers();
		
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(id, response.getBody().get(0).getId());
		Assert.assertEquals(userExample.getEmail(), response.getBody().get(0).getEmail());
		Assert.assertEquals(userExample.getFirstName(), response.getBody().get(0).getFirstName());
		Assert.assertEquals(userExample.getLastName(), response.getBody().get(0).getLastName());
		Assert.assertEquals(userExample.getPassword(), response.getBody().get(0).getPassword());
	}
	
	@Test
	public void createTest() {
		Mockito.when(userExampleDTOParaUserExampleParse.parse(userExampleDTO)).thenReturn(userExample);
		Mockito.when(userServiceAdapter.create(userExample)).thenReturn(id);
		
		ResponseEntity<Map<String, String>> response = controllerExample.create(userExampleDTO);
		
		Assert.assertEquals(id, response.getBody().get("id"));
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
}

