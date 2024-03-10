import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class EditOrderItemsHandler implements HttpHandler {
	
	static BooksDAO booksdao = BooksDAO.getInstance();

	public void handle(HttpExchange he) throws IOException {
		
		he.sendResponseHeaders(200,0); 
    
		BufferedWriter out = new BufferedWriter(  
				new OutputStreamWriter(he.getResponseBody() ));
		
		String URL = he.getRequestURI().toString();
		String id = Util.getIdFromUrl(URL);
		int idInt = Integer.parseInt(id);
		System.out.println(idInt);
		
		Order o = booksdao.getOrder(idInt);
		
		ArrayList<Book> items = booksdao.getAllOrderLines(idInt);
		

	    out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Edit Order Items</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Edit Order Items</h1>" +
		    "<p>Editing Order: " + o.getOrderID() + " Customer ID: " + o.getCustomer().getCustID() + "</p>" + 
		    "<a href=\"addorderline?id=" + idInt + "\">Add an item to this order</a>" + 
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>Book ID</th>" +
		    "    <th>Book Title</th>" +
		    "    <th>Actions</th>" +			
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>" +
		    "<form method=\"Post\" action=\"/editorderitemsaction\">"
	    );
        int i = 0;
        for (Book b : items){
	        out.write(
			    "<tr>"       +
			    "<td>"+ b.getBook_id() + "</td>" +
			    "<td>"+ b.getTitle() + "</td>" +
			    "<td>" +
       		 	"<Select name=\"itemstatus" + i + "\" id=\"itemstatus\">" +
       		 	"<option value=\"keep\">Keep on Order</option>" +
       		 	"<option value=\"remove\">Remove from order</option>" +	
       		 	"</td>"+ 
       		 	"    <td><input name=\"bookID" + i + "\" value=\"" + b.getBook_id() + "\" type=\"hidden\">" +
			    "</tr>"
	        );
	        i++;
	    }
	        
        out.write(
   		 	" <input name=\"orderID\" value=\"" + idInt + "\" type=\"hidden\">" +
    		" <input name=\"bookcount\" value=\"" + i + "\" type=\"hidden\">" +
        	"</tbody>" +
			"</table>" +
			"<input type=\"submit\" value=\"Submit\">  "+
  	      	"</form>" + 
			"<br><a href=\"/adminoptions\"> Back to Admin Options </a><br>" +
			"<a href=\"/\"> Logout </a>" + 
			"</div>" +   
			"</body>" +
			"</html>"
		);
	    
	    out.close();

  }

}
