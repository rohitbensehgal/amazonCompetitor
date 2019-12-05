
public class Pay implements ICommand{

	WarehouseWorker worker;
	@Override
	public String getDescription() {
		return "Collect Pay";
	}

	@Override
	public void execute() throws Exception {
		Service.pay(worker);
	}

}
