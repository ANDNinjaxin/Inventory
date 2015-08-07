package yt.inventory;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

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

    public static ArrayList<Student> getStoredStudentList() {

    }

    public static ArrayList<Book> getStoredBookList() {

    }

    public static ArrayList<BookTransaction> getBookTransactionList() {

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
