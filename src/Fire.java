
public class Fire implements ICommand{

	HumanResources humanResources;
	@Override
	public String getDescription() {
		return "Fire a worker";
	}

	@Override
	public void execute() throws Exception {
		Service.fire(humanResources);
	}

}
