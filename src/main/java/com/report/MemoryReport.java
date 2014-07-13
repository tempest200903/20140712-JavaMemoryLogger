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

import com.google.common.primitives.Longs;

public class MemoryReport {

	public static void main(String[] args) throws IOException {
		MemoryReport memoryReport = new MemoryReport();
		memoryReport.makeReport();
		System.out.println("reportOutputDirectory is "
				+ memoryReport.reportOutputDirectory);

	}

	File reportOutputDirectory = new File("target/site/memoryreport");

	File templateDirectory = new File("src/main/resources");

	void createReportFile(List<MemoryData> memoryDataList) throws IOException {
		createReportFileHtml(memoryDataList);
		createReportFileJs();
	}

	void createReportFileHtml(List<MemoryData> memoryDataList)
			throws IOException {
		StringBuilder replacement1 = new StringBuilder("var dataset = [ ");
		for (MemoryData memoryData : memoryDataList) {
			replacement1.append(memoryData.getUsed());
			replacement1.append(", ");
		}
		replacement1.append("0 ];");

		String regex1 = ".*(var dataset = \\[ 1, 2, 3, 4, 5 \\];).*";
		Pattern p1 = Pattern.compile(regex1);

		File templateFile = new File(templateDirectory, "memoryreport.html");
		File outputFile = new File(reportOutputDirectory, "memoryreport.html");
		List<String> inputLines = FileUtils.readLines(templateFile);
		List<String> outputLines = new ArrayList<String>();

		for (String line : inputLines) {
			line = p1.matcher(line).replaceFirst(replacement1.toString());
			outputLines.add(line);
		}
		FileUtils.writeLines(outputFile, outputLines);
	}

	void createReportFileJs() throws IOException {
		File srcDir = new File(templateDirectory, "d3");
		File destDir = new File(reportOutputDirectory, "d3");
		FileUtils.copyDirectory(srcDir, destDir);
	}

	List<MemoryData> extractMemoryDataList() throws FileNotFoundException,
			IOException {
		List<MemoryData> memoryDataList = new ArrayList<MemoryData>();

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
					MemoryData memoryData = new MemoryData();
					memoryData.setUsed(Long.valueOf(value));
					memoryDataList.add(memoryData);
				}
			}

		} finally {
			bufferedReader.close();
		}
		return memoryDataList;
	}

	void makeReport() throws IOException {
		setupOutputDirectory();
		List<MemoryData> memoryDataList = extractMemoryDataList();
		createReportFile(memoryDataList);
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

	long[] toPrimitiveLongArray(List<Long> list) {
		return Longs.toArray(list);
	}

}
