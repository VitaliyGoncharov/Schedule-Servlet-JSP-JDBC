package com.vit.Schedule.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.vit.Schedule.servlet.MySQLFiller;

public class FileUtils {
	public static byte[] getByteArray(String filename) {
		FileInputStream fileInputStream = null;
		File file = new File(filename);
		byte[] bFile = new byte[(int) file.length()];
		
		try {	
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bFile;
	}
	
	public static String getString(String filename) {
		byte[] bFile = getByteArray(filename);
		
		try {
			return new String(bFile, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
