package yt.inventory.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import yt.inventory.App;
import yt.inventory.R;
import yt.inventory.fragment.BaseFragment;

/**
 * Created by Ninjaxin on 8/6/2015.
 */
public class StarterRoutine {

//self contained mode:
    public static void setupVars() {

        new AlertDialog.Builder(App.getContext())
                .setTitle(App.string(R.string.no_data))
                .setMessage(App.string(R.string.startup_no_data))
                .setPositiveButton(App.string(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pullCloudData();
                    }
                })
                .setNegativeButton(App.string(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        manuallyEnterData();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public static void loadStoredData() {

    }

    public static void pullCloudData() {

    }

    public static void manuallyEnterData() {

    }

}
