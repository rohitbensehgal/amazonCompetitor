import java.util.ArrayList;

public class SalesPerson{
	
	private String username;
	private ArrayList<Item> items;
	private int numItems;
	
	SalesPerson(){
		items = new ArrayList<Item>();
	}
	
	public int getNumItems() {
		return items.size();
	}
	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
}
