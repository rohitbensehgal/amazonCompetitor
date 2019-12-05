
public interface ICommand {

	String getDescription();
	void execute() throws Exception;
	
}
