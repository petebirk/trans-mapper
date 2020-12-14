package org.backbase.handler;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class RequestHandlerFactory {
	
	public static AbstractRequestHandler getRequestHandler (String type) throws IllegalArgumentException {
		if ("BASE".contentEquals(type)) {
			return new AllTransactionsRequestHandler();
		} else if ("TYPE".equals(type)) {
			return new ByTypeTransactionsRequestHandler();
		} else if ("AMOUNT".equals(type)) {
			return new AmountByTypeTransactionsRequestHandler();
		} else {
			throw new IllegalArgumentException ("Invalid request type specified.");
		}
	}


	public static ObjectMapper getRequestDeserializer (String type) throws IllegalArgumentException {
		if ("BASE".contentEquals(type)) {
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(List.class, new AllTransactionDeserializer());
			mapper.registerModule(module);
			return mapper;
		} else if ("TYPE".equals(type)) {
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(List.class, new ByTypeTransactionDeserializer());
			mapper.registerModule(module);
			return mapper;
		} else if ("AMOUNT".equals(type)) {
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(String.class, new AmountByTypeTransactionDeserializer());
			mapper.registerModule(module);
			return mapper;
		} else {
			throw new IllegalArgumentException ("Invalid request type specified.");
		}
	}

}
