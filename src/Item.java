import java.util.ArrayList;
import java.util.HashMap;

public class Item {

	private int price;
	private int numItems;
	private boolean isActive;
	private String name;
	private String description;
	private ArrayList<Review> reviews;
	
	Item(int price, int numItems, boolean activeStatus, String name, String description){
		this.price = price;
		this.numItems = numItems;
		this.isActive = activeStatus;
		this.name = name;
		this.description = description;
		this.reviews = new ArrayList<Review>();
	}
	public ArrayList<Review> getReviews(){
		return reviews;
	}
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	public int getNumItems() {
		return numItems;
	}
	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}
	public boolean isActive() {
		return isActive;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.isActive = activeStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object item) {
		if(item.getClass() != this.getClass()) {
			return false;
		}
		if (this.isActive == ((Item) item).isActive() && this.description.equals(((Item) item).getDescription()) && this.name.equals(((Item) item).getName()) && this.price == ((Item) item).getPrice()) {
			return true;
		}
		return false;
	}
}
