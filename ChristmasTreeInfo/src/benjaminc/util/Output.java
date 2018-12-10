package benjaminc.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Output {
	
	FileWriter fileWriter;
    PrintWriter printWriter;
    int num;
    boolean outType[];
    HumanReadable hr;
    
    public Output() {
    	this(true, false);
    }
    public Output(boolean console, boolean log) {
    	hr = new HumanReadable();
    	if(log) {
    		String name = "logs\\log-" + hr.logDate(System.currentTimeMillis());
			try {
				fileWriter = new FileWriter(name + ".txt");
				printWriter = new PrintWriter(fileWriter);
			} catch (IOException e) {
				System.out.println("Failed to make file");
			}
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {  
	                printWriter.close();
	           } 
			});
    	}
		num = -1;
		outType = new boolean[] {console, log};
	}
    
	public void setNum(int n) {
    	num = n;
    }
	public void setOutput(boolean console, boolean log) {
		outType = new boolean[] {console, log};
	}
	
	public void println(Object msg) {
		String out = stackTrace(0, 1);
		if(num > -1) {
			out = num + " " + out;
		}
		if(out.length() < 52) {
			out = out + "\t";
		}
		out = out + "\t";
		if(outType[0] == true) {
			System.out.println(out + msg);
		}
		if(outType[1] == true) {
			printWriter.println(out + msg);
		}
	}
	
	public String stackTrace() {
		return stackTrace(0, 1);
	}
	public String stackTrace(int rel) {
		return stackTrace(rel, 1);
	}
	public String stackTrace(int rel, int step) {
		StackTraceElement loc = Thread.currentThread().getStackTrace()[2 + step];
		return loc.getClassName() + "." + loc.getMethodName() + "(" + loc.getFileName() + ":" + (loc.getLineNumber() + rel) + ")";
	}
}
