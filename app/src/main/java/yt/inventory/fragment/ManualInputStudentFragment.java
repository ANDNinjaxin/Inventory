package yt.inventory.fragment;

import android.os.Bundle;

import yt.inventory.R;

/**
 * Created by Ninjaxin on 11/3/15.
 */
public class ManualInputStudentFragment extends BaseFragment {



    public static ManualInputStudentFragment newInstance() {
        //params

        ManualInputStudentFragment fragment = new ManualInputStudentFragment();

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
    public Integer getLayoutId() {
        return R.layout.fragment_manual_input_student;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
