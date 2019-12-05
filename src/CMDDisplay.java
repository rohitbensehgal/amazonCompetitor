import java.util.ArrayList;
import java.util.Scanner;

public class CMDDisplay {
	
	ArrayList<ICommand> commands;
	
	CMDDisplay(){
		commands  = new ArrayList<ICommand>();
	}
	public void addCommand(ICommand command) {
		commands.add(command);
	}
	public void display() throws Exception {
		Scanner sc = new Scanner(System.in);
		int i = 1;
		System.out.println("Please enter the corresponding number for your action:");
		for(ICommand command : commands) {
			System.out.println("(" + i + ") " + command.getDescription());
			i++;
		}
		commands.get(Integer.valueOf(sc.nextLine())-1).execute();
		sc.close();
	}
}
