import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

	static BooksDAO bookdao = BooksDAO.getInstance();	
	static ArrayList<MenuFunction> bookMenu = new ArrayList<MenuFunction>();
	static ArrayList<MenuFunction> mainMenu = new ArrayList<MenuFunction>();
	static ArrayList<MenuFunction> customerMenu = new ArrayList<MenuFunction>();
	static String lineBreaker = "-------------------------";
	static int customerMenuChoice;
	static int bookMenuChoice; 
	static int mainMenuChoice;
	static Scanner in;
	static ArrayList<Book> books;
	static ArrayList<Customer> customers;
	
	
	public void testFunction() {
		int id;	
		addMenus();
		
		
		do {
			msg(lineBreaker + "\nRare Book Emporium administration system\nChoose from these options\n" + lineBreaker);
			printMenu(mainMenu);
			mainMenuChoice = getMenuSelection(mainMenu);
			switch(mainMenuChoice) {
			
			case 1: 
				
				do {
					msg(lineBreaker + "\nBook Options\nChoose from these options\n" + lineBreaker);
					printMenu(bookMenu);
					bookMenuChoice = getMenuSelection(bookMenu);
					switch (bookMenuChoice) {
					
					case 1:
						books = bookdao.getAllBooks(); 
						for(Book b : books) {
							msg(b.toString()); 
						}
						break;
						
					case 2:
						msg("Please enter the book ID you wish to search by");
						int book_id = getInputIntFromUser();
						msg(bookdao.getBook(book_id));
						break;
						
					case 3:
						Book newbook = newBook();
						bookdao.insertBook(newbook);
						break;
						
					case 4:
						msg("Please enter the Book ID");
						id = getInputIntFromUser();
					    books = bookdao.getAllBooks(); 
					 	for(Book b : books) {
							if(id == b.getBook_id()) {
								msg("Please enter the new price");
								int price = getInputIntFromUser();
								b.setPrice(price);
								bookdao.updateBook(b);
							}
						}
						break;
						
					case 5:
						msg("Please enter the Book ID");
						id = getInputIntFromUser();
						bookdao.deleteBook(id);
						break;
					}
				}while(bookMenuChoice != 99);
			
			case 2:
				
				do {
					msg(lineBreaker + "\nCustomer Options\nChoose from these options\n" + lineBreaker);
					printMenu(customerMenu);
					customerMenuChoice = getMenuSelection(customerMenu);
					switch (customerMenuChoice) {
					
					case 1:
						customers = bookdao.getAllCustomers(); 
						for(Customer c : customers) {
							msg(c.toString()); 
						}
						break;
						
					case 2:
						msg("Please enter the customer ID you wish to search by");
						int custID = getInputIntFromUser();
						msg(bookdao.getCustomer(custID));
						break;
						
					case 3:
						Customer newcustomer = newCustomer();
						bookdao.insertCustomer(newcustomer);
						if(bookdao.addressMatch(newcustomer.getAddress().getHousenum(), newcustomer.getAddress().getPostcode()) == false) {
							bookdao.insertAddress(newcustomer.getAddress());
						}
						break;
						
					case 4:
						msg("Please enter the customer ID");
						id = getInputIntFromUser();
					    customers = bookdao.getAllCustomers(); 
					 	for(Customer c : customers) {
							if(id == c.getCustID()) {
								msg("Please enter the new surname");
								String surname = getInputStringFromUser();
								c.setSecondName(surname);
								bookdao.updateCustomer(c);
							}
						}
						break;
						
					case 5:
						msg("Please enter the customer ID");
						id = getInputIntFromUser();
						bookdao.deleteCustomer(id);
						break;
					
					}
					
				}while(customerMenuChoice != 99);
			}	
		}while(mainMenuChoice != 99);
		
		
		
		
		in.close();
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}
	
	static void addMenus() {
		mainMenu.add(new MenuFunction(1, "Book Options"));
		mainMenu.add(new MenuFunction(2, "Customer Options"));
		mainMenu.add(new MenuFunction(99, "Exit"));
		bookMenu.add(new MenuFunction(1, "Retrieve all books"));
		bookMenu.add(new MenuFunction(2, "Search for a book"));
		bookMenu.add(new MenuFunction(3, "Insert a new book into database"));
		bookMenu.add(new MenuFunction(4, "Update existing book price details"));
		bookMenu.add(new MenuFunction(5, "Delete a book from the database"));
		bookMenu.add(new MenuFunction(99, "Exit"));
		customerMenu.add(new MenuFunction(1, "Retrieve all customers"));
		customerMenu.add(new MenuFunction(2, "Search for a customer"));
		customerMenu.add(new MenuFunction(3, "Insert a new customer into database"));
		customerMenu.add(new MenuFunction(4, "Update existing customer in database"));
		customerMenu.add(new MenuFunction(5, "Delete a customer from the database"));
		customerMenu.add(new MenuFunction(99, "Exit"));
	}
	
	static void printMenu(ArrayList<MenuFunction> menu) {
		for(MenuFunction m : menu) {
			msg(m.toString());
		}
	}
	
	
	static String getInputStringFromUser() {
		in = new Scanner(System.in);
		String inputString = in.nextLine();
		return inputString;
	} 
	
	static boolean testStringToInt(String inputString) {
		try {Integer.parseInt(inputString);}
		catch (NumberFormatException e) {return false;}
		return true;
	}
	
	static int getInputIntFromUser() {
		 boolean inputIsInt = false;
		 int inputInt = 0;
		 while(!inputIsInt) {
			 String inputAsString = getInputStringFromUser();
			 if(testStringToInt(inputAsString) == true) {
				inputInt = Integer.parseInt(inputAsString);
				inputIsInt = true;
				break;
			 } else
				 msg("Please enter a number with no letters, symbols or spaces.");
		 } return inputInt;
	}
	
	static int getMenuSelection(ArrayList<MenuFunction> menu) {
		boolean inputMenuSelectionExists = false;
		int loopCount = 0;
		int mainMenuSelection;
		MenuFunction existingOption = null;		
		while(!inputMenuSelectionExists){
			if (loopCount >= 1) 
				msg("\nInvalid menu choice, please try again:");
			else  
			 	msg(lineBreaker + "\nPlease enter your choice");
			mainMenuSelection = getInputIntFromUser();
			for (MenuFunction m : menu) { 
				if(mainMenuSelection == m.getMenuNum()) {
					inputMenuSelectionExists = true;
					existingOption = m;
				 	break;
			 	}
			 } loopCount ++; 	
		} return(existingOption.getMenuNum());
	}
	
	static Book newBook() { //probably not the best way to go about it, was just playing around using reflection
        Field[] fields = Book.class.getDeclaredFields();
        String[] strings = new String[7];
        int[] ints = new int[3];
        int i = 0;
        int j = 0;
        for (Field field : fields) { 
            if(field.getName().toLowerCase().equals("book_id".toLowerCase())) { //ignore id, we can auto generate
            	continue;
            }
            msg("Please enter the " + field.getName());
        	if(field.getType() == Integer.TYPE) {
            	ints[i] = getInputIntFromUser();
            	i++;
            } else {
            	strings[j] = getInputStringFromUser();
            	j++;
            }
        }
        Book b = new Book(strings[0], strings[1], ints[0], ints[1], strings[2], strings[3], strings[4], strings[5], ints[2], strings[6]);
        return b;
    }
	
	static Customer newCustomer() { 
        Field[] fields = Customer.class.getDeclaredFields();
        String[] strings = new String[3];
        Address addr = null;
        int i = 0;
        for (Field field : fields) { 
            if(field.getName().toLowerCase().equals("customerID".toLowerCase())) { 
            	continue;
            }
            if(field.getName().toLowerCase().equals("address".toLowerCase())) {
            	addr = newAddress();
            	continue;
            }
            
            msg("Please enter the " + field.getName());
        	strings[i] = getInputStringFromUser();
        	i++;
        }
        Customer c = new Customer(strings[0], strings[1], addr, strings[2]);
        return c;
    }
	
	static Address newAddress() { 
        Field[] fields = Address.class.getDeclaredFields();
        String[] strings = new String[5];
        int i = 0;
        for (Field field : fields) { 
           
            msg("Please enter the " + field.getName());
        	strings[i] = getInputStringFromUser();
        	i++;
        }
        Address addr = new Address(strings[0], strings[1], strings[2], strings[3], strings[4]);
        return addr;
    }

	
}
