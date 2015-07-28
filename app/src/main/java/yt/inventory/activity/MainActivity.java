package yt.inventory.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import yt.inventory.R;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();
    public static final String TAG_MAIN_FRAGMENT = TAG + ".MainContainer";

    protected FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }



    public void clearAllData() {

    }

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

}
