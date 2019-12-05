
public class Disable implements ICommand{
	
	SalesPerson salesPerson;
	@Override
	public String getDescription() {
		return "temporarily disable an item";
	}

	@Override
	public void execute() throws Exception {
		Service.disableItem(salesPerson);
	}

}
