package br.com.planilha.gastos.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.MapperUtilsException;

@RunWith(SpringRunner.class)
public class MapperUtilsTest {

	@InjectMocks
	private MapperUtils mapperUtils;

	@Spy
	private ModelMapper modelMapper;

	@Spy
	private ObjectMapper objectMapper;
	
	private User user;
	private TypeReference<Map<String, Object>> typeReference;
	
	@Before
	public void init() {
		Device device = new Device();
		device.setDeviceId(UUID.randomUUID().toString());
		device.setId(UUID.randomUUID().toString());
		device.setVerificationCode(UUID.randomUUID().toString());
		
		List<Device> devices = new ArrayList<>();
		devices.add(device);
		
		user = new User();
		user.setAutoLogin(true);
		user.setDevices(devices);
		user.setEmail(UUID.randomUUID().toString());
		user.setFirstName(UUID.randomUUID().toString());
		user.setId(UUID.randomUUID().toString());
		user.setInUseDevice(device.getDeviceId());
		user.setLastName(UUID.randomUUID().toString());
		user.setPassword(UUID.randomUUID().toString());
		user.setSecret(UUID.randomUUID().toString());
		user.setValidEmail(false);
		
		typeReference = new TypeReference<Map<String, Object>>() {};
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void mapTest() {
		String userJson = mapperUtils.writeValueAsString(user);
		
		Map<String, Object> userMapped = mapperUtils.map(userJson, typeReference);
		
		Assert.assertEquals(user.getEmail(), userMapped.get("email"));
		Assert.assertEquals(user.getFirstName(), userMapped.get("firstName"));
		Assert.assertEquals(user.getId(), userMapped.get("id"));
		Assert.assertEquals(user.getLastName(), userMapped.get("lastName"));
		Assert.assertEquals(user.getPassword(), userMapped.get("password"));
		Assert.assertEquals(user.getSecret(), userMapped.get("secret"));
		Assert.assertEquals(user.isAutoLogin(), userMapped.get("autoLogin"));
		Assert.assertEquals(user.isValidEmail(), userMapped.get("validEmail"));
		Assert.assertEquals(user.getDevices().get(0).getDeviceId(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("deviceId"));
		Assert.assertEquals(user.getDevices().get(0).getId(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("id"));
		Assert.assertEquals(user.getDevices().get(0).getVerificationCode(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("verificationCode"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void mapObjectTest() {
		Map<String, Object> userMapped = mapperUtils.map(user, typeReference);
		
		Assert.assertEquals(user.getEmail(), userMapped.get("email"));
		Assert.assertEquals(user.getFirstName(), userMapped.get("firstName"));
		Assert.assertEquals(user.getId(), userMapped.get("id"));
		Assert.assertEquals(user.getLastName(), userMapped.get("lastName"));
		Assert.assertEquals(user.getPassword(), userMapped.get("password"));
		Assert.assertEquals(user.getSecret(), userMapped.get("secret"));
		Assert.assertEquals(user.isAutoLogin(), userMapped.get("autoLogin"));
		Assert.assertEquals(user.isValidEmail(), userMapped.get("validEmail"));
		Assert.assertEquals(user.getDevices().get(0).getDeviceId(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("deviceId"));
		Assert.assertEquals(user.getDevices().get(0).getId(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("id"));
		Assert.assertEquals(user.getDevices().get(0).getVerificationCode(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("verificationCode"));
	}
	
	@Test(expected = MapperUtilsException.class)
	public void mapObjectNullTest() {
		try {
			mapperUtils.map(null, typeReference);
		} catch (MapperUtilsException e) {
			Assert.assertEquals(e.getMessage(), "Input object is null");
			
			throw e;
		}
	}
	
	@Test(expected = MapperUtilsException.class)
	public void mapClassNullTest() {
		try {
			mapperUtils.map(user, null);
		} catch (MapperUtilsException e) {
			Assert.assertEquals(e.getMessage(), "Input typeReference is null");
			
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test(expected = MapperUtilsException.class)
	public void mapErrorTest() throws JsonMappingException, JsonProcessingException {
		String userJson = mapperUtils.writeValueAsString(user);
		
		Mockito.when(objectMapper.readValue(userJson, typeReference)).thenThrow(new JsonGenerationException("error"));
		
		try {
			mapperUtils.map(userJson, typeReference);
		} catch (MapperUtilsException e) {
			Assert.assertEquals(e.getMessage(), "Error while mapping object with TypeReference");
			
			throw e;
		}
	}
	
	@Test
	public void writeValueAsStringTest() {
		String userJson = mapperUtils.writeValueAsString(user);
		
		Assert.assertNotNull(userJson);
		Assert.assertFalse(userJson.isBlank());
		Assert.assertTrue(userJson.contains(user.getEmail()));
		Assert.assertTrue(userJson.contains(user.getFirstName()));
		Assert.assertTrue(userJson.contains(user.getId()));
		Assert.assertTrue(userJson.contains(user.getLastName()));
		Assert.assertTrue(userJson.contains(user.getPassword()));
		Assert.assertTrue(userJson.contains(user.getSecret()));
		Assert.assertTrue(userJson.contains(String.valueOf(user.isAutoLogin())));
		Assert.assertTrue(userJson.contains(String.valueOf(user.isValidEmail())));
		Assert.assertTrue(userJson.contains(user.getDevices().get(0).getDeviceId()));
		Assert.assertTrue(userJson.contains(user.getDevices().get(0).getId()));
		Assert.assertTrue(userJson.contains(user.getDevices().get(0).getVerificationCode()));
	}
	
	@Test(expected = MapperUtilsException.class)
	public void writeValueAsStringNullTest() {
		try {
			mapperUtils.writeValueAsString(null);
		} catch (MapperUtilsException e) {
			Assert.assertEquals(e.getMessage(), "Input object is null");
			
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test(expected = MapperUtilsException.class)
	public void writeValueAsStringErrorTest() throws JsonProcessingException {
		Mockito.when(objectMapper.writeValueAsString(user)).thenThrow(new JsonGenerationException("error"));
		
		try {
			mapperUtils.writeValueAsString(user);
		} catch (MapperUtilsException e) {
			Assert.assertEquals(e.getMessage(), "Error while writing object");
			
			throw e;
		}
	}
	
	@Test
	public void readValueTest() {
		String userJson = mapperUtils.writeValueAsString(user);
		
		User userMapped = mapperUtils.readValue(userJson, User.class);
		
		Assert.assertEquals(user.getEmail(), userMapped.getEmail());
		Assert.assertEquals(user.getFirstName(), userMapped.getFirstName());
		Assert.assertEquals(user.getId(), userMapped.getId());
		Assert.assertEquals(user.getLastName(), userMapped.getLastName());
		Assert.assertEquals(user.getPassword(), userMapped.getPassword());
		Assert.assertEquals(user.getSecret(), userMapped.getSecret());
		Assert.assertEquals(user.isAutoLogin(), userMapped.isAutoLogin());
		Assert.assertEquals(user.isValidEmail(), userMapped.isValidEmail());
		Assert.assertEquals(user.getDevices().get(0).getDeviceId(), userMapped.getDevices().get(0).getDeviceId());
		Assert.assertEquals(user.getDevices().get(0).getId(), userMapped.getDevices().get(0).getId());
		Assert.assertEquals(user.getDevices().get(0).getVerificationCode(), userMapped.getDevices().get(0).getVerificationCode());
	}
	
	@Test
	public void readValueObjectTest() {
		User userMapped = mapperUtils.readValue(user, User.class);
		
		Assert.assertEquals(user.getEmail(), userMapped.getEmail());
		Assert.assertEquals(user.getFirstName(), userMapped.getFirstName());
		Assert.assertEquals(user.getId(), userMapped.getId());
		Assert.assertEquals(user.getLastName(), userMapped.getLastName());
		Assert.assertEquals(user.getPassword(), userMapped.getPassword());
		Assert.assertEquals(user.getSecret(), userMapped.getSecret());
		Assert.assertEquals(user.isAutoLogin(), userMapped.isAutoLogin());
		Assert.assertEquals(user.isValidEmail(), userMapped.isValidEmail());
		Assert.assertEquals(user.getDevices().get(0).getDeviceId(), userMapped.getDevices().get(0).getDeviceId());
		Assert.assertEquals(user.getDevices().get(0).getId(), userMapped.getDevices().get(0).getId());
		Assert.assertEquals(user.getDevices().get(0).getVerificationCode(), userMapped.getDevices().get(0).getVerificationCode());
	}
	
	@Test(expected = MapperUtilsException.class)
	public void readValueObjectNullTest() {
		try {
			mapperUtils.readValue(null, User.class);
		} catch (MapperUtilsException e) {
			Assert.assertEquals(e.getMessage(), "Input object is null");
			
			throw e;
		}
	}
	
	@Test(expected = MapperUtilsException.class)
	public void readValueClassNullTest() {
		try {
			mapperUtils.readValue(user, null);
		} catch (MapperUtilsException e) {
			Assert.assertEquals(e.getMessage(), "Input class is null");
			
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test(expected = MapperUtilsException.class)
	public void readValueErrorTest() throws JsonProcessingException {
		String userJson = mapperUtils.writeValueAsString(user);
		
		Mockito.when(objectMapper.readValue(userJson, User.class)).thenThrow(new JsonGenerationException("error"));
		
		try {
			mapperUtils.readValue(userJson, User.class);
		} catch (MapperUtilsException e) {
			Assert.assertEquals(e.getMessage(), "Error while reading value");
			
			throw e;
		}
	}

}
