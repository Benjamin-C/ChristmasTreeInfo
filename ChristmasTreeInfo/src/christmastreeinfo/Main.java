package christmastreeinfo;

public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		Customer ben = new Customer("Benjamin Crall"); 
		System.out.println(ben.toString());
		ben.set(DataType.PHONE_NUMBER, "123-456-7890");
		System.out.println(ben.get(DataType.PHONE_NUMBER));
		ben.set(DataType.ADDRESS, "1234 South Street Wy NW");
		System.out.println(ben.get(DataType.ADDRESS));
		
		DataInputWindow dw = new DataInputWindow(ben);
				
	}

}
