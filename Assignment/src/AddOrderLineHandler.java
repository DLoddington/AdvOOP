import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class AddOrderLineHandler implements HttpHandler {

	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException {
	
		he.sendResponseHeaders(200,0); 
		
		/* Order Id will be auto generated on entry into the table
		 * Customer Id is collected from the URI - will not be accessible otherwise in web site mapping
		 * complete will be auto set to no on creation of order
		 * leaves only information on the books being required, with a minimum of 1 product needed
		 */

		/*As with delete book process, the id to edit is passed through URL, so need to fetch with getIdFromUrl
		 * function that we made, then find the associated book
		 */
		
		String URI = he.getRequestURI().toString();
		String id = Util.getIdFromUrl(URI);
		int idInt = Integer.parseInt(id);
		
		Order o = bookdao.getOrder(idInt);
		
	    String booksError = "";
		
		/*Error statements are designed to provide some indication where input may have been wrong in first place
		 * in ideal world, inputs would be pre-populated with old responses on redirect, but difficult to do without servlets
		 */
		
		if(he.getRequestURI().toString().contains("/addorderline?err1"))
			booksError = "At least one book is required to create an order - please try again";
		
		if(he.getRequestURI().toString().contains("/addorderline?err2"))
			booksError = "Book ID must me a number with no letters, symbols or spaces - please try again";
		
		if(he.getRequestURI().toString().contains("/addorderline?err3"))
			booksError = "Book does not exist, please try again";
		
		if(he.getRequestURI().toString().contains("/addorderline?err4"))
			booksError = "That book is already part of this order, please add a new book";
		
		Customer c = bookdao.getCustomer(idInt);
		
		BufferedWriter out = new BufferedWriter(  
	            new OutputStreamWriter(he.getResponseBody() ));
		
		out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Add Book to an Order</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Add book to an existing Order</h1>" +
		    "<p>Order ID: " + idInt + " Customer ID: " + o.getCustomer().getCustID() + "</p>" +
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>Field</th>" +
		    "    <th>Entry</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>" +
		    "<form method=\"Post\" action=\"/addorderlineaction\">"
		 );
		
		 out.write(
			"  <tr>"       +
    		"	 <td>Book ID</td>" +
    		"    <td><input name=\"bookid\">" + booksError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"<input name=\"orderid\" value=\"" + idInt + "\" type=\"hidden\">" +
      		"</tbody>" +
			"</table>" +
			"<input type=\"submit\" value=\"Submit\">  "+
  	      	"</form>" + 
			"<br><br>" +
  	      	"<h2>Return to admin options</h2>" +
			"<a href=\"/adminoptions\"> Click here</a>" +
			"<br><br>" +
			"</div>" +
			"</body>" +
			"</html>"
		);
	    
		out.close();    
		
	}
	
}
