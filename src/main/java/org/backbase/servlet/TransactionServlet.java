package org.backbase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.backbase.handler.AbstractRequestHandler;
import org.backbase.handler.RequestHandlerFactory;
import org.backbase.util.RequestHelper;
import org.backbase.util.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet class for Transaction Mapper API
 * @author peterbirk
 *
 */
@WebServlet("/api/v1/transactions/*")
public class TransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(TransactionServlet.class);
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String requestType = RequestHelper.requestType(request.getPathInfo());
			AbstractRequestHandler handler = RequestHandlerFactory.getRequestHandler(requestType);
			handler.handleRequest(request, response);
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException", e);
			ResponseHelper.returnBadRequest(response, "invalid request path");
			return;
		} catch (IOException e) {
			logger.error("IOException", e);
			ResponseHelper.returnInternalServerError(response, "IOException during request handling");
			return;
		} catch (Exception e) {
			logger.error("Exception", e);
			ResponseHelper.returnInternalServerError(response, "Generic Exception (see server logs for details)");
			return;
		}
	}
	
}
