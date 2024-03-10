import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

/*As with deleteBook - dont think i need a specific handler page?
 * Maybe could do with a confirmation page? i.e Are you sure?
 */

public class ProcessDeleteCustomerHandler implements HttpHandler { 

	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException {
		
		msg("In DeleteCustomerProcessHandler");
		
		/*custID to delete is passed through the URL in the form "?id="id, so need to be able to collect id after =. 
		 * Dont need to use requestStringToMap as only one value needs returning, instead made function
		 * called getIdFromUrl in Util which is simpler than requestStringToMap for this case
		 */
		
		String URL = he.getRequestURI().toString();
		String id = Util.getIdFromUrl(URL);
		int idInt = Integer.parseInt(id);
		boolean deleted = bookdao.deleteCustomer(idInt);
		
		if(deleted) {
			he.getResponseHeaders().add("Location", "/success?dc");
	    	he.sendResponseHeaders(302, 0);
	    	return; 
		} else {
			he.getResponseHeaders().add("Location", "/fail?dc");
	    	he.sendResponseHeaders(302, 0);
	    	return;
		}
		
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}
}

