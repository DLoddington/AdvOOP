import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class ProcessEditOrderItemsHandler implements HttpHandler {

static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException { 
		
		
		msg("In EditOrderProcessHandler");
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);
	    
	    String orderid = map.get("orderID");
	    String numbooks = map.get("bookcount");
	    int numbooksAsInt = Integer.parseInt(numbooks);
	    int orderidAsInt = Integer.parseInt(orderid);
	    
	    /*need to make a loop that iterates through all the submitted books checking if they should be removed and action as appropriate
	     * 
	     */
	    for(int i = 0; i < numbooksAsInt;i++) {
	    	String itemstatussearchterm = "itemstatus" + i;
	    	String itemstatus = map.get(itemstatussearchterm);
	    	if(itemstatus.toLowerCase().equals("remove".toLowerCase()) ) {
	    		String booksearchterm = "bookID" + i;
	    		String book_id = map.get(booksearchterm);
	    		Book b = bookdao.getBook(Integer.parseInt(book_id));
	    		int bookidAsInt = b.getBook_id();
	    		bookdao.deleteOrderLine(bookidAsInt, orderidAsInt);
	    	}
	    	
	    }
	    
	    he.getResponseHeaders().add("Location", "/success?eol");
	    he.sendResponseHeaders(302, 0);
	    
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}
	
}