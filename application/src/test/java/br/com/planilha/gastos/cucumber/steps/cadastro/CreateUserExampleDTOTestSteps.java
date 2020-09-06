//package br.com.planilha.gastos.cucumber.steps.cadastro;
//
//import java.util.Optional;
//
//import org.junit.Assert;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//
//import br.com.planilha.gastos.adapter.ControllerExampleAdapter;
//import br.com.planilha.gastos.dto.UserDto;
//import br.com.planilha.gastos.entity.UserEntity;
//import br.com.planilha.gastos.repository.UserRepository;
//import cucumber.api.java.pt.Dado;
//import cucumber.api.java.pt.Entao;
//import cucumber.api.java.pt.Quando;
//
//public class CreateUserExampleDTOTestSteps {
//
//	@Autowired
//	private UserRepository repository;
//
//	@Autowired
//	private ControllerExampleAdapter controler;
//
//	private UserDto userExampleDTO;
//	private String id;
//	private Exception ex;
//
//	@Dado("^que o usuario \"(.*?)\" \"(.*?)\" de email \"(.*?)\" com a senha \"(.*?)\"$")
//	public void que_o_usuario_de_email_com_a_senha(String firstName, String lastName, String email, String password) {
//		userExampleDTO = new UserDto();
//		userExampleDTO.setFirstName(firstName);
//		userExampleDTO.setLastName(lastName);
//		userExampleDTO.setEmail(email);
//		userExampleDTO.setPassword(password);
//	}
//
//	@Quando("^ele tentar criar um usuario pelo endpoint create e for retornado um id de resposta$")
//	public void ele_tentar_criar_um_usuario_pelo_endpoint_create_e_for_retornado_um_id_de_resposta()
//			throws JsonProcessingException {
//		id = controler.create(userExampleDTO).getBody().get("id");
//
//		Assert.assertNotNull(id);
//	}
//
//	@Entao("^deve ter um usuario com o id criado na base com \"(.*?)\" \"(.*?)\" e \"(.*?)\"$")
//	public void deve_ter_um_usuario_com_o_id_criado_na_base(String email, String firstName, String lastName) {
//		Optional<UserEntity> user = repository.findById(id);
//
//		Assert.assertNotNull(user.get());
//		Assert.assertEquals(email, user.get().getEmail());
//		Assert.assertEquals(firstName, user.get().getFirstName());
//		Assert.assertEquals(lastName, user.get().getLastName());
//
//	}
//
//	@Dado("^que o usuario \"(.*?)\" \"(.*?)\" de email \"(.*?)\" com a senha \"(.*?)\" ja tenha sido cadastrado$")
//	public void que_o_usuario_de_email_com_a_senha_ja_tenha_sido_cadastrado(String firstName, String lastName,
//			String email, String password) {
//		userExampleDTO = new UserDto();
//		userExampleDTO.setFirstName(firstName);
//		userExampleDTO.setLastName(lastName);
//		userExampleDTO.setEmail(email);
//		userExampleDTO.setPassword(password);
//	}
//
//	@Quando("^ele tentar criar um usuario novamente$")
//	public void ele_tentar_criar_um_usuario_novamente() throws JsonProcessingException {
//		try {
//			controler.create(userExampleDTO);
//		} catch (Exception e) {
//			ex = e;
//		}
//	}
//
//	@Entao("^deve ser retornado uma mensagem de erro \"(.*?)\"$")
//	public void deve_ser_retornado_uma_mensagem_de_erro_e_o_status(String msg) {
//		Assert.assertEquals(msg, ex.getMessage());
//	}
//
//}
