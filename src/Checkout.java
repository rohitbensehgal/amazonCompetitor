
public class Checkout implements ICommand{

	Customer currentCustomer;
	@Override
	public String getDescription() {
		return "checkout";
	}

	@Override
	public void execute() throws Exception {
		Service.checkout(currentCustomer);
	}

}
