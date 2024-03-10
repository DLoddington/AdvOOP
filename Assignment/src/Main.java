import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;

public class Main {

	static final private int PORT = 8080;
	
	public static void main(String[] args) {
		
		msg("Hello world!");
		    
		HttpServer server = null;  
		
		try{
			server = HttpServer.create(new InetSocketAddress(PORT),0);
		} catch(IOException e) {
			msg(e.getMessage());
		};
		
		server.createContext("/", new RootHandler() );
		server.createContext("/login", new LoginHandler() );
		server.createContext("/loginaction", new ProcessLoginHandler() );
		server.createContext("/adminoptions", new AdminOptionsHandler() );
		server.createContext("/displaybooks", new BooksHandler() );
	    server.createContext("/addbook", new AddBookHandler() );
	    server.createContext("/addbookaction", new ProcessAddBookHandler() );
	    server.createContext("/deletebookaction", new ProcessDeleteBookHandler() );
	    server.createContext("/searchbooks", new SearchBookHandler() );
	    server.createContext("/searchbooksaction", new ProcessSearchBookHandler() );
	    server.createContext("/editbook", new EditBookHandler() );
	    server.createContext("/editbookaction", new ProcessEditBookHandler() );
	    server.createContext("/success", new SuccessHandler() );
		server.createContext("/fail", new FailHandler() );
		server.createContext("/displaycustomers", new CustomersHandler() );
		server.createContext("/addcustomer", new AddCustomerHandler() );
		server.createContext("/addcustomeraction", new ProcessAddCustomerHandler() );
		server.createContext("/deletecustomeraction", new ProcessDeleteCustomerHandler() );
		server.createContext("/editcustomer", new EditCustomerHandler() );
		server.createContext("/editcustomeraction", new ProcessEditCustomerHandler() );
		server.createContext("/displayorders", new OrdersHandler() );
		server.createContext("/searchcustomers", new SearchCustomerHandler() );
		server.createContext("/searchcustomersaction", new ProcessSearchCustomerHandler() );
		server.createContext("/addorder", new AddOrderHandler() );
		server.createContext("/addorderaction", new ProcessAddOrderHandler() );
		server.createContext("/addorderline", new AddOrderLineHandler() );
		server.createContext("/addorderlineaction", new ProcessAddOrderLineHandler() );
		server.createContext("/deleteorder", new ProcessDeleteOrderHandler() );
		server.createContext("/editorderstatus", new EditOrderStatusHandler() );
		server.createContext("/editorderstatusaction", new ProcessEditOrderStatusHandler() );
		server.createContext("/editorderitems", new EditOrderItemsHandler() );
		server.createContext("/editorderitemsaction", new ProcessEditOrderItemsHandler() );
	    server.setExecutor(null);
	    server.start();
	    msg("The server is listening on port " + PORT);
	    
	    //Controller c = new Controller();
	    //c.testFunction();
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}

}
