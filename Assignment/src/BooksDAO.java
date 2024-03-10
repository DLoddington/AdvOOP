import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;


public class BooksDAO {
	
	private static BooksDAO instance;
	
	private BooksDAO() {} 
	
	public static BooksDAO getInstance() {
		if (instance == null) 
			instance = new BooksDAO();
		return instance;
	}
	//above 3 functions are for lazy instantiation of singleton
	//only allow for generations of 1 BooksDAO object

	private static Connection getDBConnection() { 
		Connection dbConnection = null;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch(ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			String dbURL = "jdbc:sqlite:rarebookdb.db";
			dbConnection = DriverManager.getConnection(dbURL);
			return dbConnection;
		}catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return dbConnection;
	}
	
	public ArrayList<Book> getAllBooks(){
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM books;";
		ArrayList<Book> books = new ArrayList<>();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				int book_id = result.getInt("ID");
				String title = result.getString("Title");
				String author = result.getString("Author");
				int year = result.getInt("Year");
				int edition = result.getInt("Edition");
				String publisher = result.getString("Publisher");
				String isbn = result.getString("ISBN");
				String cover = result.getString("Cover");
				String condition = result.getString("Condition");
				int price = result.getInt("Price");
				String notes = result.getString("Notes");
				books.add(new Book(book_id,title,author,year,edition,publisher,isbn,cover,condition,price,notes));
			}
		} catch(SQLException e) {
			msg("get all books: "+e);
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return books;
	}
	
	public ArrayList<Customer> getAllCustomers(){
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM customer LEFT JOIN address ON customer.housenum = address.housenum AND customer.postcode = address.postcode;";
		ArrayList<Customer> customers = new ArrayList<>();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				int cust_id = result.getInt("custID");
				String firstname = result.getString("first_name");
				String secondname = result.getString("second_name");
				String telephonenumber = result.getString("tel");
				String housenum = result.getString("housenum");
				String postcode = result.getString("postcode");
				String street = result.getString("street");
				String towncity = result.getString("towncity");
				String country = result.getString("country");

				Address addr = new Address(housenum, street, towncity, country, postcode);
				
				customers.add(new Customer(cust_id,firstname,secondname,addr,telephonenumber));
			}
		} catch(SQLException e) {
			msg(e.getMessage());
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return customers;
	}
	
	public ArrayList<Order> getAllOrders(){
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM orders;";
		ArrayList<Order> orders = new ArrayList<>();
		ArrayList<Book> orderlines = new ArrayList<>();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				int orderID = result.getInt("orderID");
				int custID = result.getInt("custID");
				String comp = result.getString("complete");
				Boolean complete; 

				Customer c = getCustomer(custID);
				complete = comp.toLowerCase().equals("Y".toLowerCase());
				
				orderlines = getAllOrderLines(orderID); //use function created below to get individual line items for the items arrayList from the order_Lines table
				
				orders.add(new Order(orderID, orderlines, c, complete));
				
			}
		} catch(SQLException e) {
			msg(e.getMessage());
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return orders;
	}
	
	public ArrayList<Book> getAllOrderLines(int orderID){
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = ("SELECT * FROM order_line WHERE orderID =" + orderID + ";");
		ArrayList<Book> orderlines = new ArrayList<>();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				int bookID = result.getInt("ID");
				Book b = getBook(bookID);
				orderlines.add(b);
			}
		} catch(SQLException e) {
			msg(e.getMessage());
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return orderlines;
	}
	
	public Book getBook(int id) {
		Book temp = null;
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM books WHERE ID =" + id + ";";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeQuery(query);
			while (result.next()) {
				int book_id = result.getInt("ID");
				String title = result.getString("Title");
				String author = result.getString("Author");
				int year = result.getInt("Year");
				int edition = result.getInt("Edition");
				String publisher = result.getString("Publisher");
				String isbn = result.getString("ISBN");
				String cover = result.getString("Cover");
				String condition = result.getString("Condition");
				int price = result.getInt("Price");
				String notes = result.getString("Notes");
				temp = new Book(book_id,title,author,year,edition,publisher,isbn,cover,condition,price,notes);
			}
		}catch(SQLException e) {
			msg(e.getMessage());
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return temp;
	}
	
	public Customer getCustomer(int id) {
		Customer temp = null;
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM customer LEFT JOIN address ON customer.housenum = address.housenum AND customer.postcode = address.postcode WHERE custID=" + id + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeQuery(query);
			while (result.next()) {
				int cust_id = result.getInt("custID");
				String firstname = result.getString("first_name");
				String secondname = result.getString("second_name");
				String telephonenumber = result.getString("tel");
				String housenum = result.getString("housenum");
				String postcode = result.getString("postcode");
				String street = result.getString("street");
				String towncity = result.getString("towncity");
				String country = result.getString("country");
				Address addr = new Address(housenum, street, towncity, country, postcode);			
				temp = new Customer(cust_id,firstname,secondname,addr,telephonenumber);
			}
		}catch(SQLException e) {
			msg(e.getMessage());
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return temp;
	}
	
	public Address getAddress(String housenum, String postcode) {
		Address temp = null;
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM address WHERE housenum LIKE '" + housenum + "' AND postcode LIKE '" + postcode + "';";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeQuery(query);
			while (result.next()) {
				String housenumb = result.getString("housenum");
				String postcodeb = result.getString("postcode");
				String street = result.getString("street");
				String towncity = result.getString("towncity");
				String country = result.getString("country");
				Address addr = new Address(housenumb, street, towncity, country, postcodeb);			
			}
		}catch(SQLException e) {
			msg(e.getMessage());
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return temp;
	}
	
	public ArrayList<Book> searchBook(Book in) {
		Customer temp = null;
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		ArrayList<Book> books = new ArrayList<>();
		
		String IDsearch = "";
		String titlesearch = "";
		String authorsearch = "";
		String editionsearch = "";
		String publishersearch = "";
		String isbnsearch = "";

		int nonblank = 0;
		
		if(in.getBook_id() != 999999999) {
			IDsearch = "ID = " + in.getBook_id();
			nonblank++;
		}
		
		if(!in.getTitle().equals(" ")) {
			titlesearch = "LOWER(Title) LIKE LOWER('%" + in.getTitle() + "%')";
			nonblank++;
		}
		
		if(!in.getAuthor().equals(" ")) {
			authorsearch = "LOWER(Author) LIKE LOWER('%" + in.getAuthor() + "%')";
			nonblank++;
		}
		
		if(in.getEdition() != 999999999) {
			editionsearch = "Edition = " + in.getEdition();
			nonblank++;
		}
		
		if(!in.getPublisher().equals(" ")) {
			publishersearch = "LOWER(Publisher) LIKE LOWER('" +  in.getPublisher() + "')";
			nonblank++;
		}
		
		if(!in.getIsbn().equals(" ")) {
			isbnsearch = "LOWER(ISBN) LIKE LOWER('" +  in.getIsbn() + "')";
			nonblank++;
		}
		
		String[] ands = new String[5];
		
		int j = 0;
		for(int i = nonblank;i>0;i--) {
			if(i>=2) {
				ands[j] = " AND ";
				j++;
			}
		}
		
		int fill = 6 - nonblank;
		int max = 4;
		
		for(int i = fill;i>0;i--) {
			ands[max-(i-1)] = "";
		}
		
		String query =  "SELECT * FROM books WHERE " +
						IDsearch +
						ands[0] +
						titlesearch +
						ands[1] +
						authorsearch +
						ands[2] +
						editionsearch +
						ands[3] +
						publishersearch +
						ands[4] +
						isbnsearch +
						";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeQuery(query);
			while (result.next()) {
				int book_id = result.getInt("ID");
				String title = result.getString("Title");
				String author = result.getString("Author");
				int year = result.getInt("Year");
				int edition = result.getInt("Edition");
				String publisher = result.getString("Publisher");
				String isbn = result.getString("ISBN");
				String cover = result.getString("Cover");
				String condition = result.getString("Condition");
				int price = result.getInt("Price");
				String notes = result.getString("Notes");
				books.add(new Book(book_id,title,author,year,edition,publisher,isbn,cover,condition,price,notes));
			}
		}catch(SQLException e) {
			msg(e.getMessage());
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return books;
	}
	
	
	public ArrayList<Customer> searchCustomer(Customer in) {
		Customer temp = null;
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		ArrayList<Customer> customers = new ArrayList<>();
		String IDsearch = "";
		String firstnamesearch = "";
		String secondnamesearch = "";
		String postcodesearch = "";
		String telnosearch = "";
		int nonblank = 0;
		
		if(in.getCustID() != 999999999) {
			IDsearch = "custID = " + in.getCustID();
			nonblank++;
		}
		
		if(!in.getFirstName().equals(" ")) {
			firstnamesearch = "LOWER(first_name) LIKE LOWER('%" + in.getFirstName() + "%')";
			nonblank++;
		}
		
		if(!in.getSecondName().equals(" ")) {
			secondnamesearch = "LOWER(second_name) LIKE LOWER('%" + in.getSecondName() + "%')";
			nonblank++;
		}
		
		if(!in.getAddress().getPostcode().equals(" ")) {
			postcodesearch = "LOWER(postcode) LIKE LOWER('" + in.getAddress().getPostcode() + "')";
			nonblank++;
		}
		
		if(!in.getTelephoneNumber().equals(" ")) {
			telnosearch = "LOWER(tel) LIKE LOWER('" +  in.getTelephoneNumber() + "')";
			nonblank++;
		}
		
		String[] ands = new String[4];
		
		int j = 0;
		for(int i = nonblank;i>0;i--) {
			if(i>=2) {
				ands[j] = " AND ";
				j++;
			}
		}
		
		int fill = 5 - nonblank;
		int max = 3;
		
		for(int i = fill;i>0;i--) {
			ands[max-(i-1)] = "";
		}
		
		String query =  "SELECT * FROM customer WHERE " +
						IDsearch +
						ands[0] +
						firstnamesearch +
						ands[1] +
						secondnamesearch +
						ands[2] +
						postcodesearch +
						ands[3] +
						telnosearch +
						";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeQuery(query);
			while (result.next()) {
				int cust_id = result.getInt("custID");
				String firstname = result.getString("first_name");
				String secondname = result.getString("second_name");
				String telephonenumber = result.getString("tel");
				String housenum = result.getString("housenum");
				String postcode = result.getString("postcode");
				Address addr = getAddress(housenum, postcode);			
				temp = new Customer(cust_id,firstname,secondname,addr,telephonenumber);
				customers.add(temp);
			}
		}catch(SQLException e) {
			msg(e.getMessage());
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		for(Customer c : customers)
			msg(c.toString());
		return customers;
	}
	
	
	
	public Order getOrder(int id) {
		Order temp = null;
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM orders WHERE orderID=" + id + ";";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeQuery(query);
			while (result.next()) {
				int cust_id = result.getInt("custID");
				int order_id = result.getInt("orderID");
				String orderstatus = result.getString("complete");
				boolean complete = orderstatus.toLowerCase().equals("Y".toLowerCase());
				Customer c = getCustomer(cust_id);
				temp = new Order(order_id, c, complete);
			}
		}catch(SQLException e) {
			msg(e.getMessage());
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return temp;
	}
	
	public Boolean deleteBook(int book_id){
		Connection dbConnection = null;
		Statement statement = null;
		int result = 0;
		String query = "DELETE FROM books WHERE ID = " + book_id + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeUpdate(query);
		} catch(SQLException e){ msg(e.getMessage()); }
		finally {
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return (result == 1); 
	}
	
	public Boolean deleteCustomer(int id){
		Connection dbConnection = null;
		Statement statement = null;
		int result = 0;
		String query = "DELETE FROM customer WHERE custID = " + id + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg(query);
			result = statement.executeUpdate(query);
		} catch(SQLException e){ msg(e.getMessage()); }
		finally {
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return (result == 1); 
	}
	
	public Boolean deleteOrder(int id){
		Connection dbConnection = null;
		Statement statement = null;
		int result = 0;
		String query = "DELETE FROM orders WHERE orderID = " + id + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeUpdate(query);
		} catch(SQLException e){ msg(e.getMessage()); }
		finally {
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return (result == 1); 
	}
	
	public boolean insertBook(Book in){
		Connection dbConnection = null;
		Statement statement = null;
		String update = "INSERT INTO books (Title, Author, Year, Edition, Publisher, ISBN, Cover, Condition, Price, Notes) " + 
		"VALUES ('"+
				in.getTitle()+"','"+
				in.getAuthor()+"',"+
				in.getYear()+","+
				in.getEdition()+",'"+
				in.getPublisher()+"','"+
				in.getIsbn()+"','"+
				in.getCover()+"','"+
				in.getCondition()+"',"+
				in.getPrice()+",'"+
				in.getNotes()+
				"');";
		boolean ok = false;
			try {
					dbConnection = getDBConnection();
					statement = dbConnection.createStatement();
					msg("DBQuery: " + update);
					statement.executeUpdate(update);
					ok = true;
				} catch (SQLException e) {
					msg(e.getMessage());
				} finally {
					if (statement != null)
						closeStatement(statement);
					if (dbConnection != null)
						closeConnection(dbConnection);
				}
			return ok;
	}
	
	public boolean insertCustomer(Customer in){
		Connection dbConnection = null;
		Statement statement = null;
		String update = "INSERT INTO customer (first_name, second_name, tel, housenum, postcode) " + 
		"VALUES ('"+
				in.getFirstName()+"','"+
				in.getSecondName()+"','"+
				in.getTelephoneNumber()+"','"+
				in.getAddress().getHousenum()+"','"+
				in.getAddress().getPostcode()+
				"');";
		boolean ok = false;
			try {
					dbConnection = getDBConnection();
					statement = dbConnection.createStatement();
					msg("DBQuery: " + update);
					statement.executeUpdate(update);
					ok = true;
				} catch (SQLException e) {
					msg(e.getMessage());
				} finally {
					if (statement != null)
						closeStatement(statement);
					if (dbConnection != null)
						closeConnection(dbConnection);
				}
			return ok;
	}
	
	public boolean insertAddress(Address in){
		Connection dbConnection = null;
		Statement statement = null;
		String update = "INSERT INTO address (housenum, street, towncity, country, postcode) " + 
		"VALUES ('"+
				in.getHousenum()+"','"+
				in.getStreet()+"','"+
				in.getTowncity()+"','"+
				in.getCountry()+"','"+
				in.getPostcode()+
				"');";
		boolean ok = false;
			try {
					dbConnection = getDBConnection();
					statement = dbConnection.createStatement();
					msg("DBQuery: " + update);
					statement.executeUpdate(update);
					ok = true;
				} catch (SQLException e) {
					msg(e.getMessage());
				} finally {
					if (statement != null)
						closeStatement(statement);
					if (dbConnection != null)
						closeConnection(dbConnection);
				}
			return ok;
	}
	
	public boolean insertOrder(Order in){
		Connection dbConnection = null;
		Statement statement = null;
		String update = "INSERT INTO orders (custID, complete) " + 
		"VALUES ('"+
				in.getCustomer().getCustID() +"','" +
				"N"+
				"');";
		boolean ok = false;
			try {
					dbConnection = getDBConnection();
					statement = dbConnection.createStatement();
					msg("DBQuery: " + update);
					statement.executeUpdate(update);
					ok = true;
				} catch (SQLException e) {
					msg(e.getMessage());
				} finally {
					if (statement != null)
						closeStatement(statement);
					if (dbConnection != null)
						closeConnection(dbConnection);
				}
			return ok;
	}
	
	public boolean insertOrderLineItem(int orderid, int bookid){
		Connection dbConnection = null;
		Statement statement = null;
		String update = "INSERT INTO order_line (orderID, ID) " + 
		"VALUES ('"+
				orderid +"','" +
				bookid +
				"');";
		boolean ok = false;
			try {
					dbConnection = getDBConnection();
					statement = dbConnection.createStatement();
					msg("DBQuery: " + update);
					statement.executeUpdate(update);
					ok = true;
				} catch (SQLException e) {
					msg(e.getMessage());
				} finally {
					if (statement != null)
						closeStatement(statement);
					if (dbConnection != null)
						closeConnection(dbConnection);
				}
			return ok;
	}
	
	public Boolean updateBook(Book book) { 
		Connection dbConnection = null;
		Statement statement = null;
		String query = "UPDATE books " +
				"SET Title = '"+ book.getTitle() + "'," + 
				"Author= '" + book.getAuthor() + "'," + 
				"Year= '" + book.getYear() + "'," + 
				"Edition= '" + book.getEdition() + "'," +
				"Publisher= '" + book.getPublisher() + "'," +
				"ISBN= '" + book.getIsbn() + "'," +
				"Cover= '" + book.getCover() + "'," +
				"Condition= '" + book.getCondition() + "'," +
				"Price= '" + book.getPrice() + "'," +
				"Notes= '" + book.getNotes() + "'" +
				" WHERE ID = " + book.getBook_id()
				+ ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			msg(e.getMessage());
			return false;
		} finally {
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return true;
	}
	
	public Boolean updateCustomer(Customer customer) { 
		Connection dbConnection = null;
		Statement statement = null;
		String query = "UPDATE customer " +
				"SET first_name= '" + customer.getFirstName() + "'," + 
				"second_name= '" + customer.getSecondName() + "'," + 
				"tel= '" + customer.getTelephoneNumber() + "'," +
				"housenum= '" + customer.getAddress().getHousenum() + "'," +
				"postcode= '" + customer.getAddress().getPostcode() + "'," +
				" WHERE custID = " + customer.getCustID()
				+ ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			msg(e.getMessage());
			return false;
		} finally {
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return true;
	}
	
	public Boolean updateOrder(Order order) { 
		Connection dbConnection = null;
		Statement statement = null;
		String complete;
		if(order.isComplete())
			complete = "Y";
		else
			complete = "N";
		String query = "UPDATE orders " +
				"SET complete= '" + complete + "'" +
				" WHERE orderID = " + order.getOrderID()
				+ ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			msg(e.getMessage());
			return false;
		} finally {
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return true;
	}
	
	public Boolean deleteOrderLine(int bookid, int orderid) { 
		Connection dbConnection = null;
		Statement statement = null;
		String query = "DELETE FROM order_line WHERE orderID = " + orderid + " AND ID = " + bookid + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			msg(e.getMessage());
			return false;
		} finally {
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null)
				closeConnection(dbConnection);
		}
		return true;
	}
	
	
	public Boolean usernameMatch(String username) {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		Boolean exists;
		String query = "SELECT * FROM users WHERE username LIKE '" + username + "';";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeQuery(query);
			exists =  (result.getString("username").equals(username));
		} catch (SQLException e) {
			msg(e.getMessage());
			return false;
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null) 
				closeConnection(dbConnection);
		} return exists;
	}
	
	public Boolean passwordMatch(String username, String password) {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		Boolean match;
		String query = "SELECT * FROM users WHERE username LIKE '" + username + "';";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeQuery(query);
			String dbPassword = MD5.getMd5(result.getString("password"));
			match = (dbPassword.equals(password));
		} catch (SQLException e) {
			msg(e.getMessage());
			return false;
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null) 
				closeConnection(dbConnection);
		} return match;
	}
	
	public Boolean addressMatch(String housenum, String postcode) {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		Boolean match;
		String query = "SELECT * FROM address WHERE housenum LIKE '" + housenum + "' AND postcode LIKE '" + postcode + "';";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeQuery(query);
			match = (result.getString("housenum").toLowerCase().equals(housenum) && result.getString("postcode").toLowerCase().equals(postcode));
		} catch (SQLException e) {
			msg(e.getMessage());
			return false;
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null) 
				closeConnection(dbConnection);
		} return match;
	}
	
	public Boolean bookinorderMatch(int bookid) {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		Boolean match;
		String query = "SELECT * FROM order_line WHERE ID = " + bookid + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			msg("DBQuery: " + query);
			result = statement.executeQuery(query);
			match = (result.getString("ID").toLowerCase().equals(String.valueOf(bookid)));
		} catch (SQLException e) {
			msg(e.getMessage());
			return false;
		} finally {
			if (result != null)
				closeResultSet(result);
			if (statement != null)
				closeStatement(statement);
			if (dbConnection != null) 
				closeConnection(dbConnection);
		} return match;
	}

	
	private void closeResultSet(ResultSet result) {
		try {
			result.close();
		} catch (SQLException e) {
			msg(e.getMessage() );
		}
	}
	
	private void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			msg(e.getMessage() );
		}
	}
	
	private void closeConnection(Connection dbConnection) {
		try {
			dbConnection.close();
		} catch (SQLException e) {
			msg(e.getMessage() );
		}
	}

	static <T> void msg(T t) {
		System.out.println(t);
	}

}
