package yt.inventory.object;

import java.util.ArrayList;

/**
 * Created by Ninjaxin on 7/27/15.
 */
public class Student {

    private int id;
    private String lastName;
    private String firstName;
    private String dob;
    private boolean status;
    private int strikes;
    private ArrayList<BookTransaction> studentsBookHistory;

    public Student() {
        this.id = -1;
        this.lastName = "";
        this.firstName = "";
        this.dob = "";
        this.status = false;
        this.strikes = 0;
        this.studentsBookHistory = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
