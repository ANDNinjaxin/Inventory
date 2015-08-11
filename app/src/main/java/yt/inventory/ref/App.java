package yt.inventory.ref;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.gpshopper.bebe.android.account.Account;
import com.gpshopper.bebe.android.gcm.AppGcmIntentService;
import com.gpshopper.sdk.Config;
import com.gpshopper.sdk.geofence.GeofenceManager;
import com.gpshopper.sdk.geofence.GeofenceManagerPreferences;
import com.gpshopper.sdk.utility.FontsOverride;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.Transform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jc on 7/21/14.
 */
public class App extends Application {



    public static boolean isLoading;
    private static View loadingScreen;

    public static final String TAG = App.class.getCanonicalName();
    public static final String START_LOADING_KEY = TAG + ".StartLoading";
    public static final String STOP_LOADING_KEY = TAG + ".StopLoading";

    public static final int TOAST_LENGTH_LONG = 5;
    public static final int TOAST_LENGTH_SHORT = 2;
    public static final int LOADING_BAR_TIMEOUT = 15;
    private static final ScheduledThreadPoolExecutor loadingExecutor = new ScheduledThreadPoolExecutor(Integer.MAX_VALUE);
    private static final Runnable loadingHider = new Runnable() {
        @Override
        public void run() {
            loadingCount = 1;
            stopLoading();
        }
    };

    // GeoFences
    private static final long GEOFENCE_REFESH_IN_SECONDS = TimeUnit.MINUTES.toSeconds(30);
    private static final long LOCATION_REFRESH_IN_SECONDS = TimeUnit.MINUTES.toSeconds(60);
    private static final long LOCATION_FAST_REFRESH_IN_SECONDS = TimeUnit.MINUTES.toSeconds(10);
    private static final int LOCATION_ACCURACY_IN_METERS = 50;


    private static final String KEY_ACCOUNT = "_key-userid";

    private static App instance;
    private static MainActivity mainActivity;
    private static Set<PropertyChangeListener> changeListeners = new HashSet<>();
    private static int loadingCount;

    private static GoogleCloudMessaging gcm;

    @Override
    public void onCreate() {
        instance = this;

        super.onCreate();

        loadingCount = 0;

        gcm = GoogleCloudMessaging.getInstance(instance);
        registerGcmId();

        final GeofenceManager geofenceManager = GeofenceManager.getInstance(this);
        final GeofenceManagerPreferences geofencePrefs = new GeofenceManagerPreferences();
        geofencePrefs.setGeofenceRefreshPeriodInSeconds(GEOFENCE_REFESH_IN_SECONDS);
        geofencePrefs.setLocationRefreshPeriodInSeconds(LOCATION_REFRESH_IN_SECONDS);
        geofencePrefs.setLocationFastRefreshPeriodInSeconds(LOCATION_FAST_REFRESH_IN_SECONDS);
        geofencePrefs.setLocationAccuracyInMeters(LOCATION_ACCURACY_IN_METERS);
        geofenceManager.setGeofenceManagerPreferences(geofencePrefs);


        setFonts();
    }

    public static enum Font {
        ROBOTO_MEDIUM("font/Roboto-Medium.ttf"),
        ROBOTO_LIGHT("font/Roboto-Light.ttf");

        private final String location;

        Font(String location) {
            this.location = location;
        }

        public String getLocation() {
            return location;
        }
    }

    public void setFont(TextView textView, Font font) {
        Typeface robotoLight = Typeface.createFromAsset(getMainActivity().getAssets(), font.getLocation());
        textView.setTypeface(robotoLight);

    }

