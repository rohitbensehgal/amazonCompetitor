
public class Return implements ICommand{

	Customer currentCustomer;
	@Override
	public String getDescription() {
		return "return";
	}

	@Override
	public void execute() throws Exception {
		Service.choiceReturns(currentCustomer);
	}

}
