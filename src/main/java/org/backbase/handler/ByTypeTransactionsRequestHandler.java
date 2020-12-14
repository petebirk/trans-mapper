package org.backbase.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.backbase.util.JsonHelper;
import org.backbase.util.RequestHelper;
import org.backbase.util.ThreadLocalHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="/transactions")
public class ByTypeTransactionsRequestHandler extends AbstractRequestHandler {
	
	public ByTypeTransactionsRequestHandler () {};
	private static Logger logger = LoggerFactory.getLogger(ByTypeTransactionsRequestHandler.class);

	@ApiOperation(value = "/type/{type}",
			  notes = "Converts just the transactions of the type specified in the path variable {type} "
			  		+ "from OpenBank format into BackBase format. The value of {type} comes from the "
			  		+ "OpenBank field details.type.")
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			if (logger.isDebugEnabled()) logger.debug("Start request.");
			// Read the request body for transactions.  Same way for all three APIs.
			String payload = JsonHelper.readAsJson(request);
			if (logger.isDebugEnabled()) logger.debug("Full payload to be mapped and filtered: " + payload);
		    
			if (payload == null || payload.equals("")) {
				if (logger.isDebugEnabled()) logger.debug("Request body empty.");
				returnBadRequest(response, "request body empty");
				return;
			}
		    
			try {
				// Need to pass path info to custom serializer which doesn't have interface to receive it.
				// This is a thread safe way to communicate with this other class and removing the threadlocal
				// immediately after use, below in the finally block.
				String requestType = RequestHelper.getTypeFilter(request.getPathInfo());
				ThreadLocalHelper.set(requestType);
				
				// Custom deserializer is for performance reasons.  We can completely ignore data that is not
				// of the specified type.   Otherwise, we'd have an O(n*n) instead of O(n) performance.
				ObjectMapper customObjectMapper = RequestHandlerFactory.getRequestDeserializer(RequestHelper.requestType(request.getPathInfo()));
				List<org.backbase.model.Transaction> backBaseList = customObjectMapper.readValue(payload, new TypeReference<List<org.openbank.model.Transaction>>(){});
			    JsonHelper.sendAsJson(response, backBaseList);
				if (logger.isDebugEnabled()) logger.debug("Resulting mapped and filtered transactions: " + backBaseList);
				
			} finally {
				ThreadLocalHelper.remove();
			}
		} catch (IOException e) {
			e.printStackTrace();
			returnInternalServerError(response, "IOException (see server logs for details)");
		} finally {
			if (logger.isDebugEnabled()) logger.debug("End request.");
		}
	}
	
}
