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
		
		File customerFile = new File(Keys.DATA_FILE);

		try {
			Lobby.addCustomersFromFile(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CustomerSelectionWindow csw = new CustomerSelectionWindow();
		//DataInputWindow dw = new DataInputWindow(ben);
				
	}

	@SuppressWarnings("unused")
	private static void generateCustomers(List<Customer> cal, int count) {
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
		Lobby.addAll(cal);
		File customerFile = new File(Keys.DATA_FILE);
		try {
			Lobby.saveCustomersToFile(customerFile);
			Lobby.clear(true);
			Lobby.addCustomersFromFile(customerFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
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
	
	public static void exit() {
		exit(0);
	}
	public static void exit(int status) {
		try {
			Lobby.saveCustomersToFile(new File(Keys.DATA_FILE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(status);
	}
}
