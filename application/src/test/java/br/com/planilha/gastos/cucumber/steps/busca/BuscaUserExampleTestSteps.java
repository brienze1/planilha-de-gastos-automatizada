package br.com.planilha.gastos.cucumber.steps.busca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import br.com.planilha.gastos.Application;
import br.com.planilha.gastos.dto.UserExampleDTO;
import br.com.planilha.gastos.dto.UserExampleResponseDTO;
import br.com.planilha.gastos.endpoint.ControllerExampleAdapter;
import br.com.planilha.gastos.exception.UserAlreadyExistsExceptionExample;
import br.com.planilha.gastos.repository.UserExampleEntityRepository;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import io.cucumber.datatable.DataTable;

@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
public class BuscaUserExampleTestSteps {

	@Autowired
	private ControllerExampleAdapter controler;

	@Autowired
	private UserExampleEntityRepository repository;

	@Rule
	private ExpectedException expectedException = ExpectedException.none();

	private List<Map<String, String>> userList;
	private ResponseEntity<List<UserExampleResponseDTO>> responseList;
	private ResponseEntity<UserExampleResponseDTO> response;
	private ResponseStatusException ex;

	@Dado("^que nenhum usuario esteja cadastrado$")
	public void que_nenhum_usuario_esteja_cadastrado() {
		repository.deleteAll();
	}

	@Quando("^for selecionado a busca por todos os usuarios cadastrados$")
	public void for_selecionado_a_busca_por_todos_os_usuarios_cadastrados() {
		responseList = controler.findAllUsers();
	}

	@Entao("^deve ser devolvido uma lista vazia e o status \"(.*?)\"$")
	public void deve_ser_devolvido_uma_lista_vazia_e_o_status(String status) {
		Assert.assertEquals(status, String.valueOf(responseList.getStatusCodeValue()));
		Assert.assertTrue(responseList.getBody().isEmpty());
	}

	@Dado("^que os usuarios abaixo existem$")
	public void que_os_usuarios_abaixo_existem(DataTable table) {
		List<Map<String, String>> mapa = table.asMaps(String.class, String.class);

		userList = new ArrayList<>();

		for (Map<String, String> map : mapa) {
			UserExampleDTO user = new UserExampleDTO();
			user.setEmail(map.get("email"));
			user.setFirstName(map.get("firstName"));
			user.setLastName(map.get("lastName"));
			user.setPassword(map.get("password"));
			userList.add(new HashMap<>());
			userList.get(userList.size() - 1).put("email", user.getEmail());
			userList.get(userList.size() - 1).put("firstName", user.getFirstName());
			userList.get(userList.size() - 1).put("lastName", user.getLastName());
			userList.get(userList.size() - 1).put("password", user.getPassword());
			userList.get(userList.size() - 1).put("usr", map.get("usr"));

			try {
				userList.get(userList.size() - 1).put("id", controler.create(user).getBody().get("id"));
			} catch (UserAlreadyExistsExceptionExample e) {
				Assert.assertEquals("User " + user.getEmail() + " already exists", e.getMessage());

				userList.get(userList.size() - 1).put("id", repository.findAllByEmail(user.getEmail()).get(0).getId());
			}
		}
	}

	@Quando("^for selecionado a busca por todos os usuarios$")
	public void for_selecionado_a_busca_por_todos_os_usuarios() {
		responseList = controler.findAllUsers();
	}

	@Entao("^deve ser devolvido uma lista com todos os usuarios criados e o status \"(.*?)\"$")
	public void deve_ser_devolvido_uma_lista_com_todos_os_usuarios_criados_e_o_status(String status) {
		Assert.assertEquals(status, String.valueOf(responseList.getStatusCodeValue()));

		for (int i = 0; i < responseList.getBody().size(); i++) {
			Assert.assertEquals(userList.get(i).get("email"), responseList.getBody().get(i).getEmail());
			Assert.assertEquals(userList.get(i).get("firstName"), responseList.getBody().get(i).getFirstName());
			Assert.assertEquals(userList.get(i).get("id"), responseList.getBody().get(i).getId());
			Assert.assertEquals(userList.get(i).get("lastName"), responseList.getBody().get(i).getLastName());
			Assert.assertNotEquals(userList.get(i).get("password"), responseList.getBody().get(i).getPassword());
		}

		repository.deleteAll();
	}

