
public class Hours implements ICommand{

	WarehouseWorker worker;
	@Override
	public String getDescription() {
		return "view hours";
	}

	@Override
	public void execute() throws Exception {
		Service.hours(worker);;
	}

}
