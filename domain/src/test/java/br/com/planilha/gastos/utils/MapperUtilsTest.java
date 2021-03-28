package br.com.planilha.gastos.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.planilha.gastos.entity.Device;
import br.com.planilha.gastos.entity.User;
import br.com.planilha.gastos.exception.MapperUtilsException;

@SuppressWarnings({"deprecation", "unchecked"})
@ExtendWith(SpringExtension.class)
public class MapperUtilsTest {

	@InjectMocks
	private MapperUtils mapperUtils;

	@Spy
	private ModelMapper modelMapper;

	@Spy
	private ObjectMapper objectMapper;
	
	private User user;
	private TypeReference<Map<String, Object>> typeReference;
	
	@BeforeEach
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
	
	@Test
	public void mapTest() {
		String userJson = mapperUtils.writeValueAsString(user);
		
		Map<String, Object> userMapped = mapperUtils.map(userJson, typeReference);
		
		Assertions.assertEquals(user.getEmail(), userMapped.get("email"));
		Assertions.assertEquals(user.getFirstName(), userMapped.get("firstName"));
		Assertions.assertEquals(user.getId(), userMapped.get("id"));
		Assertions.assertEquals(user.getLastName(), userMapped.get("lastName"));
		Assertions.assertEquals(user.getPassword(), userMapped.get("password"));
		Assertions.assertEquals(user.getSecret(), userMapped.get("secret"));
		Assertions.assertEquals(user.isAutoLogin(), userMapped.get("autoLogin"));
		Assertions.assertEquals(user.isValidEmail(), userMapped.get("validEmail"));
		Assertions.assertEquals(user.getDevices().get(0).getDeviceId(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("deviceId"));
		Assertions.assertEquals(user.getDevices().get(0).getId(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("id"));
		Assertions.assertEquals(user.getDevices().get(0).getVerificationCode(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("verificationCode"));
	}
	
	@Test
	public void mapObjectTest() {
		Map<String, Object> userMapped = mapperUtils.map(user, typeReference);
		
		Assertions.assertEquals(user.getEmail(), userMapped.get("email"));
		Assertions.assertEquals(user.getFirstName(), userMapped.get("firstName"));
		Assertions.assertEquals(user.getId(), userMapped.get("id"));
		Assertions.assertEquals(user.getLastName(), userMapped.get("lastName"));
		Assertions.assertEquals(user.getPassword(), userMapped.get("password"));
		Assertions.assertEquals(user.getSecret(), userMapped.get("secret"));
		Assertions.assertEquals(user.isAutoLogin(), userMapped.get("autoLogin"));
		Assertions.assertEquals(user.isValidEmail(), userMapped.get("validEmail"));
		Assertions.assertEquals(user.getDevices().get(0).getDeviceId(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("deviceId"));
		Assertions.assertEquals(user.getDevices().get(0).getId(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("id"));
		Assertions.assertEquals(user.getDevices().get(0).getVerificationCode(), ((List<Map<String, String>>) userMapped.get("devices")).get(0).get("verificationCode"));
	}
	
	@Test
	public void mapObjectNullTest() {
		Assertions.assertThrows(
				MapperUtilsException.class, 
				() -> mapperUtils.map(null, typeReference), 
				"Input object is null");
	}
	
	@Test
	public void mapClassNullTest() {
		Assertions.assertThrows(
				MapperUtilsException.class, 
				() -> mapperUtils.map(user, null), 
				"Input typeReference is null");
	}
	
	@Test
	public void mapErrorTest() throws JsonMappingException, JsonProcessingException {
		String userJson = mapperUtils.writeValueAsString(user);
		
		Mockito.when(objectMapper.readValue(userJson, typeReference)).thenThrow(new JsonGenerationException("error"));
		
		Assertions.assertThrows(
				MapperUtilsException.class, 
				() -> mapperUtils.map(userJson, typeReference), 
				"Error while mapping object with TypeReference");
	}
	
	@Test
	public void writeValueAsStringTest() {
		String userJson = mapperUtils.writeValueAsString(user);
		
		Assertions.assertNotNull(userJson);
		Assertions.assertFalse(userJson.isBlank());
		Assertions.assertTrue(userJson.contains(user.getEmail()));
		Assertions.assertTrue(userJson.contains(user.getFirstName()));
		Assertions.assertTrue(userJson.contains(user.getId()));
		Assertions.assertTrue(userJson.contains(user.getLastName()));
		Assertions.assertTrue(userJson.contains(user.getPassword()));
		Assertions.assertTrue(userJson.contains(user.getSecret()));
		Assertions.assertTrue(userJson.contains(String.valueOf(user.isAutoLogin())));
		Assertions.assertTrue(userJson.contains(String.valueOf(user.isValidEmail())));
		Assertions.assertTrue(userJson.contains(user.getDevices().get(0).getDeviceId()));
		Assertions.assertTrue(userJson.contains(user.getDevices().get(0).getId()));
		Assertions.assertTrue(userJson.contains(user.getDevices().get(0).getVerificationCode()));
	}
	
	@Test
	public void writeValueAsStringNullTest() {
		Assertions.assertThrows(
				MapperUtilsException.class, 
				() -> mapperUtils.writeValueAsString(null), 
				"Input object is null");
	}
	
	@Test
	public void writeValueAsStringErrorTest() throws JsonProcessingException {
		Mockito.when(objectMapper.writeValueAsString(user)).thenThrow(new JsonGenerationException("error"));
		
		Assertions.assertThrows(
				MapperUtilsException.class, 
				() -> mapperUtils.writeValueAsString(user), 
				"Error while writing object");
	}
	
	@Test
	public void readValueTest() {
		String userJson = mapperUtils.writeValueAsString(user);
		
		User userMapped = mapperUtils.readValue(userJson, User.class);
		
		Assertions.assertEquals(user.getEmail(), userMapped.getEmail());
		Assertions.assertEquals(user.getFirstName(), userMapped.getFirstName());
		Assertions.assertEquals(user.getId(), userMapped.getId());
		Assertions.assertEquals(user.getLastName(), userMapped.getLastName());
		Assertions.assertEquals(user.getPassword(), userMapped.getPassword());
		Assertions.assertEquals(user.getSecret(), userMapped.getSecret());
		Assertions.assertEquals(user.isAutoLogin(), userMapped.isAutoLogin());
		Assertions.assertEquals(user.isValidEmail(), userMapped.isValidEmail());
		Assertions.assertEquals(user.getDevices().get(0).getDeviceId(), userMapped.getDevices().get(0).getDeviceId());
		Assertions.assertEquals(user.getDevices().get(0).getId(), userMapped.getDevices().get(0).getId());
		Assertions.assertEquals(user.getDevices().get(0).getVerificationCode(), userMapped.getDevices().get(0).getVerificationCode());
	}
	
	@Test
	public void readValueObjectTest() {
		User userMapped = mapperUtils.readValue(user, User.class);
		
		Assertions.assertEquals(user.getEmail(), userMapped.getEmail());
		Assertions.assertEquals(user.getFirstName(), userMapped.getFirstName());
		Assertions.assertEquals(user.getId(), userMapped.getId());
		Assertions.assertEquals(user.getLastName(), userMapped.getLastName());
		Assertions.assertEquals(user.getPassword(), userMapped.getPassword());
		Assertions.assertEquals(user.getSecret(), userMapped.getSecret());
		Assertions.assertEquals(user.isAutoLogin(), userMapped.isAutoLogin());
		Assertions.assertEquals(user.isValidEmail(), userMapped.isValidEmail());
		Assertions.assertEquals(user.getDevices().get(0).getDeviceId(), userMapped.getDevices().get(0).getDeviceId());
		Assertions.assertEquals(user.getDevices().get(0).getId(), userMapped.getDevices().get(0).getId());
		Assertions.assertEquals(user.getDevices().get(0).getVerificationCode(), userMapped.getDevices().get(0).getVerificationCode());
	}
	
	@Test
	public void readValueObjectNullTest() {
		Assertions.assertThrows(
				MapperUtilsException.class, 
				() -> mapperUtils.readValue(null, User.class), 
				"Input object is null");
	}
	
	@Test
	public void readValueClassNullTest() {
		Assertions.assertThrows(
				MapperUtilsException.class, 
				() -> mapperUtils.readValue(user, null), 
				"Input class is null");
	}
	
	@Test
	public void readValueErrorTest() throws JsonProcessingException {
		String userJson = mapperUtils.writeValueAsString(user);
		
		Mockito.when(objectMapper.readValue(userJson, User.class)).thenThrow(new JsonGenerationException("error"));
		
		Assertions.assertThrows(
				MapperUtilsException.class, 
				() -> mapperUtils.readValue(userJson, User.class), 
				"Error while reading value");
	}

}
