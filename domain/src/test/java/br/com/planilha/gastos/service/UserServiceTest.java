package br.com.planilha.gastos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.planilha.gastos.entity.UserExample;
import br.com.planilha.gastos.port.IdGeneratorAdapter;
import br.com.planilha.gastos.port.PasswordEncoderAdapter;
import br.com.planilha.gastos.port.UserRepositoryAdapter;
import br.com.planilha.gastos.rules.UserValidatorExample;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserValidatorExample userValidatorExample;

	@Mock
	private UserRepositoryAdapter repository;

	@Mock
	private PasswordEncoderAdapter passwordEncoder;

	@Mock
	private IdGeneratorAdapter idGenerator;

	private String id;
	private String password;
	private UserExample userExample;
	private List<UserExample> userExampleList;

	@Before
	public void init() {
		id = UUID.randomUUID().toString();
		password = UUID.randomUUID().toString();

		userExample = new UserExample();
		userExample.setEmail("jhonmarston@email.com");
		userExample.setFirstName("Jhon");
		userExample.setLastName("Marston");
		userExample.setId(UUID.randomUUID().toString());
		userExample.setPassword(UUID.randomUUID().toString());

		userExampleList = new ArrayList<>();
		userExampleList.add(userExample);
	}

	@Test
	public void test() {
		Mockito.when(idGenerator.generate()).thenReturn(id);
		Mockito.when(passwordEncoder.encode(userExample.getEmail() + userExample.getPassword())).thenReturn(password);

		String response = userService.create(userExample);

		Assert.assertNotNull(response);
	}

	@Test
	public void testFindById() {
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(userExample));

		Optional<UserExample> response = userService.findById(id);

		Assert.assertTrue(response.isPresent());
		Assert.assertEquals(userExample.getEmail(), response.get().getEmail());
		Assert.assertEquals(userExample.getFirstName(), response.get().getFirstName());
		Assert.assertEquals(userExample.getLastName(), response.get().getLastName());
		Assert.assertEquals(userExample.getId(), response.get().getId());
		Assert.assertEquals(userExample.getPassword(), response.get().getPassword());
	}

	@Test
	public void testFindAllUsers() {
		Mockito.when(repository.findAllUsers()).thenReturn(userExampleList);

		List<UserExample> response = userService.findAllUsers();

		Assert.assertEquals(userExample.getEmail(), response.get(0).getEmail());
		Assert.assertEquals(userExample.getFirstName(), response.get(0).getFirstName());
		Assert.assertEquals(userExample.getLastName(), response.get(0).getLastName());
		Assert.assertEquals(userExample.getId(), response.get(0).getId());
		Assert.assertEquals(userExample.getPassword(), response.get(0).getPassword());
	}

	@Test
	public void testFindByEmail() {
		Mockito.when(repository.findByEmail(userExample.getEmail())).thenReturn(Optional.of(userExample));

		Optional<UserExample> response = userService.findByEmail(userExample.getEmail());

		Assert.assertEquals(userExample.getEmail(), response.get().getEmail());
		Assert.assertEquals(userExample.getFirstName(), response.get().getFirstName());
		Assert.assertEquals(userExample.getLastName(), response.get().getLastName());
		Assert.assertEquals(userExample.getId(), response.get().getId());
		Assert.assertEquals(userExample.getPassword(), response.get().getPassword());
	}

}
