package org.backbase.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.backbase.handler.AllTransactionDeserializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Helper class for JSON handling.
 * @author peterbirk
 *
 */
public class JsonHelper {
	
	public static String readAsJson(HttpServletRequest request) throws IOException {
		// Grab the incoming JSON
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	    }
	    return buffer.toString();
	}
	
	/**
	 * Send the JSON response.
	 * @param response
	 * @param transactions
	 * @throws IOException
	 */
	public static void sendAsJson(
		HttpServletResponse response, 
		List<org.backbase.model.Transaction> transactions) throws IOException {
			response.setContentType("application/json");
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), transactions);  
	}

	/**
	 * Send the JSON response.
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public static void sendAsJson(
		HttpServletResponse response, 
		String json) throws IOException {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
	}
	
	/**
	 * Returns properly configured object mapper.
	 * @return
	 */
	public static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}

	public static ObjectMapper getCustomObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(List.class, new AllTransactionDeserializer());
		mapper.registerModule(module);
		return mapper;
	}
}
