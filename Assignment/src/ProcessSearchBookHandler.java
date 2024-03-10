import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.*;
import java.io.*;

public class ProcessSearchBookHandler implements HttpHandler {

	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException{ //figure out how to do without throwing exception
		
		msg("In SearchBookProcessHandler");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);
	    
	    
	    String bookID = map.get("bookID");
	    String title = map.get("title");
	    String author = map.get("author");
	    String edition = map.get("edition");
	    String publisher = map.get("publisher");
	    String isbn = map.get("isbn");
	    int idAsInt;
	    int editionAsInt;

	    /*Need at least 1 field to search by, so error if all are empty
	     * Need ID to be a number
	     */
	    
	    if(Util.testStringsAllEmpty(bookID,title,author,edition, publisher, isbn)){
	    	he.getResponseHeaders().add("Location", "/searchbooks?err1"); 
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }

	    if((Util.testStringToInt(bookID) == false) && (!bookID.isEmpty())){
	    	he.getResponseHeaders().add("Location", "/searchbooks?err2"); 
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    } 
	    
	    if((Util.testStringToInt(edition) == false) && (!edition.isEmpty())){
	    	he.getResponseHeaders().add("Location", "/searchbooks?err3"); 
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    } 

	    /* If entries are empty, need to assign them to an arbitrary value that we know and can recognise in order to ignore within the search function  
	     * 
	     */
	    
	    if(bookID.isEmpty())
	    	idAsInt = 999999999;
	    else
	    	idAsInt = Integer.parseInt(bookID);
	    
	    if(title.isEmpty())
	    	title = " ";
	    		
	    if(author.isEmpty())
	    	author = " ";

	    if(edition.isEmpty())
	    	editionAsInt = 999999999;
	    else
	    	editionAsInt = Integer.parseInt(edition);

	    if(publisher.isEmpty())
	    	publisher = " ";

	    if(isbn.isEmpty())
	    	isbn = " ";

	   Book b = new Book(idAsInt, title, author , editionAsInt , publisher, isbn);
	    
	   ArrayList<Book> searchResults = new ArrayList<>();
	   ArrayList<Book> searchResultsComplete = new ArrayList<>(); //some results may have no address fixed, so need to research for each customer based on ID
	   
	   searchResults = bookdao.searchBook(b);
	
	   searchResults.forEach( (book) -> { //using lamba expression instead of for loop coded out below
		   Book temp = bookdao.getBook(book.getBook_id());
		   searchResultsComplete.add(temp);
	   });
	   
	   /*for(Book book : searchResults) {
		   Book temp = bookdao.getBook(book.getBook_id());
		   searchResultsComplete.add(temp);
	   }*/
	   
	   String noResults = "";
	   if(searchResultsComplete.size() == 0){
		 noResults = "<p>Your search criteria has no results, please refine and try again!</p>";  
	   }
	   
	   he.sendResponseHeaders(200,0); 
	   
	   BufferedWriter out = new BufferedWriter(
			   new OutputStreamWriter(he.getResponseBody() ));
	   
	   out.write(
			    "<html>" +
			    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
			    "<head> <title>Customer List</title> </head>" +
			    "<body>" +
			    "<div class=\"container\">" +
			    "<h1>Search Results!</h1><br>" +
			    noResults +
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
			    "	 <th>Actions</th>" +	
			    "  </tr>" +
			    "</thead>" +
			    "<tbody>"
		    );

	    for (Book bb : searchResultsComplete){
	        out.write(
			    "  <tr>"       +
	    	    "    <td>"+ bb.getBook_id() + "</td>" +
			    "    <td>"+ bb.getTitle() + "</td>" +
			    "    <td>"+ bb.getAuthor() + "</td>" +
			    "    <td>"+ bb.getYear() + "</td>" +
			    "    <td>"+ bb.getEdition() + "</td>" +
			    "    <td>"+ bb.getPublisher() + "</td>" +
			    "    <td>"+ bb.getIsbn() + "</td>" +
			    "    <td>"+ bb.getCover() + "</td>" +
			    "    <td>"+ bb.getCondition() + "</td>" +
			    "    <td>"+ bb.getPrice() + "</td>" +
			    "    <td>"+ bb.getNotes() + "</td>" +
			    "    <td>"+  
			    "    <a href=\"/editbook?id=" + bb.getBook_id() + "\"> Edit </a><br>" + 
			    "    <a href=\"/deletecustomeraction?id=" + bb.getBook_id() + "\"> Delete </a><br>" +
			    "	 </td>"+
			    "  </tr>" 
	        );
	    }
    
	    out.write(
	    	"</tbody>" +
			"</table>" +
			"<a href=\"/adminoptions\"> Back to Admin Options </a><br>" +
			"<a href=\"/\"> Logout </a>" + 
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
