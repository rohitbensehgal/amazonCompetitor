import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Service {
	
	private static final String WAREHOUSE_ITEMS = "WarehouseItems.txt";
	private static final String CUSTOMERS = "Customers.txt";
	private static final String SALES_PEOPLE = "SalesPeople.txt";
	static final String USER_PASS = "UsernamesAndPasswords.txt";
	private static final String WAREHOUSE_WORKERS = "WarehouseWorkers.txt";
	private static final String HUMAN_RESOURCES = "HumanResources.txt";
	static Warehouse warehouse;
	static ArrayList<Customer> customers;
	static ArrayList<SalesPerson> salesPeople;
	static ArrayList<HumanResources> HR;
	static HashMap<String, String> userAndPasswords;
	private static HashMap<String, Integer> tasks;	// taskName, workerRequirement
	private static HashMap<String, ArrayList<Integer>> workerHoursAndSalary;
	private static int flag = 0;
	private static void init() throws Exception{
		warehouse = new Warehouse(Database.getItems(WAREHOUSE_ITEMS), Database.getWarehouseWorkers(WAREHOUSE_WORKERS));
		customers = Database.getCustomers(CUSTOMERS);
		salesPeople = Database.getSalesPeople(SALES_PEOPLE);
		userAndPasswords = Database.getUsersAndPasswords(USER_PASS);
		HR = Database.getHR(HUMAN_RESOURCES);
		tasks = new HashMap<String, Integer>();
		workerHoursAndSalary = new HashMap<String, ArrayList<Integer>>();
		tasks.put("Stock", 5);
		tasks.put("Warehouse", 10);
		tasks.put("Check Expired items", 4);
		tasks.put("Shipping", 10);
	}
	
	public static void main(String[] args) throws Exception {
		init();
		home();
	}
	
	public static void signup() throws Exception {
		AuthenticationOperationExecutor authenticationOperationExecutor = new AuthenticationOperationExecutor();
		authenticationOperationExecutor.executeOperation(new SignUp());
	}
	public static void login() throws Exception {
		AuthenticationOperationExecutor authenticationOperationExecutor = new AuthenticationOperationExecutor();
		authenticationOperationExecutor.executeOperation(new Login());
	}
	static String loginHelper() throws Exception {
		AuthenticationOperationExecutor authenticationOperationExecutor = new AuthenticationOperationExecutor();
		return authenticationOperationExecutor.executeOperation(new LoginHelper());
	}
	
	static WarehouseWorker workerLogin() throws Exception {
		System.out.println(":: WORKER LOGIN ::");
		String username = loginHelper();
		return getWarehouseWorkerByUsername(username);
	}
	static Customer customerLogin() throws Exception{
		//just a placeholder to look like login page
		System.out.println(":: CUSTOMER LOGIN ::");
		String username = loginHelper();
		Customer cust = findCustomerByUsername(username);
		return cust;
	}
	static SalesPerson salesLogin() throws Exception {
		String username = loginHelper();
		SalesPerson salesPerson = findSalesPerson(username);
		return salesPerson;
	}
	public static HumanResources HRLogin() throws Exception {
		String username = loginHelper();
		return getHRByUsername(username);
	}

	public static void HRHome(HumanResources humanResources) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input the number corresponding to what you would like to do:");
		System.out.println("(1) Edit worker hours");
		System.out.println("(2) Hire new worker");
		System.out.println("(3) Fire worker");
		System.out.println("(4) Logout");
		int choice = Integer.valueOf(sc.nextLine());
		if(choice == 1) {
			System.out.println("Enter the worker's username");
			WarehouseWorker w = getWarehouseWorkerByUsername(sc.nextLine());
			System.out.println("Please type the workers hours, comma seperated by day (day 1 is sunday)");
			System.out.println("Example (normal 9-5): 0,8,8,8,8,8,0");
			String[] s = sc.nextLine().split(",");
			int[] hours = {0,0,0,0,0,0,0};
			for(int i = 0; i < 7; i++) {
				hours[i] = Integer.valueOf(s[i]);
			}
			w.setHours(hours);
			System.out.println("Worker's hours updated");
			HRHome(humanResources);
		}else if(choice == 2) {
			WarehouseWorker w = new WarehouseWorker();
			System.out.println("Please enter the username for this worker");
			w.setUsername(sc.nextLine());
			System.out.println("How much will this worker get paid?");
			w.setSalary(Integer.valueOf(sc.nextLine()));
			w.setSkillLevel(0);
			System.out.println("Please type the workers hours, comma seperated by day (day 1 is sunday)");
			System.out.println("Example (normal 9-5): 0,8,8,8,8,8,0");
			String[] s = sc.nextLine().split(",");
			int[] hours = {0,0,0,0,0,0,0};
			for(int i = 0; i < 7; i++) {
				hours[i] = Integer.valueOf(s[i]);
			}
			w.setHours(hours);
			warehouse.getWorkers().add(w);
			HRHome(humanResources);
		}else if(choice == 3) {
			System.out.println("Please enter the username of the worker you would like to fire:");
			WarehouseWorker w = getWarehouseWorkerByUsername(sc.nextLine());
			System.out.println("Username: " + w.getUsername());
			System.out.println("Salary: $" + w.getSalary() + "/hour");
			System.out.println("Skill Level: " + w.getSkillLevel());
			String hours = "";
	    	for(int i = 0; i < w.getHours().length; i++) {
	    		hours = hours.concat(w.getHours()[i] + ",");
	    	}
	    	System.out.println("Hours: " + hours.substring(0, 13));
			System.out.println("Are you sure you want to fire this worker? (y/n)");
			if(sc.nextLine().equals("y")) {
				warehouse.getWorkers().remove(w);
				System.out.println("Worker removed from system");
			}
			HRHome(humanResources);
		}else if(choice == 4) {
			Database.saveHR(HR, HUMAN_RESOURCES);
			Database.saveWorkers(warehouse.getWorkers(), WAREHOUSE_WORKERS);
			home();
		}
	}
	static HumanResources getHRByUsername(String username) {
		for(HumanResources h : HR) {
			if(h.getUsername().equals(username)) {
				return h;
			}
		}
		return null;
	}
	static WarehouseWorker getWarehouseWorkerByUsername(String username) {
		for(WarehouseWorker worker : warehouse.getWorkers()) {
			if(worker.getUsername().equals(username)) {
				return worker;
			}
		}
		return null;
	}
	static void workerHome(WarehouseWorker worker) throws Exception{
		//add functionality for things the worker can do (see customerHome for example)
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input the number corresponding to what you would like to do:");
		System.out.println("(1) Take skill survey");
		System.out.println("(2) View today's hours");
		System.out.println("(3) Clock in");
		System.out.println("(4) Collect pay");
		System.out.println("(5) Logout");
		int choice = Integer.valueOf(sc.nextLine());
		if(choice == 1) {
			System.out.println("Please fill out the following survey");
			System.out.println("Do you have a college degree? (y/n)");
			String inp = sc.nextLine();
			if(inp.equals("y")) {
				System.out.println("What was your major?");
				System.out.println("1. Engineering");
				System.out.println("2. Science");
				System.out.println("3. Business");
				System.out.println("4. Biology");
				System.out.println("5. Other");
				worker.setSkillLevel(Integer.valueOf(sc.nextLine()));
			}
			workerHome(worker);
		}else if(choice == 2) {
			Timestamp t = new Timestamp(System.currentTimeMillis());
			System.out.println("To change your hours visit an HR representative");
			System.out.println("Today you work " + worker.getHours()[t.getDay()] + " Hours");
			workerHome(worker);
		}else if(choice == 3) {
			Timestamp t = new Timestamp(System.currentTimeMillis());
			int hours = t.getHours() + worker.getHours()[t.getDay()];
			int minutes = t.getMinutes();
			int seconds = t.getSeconds();
			System.out.println("You will be automatically clocked out at " + hours + ":" + minutes + ":" + seconds);
			workerHome(worker);
		}else if(choice == 4) {
			Timestamp t = new Timestamp(System.currentTimeMillis());
			if(t.getDay() != 5) {
				System.out.println("It must be friday for you to get paid");
				workerHome(worker);
			}else {
				int sum = IntStream.of(worker.getHours()).sum();
				int total = sum*worker.getSalary();
				int tax = (int) Math.round(total*0.12);
				int afterTaxTotal = total - tax;
				System.out.println("Your paycheck will be deposited with:");
				System.out.println("Total: $" + total);
				System.out.println("Taxes: $" + tax);
				System.out.println("Final: $" + afterTaxTotal);
				workerHome(worker);
			}
		}else if(choice == 5) {
			Database.saveWorkers(warehouse.getWorkers(), WAREHOUSE_WORKERS);
			home();
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
	static void salesHome(SalesPerson salesPerson) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input the number corresponding to what you would like to do:");
		System.out.println("(1) Add New Item");
		System.out.println("(2) Remove an Item");
		System.out.println("(3) Temporarily disable an Item");
		System.out.println("(4) Logout");
		int choice = sc.nextInt();
		if(choice == 1) {
			sc.nextLine();
			Item item = new Item();
			System.out.println("Please type the item name:");
			if(sc.hasNextLine()) {
				item.setName(sc.nextLine());
			}
			System.out.println("Please type the item description:");
			if(sc.hasNextLine()) {
				item.setDescription(sc.nextLine());
			}
			System.out.println("How many items will be available?");
			item.setNumItems(sc.nextInt());
			System.out.println("How much will the item cost?");
			item.setPrice(sc.nextInt());
			item.setActiveStatus(true);
			warehouse.getItems().add(item);
			salesPerson.getItems().add(item);
			System.out.println("--Item Successfully Added--");
			salesHome(salesPerson);
		}else if(choice == 2) {
			if(salesPerson.getItems().size() == 0) {
				System.out.println("You have no items in the shop");
				salesHome(salesPerson);
			}
			printItemsFromList(salesPerson.getItems());
			System.out.println("Please type the corresponding number to the Item you would like to remove:");
			int indexToRemove = sc.nextInt()-1;
			Item itemToRemove = salesPerson.getItems().get(indexToRemove);
			for(Item i : warehouse.getAvailableItems()) {
				if(i.equals(itemToRemove)) {
					warehouse.getAvailableItems().remove(i);
				}
				System.out.println("--Item successfully removed--");
				salesPerson.getItems().remove(indexToRemove);
				salesHome(salesPerson);
			}
		}else if(choice == 3) {
			if(salesPerson.getItems().size() == 0) {
				System.out.println("You have no items in the shop");
				salesHome(salesPerson);
			}
			printItemsFromList(salesPerson.getItems());
			System.out.println("Please type the corresponding number to the Item you would like to disable:");
			Item itemToDisable = salesPerson.getItems().get(sc.nextInt()-1);
			itemToDisable.setActiveStatus(false);
			for(Item i : warehouse.getAvailableItems()) {
				if(i.equals(itemToDisable)) {
					i.setActiveStatus(false);
				}
				System.out.println("--Item successfully disabled--");
				salesHome(salesPerson);
			}
		}else if(choice == 4) {
			Database.saveSalesPeople(salesPeople, SALES_PEOPLE);
			Database.saveItems(warehouse.getItems(), WAREHOUSE_ITEMS);
			home();
		}else {
			throw new Exception("Bad User Input");
		}
		
	}
	private static SalesPerson findSalesPerson(String username) {
		for(SalesPerson s : salesPeople) {
			if(s.getUsername().equals(username)) {
				return s;
			}
		}
		return null;
	}
	//as long as a customer is logged in they must always return here
	static void customerHome(Customer currentCustomer) throws Exception {
		
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
			currentCustomer.getReciepts().add(printReciept(currentCustomer));
			customerHome(currentCustomer);
		}
		else if(input == 3) {
			Database.saveItems(warehouse.getItems(), WAREHOUSE_ITEMS);
			Database.saveCustomers(customers, CUSTOMERS);
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
			sc.nextLine();
			if(CheckHistory(currentCustomer, itemName)) {
				Item itemToReview = findBoughtItemByName(currentCustomer, itemName);
				System.out.println("How many stars do you give the Item from 1-5:");
				int stars = Integer.valueOf(sc.nextLine());
				System.out.println("Type any comments you have about the Item:");
				String textReview = sc.nextLine();
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
		int itemToRemove = sc.nextInt()-1;
		if(allPurchasedItems.size() > 0) {
			System.out.println("Please ship the Item to our address");
			System.out.println("$" + allPurchasedItems.get(itemToRemove).getPrice() + " has been refunded to you.");
			allPurchasedItems.remove(itemToRemove);
		}else if(itemToRemove > allPurchasedItems.size()) {
			System.out.println("Invalid Input");
		}else {
			System.out.println("You must purchase an Item to return it");
		}
		
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

	private static Cart checkOut(Cart customersCart) {
		Scanner sc = new Scanner(System.in);
		Random rand = new Random();
		int c = rand.nextInt(3)+2;
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
		String s;
		int shipCost = 7;
		System.out.println("Would you like to ship these items?(y/n)");
		String ans = sc.next();
		String[] ch;
		if(ans.equals("y")) {
			System.out.println("Please enter your address:");
			System.out.println("City, State");
			while(true) {
			s = sc.nextLine();
			ch = s.split(", ");
			//System.out.println("length"+ch.length);
			if(ch.length !=2) {
				System.out.println("Please write it in this format. For instance: (Ames, Iowa)");
				continue;
			}
			break;
			
		}
			System.out.println("Choose your delivery option:");
			
			if(ch[1].charAt(0) == 'I' || ch[1].charAt(0) == 'M') {
				System.out.println("(1) 1 day shipping - $" + shipCost);
				System.out.println("(2) 3-4 day shipping - $" + c);
			}
			else {
				shipCost = 10;
				c+= 2;
				System.out.println("(1) 1 day shipping - $" + shipCost);
				System.out.println("(2) 3-4 day shipping - $" + c);
			}
			int n = sc.nextInt();
			if(n==1) {
				total+=shipCost;
			}
			else if(n==2) {
				total+=c;
			}
			System.out.println("your total is now: $" + total);
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
		Cart cart = new Cart(items);
		cart.setDiscount(0 - shipCost);
		return cart;
	}

	private static Cart addToCart() throws Exception {
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
		if(chosenItem.getImage() != null) {
			System.out.println("Would you like to view an image of this Item? (y/n)");
			if(sc.next().equals("y")) {
				Image.DisplayImage(chosenItem.getImage());
			}
		}
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
	private static Customer findCustomerByUsername(String username) {
		for(Customer c : customers) {
			if(c.getUsername().equals(username)) {
				return c;
			}
		}
		return null;
	}
}
