package customer;

import java.util.UUID;

public class SaveDataConverter {

	/**
	 * Gets an object from a string with a string type
	 * 
	 * @param data The String representation of object
	 * @param dt The String representation of DataType. Must be exact
	 * @return The object, null if not found
	 */
	public static DataType getDataTypeFromString(String dt) {
		for(DataType d : DataType.values()) {
			if(dt.equals(d.toString())) {
				return d;
			}
		}
		return null;
	}
	public static Object getFromString(String data, DataType type) {
		switch(type) {
		case UUID: return UUID.fromString(data);

		default: return data;
		}
	}
}
