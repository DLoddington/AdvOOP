import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

/*As with deleteBook - dont think i need a specific handler page?
 * Maybe could do with a confirmation page? i.e Are you sure?
 */

public class ProcessDeleteOrderHandler implements HttpHandler { 

	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException {
		
		msg("In DeleteOrderProcessHandler");
		
		/*custID to delete is passed through the URL in the form "?id="id, so need to be able to collect id after =. 
		 * Dont need to use requestStringToMap as only one value needs returning, instead made function
		 * called getIdFromUrl in Util which is simpler than requestStringToMap for this case
		 */
		
		String URL = he.getRequestURI().toString();
		String id = Util.getIdFromUrl(URL);
		int idInt = Integer.parseInt(id);
		boolean deleted = bookdao.deleteOrder(idInt);
		
		if(deleted) {
			he.getResponseHeaders().add("Location", "/success?do");
	    	he.sendResponseHeaders(302, 0);
	    	return; 
		} else {
			he.getResponseHeaders().add("Location", "/fail?do");
	    	he.sendResponseHeaders(302, 0);
	    	return;
		}
		
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}
}

