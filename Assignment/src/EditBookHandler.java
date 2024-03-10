import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class EditBookHandler implements HttpHandler{ //need to change orientation of the table
  
	static BooksDAO booksdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException {
    
		he.sendResponseHeaders(200,0); 
		
		/*Line of thinking here - book_id should never be editable. Title and author, ideally, shouldn't be either
		 * however, to account for human error and spelling mistakes etc, it would make sense to allow it. Every
		 * other field should be editable as they weren't mandatory when entering in the book in the first place
		
		/*As with delete book process, the id to edit is passed through URL, so need to fetch with getIdFromUrl
		 * function that we made, then find the associated book
		 */
		
		String URI = he.getRequestURI().toString();
		String id = Util.getIdFromUrl(URI);
		int idInt = Integer.parseInt(id);
		
		Book b = booksdao.getBook(idInt);
		
		/* We will also have a series of error messages that need to be present 
		 * so we initiate them here
		 */
		
		String titleError = "";
		String authorError = ""; 
		String isbnError = "";
		String yearError = "";
		String editionError = "";
		String priceError = "";
		
		/*Errors by detection in the URI now use contains instead of equals because the book id is also being passed
		 * through
		 */
		
		if(he.getRequestURI().toString().contains("/editbook?err1"))
			titleError = "Title cannot be blank, please try again";
		
		if(he.getRequestURI().toString().contains("/editbook?err2"))
			authorError = "Author cannot be blank, please try again";
		
		if(he.getRequestURI().toString().contains("/editbook?err3"))
			isbnError = "ISBN must be a number with no letters or symbols, please try again";
		
		if(he.getRequestURI().toString().contains("/editbook?err4"))
			isbnError = "ISBN should be 10 or 13 numbers long, please try again";
		
		if(he.getRequestURI().toString().contains("/editbook?err5"))
			isbnError = "ISBN is invalid, please try again";
		
		if(he.getRequestURI().toString().contains("/editbook?err6"))
			yearError = "Year must be a number with no letters or symbols, please try again";
		
		if(he.getRequestURI().toString().contains("/editbook?err7"))
			editionError = "Edition must be a number with no letters or symbols, please try again";
		
		if(he.getRequestURI().toString().contains("/editbook?err8"))
			priceError = "Price must be a number in pence with no currency symbols or decimal places, please try again";

   
	    /* Now we construct the page, including a table with pre-populated fields
	     * 
	     */
		
		BufferedWriter out = new BufferedWriter(  
				new OutputStreamWriter(he.getResponseBody() ));
		
		out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Edit Book in Library</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Edit Book</h1>" +
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>Field</th>" +
		    "    <th>Current</th>" +
		    "    <th>Update to</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>" +
		    "<form method=\"Post\" action=\"/editbookaction\">"
	    );
	    
        /* Fill the first line of the table with the current values
         * 
         */
	    
	    out.write(
		    "  <tr>"       +
    		"	 <td>ID</td>" +
    		"    <td>"+ b.getBook_id() + "</td>" +
    		"    <td><input name=\"id\" value=\"" + b.getBook_id() + "\" readonly></td>" +
    		"  </tr>" 	   +
    		"  <tr>"       +
    		"	 <td>Title</td>" +
  		    "    <td>"+ b.getTitle() + "</td>" +
  		    "    <td><input name=\"title\" value=\"" + b.getTitle() + "\">" + titleError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Author</td>" +
  		    "    <td>"+ b.getAuthor() + "</td>" +
  		    "    <td><input name=\"author\" value=\"" + b.getAuthor() + "\">" + authorError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Year</td>" +
  		    "    <td>"+ b.getYear() + "</td>" +
  		    "    <td><input name=\"year\" value=\"" + b.getYear() + "\">" + yearError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Edition</td>" +
  		    "    <td>"+ b.getEdition() + "</td>" +
  		    "    <td><input name=\"edition\" value=\"" + b.getEdition()  + "\">" + editionError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Publisher</td>" +
  		    "    <td>"+ b.getPublisher() + "</td>" +
  		    "    <td><input name=\"publisher\" value=\"" + b.getPublisher() + "\"></td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>ISBN</td>" +
  		    "    <td>"+ b.getIsbn() + "</td>" +
  		    "    <td><input name=\"isbn\" value=\"" + b.getIsbn()  + "\">" + isbnError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Cover</td>" +
  		    "    <td>"+ b.getCover() + "</td>" +
  		    "    <td><input name=\"cover\" value=\"" + b.getCover() + "\"></td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Condition</td>" +
  		    "    <td>"+ b.getCondition() + "</td>" +
  		    "    <td><input name=\"condition\" value=\"" + b.getCondition() + "\"></td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Price</td>" +
  		    "    <td>"+ b.getPrice() + "</td>" +
  		    "    <td><input name=\"price\" value=\"" + b.getPrice() + "\">" + priceError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Notes</td>" +
  		    "    <td>"+ b.getNotes() + "</td>" +
  		    "    <td><input name=\"notes\" value=\"" + b.getNotes() + "\"></td>" +
      		"  </tr>" +
	    	"</tbody>" +
			"</table>" +
			"<input type=\"submit\" value=\"Submit\">  "+
  	      	"</form>" + 
			"<br><br>" +
  	      	"<h2>Return to book table</h2>" +
			"<a href=\"/displaybooks\"> Click here</a>" +
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