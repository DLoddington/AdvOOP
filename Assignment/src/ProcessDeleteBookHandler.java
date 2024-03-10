import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

/*Dont think I need a DeleteBookHandler class for this, can all be done from a single click from the
 * book table. Maybe could do with a confirmation page? i.e Are you sure?
 */

public class ProcessDeleteBookHandler implements HttpHandler { 

	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException {
		
		msg("In DeleteBookProcessHandler");
		
		/*book_id to delete is passed through the URL in the form "?id="id, so need to be able to collect id after =. 
		 * Dont need to use requestStringToMap as only one value needs returning, instead made function
		 * called getIdFromUrl in Util which is simpler than requestStringToMap for this case
		 */
		
		String URL = he.getRequestURI().toString();
		String id = Util.getIdFromUrl(URL);
		int idInt = Integer.parseInt(id);
		boolean deleted = bookdao.deleteBook(idInt);
		
		if(deleted) {
			he.getResponseHeaders().add("Location", "/success?db");
	    	he.sendResponseHeaders(302, 0);
	    	return; 
		} else {
			he.getResponseHeaders().add("Location", "/fail?db");
	    	he.sendResponseHeaders(302, 0);
	    	return;
		}
		
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}
}

