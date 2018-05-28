package xyz.axlchen.cntvhack.listener;

import android.support.design.widget.AppBarLayout;

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    private State mCurrentState = State.COLLAPSING;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.COLLAPSING) {
                onStateChanged(appBarLayout, State.COLLAPSING);
            }
            mCurrentState = State.COLLAPSING;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);

    public enum State {
        EXPANDED,
        COLLAPSED,
        COLLAPSING
    }
}