package yt.inventory.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import yt.inventory.App;
import yt.inventory.R;
import yt.inventory.fragment.HomeFragment;
import yt.inventory.fragment.ImportDataFragment;
import yt.inventory.fragment.ManualInputDataFragment;
import yt.inventory.utility.StarterRoutine;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();
    public static final String TAG_MAIN_FRAGMENT = TAG + ".MainContainer";

    public static final int SCAN_REQUEST_CODE = 0;


    protected FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

        setContentView(R.layout.activity_main);

        App.setMainActivity(this);
        App.initialize();

        if (App.isFirstRun()) {
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

    public void gotoImportData() {
        final Fragment fragment = new ImportDataFragment();
        replaceMainFragment(fragment);
    }

    public void gotoManualImportData() {
        final Fragment fragment = new ManualInputDataFragment();
        replaceMainFragment(fragment);
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

    @Override
    protected void onPause() {
        super.onPause();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        App.saveAllData();

    }
}
