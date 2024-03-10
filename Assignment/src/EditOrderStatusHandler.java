import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.IOException;

public class EditOrderStatusHandler implements HttpHandler {
	
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

	    out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Edit Order</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Edit Order</h1>" +
		    "<p>Order ID: " + idInt + " Customer ID" + o.getCustomer().getCustID() + "</p>" + 
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>Order ID</th>" +
		    "    <th>Customer ID</th>" +
		    "    <th>Order Status</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>" +
		    "<form method=\"Post\" action=\"/editorderstatusaction\">" +
		    "<tr>" +
		    "<td>" + idInt + "</td>" +
		    "<td>" + o.getCustomer().getCustID() + "</td>" +
        	"<td>" +
        		 "<Select name=\"orderstatus\" id=\"orderstatus\">" +
        		 "<option value=\"none\" selected disabled hidden>Select an Option</option>" +
        		 "<option value=\"complete\">Complete</option>" +
        		 "<option value=\"incomplete\">Incomplete</option>" +	
        	"</td>"+  
        	"<td><input name=\"orderID\" value=\"" + idInt + "\" type=\"hidden\">" +
        	"</td>" +
        	"</tr>" +
	    	"</tbody>" +
			"</table>" +
			"<input type=\"submit\" value=\"Submit\"> <br> "+
  	      	"</form>" + 
			"<a href=\"/adminoptions\"> Back to Admin Options </a><br>" +
			"<a href=\"/\"> Logout </a>" + 
			"</div>" +   
			"</body>" +
			"</html>"
		);
	    
	    out.close();

  }

}
