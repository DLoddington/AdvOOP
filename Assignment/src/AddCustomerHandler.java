import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class AddCustomerHandler implements HttpHandler{
  
	public void handle(HttpExchange he) throws IOException {
    
	he.sendResponseHeaders(200,0); 
    
    String firstnameError = "";
	String secondnameError = ""; 
	String housenumError = "";
	String postcodeError = "";
	
	/*Error statements are designed to provide some indication where input may have been wrong in first place
	 * in ideal world, inputs would be pre-populated with old responses on redirect, but difficult to do without servlets
	 */
	
	if(he.getRequestURI().toString().equals("/addcustomer?err1"))
		firstnameError = "First name cannot be blank, please try again";
	
	if(he.getRequestURI().toString().equals("/addcustomer?err2"))
		secondnameError = "Surname cannot be blank, please try again";
	
	if(he.getRequestURI().toString().equals("/addcustomer?err3"))
		housenumError = "House number cannot be blank, please try again";
	
	if(he.getRequestURI().toString().equals("/addcustomer?err4"))
		postcodeError = "Postcode cannot be blank, please try again";

    System.out.println(he.getRequestURI().toString());
	
	BufferedWriter out = new BufferedWriter(  
            new OutputStreamWriter(he.getResponseBody() ));
    
	out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Add Customer to Database</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Add a new Customer</h1>" +
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>Field</th>" +
		    "    <th>Entry</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>" +
		    "<form method=\"Post\" action=\"/addcustomeraction\">"
	    );
	 out.write(
	    		"  <tr>"       +
	    		"	 <td>First Name</td>" +
	  		    "    <td><input name=\"firstname\">" + firstnameError + "</td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Surname</td>" +
	  		    "    <td><input name=\"secondname\">" + secondnameError + "</td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>House Number</td>" +
	  		    "    <td><input name=\"housenum\">" + housenumError + "</td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Street Name</td>" +
	  		    "    <td><input name=\"street\"></td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Town/City</td>" +
	  		    "    <td><input name=\"towncity\"></td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Country</td>" +
	  		    "    <td><input name=\"country\"></td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Post Code</td>" +
	  		    "    <td><input name=\"postcode\">" + postcodeError + "</td>" +
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
