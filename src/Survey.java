
public class Survey implements ICommand{

	WarehouseWorker worker;
	@Override
	public String getDescription() {
		return "Skill Survey";
	}

	@Override
	public void execute() throws Exception {
		Service.survey(worker);
	}

}
