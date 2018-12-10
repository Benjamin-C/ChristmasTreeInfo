package benjaminc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	private PrintStream logPrintStream;
	private PrintStream tracedPrintStream;
	private PrintStream outputPrintStream;
	
	private PrintStream defaultSystemOut;
	private PrintStream defaultSystemErr;
	
	public Logger() {
		defaultSystemOut = System.out;
		defaultSystemErr = System.err;
	}
	
	public void start() {
		DateFormat logPath = new SimpleDateFormat("yyyy/MM/dd");
 		DateFormat logName = new SimpleDateFormat("yyyyMMddHHmmss");
 		Date cal = new Date();
 		String logPathStr = "logs/" + logPath.format(cal);
 		String logNameStr = logName.format(cal) + ".txt";
 		System.out.println("logname =" + logPathStr  + "/" + logNameStr);
 		File f = new File(logPathStr);
 		f.mkdirs();
 		try {
			logPrintStream = new PrintStream(new PrintStream(logPathStr + "/" + logNameStr), true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		outputPrintStream = new PrintStreamDuplicator(logPrintStream, System.out);
 		tracedPrintStream = new TracedPrintStream(outputPrintStream, true);
 		System.setOut(tracedPrintStream);
 		System.setErr(outputPrintStream);
 		System.out.println("Jeopardy");
 		logPrintStream.flush();
	}
	
	public void stop() {
		logPrintStream.close();
		System.setOut(defaultSystemOut);
 		System.setErr(defaultSystemErr);
	}
}
