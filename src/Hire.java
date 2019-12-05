
public class Hire implements ICommand{

	HumanResources humanResources;
	@Override
	public String getDescription() {
		return "Hire a worker";
	}

	@Override
	public void execute() throws Exception {
		Service.hire(humanResources);
	}

}
