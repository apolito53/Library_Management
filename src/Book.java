import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {
    
    private String title;
    private String author;
    private String dueDate;
    private SimpleDateFormat dateFormat;
    private User user;
    private int id;
    private Boolean isCheckedOut;

    //Constructor, sets title, author, and id
    public Book(String title, String author, int id) {
        this.title = title;
        this.author = author;
        this.id = id;
        isCheckedOut = false;
        
        dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //Not used, was for
            //setting the due date
    }
    
    //Method for checking out the book, sets the isCheckedOut variable to true
     //and the user variable to what's passed in
    public void checkOut(User user) {
        if(!isCheckedOut) {
            isCheckedOut = true;
            this.user = user;
            System.out.println(title + " is now checked out by " + user.getUsername() + "\n");
        }
    }
    
    //Method for checking in the book. Does the opposite of the checkOut() method
    public void checkIn(User user) {
        if(isCheckedOut) {
            isCheckedOut = false;
            user = null;
            System.out.println("Checked in!\n");
        } else {
            System.out.println("Book is not checked out!\n");    
        }
    }
    
    //Returns the boolean variable isCheckedOut
    public Boolean isCheckedOut() {
        return isCheckedOut;
    } 
    
    //Returns the String representing the possessing user's username
    public String isCheckedOutBy() {
        return user.getUsername();
    }
    
    //Returns the String variable for title
    public String getTitle() {
        return title;
    }
    
    //Returns the string variable for author
    public String getAuthor() {
        return author;
    }
    
    //Returns the integer variable for id
    public int getID() {
        return id;
    }
    
    //For setting the due date, wasn't implemented fully
    public String setDueDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        
        try {
            
            return title + " is due on " + dueDate;
        } catch (Exception ex) {
            return "Something happened";
        }
    }
    
    //Not implemented
    public void extendRental(Book book) {
        String newDate;
        
    }
    
    //Returns the string representation of the Book object, including the title,
     //author, and if it's checked out of not
    @Override
    public String toString() {
        String output = "Title: " + title + "\nAuthor: " + author + "\n";
        if(isCheckedOut) {
            output += "Book is currently unavailable.\n";
        } else {
            output += "Book is currently available.\n";
        }
        
        return output;
    }
}
