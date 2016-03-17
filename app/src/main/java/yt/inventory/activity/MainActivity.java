package yt.inventory.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import yt.inventory.App;
import yt.inventory.R;
import yt.inventory.fragment.CheckoutBookFragment;
import yt.inventory.fragment.CheckoutBookTwoFragment;
import yt.inventory.fragment.DashboardFragment;
import yt.inventory.fragment.HomeFragment;
import yt.inventory.fragment.ImportDataFragment;
import yt.inventory.fragment.ManualInputDataFragment;
import yt.inventory.object.Student;
import yt.inventory.utility.ReadExcelFile;
import yt.inventory.utility.StarterRoutine;


public class MainActivity extends FragmentActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();
    public static final String TAG_MAIN_FRAGMENT = TAG + ".MainContainer";

    public static final int SCAN_REQUEST_CODE = 0;


    protected FragmentManager fragmentManager;

    private TextView tvtoolbarmenu;


    private ListView lvUserDashboard,
            lvAdminDashboard;
    private final String
            CHECK_OUT = "Checkout Book",
            CHECK_IN = "Return Book",
            NEW_STUDENT = "New Student",
            NEW_BOOK = "New Book",
            SWITCH_ADMIN = "Admin Mode",
            SWITCH_USER = "EXIT Admin Mode",
            RESET_PIN = "Reset Pin",
            EDIT_STUDENT = "Edit Student",
            EDIT_BOOK = "Edit Book",
            EDIT_TRANSACTION = "Edit Transaction";


    private String[] adminlist = {
            CHECK_OUT,
            CHECK_IN,
            NEW_STUDENT,
            NEW_BOOK,
            SWITCH_USER,
            EDIT_STUDENT,
            EDIT_BOOK,
            EDIT_TRANSACTION,
            RESET_PIN
    };
    private String[] userlist = {
            CHECK_OUT,
            CHECK_IN,
            NEW_STUDENT,
            NEW_BOOK,
            RESET_PIN,
            SWITCH_ADMIN
    };
    private ArrayAdapter<String> adapteradmin, adapteruser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

        App.setMainActivity(this);
        App.initialize();
