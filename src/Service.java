import java.util.ArrayList;
import java.util.HashMap;

public class Service {
	
	private Warehouse warehouse;
	private ArrayList<Customer> customers;
	private HashMap<String, String> usernamesAndPasswords;
	
	
	public static void main(String[] args) {
		
	}
	
	private ArrayList<Item> createRandItems(){
		ArrayList<Item> randItems = new ArrayList();
		for(int i = 0; i < 100; i++) {
			int price = (int)(Math.random() * 1000) + 1;
			int numItems = (int)(Math.random() * 150) + 1;
			boolean isActive = numItems > 125;
			String name = "item-" + i;
			String description = "some description for" + name;
			randItems.add(new Item(price, numItems, isActive, name, description));
		}
		return randItems;
	}
}
