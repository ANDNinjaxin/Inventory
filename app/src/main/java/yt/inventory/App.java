package yt.inventory;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.TypedValue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    }

    public static void initialize() {
//        context = mainActivity.getApplicationContext();
        pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

    }

    public static void setMainActivity(MainActivity mainActivity) {
        App.mainActivity = mainActivity;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static Context getContext() {
        return context;
    }

    public static App getInstance() {
        return instance;
    }

    public static String string(int id) {
        return App.getContext().getString(id);
    }

    public static SharedPreferences getPreferences() {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    //duplicate - see which is better naming convention
    public static SharedPreferences prefs() {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
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
     * Save Data
     */
    public static void saveAllData() {

        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();

        String jsonBookList = gson.toJson(bookList);
        String jsonStudentList = gson.toJson(studentList);
        String jsonBookTransactionList = gson.toJson(bookTransactionList);

        editor.putString("booklist", jsonBookList).apply();
        editor.putString("studentlist", jsonStudentList).apply();
        editor.putString("booktransactionlist", jsonBookTransactionList).apply();

        try {

        } catch (Exception e) {

        }

    }

    public static boolean loadAllData() {

        try {
            Gson gson = new Gson();

            String jsonBookList = pref.getString("booklist", null);
            String jsonStudentList = pref.getString("studentlist", null);
            String jsonBookTransactionList = pref.getString("booktransactionlist", null);

            Type typeBook = new TypeToken<ArrayList<Book>>() {
            }.getType();
            Type typeStudent = new TypeToken<ArrayList<Student>>() {
            }.getType();
            Type typeBookTransaction = new TypeToken<ArrayList<BookTransaction>>() {
            }.getType();

            ArrayList<Book> cbookList = gson.fromJson(jsonBookList, typeBook);
            ArrayList<Student> cstudentList = gson.fromJson(jsonStudentList, typeStudent);
            ArrayList<BookTransaction> cbookTransactionList = gson.fromJson(jsonBookTransactionList, typeBookTransaction);

            bookList = cbookList;
            studentList = cstudentList;
            bookTransactionList = cbookTransactionList;

            return true;

        } catch (Exception e) {
            return false;
        }

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
        if (pref.getBoolean("firstrun", true)) {
            return true;

        } else {
            //TODO: enable to for release
            //pref.edit().putBoolean("firstrun", true).commit();
            return false;
        }
    }

    /**
     * functions:
     */
    public static String resString(int resId) {
        return App.getContext()
                .getResources()
                .getString(resId);
    }

    public static int dpToInt(int desiredDP) {
        final int intSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                desiredDP, getMainActivity().getResources().getDisplayMetrics());
        return intSize;
    }

}