//        hideToolbar();
        setContentView(R.layout.activity_main);
        initView();

        if (App.isFirstRun()) {

            long start = System.nanoTime();

            new AsyncLoadData().execute();
//            App.loadAllData();

            long elapsed = System.nanoTime() - start;
            Log.v("benchmark: ", "incode Loaddata:  " + elapsed);

//            ReadExcelFile.readFile(this, App.INTERNAL_FILE_NAME);
            App.getDefaultPin();
            gotoCheckoutBook();
            gotoManualImportData();
            //startupRoutine();

        } else {
            if (App.loadAllData()) {
                gotoHomePage();
            } else {
                gotoImportData();
            }
        }

    }

    private void initView() {
        tvtoolbarmenu = (TextView) findViewById(R.id.toolbarmenu);


        tvtoolbarmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    fragmentManager.popBackStack();
                }
                gotoDashboard();
            }
        });
    }


    public boolean initializeCheckDataValidity() {

        return false;
    }

    public void startupRoutine() {
        new AlertDialog.Builder(this)
                .setTitle(App.string(R.string.no_data))
                .setMessage(App.string(R.string.startup_no_data))
                .setPositiveButton(App.string(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Pull Cloud data
                    }
                })
                .setNegativeButton(App.string(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gotoManualImportData();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void gotoHomePage() {
        final Fragment fragment = new HomeFragment();
        replaceMainFragment(fragment);
    }

//    public void gotoDashboard() {
//        final Fragment fragment = DashboardFragment.newInstance();
//        replaceMainFragment(fragment);
//    }

    public void gotoImportData() {
        final Fragment fragment = new ImportDataFragment();
        replaceMainFragment(fragment);
    }

    public void gotoManualImportData() {
        //TODO: int parameter: 0 or 1 for tab default
        final Fragment fragment = new ManualInputDataFragment();
        replaceMainFragment(fragment);
    }



    public void gotoCheckoutBook() {
        final Fragment fragment = new CheckoutBookFragment();
        replaceMainFragment(fragment);
    }

    public void gotoCheckoutBookTwo(Student studid) {
        final Fragment fragment = CheckoutBookTwoFragment.newInstance(studid);
        replaceMainFragment(fragment);
    }

    public String checkAdminMode() {
        if (App.isAdmin()) {
            return "Admin";
        } else {
            return "MENU";
        }
    }

    public void setTitle(String title) {
        String base = checkAdminMode();
        if ( (title == null) || (title.isEmpty()) ) {
            tvtoolbarmenu.setText(base);
            return;
        }
        if (title.equalsIgnoreCase(" ")) {
            return;
        }
        String ext = " / " + title;
        tvtoolbarmenu.setText(base + ext);
    }

    public void gotoDashboard() {

        adapteradmin = new ArrayAdapter<String>(App.getContext(), R.layout.spinner_item_book_level, adminlist);
        adapteruser = new ArrayAdapter<String>(App.getContext(), R.layout.spinner_item_book_level, userlist);

        lvUserDashboard = (ListView) findViewById(R.id.lvdashboarduser);
        lvAdminDashboard = (ListView) findViewById(R.id.lvdashboardadmin);
        setDashboard();
        lvAdminDashboard.setAdapter(adapteradmin);
        lvUserDashboard.setAdapter(adapteruser);

        setupDashboardListeners();

    }

    private void setupDashboardListeners() {
        lvUserDashboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String choice = lvUserDashboard.getItemAtPosition(position).toString();
                dashboardChoice(choice);
            }
        });

        lvAdminDashboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String choice = lvAdminDashboard.getItemAtPosition(position).toString();
                dashboardChoice(choice);
            }
        });


    }

    private void setDashboard() {
        if (App.isAdmin()) {
            tvtoolbarmenu.setText("DASHBOARD - ADMIN MODE");
            lvAdminDashboard.setVisibility(View.VISIBLE);
            lvUserDashboard.setVisibility(View.GONE);
        } else {
            tvtoolbarmenu.setText("DASHBOARD - USER MODE");
            lvUserDashboard.setVisibility(View.VISIBLE);
            lvAdminDashboard.setVisibility(View.GONE);
        }
    }

    private void hideDashboards() {
        lvAdminDashboard.setVisibility(View.GONE);
        lvUserDashboard.setVisibility(View.GONE);
    }

    private void dashboardChoice(String choice) {
        hideDashboards();

        switch (choice) {
            case CHECK_OUT:
                gotoCheckoutBook();
                gotoCheckoutBook();
                break;
            case CHECK_IN:
                //TODO: RETURNING
                App.showToast("NOT YET IMPLEMENTED!");
                gotoDashboard();
                break;
            case NEW_STUDENT:
                gotoManualImportData();
                break;
            case NEW_BOOK:
                gotoManualImportData();
                break;
            case SWITCH_ADMIN:
                //TODO: AdminLogin()
                App.showToast("NOT YET IMPLEMENTED!");
                gotoDashboard();
                break;
            case SWITCH_USER:
                //TODO: AdminLogin()
                App.showToast("NOT YET IMPLEMENTED!");
                gotoDashboard();
                break;
            case RESET_PIN:
                //TODO: make
                App.showToast("NOT YET IMPLEMENTED!");
                gotoDashboard();
                break;
            case EDIT_STUDENT:
                //TODO: admin mode to edit students from fake DB
                App.showToast("NOT YET IMPLEMENTED!");
                gotoDashboard();
                break;
            case EDIT_BOOK:
                //TODO: admin mode to edit books from fake DB
                App.showToast("NOT YET IMPLEMENTED!");
                gotoDashboard();
                break;
            case EDIT_TRANSACTION:
                //TODO: admin mode to edit transactions from fake DB
                App.showToast("NOT YET IMPLEMENTED!");
                gotoDashboard();
                break;

            default:
                gotoDashboard();
                Log.v(TAG, "Listview choice didn't work");
        }

    }



    /**
     * Scanner options:
     */


    public void scanBarcode() {
        Intent i = new Intent(this, SimpleScannerActivity.class);
        startActivityForResult(i, SCAN_REQUEST_CODE);
    }





    /**
     * Functionality
     * @param fragment
     */
    public void replaceMainFragment(Fragment fragment) {
        Log.d("Fragment Replacement", fragment.getClass().getSimpleName());
        replaceMainFragment(fragment, false);
    }

    public void replaceMainFragment(Fragment fragment, boolean addToBackStack) {
        replaceMainFragment(fragment, addToBackStack, false);
    }

    public void replaceMainFragment(Fragment fragment, boolean addToBackStack, boolean allowStateLoss) {
        replaceMainFragment(fragment, addToBackStack, allowStateLoss, addToBackStack);
    }

    public void replaceMainFragment(final Fragment fragment, final boolean addToBackStack, final boolean allowStateLoss, final boolean animateWithSlide) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (fragment == null) {
                    return;
                }

                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (animateWithSlide) {
                    fragmentTransaction.setCustomAnimations(R.anim.enter_slide_from_left, R.anim.exit_slide_from_left, R.anim.pop_enter_slide_from_left, R.anim.pop_exit_slide_from_left);
                } else {
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                }

                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.replace(getMainContainerId(), fragment, TAG_MAIN_FRAGMENT);

                fragmentTransaction.commitAllowingStateLoss();
            }
        });
    }

    public int getMainContainerId() {
        return R.id.base_frame;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideToolbar() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        long start = System.nanoTime();
        new AsyncSaveData().execute();
        long elapsed = System.nanoTime() - start;
        Log.v("benchmark: ", "onPause/save:  " + elapsed);

        Log.v(TAG, "Paused");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (fragmentManager.getBackStackEntryCount() < 2) {
            fragmentManager.popBackStack();
            gotoDashboard();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.v(TAG, "Destroyed");
    }

    @Override
    protected void onStop() {
        super.onStop();

        long start = System.nanoTime();
        new AsyncSaveData().execute();
        long elapsed = System.nanoTime() - start;
        Log.v("benchmark: ", "onStop/save:  " + elapsed);

        Log.v(TAG, "Stopped");
    }

    private class AsyncSaveData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            long start = System.nanoTime();
            App.saveAllData();
            long elapsed = System.nanoTime() - start;
            Log.v("benchmark: ", "save inside async:  " + elapsed);

            //App.saveAllData();
            return null;
        }
    }

    private class AsyncLoadData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            long start = System.nanoTime();
            App.loadAllData();
            long elapsed = System.nanoTime() - start;
            Log.v("benchmark: ", "load inside async:  " + elapsed);

            //App.loadAllData();
            return null;
        }
    }
}
