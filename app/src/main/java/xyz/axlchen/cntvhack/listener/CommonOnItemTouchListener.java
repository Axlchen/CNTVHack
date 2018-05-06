package xyz.axlchen.cntvhack.listener;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class CommonOnItemTouchListener implements RecyclerView.OnItemTouchListener, GestureDetector.OnGestureListener {

    private static final String TAG = "CommonOnItemTouchListen";
    private GestureDetectorCompat mGestureDetectorCompat;
    private RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;

    public CommonOnItemTouchListener(Context context) {
        mGestureDetectorCompat = new GestureDetectorCompat(context, this);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mRecyclerView = rv;
        return mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onTouchEvent");
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.d(TAG, "onRequestDisallowInterceptTouchEvent");
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG, "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp");
        View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (childView != null) {
            int layoutPosition = mRecyclerView.getChildLayoutPosition(childView);
            if (layoutPosition != RecyclerView.NO_POSITION) {
                RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForLayoutPosition(layoutPosition);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(layoutPosition);
                    mOnItemClickListener.onClick(layoutPosition, holder);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public static abstract class OnItemClickListener {
        public void onClick(int position){

        }

        public void onClick(int position, RecyclerView.ViewHolder holder){

        }
    }
}
