package benjaminc.util;

public class BenString {
	public static boolean isNumeric(String str)  { // Find out if a string only contains numbers and spaces
		try  
		{  
			double d = Double.parseDouble(str);
			d = d + 1;
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		if(str.isEmpty()) {
			return false;
		}
	return true;  
	}
}
