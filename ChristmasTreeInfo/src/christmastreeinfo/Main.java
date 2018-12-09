package christmastreeinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		Customer ben = new Customer("Benjamin Crall"); 
		System.out.println(ben.toString());
		ben.set(DataType.PHONE_NUMBER, "123-456-7890");
		ben.set(DataType.ADDRESS, "1234 South Street Wy NW");
		System.out.println(ben.get(DataType.PHONE_NUMBER));
		System.out.println(ben.get(DataType.ADDRESS));
		List<Customer> cal = new ArrayList<Customer>();
		cal.add(ben);
		for(int i = 0; i < 74; i++) {
			cal.add(Customer.getRandomCustomer());
		}
		WaitingRoom.addAll(cal);
		CustomerSelectionWindow csw = new CustomerSelectionWindow();
		//DataInputWindow dw = new DataInputWindow(ben);
				
	}

}
