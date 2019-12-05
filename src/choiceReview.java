
public class choiceReview implements ICommand{

	Customer currentCustomer;
	@Override
	public String getDescription() {
		return "review";
	}

	@Override
	public void execute() throws Exception {
		Service.review(currentCustomer);
	}

}
