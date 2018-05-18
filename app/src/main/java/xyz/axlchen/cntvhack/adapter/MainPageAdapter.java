package xyz.axlchen.cntvhack.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import xyz.axlchen.cntvhack.ui.fragment.BaseFragment;

public class MainPageAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;

    public MainPageAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        if (fragments == null || fragments.size() < 1) {
            throw new IllegalArgumentException("fragments must not be null");
        }
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
