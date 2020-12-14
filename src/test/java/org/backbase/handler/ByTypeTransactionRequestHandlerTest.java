package org.backbase.handler;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.backbase.config.AppConfig;
import org.backbase.model.Transaction;
import org.backbase.util.FileHelper;
import org.backbase.util.JsonHelper;
import org.backbase.util.TestServletOutputStream;
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
public class ByTypeTransactionRequestHandlerTest {

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
	public void testHandleRequestDefaultDataWithValidType() {
		OutputStream outputStream = null;
		TestServletOutputStream stream = null;
		
		try {
			// Get input file into BufferedReader
			InputStream inputStream = getClass().getResourceAsStream("/requestBodyDefault.json");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        // Get outputstream to a file.
			File file = File.createTempFile("temp", null);
	        stream = new TestServletOutputStream(file);
	        
	        HttpServletRequest request = mock(HttpServletRequest.class);
			when(request.getPathInfo()).thenReturn("/type/SEPA");
			when(request.getReader()).thenReturn(reader);
			HttpServletResponse response = mock(HttpServletResponse.class);
			ByTypeTransactionsRequestHandler handler = new ByTypeTransactionsRequestHandler();
			when(response.getOutputStream()).thenReturn(stream);
			handler.handleRequest(request, response);
			
			// This is the API output.  The file should exist.
			Assert.assertTrue(file.exists());
			
			// Cleanup of strange characters output after implementing a test ServletOutputStream, but it works!
			String json = FileHelper.readFile(file);
			int arrayStart = json.indexOf('[');
			json = json.substring(arrayStart);
			json = json.replaceAll("wS", "");
			System.out.println("Json: " + json);
			ObjectMapper mapper = JsonHelper.getObjectMapper();
			List<org.backbase.model.Transaction> transList = mapper.readValue(json, new TypeReference<List<org.backbase.model.Transaction>>(){});
			
			// Make sure there are three transactions in the list.
			Assert.assertTrue(transList.size() == 3);
			
			for (int i=0; i<transList.size(); i++) {
				Transaction tran = transList.get(i);
				Assert.assertNotNull(tran.getAccountId());
				Assert.assertNotNull(tran.getCounterpartyAccount());
				Assert.assertNotNull(tran.getCounterPartyLogoPath());
				Assert.assertNotNull(tran.getCounterpartyName());
				Assert.assertNotNull(tran.getId());
				Assert.assertNotNull(tran.getDescription());
				Assert.assertNotNull(tran.getInstructedAmount());
				Assert.assertNotNull(tran.getInstructedCurrency());
				Assert.assertNotNull(tran.getTransactionAmount());
				Assert.assertNotNull(tran.getTransactionCurrency());
				Assert.assertNotNull(tran.getTransactionType());
				Assert.assertEquals("SEPA", tran.getTransactionType());
				System.out.println(tran);
			}
			
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
	public void testHandleRequestDefaultLargeDataWithSEPAType() {
		OutputStream outputStream = null;
		TestServletOutputStream stream = null;
		
		try {
			// Get input file into BufferedReader
			InputStream inputStream = getClass().getResourceAsStream("/requestBodyDefaultLarge.json");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        // Get outputstream to a file.
			File file = File.createTempFile("temp", null);
	        stream = new TestServletOutputStream(file);
	        
	        HttpServletRequest request = mock(HttpServletRequest.class);
			when(request.getPathInfo()).thenReturn("/type/SEPA");
			when(request.getReader()).thenReturn(reader);
			HttpServletResponse response = mock(HttpServletResponse.class);
			ByTypeTransactionsRequestHandler handler = new ByTypeTransactionsRequestHandler();
			when(response.getOutputStream()).thenReturn(stream);
			handler.handleRequest(request, response);
			
			// This is the API output.  The file should exist.
			Assert.assertTrue(file.exists());
			
			// Cleanup of strange characters output after implementing a test ServletOutputStream, but it works!
			String json = FileHelper.readFile(file);
			int arrayStart = json.indexOf('[');
			json = json.substring(arrayStart);
			json = json.replaceAll("wS", "");
			json = json.replaceAll("�", "");
			json = json.replaceAll("z", "");
			json = json.replaceAll("\\p{Cntrl}", "");
			System.out.println("Json: " + json);
			ObjectMapper mapper = JsonHelper.getObjectMapper();
			List<org.backbase.model.Transaction> transList = mapper.readValue(json, new TypeReference<List<org.backbase.model.Transaction>>(){});
			
			// Make sure there are three transactions in the list.
			Assert.assertTrue(transList.size() == 49);
			
			for (int i=0; i<transList.size(); i++) {
				Transaction tran = transList.get(i);
				Assert.assertNotNull(tran.getAccountId());
				Assert.assertNotNull(tran.getCounterpartyAccount());
				Assert.assertNotNull(tran.getCounterPartyLogoPath());
				Assert.assertNotNull(tran.getCounterpartyName());
				Assert.assertNotNull(tran.getId());
				Assert.assertNotNull(tran.getDescription());
				Assert.assertNotNull(tran.getInstructedAmount());
				Assert.assertNotNull(tran.getInstructedCurrency());
				Assert.assertNotNull(tran.getTransactionAmount());
				Assert.assertNotNull(tran.getTransactionCurrency());
				Assert.assertNotNull(tran.getTransactionType());
				Assert.assertEquals("SEPA", tran.getTransactionType());
				System.out.println(tran);
			}
			
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
	public void testHandleRequestDefaultLargeDataWithSEPBType() {
		OutputStream outputStream = null;
		TestServletOutputStream stream = null;
		
		try {
			// Get input file into BufferedReader
			InputStream inputStream = getClass().getResourceAsStream("/requestBodyDefaultLarge.json");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        // Get outputstream to a file.
			File file = File.createTempFile("temp", null);
	        stream = new TestServletOutputStream(file);
	        
	        HttpServletRequest request = mock(HttpServletRequest.class);
			when(request.getPathInfo()).thenReturn("/type/SEPB");
			when(request.getReader()).thenReturn(reader);
			HttpServletResponse response = mock(HttpServletResponse.class);
			ByTypeTransactionsRequestHandler handler = new ByTypeTransactionsRequestHandler();
			when(response.getOutputStream()).thenReturn(stream);
			handler.handleRequest(request, response);
			
			// This is the API output.  The file should exist.
			Assert.assertTrue(file.exists());
			
			// TODO: Cleanup of strange characters output after implementing a test ServletOutputStream, but it works!
			String json = FileHelper.readFile(file);
			int arrayStart = json.indexOf('[');
			json = json.substring(arrayStart);
			json = json.replaceAll("wS", "");
			json = json.replaceAll("�", "");
			json = json.replaceAll("z", "");
			json = json.replaceAll("wC", "");
			json = json.replaceAll("\\p{Cntrl}", "");
			System.out.println("Json: " + json);
			ObjectMapper mapper = JsonHelper.getObjectMapper();
			List<org.backbase.model.Transaction> transList = mapper.readValue(json, new TypeReference<List<org.backbase.model.Transaction>>(){});
			
			// Make sure there are three transactions in the list.
			Assert.assertTrue(transList.size() == 50);
			System.out.println("temp file: " + file.getAbsolutePath());
			
			for (int i=0; i<transList.size(); i++) {
				Transaction tran = transList.get(i);
				System.out.println(tran);
				Assert.assertNotNull(tran.getAccountId());
				Assert.assertNotNull(tran.getCounterpartyAccount());
				Assert.assertNotNull(tran.getCounterPartyLogoPath());
				Assert.assertNotNull(tran.getCounterpartyName());
				Assert.assertNotNull(tran.getId());
				Assert.assertNotNull(tran.getDescription());
				Assert.assertNotNull(tran.getInstructedAmount());
				Assert.assertNotNull(tran.getInstructedCurrency());
				Assert.assertNotNull(tran.getTransactionAmount());
				Assert.assertNotNull(tran.getTransactionCurrency());
				Assert.assertNotNull(tran.getTransactionType());
				Assert.assertEquals("SEPB", tran.getTransactionType());
				System.out.println(tran);
			}
			
			// get rid of the tmp file.
			//file.deleteOnExit();
			
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
	public void testHandleRequestDefaultDataWithInvalidType() {
		OutputStream outputStream = null;
		TestServletOutputStream stream = null;
		
		try {
			// Get input file into BufferedReader
			InputStream inputStream = getClass().getResourceAsStream("/requestBodyDefault.json");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        // Get outputstream to a file.
			File file = File.createTempFile("temp", null);
	        stream = new TestServletOutputStream(file);
	        
	        HttpServletRequest request = mock(HttpServletRequest.class);
			when(request.getPathInfo()).thenReturn("/type/NOT_VALID");
			when(request.getReader()).thenReturn(reader);
			HttpServletResponse response = mock(HttpServletResponse.class);
			ByTypeTransactionsRequestHandler handler = new ByTypeTransactionsRequestHandler();
			when(response.getOutputStream()).thenReturn(stream);
			handler.handleRequest(request, response);
						
			// This is the API output.  The file should exist.
			Assert.assertTrue(file.exists());
			
			// Cleanup of strange characters output after implementing a test ServletOutputStream, but it works!
			String json = FileHelper.readFile(file);
			int arrayStart = json.indexOf('[');
			json = json.substring(arrayStart);
			json = json.replaceAll("wS", "");
			System.out.println("Json: " + json);
			ObjectMapper mapper = JsonHelper.getObjectMapper();
			List<org.backbase.model.Transaction> transList = mapper.readValue(json, new TypeReference<List<org.backbase.model.Transaction>>(){});
			
			// Make sure there are NO transactions in the list.
			Assert.assertTrue(transList.size() == 0);
						
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
	public void testHandleRequestDifferentTypesTypeSEPB() {
		OutputStream outputStream = null;
		TestServletOutputStream stream = null;
		
		try {
			// Get input file into BufferedReader
			InputStream inputStream = getClass().getResourceAsStream("/requestBodyDifferentTypes.json");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        // Get outputstream to a file.
			File file = File.createTempFile("temp", null);
	        stream = new TestServletOutputStream(file);
	        
	        HttpServletRequest request = mock(HttpServletRequest.class);
			when(request.getPathInfo()).thenReturn("/type/SEPB");
			when(request.getReader()).thenReturn(reader);
			HttpServletResponse response = mock(HttpServletResponse.class);
			ByTypeTransactionsRequestHandler handler = new ByTypeTransactionsRequestHandler();
			when(response.getOutputStream()).thenReturn(stream);
			handler.handleRequest(request, response);
						
			// This is the API output.  The file should exist.
			Assert.assertTrue(file.exists());
			
			// Cleanup of strange characters output after implementing a test ServletOutputStream, but it works!
			String json = FileHelper.readFile(file);
			int arrayStart = json.indexOf('[');
			json = json.substring(arrayStart);
			json = json.replaceAll("wS", "");
			System.out.println("Json: " + json);
			ObjectMapper mapper = JsonHelper.getObjectMapper();
			List<org.backbase.model.Transaction> transList = mapper.readValue(json, new TypeReference<List<org.backbase.model.Transaction>>(){});
			
			// Should be just one SEPB transaction in the list.
			Assert.assertTrue(transList.size() == 1);
			
			Transaction trans = transList.get(0);
			Assert.assertEquals("e22b7066-d02f-41fa-a84f-5dbfcc39e307", trans.getId());
			Assert.assertEquals("savings-kids-john", trans.getAccountId());
						
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
