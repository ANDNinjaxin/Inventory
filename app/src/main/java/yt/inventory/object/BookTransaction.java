package yt.inventory.object;

/**
 * Created by Ninjaxin on 7/29/15.
 */
public class BookTransaction {

    private int studentID;
    private String bookCheckedOut;
    private String bookCheckedIn;
    private Book itemBook;

    public BookTransaction() {
        this.studentID = 0;
        this.bookCheckedOut = "";
        this.bookCheckedIn = "";
        this.itemBook = new Book(); // unnecessary?
    }


    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getBookCheckedOut() {
        return bookCheckedOut;
    }

    public void setBookCheckedOut(String bookCheckedOut) {
        this.bookCheckedOut = bookCheckedOut;
    }

    public String getBookCheckedIn() {
        return bookCheckedIn;
    }

    public void setBookCheckedIn(String bookCheckedIn) {
        this.bookCheckedIn = bookCheckedIn;
    }

    public Book getItemBook() {
        return itemBook;
    }

    public void setItemBook(Book itemBook) {
        this.itemBook = itemBook;
    }
}
