package org.backbase.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileHelper {

	public static String readFile (File file) throws IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(file));
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	    }
	    return buffer.toString();
	}
}
