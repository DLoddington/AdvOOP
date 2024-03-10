import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.IOException;

public class CustomersHandler implements HttpHandler {
	
static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException {
    
		he.sendResponseHeaders(200,0); 
		
		ArrayList<Customer> allCustomers = bookdao.getAllCustomers();
    
		BufferedWriter out = new BufferedWriter(  
				new OutputStreamWriter(he.getResponseBody() ));
   
	    out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Customer List</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Rare Book Emporium Customer List</h1>" +
		    "<a href=\"/addcustomer\"> Add New Customer </a>" + 
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>ID</th>" +
		    "    <th>First Name</th>" +
		    "    <th>Surname</th>"   +
		    "    <th>House Number</th>" +
		    "    <th>Street Name</th>" +
		    "    <th>Town/City</th>" +
		    "    <th>Country</th>" +
		    "    <th>Post Code</th>" +
		    "    <th>Telephone Numner</th>" +
		    "    <th>Actions</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>"
	    );

	    for (Customer c : allCustomers){
	        out.write(
			    "  <tr>"       +
			    "    <td>"+ c.getCustID() + "</td>" +
			    "    <td>"+ c.getFirstName() + "</td>" +
			    "	 <td>"+ c.getSecondName() + "</td>" +
			    "    <td>"+ c.getAddress().getHousenum() + "</td>" +
			    "    <td>"+ c.getAddress().getStreet() + "</td>" +
			    "    <td>"+ c.getAddress().getTowncity() + "</td>" +
			    "    <td>"+ c.getAddress().getCountry() + "</td>" +
			    "    <td>"+ c.getAddress().getPostcode() + "</td>" +
			    "    <td>"+ c.getTelephoneNumber() + "</td>" +
			    "    <td>"+  
			    "    <a href=\"/editcustomer?id=" + c.getCustID() + "\"> Edit </a><br>" + 
			    "    <a href=\"/deletecustomeraction?id=" + c.getCustID() + "\"> Delete </a>" +
			    "    <a href=\"/addorderaction?id=" + c.getCustID() + "\"> Add Order </a>" +
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
