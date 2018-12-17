package christmastreeinfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import customer.Customer;
import customer.DataType;

public class RandomPersonFactory {
	
	private List<String> name1;
	private List<String> name2;
	private List<String> phone;
	private List<String> email;
	private List<String> address;
	private List<String> city;
	private Scanner scan;
	private int filesize;
	
	public RandomPersonFactory(File f) throws FileNotFoundException {
		Path path = Paths.get(f.getPath());
		int lineCount = 0;
		try {
			lineCount = (int) Files.lines(path).count();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		filesize = lineCount;
		
		name1 = new ArrayList<String>();
		name2 = new ArrayList<String>();
		phone = new ArrayList<String>();
		email = new ArrayList<String>();
		address = new ArrayList<String>();
		city = new ArrayList<String>();
		
		scan = new Scanner(f);
		for(int i = 0; i < filesize; i++) {
			String part[] = scan.nextLine().split(";");
			switch(part.length) {
			case 5: city.add(part[4]);
			case 4: address.add(part[3]);
			case 3: email.add(part[2]);
			case 2: phone.add(part[1]);
			case 1: {String name[] = part[0].split(" ");
				name1.add(name[0]);
				if(name.length >= 2) {
					name2.add(name[1]);
				}
			}
			}
		}
		scan.close();
	}
	
	public Customer getCustomer() {
		Customer out = new Customer();
		Random r = new Random();
		out.set(DataType.NAME, name1.get(r.nextInt(name1.size())) + " " + name2.get(r.nextInt(name2.size())));
		out.set(DataType.PHONE_NUMBER, phone.get(r.nextInt(phone.size())));
		out.set(DataType.EMAIL_ADDRESS, email.get(r.nextInt(email.size())));
		out.set(DataType.ADDRESS, address.get(r.nextInt(address.size())));
		out.set(DataType.CITY, city.get(r.nextInt(city.size())));
		return out;
	}

}
