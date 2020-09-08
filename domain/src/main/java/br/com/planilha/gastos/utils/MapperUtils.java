package br.com.planilha.gastos.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MapperUtils {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;

	public <D> D map(Object object, Class<D> clazz) {
		if(object == null) {
			throw new NullPointerException();
		} else {
			try {
				return modelMapper.map(object, clazz);
			} catch (Exception e){
				throw new NullPointerException();
			}
		}
	}
	
	public String writeValueAsString(Object object) {
		if(object == null) {
			throw new NullPointerException();
		} else {
			try {
				return objectMapper.writeValueAsString(object);
			} catch (Exception e){
				throw new NullPointerException();
			}
		}
	}

	public <T> T map(Object object, TypeReference<T> typeReference) {
		if(object == null) {
			throw new NullPointerException();
		} else {
			try {
				return objectMapper.readValue(writeValueAsString(object), typeReference);
			} catch (Exception e){
				throw new NullPointerException();
			}
		}
	}

	public <T> T readValue(Object object, Class<T> clazz) {
		if(object == null) {
			throw new NullPointerException();
		} else {
			try {
				if(object instanceof String) {
					return objectMapper.readValue((String) object, clazz);
				} 
				
				return objectMapper.readValue(writeValueAsString(object), clazz);
			} catch (Exception e){
				throw new NullPointerException();
			}
		}
	}
}
