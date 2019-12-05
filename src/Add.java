
public class Add implements ICommand{

	SalesPerson salesPerson;
	@Override
	public String getDescription() {
		return "Add Item";
	}

	@Override
	public void execute() throws Exception {
		Service.addItem(salesPerson);
	}

}
