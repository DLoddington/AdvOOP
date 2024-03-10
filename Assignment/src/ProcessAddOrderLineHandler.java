import java.io.*;
import java.util.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class ProcessAddOrderLineHandler implements HttpHandler {

	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException { //figure out how to do without throwing exception
		
		msg("In AddOrderLineProcessHandler");
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);
	    
	    String orderid = map.get("orderid");
	    String bookid = map.get("bookid");
	    
	    /*Line of thinking here - some fields are mandatory, some may be added later, if someone is adding a customer into the db
	     * there should be some bare minimum information - name(both), house num, postcode should be minimum.  
	     */
	    
	    if(bookid.isEmpty()){
	    	he.getResponseHeaders().add("Location", "/addorderline?err1id=" + orderid);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(!Util.testStringToInt(bookid)) {
	    	he.getResponseHeaders().add("Location", "/addorderline?err2id=" + orderid);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    int bookidAsInt = Integer.parseInt(bookid);
	    int orderidAsInt = Integer.parseInt(orderid);
	    
	    if(bookdao.getBook(bookidAsInt) == null) {
	    	he.getResponseHeaders().add("Location", "/addorderline?err3id=" + orderid);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(bookdao.bookinorderMatch(bookidAsInt)) { //make sure book doesnt already exist against an order
	    	he.getResponseHeaders().add("Location", "/addorderline?err4id=" + orderid);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    
	    if(bookdao.insertOrderLineItem(orderidAsInt, bookidAsInt)) { 
	    	he.getResponseHeaders().add("Location", "/success?olaid=" + orderidAsInt);
	    	he.sendResponseHeaders(302, 0);
	    } else { //dont think this should be reachable but should cover it just in case
	    	he.getResponseHeaders().add("Location", "/fail?ola");
	    	he.sendResponseHeaders(302, 0);
	    }
    }
	
	static <T> void msg(T t) {
		System.out.println(t);
	}

	
}
