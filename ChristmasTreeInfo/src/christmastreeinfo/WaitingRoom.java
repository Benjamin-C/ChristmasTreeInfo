package christmastreeinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
}
