import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class AddBookHandler implements HttpHandler{
  
	public void handle(HttpExchange he) throws IOException {
    
	he.sendResponseHeaders(200,0); 
    
    String titleError = "";
	String authorError = ""; 
	String isbnError = "";
	String yearError = "";
	String editionError = "";
	String priceError = "";
	
	/*Error statements are designed to provide some indication where input may have been wrong in first place
	 * in ideal world, inputs would be pre-populated with old responses on redirect, but difficult to do without servlets
	 */
	
	if(he.getRequestURI().toString().equals("/addbook?err1"))
		titleError = "Title cannot be blank, please try again";
	
	if(he.getRequestURI().toString().equals("/addbook?err2"))
		authorError = "Author cannot be blank, please try again";
	
	if(he.getRequestURI().toString().equals("/addbook?err3"))
		isbnError = "ISBN must be a number with no letters or symbols, please try again";
	
	if(he.getRequestURI().toString().equals("/addbook?err4"))
		isbnError = "ISBN should be 10 or 13 numbers long, please try again";
	
	if(he.getRequestURI().toString().equals("/addbook?err5"))
		isbnError = "ISBN is invalid, please try again";
	
	if(he.getRequestURI().toString().equals("/addbook?err6"))
		yearError = "Year must be a number with no letters or symbols, please try again";
	
	if(he.getRequestURI().toString().equals("/addbook?err7"))
		editionError = "Edition must be a number with no letters or symbols, please try again";
	
	if(he.getRequestURI().toString().equals("/addbook?err8"))
		priceError = "Price must be a number in pence with no currency symbols or decimal places, please try again";
	
	BufferedWriter out = new BufferedWriter(  
            new OutputStreamWriter(he.getResponseBody() ));
    
	out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Add Book to Library</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Add a new book</h1>" +
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>Field</th>" +
		    "    <th>Entry</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>" +
		    "<form method=\"Post\" action=\"/addbookaction\">"
	    );
	 out.write(
	    		"  <tr>"       +
	    		"	 <td>Title</td>" +
	  		    "    <td><input name=\"title\">" + titleError + "</td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Author</td>" +
	  		    "    <td><input name=\"author\">" + authorError + "</td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Year</td>" +
	  		    "    <td><input name=\"year\">" + yearError + "</td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Edition</td>" +
	  		    "    <td><input name=\"edition\">" + editionError + "</td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Publisher</td>" +
	  		    "    <td><input name=\"publisher\"></td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>ISBN</td>" +
	  		    "    <td><input name=\"isbn\">" + isbnError + "</td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Cover</td>" +
	  		    "    <td><input name=\"cover\"></td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Condition</td>" +
	  		    "    <td><input name=\"condition\"></td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Price</td>" +
	  		    "    <td><input name=\"price\">" + priceError + "</td>" +
	      		"  </tr>" 	   +
	      		"  <tr>"       +
	      		"	 <td>Notes</td>" +
	  		    "    <td><input name=\"notes\"></td>" +
	      		"  </tr>" +
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
