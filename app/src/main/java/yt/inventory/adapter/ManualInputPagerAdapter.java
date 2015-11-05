package yt.inventory.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Ninjaxin on 11/3/15.
 */
public class ManualInputPagerAdapter extends FragmentStatePagerAdapter {

    public ManualInputPagerAdapter(FragmentManager fragmentManager, int whichPage) {
        super(fragmentManager);

        fragments[0] = ProductDetailsFragment.newInstance(product);
        fragments[1] = ProductReviewsFragment.newInstance(product);
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
