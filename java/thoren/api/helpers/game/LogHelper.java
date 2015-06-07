package thoren.api.helpers.game;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelper 
{
	private static Logger logger;

	/**
	 * Logs a message of Level "INFO" under log name "ThorCore".
	 * @param message String that will be output to the console.
	 */
	public static void info(String message)
	{
		logger = LogManager.getLogger("ThorCore");
		logger.log(Level.INFO, message);
	}
	
	/**
	 * Logs a message of Level "INFO" under the specified log name (loggerName).
	 * @param loggerName String that becomes the log name.
	 * @param message String that will be output to the console.
	 */
	public static void info(String loggerName, String message)
	{
		logger = LogManager.getLogger(loggerName);
		logger.log(Level.INFO, message);
	}
	
	/**
	 * Logs a message of Level "WARN" under log name "ThorCore".
	 * @param message String that will be output to the console.
	 */
	public static void warning(String message)
	{
		logger = LogManager.getLogger("ThorCore");
		logger.log(Level.WARN, message);
	}
	
	/**
	 * Logs a message of Level "WARN" under the specified log name (loggerName).
	 * @param loggerName String that becomes the log name.
	 * @param message String that will be output to the console.
	 */
	public static void warning(String loggerName, String message)
	{
		logger = LogManager.getLogger(loggerName);
		logger.log(Level.WARN, message);
	}
	
	/**
	 * Logs a message of Level "ERROR" under log name "ThorCore".
	 * @param message String that will be output to the console.
	 */
	public static void severe(String message)
	{
		logger = LogManager.getLogger("ThorCore");
		logger.log(Level.ERROR, message);
	}
	
	/**
	 * Logs a message of Level "ERROR" under the specified log name (loggerName).
	 * @param loggerName String that becomes the log name.
	 * @param message String that will be output to the console.
	 */
	public static void severe(String loggerName, String message)
	{
		logger = LogManager.getLogger(loggerName);
		logger.log(Level.ERROR, message);
	}
}