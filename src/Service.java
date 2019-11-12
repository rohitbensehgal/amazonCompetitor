import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Service {
	
	private static Warehouse warehouse;
	private static ArrayList<Customer> customers;
	private static HashMap<String, String> usernamesAndPasswords;
	
	private static void init(){
		warehouse = new Warehouse(createRandItems(), createWarehouseWorkers());
	}
	
	public static void main(String[] args) throws Exception {
		init();
		home();
	}
	//Placeholder methods that need to be filled out to work
	
	public static void signup() throws Exception {
		//some sign up function
		Customer customer = new Customer(null, null, null);
		//.
		//.
		//.
		customerHome(customer);
	}
	//workers usernames & passwords will be randomly created & stored as filler info
	//as long as a worker is logged in they must always return here
	private static Worker workerLogin() {
		Worker worker = new Worker(null, 0, 0);
		//.
		//.
		//.
		return worker;
	}
	private static Customer customerLogin() {
		//just a placeholder to look like login page
		Scanner sc = new Scanner(System.in);
		System.out.println("login username:");
		String username = sc.next();
		System.out.println("login password:");
		String password = sc.next();
		//random for now
		return new Customer(username, password, new Cart());
	}
	private static void workerHome(Worker worker){
		//add functionality for things the worker can do (see customerHome for example)
		
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
		System.out.println("Please type the corresponding number for what you want to do:");
		System.out.println("(1) Shop");
		System.out.println("(2) Checkout");
		System.out.println("(3) Logout");
		System.out.println("(4) Returns");
		int input = sc.nextInt();
		if(input == 1) {
			currentCustomer.setCart(addToCart());
			customerHome(currentCustomer);
		}else if(input == 2) {
			currentCustomer.setCart(checkOut(currentCustomer.getCart()));
			shipOrder(currentCustomer.getCart());
			currentCustomer.getReciepts().add(printReciept(currentCustomer));
			customerHome(currentCustomer);
		}else if(input == 3) {
			home();
		}else if(input == 4) {
			returns(currentCustomer);
			customerHome(currentCustomer);
		}else {
			throw new Exception("Bad user input");
		}
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
		ArrayList<Item> items = new ArrayList(currentCustomer.getCart().getItems());
		currentCustomer.getCart().getItems().clear();
		int total = 0;
		for(Item i : items) {
			total += i.getPrice();
		}
		Cart recieptCart = new Cart(items);
		Reciept r = new Reciept(currentCustomer, recieptCart, new Timestamp(System.currentTimeMillis()));
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
		System.out.println("your total is: $" + total);
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
		return new Cart(items);
		
	}
	
	private static Cart addToCart() {
		Cart customersCart = new Cart();
		Scanner sc = new Scanner(System.in);
		System.out.println("current available items:");
		ArrayList<Item> items = warehouse.getAvailableItems();
		printItemsFromList(items);
		System.out.println("pick an item by typing its associated number:");
		Item chosenItem = items.get(sc.nextInt()-1);
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
			String name = "WWorker" + i;
			boolean isWorking = i > 7;
			randWorkers.add(new WarehouseWorker(name, hours, salary, isWorking));
		}
		return randWorkers;
	}
}
