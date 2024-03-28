package de.grnx;

import java.util.Arrays;

public class Log {
	public static void e(String ... str) {
		System.out.println(Arrays.toString(str));
	}

	public static void e(String logTag, String string, Exception e) {
	System.out.println(logTag + string + e.getMessage());
		
	}

	public static void d(String logTag, String string) {
			System.out.println(logTag+string);
		
	}
}
