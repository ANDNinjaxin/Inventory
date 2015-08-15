//package yt.inventory.ref;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.ActionBar;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.gpshopper.bebe.android.Metrics;
//import com.gpshopper.bebe.android.R;
//import com.gpshopper.bebe.android.manager.ToolbarManager;
//import com.gpshopper.bebe.android.validation.Validator;
//
///**
// * Created by jc on 7/21/14.
// */
//public abstract class BaseFragment extends Fragment {
//
//    private Toast toast;
//    private MenuItem search;
//    private MenuItem locate;
//    private MenuItem bag;
//    private boolean isVisible;
//
//    private View mView;
//
//    private boolean useHomeMenu = true;
//
//    private boolean ignoreToolbar = false;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    public void setIgnoreToolbar(boolean ignoreToolbar) {
//        this.ignoreToolbar = ignoreToolbar;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final ActionBar actionBar = getMainActivity().getSupportActionBar();
//
//        if (!ignoreToolbar) {
//            ToolbarManager toolbarManager = ToolbarManager.getInstance(getMainActivity());
//            toolbarManager.resetToolbarToDefault(this);
//        }
//        /*if (actionBar != null && showTitle) {
//            final String title = getTitle();
//
//            actionBar.setIcon(R.drawable.logo);
//            actionBar.setTitle(title);
//
//        } else {
//            actionBar.setIcon(R.drawable.blank);
//            actionBar.setTitle("");
//        }*/
//
//        mView = inflater.inflate(getLayoutId(), container, false);
//
//        //updateActionBar();
//
//        if(canChangeToolbar()) {
//            if (getTitle() != null && !getTitle().isEmpty()) {
//                setTitle(getTitle());
//            } else {
//                setShowTitle(false);
//            }
//
//            setToolbar();
//        }
//
//        initUI(mView);
//        return mView;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        isVisible = true;
//        //updateToolbar();
//        //updateActionBar();
//    }
//
//    protected View getFragmentView(){
//        return mView;
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        isVisible = false;
//        if (MainActivity.isApplicationSentToBackground(getActivity())) {
//            if (toast != null) {
//                toast.cancel();
//            }
//        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        activity.invalidateOptionsMenu();
//
//        if(canChangeToolbar())
//            getMainActivity().setSectionTitle(getTitle());
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        if (useHomeMenu) {
//            inflater.inflate(R.menu.home_menu, menu);
//
////            toolbar = toolbarManager.getToolbarView();
//
////            logo = toolbar.findViewById(R.id.actionbar_logo);
////            text = toolbar.findViewById(R.id.actionbar_text);
////            searchLayout = getActivity().findViewById(R.id.actionbar_search_layout);
//
//            search = menu.findItem(R.id.action_search);
//            locate = menu.findItem(R.id.action_locate);
//            bag = menu.findItem(R.id.action_bag);
//
//            locate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    getMainActivity().goToStoreLocator();
//                    return true;
//                }
//            });
//
//            search.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    openActionBarSearch();
//                    return true;
//                }
//            });
//
//            View bagLayout = bag.getActionView();
//            TextView tv = (TextView) bagLayout.findViewById(R.id.cart_counter);
//
//            if (ProductFragment.cartItemCounter == 0) {
//                tv.setText(String.valueOf(""));
//            } else {
//                tv.setText(String.valueOf(ProductFragment.cartItemCounter));
//            }
//            bagLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //TODO: take user to web cart page
//                    replaceMainFragment(getWebView(ProductFragment.cartUrl));
//                }
//            });
//        }
//    }
//
////<<<<<<< HEAD
////    private View toolbar;
//
////    private View logo;
////    private View text;
////    private View searchLayout;
////=======
//    protected boolean isEmpty(EditText... edits) {
//        for (EditText edit : edits)
//            if (TextUtils.isEmpty(edit.getText()))
//                return true;
//
//        return false;
//    }
////>>>>>>> bebe_ui
//
//    protected void overrideOptionsMenu(boolean override) {
//        useHomeMenu = !override;
//    }
//
//    private void openActionBarSearch() {
//
//        ToolbarManager manager = ToolbarManager.getInstance(getMainActivity());
//
//        manager.openSearchLayout();
//
////        searchLayout.setVisibility(View.VISIBLE);
////        logo.setVisibility(View.GONE);
////        text.setVisibility(View.GONE);
//        search.setVisible(false);
//        locate.setVisible(false);
//        bag.setVisible(false);
////        final TextView okButton = (TextView) searchLayout.findViewById(R.id.actionbar_search_ok);
////        okButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                final EditText searchText = (EditText) searchLayout.findViewById(R.id.actionbar_search_edittext);
////                closeActionBarSearch();
////                getMainActivity().goToSearchPageWithQuery(searchText.getText().toString());
////            }
////        });
////        final TextView cancelButton = (TextView) searchLayout.findViewById(R.id.actionbar_search_cancel);
////        cancelButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                closeActionBarSearch();
////            }
////        });
//
//        getMainActivity().openActionbarSearch();
//    }
//
//    private WebViewFragment getWebView(final String url) {
//        WebViewFragment fragment = new WebViewFragment();
//
//        final Bundle arguments = new Bundle();
//        arguments.putString(WebViewFragment.URL_ARG_KEY, url);
//        fragment.setArguments(arguments);
//
//        return fragment;
//    }
//
//    private void closeActionBarSearch() {
//        //searchLayout.setVisibility(View.GONE);
//        //logo.setVisibility(View.VISIBLE);
//        //text.setVisibility(View.VISIBLE);
//        search.setVisible(true);
//        locate.setVisible(true);
//        bag.setVisible(true);
//
//        //toolbar.setLayoutParams(new LinearLayout.LayoutParams(toolbar.getWidth(), toolBarHeight));
//        getMainActivity().closeActionBarSearch();
//    }
//
//    protected boolean isOverlayMode() {
//        return false;
//    }
//
//    public boolean canChangeToolbar() {
//        return true;
//    }
//
//    protected void restoreToolbar() {
//        ToolbarManager manager = ToolbarManager.getInstance(getMainActivity());
//        MainActivity activity = getMainActivity();
//        activity.setBarSpaceVisible(true);
////        manager.getAnimator().showToolbar();
//    }
//
//    protected void updateToolbar() {
//        if (!canChangeToolbar()) {
//            return;
//        }
//
//        ToolbarManager manager = ToolbarManager.getInstance(getMainActivity());
//        MainActivity activity = getMainActivity();
//
//        if (hasToolbar()) {
//            manager.showToolbar();
//        } else {
//            manager.hideToolbar();
//        }
//
//        activity.setBarSpaceVisible(!isOverlayMode());
////
////<<<<<<< HEAD
////
////        if(!hasToolbar()){
//////            manager.getAnimator().hideToolbar();
////=======
////        if (!hasToolbar()) {
////            manager.getAnimator().hideToolbar();
////>>>>>>> bebe_ui
////            return;
////        }
////
////        activity.setBarSpaceVisible(!isOverlayMode());
////<<<<<<< HEAD
////
////
//////        manager.getAnimator().showToolbar();
////=======
////        manager.getAnimator().showToolbar();
////>>>>>>> bebe_ui
//
//
//        String title = getTitle();
//        if (title == null) {
//            manager.showLogo();
//        } else {
//            manager.showTitle(title.toUpperCase());
//        }
//    }
//
//    protected void updateActionBar() {
//        if (!canChangeToolbar())
//            return;
//
//        getMainActivity().updateActionBar();
//    }
//
//    protected boolean hasToolbar() {
//        return true;
//    }
//
//    protected boolean isFragmentVisible() {
//        return isVisible;
//    }
//
//    public void hideKeyboard() {
//        try {
//            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (inputManager != null) {
//                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        } catch (Exception e) {
//            Log.d("", e.getMessage());
//        }
//    }
//
//    protected boolean textEquals(EditText edit1, EditText edit2) {
//        return edit1.getText().toString().equals(edit2.getText().toString());
//    }
//
//    /**
//     * Custom toast message.
//     *
//     * @param title
//     * @param message
//     */
//    protected void showToast(String title, String message) {
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View layout = inflater.inflate(R.layout.toast_layout,
//                (ViewGroup) getActivity().findViewById(R.id.toast_layout_root));
//
//        if (null == title || title.isEmpty()) {
//            showToast(message);
//            return;
//        }
//
//        TextView titleView = (TextView) layout.findViewById(R.id.toast_title);
//        titleView.setText(title);
//
//        TextView text = (TextView) layout.findViewById(R.id.toast_message);
//        text.setText(message);
//
//        toast = new Toast(getActivity().getApplicationContext());
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();
//    }
//
//    /**
//     * Custom toast message.
//     *
//     * @param message
//     */
//    protected void showToast(String message) {
//        Metrics.trackSection("Error", message);
//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View layout = inflater.inflate(R.layout.toast_layout,
//                (ViewGroup) getActivity().findViewById(R.id.toast_layout_root));
//
//        TextView titleView = (TextView) layout.findViewById(R.id.toast_title);
//        titleView.setVisibility(View.GONE);
//
//        TextView text = (TextView) layout.findViewById(R.id.toast_message);
//        text.setText(message);
//
//        toast = new Toast(getActivity().getApplicationContext());
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();
//    }
//
//    final protected void setShowTitle(boolean showTitle) {
//        ToolbarManager toolbarManager = ToolbarManager.getInstance(getMainActivity());
//        if (showTitle) {
//            toolbarManager.showTitle(this);
//        } else {
//            toolbarManager.hideTitle();
//        }
////        getMainActivity().setShowTitle(showTitle);
//    }
//
//    final protected void setActionBarSetup(boolean set) {
//        getMainActivity().setActionBarSetup(set);
//    }
//
//    final protected void setShowBackButton(boolean showBackButton) {
//        ToolbarManager toolbarManager = ToolbarManager.getInstance(getMainActivity());
//        if (showBackButton) {
//            toolbarManager.showBackButton();
//        } else {
//            toolbarManager.hideBackButton();
//        }
////        getMainActivity().setShowBackButton(showBackButton);
//    }
//
//    protected ToolbarManager getToolbarManager(){
//        return ToolbarManager.getInstance(getMainActivity());
//    }
//
//    protected void setToolbar() { }
//
//    protected void setTitle(String value) {
//        getMainActivity().setSectionTitle(value);
//    }
//
//    protected void initUI(View view) {
//    }
//
//    // http://stackoverflow.com/a/11253987
//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        if (MainActivity.isPoppingBackStack()) {
//            return new Animation() {
//            };
//        }
//        return super.onCreateAnimation(transit, enter, nextAnim);
//    }
//
//    protected MainActivity getMainActivity() {
//        return (MainActivity) getActivity();
//    }
//
//    protected void replaceMainFragment(Fragment fragment) {
//        replaceMainFragment(fragment, false);
//    }
//
//    protected void replaceMainFragment(Fragment fragment, boolean addToBackStack) {
//        getMainActivity().replaceMainFragment(fragment, addToBackStack);
//    }
//
//    public abstract String getTitle();
//
//    public abstract Integer getLayoutId();
//
//    public Integer getMenuId() {
//        return R.menu.home_menu;
//    }
//
//    public static TextWatcher setValidator(final Validator validator) {
//        TextWatcher watcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                validator.validateInput();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        };
//        return watcher;
//    }
//
//    //
//    protected void shareFacebook() {
//        if (isFacebookInstalled()) {
//            share();
//        } else {
//            showToast(getActivity().getResources().getString(R.string.appNotInstalled),
//                    getActivity().getResources().getString(R.string.facebookNotInstalled));
//        }
//    }
//
//    //
//    protected void shareTwitter() {
//        if (isTwitterInstalled()) {
//            share();
//        } else {
//            showToast(getActivity().getResources().getString(R.string.appNotInstalled),
//                    getActivity().getResources().getString(R.string.twitterNotInstalled));
//        }
//    }
//
//    //
//    protected boolean isFacebookInstalled() {
//        return isPackageInstalled("com.facebook.katana");
//    }
//
//    //
//    protected boolean isTwitterInstalled() {
//        return isPackageInstalled("com.twitter.android");
//    }
//
//    protected boolean isPackageInstalled(String packagename) {
//        PackageManager pm = getActivity().getPackageManager();
//        try {
//            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
//    }
//
//    //
//    protected void share() {
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//
//        final String shareBody = "some cool product";
//        sharingIntent.setType("text/plain");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Bebe");
//        getActivity().startActivity(Intent.createChooser(sharingIntent, "Share via"));
//    }
//}
