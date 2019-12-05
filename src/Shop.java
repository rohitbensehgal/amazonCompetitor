
public class Shop implements ICommand{
	
	Customer currentCustomer;

	@Override
	public String getDescription() {
		return "shop";
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		Service.shop(currentCustomer);
	}

}
