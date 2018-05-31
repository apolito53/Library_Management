import java.util.ArrayList;

public class User {
    
    private String username;
    private String password;
    private Boolean isLibrarian;
    private ArrayList<Book> booksInPossession;
    
    //Constructor
    public User(String username, String password, Boolean isLibrarian) {
        this.username = username;
        this.password = password;
        this.isLibrarian = isLibrarian;
        booksInPossession = new ArrayList();
    }
    
    //Method for checking out book, adds the Book object to the booksInPossession array
    public void checkoutBook(Book book) {
        if(!book.isCheckedOut()) {
            book.checkOut(this);
            booksInPossession.add(book);
        } else {
            System.out.println("That book is not available for checkout.");
        }
    }
    
    //Method for returning book, removes the Book object from the booksInPossession array
    public void returnBook(Book book) {
        booksInPossession.remove(book);
    }
    
    //Returns the String value of username
    public String getUsername() {
        return username;
    } 
    
    //Returns the String value of password
    public String getPassword() {
        return password;
    }
    
    //Returns the boolean value of isLibrarian
    public Boolean isLibrarian() {
        return isLibrarian;
    }
    
    //Sets username to str and outputs a confirmation message
    public void setUsername(String str) {
        username = str;
        System.out.println("Username now set to: " + username + "\n");
    }
    
    //Sets password to str and outputs a confirmation message
    public void setPassword(String str) {
        password = str;
        System.out.println("Password now set to: " + password + "\n");
    }
    //Sets isLibrarian to bool and outputs a confirmation message
    public void setLibrarian(boolean bool) {
        isLibrarian = bool;
        System.out.println("Librarian status now set to: " + isLibrarian + "\n");
    }
    
    //Returns a string representation of the booksInPossession array, or an
     //error message if the user does not have any
    public String getBooksInPossession() {
        String output = "";
        
        if(booksInPossession.isEmpty()) {
            output = "User currently does not possess any books";
        } else {      
            for(int i = 0; i < booksInPossession.size(); i++) {
                output += booksInPossession.get(i).getTitle();
                if(i != booksInPossession.size() - 1) {
                    output += ", ";
                }
            }
        }
        
        return output;
    }
    
    //Checks the title passed in against the user's books to see if it exists
    public boolean userHasBook(String title) {
        System.out.println();
        if(!booksInPossession.isEmpty()) {
            for(int i = 0; i < booksInPossession.size(); i++) {
                if(booksInPossession.get(i).getTitle().equals(title)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    //Returns a string representation of the User, including name, password,
     //librarian status, and the books in possession
    @Override
    public String toString() {
        String strout = this.username + "\n" + this.password + "\n";
        try {
            if(isLibrarian) {
                strout += "Is a Librarian \n";
            }
            if(booksInPossession.isEmpty()) {
                strout += "User currently has no books.\n";
            } else {
                strout += "Books currently in possession: \n" 
                    + booksInPossession.toString() + "\n";
            }
            
        } catch (Exception ex) {}
        
        return strout;
    }
    
    //Not implemented
    public String setDueDate() {
        return "";
    }
}
