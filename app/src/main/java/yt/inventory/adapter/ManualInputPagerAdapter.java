package yt.inventory.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import yt.inventory.App;
import yt.inventory.R;
import yt.inventory.fragment.ManualInputBookFragment;
import yt.inventory.fragment.ManualInputStudentFragment;

/**
 * Created by Ninjaxin on 11/3/15.
 */
public class ManualInputPagerAdapter extends FragmentStatePagerAdapter {

    private int currentPosition = 0;
    private OnPageFlipListener pageFlipListener;
    private String[] pageTitles = {
            App.resString(R.string.student),
            App.resString(R.string.book)
    };
    private Fragment[] fragments = new Fragment[2];


    public ManualInputPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        fragments[0] = ManualInputStudentFragment.newInstance();
        fragments[1] = ManualInputBookFragment.newInstance();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentPosition = position;
        if (pageFlipListener != null) {
            pageFlipListener.onPageFlip(position);
        }
        super.setPrimaryItem(container, position, object);
    }



    public void setOnPageFlipListener(OnPageFlipListener pageFlipListener) {
        this.pageFlipListener = pageFlipListener;
    }

    public interface OnPageFlipListener {

        void onPageFlip(int position);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }

    @Override
    public Fragment getItem(int position) {

        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
