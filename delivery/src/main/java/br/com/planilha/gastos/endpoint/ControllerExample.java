package br.com.planilha.gastos.endpoint;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.planilha.gastos.dto.UserExampleDTO;
import br.com.planilha.gastos.dto.UserExampleResponseDTO;
import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.parse.UserExampleDTOParaUserExampleParse;
import br.com.planilha.gastos.parse.UserExampleParaUserExampleResponseDTOParse;
import br.com.planilha.gastos.port.UserServiceAdapter;

@RestController
public class ControllerExample implements ControllerExampleAdapter {
	
	@Autowired
	private UserExampleDTOParaUserExampleParse userExampleDTOParaUserExampleParse;
	
	@Autowired
	private UserExampleParaUserExampleResponseDTOParse userExampleParaUserExampleResponseDTOParse;
	
	@Autowired
	private UserServiceAdapter userServiceAdapter;
	
	@GetMapping("/teste/find/{id}")
	public ResponseEntity<UserExampleResponseDTO> findById(@PathVariable(name="id", required=true) String id) {
		System.out.println("Chamada (/teste/find/{id}) no horario: " + LocalDateTime.now());
		
		Optional<UserExample> userExample = userServiceAdapter.findById(id);
		
		if(userExample.isPresent()) {
			return new ResponseEntity<UserExampleResponseDTO>(userExampleParaUserExampleResponseDTOParse.parse(userExample.get()), HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found.");
		}
	    
	}
	
	@GetMapping("/teste/find")
	public ResponseEntity<List<UserExampleResponseDTO>> findAllUsers() {
		System.out.println("Chamada (/teste/find) no horario: " + LocalDateTime.now());
		
		List<UserExample> userExampleList = userServiceAdapter.findAllUsers();
		
		return new ResponseEntity<List<UserExampleResponseDTO>>(userExampleParaUserExampleResponseDTOParse.parseList(userExampleList), HttpStatus.OK);
	}
	
	@PostMapping("/teste/create")
	public ResponseEntity<Map<String, String>> create(@RequestBody UserExampleDTO userExampleDTO) {
		System.out.println("Chamada (/teste/post) no horario: " + LocalDateTime.now() + "; Email:" + userExampleDTO.getEmail());
		
		Map<String, String> response = new HashMap<>();
		response.put("id", userServiceAdapter.create(userExampleDTOParaUserExampleParse.parse(userExampleDTO)));
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
	}
	
}

