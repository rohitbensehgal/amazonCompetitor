
public class Login implements ICommand{

	@Override
	public String getDescription() {
		
		return "login";
	}

	@Override
	public void execute() throws Exception {
		Service.login();
	}

}
