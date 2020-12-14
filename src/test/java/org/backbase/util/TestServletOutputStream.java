package org.backbase.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletOutputStream;

public class TestServletOutputStream extends ServletOutputStream {

	FileOutputStream outputStream = null;
	ObjectOutputStream OOStream = null;
	javax.servlet.WriteListener listener = null;
	boolean ready = true;
	
	public TestServletOutputStream (File tempFile) {
	    try {
			outputStream = new FileOutputStream(tempFile, true);
			OOStream = new ObjectOutputStream(outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean isReady() {
		return ready;
	}

	@Override
	public void write(int b) throws IOException {
		ready=false;
		OOStream.write(b);
		if (this.listener != null) this.listener.notify();
		ready=true;
	}
	
	public void setWriteListener(javax.servlet.WriteListener listener) {
		this.listener = listener;
	}
	
	public void close() {
		try {
			if (OOStream != null) OOStream.close();
			if (outputStream != null) outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
