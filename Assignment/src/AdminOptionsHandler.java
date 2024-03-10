import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class AdminOptionsHandler implements HttpHandler {
	
	public void handle(HttpExchange he) throws IOException {
	    he.sendResponseHeaders(200,0); //what does this mean?
	    BufferedWriter out = new BufferedWriter(  
	        new OutputStreamWriter(he.getResponseBody() ));
	    
	    out.write(
			    "<html>" +
			    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
			    "<head> <title>Administrator Options</title> </head>" +
			    "<body>" +
			    "<div class=\"container\">" +
			    "<h1>Administrator Options</h1>" +
			    "<table class=\"table\">" +
			    "<thead>" +
			    "  <tr>" +
			    "    <th>Books</th>" +
			    "    <th>Customers</th>" +
			    "	 <th>Orders</th>" +
			    "  </tr>" +
			    "</thead>"
	    		);
	    
	    out.write(
	    		"<tr>"  +
	    		"	<td> " +
	    		"		<a href=\"/displaybooks\"> Display all Books </a>" +
	      		"  </td>" +
	      		"  <td>"  +
	      		"		<a href=\"/displaycustomers\"> Display all Customers </a>" +
	      		"  </td>" +
	      		"  <td>"  +
	      		"		<a href=\"/displayorders\"> Display all Orders </a>" +
	      		"  </td>" +
	      		"</tr>" +
	      		"<tr>"  +
	    		"	<td> " +
	    		"       <a href=\"/searchbooks\"> Search for a book </a>" +
	      		"  </td>" +
	      		"  <td>"  +
	      		"		<a href=\"/searchcustomers\"> Search for a customer </a>" +
	      		"  </td>" +
	      		"  <td>"  +
	      		"  </td>" +
	      		"</tr>" +
	      		"</tr>" +
	      		"<tr>"  +
	    		"	<td> " +
	    		"       <a href=\"/addbook\"> Add a new book </a>" +
	      		"  </td>" +
	      		"  <td>"  +
	      		"		<a href=\"/addcustomer\"> Add a new customer </a>" +
	      		"  </td>" +
	      		"  <td>"  +
	      		"		<a href=\"/addorder\"> Add a new order </a>" +
	      		"  </td>" +
	      		"</tr>" +
		    	"</tbody>" +
				"</table>" +
				"<a href=\"/?logout\"> Logout </a>" + 
				"</div>" +
				"</body>" +
	    	    "</html>"
	    		);
	    	    
	    out.close();

	    
	}
}
