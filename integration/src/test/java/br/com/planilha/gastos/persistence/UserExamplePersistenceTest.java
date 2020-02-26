package br.com.planilha.gastos.persistence;

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
import br.com.planilha.gastos.entity.UserExampleEntity;
import br.com.planilha.gastos.parse.UserExampleEntityParaUserExampleParse;
import br.com.planilha.gastos.parse.UserExampleParaUserExampleEntityParse;
import br.com.planilha.gastos.repository.UserExampleEntityRepository;

@RunWith(SpringRunner.class)
public class UserExamplePersistenceTest {

	@InjectMocks
	private UserExamplePersistence userExamplePersistence;

	@Mock
	private UserExampleEntityRepository userExampleEntityRepository;
	
	@Mock
	private UserExampleEntityParaUserExampleParse userExampleEntityParaUserExampleParse;

	@Mock
	private UserExampleParaUserExampleEntityParse userExampleParaUserExampleEntityParse;
	
	private UserExampleEntity userExampleEntity;
	private UserExample userExample;
	private List<UserExampleEntity> userExampleEntityList;
	private List<UserExample> userExampleList;
	
	@Before
	public void init() {
		userExampleEntity = new UserExampleEntity();
		userExampleEntity.setEmail("jhonmartson@email.com");
		userExampleEntity.setFirstName("Jhon");
		userExampleEntity.setId(UUID.randomUUID().toString());
		userExampleEntity.setLastName("Marston");
		userExampleEntity.setPassword(UUID.randomUUID().toString());
		
		userExampleEntityList = new ArrayList<>();
		userExampleEntityList.add(userExampleEntity);
		
		userExample = new UserExample();
		userExample.setEmail(userExampleEntity.getEmail());
		userExample.setFirstName(userExampleEntity.getFirstName());
		userExample.setId(userExampleEntity.getId());
		userExample.setLastName(userExampleEntity.getLastName());
		userExample.setPassword(userExampleEntity.getPassword());
	
		userExampleList = new ArrayList<>();
		userExampleList.add(userExample);
	}
	
	@Test
	public void testFindByID() {
		Mockito.when(userExampleEntityRepository.findById(userExampleEntity.getId())).thenReturn(Optional.of(userExampleEntity));
		Mockito.when(userExampleEntityParaUserExampleParse.parse(userExampleEntity)).thenReturn(userExample);
		
		Optional<UserExample> response = userExamplePersistence.findById(userExampleEntity.getId());
		
		Assert.assertTrue(response.isPresent());
		Assert.assertEquals(userExampleEntity.getEmail(), response.get().getEmail());
		Assert.assertEquals(userExampleEntity.getFirstName(), response.get().getFirstName());
		Assert.assertEquals(userExampleEntity.getId(), response.get().getId());
		Assert.assertEquals(userExampleEntity.getLastName(), response.get().getLastName());
		Assert.assertEquals(userExampleEntity.getPassword(), response.get().getPassword());
	}
	
	@Test
	public void testFindByIdNotFound() {
		Mockito.when(userExampleEntityRepository.findById(userExampleEntity.getId())).thenReturn(Optional.ofNullable(null));
		
		Optional<UserExample> response = userExamplePersistence.findById(userExampleEntity.getId());
		
		Assert.assertTrue(!response.isPresent());
	}
	
	@Test
	public void testFindAllUsers() {
		Mockito.when(userExampleEntityRepository.findAll()).thenReturn(userExampleEntityList);
		Mockito.when(userExampleEntityParaUserExampleParse.parseList(userExampleEntityList)).thenReturn(userExampleList);
		
		List<UserExample> response = userExamplePersistence.findAllUsers();
		
		Assert.assertTrue(!response.isEmpty());
		Assert.assertEquals(userExampleEntity.getEmail(), response.get(0).getEmail());
		Assert.assertEquals(userExampleEntity.getFirstName(), response.get(0).getFirstName());
		Assert.assertEquals(userExampleEntity.getId(), response.get(0).getId());
		Assert.assertEquals(userExampleEntity.getLastName(), response.get(0).getLastName());
		Assert.assertEquals(userExampleEntity.getPassword(), response.get(0).getPassword());
	}
	
	@Test
	public void testCreate() {
		Mockito.when(userExampleParaUserExampleEntityParse.parse(userExample)).thenReturn(userExampleEntity);
		
		userExamplePersistence.create(userExample);
		
		Mockito.verify(userExampleEntityRepository, Mockito.times(1)).save(userExampleEntity);
	}
	
	@Test
	public void testFindByEmail() {
		Mockito.when(userExampleEntityRepository.findAllByEmail(userExampleEntity.getEmail())).thenReturn(userExampleEntityList);
		Mockito.when(userExampleEntityParaUserExampleParse.parse(userExampleEntityList.get(0))).thenReturn(userExample);
		
		Optional<UserExample> response = userExamplePersistence.findByEmail(userExampleEntity.getEmail());
		
		Assert.assertTrue(response.isPresent());
		Assert.assertEquals(userExampleEntity.getEmail(), response.get().getEmail());
		Assert.assertEquals(userExampleEntity.getFirstName(), response.get().getFirstName());
		Assert.assertEquals(userExampleEntity.getId(), response.get().getId());
		Assert.assertEquals(userExampleEntity.getLastName(), response.get().getLastName());
		Assert.assertEquals(userExampleEntity.getPassword(), response.get().getPassword());
	}
	
	@Test
	public void testFindByEmailNotFound() {
		Mockito.when(userExampleEntityRepository.findAllByEmail(userExampleEntity.getEmail())).thenReturn(new ArrayList<>());
		
		Optional<UserExample> response = userExamplePersistence.findByEmail(userExampleEntity.getEmail());
		
		Assert.assertTrue(!response.isPresent());
	}
	
}

