import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

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
		//global exception handler
		while(true) {
			try {
				home();
			} catch(Exception e) {
				System.out.println("An error ocurred please try again");
			}
		}
	}
	static void customerHome(Customer currentCustomer) throws Exception {
		
		try{
	          Image.DisplayImage(currentCustomer.getUsername() + ".jpg");
	        }catch (Exception e){
	        	System.out.println(currentCustomer.getUsername() + "has no profile picture");
	        	System.out.println("To add a profile picture add the image to the filesystem with your username.jpg");
	        }
		
		CMDDisplay display = new CMDDisplay();
		Shop s = new Shop();
		s.currentCustomer = currentCustomer;
		Checkout c = new Checkout();
		c.currentCustomer = currentCustomer;
		Logout l = new Logout();
		Return r = new Return();
		r.currentCustomer = currentCustomer;
		choiceReview review = new choiceReview();
		review.currentCustomer = currentCustomer;
		r.currentCustomer = currentCustomer;
		VirtualChat v = new VirtualChat();
		v.currentCustomer = currentCustomer;
		
		display.addCommand(v);
		display.addCommand(r);
		display.addCommand(s);
		display.addCommand(c);
		display.addCommand(l);
		display.addCommand(review);
		
		display.display();
	}
	static void workerHome(WarehouseWorker worker) throws Exception{

        try{
          Image.DisplayImage(worker.getUsername() + ".jpg");
        }catch (Exception e){
        	System.out.println(worker.getUsername() + "has no profile picture");
        	System.out.println("To add a profile picture add the image to the filesystem with your username.jpg");
        }
        
		CMDDisplay display = new CMDDisplay();
		Survey s = new Survey();
		s.worker = worker;
		Hours h = new Hours();
		h.worker = worker;
		ClockIn c = new ClockIn();
		c.worker = worker;
		Pay p = new Pay();
		p.worker = worker;
		Logout l = new Logout();
		
		display.addCommand(s);
		display.addCommand(h);
		display.addCommand(c);
		display.addCommand(p);
		display.addCommand(l);
		
		display.display();
	}
	static void salesHome(SalesPerson salesPerson) throws Exception {
		
		 try{
	          Image.DisplayImage(salesPerson.getUsername() + ".jpg");
	        }catch (Exception e){
	        	System.out.println(salesPerson.getUsername() + "has no profile picture");
	        	System.out.println("To add a profile picture add the image to the filesystem with your username.jpg");
	        }
		 
		CMDDisplay display = new CMDDisplay();
		
		Add a = new Add();
		a.salesPerson = salesPerson;
		Remove r = new Remove();
		r.salesPerson = salesPerson;
		Disable d = new Disable();
		d.salesPerson = salesPerson;
		Logout l = new Logout();
		
		display.addCommand(a);
		display.addCommand(r);
		display.addCommand(d);
		display.addCommand(l);
		display.display();
		
	}
	public static void HRHome(HumanResources humanResources) throws Exception {
		
		 try{
	          Image.DisplayImage(humanResources.getUsername() + ".jpg");
	        }catch (Exception e){
	        	System.out.println(humanResources.getUsername() + "has no profile picture");
	        	System.out.println("To add a profile picture add the image to the filesystem with your username.jpg");
	        }
		 
		CMDDisplay display = new CMDDisplay();
		
		Edit e = new Edit();
		e.humanResources = humanResources;
		Hire h = new Hire();
		h.humanResources = humanResources;
		Fire f = new Fire();
		f.humanResources = humanResources;
		Logout l = new Logout();
		
		display.addCommand(e);
		display.addCommand(h);
		display.addCommand(f);
		display.addCommand(l);
		
		display.display();
	}
	public static void signup() throws Exception {
		boolean isWorker = false;
		boolean isSales = false;
		boolean isHR = false;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please type the corresponding number for your account type:");
		System.out.println("(1) Customer");
		System.out.println("(2) Worker");
		int input2 = sc.nextInt();
		
		if(input2 == 1) {
			System.out.println("::CUSTOMER SIGNUP::");
		} else if(input2 == 2) {
			System.out.println("::WORKER SIGNUP::");
			isWorker = true;
			System.out.println("Please type the corresponding number for your account type:");
			System.out.println("(1) Warehouse");
			System.out.println("(2) Sales");
			System.out.println("(3) HR");
			int choice = sc.nextInt();
			if(choice == 2) {
				isSales = true;
			}
			if(choice == 3) {
				isHR = true;
			}
		}
		else {
			throw new Exception("Bad User Input");
		}
		System.out.println("Enter username:");
		String username = sc.next();
		System.out.println("Enter password:");
		String password = sc.next();
		
		if(!Service.userAndPasswords.containsKey(username)) {
			Service.userAndPasswords.put(username, password);
			if(!isWorker) {
				Customer c = new Customer(username, new Cart());
				Service.customers.add(c);
			}
			if(isSales) {
				SalesPerson s = new SalesPerson();
				s.setUsername(username);
				Service.salesPeople.add(s);
			}else if(isWorker && !isHR) {
				WarehouseWorker w = new WarehouseWorker();
				w.setUsername(username);
				int[] hours = {0,0,0,0,0,0,0};
				w.setHours(hours);
				w.setSalary(8);
				w.setSkillLevel(0);
				Service.warehouse.getWorkers().add(w);
			}else if(isHR) {
				HumanResources h = new HumanResources();
				h.setUsername(username);
				Service.HR.add(h);
			}
			Database.saveUsersAndPasswords(Service.userAndPasswords, Service.USER_PASS);
		} else {
			System.out.println("This username already exists, try using another username");
			Service.signup();
		}
		Service.home();
	}
	public static void login() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please type the corresponding number for your account type:");
		System.out.println("(1) Customer");
		System.out.println("(2) Worker");
		int input2 = sc.nextInt();
		if(input2 == 1) {
			Customer currentCustomer = Service.customerLogin();
			Service.customerHome(currentCustomer);
			Service.home();
		}else if(input2 == 2) {
			System.out.println("Please type the corresponding number for your account type:");
			System.out.println("(1) Warehouse");
			System.out.println("(2) Sales");
			System.out.println("(3) HR");
			int choice = sc.nextInt();
			if(choice == 2) {
				SalesPerson salesPerson = Service.salesLogin();
				Service.salesHome(salesPerson);
			}else if(choice == 1) {
				WarehouseWorker worker = Service.workerLogin();
				Service.workerHome(worker);
			}else if(choice == 3){
				HumanResources humanResources = Service.HRLogin();
				Service.HRHome(humanResources);
			}else {
				throw new Exception("Bad User Input");
			}
			Service.home();
		}else {
			throw new Exception("Bad User Input");
		}
		sc.close();
	}
	static String loginHelper() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter username:");
		String username = sc.next();
		int input;
		if(Service.userAndPasswords.containsKey(username) == false) {
			System.out.println("Username doesn't exist, try again or sign up");
			System.out.println("(1) Try again");
			System.out.println("(2) Sign Up");
			input = sc.nextInt();
			if(input == 1) {
				Service.loginHelper();
			}else if(input == 2) {
				Service.signup();
			}else {
				throw new Exception("Bad User Input");
			}
		}
		String check = Service.userAndPasswords.get(username);
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
					Service.loginHelper();
				}
			}
		}
		return username;
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
	static void edit(HumanResources humanResources) throws Exception {
		Scanner sc = new Scanner(System.in);
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
	}
	static void hire(HumanResources humanResources) throws Exception {
		Scanner sc = new Scanner(System.in);
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
	}
	static void fire(HumanResources humanResources) throws Exception {
		Scanner sc = new Scanner(System.in);
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
	static void survey(WarehouseWorker worker) throws Exception {
		Scanner sc = new Scanner(System.in);
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
	}
	static void hours(WarehouseWorker worker) throws Exception {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		System.out.println("To change your hours visit an HR representative");
		System.out.println("Today you work " + worker.getHours()[t.getDay()] + " Hours");
		workerHome(worker);
	}
	static void clockIn(WarehouseWorker worker) throws Exception {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		int hours = t.getHours() + worker.getHours()[t.getDay()];
		int minutes = t.getMinutes();
		int seconds = t.getSeconds();
		System.out.println("You will be automatically clocked out at " + hours + ":" + minutes + ":" + seconds);
		workerHome(worker);
	}
	static void pay(WarehouseWorker worker) throws Exception {
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
	}
	//Start of use cases
	public static void home() throws Exception {
		CMDDisplay display = new CMDDisplay();
		display.addCommand(new Login());
		display.addCommand(new Signup());
		display.display();
	}
	static void addItem(SalesPerson salesPerson) throws Exception {
		Scanner sc = new Scanner(System.in);
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
	}
	static void removeItem(SalesPerson salesPerson) throws Exception {
		Scanner sc = new Scanner(System.in);
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
	}
	static void disableItem(SalesPerson salesPerson) throws Exception {
		Scanner sc = new Scanner(System.in);
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
	static void shop(Customer currentCustomer) throws Exception {
		currentCustomer.setCart(addToCart());
		customerHome(currentCustomer);
	}
	static void checkout(Customer currentCustomer) throws Exception {
		currentCustomer.setCart(checkOut(currentCustomer.getCart()));
		currentCustomer.getReciepts().add(printReciept(currentCustomer));
		customerHome(currentCustomer);
	}
	static void logout() throws Exception {
		Database.saveCustomers(customers, CUSTOMERS);
		Database.saveItems(warehouse.getItems(), WAREHOUSE_ITEMS);
		Database.saveSalesPeople(salesPeople, SALES_PEOPLE);
		Database.saveHR(HR, HUMAN_RESOURCES);
		Database.saveWorkers(warehouse.getWorkers(), WAREHOUSE_WORKERS);
		Database.saveUsersAndPasswords(userAndPasswords, USER_PASS);
		home();
	}
	static void choiceReturns(Customer currentCustomer) throws Exception {
		returns(currentCustomer);
		customerHome(currentCustomer);
	}
	static void virtualChat(Customer currentCustomer) throws Exception {
		Marketing ad = new Marketing(warehouse.getAvailableItems());
		if (flag == 0) {
			flag=1;
			ad.sale_of_the_week();
		}
		ArrayList<Item> items = ad.VirtualChat();
		for(Item i : items) {
			currentCustomer.getCart().addItem(i);
		}
		
		customerHome(currentCustomer);
	}
	static void review(Customer currentCustomer) throws Exception {
		Scanner sc = new Scanner(System.in);
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
