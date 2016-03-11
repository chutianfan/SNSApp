package com.handmark.pulltorefresh.library;

import android.annotation.TargetApi;
import android.view.View;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;

@TargetApi(9)
public final class OverscrollHelper {
    private static /* synthetic */ int[] f940x9c6cb41e = null;
    static final float DEFAULT_OVERSCROLL_SCALE = 1.0f;
    static final String LOG_TAG = "OverscrollHelper";

    static /* synthetic */ int[] m952x9c6cb41e() {
        int[] iArr = f940x9c6cb41e;
        if (iArr == null) {
            iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.HORIZONTAL.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Orientation.VERTICAL.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            f940x9c6cb41e = iArr;
        }
        return iArr;
    }

    public static void overScrollBy(PullToRefreshBase<?> view, int deltaX, int scrollX, int deltaY, int scrollY, boolean isTouchEvent) {
        overScrollBy(view, deltaX, scrollX, deltaY, scrollY, 0, isTouchEvent);
    }

    public static void overScrollBy(PullToRefreshBase<?> view, int deltaX, int scrollX, int deltaY, int scrollY, int scrollRange, boolean isTouchEvent) {
        overScrollBy(view, deltaX, scrollX, deltaY, scrollY, scrollRange, 0, DEFAULT_OVERSCROLL_SCALE, isTouchEvent);
    }

    public static void overScrollBy(PullToRefreshBase<?> view, int deltaX, int scrollX, int deltaY, int scrollY, int scrollRange, int fuzzyThreshold, float scaleFactor, boolean isTouchEvent) {
        int deltaValue;
        int scrollValue;
        int currentScrollValue;
        switch (m952x9c6cb41e()[view.getPullToRefreshScrollDirection().ordinal()]) {
            case 2:
                deltaValue = deltaX;
                scrollValue = scrollX;
                currentScrollValue = view.getScrollX();
                break;
            default:
                deltaValue = deltaY;
                scrollValue = scrollY;
                currentScrollValue = view.getScrollY();
                break;
        }
        if (view.isPullToRefreshOverScrollEnabled() && !view.isRefreshing()) {
            Mode mode = view.getMode();
            if (mode.permitsPullToRefresh() && !isTouchEvent && deltaValue != 0) {
                int newScrollValue = deltaValue + scrollValue;
                if (newScrollValue < 0 - fuzzyThreshold) {
                    if (mode.showHeaderLoadingLayout()) {
                        if (currentScrollValue == 0) {
                            view.setState(State.OVERSCROLLING, new boolean[0]);
                        }
                        view.setHeaderScroll((int) (((float) (currentScrollValue + newScrollValue)) * scaleFactor));
                    }
                } else if (newScrollValue > scrollRange + fuzzyThreshold) {
                    if (mode.showFooterLoadingLayout()) {
                        if (currentScrollValue == 0) {
                            view.setState(State.OVERSCROLLING, new boolean[0]);
                        }
                        view.setHeaderScroll((int) (((float) ((currentScrollValue + newScrollValue) - scrollRange)) * scaleFactor));
                    }
                } else if (Math.abs(newScrollValue) <= fuzzyThreshold || Math.abs(newScrollValue - scrollRange) <= fuzzyThreshold) {
                    view.setState(State.RESET, new boolean[0]);
                }
            } else if (isTouchEvent && State.OVERSCROLLING == view.getState()) {
                view.setState(State.RESET, new boolean[0]);
            }
        }
    }

    static boolean isAndroidOverScrollEnabled(View view) {
        return view.getOverScrollMode() != 2;
    }
}
