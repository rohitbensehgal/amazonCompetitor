import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class Reciept {
	
	private Customer customer;
	private Cart cart;
	private Timestamp timeOfPurchase;
	
	public Reciept(Customer customer, Cart cart, Timestamp timeOfPurchase) {
		this.customer = customer;
		this.cart = cart;
		this.timeOfPurchase = timeOfPurchase;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Timestamp getTimeOfPurchase() {
		return timeOfPurchase;
	}
	public void setTimeOfPurchase(Timestamp timeOfPurchase) {
		this.timeOfPurchase = timeOfPurchase;
	}

	public void print() {
		System.out.println("Reciept:");
		System.out.println(customer.getUsername() + "\n");
		displayCart(cart.getItems());
		System.out.println(timeOfPurchase);
		
	}
	private static void displayCart(ArrayList<Item> items) {
		HashMap<Item, ArrayList<Item>> itemMap = new HashMap<>();
		for(Item i : items) {
			if(itemMap.containsKey(i)) {
				itemMap.get(i).add(i);
			}else {
				ArrayList<Item> initList = new ArrayList<Item>();
				initList.add(i);
				itemMap.put(i, initList);
			}
		}
		int total = 0;
		int itemNum = 0;
		for(Item j : itemMap.keySet()) {
			itemNum++;
			int numItems = itemMap.get(j).size();
			System.out.println(numItems + " x " + j.getName() + " - $" + j.getPrice()*numItems);
			total += j.getPrice()*numItems;
		}
		System.out.println("total: $" + total);
	}
}
