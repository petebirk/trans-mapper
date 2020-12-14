package org.backbase.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.backbase.util.ResponseHelper;

/**
 * Abstract Class for API RequestHandler
 * @author peterbirk
 *
 */
public abstract class AbstractRequestHandler {

	public abstract void handleRequest (HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	public void returnBadRequest (HttpServletResponse response, String message) throws IOException {
		ResponseHelper.returnBadRequest(response, message);
	}

	public void returnInternalServerError (HttpServletResponse response, String message) throws IOException {
		ResponseHelper.returnInternalServerError(response, message);
	}
	
}