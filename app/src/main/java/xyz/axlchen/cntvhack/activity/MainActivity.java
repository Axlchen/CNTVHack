package xyz.axlchen.cntvhack.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.fragment.ChannelFragment;
import xyz.axlchen.cntvhack.fragment.ProgramFragment;
import xyz.axlchen.cntvhack.fragment.ShortVideoFragment;

public class MainActivity extends AppCompatActivity {

    private ChannelFragment mChannelFragment;
    private ProgramFragment mProgramFragment;
    private ShortVideoFragment mShortVideoFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_channel:
                    if (mChannelFragment == null){
                        mChannelFragment = ChannelFragment.newInstance();
                    }
                    replaceFragment(mChannelFragment);
                    return true;
                case R.id.navigation_program:
                    if (mProgramFragment == null) {
                        mProgramFragment = ProgramFragment.newInstance();
                    }
                    replaceFragment(mProgramFragment);
                    return true;
                case R.id.navigation_short_video:
                    if (mShortVideoFragment == null) {
                        mShortVideoFragment = ShortVideoFragment.newInstance();
                    }
                    replaceFragment(mShortVideoFragment);
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mChannelFragment = ChannelFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_main_container, mChannelFragment)
                .commit();
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main_container, fragment)
                .commit();
    }
}
