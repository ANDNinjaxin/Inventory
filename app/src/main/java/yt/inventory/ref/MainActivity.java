//package yt.inventory.ref;
//
//
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.res.Configuration;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.Drawable;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.preference.PreferenceManager;
//import android.provider.Settings;
//import android.support.v4.app.ActionBarDrawerToggle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.view.inputmethod.InputMethodManager;
//import android.webkit.CookieSyncManager;
//import android.webkit.WebView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.gpshopper.bebe.android.BuildConfig;
//import com.gpshopper.bebe.android.Metrics;
//import com.gpshopper.bebe.android.R;
//import com.gpshopper.bebe.android.WebviewMetrics;
//import com.gpshopper.bebe.android.account.AccountManager;
//import com.gpshopper.bebe.android.browse.BrowseRepository;
//import com.gpshopper.bebe.android.fragment.AccountParentFragment;
//import com.gpshopper.bebe.android.fragment.BrowseFragment;
//import com.gpshopper.bebe.android.fragment.CategoriesFragment;
//import com.gpshopper.bebe.android.fragment.ContactUsFragment;
//import com.gpshopper.bebe.android.fragment.DemoFragment;
//import com.gpshopper.bebe.android.fragment.FilterFragment;
//import com.gpshopper.bebe.android.fragment.GiftCardFragment;
//import com.gpshopper.bebe.android.fragment.HomeFragment;
//import com.gpshopper.bebe.android.fragment.ProductCheckAvailabilityFragment;
//import com.gpshopper.bebe.android.fragment.ProductFragment;
//import com.gpshopper.bebe.android.fragment.ProductFullScreenImageFragment;
//import com.gpshopper.bebe.android.fragment.ProductInfoPagerFragment;
//import com.gpshopper.bebe.android.fragment.ProductsListFragment;
//import com.gpshopper.bebe.android.fragment.SocialFragment;
//import com.gpshopper.bebe.android.fragment.SortFragment;
//import com.gpshopper.bebe.android.fragment.SplashFragment;
//import com.gpshopper.bebe.android.fragment.StoresFragment;
//import com.gpshopper.bebe.android.fragment.WebViewFragment;
//import com.gpshopper.bebe.android.fragment.WishListFragment;
//import com.gpshopper.bebe.android.fragment.WishlistFullScreenImageFragment;
//import com.gpshopper.bebe.android.manager.BannerManager;
//import com.gpshopper.bebe.android.manager.ToolbarManager;
//import com.gpshopper.bebe.android.network.Reservation;
//import com.gpshopper.bebe.android.objects.BebeProduct;
//import com.gpshopper.bebe.android.objects.ColorFilter;
//import com.gpshopper.bebe.android.objects.PriceFilter;
//import com.gpshopper.bebe.android.objects.Root;
//import com.gpshopper.bebe.android.objects.SizeFilter;
//import com.gpshopper.bebe.android.view.ScriptActionHandler;
//import com.gpshopper.sdk.Callback;
//import com.gpshopper.sdk.Config;
//import com.gpshopper.sdk.Request;
//import com.gpshopper.sdk.Session;
//import com.gpshopper.sdk.banner.Banner;
//import com.gpshopper.sdk.banner.BannersCallback;
//import com.gpshopper.sdk.banner.BannersRequest;
//import com.gpshopper.sdk.banner.widget.BannerView;
//import com.gpshopper.sdk.catalog.CategoriesCallback;
//import com.gpshopper.sdk.catalog.CategoriesRequest;
//import com.gpshopper.sdk.catalog.Category;
//import com.gpshopper.sdk.catalog.Product;
//import com.gpshopper.sdk.catalog.ProductCallback;
//import com.gpshopper.sdk.catalog.ProductRequest;
//import com.gpshopper.sdk.gcm.GcmIntentFactory;
//import com.gpshopper.sdk.geofence.GeofenceManager;
//import com.gpshopper.sdk.store.Store;
//import com.gpshopper.sdk.utility.Tools;
//import com.newrelic.agent.android.NewRelic;
//import com.readystatesoftware.systembartint.SystemBarTintManager;
//
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//
//// TODO Write proper static Fragment instantiation methods (e.g., newInstance() to pass arguments shorthand)
//// TODO Decouple most Fragments from each other
//public class MainActivity extends ActionBarActivity implements PropertyChangeListener, DemoFragment.OnFragmentInteractionListener, ScriptActionHandler {
//
//    public static final int SCAN_REQUEST_CODE = 0;
//    public static final int ACCOUNT_REQUEST_CODE = 1;
//
//    public static final String TAG = MainActivity.class.getCanonicalName();
//    public static final String TAG_MAIN_FRAGMENT = TAG + ".MainContainer";
//    private static boolean poppingBackStack = false;
//
//    private Handler mHandler = new Handler();
//
//    private String mSessionId;
//
//    protected FragmentManager fragmentManager;
//    protected Toolbar actionBar;
//    protected View loadingBar;
//    private View loadingBlock;
//    private Animation fadeInAnimation;
//    private Animation fadeOutAnimation;
//
//    private final String subnavTextColor = "#909090";
//
//    private boolean menuShowing = false;
//    private String title = "";
//    private int cornerRadius;
//
//    private Location location;
//    public static Integer CTID;
//    private CookieSyncManager cookieSyncManager;
//
//    //==== SIDE NAV / SECTIONS ====
//    public static final String SPLASH = "SPLASH";
//    public static final String ACCOUNT = "My Account";
//
//    public static final String HOME = "HOME";
//    public static final String SHOP = "SHOP";
//    public static final String SOCIAL = "SOCIAL";
//    public static final String STORES = "STORES";
//    public static final String SCAN = "SCAN";
//
//    public static final String CONTACT_US = "CONTACT US";
//    public static final String SHIPPING = "SHIPPING";
//    public static final String RETURNS = "RETURNS";
//    public static final String GIFT_CARDS = "GIFT CARDS";
//    public static final String SIZE_CHART = "SIZE CHART";
//    public static final String PRIVACY = "PRIVACY";
//    public static final String TERMS_OF_USE = "TERMS OF USE";
//
//    // GeoFences
//    private GeofenceManager geofenceManager;
//
//
//    private String[] sections = {
//            HOME,
//            SHOP,
//            SOCIAL,
//            STORES,
//            SCAN,
//            CONTACT_US,
//            SHIPPING,
//            RETURNS,
//            GIFT_CARDS,
//            SIZE_CHART,
//            PRIVACY,
//            TERMS_OF_USE
//    };
//    private int[] sideNavInactiveIcons = {
//            R.drawable.inactive_menu_home,
//            R.drawable.inactive_menu_shop,
//            R.drawable.inactive_menu_social,
//            R.drawable.inactive_menu_stores,
//            R.drawable.inactive_menu_scan,
//    };
//    private int[] sideNavActiveIcons = {
//            R.drawable.active_menu_home,
//            R.drawable.active_menu_shop,
//            R.drawable.active_menu_social,
//            R.drawable.active_menu_stores,
//            R.drawable.active_menu_scan,
//    };
//
//    private ArrayList<View> sideNavItems = new ArrayList<>();
//    // ==== ==== ====
//
//    private boolean mockLocationOn = false;
//
//
//    private DrawerLayout mDrawerLayout;
//    private ListView mDrawerList;
//    private ActionBarDrawerToggle mDrawerToggle;
//
//
//    public static Map<String, List<Banner>> bannerListMap = new HashMap<>();
//
//
//
//    private FrameLayout mHostFragment;
//
//    public void goToStoreLocator() {
//        onNavigationDrawerItemSelected(STORES);
//    }
//
//    public void goToStores(ArrayList<Product.Child> instances, BebeProduct parent, Store store) {
//        replaceMainFragment(StoresFragment.newInstance(instances, parent, store));
//    }
//
//    public void goToStoreOnMap(Store store) {
//
//    }
//
//    public interface BackPressedListener{
//        public boolean onBackPressed();
//    }
//
//    public static interface NavigationDrawerCallbacks {
//        void onNavigationDrawerItemSelected(String position);
//    }
//
//    public static final String LOGGED_IN = "loggedin";
//    public static final String LOC_SERVICE = "loc_service";
//    public static final String PN_ENABLED = "pn_enabled";
//    public static final String GEO_LOCATION = "geolocation";
//
//    public static final String ROOTED = "rooted_device";
//    public static final String MOCK_LOCATIONS = "mock_location";
//
//    //
//    public static void addCustomVariables( final Context context ) {
//
////        final String locServiceOn = RegisterFragment.isLocationServicesOn( context ) ? "Yes" : "No";
//        final String regForPush = "Yes";
//
//        final String isRooted = Root.isDeviceRooted() ? "Yes" : "No";
//        final String isMockLocations = MainActivity.isMockSettingsON( context ) ? "Yes" : "No";
//
//        //TODO - see if this is importaint
////        Config.setCustomVariable( LOC_SERVICE, locServiceOn );
//        Config.setCustomVariable( PN_ENABLED, regForPush );
//
//        Config.setCustomVariable( ROOTED, isRooted );
//        Config.setCustomVariable( MOCK_LOCATIONS, isMockLocations );
//    }
//
//    public int getStatusBarHeight() {
//        int result = 0;
//        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }
//
//    private void sendDeviceSession(){
//        AccountManager manager = AccountManager.getInstance(this);
//        manager.makeDeviceSessionRequest(null);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        NewRelic.withApplicationToken("AA6bd4d7075e853c366cc5b367b9184093718204d5").start(this.getApplication());
//
//        App.setMainActivity(this);
//        WebviewMetrics.setMainActivity(this);
//
//        sendDeviceSession();
//
//
//        // GeoFences
//        geofenceManager = GeofenceManager.getInstance(getApplicationContext());
//
//
//        int playServicesResultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        if (playServicesResultCode != ConnectionResult.SUCCESS) {
//            GooglePlayServicesUtil.getErrorDialog(playServicesResultCode, this, 0, new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialogInterface) {
//                    // TODO Display a message, and then kill the app?
//                }
//            }).show();
//        }
//
//        final Integer clientId = BuildConfig.APP_CTID;
//        final String clientName = getString(R.string.app_dev_name);
//        final String clientKey = getString(R.string.app_dev_key);
//        final String host = BuildConfig.APP_HOSTNAME;
//        Config.initialize(this, clientId, clientName, clientKey, host);
//        addCustomVariables(getApplicationContext());
//        CTID = clientId;
//
//        Config.setImageHost(host);
//
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        setContentView(getLayoutId());
//
//        // Fix for devices running version numbers under kitkat
//        final int versionNumber = Build.VERSION.SDK_INT;
//        final int minVersionForTransparentStatusBar = Build.VERSION_CODES.KITKAT;
//        if (versionNumber < minVersionForTransparentStatusBar) {
//            findViewById(R.id.toolbarSpace2).setVisibility(View.GONE);
//        }
//
//        restartSession();
//        AccountManager.getInstance(this).addChangeListener(this);
//
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_base_layout);
//
//        fragmentManager = getSupportFragmentManager();
//
//        actionBar = (Toolbar) findViewById(R.id.activity_base_app_bar);
//
//        setSupportActionBar(actionBar);
//
//        // create our manager instance after the content view is set
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        // enable status bar tint
//        tintManager.setStatusBarTintEnabled(true);
//        // enable navigation bar tint
//        tintManager.setNavigationBarTintEnabled(true);
//        // set the transparent color of the status bar, 20% darker
//        tintManager.setTintColor(Color.parseColor("#20000000"));
//
////        actionBar.setPadding(0, getStatusBarHeight(), 0, 0);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        setUpToolbar();
//
//        //@dimen/actionbar_height
//        mDrawerLayout.setPadding(0, getStatusBarHeight(), 0, 0);
////        actionBar.setPadding(0, getStatusBarHeight(), 0, 0);
//        //
//
//        loadingBar = findViewById(R.id.activity_loading_bar);
//        loadingBlock = findViewById(R.id.activity_loading_block);
//        loadingBlock.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//
//        App.addChangeListener(this);
//
//        fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
//        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                loadingBar.setVisibility(View.VISIBLE);
//                loadingBlock.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {}
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {}
//        });
//
//        fadeOutAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
//        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {}
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                loadingBar.setVisibility(View.GONE);
//                loadingBlock.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {}
//        });
//
//        //setNavDrawer();
//        mHostFragment = (FrameLayout) findViewById(R.id.base_frame);
//
//        //config Sidenav
//        initSideNav();
//
//        requestAccount();
//
//        BannerManager.getInstance().loadAllBanners();
//
//        ArrayList<BannerView> bannerViews = new ArrayList<>();
//        bannerViews.add((BannerView) findViewById(R.id.sidenav_banner));
//        BannerManager.getInstance().displayBanners(this, BannerManager.Section.SIDENAV, bannerViews);
////        requestBanners();
//        // === ===
//
//        onNavigationDrawerItemSelected(SPLASH);
//
//        if (Tools.isAirplaneModeEnabled()) {
//            //showSimpleDialog(R.string.airplane_mode_title, R.string.airplane_mode_text);
//        }
//
//        //actionBar.setDisplayHomeAsUpEnabled(true);
//
//        App.getInstance().setFonts();
//        cornerRadius = getResources().getDimensionPixelSize(R.dimen.dashboard_account_member_portrait_diameter);
//
//        Intent launchIntent = getIntent();
//        if (launchIntent != null) {
//            onNewIntent(launchIntent);
//        }
//
//        setOverscroll();
//        fetchCategories();
////        fetchPromoBanner();
//        syncCookies();
//        fetchCartItems();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        checkIfHasInternet();
//        geofenceManager.startService();
//    }
//
//    private void syncCookies() {
//        if(cookieSyncManager == null) {
//            cookieSyncManager = CookieSyncManager.createInstance(this);
//        }
//        cookieSyncManager.sync();
//    }
//
//    private void fetchCartItems(){
//        if (AccountManager.getInstance(getApplicationContext()).isLoggedIn()){
//            AccountManager.getInstance(getApplicationContext()).makeCartFetchCall();
//        }
//    }
//
//
//    private View.OnClickListener sideNavClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            final int selectedTag = (int) v.getTag();
//            for (View view : sideNavItems) {
//                final int itemTag = (int) view.getTag();
//                if (itemTag == selectedTag) {
//                    selectSection(view);
//                } else {
//                    deselectSection(view);
//                }
//            }
//        }
//    };
//
//
//    private void initSideNav(){
//        final LinearLayout sideNavLayout = (LinearLayout) findViewById(R.id.navDrawer_layout);
//        final LinearLayout navItemsLayout = (LinearLayout) findViewById(R.id.nav_items_layout);
//        populateSideNav(navItemsLayout, sections, sideNavInactiveIcons);
//        Button button = (Button) sideNavLayout.findViewById(R.id.navToAccountButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onNavigationDrawerItemSelected(ACCOUNT);
//                closeNavDrawer();
//            }
//        });
//        selectSection(sideNavItems.get(0));
//
//        final TextView welcomeText = (TextView) findViewById(R.id.welcome_text);
//        final TextView nameText = (TextView) findViewById(R.id.name_text);
//        final TextView accountButton = (TextView) findViewById(R.id.navToAccountButton);
//
//        final AccountManager accountManager = AccountManager.getInstance(this);
//
//        accountManager.addLogInListener(mLoginListener);
//
//        accountManager.addLogoutListener(mLogoutListener);
//    }
//
//    private AccountManager.LoggedInListener mLoginListener = new AccountManager.LoggedInListener() {
//        @Override
//        public void onLoggedIn() {
//
//            AccountManager manager = AccountManager.getInstance(MainActivity.this);
//            findViewById(R.id.welcome_text).setVisibility(View.VISIBLE);
//            ((TextView)findViewById(R.id.name_text)).setText(manager.getAccountInfo().getFirstName());
//            ((TextView) findViewById(R.id.navToAccountButton)).setText("MY ACCOUNT");
//
//
//            manager.addLogoutListener(mLogoutListener);
//
//        }
//
//        @Override
//        public void onLoggedFails(String msg) {
//
//        }
//    };
//
//    private AccountManager.LoggedOutListener mLogoutListener = new AccountManager.LoggedOutListener(){
//        @Override
//        public void onLoggedOut() {
//            AccountManager manager = AccountManager.getInstance(MainActivity.this);
//
//            findViewById(R.id.welcome_text).setVisibility(View.INVISIBLE);
//            ((TextView)findViewById(R.id.name_text)).setText("WELCOME");
//            ((TextView) findViewById(R.id.navToAccountButton)).setText("SIGN IN");
//
//            manager.addLogInListener(mLoginListener);
//
//        }
//    };
//
//    private void selectSection(View view) {
//        final int index = (int) view.getTag();
//
//        TextView text = (TextView) view.findViewById(R.id.navText);
//        ImageView image = (ImageView) view.findViewById(R.id.navImage);
//
//        text.setTextColor(getResources().getColor(R.color.gold));
//        if (image != null) {
//            image.setImageDrawable(getResources().getDrawable(sideNavActiveIcons[index]));
//        }
//
//        onNavigationDrawerItemSelected(sections[index]);
//        closeNavDrawer();
//
//    }
//
//    private void deselectSection(View view) {
//        final int index = (int) view.getTag();
//        view.setBackgroundColor(getResources().getColor(R.color.apptheme_color));
//
//        TextView text = (TextView) view.findViewById(R.id.navText);
//        ImageView image = (ImageView) view.findViewById(R.id.navImage);
//
//        text.setTextColor(Color.rgb(0x00, 0x00, 0x00));
//        if (image != null) {
//            image.setImageDrawable(getResources().getDrawable(sideNavInactiveIcons[index]));
//        } else {
//            text.setTextColor(Color.parseColor(subnavTextColor));
//        }
//    }
//
//    private void populateSideNav(LinearLayout layout, String[] sectionNames, int[] sectionIcons) {
//        sideNavItems.clear();
//        for (int index = 0; index < sectionIcons.length; index++) {
//            View view = getLayoutInflater().inflate(R.layout.nav_item, null);
//            view.setTag(index);
//
//            TextView text = (TextView) view.findViewById(R.id.navText);
//            ImageView image = (ImageView) view.findViewById(R.id.navImage);
//
//            text.setText(sectionNames[index]);
//            image.setImageDrawable(getResources().getDrawable(sectionIcons[index]));
//
//            view.setOnClickListener(sideNavClickListener);
//            layout.addView(view);
//            sideNavItems.add(view);
//        }
//        if (sectionNames.length > sectionIcons.length) {
//            View separator = getLayoutInflater().inflate(R.layout.horizontal_separator, null);
//            layout.addView(separator);
//
//            for (int index = sectionIcons.length; index < sectionNames.length; index++) {
//                View view = getLayoutInflater().inflate(R.layout.nav_item_simple, null);
//                view.setTag(index);
//
//                TextView text = (TextView) view.findViewById(R.id.navText);
//
//                text.setTextColor(Color.parseColor(subnavTextColor));
//                text.setText(sectionNames[index]);
//
//                view.setOnClickListener(sideNavClickListener);
//                layout.addView(view);
//                sideNavItems.add(view);
//            }
//        }
//    }
//
//    protected void openNavDrawer() {
//        View drawer = (View) findViewById(R.id.base_nav_drawer);
//        mDrawerLayout.openDrawer(drawer);
//        hideKeyBoard();
//    }
//
//    protected void closeNavDrawer() {
//        View drawer = (View) findViewById(R.id.base_nav_drawer);
//        mDrawerLayout.closeDrawer(drawer);
//    }
//
//    public void hideKeyBoard(){
//        try{
//            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            if ( inputManager != null ){
//                inputManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }catch( Exception e ){
//            Log.d("", e.getMessage());
//        }
//    }
//
//    @Override
//    public void onPause(){
//        super.onPause();
//
//        if ( isApplicationSentToBackground( this ) ){
//            //finish();
//        }
//    }
//
//    public static boolean isApplicationSentToBackground(final Context context) {
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
//        if (!tasks.isEmpty()) {
//            ComponentName topActivity = tasks.get(0).topActivity;
//            if (!topActivity.getPackageName().equals(context.getPackageName())) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    @Override
//    protected void onDestroy() {
//        Config.destroy();
//
//        super.onDestroy();
//    }
//
//    private Intent pendingIntent;
//    public Intent getPendingIntent() { return pendingIntent; }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        handleNewIntent(intent);
//    }
//
//    public void handleNewIntent(Intent intent) {
//
//        final String scriptType = intent.getStringExtra(GcmIntentFactory.SCRIPT_TYPE_KEY);
//        final String scriptValue = intent.getStringExtra(GcmIntentFactory.SCRIPT_VALUE_KEY);
//        final String messageId = intent.getStringExtra( GcmIntentFactory.MESSAGE_ID );
//
//        if (scriptType != null && scriptValue != null) {
//
//            //
//
//            handleScriptAction(scriptType, scriptValue);
//
//            pendingIntent = intent;
//
//            Metrics.trackSection("Push", scriptType, scriptValue, messageId );
//        }
//    }
//
//    public void showToast(String message){
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//
//
//    public void onNavigationDrawerItemSelected( String position ) {
//
//
//        Fragment fragment = null;
//        final Bundle arguments = new Bundle();
//
//
//        WebviewMetrics.menu(position);
//
//        Metrics.trackSection("Side Navigation", position);
//
//        switch (position) {
//
//            case SPLASH:
//                fragment = SplashFragment.newInstance();
//                break;
//
//            case HOME:
//                fragment = HomeFragment.newInstance();
//                break;
//
//            case SHOP:
//                //TODO - set Fragment newInstance methods
//                fragment = new CategoriesFragment();
//                break;
//
//            case SOCIAL:
//                fragment = new SocialFragment();
//                break;
//
//            case STORES:
//                fragment = StoresFragment.newInstance();
//                break;
//
//            case SCAN:
//                startCodeScanner();
//                return;     // don't drop out of the switch and replace the main fragment
//
//            case ACCOUNT:
//                fragment = new AccountParentFragment();
//                break;
//
//            //========== 2nd section ==========
//            case CONTACT_US:
//                fragment = new ContactUsFragment();
//                break;
//
//            case SHIPPING:
//                fragment = getWebView(R.string.shippingUrl);
//                break;
//
//            case RETURNS:
//                fragment = getWebView(R.string.returnsUrl);
//                break;
//
//            case GIFT_CARDS:
//                fragment = new GiftCardFragment();
//                break;
//
//            case SIZE_CHART:
//                fragment = getWebView(R.string.sizeChartUrl);
//                break;
//
//            case PRIVACY:
//                fragment = getWebView(R.string.privacyUrl);
//                break;
//
//            case TERMS_OF_USE:
//                fragment = getWebView(R.string.termsUrl);
//                break;
//
//            default:
//                fragment = new HomeFragment();
//                //fragment = new OldBrowseFragment();
//                break;
//        }
//
//
//
//        replaceMainFragment(fragment);
//    }
//
//
//    public void startCodeScanner() {
//        Metrics.trackSection("Scan");
//        Intent intent = new Intent(this, ScanActivity.class);
//        startActivityForResult(intent, SCAN_REQUEST_CODE);
//    }
//
//    public void goToProductCheckAvailability(BebeProduct product, ArrayList<Store> instanceStores) {
//        ProductCheckAvailabilityFragment fragment = ProductCheckAvailabilityFragment.newInstance(product, instanceStores);
//        replaceMainFragment(fragment);
//    }
//
//    public void goToWishlist() {
//        replaceMainFragment(WishListFragment.newInstance());
//    }
//
//    public void goToWishlistFullScreenFragment(Product product, int colorIndex, int imageIndex){
//        WishlistFullScreenImageFragment fragment = WishlistFullScreenImageFragment.newInstance(new BebeProduct(product), colorIndex, imageIndex);
//        replaceMainFragment(fragment);
//    }
//
//    public void goToProductFullScreenFragment(Product product, int colorIndex, int imageIndex) {
//        ProductFullScreenImageFragment fragment = ProductFullScreenImageFragment.newInstance(new BebeProduct(product), colorIndex, imageIndex);
//        replaceMainFragment(fragment);
//    }
//
//    public void goToProductDetailsFragment(BebeProduct product) {
//        ProductInfoPagerFragment fragment = ProductInfoPagerFragment.getInstance(product);
//        replaceMainFragment(fragment);
//    }
//
//
//    public void goToSortFragment(ProductsListFragment listFragment) {
//        SortFragment fragment = SortFragment.newInstance();
//        fragment.setProductsListFragment(listFragment);
//        replaceMainFragment(fragment, false, false, true);
//    }
//
//    public void goToFilterFragment(ArrayList<ColorFilter> colorFilters, ArrayList<PriceFilter> priceFilters, ArrayList<SizeFilter> sizeFilters, ProductsListFragment parentFramgnet) {
//        replaceMainFragment(FilterFragment.newInstance(colorFilters, priceFilters, sizeFilters, parentFramgnet), false, false, true);
//    }
//
//    public void goToBrowseFragment(Category parentCategory, int pageToOpenTo) {
//        replaceMainFragment(BrowseFragment.getInstance(parentCategory, pageToOpenTo));
//    }
//
//    public void goToProductDisplayPage(Product product) {
//        replaceMainFragment(ProductFragment.newInstance(new BebeProduct(product)));
//        //replaceMainFragment(OldProductFragment.newInstance(product.getId(), product.getTitle(), product.getRegularPrice()));
//    }
//
//    public void goToWebPage(String url, String data){
//        replaceMainFragment(WebViewFragment.newInstance(url, data));
//    }
//
//    public void goToSearchPageWithQuery(String query) {
//        ProductsListFragment fragment = ProductsListFragment.getInstance(query, true);
//        replaceMainFragment(fragment);
//    }
//
//    public void goToFilteredSearchPage(String query, ArrayList<ColorFilter> selectedColors, ArrayList<PriceFilter> selectedPrices, ArrayList<SizeFilter> selectedSizes) {
//        ProductsListFragment fragment = ProductsListFragment.newInstance(query, selectedColors, selectedPrices, selectedSizes);
//        replaceMainFragment(fragment);
//    }
//
//    public void goToSectionByString(String sectionName) {
//        Fragment fragment = null;
//        final Bundle arguments = new Bundle();
//
//        if (sectionName.equalsIgnoreCase("home")) {
//            fragment = HomeFragment.newInstance();
//        } else if (sectionName.equalsIgnoreCase("shop")) {
////            fetchCategories();
////            ArrayList<Category> categories = (ArrayList<Category>) BrowseRepository.getData();
////            if (!handleCategories(categories, sectionName)) {
////                Toast.makeText(this, getString(R.string.barcode_failed), Toast.LENGTH_LONG);
////            }
//            fragment = new CategoriesFragment();
//        } else if (sectionName.equalsIgnoreCase("browse")) {
//            fragment = new CategoriesFragment();
//        } else if (sectionName.equalsIgnoreCase("social")) {
//            fragment = new SocialFragment();
//        } else if (sectionName.equalsIgnoreCase("stores")) {
//            fragment = new StoresFragment();
//        } else if (sectionName.equalsIgnoreCase("scan")) {
//            startCodeScanner();
//        } else if (sectionName.equalsIgnoreCase("wishlist")) {
//            goToWishlist();
//        } else if (sectionName.equalsIgnoreCase("bag")) {
//            goToCart();
//        } else if (sectionName.equalsIgnoreCase("account")) {
//            goToAccount();
//        } else if (sectionName.equalsIgnoreCase("contact")) {
//            replaceMainFragment(new ContactUsFragment());
//        }
//
//        if (fragment != null) {
//            replaceMainFragment(fragment);
//        }
//    }
//
//
//    public void goToCart() {
//        Fragment fragment = getWebViewRelativePath(R.string.cartUrl);
//        replaceMainFragment(fragment);
//    }
//
//    private void goToAccount(){
//
//        AccountParentFragment account = AccountParentFragment.newInstance();
//
//        replaceMainFragment(account);
//
//    }
//
//    public void replaceMainFragment(Fragment fragment) {
//        Log.d("Fragment Replacement", fragment.getClass().getSimpleName());
//        replaceMainFragment(fragment, false);
//    }
//
//    public void replaceMainFragment(Fragment fragment, boolean addToBackStack) {
//        replaceMainFragment(fragment, addToBackStack, false);
//    }
//
//    public void replaceMainFragment(Fragment fragment, boolean addToBackStack, boolean allowStateLoss) {
//        replaceMainFragment(fragment, addToBackStack, allowStateLoss, addToBackStack);
//    }
//
//    public void replaceMainFragment(final Fragment fragment, final boolean addToBackStack, final boolean allowStateLoss, final boolean animateWithSlide) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (fragment == null) {
//                    return;
//                }
//
//                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                if (animateWithSlide) {
//                    fragmentTransaction.setCustomAnimations(R.anim.enter_slide_from_left, R.anim.exit_slide_from_left, R.anim.pop_enter_slide_from_left, R.anim.pop_exit_slide_from_left);
//                } else {
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
//                }
//
////                if (addToBackStack) {
//                fragmentTransaction.addToBackStack(null);
////                }
//
//                fragmentTransaction.replace(getMainContainerId(), fragment, TAG_MAIN_FRAGMENT);
//
////                if (allowStateLoss) {
//                fragmentTransaction.commitAllowingStateLoss();
////                } else {
////                    fragmentTransaction.commit();
////                }
//            }
//        });
//    }
//
//    public static Map<String, List<Banner>> getBannerMap(){
//        return bannerListMap;
//    }
//
//
//    private void fetchCategories(){
//        CategoriesRequest request = new CategoriesRequest();
//
//        request.make(new CategoriesCallback() {
//            @Override
//            public void onComplete(Object result) {
//
//
//                List<Category> categories = (List<Category>) result;
//                BrowseRepository.putData(categories);
//
//            }
//        });
//    }
//
//
//    //use for given relative path
//
//    public WebViewFragment getWebViewRelativePath(final int relativePathResource) {
//        String basePath = BuildConfig.APP_HOSTNAME;
//        String relativePath = getString(relativePathResource);
//        String fullPath = basePath + relativePath;
//
//        WebViewFragment fragment;
//        fragment = getWebView(fullPath);
//
//        return fragment;
//    }
//
//    //use for full http path
//    private WebViewFragment getWebView(final String url) {
//
//        return WebViewFragment.newInstance(url);
//    }
//
//    //use for string resource
//    private WebViewFragment getWebView( final int urlResource ){
//
//        WebViewFragment fragment;
//
//        final String url = getResources().getString( urlResource );
//        fragment = getWebView(url);
//
//        return fragment;
//    }
//
//
//    //only for metrics
//    public void oMetrics(String oURL) {
//        WebView omnitureWebView;
//        omnitureWebView = (WebView) findViewById(R.id.oMetricsWebView);
//        omnitureWebView.loadUrl(oURL);
//    }
//
////    private View actionBarView;
////    private float actionBarHeight;
//
//    private TextView actionSearchOkButton;
//
//
//    public void openActionbarSearch() {
//
//        ToolbarManager.getInstance(this).openSearchLayout();
//
////        actionSearchLayout.setVisibility(View.VISIBLE);
////        actionLogoImage.setVisibility(View.GONE);
////        actionTitleText.setVisibility(View.GONE);
////        actionSearchOkButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                final EditText searchText = (EditText) actionSearchLayout.findViewById(R.id.actionbar_search_edittext);
////                closeActionBarSearch();
////                goToSearchPageWithQuery(searchText.getText().toString());
////            }
////        });
////        final TextView cancelButton = (TextView) actionSearchLayout.findViewById(R.id.actionbar_search_cancel);
////        cancelButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                closeActionBarSearch();
////            }
////        });
////
////        actionBarView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) actionBarHeight * 2));
////        actionBar.invalidate();
//    }
//
//    public void closeActionBarSearch() {
//        ToolbarManager.getInstance(this).closeSearchLayout();
////        actionSearchLayout.setVisibility(View.GONE);
////        actionLogoImage.setVisibility(View.VISIBLE);
////        actionTitleText.setVisibility(View.VISIBLE);
////        search.setVisible(true);
////        locate.setVisible(true);
////        bag.setVisible(true);
//
////        actionBarView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) actionBarHeight));
//    }
//
//    private boolean showTitle = false;
//    private boolean actionBarSetup = true;
//    private boolean showBackButton = false;
//    private String sectionTitle;
//
//    public void setSectionTitle(String sectionTitle) {
//        this.sectionTitle = sectionTitle;
//    }
//
//    public void setShowTitle(boolean showTitle) {
//        this.showTitle = showTitle;
//    }
//
//
//    public void setActionBarSetup(boolean set) {
//        actionBarSetup = set;
//    }
//
//
//    public void setShowBackButton(boolean showBackButton) {
//        this.showBackButton = showBackButton;
//    }
//
////    TextView actionTitleText;
////    ImageView actionLogoImage;
////    ImageView actionBackButton;
////    ImageView actionMenuButton;
////    View actionSearchLayout;
//
//    public void updateActionBar() {
//
//        if (actionBarSetup) {
//            ToolbarManager.getInstance(this).resetToolbarToDefault();
//
//        }
//    }
//
//
//    private EditText getSearchEditText() {
//        return searchEditText;
//    }
//
//    public void setSearchActionListener(final ToolbarManager.SearchListener searchListener) {
//        ToolbarManager.getInstance(this).setSearchListener(searchListener);
////        searchEditText.setOnEditorActionListener(listener);
////        actionSearchOkButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                getSearchEditText().onEditorAction(EditorInfo.IME_ACTION_SEARCH);
////            }
////        });
//    }
//
//    TextView.OnEditorActionListener defaultSearchActionListener = new TextView.OnEditorActionListener() {
//        @Override
//        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//            goToSearchPageWithQuery(v.getText().toString());
//            findViewById(R.id.actionbar_search_layout).setVisibility(View.GONE);
//            return true;
//        }
//    };
//
//    public void restoreSearchActionListener() {
//        searchEditText.setOnEditorActionListener(defaultSearchActionListener);
//    }
//
//    EditText searchEditText;
//
//    Toolbar includedToolbar;
//    LinearLayout headerBar;
//    FrameLayout toolbarSpace;
//
//    private void setUpToolbar(){
//        ToolbarManager toolbarManager = ToolbarManager.getInstance(this);
//
//        toolbarManager.setMenuDefaultOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openNavDrawer();
//            }
//        });
//        toolbarManager.setBackDefaultOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        toolbarManager.setSearchDefaultListener(new ToolbarManager.SearchListener() {
//            @Override
//            public void performSearch(EditText editText) {
//                goToSearchPageWithQuery(editText.getText().toString());
//            }
//        });
//
//
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        switch (id) {
//            //TODO - case for each icon
//            //case R.id.action_search:
//                //ToolbarManager.getInstance(this).switchSearchBar();
//                //break;
//        }
//
//        return true;
//        //return super.onOptionsItemSelected(item);
//    }
//
//    private void requestAccount() {
//
//        AccountManager.getInstance(this).loginWithSavedCreds();
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        // TODO Account for dialogs and other modals
//        final Fragment topFragment = fragmentManager.findFragmentByTag(TAG_MAIN_FRAGMENT);
//        if (topFragment instanceof WebViewFragment) {
//            final WebView webView = ((WebViewFragment) topFragment).getWebView();
//
//            boolean goback = webView.canGoBack();
//            if (!goback) {
//                super.onBackPressed();
//            }
//        } else if (topFragment instanceof BackPressedListener ) {
//
//            BackPressedListener listener = (BackPressedListener) topFragment;
//
//            if(listener.onBackPressed())
//                return;
//        }
//
//        restoreActionBar();
//        syncCookies();
//        fetchCartItems();
//
//        super.onBackPressed();
//    }
//
//    public int getLayoutId() {
//        return R.layout.activity_drawer_layout;
//    }
//
//    public View getActionbarSpace() {
//        return findViewById(R.id.toolbarSpace);
//    }
//
//    public int getMainContainerId() {
//        return R.id.base_frame;
//    }
//
//    private boolean isMainContainerEmpty() {
//        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_main_container);
//        return fragment == null;
//    }
//
//    private void restoreActionBar() {
//    }
//
//    public void setBarSpaceVisible(boolean visible){
//        findViewById(R.id.toolbarSpace).setVisibility(visible ? View.VISIBLE : View.GONE);
//    }
//
//    public void resetNavigationBar() {
//        ViewGroup bar = (ViewGroup) findViewById(R.id.navigationBar);
//
//        bar.removeAllViews();
//
//        bar.invalidate();
//    }
//
//    public void setNavigationBar(View view){
//        ViewGroup bar = (ViewGroup) findViewById(R.id.navigationBar);
//
//        bar.removeAllViews();
//
//        if(view == null)
//            return;
//
//        bar.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        bar.invalidate();
//    }
//
//    public View getHeaderBar(){
//        return findViewById(R.id.headerBar);
//    }
//
//
//
//    //
//    private void setOverscroll(){
//        if ( android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.KITKAT ){
//
//            final int brandColor = getResources().getColor( android.R.color.black );
//            //glow
//            int glowDrawableId = getResources().getIdentifier("overscroll_glow", "drawable", "android");
//            Drawable androidGlow = getResources().getDrawable(glowDrawableId);
//            androidGlow.setColorFilter(brandColor, PorterDuff.Mode.MULTIPLY);
//            //edge
//            int edgeDrawableId = getResources().getIdentifier("overscroll_edge", "drawable", "android");
//            Drawable androidEdge = getResources().getDrawable(edgeDrawableId);
//            androidEdge.setColorFilter(brandColor, PorterDuff.Mode.MULTIPLY);
//        }
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == SCAN_REQUEST_CODE) {
//            onScanResult(resultCode, data);
//            return;
//        }
//
//    }
//
//    private void onScanResult(int resultCode, Intent data){
//        if (resultCode != Activity.RESULT_OK) {
//            return;
//        }
//
//        String contents = data.getStringExtra(ScanActivity.BARCODE_CONTENTS);
//
//        try {
//
//
//            Integer.parseInt(contents);
//
//
//
//            ProductsListFragment fragment = ProductsListFragment.getInstance(contents, true);
//            replaceMainFragment(fragment);
//
//        } catch (NumberFormatException e) {
//            if (Patterns.WEB_URL.matcher(contents).matches()) {
//                processBarcode(contents);
//
////                Fragment fragment;
////                fragment = getWebView(contents);
////                replaceMainFragment(fragment);
//
//            } else {
//                Toast.makeText(this, getString(R.string.barcode_failed), Toast.LENGTH_LONG).show();
//            }
//
//        }
//
//    }
//
//    private void processBarcode(String url) {
//        int bannerID = 0;
//        try {
//            int lastIndex = url.lastIndexOf("=");
//            String cacheID = url.substring(lastIndex, url.length());
//            if (cacheID.length() > 0) {
//                cacheID = cacheID.substring(1, cacheID.length());
//            }
//            Log.v("substring: ", cacheID);
//
//            final int itemID = Integer.parseInt(cacheID);
//
//            BannersRequest bannersRequest = new BannersRequest("barcode");
//            bannersRequest.make(new BannersCallback() {
//                @Override
//                public void onComplete(Object result) {
//                    Map<String, List<Banner>> bannerListMap;
//                    bannerListMap = (Map<String, List<Banner>>) result;
//
//                    Log.v("bannerList: ", "here");
//
//                    String scriptType = "";
//                    String scriptValue = "";
//                    if ((bannerListMap.size() > 0)
//                            && (bannerListMap.containsKey("barcode"))
//                            && (bannerListMap.get("barcode") != null)
//                            && (!bannerListMap.get("barcode").isEmpty())
//                            && (bannerListMap.get("barcode").get(0) != null)) {
//
//                        for (int i = 0; i < bannerListMap.get("barcode").size(); i++) {
//                            if (bannerListMap.get("barcode").get(i).getId() == itemID) {
//                                Log.v("founditem: ", "here");
//
//                                scriptValue = bannerListMap.get("barcode").get(i).getScriptValue().toString();
//                                scriptType = bannerListMap.get("barcode").get(i).getScriptType().toString();
//
//                                handleScriptAction(scriptType, scriptValue);
//                            }
//                        }
//
//                    }
//
//                }
//            });
//
//        } catch (NumberFormatException e) { }
//
//        if (bannerID == 0) {
//            Toast.makeText(this, getString(R.string.barcode_failed), Toast.LENGTH_LONG);
//        } else {
//
//        }
//    }
//
//    @Override
//    public void propertyChange(PropertyChangeEvent event) {
//        final String propertyName = event.getPropertyName();
//        if (propertyName == null) {
//            return;
//        }
//
//        if(propertyName.equalsIgnoreCase(Session.ID_KEY)) {
//            mSessionId = (String) event.getNewValue();
//            Log.i(TAG, String.format("Session id set to: %s", mSessionId));
//        }
//        else if (propertyName.equalsIgnoreCase(App.START_LOADING_KEY)) {
//            startLoading((Boolean) event.getNewValue());
//        } else if (propertyName.equalsIgnoreCase(App.STOP_LOADING_KEY)) {
//            stopLoading();
//        }
//    }
//
//    // TODO If already loading a non-blocking request, and a new block request is made, only fade in the blocker.
//    public void startLoading(final boolean shouldBlockUi) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                loadingBar.startAnimation(fadeInAnimation);
//
////                if (shouldBlockUi) {
//                loadingBlock.startAnimation(fadeInAnimation);
////                }
//            }
//        });
//    }
//
//    public void stopLoading() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                loadingBar.startAnimation(fadeOutAnimation);
//
//                if (loadingBlock.getVisibility() == View.VISIBLE) {
//                    loadingBlock.startAnimation(fadeOutAnimation);
//                }
//            }
//        });
//    }
//
//    public void setSelectedSection(int id) {
//
//    }
//
//    //get any product by productID
//    public void findProduct(long productID) {
//        final ProductRequest request = new ProductRequest(productID, 25.0, 0L, false);
//        request.make(new ProductCallback() {
//            @Override
//            public void onComplete(Object result) {
//                Product tempProduct = (Product) result;
//                goToProductDisplayPage(tempProduct);
//            }
//        });
//    }
//
//    private boolean handleCategories(ArrayList<Category> allCategories, String sectionName) {
//        if (null != allCategories  &&  !allCategories.isEmpty()) {
//            for (int i = 0; i < allCategories.size(); i++) {
//                if (allCategories.get(i).getTitle().equalsIgnoreCase(sectionName)) {
//                    goToBrowseFragment(allCategories.get(i), 0);
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//
//    public void handleScriptAction(String type, String value) {
//        if (type == null) {
//            type = "";
//        }
//        if (value == null) {
//            value = "";
//        }
//
//        // TODO Consolidate dashboard menu handler and this
//
//        Fragment fragment = null;
////        Bundle arguments = new Bundle();
////        boolean addToBackStack = !isMainContainerEmpty();
//
//
//        if ( "NA".equalsIgnoreCase( type ) ) {
//            return;
//        }
//
//        if (type.equalsIgnoreCase("product")) {
//            try {
//                Long id = Long.parseLong(value);
//                this.productId = id;
//                findProduct(id);
//
//            } catch(NumberFormatException e) {
//                Log.e(TAG, "Error handling script: " + e.getMessage());
//            }
//        } else if (type.equalsIgnoreCase("pop up")) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage(value)
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            //do things
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//
//        } else if (type.equalsIgnoreCase("contest")) {
//            //TODO: not for BEBE 4.0
//
//        } else if (type.equalsIgnoreCase("url")) {
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(value));
//            startActivity(i);
////            fragment = getWebView(value);
//
//            //Front Page banner
//        } else if (value.equalsIgnoreCase("shop")) {
//            goToSectionByString(value);
//
//        } else if ( (type.equalsIgnoreCase("search"))) {
//            goToSearchPageWithQuery(value);
//
//        } else if (type.equalsIgnoreCase("section")) {
//            goToSectionByString(value);
//            return;
//
//        } else if (type.equalsIgnoreCase("search")) {
//            goToSearchPageWithQuery(value);
//
//        } else if ( (type.equalsIgnoreCase("home")) ) {
//            goToHome();
//        }
//
//        //specific for home?
//
//        if (fragment != null)
//            replaceMainFragment(fragment);
//    }
//
//
//            // TODO: Placeholder.
//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
//
//
//
//    /*
//
//    Commented out for now. Please don't erase in case we want to go down this path again.
//
//    //
//    private void getInZoneZipcodes() {
//
//
//        String url = getString( R.string.zicodeUrl );
//        Ion.with(this).load( url ).asString().setCallback( new FutureCallback<String>() {
//
//            @Override
//            public void onCompleted(Exception e, String result) {
//
//                HomeFragment.setZipcodeListString( result );
//
//            }
//        });
//
//
//
//
//    }
//
//
//    */
//
//
//    //
//    public Location getLocation(){
//        return location;
//    }
//
//
//    public void goToHome() {
//        goToSectionByString(HOME);
//    }
//
//
//
//    public static final String DEFAULT_ZIP = "99999";
//
//    String suffix = "";
//    public String getSuffix() { return suffix; }
//
//    private String zipcodeListString;
//    public String getZipcodeListString() { return zipcodeListString; }
//
//
//    //
//    private boolean isNotStagingZip( final String zip ) {
//
//        final String ctid = "220";//getString( R.string.app_client_id );
//        final int ctidInt = Integer.parseInt(ctid);
//        if ( ctidInt == 220 ) {
//
//            final boolean fakeZip = "60602".equals( zip )
//                                    || "60603".equals( zip )
//                                    || "97217".equals( zip );
//
//            return ! fakeZip;
//        }
//
//        return true;
//    }
//
//    public static String getZipcode(Activity activity){
//
//
//        final Geocoder geocoder = new Geocoder( activity , Locale.getDefault() );
//
//
//        try {
//
//            Location location = ( (MainActivity) activity ).getLocation();
//
//
//            final double lat = location.getLatitude();
//            final double lon = location.getLongitude();
//
//            final List<Address> addresses = geocoder.getFromLocation( lat, lon, 1 );
//
//            if ( addresses != null ){
//
//                for ( int i = 0; i < addresses.size(); i++ ){
//
//                    final Address add = addresses.get( i );
//                    if ( add.getPostalCode() != null ){
//
//                        return add.getPostalCode();
//                    }
//
//                }
//
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch( Exception ex ) {
//
//            return DEFAULT_ZIP;
//        }
//
//        return DEFAULT_ZIP;
//
//    }
//
//
//    public static String loadTextFile(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//        byte[] bytes = new byte[4096];
//        int len = 0;
//        while ((len = inputStream.read(bytes)) > 0)
//            byteStream.write(bytes, 0, len);
//        return new String(byteStream.toByteArray(), "UTF8");
//    }
//
//
//
//
//    //
//    private long productId;
//    public void setProductId( final String productId ) {
//        this.productId = Long.parseLong(productId);
//    }
//    public long getProductId() { return  productId; }
//
//
//
//
//
//    //
//    private boolean checkIfMockLocations(){
//
//
//        if ( isMockSettingsON( this ) ) {
//
//            showStopDialog( R.layout.mock_locations_popup );
//            Metrics.trackSection( "Error", "Mock Locations User" );
//            return true;
//
//        } else if (Root.isDeviceRooted() ) {
//
//            showStopDialog( R.layout.rooted_device_popup );
//            Metrics.trackSection( "Error", "Rooted User" );
//            return true;
//        }
//
//        return false;
//    }
//
//
//
//
//
//    //
//    private void showStopDialog( final int layoutId ) {
//
//
//        final Dialog dialog = new Dialog( this );
//        dialog.setContentView( layoutId );
//        dialog.setCancelable( false );
//        int divierId = dialog.getContext().getResources()
//                .getIdentifier("android:id/titleDivider", null, null);
//        View divider = dialog.findViewById(divierId);
//        divider.setBackgroundColor(getResources().getColor( android.R.color.black ) );
//
//        dialog.show();
//
//    }
//
//
//
//    //
//    public static boolean isMockSettingsON( Context context) {
//
//        String s = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION);
//        if (s.equals("0") ) {
//            return false;
//        } else {
//            return true;
//
//        }
//    }
//
//    public void popBackStack() {
//        getSupportFragmentManager().popBackStack();
//    }
//
//    public static boolean isPoppingBackStack() {
//        return poppingBackStack;
//    }
//
//
//
//    public void showSimpleDialog(String title, String message) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        if (title != null && title.length() > 0) {
//            dialog.setTitle(title);
//        }
//        dialog.setMessage(message)
//                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {}
//                });
//        dialog.show();
//    }
//
//    public void showSimpleDialog(Integer titleResourceId, int messageResourceId) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        if (titleResourceId != null) {
//            dialog.setTitle(titleResourceId);
//        }
//        dialog.setMessage(messageResourceId)
//                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//        dialog.show();
//    }
//
//    private boolean hasReservation(){
//        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        return prefs.getBoolean(Reservation.RESERVATION, false);
//    }
//
//
//    @Override
//    public boolean onPrepareOptionsMenu( Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        //mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//
//    //
////    private void selectItem( final String section ){
////
////        if ( callbacks != null ){
////            callbacks.onNavigationDrawerItemSelected( section );
////        }
////    }
//
//
//
//
//    /**
//     * Callbacks interface that all activities using this fragment must implement.
//     */
//
//
//
//
//    public void allowDrawerToSwipe() {
//        mDrawerLayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_UNLOCKED );
//    }
//
//    public void stopDrawerFromSwiping() {
//        mDrawerLayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED );
//    }
//
//    public String getmSessionId() {
//        return mSessionId;
//    }
//
//
//
//
//    //
//    private void checkIfHasInternet() {
//
//        if ( ! isNetworkAvailable( this ) ) {
//
//
//            showSimpleDialog( getString( R.string.noInternetTitle ), getString( R.string.noInternetMessage ) );
//        }
//
//    }
//
//
//
//    //
//    private static boolean isNetworkAvailable( final Context context ) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//
//    /**
//     * Attempts to logout the existing session and start a new one.
//     */
//    public void restartSession() {
//
//        Request logoutRequest = new Request() {
//            @Override
//            public String getType() {
//                return "logout";
//            }
//        };
//
//        logoutRequest.make(new Callback() {
//            @Override
//            public void onComplete(Object result) {
//
//                // Register new device session. Must have for guests.
//                Session deviceSessionManager = new Session();
//                deviceSessionManager.addChangeListener(MainActivity.this);
//                deviceSessionManager.initialize();
//            }
//
//            @Override
//            public void onError(int responseCode, String responseMessage) {
//                Log.e(TAG, String.format("Could logout or restart session: %s", responseCode));
//                super.onError(responseCode, responseMessage);
//            }
//        });
//    }
//
//    /**
//     * Show's home/navigation button as close button.
//     * @param returnToDefault true if button should return original state.
//     */
//    public void showHomeAsClose(final boolean returnToDefault) {
//        ToolbarManager toolbarManager = ToolbarManager.getInstance(this);
//        toolbarManager.showXButton(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                popBackStack();
//                if (returnToDefault) {
//                    showHomeAsNavigation();
//                }
//            }
//        });
////
//    }
//
//    public void showHomeAsNavigation() {
//        ToolbarManager toolbarManager = ToolbarManager.getInstance(this);
//        toolbarManager.hideXButton();
//        updateActionBar();
//    }
//
//}
