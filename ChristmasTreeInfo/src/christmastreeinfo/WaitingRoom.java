package christmastreeinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import customer.Customer;
import customer.DataType;

public class WaitingRoom {
	
private List<Customer> customers;
	

	public WaitingRoom() {
		customers = new ArrayList<Customer>();
	}
	public WaitingRoom(List<Customer> customers) {
		this.customers = customers;
	}
	/**
	 * Get all customers in the waiting room
	 * 
	 * @return A list of all customers
	 */
	public List<Customer> getAllCustomers() {
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
	public Customer getCustomerByName(String name) {
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
	public Customer getCustomerByUUID(UUID uuid) {
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
	public Customer getCustomerBySpot(int spot) {
		return customers.get(spot);
	}
	/**
	 * Adds a customer to the room
	 * 
	 * @param c The Customer customer to add
	 */
	public void add(Customer c) {
		customers.add(c);
	}
	/**
	 * Adds all customers from C to the room
	 * @param c The List<Customer> of customers to add
	 */
	public void addAll(List<Customer> c) {
		customers.addAll(c);
	}
	
	/**
	 * Gets how many people are in the room
	 * @return Number of people in room
	 */
	public int size() {
		return customers.size();
	}
	
	/**
	 * 
	 * @return The index of the last item on the list
	 */
	public int lastindex() {
		return customers.size() - 1;
	}
	
	/**
	 * Remove all customers from the room if verify = true;
	 * @param verify a Boolean to verify the remove. If false, nothing happens
	 */
	public void clear(boolean verify) {
		if(verify) {
			customers.clear();
		}
	}
	/**
	 * Remove a customer by object
	 * @param c the Customer to remove
	 */
	public void remove(Customer c) {
		customers.remove(c);
	}
	/**
	 * Removes a customer by UUID
	 * @param uuid the UUID of the customer to remove
	 */
	public void remove(UUID uuid) {
		customers.remove(getCustomerByUUID(uuid));
	}

}
