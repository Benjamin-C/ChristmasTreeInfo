package windows;

public class InfoWindowButton {
	
	private Runnable run;
	private String text;
	private boolean close;
	
	public InfoWindowButton(String text, Runnable run, boolean close) {
		super();
		this.run = run;
		this.text = text;
		this.close = close;
	}
	public InfoWindowButton(String text, Runnable run) {
		this(text, run, false);
	}
	public boolean hasRun() {
		return run != null;
	}
	public Runnable getRun() {
		return run;
	}
	public String getText() {
		return text;
	}
	public boolean isClose() {
		return close;
	}
}
