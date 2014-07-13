package com.logger;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoryLogger {

	static Logger myLogger = Logger.getLogger(MemoryLogger.class.getName());

	public void log(Level level, String msg) {
		MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = mbean.getHeapMemoryUsage();
		long used = heap.getUsed();
		myLogger.log(level, msg + " used:{" + used + "}");
	}

}
