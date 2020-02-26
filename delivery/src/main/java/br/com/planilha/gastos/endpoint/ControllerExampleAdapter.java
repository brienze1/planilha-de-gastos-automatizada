package br.com.planilha.gastos.endpoint;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.planilha.gastos.dto.UserExampleDTO;
import br.com.planilha.gastos.dto.UserExampleResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
public interface ControllerExampleAdapter {
	
	@ApiOperation(value = "Retorna um usuario cadastrado")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de usuarios"),
	    @ApiResponse(code = 400, message = "Bad Request"),
	    @ApiResponse(code = 500, message = "Erro interno do servidor"),
	})
	@ResponseBody
	public ResponseEntity<UserExampleResponseDTO> findById(@PathVariable(name="id", required=true) String id);
	
	@ApiOperation(value = "Retorna uma lista de usuarios cadastrados")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna um usuario."),
		    @ApiResponse(code = 400, message = "Bad Request"),
		    @ApiResponse(code = 500, message = "Erro interno do servidor"),
	})
	@ResponseBody
	public ResponseEntity<List<UserExampleResponseDTO>> findAllUsers();

	@ApiOperation(value = "Cadastra um usuario na base de dados")
	@ApiResponses(value = {
	    @ApiResponse(code = 201, message = "Usuario cadastrado com sucesso"),
	    @ApiResponse(code = 403, message = "User Already Exists."),
	    @ApiResponse(code = 400, message = "Bad Request"),
	    @ApiResponse(code = 500, message = "Erro interno do servidor"),
	})
	@ResponseBody
	public ResponseEntity<Map<String, String>> create(@RequestBody UserExampleDTO userExampleDTO);
	
}
