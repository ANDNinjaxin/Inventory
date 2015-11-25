package yt.inventory.object;

import java.util.ArrayList;

/**
 * Created by Ninjaxin on 7/27/15.
 */
public class Book {

    private String id;
    private String bookLevel;
    private String bookID;
//    private int bookNumber;
//    private String bookTitle;
//    private String bookAuthor;
    private boolean isAvailable;
    private ArrayList<String> checkoutHistory;

//    private ArrayList<Student> checkoutHistory;

    public Book() {
        this.id = "";
        this.bookLevel = "";
        this.bookID = "";
//        this.bookNumber = -1;
//        this.bookTitle = "";
//        this.bookAuthor = "";
        this.isAvailable = true;
        this.checkoutHistory = new ArrayList<>();
    }

//    int a=123;
//    System.out.println(String.format("%05d",a));

    public Book(String id,
                String bookLevel,
                String bookNumber,
                boolean isAvailable) {

        this.id = id;
        this.bookLevel = bookLevel;
        this.bookID = bookNumber;
//        this.bookNumber = bookNumber;
//        this.bookTitle = bookTitle;
//        this.bookAuthor = bookAuthor;
        this.isAvailable = isAvailable;
        this.checkoutHistory = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookLevel() {
        return bookLevel;
    }

    public void setBookLevel(String bookLevel) {
        this.bookLevel = bookLevel;
    }

    public String getBookID() {
        return this.bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

//    public int getBookNumber() {
//        return bookNumber;
//    }
//
//    public String getBookTitle() {
//        return bookTitle;
//    }
//
//    public String getBookAuthor() {
//        return bookAuthor;
//    }

//    public void setBookNumber(int bookNumber) {
//        this.bookNumber = bookNumber;
//    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

//    public void setBookTitle(String bookTitle) {
//        this.bookTitle = bookTitle;
//    }
//
//    public void setBookAuthor(String bookAuthor) {
//        this.bookAuthor = bookAuthor;
//    }

    public ArrayList<String> getCheckoutHistory() {
        return checkoutHistory;
    }

    public void setCheckoutHistory(ArrayList<String> checkoutHistory) {
        this.checkoutHistory = checkoutHistory;
    }
}
