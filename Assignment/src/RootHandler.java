import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.IOException;

public class RootHandler implements HttpHandler{
  
	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException {
    
		he.sendResponseHeaders(200,0); 
		
		ArrayList<Book> allBooks = bookdao.getAllBooks();
    
		BufferedWriter out = new BufferedWriter(  
				new OutputStreamWriter(he.getResponseBody() ));
   
	    out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Book Library</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Welcome to the Rare Book Store</h1>" +
		    "<a href=\"/login\"> Admin Login </a>" + 
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>ID</th>" +
		    "    <th>Title</th>" +
		    "    <th>Author</th>" +
		    "    <th>Year</th>" +
		    "    <th>Edition</th>" +
		    "    <th>Publisher</th>" +
		    "    <th>ISBN</th>" +
		    "    <th>Cover</th>" +
		    "    <th>Condition</th>" +
		    "    <th>Price</th>" +
		    "    <th>Notes</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>"
	    );

	    for (Book b : allBooks){
	        out.write(
			    "  <tr>"       +
			    "    <td>"+ b.getBook_id() + "</td>" +
			    "    <td>"+ b.getTitle() + "</td>" +
			    "    <td>"+ b.getAuthor() + "</td>" +
			    "    <td>"+ b.getYear() + "</td>" +
			    "    <td>"+ b.getEdition() + "</td>" +
			    "    <td>"+ b.getPublisher() + "</td>" +
			    "    <td>"+ b.getIsbn() + "</td>" +
			    "    <td>"+ b.getCover() + "</td>" +
			    "    <td>"+ b.getCondition() + "</td>" +
			    "    <td>"+ b.getPrice() + "</td>" +
			    "    <td>"+ b.getNotes() + "</td>" +
			    "  </tr>" 
	        );
	    }
    
	    out.write(
	    	"</tbody>" +
			"</table>" +
			"</div>" +   
			"</body>" +
			"</html>"
		);
	    
	    out.close();

  }

}