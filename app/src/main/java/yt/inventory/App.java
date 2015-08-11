package yt.inventory;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import yt.inventory.activity.MainActivity;
import yt.inventory.object.Book;
import yt.inventory.object.BookTransaction;
import yt.inventory.object.Student;


/**
 * Created by Ninjaxin on 7/28/15.
 */
public class App extends Application {

    private static App instance;
    private static Context context;
    private static MainActivity mainActivity;

    private static SharedPreferences sharedPreference;
    private static SharedPreferences pref;

    private static ArrayList<Student> studentList = new ArrayList<>();
    private static ArrayList<Book> bookList = new ArrayList<>();
    private static ArrayList<BookTransaction> bookTransactionList = new ArrayList<>();





    @Override
    public void onCreate() {

        instance = this;
        context = instance;

        super.onCreate();


//        sharedPreference = context.getSharedPreferences(
//                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

    }


    public static void setMainActivity(MainActivity mainActivity) {
        App.mainActivity = mainActivity;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static Context getContext() {
        return instance;
    }

    public static App getInstance() {
        return instance;
    }

    public static SharedPreferences getPreferences() {
        return instance.getSharedPreferences(instance.getPackageName(), Context.MODE_PRIVATE);
    }

    //duplicate - see which is better naming convention
    public static SharedPreferences prefs() {
        return instance.getSharedPreferences(instance.getPackageName(), Context.MODE_PRIVATE);
    }



    public void setBoolean(String string, boolean page) {
        pref.edit().putBoolean(string, page).commit();
    }

    /*
            Data Handle
    */
    public static ArrayList<Student> getStudentList() {
        return studentList;
    }

    public static void setStudentList(ArrayList<Student> sList) {
        studentList = sList;
    }

    public static ArrayList<Book> getBookList() {
        return bookList;
    }

    public static void setBookList(ArrayList<Book> bList) {
        bookList = bList;
    }

    public static ArrayList<BookTransaction> getBookTransactionList() {
        return bookTransactionList;
    }

    public static void setBookTransactionList(ArrayList<BookTransaction> transList) {
        bookTransactionList = transList;
    }

    /**
     *      Save Data
     */
    public static void saveAllData() {
//        Set<Student> hsetStudent = new HashSet<>();
//        Set<Book> hsetBook = new HashSet<>();
//        Set<BookTransaction> hsetBookTransaction = new HashSet<>();
//
//        hsetStudent.addAll(studentList);
//        hsetBook.addAll(bookList);
//        hsetBookTransaction.addAll(bookTransactionList);

        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookList);


        try {

        } catch(Exception e) {

        }


        /*

        public static final String CONNECTIONS = "connections";
SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();

User entity = new User();
// ... set entity fields

List<Connection> connections = entity.getConnections();
// convert java object to JSON format,
// and returned as JSON formatted string
String connectionsJSONString = new Gson().toJson(connections);
editor.putString(CONNECTIONS, connectionsJSONString);
editor.commit();




String connectionsJSONString = getPreferences(MODE_PRIVATE).getString(CONNECTIONS, null);
Type type = new TypeToken < List < Connection >> () {}.getType();
List < Connection > connections = new Gson().fromJson(connectionsJSONString, type);

         */








    }

    /*
            ClearData
     */
    public static void clearStudentList() {
        studentList.clear();
    }

    public static void clearBookList() {
        bookList.clear();
    }

    public static void clearBookTransactionsList() {
        bookTransactionList.clear();
    }

    public static void clearAllLocalData() {
        clearStudentList();
        clearBookList();
        clearBookTransactionsList();
    }

    public static void clearAllCloudData() {
        //TODO wipe app storage data on gdrive
    }
    public static boolean isFirstRun() {
        if (pref.getBoolean("firstrun", false)) {
            pref.edit().putBoolean("firstrun", true).commit();
            return false;

        } else {
            return true;
        }
    }



}
