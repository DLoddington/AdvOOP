import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class SuccessHandler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange he) throws IOException {
		
		he.sendResponseHeaders(200,0);
		
	    BufferedWriter out = new BufferedWriter(  
	        new OutputStreamWriter(he.getResponseBody() ));
	    
	    String obj = "";
	    String action = "";
	    String addmore = "";
	    String id = "";
	    
	    String URL = he.getRequestURI().toString();
	    
	    if(he.getRequestURI().toString().contains("id=")) //get the id from the uri only if it contains an id
	    	id = Util.getIdFromUrl(URL);
	    
	    if(he.getRequestURI().toString().contains("/success?ab")) {
			obj = "Book";
	    	action = "added"; 
	    }
	    
	    if(he.getRequestURI().toString().contains("/success?eb")) {
			obj = "Book";
	    	action = "updated"; 
	    }
	    
	    if(he.getRequestURI().toString().contains("/success?db")) {
			obj = "Book";
	    	action = "deleted"; 
	    }
	    
	    if(he.getRequestURI().toString().contains("/success?ac")) {
			obj = "Customer";
	    	action = "added"; 
	    }
	    
	    if(he.getRequestURI().toString().contains("/success?ec")) {
			obj = "Customer";
	    	action = "updated"; 
	    }
	    
	    if(he.getRequestURI().toString().contains("/success?dc")) {
			obj = "Customer";
	    	action = "deleted"; 
	    }
	    
	    if(he.getRequestURI().toString().contains("/success?do")) {
			obj = "Order";
	    	action = "deleted"; 
	    }
	    
	    if(he.getRequestURI().toString().contains("/success?ao")) {
			obj = "Order";
	    	action = "added";
	    	addmore = "<p>To add further products to your order: <a href=\"/addorderlines?id=" + id +"\">Click here</a></p>";
	    }
	    
	    if(he.getRequestURI().toString().contains("/success?eos")) {
			obj = "Order status";
	    	action = "updated"; 
	    }
	    
	    if(he.getRequestURI().toString().contains("/success?eol")) {
			obj = "Order line(s)";
	    	action = "updated"; 
	    }
	    
	    if(he.getRequestURI().toString().contains("/success?ola")) { //had to switch formal from aol (added order line) because ao (added order) would conflict - ola - order line added
			obj = "Order line";
	    	action = "added";
	    	addmore = "<p>To add further products to your order: <a href=\"/addorderlines?id=" + id +"\">Click here</a></p>";
	    }

	    
	    
    	out.write(
	    	      "<html>" +
	    	      "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
	    	      "<head> <title>Success</title> </head>" +
	    	      "<body>" +
	    	      "<div class=\"container\">" +
	    	      "<h1>Success!</h1><br>" +
	    	      "<p>" + obj + " successfully " + action + "!</p>" +
	    	      addmore + 
	    	      "<p>To return <a href=\"/adminoptions\">Click here</a></p>" +
	    	      "</div>" +
	    	      "</body>" +
	    	      "</html>"
	    	      );
    	
	    out.close();
	}
	

}
