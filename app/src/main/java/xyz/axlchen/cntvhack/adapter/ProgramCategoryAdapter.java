package xyz.axlchen.cntvhack.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import xyz.axlchen.cntvhack.data.entity.ProgramCategory;
import xyz.axlchen.cntvhack.fragment.ProgramListFragment;

public class ProgramCategoryAdapter extends FragmentPagerAdapter {

    private List<ProgramListFragment> mFragments;
    private List<ProgramCategory.Item> mCategories;

    public ProgramCategoryAdapter(FragmentManager fm) {
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

    public void setCategories(List<ProgramCategory.Item> categories) {
        if (categories.size() > 0) {
            mCategories.clear();
            mCategories.addAll(categories);

            mFragments.clear();
            for (ProgramCategory.Item category : mCategories) {
                mFragments.add(ProgramListFragment.newInstance(category.getId()));
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
