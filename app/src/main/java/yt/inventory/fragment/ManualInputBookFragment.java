package yt.inventory.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yt.inventory.R;

/**
 * Created by Ninjaxin on 11/3/15.
 */
public class ManualInputBookFragment extends BaseFragment {



    public static ManualInputBookFragment newInstance() {
        //params

        ManualInputBookFragment fragment = new ManualInputBookFragment();

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
        return R.layout.fragment_manual_input_book;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
