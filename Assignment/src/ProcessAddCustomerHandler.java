import java.io.*;
import java.util.HashMap;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class ProcessAddCustomerHandler implements HttpHandler {

	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException { //figure out how to do without throwing exception
		
		msg("In AddCustomerProcessHandler");
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);
	    
	    String firstname = map.get("firstname");
	    String secondname = map.get("secondname");
	    String housenum = map.get("housenum");
	    String street = map.get("street");
	    String towncity = map.get("towncity");
	    String country = map.get("country");
	    String postcode = map.get("postcode");
	    String telephonenumber = map.get("telephonenumber");
	    
	    /*Line of thinking here - some fields are mandatory, some may be added later, if someone is adding a customer into the db
	     * there should be some bare minimum information - name(both), house num, postcode should be minimum.  
	     */
	    
	    if(firstname.isEmpty()){
	    	he.getResponseHeaders().add("Location", "/addcustomer?err1");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(secondname.isEmpty()) {
	    	he.getResponseHeaders().add("Location", "/addcustomer?err2");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(housenum.isEmpty()) {
	    	he.getResponseHeaders().add("Location", "/addcustomer?err3");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(postcode.isEmpty()) {
	    	he.getResponseHeaders().add("Location", "/addcustomer?err4");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    Address addr = new Address(housenum, street, towncity, country, postcode);
	    
	    Customer c = new Customer(firstname, secondname, addr, telephonenumber);
	    
		//BufferedWriter out = new BufferedWriter( new OutputStreamWriter(he.getResponseBody()));
	    
    	if(!bookdao.addressMatch(housenum, postcode)) { //add address into table first to maintain referential integrity
    		bookdao.insertAddress(addr);
    	}
	    
	    if(bookdao.insertCustomer(c)) { 
	    	he.getResponseHeaders().add("Location", "/success?ac");
	    	he.sendResponseHeaders(302, 0);
	    } else { //dont think this should be reachable but should cover it just in case
	    	he.getResponseHeaders().add("Location", "/fail?ac");
	    	he.sendResponseHeaders(302, 0);
	    }
    }
	
	static <T> void msg(T t) {
		System.out.println(t);
	}

	
}
