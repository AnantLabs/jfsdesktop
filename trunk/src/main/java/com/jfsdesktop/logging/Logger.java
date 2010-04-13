package com.jfsdesktop.logging;
 

public class Logger 
{
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Logger.class);

	
	public static void debug(String msg)
	{
		logger.debug(msg);
	}

	public static void trace(String msg) {
		logger.trace(msg);		
	}

	public static void warn(String msg) {
		logger.warn(msg);	
	}
	
	public static void error(String msg) {
		logger.error(msg);
	}
	
	public static void error(Throwable t) {
		logger.error(t);
	}
	
}
