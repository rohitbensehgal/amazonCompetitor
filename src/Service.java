import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Service {
	
	private static Warehouse warehouse;
	private static ArrayList<Customer> customers;
	private static HashMap<String, String> userAndPasswords;
	private static HashMap<String, Integer> tasks;	// taskName, workerRequirement
	private static HashMap<String, ArrayList<Integer>> workerHoursAndSalary;
	private static int flag = 0;
	private static void init(){
		warehouse = new Warehouse(createRandItems(), createWarehouseWorkers());
		userAndPasswords = new HashMap<String, String>();
		tasks = new HashMap<String, Integer>();
		workerHoursAndSalary = new HashMap<String, ArrayList<Integer>>();
		userAndPasswords.put("cust1", "cust1");
		userAndPasswords.put("cust2", "cust2");
		userAndPasswords.put("work1", "work1");
		userAndPasswords.put("work2", "work2");
		tasks.put("Stock", 5);
		tasks.put("Warehouse", 10);
		tasks.put("Check Expired items", 4);
		tasks.put("Shipping", 10);
	}
	
	public static void main(String[] args) throws Exception {
		// custUsernamesAndPasswords = new HashMap<String, String>();
		// workUsernamesAndPasswords = new HashMap<String, HashMap<String, String>>();
		init();
		home();
	}
	//Placeholder methods that need to be filled out to work
	
	public static void signup() throws Exception {
		//some sign up function
		Scanner sc = new Scanner(System.in);
		System.out.println("Please type the corresponding number for your account type:");
		System.out.println("(1) Customer");
		System.out.println("(2) Worker");
		int input2 = sc.nextInt();
		boolean isWorker = false;
		if(input2 == 1) {
			System.out.println("::CUSTOMER SIGNUP::");
		} else if(input2 == 2) {
			System.out.println("::WORKER SIGNUP::");
			isWorker = true;
		}
		else {
			throw new Exception("Bad User Input");
		}		
		System.out.println("Enter username:");
		String username = sc.next();
		System.out.println("Enter password:");
		String password = sc.next();

		if(userAndPasswords.containsKey(username) == false) {
			userAndPasswords.put(username, password);
		} else {
			System.out.println("This username already exists, try using another username");
			signup();
		}
		home();
	}
	
	//workers usernames & passwords will be randomly created & stored as filler info
	//as long as a worker is logged in they must always return here

	private static String loginHelper() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter username:");
		String username = sc.next();
		int input;
		if(userAndPasswords.containsKey(username) == false) {
			System.out.println("Username doesn't exist, try again or sign up");
			System.out.println("(1) Try again");
			System.out.println("(2) Sign Up");
			input = sc.nextInt();
			if(input == 1) {
				loginHelper();
			}else if(input == 2) {
				signup();
			}else {
				throw new Exception("Bad User Input");
			}
		}
		String check = userAndPasswords.get(username);
		while(true) {
			System.out.println("Enter password:");
			String password = sc.next();			
			if(check.equals(password)) {
				System.out.println("Welcome Back!");
				break;
			} else {
				System.out.println("Wrong password!");
				System.out.println("(1) Try with different username");
				System.out.println("(2) Retry password");
				input = sc.nextInt();
				if(input == 1) {
					loginHelper();
				}
			}
		}
		return username;
	}

	private static Worker workerLogin() throws Exception {
		System.out.println(":: WORKER LOGIN ::");
		String username = loginHelper();
		// TODO:: create random hours, salary
		Random rand = new Random();
		int hours = rand.nextInt(12)+1;
		int salary = 0;
		if(hours>8) {
			salary = rand.nextInt(800)+2000;
		}
		else {
			salary = 2000;
		}
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(hours);
		list.add(salary);
		workerHoursAndSalary.put(username, list);
		return new Worker(username, hours, salary);
	}
	private static Customer customerLogin() throws Exception{
		//just a placeholder to look like login page
		System.out.println(":: CUSTOMER LOGIN ::");
		String username = loginHelper();
		return new Customer(username, new Cart());
	}
	private static void workerHome(Worker worker){
		//add functionality for things the worker can do (see customerHome for example)
		Scanner sc = new Scanner(System.in);
		System.out.println(":: WORKER HOME ::");
		System.out.println("Welcome back " + worker.username);
		System.out.println("AVAILABLE TASKS");
		int serial = 1;
		int count = 0;
		for(String name: tasks.keySet()) {
			count = tasks.get(name);
			if(count != 0) {
				System.out.println(serial+" "+name);
				serial++;
			}
		}
		Random rand = new Random();
		int assign = rand.nextInt(serial)+1;
		int i = 0;	
		ArrayList<Integer> list;
		for(String name: tasks.keySet()) {
			count = tasks.get(name);
			if(count != 0) {
				i++;
				if(i == assign) {
					System.out.println("You have been assigned: " + name);
					list = workerHoursAndSalary.get(worker.username);
					System.out.println("Your have to work for " + list.get(0) + " hours");
					System.out.println("Your salary is " + list.get(1) + " per month");
					count--;
					tasks.put(name, count);
					break;
				}
			}
		}		
	}

	//Start of use cases
	public static void home() throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please type the corresponding number for what you would like to do:");
		System.out.println("(1) Login");
		System.out.println("(2) Sign Up");
		int input = scanner.nextInt();
		if(input == 1) {
			login();
		}else if(input == 2) {
			signup();
		}else {
			throw new Exception("Bad User Input");
		}
	}
	public static void login() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please type the corresponding number for your account type:");
		System.out.println("(1) Customer");
		System.out.println("(2) Worker");
		int input2 = sc.nextInt();
		if(input2 == 1) {
			Customer currentCustomer = customerLogin();
			customerHome(currentCustomer);
			home();
		}else if(input2 == 2) {
			Worker worker = workerLogin();
			workerHome(worker);
			home();
		}else {
			throw new Exception("Bad User Input");
		}
		sc.close();
	}
	//as long as a customer is logged in they must always return here
	private static void customerHome(Customer currentCustomer) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		Marketing ad = new Marketing(warehouse.getAvailableItems());
		if (flag == 0) {
			flag=1;
			ad.sale_of_the_week();
		}
		System.out.println("Please type the corresponding number for what you want to do:");
		System.out.println("(1) Shop");
		System.out.println("(2) Checkout");
		System.out.println("(3) Logout");
		System.out.println("(4) Returns");
		System.out.println("(5) Virtual Chat");
		System.out.println("(6) Review Item");
		int input = sc.nextInt();
		if(input == 1) {
			currentCustomer.setCart(addToCart());
			customerHome(currentCustomer);
		}else if(input == 2) {
			currentCustomer.setCart(checkOut(currentCustomer.getCart()));
			shipOrder(currentCustomer.getCart());
			currentCustomer.getReciepts().add(printReciept(currentCustomer));
			customerHome(currentCustomer);
		}
		else if(input == 3) {
			home();
		}
		else if(input == 4) {
			returns(currentCustomer);
			customerHome(currentCustomer);
		}
		else if (input == 5) {
			ArrayList<Item> items = ad.VirtualChat();
			for(Item i : items) {
				currentCustomer.getCart().addItem(i);
			}
			
			customerHome(currentCustomer);
		}else if (input == 6) {
			System.out.println("Type the name of the Item you would like to review:");
			String itemName = sc.next();
			if(CheckHistory(currentCustomer, itemName)) {
				Item itemToReview = findBoughtItemByName(currentCustomer, itemName);
				System.out.println("How many stars do you give the Item from 1-5:");
				int stars = sc.nextInt();
				System.out.println("Type any comments you have about the Item:");
				String textReview = sc.next();
				Review review = new Review(stars, textReview);
				itemToReview.getReviews().add(review);
			}else {
				System.out.println("You must purchase this Item to review it");
			}
			
			customerHome(currentCustomer);
		}
		else {
			throw new Exception("Bad user input");
		}
	}
	private static boolean CheckHistory(Customer currentCustomer, String a){
		ArrayList<Reciept> reciept = currentCustomer.getReciepts();
        for(int i =0; i < reciept.size();i++)
        {
            Cart cart = reciept.get(i).getCart();
            ArrayList<Item> items= cart.getItems();
            for(int j =0; j<items.size();j++)
            {
                if(items.get(i).getName().equals(a))
                {
                    return true;
                }
            }
        }
        return false;
    }
	private static Item findBoughtItemByName(Customer currentCustomer, String a){
		ArrayList<Reciept> reciept = currentCustomer.getReciepts();
        for(int i =0; i < reciept.size();i++)
        {
            Cart cart = reciept.get(i).getCart();
            ArrayList<Item> items= cart.getItems();
            for(int j =0; j<items.size();j++)
            {
                if(items.get(i).getName().equals(a))
                {
                    return items.get(i);
                }
            }
        }
        return null;
    }
	private static void returns(Customer currentCustomer) throws Exception {
		Scanner sc = new Scanner(System.in);
		ArrayList<Reciept> reciepts = currentCustomer.getReciepts();
		ArrayList<Item> allPurchasedItems = new ArrayList<>();
		for(Reciept r : reciepts) {
			for(Item i : r.getCart().getItems()) {
				allPurchasedItems.add(i);
			}
		}
		printItemsFromList(allPurchasedItems);
		System.out.println("Please type the number corresponding to the item to return:");
		if(!sc.hasNextInt()) {
			throw new Exception("Bad user input");
		}
		System.out.println("Please ship the Item to our address");
		System.out.println("$" + allPurchasedItems.get(sc.nextInt()-1).getPrice() + " has been refunded to you.");
		allPurchasedItems.remove(sc.nextInt()-1);
	}
	private static Reciept printReciept(Customer currentCustomer) {
		ArrayList<Item> items = new ArrayList<Item>(currentCustomer.getCart().getItems());
		currentCustomer.getCart().getItems().clear();
		int total = 0;
		for(Item i : items) {
			total += i.getPrice();
		}
		total = total- currentCustomer.getCart().getDiscount();
		Cart recieptCart = new Cart(items);
		recieptCart.setDiscount(currentCustomer.getCart().getDiscount());
		Reciept r = new Reciept(currentCustomer, recieptCart, new Timestamp(System.currentTimeMillis()));
		r.setDiscount(currentCustomer.getCart().getDiscount());
		r.setTotal(total);
		r.print();
		return r;
	}
	private static void shipOrder(Cart finalPaidCart) {
		ArrayList<WarehouseWorker> potentialWorkers = warehouse.getAvailableWorkers();
		potentialWorkers.get(0).hours += 1;
		ArrayList<Item> items = new ArrayList<Item>(finalPaidCart.getItems());
		for(Item i : items) {
			warehouse.decrementItem(i);
		}
	}
	
	private static Cart checkOut(Cart customersCart) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Item> items = new ArrayList<Item>(customersCart.getItems());
		for(Item i : items) {
			System.out.println("your current cart:");
			displayCart(customersCart.getItems());
			System.out.println("would you like to remove an item? (y/n)");
			if(sc.next().equals("y")) {
				printItemsFromList(customersCart.getItems());
				System.out.println("type the number of the item to remove:");
				customersCart.getItems().remove(sc.nextInt()-1);
			}else {
				break;
			}
		}
		items = customersCart.getItems();
		if(items.size() == 0) {
			System.out.println("your cart is empty");
			return new Cart(items);
		}
		int total = 0;
		for(Item i : items) {
			total += i.getPrice();
		}
		int discount=0;
		boolean check = false;
		System.out.println("your total is: $" + total);
		Marketing promo = new Marketing(total, check);
		promo.getPromoCode();
		if(promo.getPromoCode() == true) {
			Scanner scan = new Scanner(System.in);
			String ip = scan.nextLine();
			if (ip.equals("get20")){
				discount = 20;
			}
			if (ip.equals("get50")){
				discount = 50;
			}
			if (ip.equals("get80")){
				discount = 80;
			}
			if (ip.equals("get100")){
				discount = 100;
			}
			total = total-discount;
			customersCart.setDiscount(discount);
			System.out.println("Congrats, your new total is: $" + total);
		}
		System.out.println("please input your card number:");
		String cardNumber = sc.next();
		System.out.println("is " + cardNumber + " valid? (y/n)");
		if(sc.next().equals("n")) {
			boolean cardNumberValid = false;
			while(!cardNumberValid) {
				System.out.println("your total is: $" + total);
				System.out.println("please input your card number:");
				String nextCardNumber = sc.next();
				System.out.println("is " + nextCardNumber + " valid? (y/n)");
				if(sc.next().equals("y")) {
					cardNumberValid = true;
				}
			}
		}
		Cart r = new Cart(items);
		r.setDiscount(discount);
		return r;

	}

	private static Cart addToCart() {
		Cart customersCart = new Cart();
		Scanner sc = new Scanner(System.in);
		System.out.println("current available items:");
		ArrayList<Item> items = warehouse.getAvailableItems();
		printItemsFromList(items);
		System.out.println("pick an item by typing its associated number:");
		Item chosenItem = items.get(sc.nextInt()-1);
		System.out.println(chosenItem.getName());
		System.out.println(chosenItem.getDescription());
		System.out.println("Item Reviews:");
		printReviews(chosenItem);
		System.out.println("would you like to add this to your cart? (y/n)");
		if(sc.next().equals("y")) {
			customersCart.addItem(chosenItem);
		}
		System.out.println("would you like to continue shopping? (y/n)");
		if(sc.next().equals("y")) {
			Cart deepCart = addToCart();
			customersCart.getItems().addAll(deepCart.getItems());
			return customersCart;
		}else {
			return customersCart;
		}
	}
	public static void printReviews(Item item){
		ArrayList<Review> reviews = item.getReviews();
		Collections.sort(reviews, new Review());
		for(Review r : reviews) {
			System.out.println(r.getStars() + " stars");
			System.out.println(r.getReview());
		}
	}
	private static void printItemsFromList(ArrayList<Item> items) {
		for(int i = 1; i < items.size() + 1; i++) {
			Item item = items.get(i-1);
			System.out.println("(" + i + ") " + item.getName() + " $" + item.getPrice() + "\n");
			System.out.println(item.getDescription());
		}
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
		int discount=0;
		boolean check = false;
		for(Item j : itemMap.keySet()) {
			itemNum++;
			int numItems = itemMap.get(j).size();
			System.out.println(numItems + " x " + j.getName() + " - $" + j.getPrice()*numItems);
			total += j.getPrice()*numItems;
		}
		System.out.println("total: $" + total);
	}
	private static ArrayList<Item> createRandItems(){
		ArrayList<Item> randItems = new ArrayList();
		for(int i = 0; i < 45; i++) {
			int price = (int)(Math.random() * 1000) + 1;
			int numItems = (int)(Math.random() * 150) + 1;
			boolean isActive = numItems < 125;
			String name = "item-" + i;
			String description = "some description for" + name;
			randItems.add(new Item(price, numItems, isActive, name, description));
		}
		return randItems;
	}
	private static ArrayList<WarehouseWorker> createWarehouseWorkers(){
		ArrayList<WarehouseWorker> randWorkers = new ArrayList<>();
		for(int i = 0; i < 15; i++) {
			int hours = 0;
			int salary = 8;
			String name = "Worker" + i;
			boolean isWorking = i > 7;
			randWorkers.add(new WarehouseWorker(name, hours, salary, isWorking));
		}
		return randWorkers;
	}
}
