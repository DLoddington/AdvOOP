import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class AddOrderHandler implements HttpHandler {

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
	
	    String booksError = "";
	    String custError = "";
	    String custInput = "";
		
		/*Error statements are designed to provide some indication where input may have been wrong in first place
		 * in ideal world, inputs would be pre-populated with old responses on redirect, but difficult to do without servlets
		 */
		
		if(he.getRequestURI().toString().contains("/addorder?err1"))
			booksError = "At least one book is required to create an order - please try again";
		
		if(he.getRequestURI().toString().contains("/addorder?err2"))
			booksError = "Book ID must be a number with no letters, symbols or spaces - please try again";
		
		if(he.getRequestURI().toString().contains("/addorder?err3"))
			booksError = "Book does not exist, please try again";
		
		if(he.getRequestURI().toString().contains("/addorder?err4")) {
			custError = "Customer ID cannot be blank, please try again";
		}
		
		if(he.getRequestURI().toString().contains("/addorder?err5")) {
			custError = "Customer ID must be a number with no letters, symbols or spaces - please try again";
		}
		
		if(he.getRequestURI().toString().contains("/addorder?err6")) {
			custError = "Customer does not exist, please try again";
		}
		
		if(he.getRequestURI().toString().contains("/addorder?err7")) {
			booksError = "Book is already assigned to another order";
		}
		
		if(he.getRequestURI().toString().contains("id=")) {
			String URI = he.getRequestURI().toString();
			String id = Util.getIdFromUrl(URI);
			int idInt = Integer.parseInt(id);
			Customer c = bookdao.getCustomer(idInt);
			custInput = "<td><input name=\"custid\" value=\"" + c.getCustID()  + "\" readonly></td>";
		} else {
			custInput = "<td><input name=\"custid\">" + custError + "</td>";
		}
		
		
		BufferedWriter out = new BufferedWriter(  
	            new OutputStreamWriter(he.getResponseBody() ));
		
		out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Add an Order to Database</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Add a new Order</h1>" +
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>Field</th>" +
		    "    <th>Entry</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>" +
		    "<form method=\"Post\" action=\"/addorderaction\">"
		 );
		
		 out.write(
			"  <tr>"       +
    		"	 <td>Customer ID</td>" +
    		custInput +
      		"  </tr>" 	   +
			"  <tr>"       +
    		"	 <td>Book ID</td>" +
    		"    <td><input name=\"bookid\">" + booksError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
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
