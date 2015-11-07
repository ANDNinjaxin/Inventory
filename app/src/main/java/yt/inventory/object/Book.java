package yt.inventory.object;

import java.util.ArrayList;

/**
 * Created by Ninjaxin on 7/27/15.
 */
public class Book {

    private int id;
    private String bookLevel;
    private int bookNumber;
    private boolean isAvailable;
    private ArrayList<String> checkoutHistory;

//    private ArrayList<Student> checkoutHistory;

    public Book() {
        this.id = -1;
        this.bookLevel = "";
        this.bookNumber = -1;
        this.isAvailable = true;
        this.checkoutHistory = new ArrayList<>();
    }

//    int a=123;
//    System.out.println(String.format("%05d",a));

    public Book(int id,
                String bookLevel,
                int bookNumber,
                boolean isAvailable) {

        this.id = id;
        this.bookLevel = bookLevel;
        this.bookNumber = bookNumber;
        this.isAvailable = isAvailable;
        this.checkoutHistory = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookLevel() {
        return bookLevel;
    }

    public void setBookLevel(String bookLevel) {
        this.bookLevel = bookLevel;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public ArrayList<String> getCheckoutHistory() {
        return checkoutHistory;
    }

    public void setCheckoutHistory(ArrayList<String> checkoutHistory) {
        this.checkoutHistory = checkoutHistory;
    }
}
