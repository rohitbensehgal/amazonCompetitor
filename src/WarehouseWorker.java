import java.sql.Timestamp;

public class WarehouseWorker {
	
	private String username;
	int[] hours; //hours[0]-Sunday hours[1]-Monday etc.
	private int salary;
	private int skillLevel; //1-4
	
	private boolean isWorking;

	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}
	public int[] getHours() {
		return hours;
	}
	public void setHours(int[] hours) {
		this.hours = hours;
	}
	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getHoursToday(Timestamp timestamp) {
		return 1;
	}
	public void setSkillLevel(Integer valueOf) {
		this.skillLevel = valueOf;
	}
	public int getSkillLevel() {
		return this.skillLevel;
	}

}
