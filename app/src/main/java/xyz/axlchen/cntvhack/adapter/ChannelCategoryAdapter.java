package xyz.axlchen.cntvhack.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import xyz.axlchen.cntvhack.data.entity.ChannelCategory;
import xyz.axlchen.cntvhack.fragment.ChannelListFragment;

public class ChannelCategoryAdapter extends FragmentPagerAdapter {

    private List<ChannelListFragment> mFragments;
    private List<ChannelCategory.Item> mCategories;

    public ChannelCategoryAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        mCategories = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void setCategories(List<ChannelCategory.Item> categories) {
        if (categories.size() > 0) {
            mCategories.clear();
            for (ChannelCategory.Item category : categories) {
                if (!(!TextUtils.isEmpty(category.getChannelSign())
                        && category.getChannelSign().equals("hudong"))) {
                    mCategories.add(category);
                }
            }

            mFragments.clear();
            for (ChannelCategory.Item category : mCategories) {
                mFragments.add(ChannelListFragment.newInstance(category.getChannelListUrl()));
            }
            notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mCategories != null && mCategories.size() > position) {
            return mCategories.get(position).getTitle();
        }
        return super.getPageTitle(position);
    }
}
