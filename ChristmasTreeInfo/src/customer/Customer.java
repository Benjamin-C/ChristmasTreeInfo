package customer;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Customer {

	private HashMap<DataType, Object> data;
	
	public Customer(DataPoint... dp) {
		this(new HashMap<DataType, Object>());
		for(DataPoint d : dp) {
			d.addTo(data);
		}
	}
	public Customer(HashMap<DataType, Object> h) {
		data = h;
		UUID me = UUID.randomUUID();
		data.put(DataType.UUID, me);
	}
	public Customer(String name) {
		this();
		data.put(DataType.NAME, name);
	}
	public String get(DataType k) {
		if(data.containsKey(k)) {
			return data.get(k).toString();
//			if(data.get(k) instanceof String) {
//				return (String) data.get(k);
//			}
		}
		return "";
	}
	
	public Object getraw(DataType k) {
		return data.get(k);
	}
	
	public void setraw(DataType k, Object o) {
		data.put(k, o);
	}
	
	public void set(DataType k, Object o) {
		if(o instanceof String) {
			data.put(k, SaveDataConverter.getFromString((String) o, k));
		} else {
			setraw(k, o);
		}
	}
	public boolean exists(DataType k) {
		return data.containsKey(k);
	}
	public String toString() {
		String dataString = "";
		if(exists(DataType.NAME)) {
			dataString += get(DataType.NAME);
		}
		return "Customer[" + dataString + "]";
	}
	
	public static Customer getRandomCustomer() {
		HashMap<DataType, Object> npd = new HashMap<DataType, Object>();
		Random r = new Random();
		npd.put(DataType.NAME, randomString(2, 6).toLowerCase() + " " + randomString(2, 6).toLowerCase());
		npd.put(DataType.PHONE_NUMBER, (r.nextInt(899) + 100) + "-" + (r.nextInt(899) + 100) + "-" + (r.nextInt(8999) + 1000));
		npd.put(DataType.ADDRESS, (r.nextInt(899) + 100) + " " + randomString(4, 12) + " St.");
		npd.put(DataType.CITY, randomString(4, 12) + ", " + randomString(4, 12));
		npd.put(DataType.EMAIL_ADDRESS, randomString(4, 12) + "@" + randomString(4, 12) + ".com");
		return new Customer(npd);
	}
	
	private static String randomString(int min, int max) {
		String out = "";
		Random r = new Random();
		int legnth = r.nextInt(max - min) + min;
		for(int i = 0; i < legnth; i++) {
			if(r.nextBoolean()) {
				out = out + (char) (65 + r.nextInt(90-65));
			} else {
				out = out + (char) (97 + r.nextInt(122-97));
			}
		}
		return out;
	}
	
	public static Customer getCustomerFromString(String data, String delim, DataType... dataTypes) {
		String partsp[] = data.split(delim);
		Customer out = new Customer();
		for(int i = 0; i < Math.min(partsp.length, dataTypes.length); i++) {
			out.set(dataTypes[i], SaveDataConverter.getFromString(partsp[i], dataTypes[i]));
		};
		return out;
	}
}
