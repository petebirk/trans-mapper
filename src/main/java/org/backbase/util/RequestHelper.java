package org.backbase.util;

public class RequestHelper {

	public static String requestType (String pathInfo) {
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) return "BASE";
		String[] split = pathInfo.split("/");
		if (split.length == 3 && split[1].equals("type")) return "TYPE";
		else if (split.length == 4 && split[3].equals("amount")) return "AMOUNT";
		else return "";
	}

	public static String getTypeFilter (String pathInfo) {
		if (pathInfo == null) return "";
		String[] split = pathInfo.split("/");
		if (split != null && split.length >= 3) return split[2];
		return "";
	}
		
}
