package yt.inventory.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yt.inventory.R;

/**
 * Created by Ninjaxin on 11/28/2015.
 */
public class DashboardFragment extends BaseFragment {


    public static DashboardFragment newInstance() {
        //params

        DashboardFragment fragment = new DashboardFragment();

//        Bundle args = new Bundle();
//        args.putSerializable();
//        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Integer getLayoutId() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
