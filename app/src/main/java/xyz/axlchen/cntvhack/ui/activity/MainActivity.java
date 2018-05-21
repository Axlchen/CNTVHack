package xyz.axlchen.cntvhack.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.adapter.MainPageAdapter;
import xyz.axlchen.cntvhack.ui.fragment.BaseFragment;
import xyz.axlchen.cntvhack.ui.fragment.ChannelFragment;
import xyz.axlchen.cntvhack.ui.fragment.MoreFragment;
import xyz.axlchen.cntvhack.ui.fragment.ProgramFragment;
import xyz.axlchen.cntvhack.ui.fragment.ShortVideoFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_channel:
                    mViewPager.setCurrentItem(0,false);
                    return true;
                case R.id.navigation_program:
                    mViewPager.setCurrentItem(1,false);
                    return true;
                case R.id.navigation_short_video:
                    mViewPager.setCurrentItem(2,false);
                    return true;
                case R.id.navigation_more:
                    mViewPager.setCurrentItem(3,false);
                    return true;
            }
            return false;
        }
    };
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mNavigationView.setSelectedItemId(R.id.navigation_channel);
                    break;
                case 1:
                    mNavigationView.setSelectedItemId(R.id.navigation_program);
                    break;
                case 2:
                    mNavigationView.setSelectedItemId(R.id.navigation_short_video);
                    break;
                case 3:
                    mNavigationView.setSelectedItemId(R.id.navigation_more);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(ChannelFragment.newInstance());
        fragments.add(ProgramFragment.newInstance());
        fragments.add(ShortVideoFragment.newInstance());
        fragments.add(MoreFragment.newInstance());

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new MainPageAdapter(getSupportFragmentManager(), fragments));
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);

        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}