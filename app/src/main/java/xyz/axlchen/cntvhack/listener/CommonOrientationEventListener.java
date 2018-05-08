package xyz.axlchen.cntvhack.listener;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.OrientationEventListener;

public class CommonOrientationEventListener extends OrientationEventListener {

    private static final String TAG = "CommonOrientationEventL";
    private Activity mContext;

    public CommonOrientationEventListener(Activity context) {
        super(context);
        mContext = context;
    }

    public CommonOrientationEventListener(Activity context, int rate) {
        super(context, rate);
        mContext = context;
    }

    @Override
    public void onOrientationChanged(int orientation) {
        Log.d(TAG, "orientation: " + orientation);
        int screenOrientation = mContext.getResources().getConfiguration().orientation;
        if (((orientation >= 0) && (orientation < 45)) || (orientation >= 315)) {
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    && orientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else if (orientation >= 45 && orientation < 135) {
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        } else if (orientation >= 135 && orientation < 225) {
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            }
        } else if (orientation >= 225 && orientation < 315) {
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }
    }
}
