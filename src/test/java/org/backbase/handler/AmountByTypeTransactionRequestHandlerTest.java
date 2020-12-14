package org.backbase.handler;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.backbase.config.AppConfig;
import org.backbase.model.Transaction;
import org.backbase.util.FileHelper;
import org.backbase.util.JsonHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@WebAppConfiguration
public class AmountByTypeTransactionRequestHandlerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void givenWac_whenServletContext_thenItProvidesGreetController() {
	    ServletContext servletContext = wac.getServletContext();
	    
	    Assert.assertNotNull(servletContext);
	    Assert.assertTrue(servletContext instanceof MockServletContext);
	    Assert.assertNotNull(wac.getBean("passwordEncoder"));
	}

	@Test
	public void testHandleRequestDefaultData() {
		OutputStream outputStream = null;
		FileOutputStream stream = null;
		
		try {
			// Get input file into BufferedReader
			InputStream inputStream = getClass().getResourceAsStream("/requestBodyDefault.json");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        // Get outputstream to a file.
			File file = File.createTempFile("temp", null);
	        stream = new FileOutputStream(file);
	        
	        HttpServletRequest request = mock(HttpServletRequest.class);
			when(request.getPathInfo()).thenReturn("/type/SEPA/amount");
			when(request.getReader()).thenReturn(reader);
			HttpServletResponse response = mock(HttpServletResponse.class);
			AmountByTypeTransactionsRequestHandler handler = new AmountByTypeTransactionsRequestHandler();
			//when(response.getOutputStream()).thenReturn(stream);
			when(response.getWriter()).thenReturn(new PrintWriter(stream, true));
			handler.handleRequest(request, response);
			
			// This is the API output.  The file should exist.
			Assert.assertTrue(file.exists());
			
			// Cleanup of strange characters output after implementing a test ServletOutputStream, but it works!
			String json = FileHelper.readFile(file);
			int jsonStart = json.indexOf('{');
			if (jsonStart != -1) json = json.substring(jsonStart);
			System.out.println("Json: " + json);
			// Make sure there are three transactions in the list.
			Assert.assertNotNull(json);
			Assert.assertEquals("{\"GBP\":\"41.20\"}", json);
			
			// get rid of the tmp file.
			file.deleteOnExit();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (stream != null) stream.close();
			} catch (IOException e) {}
		}
	}

	@Test
	public void testHandleRequestDefaultDataLarge() {
		OutputStream outputStream = null;
		FileOutputStream stream = null;
		
		try {
			// Get input file into BufferedReader
			InputStream inputStream = getClass().getResourceAsStream("/requestBodyDefaultLarge.json");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        // Get outputstream to a file.
			File file = File.createTempFile("temp", null);
	        stream = new FileOutputStream(file);
	        
	        HttpServletRequest request = mock(HttpServletRequest.class);
			when(request.getPathInfo()).thenReturn("/type/SEPA/amount");
			when(request.getReader()).thenReturn(reader);
			HttpServletResponse response = mock(HttpServletResponse.class);
			AmountByTypeTransactionsRequestHandler handler = new AmountByTypeTransactionsRequestHandler();
			//when(response.getOutputStream()).thenReturn(stream);
			when(response.getWriter()).thenReturn(new PrintWriter(stream, true));
			handler.handleRequest(request, response);
			
			// This is the API output.  The file should exist.
			Assert.assertTrue(file.exists());
			
			// TODO: Cleanup of strange characters output after implementing a test ServletOutputStream, but it works!
			String json = FileHelper.readFile(file);
			int jsonStart = json.indexOf('{');
			if (jsonStart != -1) json = json.substring(jsonStart);
			System.out.println("Json: " + json);
			// Make sure there are three transactions in the list.
			Assert.assertNotNull(json);
			Assert.assertEquals("{\"GBP\":\"421.40\"}", json);
			
			// get rid of the tmp file.
			file.deleteOnExit();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (stream != null) stream.close();
			} catch (IOException e) {}
		}
		
	}
	
	@Test
	public void testHandleRequestDifferentCurrencies() {
		OutputStream outputStream = null;
		FileOutputStream stream = null;
		
		try {
			// Get input file into BufferedReader
			InputStream inputStream = getClass().getResourceAsStream("/requestBodyDifferentCurrencies.json");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        // Get outputstream to a file.
			File file = File.createTempFile("temp", null);
	        stream = new FileOutputStream(file);
	        
	        HttpServletRequest request = mock(HttpServletRequest.class);
			when(request.getPathInfo()).thenReturn("/type/SEPA/amount");
			when(request.getReader()).thenReturn(reader);
			HttpServletResponse response = mock(HttpServletResponse.class);
			AmountByTypeTransactionsRequestHandler handler = new AmountByTypeTransactionsRequestHandler();
			//when(response.getOutputStream()).thenReturn(stream);
			when(response.getWriter()).thenReturn(new PrintWriter(stream, true));
			handler.handleRequest(request, response);
			
			// This is the API output.  The file should exist.
			Assert.assertTrue(file.exists());
			
			// Cleanup of strange characters output after implementing a test ServletOutputStream, but it works!
			String json = FileHelper.readFile(file);
			int jsonStart = json.indexOf('{');
			if (jsonStart != -1) json = json.substring(jsonStart);
			System.out.println("Json: " + json);
			// Make sure there are three transactions in the list.
			Assert.assertNotNull(json);
			Assert.assertEquals("{\"GBP\":\"8.60\",\"USD\":\"8.60\",\"CAD\":\"24.00\"}", json);
			
			// get rid of the tmp file.
			file.deleteOnExit();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (stream != null) stream.close();
			} catch (IOException e) {}
		}

		
	}	
	
}
