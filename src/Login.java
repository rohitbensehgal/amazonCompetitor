import java.util.Scanner;

public class Login implements AuthenticationOperation{

	@Override
	public String Execute() throws Exception {
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
			int choice = sc.nextInt();
			if(choice == 2) {
				SalesPerson salesPerson = Service.salesLogin();
				Service.salesHome(salesPerson);
			}else if(choice == 1) {
				Worker worker = Service.workerLogin();
				Service.workerHome(worker);
			}else {
				throw new Exception("Bad User Input");
			}
			Service.home();
		}else {
			throw new Exception("Bad User Input");
		}
		sc.close();
		return null;
	}
	
}
