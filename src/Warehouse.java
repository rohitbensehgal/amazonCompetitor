import java.util.ArrayList;

public class Warehouse {
	
	private ArrayList<Item> items;
	private ArrayList<WarehouseWorker> workers;
	
	public Warehouse(ArrayList<Item> items, ArrayList<WarehouseWorker> workers) {
		this.items = items;
		this.workers = workers;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	public ArrayList<WarehouseWorker> getWorkers() {
		return workers;
	}
	public void setWorkers(ArrayList<WarehouseWorker> workers) {
		this.workers = workers;
	}
	public void decrementItem(Item item) {
		for(Item i : items) {
			if(item.equals(i)) {
				if(i.getNumItems() >1) {
					i.setNumItems(i.getNumItems()-1);
				}else {
					i.setNumItems(i.getNumItems()-1);
					i.setActiveStatus(false);
				}
			}
		}
	}
	public ArrayList<WarehouseWorker> getAvailableWorkers(){
		ArrayList<WarehouseWorker> workingWorkers = new ArrayList();
		for(WarehouseWorker worker : workers) {
			if(worker.isWorking()) {
				workingWorkers.add(worker);
			}
		}
		return workingWorkers;
	}
	public ArrayList<Item> getAvailableItems(){
		ArrayList<Item> availableItems = new ArrayList();
		for(Item item : items) {
			if(item.isActive() && item.getNumItems() > 0) {
				availableItems.add(item);
			}
		}
		return availableItems;
	}
}
