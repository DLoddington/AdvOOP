import java.io.*;
import java.util.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class ProcessAddOrderHandler implements HttpHandler {

	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException { //figure out how to do without throwing exception
		
		msg("In AddOrderProcessHandler");
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);
	    
	    String custid = map.get("custid");
	    String bookid = map.get("bookid");
	    
	    
	    /*Line of thinking here - some fields are mandatory, some may be added later, if someone is adding a customer into the db
	     * there should be some bare minimum information - name(both), house num, postcode should be minimum.  
	     */
	    
	    if(bookid.isEmpty()){
	    	he.getResponseHeaders().add("Location", "/addorder?err1");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(!Util.testStringToInt(bookid)) {
	    	he.getResponseHeaders().add("Location", "/addorder?err2");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    int bookidAsInt = Integer.parseInt(bookid);
	    
	    if(bookdao.getBook(bookidAsInt) == null) {
	    	he.getResponseHeaders().add("Location", "/addorder?err3");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(custid.isEmpty()){
	    	he.getResponseHeaders().add("Location", "/addorder?err4");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(!Util.testStringToInt(custid)) {
	    	he.getResponseHeaders().add("Location", "/addorder?err5");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    int custidAsInt = Integer.parseInt(custid);
	    
	    if(bookdao.getCustomer(custidAsInt) == null) {
	    	he.getResponseHeaders().add("Location", "/addorder?err6");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(bookdao.bookinorderMatch(bookidAsInt)) {
	    	he.getResponseHeaders().add("Location", "/addorder?err7");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    Customer c = bookdao.getCustomer(custidAsInt);
	    
	    Order o = new Order(c);
	    
	    ArrayList<Order> orders = bookdao.getAllOrders();
	    int ordersSize = orders.size();
	    int lastIdGenerated = orders.get(ordersSize -1).getOrderID();
	    int newId = lastIdGenerated + 1;
	    
	    if(bookdao.insertOrder(o) && bookdao.insertOrderLineItem(custidAsInt, bookidAsInt)) { 
	    	he.getResponseHeaders().add("Location", "/success?aoid=" + newId);
	    	he.sendResponseHeaders(302, 0);
	    } else { //dont think this should be reachable but should cover it just in case
	    	he.getResponseHeaders().add("Location", "/fail?ao");
	    	he.sendResponseHeaders(302, 0);
	    }
    }
	
	static <T> void msg(T t) {
		System.out.println(t);
	}

	
}
