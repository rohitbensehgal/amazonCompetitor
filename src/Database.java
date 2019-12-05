import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Database {
	
	public static ArrayList<Item> getItems(String fileName) throws Exception {
		File file = new File(fileName); 
		Scanner sc = new Scanner(file); 
		ArrayList<Item> items = new ArrayList<>();
		while (sc.hasNextLine()) {
			Item item = new Item();
			String itemToParse = sc.nextLine();
			getParsedItem(item, itemToParse);
			items.add(item);
		}
		return items;
	}
	public static void saveItems(ArrayList<Item> items, String fileName) throws Exception{
		
	    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    for(Item i : items) {
	    	writer.write(i.toDBString() + "\n");
	    }
	    writer.close();
	}
	public static ArrayList<Reciept> getReciepts(String fileName, Customer customer) throws Exception{
		
		File file = new File(fileName); 
		Scanner sc = new Scanner(file);
		ArrayList<Reciept> reciepts = new ArrayList<>();
		while (sc.hasNextLine()) {
			Reciept reciept = new Reciept();
			Cart cart = new Cart();
			reciept.setCustomer(customer);
			int numItems = Integer.valueOf(sc.nextLine());
			cart.setDiscount(Integer.valueOf(sc.nextLine()));
			for(int i = 0; i < numItems; i++) {
				Item item = new Item();
				getParsedItem(item, sc.nextLine());
				cart.getItems().add(item);
			}
			reciept.setCart(cart);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    Date parsedDate = dateFormat.parse(sc.nextLine());
		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			reciept.setTimeOfPurchase(timestamp);
			reciept.setTotal(Integer.valueOf(sc.nextLine()));
			reciept.setDiscount(Integer.valueOf(sc.nextLine()));
			reciepts.add(reciept);
		}
		return reciepts;
	}
	public static void saveReciepts(ArrayList<Reciept> reciepts, String fileName) throws Exception {

		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    for(Reciept r : reciepts) {
	    	writer.write(r.getCart().getNumItems() + "\n");
	    	writer.write(r.getCart().getDiscount() + "\n");
	    	for(Item i : r.getCart().getItems()) {
	    		writer.write(i.toDBString() + "\n");
	    	}
	    	writer.write(r.getTimeOfPurchase() + "\n");
	    	writer.write(r.getTotal() + "\n");
	    	writer.write(r.getDiscount() + "\n");
	    }
	    writer.close();
	}
	public static ArrayList<Customer> getCustomers(String fileName) throws Exception{
		
		ArrayList<Customer> customers = new ArrayList<>();
		File file = new File(fileName); 
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			Customer customer = new Customer();
			Cart cart = new Cart();
			customer.setUsername(sc.nextLine());
			
			File recFile = new File(customer.getUsername() + "-reciepts.txt");
			if(recFile.exists() && recFile.isFile()) {
				customer.setReciepts(getReciepts(customer.getUsername() + "-reciepts.txt", customer));
			}else {
				customer.setReciepts(new ArrayList<Reciept>());
			}
			sc.nextLine();
			int numItems = Integer.valueOf(sc.nextLine());
			cart.setDiscount(Integer.valueOf(sc.nextLine()));
			for(int i = 0; i < numItems; i++) {
				Item item = new Item();
				getParsedItem(item, sc.nextLine());
				cart.getItems().add(item);
			}
			customer.setCart(cart);
			customers.add(customer);
		}
		
		return customers;
	}
	public static void saveCustomers(ArrayList<Customer> customers, String fileName) throws Exception {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    for(Customer c : customers) {
	    	writer.write(c.getUsername() + "\n");
	    	writer.write(c.getUsername() + "-reciepts.txt" + "\n");
	    	if(c.getCart().getNumItems() > 0) {
	    		writer.write(c.getCart().getNumItems() + "\n");
		    	writer.write(c.getCart().getDiscount() + "\n");
		    	for(Item i : c.getCart().getItems()) {
		    		writer.write(i.toDBString() + "\n");
		    	}
	    	}else {
	    		writer.write(0 + "\n");
		    	writer.write(0 + "\n");
	    	}
	    	
	    	saveReciepts(c.getReciepts(), c.getUsername() + "-reciepts.txt");
	    }
	    writer.close();
	}
	public static ArrayList<HumanResources> getHR(String fileName) throws FileNotFoundException {
		ArrayList<HumanResources> humanResources = new ArrayList<>();
		File file = new File(fileName); 
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			HumanResources hr = new HumanResources();
			hr.setUsername(sc.nextLine());
			humanResources.add(hr);
		}
		return humanResources;
	}
	public static void saveHR(ArrayList<HumanResources> hR, String fileName) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    for(HumanResources h : hR) {
	    	writer.write(h.getUsername() + "\n");
	    }
	    writer.close();
	}
	public static void saveSalesPeople(ArrayList<SalesPerson> salesPeople, String fileName) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    for(SalesPerson s : salesPeople) {
	    	writer.write(s.getUsername() + "\n");
	    	writer.write(s.getNumItems() + "\n");
	    	for(Item i : s.getItems()) {
	    		writer.write(i.toDBString() + "\n");
	    	}
	    }
	    writer.close();
	}
	public static ArrayList<SalesPerson> getSalesPeople(String fileName) throws Exception {
		ArrayList<SalesPerson> salesPeople = new ArrayList<>();
		File file = new File(fileName); 
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			SalesPerson salesPerson = new SalesPerson();
			ArrayList<Item> items = new ArrayList<Item>();
			salesPerson.setUsername(sc.nextLine());
			int numItems = Integer.valueOf(sc.nextLine());
			salesPerson.setNumItems(Integer.valueOf(numItems));
			for(int i = 0; i < numItems; i++) {
				Item item = new Item();
				getParsedItem(item, sc.nextLine());
				items.add(item);
			}
			salesPeople.add(salesPerson);
		}
		return salesPeople;
	}
	public static void saveWorkers(ArrayList<WarehouseWorker> warehouseWorkers, String fileName) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    for(WarehouseWorker w : warehouseWorkers) {
	    	writer.write(w.getUsername() + "\n");
	    	writer.write(w.getSalary() + "\n");
	    	writer.write(w.getSkillLevel() + "\n");
	    	String hours = "";
	    	for(int i = 0; i < w.getHours().length; i++) {
	    		hours = hours.concat(w.getHours()[i] + ",");
	    	}
	    	writer.write(hours.substring(0, 13) + "\n");
	    }
	    writer.close();
	}
	public static ArrayList<WarehouseWorker> getWarehouseWorkers(String fileName) throws Exception {
		ArrayList<WarehouseWorker> warehouseWorkers = new ArrayList<>();
		File file = new File(fileName); 
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			WarehouseWorker w = new WarehouseWorker();
			w.setUsername(sc.nextLine());
			w.setSalary(Integer.valueOf(sc.nextLine()));
			w.setSkillLevel(Integer.valueOf(sc.nextLine()));
			int[] hours = {0,0,0,0,0,0,0};
			String[] s = sc.nextLine().split(",");
			for(int i = 0; i < 7; i++) {
				hours[i] = Integer.valueOf(s[i]);
			}
			w.setHours(hours);
			warehouseWorkers.add(w);
		}
		return warehouseWorkers;
	}
	public static HashMap<String, String> getUsersAndPasswords(String fileName) throws Exception{
		HashMap<String, String> userPass = new HashMap<String, String>();
		File file = new File(fileName); 
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			String[] userPasswords = sc.nextLine().split(",");
			userPass.put(userPasswords[0], userPasswords[1]);
		}
		
		return userPass;
	}
	public static void saveUsersAndPasswords(HashMap<String, String> userPass, String fileName) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    	for(String username : userPass.keySet()) {
	    		writer.write(username + "," + userPass.get(username) + "\n");
	    	}
	    	
	    writer.close();
	}
	public static void getParsedItem(Item item, String itemToParse) {
		String[] split = itemToParse.split(",");
		item.setPrice(Integer.valueOf(split[0]));
		item.setNumItems(Integer.valueOf(split[1]));
		if(split[2].equals("0")) {
			item.setActiveStatus(false);
		}else {
			item.setActiveStatus(true);
		}
		item.setName(split[3]);
		item.setDescription(split[4]);
		if(split.length == 7) {
			String[] reviewSplit = split[5].split(";");
			for(String s : reviewSplit) {
				Review review = new Review();
				String[] individualReview = s.split("-");
				review.setStars(Integer.valueOf(individualReview[0]));
				review.setReview(individualReview[1]);
				item.getReviews().add(review);
			}
			item.setImage(split[6]);
		}else if(split.length == 6) {
			if(split[5].contains(".jpg")) {
				item.setImage(split[5]);
			}else {
				String[] reviewSplit = split[5].split(";");
				for(String s : reviewSplit) {
					Review review = new Review();
					String[] individualReview = s.split("-");
					review.setStars(Integer.valueOf(individualReview[0]));
					review.setReview(individualReview[1]);
					item.getReviews().add(review);
				}
			}
		}
	}
}
