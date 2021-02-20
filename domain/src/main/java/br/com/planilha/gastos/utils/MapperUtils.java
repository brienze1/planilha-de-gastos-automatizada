package br.com.planilha.gastos.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.planilha.gastos.exception.MapperUtilsException;

@Component
public class MapperUtils {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public <T> T map(Object object, TypeReference<T> typeReference) {
		if(object == null) {
			throw new MapperUtilsException("Input object is null");
		} else if(typeReference == null) {
			throw new MapperUtilsException("Input typeReference is null");
		} else {
			try {
				if(object instanceof String) {
					return objectMapper.readValue((String) object, typeReference);
				}
				
				return objectMapper.readValue(writeValueAsString(object), typeReference);
			} catch (Exception e){
				throw new MapperUtilsException("Error while mapping object with TypeReference", e);
			}
		}
	}
	
	public String writeValueAsString(Object object) {
		if(object == null) {
			throw new MapperUtilsException("Input object is null");
		} else {
			try {
				return objectMapper.writeValueAsString(object);
			} catch (Exception e){
				throw new MapperUtilsException("Error while writing object", e);
			}
		}
	}

	public <T> T readValue(Object object, Class<T> clazz) {
		if(object == null) {
			throw new MapperUtilsException("Input object is null");
		} else if(clazz == null) {
			throw new MapperUtilsException("Input class is null");
		} else {
			try {
				if(object instanceof String) {
					return objectMapper.readValue((String) object, clazz);
				} 
				
				return objectMapper.readValue(writeValueAsString(object), clazz);
			} catch (Exception e){
				throw new MapperUtilsException("Error while reading value", e);
			}
		}
	}
}
