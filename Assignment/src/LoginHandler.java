import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;



public class LoginHandler implements HttpHandler{

	
	@Override
	public void handle(HttpExchange he) throws IOException {
		
		he.sendResponseHeaders(200,0);
		
		String usernameError = "";
		String passwordError = ""; 
		
		if(he.getRequestURI().toString().equals("/login?err1"))
			usernameError = "Username cannot be blank, please try again";
		
		if(he.getRequestURI().toString().equals("/login?err2"))
			usernameError = "Username is not recognised, please try again";
		
		if(he.getRequestURI().toString().equals("/login?err3"))
			passwordError = "Password is incorrect, please try again";

		
	    BufferedWriter out = new BufferedWriter(  
	        new OutputStreamWriter(he.getResponseBody() ));
	    
	    out.write(
		    "<html>" +
		    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
		    "<head> <title>Add Book to Library</title> </head>" +
		    "<body>" +
		    "<div class=\"container\">" +
		    "<h1>Admin Login</h1>" +
		    "<table class=\"table\">" +
		    "<thead>" +
		    "  <tr>" +
		    "    <th>Field</th>" +
		    "    <th>Entry</th>" +
		    "  </tr>" +
		    "</thead>" +
		    "<tbody>" +
		    "<form method=\"Post\" action=\"/loginaction\">"
    		);
    			
    			
		out.write(	
			"  <tr>"       +
      		"	 <td>Username</td>" +
      		"    <td><input name=\"username\">" + usernameError + "</td>" +
      		"  </tr>" 	   +
      		"  <tr>"       +
      		"	 <td>Password</td>" +
  		    "    <td><input name=\"password\">" + passwordError + "</td>" +
      		"  </tr>" +
	    	"</tbody>" +
			"</table>" +
			"<input type=\"submit\" value=\"Submit\">  "+
  	      	"</form>" +   
    	    "</div>" +
  	      	"</body>" +
    	    "</html>"
    	    );
	    out.close();
	}

}
