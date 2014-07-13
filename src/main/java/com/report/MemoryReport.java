package com.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryReport {

	public static void main(String[] args) throws IOException {
		File logFile = new File("logs/java0.log");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				logFile));
		try {
			String regex = ".*used:\\{(\\d+)\\}.*";
			Pattern p = Pattern.compile(regex);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				Matcher m = p.matcher(line);
				if (m.matches()) {
					System.out.println(m.group(1));
				}
			}

		} finally {
			bufferedReader.close();
		}
	}

	// src/main/resources/memoryreport.html

}