    public void setFonts(){
        FontsOverride.setDefaultFont(this, "DEFAULT", "Roboto-Medium.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "Roboto-Medium.ttf");
        FontsOverride.setDefaultFont(this, "DEFAULT_BOLD", "Roboto-Bold.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "Roboto-MediumItalic.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "Roboto-Medium.ttf");
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    public static SharedPreferences getPrefs() {
        return instance.getSharedPreferences(instance.getPackageName(), Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getPrefsEditor() {
        return getPrefs().edit();
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(MainActivity mainActivity) {
        App.mainActivity = mainActivity;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int convertIntToDp(int inputDP) {
        float dp = inputDP;
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);

        return pixels;
    }

    public static Transform getCircleTransform(final int cornerRadius) {
        return new Transform() {
            final boolean isOval = false;

            @Override
            public Bitmap transform(Bitmap bitmap) {
                Bitmap bitmapRounded = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                Canvas canvas = new Canvas(bitmapRounded);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                canvas.drawRoundRect(new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight()), cornerRadius, cornerRadius, paint);
                return bitmapRounded;
            }

            @Override
            public String key() {
                return "rounded_radius_" + cornerRadius + "_oval_" + isOval;
            }
        };
    }

    public static void loadCircularImage(ImageView imageView, int diameter, String imageUrl) {
        Ion.with(imageView).resize(diameter, diameter).centerCrop().transform(getCircleTransform(diameter)).load(imageUrl);
    }

    public static void startLoading(boolean shouldBlockUi) {
        loadingCount++;
        if (loadingCount > 0) {
            notifyChangeListeners(START_LOADING_KEY, false, false);
        }

        loadingExecutor.getQueue().clear();
        loadingExecutor.schedule(loadingHider, LOADING_BAR_TIMEOUT, TimeUnit.SECONDS);
    }

    public static void stopLoading() {
        if (loadingCount < 1) {
            return;
        }

        loadingCount--;
        if (loadingCount == 0) {
            notifyChangeListeners(STOP_LOADING_KEY, false, true);
        }
    }

    public static boolean isLoading() {
        return loadingCount > 0;
    }

    public static void showToast(String message) {
        Toast.makeText(instance, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int messageResourceId) {
        Toast.makeText(instance, messageResourceId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String message, int duration) {
        Toast toast = Toast.makeText(instance, message, Toast.LENGTH_SHORT);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showToast(int messageResourceId, int duration) {
        Toast toast = Toast.makeText(instance, messageResourceId, Toast.LENGTH_SHORT);
        toast.setDuration(duration);
        toast.show();
    }

    public static boolean addChangeListener(PropertyChangeListener listener) {
        return changeListeners.add(listener);
    }

    public static boolean removeChangeListener(PropertyChangeListener listener) {
        return changeListeners.remove(listener);
    }

    private static void notifyChangeListeners(String property, boolean oldValue, boolean newValue) {
        for (PropertyChangeListener listener : changeListeners) {
            listener.propertyChange(new PropertyChangeEvent(instance, property, oldValue, newValue));
        }
    }

    public static void initializeGcm() {
        // TODO Move to SDK; Implement exponential back-off
        String regId = Config.getGcmRegistrationId();
        if (gcm != null && regId != null && regId.equalsIgnoreCase("")) {
            registerGcmId();
        } else {
            Log.i(TAG, "GCM registration ID: " + regId);
        }
    }

    public static void registerGcmId() {
        new AsyncTask<String, Object, String>() {
            @Override
            protected String doInBackground(String... regIds) {
                try {
//                    String regId = gcm.register(instance.getString(R.string.app_gcm_sender_id));
                    String regId = gcm.register(BuildConfig.GCM_SENDER_ID);
                    Log.i(TAG, "GCM registration ID: " + regId);

                    Config.setGcmIntentService(AppGcmIntentService.class);
                    Config.setGcmRegistrationId(regId);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "GCM registration failed: " + e.getMessage());
                }
                return "";
            }
        }.execute(null, null, null);
    }

    public static void unregisterGcmId() {
        new AsyncTask<String, Object, String>() {
            @Override
            protected String doInBackground(String... regIds) {
                try {
                    gcm.unregister();
                    Log.i(TAG, "Unregistered GCM registration ID");

                    Config.setGcmRegistrationId("");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "GCM unregistration failed: " + e.getMessage());
                }
                return "";
            }
        }.execute(null, null, null);
    }

    public static void saveAccount(Account account) {
        SharedPreferences.Editor prefs = getPrefsEditor();
        prefs.putString(KEY_ACCOUNT, new Gson().toJson(account));
        prefs.commit();
    }

    public static Account getAccount() {
        Account result = null;

        if(getPrefs().contains(KEY_ACCOUNT)) {
            String json = getPrefs().getString(KEY_ACCOUNT, "");
            result = new Gson().fromJson(json, Account.class);
        }
        return result;
    }

//    public static boolean userRegistered() {
//        boolean result = false;
//
//        if(getPrefs().contains(KEY_ACCOUNT)) {
//            result = true;
//        }
//
//        return result;
//    }


    public static void startLoadingSpinner( Fragment fragment) {
        if(isLoading) { return; }
        LayoutInflater layoutInflater = LayoutInflater.from(fragment.getActivity());
        if ( fragment != null && fragment.getView() != null ) {
            ViewGroup viewGroup = (ViewGroup) fragment.getView().getParent();
            startLoadingSpinner(viewGroup, layoutInflater);
        }
    }

    //Works inside of onCreateView
    public static void startLoadingSpinner(ViewGroup viewGroup, LayoutInflater inflater) {
        if(isLoading) { return; }
        loadingScreen = inflater.inflate(R.layout.loading_layout, viewGroup, false);
        viewGroup.addView(loadingScreen);
        isLoading = true;
    }

    public static void stopLoadingSpinner(ViewGroup viewGroup) {
        viewGroup.removeView(loadingScreen);
        isLoading = false;
    }

    public static void stopLoadingSpinner(Fragment fragment) {
        final View view = fragment.getView();
        if (view != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            stopLoadingSpinner(viewGroup);
        }
    }

    public static int dpToInt(int desiredDP) {
        final int intSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                desiredDP, getMainActivity().getResources().getDisplayMetrics());
        return intSize;
    }

    public static String makeCurrencyFormat(double tobeFormatted) {

        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();
        String formattedCurrency = dollarFormat.format(tobeFormatted);
        return formattedCurrency.substring(1);
    }

    public static String makeCurrencyFormat(String tobeFormatted) {
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();
        double needsFormatting;
        try {
            needsFormatting = Double.parseDouble(tobeFormatted);
        } catch (NumberFormatException e) {
            Log.v("makeCurrencyFormat: ", "failed");
            return "";
        }
        String formattedCurrency = dollarFormat.format(needsFormatting);
        return formattedCurrency.substring(1);
    }

    public static Typeface getFont(final int resource) {
        final Context context = getContext();
        if (context == null) {
            return Typeface.SERIF;
        }
        return Typeface.createFromAsset(context.getAssets(), context.getString(resource));
    }

    public static Typeface getFontBlack( final Context context ){
        if ( context == null ) { return Typeface.SERIF; }
        return Typeface.createFromAsset(context.getAssets(), "font/Roboto-Medium.ttf");
    }


    public static Typeface getFontBold( final Context context ){
        if ( context == null ) { return Typeface.SERIF; }
        return Typeface.createFromAsset(context.getAssets(), "font/Roboto-Bold.ttf");
    }


    public static Typeface getFontLight( final Context context ){
        if ( context == null ) { return Typeface.SERIF; }
        return Typeface.createFromAsset(context.getAssets(), "font/Roboto-Light.ttf");
    }


    public static Typeface getFontRegular( final Context context ) {
        if ( context == null ) { return Typeface.SERIF; }
        return Typeface.createFromAsset(context.getAssets(), "font/Roboto-Medium.ttf");
    }

    public static Typeface getFontAlternativeRegular( final Context context ) {
        if ( context == null ) { return Typeface.SERIF; }
        return Typeface.createFromAsset(context.getAssets(), "font/Roboto-MediumItalic.ttf");
    }
}
