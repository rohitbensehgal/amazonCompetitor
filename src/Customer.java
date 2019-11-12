import java.util.ArrayList;

public class Customer {
	
	private String username;
	private String cardNumber;
	private Cart cart;
	private ArrayList<Reciept> reciepts;
	
	Customer(String username, String cardNumber, Cart cart){
		this.username = username;
		this.cardNumber = cardNumber;
		this.cart = cart;
		reciepts = new ArrayList<Reciept>();
	}
	
	public ArrayList<Reciept> getReciepts() {
		return reciepts;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
}
