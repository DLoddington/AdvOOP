
/**
 * This class models a simple book
 * @author Daniel Smith
 * @version 1.1
 */

public class Book {

	private int book_id; 
	private String title;
	private String author;
	private int year;
	private int edition;
	private String publisher;
	private String isbn;
	private String cover;
	private String condition;
	private int price;
	private String notes;
	
	/**
	 * @param the id of the book within the books table of database stored as the primary keep
	 * @param title the title of the book
	 * @param author the author of the book
	 * @param year the year the book was published
	 * @param edition the printing edition of the book
	 * @param publisher the publishing company of the book
	 * @param isbn the International Standard Book Number related to the book
	 * @param cover cover style of the book e.g. hardback, soft
	 * @param condition the condition of the book
	 * @param price the price of the book in pence
	 * @param notes general notes about the book not captured by the above parameters
	 */
	public Book (String title, String author, int year, int edition, String publisher, String isbn, String cover, String condition, int price, String notes) {
		this.title = title;
		this.author = author;
		this.year = year;
		this.edition = edition;
		this.publisher = publisher;
		this.isbn = isbn;
		this.cover = cover;
		this.condition = condition;
		this.price = price;
		this.notes = notes;
		//don't want to add the book_id in constructor for insertion because the database table auto increments the id
	}
	
	public Book (int book_id, String title, String author, int year, int edition, String publisher, String isbn, String cover, String condition, int price, String notes) {
		this.book_id = book_id;
		this.title = title;
		this.author = author;
		this.year = year;
		this.edition = edition;
		this.publisher = publisher;
		this.isbn = isbn;
		this.cover = cover;
		this.condition = condition;
		this.price = price;
		this.notes = notes;
		//need a second constructor for when fetching a book from database to make a temp book object
	}
	
	public Book (int book_id, String title, String author, int edition, String publisher, String isbn) {
		this.book_id = book_id;
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.publisher = publisher;
		this.isbn = isbn;
		//need a third constructor for advanced searches
	}
	
	/**
	 * @return the book id of the book
	 */
	public int getBook_id() {
		return book_id;
	}
	
	/**
	 * @param book_id sets the identification number of the book object as collected from the database
	 */
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	
	/**
	 * 
	 * @return the title of the book
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 
	 * @param title sets the title of the book object
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 
	 * @return the author of the book object
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 
	 * @param author sets the author of the book object
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * 
	 * @return the year the book was published
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 
	 * @param year sets the year the book was published for the book object
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * 
	 * @return the printing edition of the book
	 */
	public int getEdition() {
		return edition;
	}

	/**
	 * 
	 * @param edition sets the printing edition of the book object
	 */
	public void setEdition(int edition) {
		this.edition = edition;
	}

	/**
	 * 
	 * @return the name of the Publishing company for the book
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * 
	 * @param publisher sets the publishing company name for the book object
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * 
	 * @return the International Standard Book number for the book, used to identify a book 
	 * from another similar book, worldwide
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * 
	 * @param isbn sets the International Standard Book Number for the book object
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * 
	 * @return the type of cover of the book, e.g. hardback
	 */
	public String getCover() {
		return cover;
	}

	/**
	 * 
	 * @param cover set the cover type for the book object
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}

	/**
	 * 
	 * @return the condition of the book
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * 
	 * @param condition sets the condition of the book object
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * 
	 * @return the price in pence (GBP) for the book
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * 
	 * @param price set the price in pence for the book
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * 
	 * @return the book specific notes that covers anything not covered in the above parameters
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * 
	 * @param notes set the notes for the book object
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * create the appearance for the book object when called upon as a string
	 */
	
	@Override
	public String toString() {
		return "Book [book_id=" + book_id + ", title=" + title + ", author=" + author + ", year=" + year + ", edition="
				+ edition + ", publisher=" + publisher + ", isbn=" + isbn + ", cover=" + cover + ", condition="
				+ condition + ", price=" + price + ", notes=" + notes + "]";
	}
	
	
	
}
