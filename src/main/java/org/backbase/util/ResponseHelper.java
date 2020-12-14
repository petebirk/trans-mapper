package org.backbase.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseHelper {

	public static void returnBadRequest (HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 400 - " + message);
	}

	public static void returnInternalServerError (HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 500 - " + message);
	}
}
