package org.backbase.util;

public class ThreadLocalHelper {

	private static ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>();

	public static String get() {
		return THREAD_LOCAL.get();
	}
	
	public static void set(String value) {
		THREAD_LOCAL.set(value);
	}
	
	public static void remove() {
		THREAD_LOCAL.remove();
	}
}
