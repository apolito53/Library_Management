/*
Requirements:

Book must have a unique ID                 
    Librarians can add books to the DB
    
User Account control:
    Each user must have a unique ID (username)
    Users must have:
        username
        user ID (maybe)
        telephone number
        address
    System can keep track of user's borrow history
    Librarians can delete a User account
    User can delete their own account
    Librarians can:
        access User accounts
        edit User information
    User can update their own information
    
Circulation control:
    System must be able to track availability of all books
    System must be able to assign a tracking number for all checked out books
        for record keeping purposes
    Users and Librarians can check the availability of any book
    System must be able to update the availability of any book
    Librarians can override the availability status of a book
    Librarians can assign a book to a user's account
    Librarians can assign a due date to a User
    Users can extend the due date of a book
    Librarians can check a book back into the system

Index control:
    Librarians can enter a book into the database
        Title
        Author
        Index number (ID)

Access Control:
    All user accounts must be password protected
    Librarian accounts can access the info in a User account
 
Search capability:
    System must be able to search for a book based on title, author, or ID
    Librarians can search for Users using username, phone number, or rental tracking ID
    Book searches will return availability of book (and due date if checked out)
        and book information. If a librarian searches, the user the book is loaned
        to will also be returned.
    Librarian search for User will return user info and books in possession.
    
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.*;

public class LibraryManagement extends JFrame {

    private Scanner scanner;
    
    private Index index;
    
    private JPanel loginPanel;
    private JLabel lblLogin;
    private JTextField userField;
    private JPasswordField passwordField;
    
    private User currentUser;
    private boolean userIsLoggedIn;
    
    
    public LibraryManagement() {
        index = new Index();              //Create the index of users and books
        initComponents();                       //Initialize components of frame
        
        
        //test();
        signIn();
    }
    
    //////////// Method for instantiating all components and the game window /////////
    private void initComponents() {
        scanner = new Scanner(System.in);
        
        //Main JFrame attributes
        setDefaultCloseOperation(EXIT_ON_CLOSE);     //Game fully exits when closed
        setResizable(false);                         //Player cannot resize window
        setVisible(false);                            //Makes window visible
        setSize(1150, 800);                          //Sets size
        setLayout(null);                             //Sets layout manager to null, 
                                                      //allows manual placement of
                                                      //components within frame
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().width / 5, 
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 10);
                                                     //Places frame in specific location
                                                     
        //JPanel containing login fields                                                     
        loginPanel = new JPanel();
        add(loginPanel);
        loginPanel.setLayout(new BorderLayout());
        loginPanel.setBounds(350, 200, 400, 300);
        
        lblLogin = new JLabel();
        loginPanel.add(lblLogin);
        lblLogin.setText("Welcome! Please sign in: ");
        lblLogin.setFont(lblLogin.getFont().deriveFont((24.0f)));
        lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogin.setVerticalAlignment(SwingConstants.TOP);
        
        userField = new JTextField();
        loginPanel.add(userField);
        userField.setHorizontalAlignment(SwingConstants.CENTER);
        userField.setBackground(Color.red);
        
        //addKeyListener(this);                            //Adds a Key Listener to this frame
    }
    ////////////////////////// END initComponents() //////////////////////////////////
    
    public static void main(String[] args) {
        LibraryManagement lm = new LibraryManagement();
    }
    
    //Method for signing user in, this takes the user's input from the console and
     //checks it against the users array from the index class. If a matching user
     //and password combo is found, the currentUser variable is update to that of
     //whoever is logging in.
    public void signIn() {
        userIsLoggedIn = false;

        System.out.println("Welcome! Please sign in:");
        
        String username;
        String password;
        while(!userIsLoggedIn) {
            System.out.print("Username: ");
            username = scanner.next();
            System.out.print("Password: ");
            password = scanner.next();
            
            if(index.verifyUser(username, password)) {
                userIsLoggedIn = true;
                currentUser = index.getUser(username);
                if(currentUser.isLibrarian()) {
                    System.out.println("Welcome Librarian: " + username + "!\n");
                } else {
                    System.out.println("Welcome " + username + "!\n");
                }
                
                menu();
            } else {
                System.out.println("Invalid username/password\n");
                System.out.println("Please re-enter a valid username and password");
                scanner.nextLine();
            }
        }
    }
    
    //The command list that displays after a user logs in. A basic user can only
     //search for, checkout, and check in books. Librarians have the ability to
     //add or remove books and add/remove/modify user accounts.
    public void menu() {
        System.out.print("What would you like to do? Please enter the number"
                + " of your selection: \n" +
                "0) Sign out\n" +
                "1) Search for a book \n" +
                "2) Checkout a book \n" +
                "22) Return a book\n");
        if(currentUser.isLibrarian()) {
            System.out.print(
                "3) Check status of all books\n" +
                "4) Add a book to the system\n" +
                "5) Remove a book from the system\n" +
                "6) Search for a user\n" +
                "7) Check status of all user accounts\n" +
                "8) Add a user to the system\n" +
                "9) Update a user credentials\n" +
                "10) Remove a user from the system\n");
        }
        
        //Switch statement is used to determine user's input from the console
        switch(scanner.nextInt()) {
            case 0:
                signIn();      //Will return the console to the sign in prompt
                break;
            case 1:
                bookSearch();  //Calls the bookSearch() method
                break;
            case 2:
                checkoutBook(); //Calls the checkoutBook() method
                break;
                
            case 22:
                returnBook();
                
            //The following will only be available if the logged in user is
             // a librarian
            case 3:
                if(currentUser.isLibrarian()) {
                    displayBooks(); //Calls the displayBooks() method
                }
                break;
            case 4:
                if(currentUser.isLibrarian()) {
                    addBook();     //Calls the addBook() method
                }
                break;
            case 5:
                if(currentUser.isLibrarian()) {
                    removeBook(); //Calls the removeBook() method
                }
                break;
            case 6:
                if(currentUser.isLibrarian()) {
                    userSearch(); //Calls the userSearch() method
                }
                break;
            case 7:
                if(currentUser.isLibrarian()) {
                    displayUsers(); //Calls the displayUser() method
                }
                break;
            case 8:
                if(currentUser.isLibrarian()) {
                    addUser();      //Calls the addUser() method
                }
                break;
            case 9:
                if(currentUser.isLibrarian()) {
                    updateUserInfo(); //Calls the updateUserInfo() method
                }
                break;
            case 10:
                if(currentUser.isLibrarian()) {
                    removeUser();     //Calls the removeUserMethod()
                    break;
                }
                
            //The default section will be triggered if a user inputs something invalid
             //Doing so will simply display an error message and redisplay the menu
            default:
                System.out.println("Invalid selection");
                menu();
                break;
        }
        
        menu();
    }
    
    //Used for setting dates, didn't get the time to implement fully
    private void setDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Scanner scanner = new Scanner(System.in);
            String fullDate = "";
            System.out.println("Please input the month (01-12)");
            String str = scanner.next();
            fullDate += str;
            System.out.println("Please input the day (01-31)");
            str = scanner.next();
            fullDate += "/" + str;
            System.out.println("Please input the year (xxxx)");
            str = scanner.next();
            fullDate += "/" + str;
            System.out.println(fullDate);
            Date date = dateFormat.parse(fullDate);
            System.out.println("Today's date is: " + dateFormat.format(date));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    //Method for searching for a book. Currently only supports searching by Title.
     //Takes user input from the console in the form of a string and compares it 
     //against the books array. If a match is found, that book is returned, otherwise
     //an error message is displayed and the user is given the option to search again.
    public Book bookSearch() {
        System.out.print("Please enter the title of the book you are searching for: ");
        scanner.nextLine();
        String temp = scanner.nextLine();
        if(index.isInBookIndex(temp)) {
            System.out.println("Book found: ");
            System.out.println(index.getBook(temp).toString());
            if(currentUser.isLibrarian()) {
                if(index.getBook(temp).isCheckedOut()) {
                    System.out.println("Book is currently checked out by: " +
                        index.getBook(temp).isCheckedOutBy() + "\n");
                } 
            }
            menu();
            return(index.getBook(temp));
        } else {
            System.out.println("The book does not exist. Search again? (y/n)\n");
            switch(scanner.next()) {
                case "y":
                    bookSearch();
                    break;
                case "n":
                    menu();
                    break;
                default:
                    System.out.println("Invalid input, please enter y/n");
            }
        }
        
        return null;
    }
    
    //Method for checking out a book. The system first takes the user's input, checks
     //the book index to make sure it exists, and then calls the relevant checkout methods.
     //If the book is already checked out, the system returns an error message and asks
     // the user if they wish to re-enter.
    public void checkoutBook() {
        System.out.print("Please enter the title of the book you wish to checkout: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        Book book = index.getBook(title);
        
        if(index.isInBookIndex(title)) {
            if(book.isCheckedOut()) {
                System.out.println("Book is already checked out!\n");
                menu();
            } else {
                currentUser.checkoutBook(book);
                book.checkOut(currentUser);
                menu();
            }
        } else {
            System.out.println("The book does not exist. Search again? (y/n)");
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {}
            switch(scanner.next()) {
                case "y":
                    checkoutBook();
                    break;
                case "n":
                    menu();
                    break;
                default:
                    System.out.println("Invalid input, please enter y/n");
            }
        }
    }
    
    //Method for returning a book. Does the same as the checkoutBook() method except
     //it calls the relevant return methods instead.
    public void returnBook() {
        System.out.print("Please enter the title of the book you wish to return: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        Book book = index.getBook(title);
        
        if(currentUser.userHasBook(title)) {
            currentUser.returnBook(book);
            book.checkIn(currentUser);
            System.out.println("Book successfully returned. \n");
            menu();
        } else {
            System.out.println("The user does not possess a book with that title. "
                + "Try again? (y/n)\n");
            switch(scanner.next()) {
                case "y":
                    returnBook();
                    break;
                case "n":
                    menu();
                    break;
                default:
                    System.out.println("Invalid input");
                    menu();
                    break;
            }
        }
        
    }
    
    //Displays all books in the book index to the console (Librarian command)
    public void displayBooks() {
        System.out.println(index.outputContents(index.getBooks()));
        menu();
    }
    
    //Adds a book to the system, ensures the user enters valid information for
     //title, author, and ID before adding them to the book array. If the title
     //of the book already exists in the book array, an error message is displayed.
    public void addBook() {
        String title = "";
        String author = "";
        int id = 0;
        
        System.out.println("To add a new book, please input the following information: ");

        boolean bookExists = true;
        while(bookExists) {
            System.out.print("Title: ");
            scanner.nextLine();
            title = scanner.nextLine();
            if(index.isInBookIndex(title)) {
                System.out.println("That book is already in the system!");
            } else {
                bookExists = false;
            }
        }
        
        System.out.print("Author: ");
        author = scanner.nextLine();
        
        boolean invalidID = true;
        while(invalidID) {
            System.out.print("ID number: ");
            try{
                id = scanner.nextInt();
                invalidID = false;
            } catch (Exception ex) {
                System.out.println("Please input an integer.");
                scanner.next();
            }
        }
        index.addBook(title, author, id);
        
        System.out.println("Book added: ");
        System.out.println(index.getBook(title).toString());
        
        try {
            Thread.sleep(3000);
        } catch(Exception ex) {
            ex.toString();
        }
        menu();
    }
    
    //Method for removing a book. The system checks the user's input against the
     //book array to ensure the book exists before removing it. If a matching book
     //title is not found, the system displays an error message and asks if the user
     //wishes to retry.
    public void removeBook() {
        System.out.println("Please input the title of the book to be removed: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        
        if(index.isInBookIndex(title)) {
            index.removeBook(title);
            System.out.println(title + " removed from system.\n");
            menu();
        } else {
            System.out.println("The book does not exist. Search again? (y/n)");
            switch(scanner.next()) {
                case "y":
                    removeBook();
                    break;
                case "n":
                    menu();
                    break;
                default:
                    System.out.println("Invalid input, please enter y/n");
            }
        }
    }
    
    //Method for searching for a book. Identical to the bookSearch() method except
     //it returns a user instead.
    public User userSearch() {
        System.out.print("Please enter the username you are searching for: ");
        scanner.nextLine();
        String temp = scanner.nextLine();
        if(index.isInUserIndex(temp)) {
            System.out.println("User found: ");
            System.out.println(index.getUser(temp).toString());
            menu();
            return(index.getUser(temp));
        } else {
            System.out.println("That user does not exist. Search again? (y/n)");
            switch(scanner.next()) {
                case "y":
                    userSearch();
                    break;
                case "n":
                    menu();
                    break;
                default:
                    System.out.println("Invalid input, please enter y/n");
                    menu();
                    break;
            }
        }
        
        return null;
    }
    
    //Displays a list of all users (Librarian command)
    public void displayUsers() {
        System.out.println(index.outputContents(index.getUsers()));
        menu();
    }
    
    public void addUser() {
        String username = "";
        String password = "";
        boolean isLibrarian = false;
        
        System.out.println("Please enter the following user credentials: ");

        boolean userExists = true;
        while(userExists) {
            System.out.print("Username: ");
            scanner.nextLine();
            username = scanner.nextLine();
            if(index.isInUserIndex(username)) {
                System.out.println("That user is already in the system!");
            } else {
                userExists = false;
            }
        }
        
        System.out.print("Password: ");
        password = scanner.nextLine();
        
        boolean invalid = true;
        while(invalid) {
            System.out.print("Is this user a Librarian?: (true/false) ");
            try{
                isLibrarian = scanner.nextBoolean();
                invalid = false;
            } catch (Exception ex) {
                System.out.println("Please input either 'true' or 'false'.");
                scanner.next();
            }
        }
        index.addUser(username, password, isLibrarian);
        
        System.out.println("User added: ");
        System.out.println(index.getUser(username).toString());

        menu();
    }
    
    public void updateUserInfo() {
        System.out.println("Enter the username of the user you wish to modify: ");
        User user = null;
        scanner.nextLine();
        String temp = scanner.nextLine();
        if(index.isInUserIndex(temp)) {
            System.out.println("User found: ");
            System.out.println(index.getUser(temp).toString());
            user = index.getUser(temp);
        } else {
            System.out.println("That user does not exist. Search again? (y)");
            switch(scanner.next()) {
                case "y":
                    updateUserInfo();
                    break;
                default:
                    menu();
                    break;
            }
        }
        
        System.out.println("What do you wish to modify? Enter the number of your selection: \n" +
                "1) username \n" +
                "2) password \n" +
                "3) librarian status \n");
        switch(scanner.nextInt()) {
            case 1:
                System.out.println("Please enter the new username: ");
                temp = scanner.next();
                user.setUsername(temp);
                menu();
                break;
            case 2:
                System.out.println("Please enter the new password: ");
                temp = scanner.next();
                user.setPassword(temp);
                menu();
                break;
            case 3:
                System.out.println("Please enter the new status ('true'/'false'): ");
                boolean bool = scanner.nextBoolean();
                user.setLibrarian(bool);
                menu();
                break;
            default:
                System.out.println("Invalid input");
                scanner.next();
                menu();
                break;
        }
    }
    
    public void removeUser() {
        System.out.println("Please input the username of the user to be removed: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        
        if(index.isInUserIndex(username)) {
            index.removeUser(username);
            System.out.println(username + " removed from system.\n");
            menu();
        } else {
            System.out.println("That user does not exist. Search again? (y/n)");
            switch(scanner.next()) {
                case "y":
                    removeBook();
                    break;
                case "n":
                    menu();
                    break;
                default:
                    System.out.println("Invalid input, please enter y/n");
            }
        }
    }
}