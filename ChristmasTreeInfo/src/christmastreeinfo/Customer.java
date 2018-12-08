package christmastreeinfo;

import java.util.Date;
import java.util.HashMap;

public class Customer {

	private HashMap<DataType, Object> data;
	
	public Customer() {
		data = new HashMap<DataType, Object>();
	}
	public Customer(HashMap<DataType, Object> h) {
		data = h;
	}
	public Customer(String name) {
		this();
		data.put(DataType.NAME, name);
	}
	public String get(DataType k) {
		if(data.containsKey(k)) {
			if(data.get(k) instanceof String) {
				return (String) data.get(k);
			}
		}
		return null;
	}
	
	public Object getraw(DataType k) {
		return data.get(k);
	}
	
	public void set(DataType k, Object o) {
		data.put(k, o);
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
}
