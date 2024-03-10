import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class EditCustomerHandler implements HttpHandler{ //need to change orientation of the table
  
	static BooksDAO booksdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException {
    
		he.sendResponseHeaders(200,0); 
		
		/*As with editing books, ID should never be editable, its assigned on auto-increment from the database
		
		 As with delete customer process, the id to edit is passed through URL, so need to fetch with getIdFromUrl
		 * function that we made, then find the associated book
		 */
		
		String URL = he.getRequestURI().toString();
		String id = Util.getIdFromUrl(URL);
		int idInt = Integer.parseInt(id);
		System.out.println(idInt);
		
		Customer c = booksdao.getCustomer(idInt);
		
		/* We will also have a series of error messages that need to be present 
		 * so we initiate them here
		 */
		
		String firstnameError = "";
		String secondnameError = ""; 
		String housenumError = "";
		String postcodeError = "";
		
		if(he.getRequestURI().toString().contains("/editcustomer?err1"))
			firstnameError = "Title cannot be blank, please try again";
		
		if(he.getRequestURI().toString().contains("/editcustomer?err2"))
			secondnameError = "Author cannot be blank, please try again";
		
		if(he.getRequestURI().toString().contains("/editcustomer?err3"))
			housenumError = "ISBN must be a number with no letters or symbols, please try again";
		
		if(he.getRequestURI().toString().contains("/editcustomer?err4"))
			postcodeError = "ISBN should be 10 or 13 numbers long, please try again";

	    /* Now we construct the page, including a table with pre-populated fields
	     * 
	     */
		
		BufferedWriter out = new BufferedWriter(  
				new OutputStreamWriter(he.getResponseBody() ));
		
		out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Edit Customer in Database</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Edit Customer</h1>" +
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>Field</th>" +
		    "    <th>Current</th>" +
		    "    <th>Update to</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>" +
		    "<form method=\"Post\" action=\"/editcustomeraction\">"
	    );
	    
        /* Fill the current column of the table with the current values
         * then put input boxes in Update to column, pre-populated with current values, which can be edited
         * note: id will be read-only and needs to be an input so it is passed through in the submit
         */
	    
	    out.write(
		    "  <tr>"       +
    		"	 <td>ID</td>" +
    		"    <td>"+ c.getCustID() + "</td>" +
    		"    <td><input name=\"id\" value=\"" + c.getCustID() + "\" readonly></td>" +
    		"  </tr>" 	   +
    		"  <tr>"       +
    		"	 <td>First Name</td>" +
  		    "    <td>"+ c.getFirstName() + "</td>" +
  		    "    <td><input name=\"firstname\" value=\"" + c.getFirstName() + "\">" + firstnameError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Surname</td>" +
  		    "    <td>"+ c.getSecondName() + "</td>" +
  		    "    <td><input name=\"secondname\" value=\"" + c.getSecondName() + "\">" + secondnameError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>House Number</td>" +
  		    "    <td>"+ c.getAddress().getHousenum() + "</td>" +
  		    "    <td><input name=\"housenum\" value=\"" + c.getAddress().getHousenum() + "\">" + housenumError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Street</td>" +
  		    "    <td>"+ c.getAddress().getStreet() + "</td>" +
  		    "    <td><input name=\"street\" value=\"" + c.getAddress().getStreet()  + "\"></td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Town/City</td>" +
  		    "    <td>"+c.getAddress().getTowncity() + "</td>" +
  		    "    <td><input name=\"towncity\" value=\"" + c.getAddress().getTowncity() + "\"></td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Country</td>" +
  		    "    <td>"+ c.getAddress().getCountry() + "</td>" +
  		    "    <td><input name=\"country\" value=\"" + c.getAddress().getCountry()  + "\"></td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Post Code</td>" +
  		    "    <td>"+ c.getAddress().getPostcode() + "</td>" +
  		    "    <td><input name=\"postcode\" value=\"" + c.getAddress().getPostcode() + "\">" + postcodeError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Telephone Number</td>" +
  		    "    <td>"+ c.getTelephoneNumber() + "</td>" +
  		    "    <td><input name=\"condition\" value=\"" + c.getTelephoneNumber() + "\"></td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
	    	"</tbody>" +
			"</table>" +
			"<input type=\"submit\" value=\"Submit\">  "+
  	      	"</form>" + 
			"<br><br>" +
  	      	"<h2>Return to customer table</h2>" +
			"<a href=\"/displaycustomers\"> Click here</a>" +
			"<br><br>" +
			"</div>" +
			"</body>" +
			"</html>"
			);
	    out.close();
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}
}