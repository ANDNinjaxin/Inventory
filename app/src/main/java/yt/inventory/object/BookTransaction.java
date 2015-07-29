package yt.inventory.object;

/**
 * Created by Ninjaxin on 7/29/15.
 */
public class BookTransaction {

    private String studentID;
    private String bookCheckedOut;
    private String bookCheckedIn;
    private Book itemBook;

    public BookTransaction() {
        this.studentID = "";
        this.bookCheckedOut = "";
        this.bookCheckedIn = "";
        this.itemBook = new Book(); // unnecessary?
    }

}
