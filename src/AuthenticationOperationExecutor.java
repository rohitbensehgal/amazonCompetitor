import java.util.ArrayList;
import java.util.List;

public class AuthenticationOperationExecutor {

	private final List<AuthenticationOperation> authenticationOperations = new ArrayList<AuthenticationOperation>();
	
	public String executeOperation(AuthenticationOperation authenticationOperation) throws Exception {
		authenticationOperations.add(authenticationOperation);
		return authenticationOperation.Execute();
	}
}
