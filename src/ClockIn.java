
public class ClockIn implements ICommand{

	WarehouseWorker worker;
	@Override
	public String getDescription() {
		return "clock in";
	}

	@Override
	public void execute() throws Exception {
		Service.clockIn(worker);
	}

}
