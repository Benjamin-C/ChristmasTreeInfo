package christmastreeinfo;

import java.util.UUID;

public class SaveDataConverter {

	public static Object getFromString(String data, DataType type) {
		switch(type) {
		case UUID: return UUID.fromString(data);

		default: return data;
		}
	}
}
