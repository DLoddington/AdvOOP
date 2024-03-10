import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class SearchCustomerHandler implements HttpHandler{
  
	public void handle(HttpExchange he) throws IOException {
    
		he.sendResponseHeaders(200,0); 
	    
	    String allblankError = "";
	    String orderMessage = "";
		
		/*Error statements are designed to provide some indication where input may have been wrong in first place
		 * in ideal world, inputs would be pre-populated with old responses on redirect, but difficult to do without servlets
		 */
		
		if(he.getRequestURI().toString().equals("/searchcustomers?err1"))
			allblankError = "All fields cannot be blank, please enter at least one search term";
		
		if(he.getRequestURI().toString().equals("/searchcustomers?err2"))
			allblankError = "ID must be a number with no letters, spaces or symbols";
		
		if(he.getRequestURI().toString().equals("/searchcustomers?1"))
			orderMessage = "Please search for the customer you would like to place an order for!";
		
		BufferedWriter out = new BufferedWriter(  
	            new OutputStreamWriter(he.getResponseBody() ));
	    
		out.write(
			    "<html>" +
			    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
			    "<head> <title>Search Customers within the Database</title> </head>" +
			    "<body>" +
			    "<div class=\"container\">" +
			    "<h1>Advanced Customer Search</h1>" +
			    "<p>" + orderMessage + "</p>" +
			    "<table class=\"table\">" +
			    "<thead>" +
			    "  <tr>" +
			    "    <th>Field</th>" +
			    "    <th>Entry</th>" +
			    "  </tr>" +
			    "</thead>" +
			    "<tbody>" +
			    "<form method=\"Post\" action=\"/searchcustomersaction\">"
		    );
		out.write(
		 	"  <tr>"       +
			"	 <td>ID</td>" +
		    "    <td><input name=\"custID\">" + allblankError  +   "</td>" +
	  		"  </tr>" 	   +
		 	"  <tr>"       +
			"	 <td>First Name</td>" +
		    "    <td><input name=\"firstname\"></td>" +
	  		"  </tr>" 	   +
	  		"  <tr>"       +
	  		"	 <td>Surname</td>" +
		    "    <td><input name=\"secondname\"></td>" +
	  		"  </tr>" 	   +
	  		"  <tr>"       +
	  		"	 <td>Post Code</td>" +
		    "    <td><input name=\"postcode\"></td>" +
	  		"  </tr>" 	   +
	  		"  <tr>"       +
	  		"	 <td>Telephone Number</td>" +
		    "    <td><input name=\"telephonenumber\"></td>" +
	  		"  </tr>" 	   +
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

