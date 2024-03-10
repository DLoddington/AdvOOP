import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class ProcessEditOrderStatusHandler implements HttpHandler {

static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException { 
		
		msg("In EditOrderStausProcessHandler");
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);
	    msg("test");
	    
	    String orderID = map.get("orderID");
	    String orderstatus = map.get("orderstatus");
	    int orderIDAsInt = Integer.parseInt(orderID);
	    
	    /*Notes for error reasoning are in ProcessAddBookHandler - all errors should be the same in this case
	     */
	    
	    if(orderstatus.isEmpty()){
	    	he.getResponseHeaders().add("Location", "/editorderstats?err1id=" + orderIDAsInt);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    boolean complete = orderstatus.toLowerCase().equals("Complete".toLowerCase());
	    Order o = bookdao.getOrder(orderIDAsInt);
	    Customer c = o.getCustomer();
	    
	    Order updated = new Order(orderIDAsInt, c, complete);
	    
	    if(bookdao.updateOrder(updated) == true) { 
	    	he.getResponseHeaders().add("Location", "/success?eos");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    } else { 
	    	he.getResponseHeaders().add("Location", "/fail?eos");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}
	
}