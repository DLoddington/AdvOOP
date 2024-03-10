import java.util.*;


public class Order{
	private Customer customer;
	private ArrayList<Book> items;
	private int orderID;
	private boolean complete;
	
	public Order(int orderID, Customer customer, boolean complete) {
		
		this.orderID = orderID;
		this.customer = customer;
		items = new ArrayList<Book>();
		this.complete = complete;		
	}
	
	public Order(Customer customer, int orderID) {
		
		this.customer = customer;
		items = new ArrayList<Book>();
		this.orderID = orderID;
		complete = false;		
	}
	
	public Order(Customer customer) {
		
		this.customer = customer;
		items = new ArrayList<Book>();
		complete = false;		
	}
	
	public Order(int orderID, ArrayList<Book> items, Customer customer, boolean complete) {
		
		this.orderID = orderID;
		this.customer = customer;
		this.items = items;
		this.complete = complete;		
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void addItems(Book b) {
		items.add(b);
	}
  
	public void removeItems(Book b) {
		items.remove(b);
	}

	public int getOrderID() {
		return orderID;
	}
	
	public ArrayList<Book> getItems(){
		return items;
	}
	
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public double getOrderCost(Order o) {
		double orderSum = 0;
		for (Book b : items) {
			System.out.println(b.toString());
			orderSum += b.getPrice();
		} return orderSum;
	}
	
	public void printOrderCost(Order o) {
		double orderSum = 0;
		orderSum = o.getOrderCost(o);
		if ((orderSum/100) % 1 != 0) {
			System.out.println(">>>>>>> TOTAL ORDER VALUE = £" + orderSum/100 + " <<<<<<<<\n");
		} else {
			System.out.println(">>>>>>> TOTAL ORDER VALUE = £" + orderSum/100 + "0 <<<<<<<<\n");
		}
	}
	
	public void printItems(Order o) {
		int i = 0;
		for(Book b : items) {
			System.out.println("[" + (i+1) + "] " + b.toString());
			i++;
		}
	}
	
	@Override
	public String toString() {
		return "------------ ORDER ID - " + orderID + " ------------\nCustomer name: " + customer.getFirstName() + " " + customer.getSecondName() + ". Order complete: " + complete +".\n        >>>>>>>> ITEMS <<<<<<<<";
	}
}