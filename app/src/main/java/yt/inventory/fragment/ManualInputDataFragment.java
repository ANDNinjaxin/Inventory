package yt.inventory.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import yt.inventory.App;
import yt.inventory.R;
import yt.inventory.adapter.ManualInputPagerAdapter;

/**
 * Created by Ninjaxin on 8/13/2015.
 */
public class ManualInputDataFragment extends BaseFragment {

    private static final String TAG = "Manual_Input_Container";

    private static final int TAB_STUDENT = 0;
    private static final int TAB_BOOK = 1;

    private View fakeIndicator;
    private ViewPager viewPager;
    private ManualInputPagerAdapter adapter;
    private Button tvStudent;
    private Button tvBook;

    int focusedTab = -1;


    public static ManualInputDataFragment newInstance() {
        //params

        ManualInputDataFragment fragment = new ManualInputDataFragment();

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
    protected void initUI(View view) {
        getScreenWidth();

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        adapter = new ManualInputPagerAdapter(getFragmentManager());
        tvStudent = (Button) view.findViewById(R.id.fake_tab_1);
        tvBook = (Button) view.findViewById(R.id.fake_tab_2);
        viewPager.setAdapter(adapter);

        fakeIndicator = view.findViewById(R.id.fake_indicator);
        fakeIndicator.setLayoutParams(setIndicatorSize());

        float xIndicator = 0;
        fakeIndicator.setX(xIndicator);

        initListeners(view);

    }

    private void initListeners(View view) {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float pos = position;
                float rawFloat = pos + positionOffset;
                rawFloat = rawFloat/2;

                float truePos = rawFloat * getScreenWidth();
                fakeIndicator.setX(truePos);
            }

            @Override
            public void onPageSelected(int position) {
                focusedTab = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focusedTab != TAB_STUDENT) {
                    viewPager.setCurrentItem(TAB_STUDENT);
                }
            }
        });

        tvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focusedTab != TAB_BOOK) {
                    viewPager.setCurrentItem(TAB_BOOK);
                }
            }
        });

    }

    private LinearLayout.LayoutParams setIndicatorSize() {
        final int width = (int) getScreenWidth()/2;
        final LinearLayout.LayoutParams indicatorParams = new LinearLayout.LayoutParams(width, App.dpToInt(2));
        return indicatorParams;
    }

    private float getScreenWidth() {
        float xScreenWidth = 0;
        final Display display = App
                                .getMainActivity()
                                .getWindowManager()
                                .getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        xScreenWidth = size.x;
        return xScreenWidth;
    }

    @Override
    public Integer getLayoutId() {
        return R.layout.fragment_manual_input_data;
    }

    @Override
    public String getTitle() {
        return getString(R.string.fragmentmanualinput);
    }
}
