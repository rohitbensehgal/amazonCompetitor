
public class Worker{
	
	// public String name;
	public String username;
	public int hours;
	public int salary;
	
	Worker(String username, int hours, int salary){
		this.username = username;
		this.hours = hours;
		this.salary = salary;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
}
