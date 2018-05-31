import java.util.ArrayList;

public class Index {
    
    private ArrayList<Book> books = new ArrayList();              //Initialize array of books
    private ArrayList<User> users = new ArrayList();              //Initialize array of users
    
    ////////////////////////////// CONSTRUCTOR ///////////////////////////////////////
    public Index() {
        for(int i = 1; i <= 10; i++) {
            Book book = new Book("Title" + String.valueOf(i), 
                    "Author" + String.valueOf(i), i);
            books.add(book);
        }
        
        Book book = new Book("Harry Potter", "JK Rowling", 100);
        books.add(book);
        
        User user = new User("user", "password", false);
        users.add(user);
        User librarian = new User("admin", "password", true);
        users.add(librarian);
        
        //System.out.println(outputContents(books));
        //System.out.println(outputContents(users));
    }
    /////////////////////////////// END CONSTRUCTOR //////////////////////////////////
    
    //Method for veriyfying a user's login credentials. First checks the passed
     // in username string against all registered users. If a match is found, the
     // passed in password is then checked. If the password matches, the method
     // returns true. If either condition fails, it returns false/
    public boolean verifyUser(String username, String password) {
        if(isInUserIndex(username) && getUser(username).getPassword().equals(password)) {
            return true;
        }
        
        return false;
    }
    
    //Method for adding a new book to the system
    public void addBook(String title, String author, int id) {
        Book book = new Book(title, author, id);
        books.add(book);
    }
    
    //Method for adding a new user to the system
    public void addUser(String username, String password, boolean isLibrarian) {
        User user = new User(username, password, isLibrarian);
        users.add(user);
    }
    
    //Method for removing a book from the system
    public void removeBook(String title) {
        books.remove(getBook(title));
    }
    
    //Method for removing a user from the system
    public void removeUser(String username) {
        users.remove(getUser(username));
    }
    
    //Checks the title passed in against the book index to see if it exists
    public boolean isInBookIndex(String title) {
        System.out.println();
        for(int i = 0; i < books.size(); i++) {
            if(books.get(i).getTitle().equals(title)) {
                return true;
            }
        }
        
        return false;
    }
    
    //Checks the username passed in against the index to see if it exists
    public boolean isInUserIndex(String username) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        
        return false;
    }
    
    //Returns the User object whose username matches the passed in String
    public User getUser(String username) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        
        return null;
    }
    
    //Returns the Book object whose title matches the passed in string
    public Book getBook(String title) {
        for(int i = 0; i < books.size(); i++) {
            if(books.get(i).getTitle().equals(title)) {
                return books.get(i);
            }
        }
        
        return null;
    }
    
    //Returns the ArrayList containing all the books in the system
    public ArrayList getBooks() {
        return books;
    }
    
    //Returns the ArrayList containing all the users in the system
    public ArrayList getUsers() {
        return users;
    }
    
    //Outputs the entire ArrayList's contents
    public String outputContents(ArrayList ar) {
        String strout = "";
        
        for(int i = 0; i < ar.size(); i++) {
            strout += ar.get(i).toString();
        }
        
        return strout;
    }
}