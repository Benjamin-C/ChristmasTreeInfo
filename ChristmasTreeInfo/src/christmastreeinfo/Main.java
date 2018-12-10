package christmastreeinfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import benjaminc.util.TracedPrintStream;
import customer.Customer;
import customer.DataType;
import customer.Keys;
import windows.CustomerSelectionWindow;

public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		System.setOut(new TracedPrintStream(System.out));
		Customer ben = new Customer("Benjamin Crall"); 
		System.out.println(ben.toString());
		ben.set(DataType.PHONE_NUMBER, "123-456-7890");
		ben.set(DataType.ADDRESS, "1234 South Street Wy NW");
		ben.set(DataType.DATA_LOCKED, Keys.TRUE);
		System.out.println(ben.get(DataType.PHONE_NUMBER));
		System.out.println(ben.get(DataType.ADDRESS));
		List<Customer> cal = new ArrayList<Customer>();
		cal.add(ben);
		Scanner s;
		try {
			s = new Scanner(new File("people.txt"));
			for(int i = 0; i < 7; i++) {
				cal.add(Customer.getCustomerFromString(s.nextLine(), ";", DataType.NAME, DataType.PHONE_NUMBER,
						DataType.EMAIL_ADDRESS, DataType.ADDRESS, DataType.CITY));
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException, using backup generation method");
			for(int i = 0; i < 74; i++) {
				cal.add(Customer.getRandomCustomer());
			}
		}
		WaitingRoom.addAll(cal);
		CustomerSelectionWindow csw = new CustomerSelectionWindow();
		//DataInputWindow dw = new DataInputWindow(ben);
				
	}

}
