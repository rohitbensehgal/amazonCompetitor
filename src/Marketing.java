import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



public class Marketing {
	ArrayList<Item> items;
	Item item;
	int total;
	boolean flag;
	int checker=0;
	int once=0;
	int disc=0;
	public Marketing(ArrayList<Item> inventory) 
	{
		this.items = inventory; 
	}
	public Marketing(Item item) 
	{
		this.item = item; 
	}
	public Marketing( int total, boolean flag) 
	{
		this.total = total;
		this.flag = flag;
	}
	public void sale_of_the_week() 
	{
		Random rand = new Random();
		int item =rand.nextInt(items.size()-1);
		int price = items.get(item).getPrice();
		String name = items.get(item).getName();
		if(price > 40) {
			System.out.println("**********************************************");
			System.out.println("~WEEKLY SALE ITEM~: "+ name);
			System.out.println("The original price of "+name+" is: "+ price);
			price = price -10;
			items.get(item).setPrice(price);
			System.out.println("For today, the price "+name +" is: " + price);
			System.out.println("**********************************************");
		}
		else {
			this.sale_of_the_week();
		}
	}

	public void discount() 
	{
		int price = item.getPrice();
		if (price > 80 && disc==0) 
		{
			disc=1;
			System.out.println("Congrats, today you will get a $5 discount on "+ item.getName());
			price = price-5;
			item.setPrice(price);
			System.out.println("The price is now: "+ price);
		}

	}

	public boolean getPromoCode() {
		boolean check = false;
		if(total >=100 && total <= 200) {
			System.out.println("Use promo code \"get20\" to get another $20 discount!");
			check = true;
		}
		if(total >=200 && total <= 300) {
			System.out.println("Use promo code \"get50\" to get another $50 discount!");
			check = true;
		}
		if(total >=300 && total <= 400) {
			System.out.println("Use promo code \"get80\" to get another $80 discount!");
			check = true;
		}
		if(total >=400 ) {
			System.out.println("Use promo code \"get100\" to get another $100 discount!");
			check = true;
		}
		return check;
	}

	public ArrayList<Item> VirtualChat() {
		
		String input;
		int found=0;
		
		ArrayList<Item> addToCart = new ArrayList<Item>();
		Scanner s= new Scanner(System.in);
		if(once==0) {
			once=1;
			System.out.println("I am Virtual Chat Box, here to assist to find your desired Item.");
		}
		while (checker==0) {
			System.out.println("Press \"exit\" to go back to the home screen.");
			System.out.println("SEARCH:");
			input = s.nextLine();
			if (input.equals("exit")) {
				return addToCart;
			}
			for(Item i : items) 
			{
				if (i.getName().equals(input)) {
					found=1;
					System.out.println("We have "+ input+ " in our Store.");
					System.out.println("would you like to add this to your cart? (y/n)");
					input = s.nextLine();
					if(input.equals("y")) {
						addToCart.add(i);
					}
					System.out.println("would you like to continue shopping? (y/n)");
					input = s.nextLine();
					if(input.equals("n")) 
					{
						checker=1;
						break;
					}
				}
			}
			if(found==0) {
				System.out.println("Sorry, We don't have "+input+ " currently available.");	
				System.out.println("would you like to continue shopping? (y/n)");
				input = s.nextLine();
				if(input.equals("n")) 
				{
					checker=1;
					break;
				}
			}
			found=0;
		}
		return addToCart;
	}
}
