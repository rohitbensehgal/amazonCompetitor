
public class Edit implements ICommand{

	HumanResources humanResources;
	@Override
	public String getDescription() {
		return "Edit worker hours";
	}

	@Override
	public void execute() throws Exception {
		Service.edit(humanResources);
	}

}
