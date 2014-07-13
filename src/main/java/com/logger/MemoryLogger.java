package com.logger;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoryLogger {

	static Logger myLogger = Logger.getLogger(MemoryLogger.class.getName());

	String section;

	public String getSection() {
		return section;
	}

	public void log(Level level, String msg) {
		MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = mbean.getHeapMemoryUsage();
		long used = heap.getUsed();
		myLogger.log(level, msg + " section:{" + section + "}, used:{" + used
				+ "}");
	}

	public void setSection(String section) {
		this.section = section;
	}

}
