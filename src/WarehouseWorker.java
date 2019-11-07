
public class WarehouseWorker extends Worker{
	
	private boolean isWorking;

	WarehouseWorker(String name, int hours, int salary) {
		super(name, hours, salary);
	}
	WarehouseWorker(String name, int hours, int salary, boolean isWorking) {
		super(name, hours, salary);
		this.isWorking = isWorking;
		// TODO Auto-generated constructor stub
	}
	
	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

}
