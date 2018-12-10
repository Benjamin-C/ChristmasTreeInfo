package benjaminc.util;

import java.util.Calendar;

public class HumanReadable {

	public HumanReadable() {
		super();
	}
	
	public String date(long epochMillis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());

		String date = c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH);
		String time = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
		return date + " " + time;
	}
	
	public String logDate(long epochMillis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());

		String date = c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH);
		String time = c.get(Calendar.HOUR_OF_DAY)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.SECOND);
		return date + "+" + time;
	}

}
