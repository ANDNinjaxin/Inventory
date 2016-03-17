package yt.inventory.object;

/**
 * Created by Ninjaxin on 7/29/15.
 */
public class BookTransaction {

    private int transactionID;
    private Student studentID;
    private String bookCheckedOut;
    private String bookCheckedIn;
    private Book itemBook;

    public BookTransaction() {
        this.transactionID = 0;
        this.studentID = new Student();
        this.bookCheckedOut = "";
        this.bookCheckedIn = "";
        this.itemBook = new Book(); // unnecessary?
    }

    public BookTransaction(Student studentID,
                           String bookCheckedOut,
                           Book itemBook) {
        this.transactionID = 0;
        this.studentID = studentID;
        this.bookCheckedOut = bookCheckedOut;
        this.bookCheckedIn = "";
        this.itemBook = itemBook;
    }

    public BookTransaction(String bookcheckedIn,
                           Student studentID,
                           Book itemBook) {
        this.transactionID = 0;
        this.studentID = studentID;
        this.bookCheckedOut = "";
        this.bookCheckedIn = "";
        this.itemBook = itemBook;
    }

    public Student getStudentID() {
        return studentID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public void setStudentID(Student studentID) {
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