	@Dado("^que os usuarios abaixo existem na base$")
	public void que_os_usuarios_abaixo_existem_na_base(DataTable table) {

		List<Map<String, String>> mapa = table.asMaps(String.class, String.class);

		userList = new ArrayList<>();

		for (Map<String, String> map : mapa) {
			UserExampleDTO user = new UserExampleDTO();
			user.setEmail(map.get("email"));
			user.setFirstName(map.get("firstName"));
			user.setLastName(map.get("lastName"));
			user.setPassword(map.get("password"));
			userList.add(new HashMap<>());
			userList.get(userList.size() - 1).put("email", user.getEmail());
			userList.get(userList.size() - 1).put("firstName", user.getFirstName());
			userList.get(userList.size() - 1).put("lastName", user.getLastName());
			userList.get(userList.size() - 1).put("password", user.getPassword());
			userList.get(userList.size() - 1).put("usr", map.get("usr"));

			try {
				userList.get(userList.size() - 1).put("id", controler.create(user).getBody().get("id"));
			} catch (UserAlreadyExistsExceptionExample e) {
				Assert.assertEquals("User " + user.getEmail() + " already exists", e.getMessage());

				userList.get(userList.size() - 1).put("id", repository.findAllByEmail(user.getEmail()).get(0).getId());
			}
		}
	}

	@Quando("^for selecionado a busca pelo id do usuario \"(.*?)\"$")
	public void for_selecionado_a_busca_pelo_id_do_usuario(String usr) {
		String id = null;
		for (Map<String, String> map : userList) {
			if (map.get("usr").equals(usr)) {
				id = map.get("id");
			}
		}
		response = controler.findById(id);
	}

	@Entao("^deve ser devolvido o usuario com os seguintes dados abaixo e o status \"(.*?)\"$")
	public void deve_ser_devolvido_o_usuario_com_os_seguintes_dados_abaixo_e_o_status(String statusCode,
			DataTable table) {
		Assert.assertEquals(statusCode, String.valueOf(response.getStatusCode().value()));

		List<Map<String, String>> assertList = table.asMaps(String.class, String.class);
		Assert.assertEquals(assertList.get(0).get("email"), response.getBody().getEmail());
		Assert.assertEquals(assertList.get(0).get("firstName"), response.getBody().getFirstName());
		Assert.assertEquals(assertList.get(0).get("lastName"), response.getBody().getLastName());
		Assert.assertNotEquals(assertList.get(0).get("password"), response.getBody().getPassword());

		repository.deleteAll();
	}

	@Dado("^que os usuarios abaixo existem na base de dados$")
	public void que_os_usuarios_abaixo_existem_na_base_de_dados(DataTable table) {

		List<Map<String, String>> mapa = table.asMaps(String.class, String.class);

		userList = new ArrayList<>();

		for (Map<String, String> map : mapa) {
			UserExampleDTO user = new UserExampleDTO();
			user.setEmail(map.get("email"));
			user.setFirstName(map.get("firstName"));
			user.setLastName(map.get("lastName"));
			user.setPassword(map.get("password"));
			userList.add(new HashMap<>());
			userList.get(userList.size() - 1).put("email", user.getEmail());
			userList.get(userList.size() - 1).put("firstName", user.getFirstName());
			userList.get(userList.size() - 1).put("lastName", user.getLastName());
			userList.get(userList.size() - 1).put("password", user.getPassword());
			userList.get(userList.size() - 1).put("usr", map.get("usr"));

			try {
				userList.get(userList.size() - 1).put("id", controler.create(user).getBody().get("id"));
			} catch (UserAlreadyExistsExceptionExample e) {
				Assert.assertEquals("User " + user.getEmail() + " already exists", e.getMessage());

				userList.get(userList.size() - 1).put("id", repository.findAllByEmail(user.getEmail()).get(0).getId());
			}
		}
	}

	@Quando("^for selecionado a busca pelo id \"(.*?)\"$")
	public void for_selecionado_a_busca_pelo_id(String id) {
		try {
			response = controler.findById(id);
		} catch (ResponseStatusException e) {
			ex = e;
		}
	}

	@Entao("^deve ser devolvido uma excecao com a mensagem \"(.*?)\" e o status \"(.*?)\"$")
	public void deve_ser_devolvido_o_usuario_com_os_seguintes_dados_abaixo_e_o_status(String msg, String statusCode) {
		Assert.assertEquals(statusCode, String.valueOf(ex.getStatus().value()));
		Assert.assertEquals(msg, ex.getReason());

		repository.deleteAll();
	}
}
