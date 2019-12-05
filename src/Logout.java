
public class Logout implements ICommand{

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "logout";
	}

	@Override
	public void execute() throws Exception {
		Service.logout();
	}

}
