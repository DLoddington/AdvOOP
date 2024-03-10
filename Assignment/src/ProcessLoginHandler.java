import java.io.*;
import java.util.HashMap;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class ProcessLoginHandler implements HttpHandler {
	
	static BooksDAO bookdao = BooksDAO.getInstance();
	
	public void handle(HttpExchange he) throws IOException {
		
		msg("In LoginProcessHandler");
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	   
	    String line;
	    String request = "";
	    while( (line = in.readLine()) != null ){
	      request = request + line;
	    } 
	    
	    msg("request" +request);
	    HashMap<String,String> map = Util.requestStringToMap(request);

	    String username = map.get("username");
	    String password = MD5.getMd5(map.get("password"));
	    
	    if(username.isEmpty()){
	    	he.getResponseHeaders().add("Location", "/login?err1");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    } else if(bookdao.usernameMatch(username) == false) {
	    	he.getResponseHeaders().add("Location", "/login?err2");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    } else if (bookdao.passwordMatch(username, password) == false) {
	    	he.getResponseHeaders().add("Location", "/login?err3");
	    	he.sendResponseHeaders(302, 0);
	    	return;
	    } else if (bookdao.passwordMatch(username, password)){
	    	he.getResponseHeaders().add("Location", "/adminoptions");
	    	he.sendResponseHeaders(302,0);
	    	return;
	    }
	    
	}
	
	static <T> void msg(T t) {
		System.out.println(t);
	}

}
