import java.util.Scanner;

public class SignUp implements AuthenticationOperation{

	@Override
	public String Execute() throws Exception {
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
			}else if(isWorker) {
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
		return null;
	}

}
