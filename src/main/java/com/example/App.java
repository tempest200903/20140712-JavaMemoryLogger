package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.logger.MemoryLogger;

/**
 * Hello world!
 * 
 */
public class App {

	static Logger myLogger = Logger.getLogger(App.class.getName());

	static MemoryLogger memoryLogger = new MemoryLogger();

	static void consumeMemory() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 1000000; i++) {
			list.add(App.class.getName());
		}
	}

	public static void main(String[] args) {
		myLogger.info("begin");
		for (int i = 0; i < 10; i++) {
			memoryLogger.setSection("section" + (i / 3));
			memoryLogger.log(Level.INFO, String.valueOf(i));
			consumeMemory();
		}
		myLogger.info("end");
	}

}
