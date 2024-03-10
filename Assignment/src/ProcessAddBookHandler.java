import java.io.*;
import java.util.HashMap;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class ProcessAddBookHandler implements HttpHandler {

	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException { //figure out how to do without throwing exception
		
		msg("In AddBookProcessHandler");
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);
	    
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
	    
	    /*Line of thinking here - some fields are mandatory, some may be added later, if someone is adding something into
	     * the inventory of books and will look to update things after. Some fields e.g. price/edition may not be known at the time, 
	     * but book needs to be catalogued, so some fields can be blank. Title and Author should be the bare minimum required. 
	     */
	    
	    if(title.isEmpty()){
	    	he.getResponseHeaders().add("Location", "/addbook?err1");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if(author.isEmpty()) {
	    	he.getResponseHeaders().add("Location", "/addbook?err2");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    /* Next - the ISBN:
	     * First, need to make sure the input is numerical by parsing each char in the string to int using function
	     * created in Util
	     * Second; need to check the ISBN is either 10 or 13 characters long
	     * *Next check that the ISBN in valid using the function created in Util
	     */

	    if((!Util.testIsbnIsNum(isbn)) && (!isbn.isEmpty())) {
    		he.getResponseHeaders().add("Location", "/addbook?err3");
	    	he.sendResponseHeaders(302, 0);
	    	return;
    	}
	    
	    if((isbn.length() != 13 && isbn.length() != 10) && (!isbn.isEmpty())) {
	    	he.getResponseHeaders().add("Location", "/addbook?err4");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if ((!Util.isIsbnValid(isbn)) && (!isbn.isEmpty())) {
    		he.getResponseHeaders().add("Location", "/addbook?err5");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    /* Next, some values need to be ints so we need to ensure that these values can parse to number. 
	     * These are year, edition and price. 
	     * Can be blank, so we check they arent empty first
	     */
	    
	    if((Util.testStringToInt(year) == false) && (!year.isEmpty())) {
	    	he.getResponseHeaders().add("Location", "/addbook?err6");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if((Util.testStringToInt(edition) == false) && (!edition.isEmpty())) {
	    	he.getResponseHeaders().add("Location", "/addbook?err7");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    if((Util.testStringToInt(price) == false) && (!price.isEmpty())) {
	    	he.getResponseHeaders().add("Location", "/addbook?err8");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }
	    
	    /*Now we account for them being empty - we can't parse empty string to int so need to cover the error
	     * by ensuring that the value can never be empty
	     */
	    
	    int yearInt = 0;
	    int editionInt = 0;
	    int priceInt = 0;
	    
	    if(!year.isEmpty())
	    	yearInt = Integer.parseInt(year);
	    
	    if(!edition.isEmpty())
	    	editionInt = Integer.parseInt(edition);
	    
	    if(!price.isEmpty())
	    	priceInt = Integer.parseInt(price);
	    
	    /*Next - create the book from the collected values
	     * 
	     */
	    
	    Book b = new Book(title, author, yearInt, editionInt, publisher, isbn, cover, condition, priceInt, notes);
	    
		//BufferedWriter out = new BufferedWriter( new OutputStreamWriter(he.getResponseBody()));
	    
	    if(bookdao.insertBook(b) == true) { 
	    	//why does this call the function multiple times if I try to use out.write() from the above? 
	    	//can only get this to work if I redirect instead
	    	he.getResponseHeaders().add("Location", "/success?ab");
	    	he.sendResponseHeaders(302, 0);
	    } else { //dont think this should be reachable but should cover it just in case
	    	he.getResponseHeaders().add("Location", "/fail?ab");
	    	he.sendResponseHeaders(302, 0);
	    }
    }
	
	static <T> void msg(T t) {
		System.out.println(t);
	}

	
}
