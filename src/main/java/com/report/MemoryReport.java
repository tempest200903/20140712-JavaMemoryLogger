package com.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class MemoryReport {

	public static void main(String[] args) throws IOException {
		new MemoryReport().makeReport();
	}

	File reportOutputDirectory = new File("target/site/memoryreport");

	File templateDirectory = new File("src/main/resources");

	void createReportFile(List<Double> memoryData) throws IOException {
		createReportFileHtml(memoryData);
		createReportFileJs();
	}

	void createReportFileHtml(List<Double> memoryData) throws IOException {
		StringBuilder replacement = new StringBuilder(
				"var dataset = [ 20, 40, 10, 30, 50 ];");
		StringUtils.join;

		File templateFile = new File(templateDirectory, "memoryreport.html");
		File outputFile = new File(reportOutputDirectory, "memoryreport.html");
		List<String> inputLines = FileUtils.readLines(templateFile);
		List<String> outputLines = new ArrayList<String>();

		String regex = ".*(var dataset = \\[ 1, 2, 3, 4, 5 \\];).*";
		Pattern p = Pattern.compile(regex);
		for (String line : inputLines) {
			Matcher m = p.matcher(line);
			line = m.replaceFirst(replacement.toString());
			outputLines.add(line);
		}
		FileUtils.writeLines(outputFile, outputLines);
	}

	void createReportFileJs() throws IOException {
		File srcDir = new File(templateDirectory, "d3");
		File destDir = new File(reportOutputDirectory, "d3");
		FileUtils.copyDirectory(srcDir, destDir);
	}

	List<Double> extractMemoryData() throws FileNotFoundException, IOException {
		List<Double> memoryData = new ArrayList<Double>();

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
					String value = m.group(1);
					// System.out.println(value);
					memoryData.add(Double.valueOf(value));
				}
			}

		} finally {
			bufferedReader.close();
		}
		return memoryData;
	}

	void makeReport() throws IOException {
		setupOutputDirectory();
		List<Double> memoryData = extractMemoryData();
		createReportFile(memoryData);
	}

	void regexSample() throws IOException {
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

	void setupOutputDirectory() throws IOException {
		FileUtils.forceMkdir(reportOutputDirectory);
	}

}
