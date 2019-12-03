import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class Reciept {

	private Customer customer;
	private Cart cart;
	private Timestamp timeOfPurchase;
	private int total;
	private int discount;
	private double num;  //total money of items
	private PostalService postalService;
	
	public Reciept(Customer customer, Cart cart, Timestamp timeOfPurchase) {
		this.customer = customer;
		this.cart = cart;
		this.timeOfPurchase = timeOfPurchase;
	}
	public Reciept() {
		// TODO Auto-generated constructor stub
	}
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
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
		
		System.out.println("Discount: $" + this.discount);
		System.out.println("Total: $" + this.total);
		System.out.println(timeOfPurchase);
		
	}
	private void displayCart(ArrayList<Item> items) {
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
		// change total form int to double
		double total = 0;
		int itemNum = 0;
		for(Item j : itemMap.keySet()) {
			itemNum++;
			int numItems = itemMap.get(j).size();
			System.out.println(numItems + " x " + j.getName() + " - $" + j.getPrice()*numItems);
			total += j.getPrice()*numItems;
			num = total;
		}
		//System.out.println("total: $" + total);
	}
	// return total money of items
	public double checkTotal()
	{
		return num;
	}
	// this class replace the above displayCart class to show the total money(postal fee added)
	private void displayCart1()
	{
		double num1 = num+postalService.PostalFee();
		System.out.println("total: $" + num1);
	}
}
