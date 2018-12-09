package customer;

import java.util.HashMap;

public class DataPoint {

	private DataType key;
	private Object value;
	
	public DataPoint(DataType key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public DataType getKey() {
		return key;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void addTo(HashMap<DataType, Object> map) {
		map.put(key, value);
	}
}
