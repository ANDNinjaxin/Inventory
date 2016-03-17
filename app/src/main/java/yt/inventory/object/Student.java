package yt.inventory.object;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ninjaxin on 7/27/15.
 */

public class Student implements Serializable{

    private String id;
    private String lastName;
    private String firstName;
    private String dob;
    private boolean status;
    private int strikes;
    private ArrayList<BookTransaction> studentsBookHistory;

    public Student() {
        this.id = "";
        this.lastName = "";
        this.firstName = "";
        this.dob = "";
        this.status = false;
        this.strikes = 0;
        this.studentsBookHistory = new ArrayList<>();
    }

    public Student(String id,
                   String firstname,
                   String lastname,
                   String dob) {

        this.id = id;
        this.firstName = firstname;
        this.lastName = lastname;
        this.dob = dob;
        this.status = true;
        this.strikes = 0;
        this.studentsBookHistory = new ArrayList<>();
    }

    public Student(String firstname,
                   String lastname,
                   String dob) {

        this.id = "";
        this.firstName = firstname;
        this.lastName = lastname;
        this.dob = dob;
        this.status = true;
        this.strikes = 0;
        this.studentsBookHistory = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    public ArrayList<BookTransaction> getStudentsBookHistory() {
        return studentsBookHistory;
    }

    public void setStudentsBookHistory(ArrayList<BookTransaction> studentsBookHistory) {
        this.studentsBookHistory = studentsBookHistory;
    }

    public void addTransaction(BookTransaction bookTransaction) {
        this.studentsBookHistory.add(bookTransaction);
    }

//    public boolean editStudentBookTransaction(BookTransaction bookTransaction,) {
//        if ()
//    }
}
