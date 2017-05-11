package hu.ait.viewpagerdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPager extends FragmentPagerAdapter {
    public MyFragmentPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentMain();
                break;
            case 1:
                fragment = new FragmentDetails();
                break;
            default:
                fragment = new FragmentMain();
                break;
        }

        return fragment;

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0) {
            return "Main page";
        } else {
            return "Details";
        }
    }
}
