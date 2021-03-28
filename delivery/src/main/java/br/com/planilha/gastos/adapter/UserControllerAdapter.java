package br.com.planilha.gastos.adapter;

import br.com.planilha.gastos.dto.DataDto;
import br.com.planilha.gastos.dto.LoginDto;
import br.com.planilha.gastos.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
public interface UserControllerAdapter {

	@ApiOperation(value = "Cadastra um usuario novo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Jwt com dados dentro do token"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Erro interno do servidor"), })
	DataDto register(UserDto userDto);

	@ApiOperation(value = "Faz login do usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "no content"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Erro interno do servidor"), })
	DataDto login(LoginDto loginDto);

	@ApiOperation(value = "Faz login do usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "no content"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Erro interno do servidor"), })
	DataDto autoLogin(LoginDto loginDto);
	


}
