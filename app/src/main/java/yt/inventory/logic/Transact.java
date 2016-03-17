package yt.inventory.logic;

import android.util.Log;

import yt.inventory.App;
import yt.inventory.object.Book;
import yt.inventory.object.BookTransaction;
import yt.inventory.object.Student;

/**
 * Created by Ninjaxin on 11/28/2015.
 */
public class Transact {

    private static final String TAG = "Transact.class";

    /*
    Checkout:   update App.studentlist
                update App.book
                create new booktransaction

    Checkin:        Documented Book:
                update App.studentlist.booktransaction
                update App.book
                update booktransaction
                    Undocumented Book:
                create App.book
                create booktransaction
                create App.studentlist.booktransaction

    BookTransaction:
                always generate unique id

     */


    public static boolean checkoutBook(String studentid, String bookid) {
        Student student = Logic.findStudent(studentid);
        String datestamp = Logic.getFullDate();
        Book book = Logic.findBook(bookid);
        if (student == null) {
            App.showToast("ERROR! Student Mismatch");
            return false;
        }
        if (book == null) {
            App.showToast("ERROR! Book Mismatch");
            return false;
        }
        BookTransaction bookTransaction = new BookTransaction(student, datestamp, book);

        App.addBookTransaction(bookTransaction);
        if (App.editBook(bookid, false)
            && App.checkoutStudentList(studentid, bookTransaction) ) {
            Log.v(TAG, "good transact");
            App.showToast("Good Info");
        } else {
            Log.v(TAG, "Bad transact");
            App.showToast("ERROR in Checkout!");
        }

        App.showToast("Successfully checked out!");
        return true;
    }

    public static boolean returnBook(String bookid) {
        Book book = Logic.findBook(bookid);
        if (book == null) {
            App.showToast("ERROR! No book found!");
            return false;
        }
        if (book.isAvailable()) {
            App.showToast("That book was never checked out!");
            return false;
        }



        return false;



    }

}
