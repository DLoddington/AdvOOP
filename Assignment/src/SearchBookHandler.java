import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class SearchBookHandler implements HttpHandler{
  
	public void handle(HttpExchange he) throws IOException {
    
		he.sendResponseHeaders(200,0); 
	    
	    String allblankError = "";
	    String bookMessage = "";
	    String editionError = "";
		
		/*Error statements are designed to provide some indication where input may have been wrong in first place
		 * in ideal world, inputs would be pre-populated with old responses on redirect, but difficult to do without servlets
		 */
		
		if(he.getRequestURI().toString().equals("/searchbooks?err1"))
			allblankError = "All fields cannot be blank, please enter at least one search term";
		
		if(he.getRequestURI().toString().equals("/searchbooks?err2"))
			allblankError = "ID must be a number with no letters, spaces or symbols";
		
		if(he.getRequestURI().toString().equals("/searchbooks?err3"))
			editionError = "Edition must be a number with no letters, spaces or symbols";
		
		
		BufferedWriter out = new BufferedWriter(  
	            new OutputStreamWriter(he.getResponseBody() ));
	    
		out.write(
			    "<html>" +
			    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
			    "<head> <title>Search Books within the Rare Book Emporium</title> </head>" +
			    "<body>" +
			    "<div class=\"container\">" +
			    "<h1>Advanced Book Search</h1>" +
			    "<table class=\"table\">" +
			    "<thead>" +
			    "  <tr>" +
			    "    <th>Field</th>" +
			    "    <th>Entry</th>" +
			    "  </tr>" +
			    "</thead>" +
			    "<tbody>" +
			    "<form method=\"Post\" action=\"/searchbooksaction\">"
		    );
		out.write(
		 	"  <tr>"       +
			"	 <td>Book ID</td>" +
		    "    <td><input name=\"bookID\">" + allblankError  +   "</td>" +
	  		"  </tr>" 	   +
		 	"  <tr>"       +
			"	 <td>Title</td>" +
		    "    <td><input name=\"title\"></td>" +
	  		"  </tr>" 	   +
	  		"  <tr>"       +
	  		"	 <td>Author</td>" +
		    "    <td><input name=\"author\"></td>" +
	  		"  </tr>" 	   +
	  		"  <tr>"       +
	  		"	 <td>Edition</td>" +
		    "    <td><input name=\"edition\">" + editionError +  "</td>" +
	  		"  </tr>" 	   +
	  		"  <tr>"       +
	  		"	 <td>Publisher</td>" +
		    "    <td><input name=\"publisher\"></td>" +
	  		"  </tr>" 	   +
	  		"  <tr>"       +
	  		"	 <td>ISBN</td>" +
		    "    <td><input name=\"isbn\"></td>" +
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

