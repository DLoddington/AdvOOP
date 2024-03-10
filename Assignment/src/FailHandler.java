import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class FailHandler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange he) throws IOException {
		
		he.sendResponseHeaders(200,0);
		
	    BufferedWriter out = new BufferedWriter(  
	        new OutputStreamWriter(he.getResponseBody() ));
	    
	    String obj = "";
	    String action = "";
	    
	    if(he.getRequestURI().toString().equals("/fail?ab")) {
			obj = "Book";
	    	action = "added"; 
	    }
	    
	    if(he.getRequestURI().toString().equals("/fail?eb")) {
			obj = "Book";
	    	action = "updated"; 
	    }
	    
	    if(he.getRequestURI().toString().equals("/fail?db")) {
			obj = "Book";
	    	action = "deleted"; 
	    }
	    
	    if(he.getRequestURI().toString().equals("/fail?ac")) {
			obj = "Customer";
	    	action = "added"; 
	    }
	    
	    if(he.getRequestURI().toString().equals("/fail?ec")) {
			obj = "Customer";
	    	action = "updated"; 
	    }
	    
	    if(he.getRequestURI().toString().equals("/fail?dc")) {
			obj = "Customer";
	    	action = "deleted"; 
	    }
	    
	    if(he.getRequestURI().toString().equals("/fail?do")) {
			obj = "Order";
	    	action = "deleted"; 
	    }
	    
	    if(he.getRequestURI().toString().equals("/fail?eos")) {
			obj = "Order status";
	    	action = "updated"; 
	    }
	    
	    if(he.getRequestURI().toString().equals("/fail?ao")) {
			obj = "Order";
	    	action = "added"; 
	    }
	    
	    if(he.getRequestURI().toString().equals("/fail?aol")) {
			obj = "Order line";
	    	action = "added"; 
	    }
	    
	    
    	out.write(
	    	      "<html>" +
	    	      "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
	    	      "<head> <title>Operation failed</title> </head>" +
	    	      "<body>" +
	    	      "<div class=\"container\">" +
	    	      "<h1>Operation failed</h1><br>" +
	    	      "<p>" + obj + " could not be " + action + ".</p>" +  
	    	      "<p>To return <a href=\"/adminoptions\">Click here</a></p>" +
	    	      "</div>" +
	    	      "</body>" +
	    	      "</html>"
	    	      );
    	
	    out.close();
	}
	

}
