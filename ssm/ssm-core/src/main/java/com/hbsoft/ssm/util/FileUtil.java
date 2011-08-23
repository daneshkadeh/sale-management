package com.hbsoft.ssm.util;

import java.io.IOException;

/**
 * Handle file
 * @author Thanh Hoang
 *
 */
public class FileUtil {
	/**
	 * Run file with suitable program
	 * @param filePath       the path of file
	 */
	public static void openFile(String filePath) {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
