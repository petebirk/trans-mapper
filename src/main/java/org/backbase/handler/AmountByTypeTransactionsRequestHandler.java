package org.backbase.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.backbase.util.JsonHelper;
import org.backbase.util.RequestHelper;
import org.backbase.util.ThreadLocalHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="/transactions")
public class AmountByTypeTransactionsRequestHandler extends AbstractRequestHandler {

	private static Logger logger = LoggerFactory.getLogger(AmountByTypeTransactionsRequestHandler.class);

	@ApiOperation(value = "/type/{type}/amount",
			  notes = "Returns the amount summed up for each currency. If there are multiple currencies in the "
			  		+ "input data, there will be multiple currencies in the result which the sum of each currency "
			  		+ "seen in the filtered transaction list. The transactions considered for the summation are "
			  		+ "only those of the {type} specified.")
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			if (logger.isDebugEnabled()) logger.debug("Start request.");
			// Read the request body for transactions.  Same way for all three APIs.
			String payload = JsonHelper.readAsJson(request);
		    
			if (payload == null || payload.equals("")) {
				if (logger.isDebugEnabled()) logger.debug("Request body empty.");
				returnBadRequest(response, "request body empty");
				return;
			}
		    
			try {
				// Need to pass path info to custom serializer which doesn't have interface to receive it.
				// This is a thread safe way to communicate with this other class and removing the threadlocal
				// immediately after use, below in the finally block.
				String requestFilter = RequestHelper.getTypeFilter(request.getPathInfo());
				ThreadLocalHelper.set(requestFilter);
				
				// Custom deserializer is for performance reasons.  We can completely ignore data that is not
				// of the specified type.   Otherwise, we'd have an O(n*n) instead of O(n) performance.
				ObjectMapper customObjectMapper = RequestHandlerFactory.getRequestDeserializer(RequestHelper.requestType(request.getPathInfo()));
				String json = customObjectMapper.readValue(payload, String.class);
				JsonHelper.sendAsJson(response, json);
				if (logger.isDebugEnabled()) logger.debug("Currency amounts sent: " + json);
				
			} finally {
				ThreadLocalHelper.remove();
			}
		} catch (IOException e) {
			logger.error("IOException", e);
			returnInternalServerError(response, "IOException (see server logs for details)");
		} finally {
			if (logger.isDebugEnabled()) logger.debug("End request.");
		}
	}
	
}
