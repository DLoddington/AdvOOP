import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.*;
import java.io.*;

public class ProcessSearchCustomerHandler implements HttpHandler {

	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException{ //figure out how to do without throwing exception
		
		msg("In SearchCustomerProcessHandler");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);
	    
	    
	    String custID = map.get("custID");
	    String firstname = map.get("firstname");
	    String secondname = map.get("secondname");
	    String postcode = map.get("postcode");
	    String telephonenumber = map.get("telephonenumber");
	    int idAsInt;

	    /*Need at least 1 field to search by, so error if all are empty
	     * Need ID to be a number
	     */
	    
	    if(Util.testStringsAllEmpty(custID,firstname,secondname,postcode,telephonenumber)){
	    	he.getResponseHeaders().add("Location", "/searchcustomers?err1"); 
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    }

	    if((Util.testStringToInt(custID) == false) && (!custID.isEmpty())){
	    	he.getResponseHeaders().add("Location", "/searchcustomers?err2"); 
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    } 

	    /* If entries are empty, need to assign them to an arbitrary value that we know and can recognise in order to ignore within the search function  
	     * 
	     */
	    
	    if(custID.isEmpty())
	    	idAsInt = 999999999;
	    else
	    	idAsInt = Integer.parseInt(custID);
	    
	    if(firstname.isEmpty())
	    	firstname = " ";
	    		
	    if(secondname.isEmpty())
	    	secondname = " ";

	    if(postcode.isEmpty())
	    	postcode = " ";
	    
	    if(telephonenumber.isEmpty())
	    	telephonenumber = " ";

	   Address addr = new Address(" ", " ", " ", " ", postcode);
	    
	   Customer c = new Customer(idAsInt, firstname, secondname, addr ,telephonenumber);
	    
	   ArrayList<Customer> searchResults = new ArrayList<>();
	   ArrayList<Customer> searchResultsComplete = new ArrayList<>(); //some results may have no address fixed, so need to research for each customer based on ID
	   
	   searchResults = bookdao.searchCustomer(c);
	   
	   searchResults.forEach( (customer) -> { //using lamba expression instead of for loop coded out below
		   Customer temp = bookdao.getCustomer(customer.getCustID());
		   searchResultsComplete.add(temp);
	   });
	   
	   /*for(Customer customer : searchResults) {
		   Customer temp = bookdao.getCustomer(customer.getCustID());
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

	    for (Customer cust : searchResultsComplete){
	        out.write(
			    "  <tr>"       +
			    "    <td>"+ cust.getCustID() + "</td>" +
			    "    <td>"+ cust.getFirstName() + "</td>" +
			    "	 <td>"+ cust.getSecondName() + "</td>" +
			    "    <td>"+ cust.getAddress().getHousenum() + "</td>" +
			    "    <td>"+ cust.getAddress().getStreet() + "</td>" +
			    "    <td>"+ cust.getAddress().getTowncity() + "</td>" +
			    "    <td>"+ cust.getAddress().getCountry() + "</td>" +
			    "    <td>"+ cust.getAddress().getPostcode() + "</td>" +
			    "    <td>"+ cust.getTelephoneNumber() + "</td>" +
			    "    <td>"+  
			    "    <a href=\"/editcustomer?id=" + cust.getCustID() + "\"> Edit </a><br>" + 
			    "    <a href=\"/deletecustomeraction?id=" + cust.getCustID() + "\"> Delete </a><br>" +
			    "    <a href=\"/addorderaction?id=" + cust.getCustID() + "\"> Add Order </a>" +
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
