import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class ProcessEditBookHandler implements HttpHandler {

static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException { 
		
		/*Going to look very similar to adding a book - a lot of the checks required will be the same
		 * output will differ as its not the same command
		 */
		
		
		msg("In EditBookProcessHandler");
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);
	    msg("test");
	    
	    String id = map.get("id");
	    String title = map.get("title");
	    String author = map.get("author");
	    String year = map.get("year");
	    String edition = map.get("edition");
	    String publisher = map.get("publisher");
	    String isbn = map.get("isbn");
	    String cover = map.get("cover");
	    String condition = map.get("condition");
	    String price = map.get("price");
	    String notes = map.get("notes");
	    
	    /*Notes for error reasoning are in ProcessAddBookHandler - all errors should be the same in this case
	     */
	    
	    if(title.isEmpty()){
	    	he.getResponseHeaders().add("Location", "/editbook?err1id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(author.isEmpty()) {
	    	he.getResponseHeaders().add("Location", "/editbook?err2id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if((!Util.testIsbnIsNum(isbn)) && (!isbn.isEmpty())) {
    		he.getResponseHeaders().add("Location", "/editbook?err3id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
    	}
	    
	    if((isbn.length() != 13 && isbn.length() != 10) && (!isbn.isEmpty())) {
	    	he.getResponseHeaders().add("Location", "/editbook?err4id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if ((!Util.isIsbnValid(isbn)) && (!isbn.isEmpty())) {
    		he.getResponseHeaders().add("Location", "/editbook?err5id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if((Util.testStringToInt(year) == false) && (!year.isEmpty())) {
	    	he.getResponseHeaders().add("Location", "/editbook?err6id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if((Util.testStringToInt(edition) == false) && (!edition.isEmpty())) {
	    	he.getResponseHeaders().add("Location", "/editbook?err7id=" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if((Util.testStringToInt(price) == false) && (!price.isEmpty())) {
	    	he.getResponseHeaders().add("Location", "/editbook?err8id" + id);
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    int yearInt = 0;
	    int editionInt = 0;
	    int priceInt = 0;
	    
	    if(!year.isEmpty())
	    	yearInt = Integer.parseInt(year);
	    
	    if(!edition.isEmpty())
	    	editionInt = Integer.parseInt(edition);
	    
	    if(!price.isEmpty())
	    	priceInt = Integer.parseInt(price);
	    
	    Book b = new Book(Integer.parseInt(id),title, author, yearInt, editionInt, publisher, isbn, cover, condition, priceInt, notes);
	    
	    if(bookdao.updateBook(b) == true) { 
	    	//why does this call the function multiple times if I try to use out.write() from the above? 
	    	//can only get this to work if I redirect instead
	    	he.getResponseHeaders().add("Location", "/success?eb");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    } else { //dont think this should be reachable but should cover it just in case
	    	he.getResponseHeaders().add("Location", "/fail?eb");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}
	
}
