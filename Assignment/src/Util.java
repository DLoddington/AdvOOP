import java.util.HashMap;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Util {

	 public static HashMap<String, String> requestStringToMap(String request) {
		 
		 HashMap<String, String> map = new HashMap<String, String>();
		 String[] pairs = request.split("&");

		 for (int i = 0; i < pairs.length; i++) {
			 String pair = pairs[i]; 
	     
			 try {
				 String key = pair.split("=")[0];
				 key = URLDecoder.decode(key, "UTF-8");
				 
				 String value = "";
				 if(pair.length() > key.length() + 1) { //accounting for empty data fields + =... will only attempt if there is data
					 value = pair.split("=")[1];
					 value = URLDecoder.decode(value, "UTF-8");
				 }
				 map.put(key, value);
	        
			 }catch (UnsupportedEncodingException e) {
				 System.err.println(e.getMessage());
			 }
		 }return map;
	 }
	 
	 static String getIdFromUrl(String URL) {
			String[] pairs = URL.split("=");
			String id = pairs[1];
			return id;
	 }
	 
	 static public char[] prepareISBN13(String Isbn) {
		 
		 char[] adjIsbn = new char[13];
		 if(Isbn.length() == 10) { //convert from ISBN 10 to 13 by prepending 978
			 adjIsbn[0] = 9;
			 adjIsbn[1] = 7;
			 adjIsbn[2] = 8;
			 for(int i=0;i<Isbn.length();i++) {
				 adjIsbn[(i+3)] = Isbn.charAt(i); 
			 }
		 } else {
			 for(int i=0;i<Isbn.length();i++) {
				 adjIsbn[i] = Isbn.charAt(i); 
			 }
		 }
		 return adjIsbn;
	 }
	 
	 static public boolean isIsbnValid(String Isbn) {
		 
		 int sum = 0;
		 char[] Isbn13 = new char[13];
		 Isbn13 = prepareISBN13(Isbn);
		
		 for(int i=0;i<13;i++) {
			 if(i % 2 == 0) {
				 sum+= Character.getNumericValue(Isbn13[i]);
			 } else {
				 sum+= (Character.getNumericValue(Isbn13[i]) * 3);
			 }
		 }
		 
		 return(sum % 10 == 0);
	 }
	 
	 static public boolean testIsbnIsNum(String Isbn) { //check each character can be numerical
		 
		 for(int i=0;i<Isbn.length();i++) {
			 char c = Isbn.charAt(i);
			 String test = String.valueOf(c);
			 if(!testStringToInt(test)) {
				 return false;
			 }
		 }return true;
	 }
	 
	 static boolean testStringToInt(String inputString) {
			try {Integer.parseInt(inputString);}
			catch (NumberFormatException e) {return false;}
			return true;
	 }
	 
	 static boolean testStringsAllEmpty(String...strings) {
		 boolean allBlank = true;
		 for(String s : strings) {
			 if(!s.isEmpty()) {
				 return false;
			 }
		 }
		 return allBlank;
	 }
	 
	
}
