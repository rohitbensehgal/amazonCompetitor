
public class VirtualChat implements ICommand{

	Customer currentCustomer;
	@Override
	public String getDescription() {
		return "virtual chat";
	}

	@Override
	public void execute() throws Exception {
		Service.virtualChat(currentCustomer);
	}

}
