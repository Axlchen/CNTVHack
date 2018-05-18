package xyz.axlchen.cntvhack.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import xyz.axlchen.cntvhack.data.entity.TotalShortVideoList;
import xyz.axlchen.cntvhack.ui.fragment.ShortVideoListFragment;

public class ShortVideoCategoryAdapter extends FragmentPagerAdapter {

    private List<ShortVideoListFragment> mFragments;
    private List<TotalShortVideoList.VideoCategory> mCategories;

    public ShortVideoCategoryAdapter(FragmentManager fm) {
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

    public void setCategories(List<TotalShortVideoList.VideoCategory> categories) {
        if (categories.size() > 0) {
            mCategories.clear();
            mCategories.addAll(categories);

            TotalShortVideoList.VideoCategory categoryTotal = new TotalShortVideoList.VideoCategory();
            categoryTotal.setTitle("全部");
            categoryTotal.setvType("1");
            mCategories.add(0, categoryTotal);

            mFragments.clear();
            for (TotalShortVideoList.VideoCategory category : mCategories) {
                if (category.getvType().equals("1")) {
                    mFragments.add(ShortVideoListFragment.newInstance(category.getLinkUrl()));
                }
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
