package com.handmark.pulltorefresh.library;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.ScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshNotifyScrollView.OnScrollChangedListener;

public class QuickReturnScrollViewOnScrollChangedListener implements OnScrollChangedListener {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$handmark$pulltorefresh$library$QuickReturnType;
    private View mFooter;
    private int mFooterDiffTotal;
    private View mHeader;
    private int mHeaderDiffTotal;
    private int mMinFooterTranslation;
    private int mMinHeaderTranslation;
    private QuickReturnType mQuickReturnType;

    static /* synthetic */ int[] $SWITCH_TABLE$com$handmark$pulltorefresh$library$QuickReturnType() {
        int[] iArr = $SWITCH_TABLE$com$handmark$pulltorefresh$library$QuickReturnType;
        if (iArr == null) {
            iArr = new int[QuickReturnType.values().length];
            try {
                iArr[QuickReturnType.BOTH.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[QuickReturnType.FOOTER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[QuickReturnType.GOOGLE_PLUS.ordinal()] = 4;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[QuickReturnType.HEADER.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[QuickReturnType.TWITTER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            $SWITCH_TABLE$com$handmark$pulltorefresh$library$QuickReturnType = iArr;
        }
        return iArr;
    }

    public QuickReturnScrollViewOnScrollChangedListener(QuickReturnType quickReturnType, View headerView, int headerTranslation, View footerView, int footerTranslation) {
        this.mHeaderDiffTotal = 0;
        this.mFooterDiffTotal = 0;
        this.mQuickReturnType = quickReturnType;
        this.mHeader = headerView;
        this.mMinHeaderTranslation = headerTranslation;
        this.mFooter = footerView;
        this.mMinFooterTranslation = footerTranslation;
    }

    @SuppressLint({"NewApi"})
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        int diff = oldt - t;
        if (VERSION.SDK_INT >= 11) {
            switch ($SWITCH_TABLE$com$handmark$pulltorefresh$library$QuickReturnType()[this.mQuickReturnType.ordinal()]) {
                case 1:
                    if (diff <= 0) {
                        this.mHeaderDiffTotal = Math.max(this.mHeaderDiffTotal + diff, this.mMinHeaderTranslation);
                    } else {
                        this.mHeaderDiffTotal = Math.min(Math.max(this.mHeaderDiffTotal + diff, this.mMinHeaderTranslation), 0);
                    }
                    this.mHeader.setTranslationY((float) this.mHeaderDiffTotal);
                case 2:
                    if (diff <= 0) {
                        this.mFooterDiffTotal = Math.max(this.mFooterDiffTotal + diff, -this.mMinFooterTranslation);
                    } else {
                        this.mFooterDiffTotal = Math.min(Math.max(this.mFooterDiffTotal + diff, -this.mMinFooterTranslation), 0);
                    }
                    this.mFooter.setTranslationY((float) (-this.mFooterDiffTotal));
                case 3:
                    if (diff <= 0) {
                        this.mHeaderDiffTotal = Math.max(this.mHeaderDiffTotal + diff, this.mMinHeaderTranslation);
                        this.mFooterDiffTotal = Math.max(this.mFooterDiffTotal + diff, -this.mMinFooterTranslation);
                    } else {
                        this.mHeaderDiffTotal = Math.min(Math.max(this.mHeaderDiffTotal + diff, this.mMinHeaderTranslation), 0);
                        this.mFooterDiffTotal = Math.min(Math.max(this.mFooterDiffTotal + diff, -this.mMinFooterTranslation), 0);
                    }
                    this.mHeader.setTranslationY((float) this.mHeaderDiffTotal);
                    this.mFooter.setTranslationY((float) (-this.mFooterDiffTotal));
                default:
            }
        }
    }
}
