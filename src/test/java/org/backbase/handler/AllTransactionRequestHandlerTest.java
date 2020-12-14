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
public class AllTransactionRequestHandlerTest {

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
		TestServletOutputStream stream = null;
		
		try {
			// Get input file into BufferedReader
			InputStream inputStream = getClass().getResourceAsStream("/requestBodyDefault.json");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        // Get outputstream to a file.
			File file = File.createTempFile("temp", null);
	        stream = new TestServletOutputStream(file);
	        
	        HttpServletRequest request = mock(HttpServletRequest.class);
			when(request.getPathInfo()).thenReturn("/");
			when(request.getReader()).thenReturn(reader);
			HttpServletResponse response = mock(HttpServletResponse.class);
			AllTransactionsRequestHandler handler = new AllTransactionsRequestHandler();
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
	public void testHandleRequestDefaultLargeData() {
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
			when(request.getPathInfo()).thenReturn("/");
			when(request.getReader()).thenReturn(reader);
			HttpServletResponse response = mock(HttpServletResponse.class);
			AllTransactionsRequestHandler handler = new AllTransactionsRequestHandler();
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
			Assert.assertTrue(transList.size() == 99);
			
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
				Assert.assertTrue(tran.getTransactionType().contentEquals("SEPA") ||
								  tran.getTransactionType().contentEquals("SEPB"));
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

	
}
