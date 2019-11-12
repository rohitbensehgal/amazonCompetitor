import java.util.ArrayList;

public class Cart {
	
	private ArrayList<Item> items;
	private int numItems;
	
	Cart(ArrayList<Item> items){
		this.items = items;
		this.numItems = items.size();
	}
	
	public Cart() {
		this.items = new ArrayList();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public int getNumItems() {
		return items.size();
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	public void removeItem(Item item) {
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).equals(item)) {
				items.remove(i);
				break;
			}
		}
	}
}
