package org.backbase.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.backbase.util.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Handles requests for all mapping all transactions.
 * @author peterbirk
 *
 */
@Api(value="/transactions")
public class AllTransactionsRequestHandler extends AbstractRequestHandler {

	private static Logger logger = LoggerFactory.getLogger(AllTransactionsRequestHandler.class);
	
	@ApiOperation(value = "",
		  notes = "Converts all transactions from OpenBank format into BackBase format, mapping specific fields from one into the other.")
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			if (logger.isDebugEnabled()) logger.debug("Start request.");
			// Read the request body for transactions.  Same way for all three APIs.
			String payload = JsonHelper.readAsJson(request);
			if (logger.isDebugEnabled()) logger.debug("Payload received: " + payload);
		    
			if (payload == null || payload.equals("")) {
				returnBadRequest(response, "request body empty");
				return;
			}
			
			ObjectMapper customObjectMapper = JsonHelper.getCustomObjectMapper();
			List<org.backbase.model.Transaction> backBaseList = customObjectMapper.readValue(payload, new TypeReference<List<org.openbank.model.Transaction>>(){});
			if (logger.isDebugEnabled()) logger.debug("Returning mapped list: " + backBaseList);
			JsonHelper.sendAsJson(response, backBaseList);
		    
		} catch (IOException e) {
			logger.error("IOException", e);
			returnInternalServerError(response, "IOException (see server logs for details)");
		} finally {
			if (logger.isDebugEnabled()) logger.debug("End request.");
		}
	}
	
}
