package com.vvsoo.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;

import lombok.Cleanup;

public class FileManager {
	/**
	 * readVersion
	 * @param file
	 * @return
	 */
	public static String readVersion(File file) {
		String version = "";
		try {
			@Cleanup FileReader fr = new FileReader(file);
			@Cleanup BufferedReader br = new BufferedReader(fr);
			version = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return version;
	}
}
