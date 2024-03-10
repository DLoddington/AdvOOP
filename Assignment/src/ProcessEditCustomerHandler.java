import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class ProcessEditCustomerHandler implements HttpHandler {

static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException { 
		
		/*Going to look very similar to adding a book - a lot of the checks required will be the same
		 * output will differ as its not the same command
		 */
		
		
		msg("In EditCustomerProcessHandler");
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);
	    
	    String id = map.get("ID");
	    String firstname = map.get("firstname");
	    String secondname = map.get("secondname");
	    String housenum = map.get("housenum");
	    String street = map.get("street");
	    String towncity = map.get("towncity");
	    String country = map.get("country");
	    String postcode = map.get("postcode");
	    String telephonenumber = map.get("telephonenumber");
	    
	    /*Notes for error reasoning are in ProcessAddBookHandler - all errors should be the same in this case
	     */
	    
	    if(firstname.isEmpty()){
	    	he.getResponseHeaders().add("Location", "/editbook?err1id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(secondname.isEmpty()) {
	    	he.getResponseHeaders().add("Location", "/editbook?err2id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(housenum.isEmpty()) {
	    	he.getResponseHeaders().add("Location", "/editbook?err2id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(secondname.isEmpty()) {
	    	he.getResponseHeaders().add("Location", "/editbook?err2id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    Address addr = new Address(housenum, street, towncity, country, postcode);
	    Customer c = new Customer(firstname, secondname, addr, telephonenumber);
	    
	    if(bookdao.updateCustomer(c)) { 
	    	if(!bookdao.addressMatch(housenum, postcode)) {
	    		bookdao.insertAddress(addr);
	    	}
	    	he.getResponseHeaders().add("Location", "/success?ec");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    } else { //dont think this should be reachable but should cover it just in case
	    	he.getResponseHeaders().add("Location", "/fail?ec");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}
	
}