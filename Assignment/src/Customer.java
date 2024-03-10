
public class Customer {
	
	private int customerID; 
	private String firstName;
	private String secondName;
	private Address address ;
	private String telephoneNumber;
	
	public Customer(int customerID, String firstName, String secondName, Address address, String telephoneNumber) {

		this.customerID = customerID;
		this.firstName = firstName;
		this.secondName = secondName;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
	}
	
	public Customer(String firstName, String secondName, Address address, String telephoneNumber) { //need a constructor without id as the table auto increments
		
		this.firstName = firstName;
		this.secondName = secondName;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
	}

	public int getCustID() {
		return customerID;
	}

	public void setCustID(int customerID) {
		this.customerID = customerID;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	@Override
	public String toString() {
		return customerID + ": " + firstName + " " + secondName
				+ ". " + address + ". Tel: " + telephoneNumber;
	}
	
	
	

}
