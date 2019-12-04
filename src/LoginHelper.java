import java.util.Scanner;

public class LoginHelper implements AuthenticationOperation{

	@Override
	public String Execute() throws Exception {
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

}
