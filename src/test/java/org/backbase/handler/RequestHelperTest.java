package org.backbase.handler;

import org.backbase.util.RequestHelper;
import org.junit.Test;
import org.junit.Assert;

public class RequestHelperTest {

	
	
	@Test
	public void testValidRequestTypes () {
		
		String pathInfo = null;
		String requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("BASE", requestType);

		pathInfo = "";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("BASE", requestType);

		pathInfo = "/";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("BASE", requestType);
		
		pathInfo = "/type/123";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("TYPE", requestType);
		
		pathInfo = "/type/1";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("TYPE", requestType);

		pathInfo = "/type/1234567890123456789012345678901234567890123456789012345678901234567890/";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("TYPE", requestType);
		
		pathInfo = "/type/1/amount";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("AMOUNT", requestType);
		
		pathInfo = "/type/amount/amount";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("AMOUNT", requestType);
		
		pathInfo = "/type/1/amount/";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("AMOUNT", requestType);
		
	}
	
	@Test
	public void testInvalidRequestTypes () {
		
		String pathInfo = "/amount";
		String requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("", requestType);

		pathInfo = "/type/123/123";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("", requestType);
		
		pathInfo = "/123/type";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("", requestType);
		
		pathInfo = "/type/123/amount/123";
		requestType = RequestHelper.requestType(pathInfo);
		Assert.assertEquals("", requestType);
		
	}
	
	@Test
	public void testValidTypeFilters() {

		String pathInfo = "/type/123";
		String typeFilter = RequestHelper.getTypeFilter(pathInfo);
		Assert.assertEquals("123", typeFilter);
		
		pathInfo = "/type/123/";
		typeFilter = RequestHelper.getTypeFilter(pathInfo);
		Assert.assertEquals("123", typeFilter);

		pathInfo = "/type/1";
		typeFilter = RequestHelper.getTypeFilter(pathInfo);
		Assert.assertEquals("1", typeFilter);
	
		pathInfo = "/type/789/";
		typeFilter = RequestHelper.getTypeFilter(pathInfo);
		Assert.assertEquals("789", typeFilter);
		
		pathInfo = "/type/SEPA/amount";
		typeFilter = RequestHelper.getTypeFilter(pathInfo);
		Assert.assertEquals("SEPA", typeFilter);

		pathInfo = "/type/1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
		typeFilter = RequestHelper.getTypeFilter(pathInfo);
		Assert.assertEquals("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890", typeFilter);

		pathInfo = "/type/amount/amount";
		typeFilter = RequestHelper.getTypeFilter(pathInfo);
		Assert.assertEquals("amount", typeFilter);

	}

	@Test
	public void testInvalidTypeFilters() {

		String pathInfo = "/type";
		String typeFilter = RequestHelper.getTypeFilter(pathInfo);
		Assert.assertEquals("", typeFilter);
		
		pathInfo = "/";
		typeFilter = RequestHelper.getTypeFilter(pathInfo);
		Assert.assertEquals("", typeFilter);
		
		pathInfo = null;
		typeFilter = RequestHelper.getTypeFilter(pathInfo);
		Assert.assertEquals("", typeFilter);
			
	}



}
