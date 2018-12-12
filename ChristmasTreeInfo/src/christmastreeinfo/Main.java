package christmastreeinfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
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
		String benjson = ben.toJson();
		ben = null;
		ben = Customer.getCustomerFromJSON(benjson);
		List<Customer> cal = new ArrayList<Customer>();
		int count = 64;
		cal.add(ben);
		try {
			PersonFactory pf = new PersonFactory(new File("people.txt"));
			for(int i = 0; i < count; i++) {
				cal.add(pf.getCustomer());
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException, using backup generation method");
			for(int i = 0; i < count; i++) {
				cal.add(Customer.getRandomCustomer());
			}
		}
		WaitingRoom.addAll(cal);
		CustomerSelectionWindow csw = new CustomerSelectionWindow();
		//DataInputWindow dw = new DataInputWindow(ben);
				
	}

	@Deprecated
	private List<Customer> oldGeneration(int pplcount) {
		List<Customer> cal = new ArrayList<Customer>();
		Path path = Paths.get("people.txt");
		int lineCount = 0;
		try {
			lineCount = (int) Files.lines(path).count();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int locs[] = new int[pplcount];
		HashMap<Integer, Customer> ppl = new HashMap<Integer, Customer>();
		Random r = new Random();
		while(ppl.size() < pplcount) {
			int spt = r.nextInt(lineCount);
			if(!ppl.containsKey(spt)) {
				ppl.put(spt, null);
			}
		}
		Scanner s;
		try {
			s = new Scanner(new File("people.txt"));
			for(int i = 0; i < lineCount; i++) {
				if(ppl.containsKey(i)) {
					cal.add(Customer.getCustomerFromString(s.nextLine(), ";", DataType.NAME, DataType.PHONE_NUMBER,
							DataType.EMAIL_ADDRESS, DataType.ADDRESS, DataType.CITY));
				}
				if(cal.size() == pplcount) {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException, using backup generation method");
			for(int i = 0; i < 74; i++) {
				cal.add(Customer.getRandomCustomer());
			}
		}
		return cal;
	}
}
