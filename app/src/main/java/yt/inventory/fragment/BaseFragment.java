package yt.inventory.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yt.inventory.App;

/**
 * Created by Ninjaxin on 8/9/15.
 */
public abstract class BaseFragment extends Fragment {

    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getMainActivity().setTitle(getTitle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(getLayoutId(), container, false);
        initUI(view);
        return view;
    }

    protected void initUI(View view) {

    }

    public abstract Integer getLayoutId();

    public abstract String getTitle();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
