import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.IOException;

public class OrdersHandler implements HttpHandler {
	
static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException {
    
		/*Aesthetically - I know this doesn't look amazing. With more time and mark allocation towards CSS and appearance - I would have considered making the spacing in the table a lot better
		 * Essentially, I laid it out so there are single column tables within cells that print information about specific books on the order
		 * Another easier way to go about it would have been to say that a single order can only have a single book, but wheres the fun in problem solving that?
		 */
		
		
		he.sendResponseHeaders(200,0); 
		
		ArrayList<Order> allOrders = bookdao.getAllOrders();
    
		BufferedWriter out = new BufferedWriter(  
				new OutputStreamWriter(he.getResponseBody() ));
		
		
		
		
		
   
	    out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Order List</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Rare Book Emporium Order List</h1>" +
		    "<a href=\"/addorder\"> Add New Order </a>" + 
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>Order ID</th>" +
		    "    <th>Customer ID</th>" +
		    "    <th>Customer Name</th>"   +
		    "    <th>Book ID</th>"   +
		    "    <th>Book Title</th>" +
		    "    <th>Book Author</th>" +
		    "    <th>Book Price</th>" +
		    "    <th>Total Order Price</th>" +
		    "    <th>Order Status</th>" +
		    "    <th>Actions</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>"
	    );

	    for (Order o : allOrders){
	        out.write(
			    "  <tr>"       +
			    "    <td>"+ o.getOrderID() + "</td>" +
			    "    <td>"+ o.getCustomer().getCustID() + "</td>" +
			    "	 <td>"+ o.getCustomer().getFirstName() + " " + o.getCustomer().getSecondName() +  "</td>" +
			    "    <td>"
		    );
	        
	        for(Book b : o.getItems() ) {
	        	out.write( // for all IDs
        			"<table class=\"table\">" +
        				    "<tbody>" +
        				    "  <tr>" +
        				    "    <td>" + b.getBook_id() + "</td>" +
        				    "  </tr>" +
        				    "</tbody>" +
				    "</table>" 
    			);
	        }
	        
	        out.write(
        		"</td>" +
        		"<td>"
	        );
	        
	        for(Book b : o.getItems() ) {
	        	out.write( // for all titles
        			"<table class=\"table\">" +
        				    "<tbody>" +
        				    "  <tr>" +
        				    "    <td>" + b.getTitle() + "</td>" +
        				    "  </tr>" +
        				    "</tbody>" +
				    "</table>" 
    			);
	        }
	        
	        out.write(
        		"</td>" +
        		"<td>"
	        );
	        
	        
	        for(Book b : o.getItems() ) { 
	        	out.write( //for all authors
        			"<table class=\"table\">" +
        				    "<tbody>" +
        				    "  <tr>" +
        				    "    <td>" + b.getAuthor() + "</td>" +
        				    "  </tr>" +
        				    "</tbody>" +
				    "</table>" 
	    		);
	        }
	        
	        out.write(
        		"</td>" +
        		"<td>"
		    );
	        int totalPrice = 0;	
	        for(Book b: o.getItems() ) {
		        out.write( // for all prices
        			"<table class=\"table\">" +
        				    "<tbody>" +
        				    "  <tr>" +
        				    "    <td>" + b.getPrice() + "</td>" +
        				    "  </tr>" +
        				    "</tbody>" +
				    "</table>"
			        			
	    		);
		        totalPrice += b.getPrice();
	        }
	        
	        String orderStatus = "Incomplete" ;
	        if(o.isComplete()) 
	        	orderStatus = "Complete";
		    
	        out.write(
	        "    </td>" +
		    "    <td>"+ totalPrice + "</td>" +
		    "    <td>"+ orderStatus + "</td>" +
		    "    <td>"+  
		    "    <a href=\"/editorderstatus?id=" + o.getOrderID() + "\"> Update order status </a><br>" +
		    "    <a href=\"/editorderitems?id=" + o.getOrderID() + "\"> Add or delete order items </a><br>" + 
		    "    <a href=\"/deleteorderaction?id=" + o.getOrderID() + "\"> Delete </a>" + 
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

}
