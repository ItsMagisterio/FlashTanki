package flashtanki.server.logger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	
	public static String INFO = "INFO";
	public static String ERROR = "ERROR";
	public static String WARNING = "WARNING";
	
    public static void log(String type, String log) {
        System.out.println("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]" + " " + "[" + new Throwable().getStackTrace()[1].getClassName() + "]" + " " + "[" + type + "]" + " " + log);
    }
}
