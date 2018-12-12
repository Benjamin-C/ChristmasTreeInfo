package christmastreeinfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import customer.Customer;
import customer.DataType;

public class WaitingRoom {

	private static List<Customer> customers = new ArrayList<Customer>();
	
	/**
	 * Get all customers in the waiting room
	 * 
	 * @return A list of all customers
	 */
	public static List<Customer> getAllCustomers() {
		return customers;
	}
	/**
	 * Returns a customer with a given name.
	 * If more than one exist, only one will be returned.
	 * If none exist, null will be returned
	 * 
	 * @param name The String name of the customer
	 * @return The customer with that name
	 */
	public static Customer getCustomerByName(String name) {
		for(Customer c : customers) {
			if(c.get(DataType.NAME).equals(name)) {
				return c;
			}
		}
		return null;
	}
	/**
	 * Returns a customer with a given uuid.
	 * If more than one exist, only one will be returned.
	 * It is unlikley that multiple will exist
	 * If none exist, null will be returned
	 * 
	 * @param name The UUID uuid of the customer
	 * @return The customer with that uuid
	 */
	public static Customer getCustomerByUUID(UUID uuid) {
		for(Customer c : customers) {
			if(c.getraw(DataType.UUID).equals(uuid)) {
				return c;
			}
		}
		return null;
	}
	/**
	 * Returns a customer at a specific location.
	 * This is not a reliable way to get a specific customer, for that use UUID.
	 * 
	 * @param spot The int location of the customer
	 * @return The customer
	 */
	public static Customer getCustomerBySpot(int spot) {
		return customers.get(spot);
	}
	/**
	 * Adds a customer to the room
	 * 
	 * @param c The Customer customer to add
	 */
	public static void add(Customer c) {
		customers.add(c);
	}
	/**
	 * Adds all customers from C to the room
	 * @param c The List<Customer> of customers to add
	 */
	public static void addAll(List<Customer> c) {
		customers.addAll(c);
	}
	
	/**
	 * Gets how many people are in the room
	 * @return Number of people in room
	 */
	public static int size() {
		return customers.size();
	}
	
	/**
	 * 
	 * @return The index of the last item on the list
	 */
	public static int lastindex() {
		return customers.size() - 1;
	}
	
	/**
	 * Remove all customers from the room if verify = true;
	 * @param verify a Boolean to verify the remove. If false, nothing happens
	 */
	public static void clear(boolean verify) {
		if(verify) {
			customers.clear();
		}
	}
	/**
	 * Remove a customer by object
	 * @param c the Customer to remove
	 */
	public static void remove(Customer c) {
		customers.remove(c);
	}
	/**
	 * Removes a customer by UUID
	 * @param uuid the UUID of the customer to remove
	 */
	public static void remove(UUID uuid) {
		customers.remove(getCustomerByUUID(uuid));
	}
	/**
	 * Saves all customers to a file
	 * @param f the File to save customers to
	 * @throws IOException if there is an IO error
	 */
	public static void saveCustomersToFile(File f) throws IOException {
		FileWriter fw = new FileWriter(f);
		fw.write(Keys.DATA_VALIDATION_KEY + "\n");
		for(Customer c : customers) {
			fw.write(c.toJson() + "\n");
		}
		fw.flush();
		fw.close();
	}
	/**
	 * Adds all people from a file to the WaitingRoom
	 * @param f the File to get people from
	 * @throws FileNotFoundException if the file does not exist or is not a CustomerData file
	 */
	public static void addCustomersFromFile(File f) throws FileNotFoundException {
		Scanner s = new Scanner(f);
		if(s.nextLine().equals(Keys.DATA_VALIDATION_KEY)) {
			while(s.hasNextLine()) {
				add(Customer.getCustomerFromJSON(s.nextLine()));
			}
		} else {
			s.close();
			// This will happen if the data validation key is missing
			throw new FileNotFoundException("The provided file is not a customer save file");
		}
		s.close();
	}
}
