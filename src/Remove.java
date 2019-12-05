
public class Remove implements ICommand{

	SalesPerson salesPerson;
	@Override
	public String getDescription() {
		return "remove Item";
	}

	@Override
	public void execute() throws Exception {
		Service.removeItem(salesPerson);
	}

}
