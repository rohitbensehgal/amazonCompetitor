import java.util.ArrayList;
import java.util.HashMap;

public class Item {

	private int price;
	private int numItems;
	private boolean isActive;
	private String name;
	private String description;
	private String image;
	private ArrayList<Review> reviews;
	
	Item(int price, int numItems, boolean activeStatus, String name, String description){
		this.price = price;
		this.numItems = numItems;
		this.isActive = activeStatus;
		this.name = name;
		this.description = description;
		this.reviews = new ArrayList<Review>();
	}
	public Item() {
		// TODO Auto-generated constructor stub
		reviews = new ArrayList<Review>();
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
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public boolean equals(Object item) {
		if(this.toDBString().equals(((Item) item).toDBString())) {
			return true;
		}
		return false;
	}
	
	public String toDBString() {
		String active = "0";
		String concatReviews = ",";
		if(this.isActive) {
			active = "1";
		}
		if(reviews.size() > 0) {
			for(Review r : reviews) {
				concatReviews = concatReviews.concat(r.toDBString() + ";");
			}
			concatReviews = concatReviews.substring(0, concatReviews.length() - 1);
		}else {
			concatReviews = "";
		}
		if(this.image != null) {
			return this.price + "," + this.numItems + "," + active + "," + this.name + "," + this.description + "," + concatReviews + "," + this.image;
		}
		return this.price + "," + this.numItems + "," + active + "," + this.name + "," + this.description + concatReviews;
	}
}
