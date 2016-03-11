package com.gitrose.mobile.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import com.handmark.pulltorefresh.library.QuickReturnType;
//import com.gitrose.mobile.PersonalNewActivity;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.utils.QuickReturnUtils;

public abstract class QuickReturnListViewOnScrollListener implements OnScrollListener {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$handmark$pulltorefresh$library$QuickReturnType;
    private boolean isFooterStopWork;
    private boolean isHeaderStopWork;
    private boolean mCanSlideInIdleScrollState;
    private View mFooter;
    private int mFooterDiffTotal;
    private View mHeader;
    private int mHeaderDiffTotal;
    private int mMinFooterTranslation;
    private int mMinHeaderTranslation;
    private int mPrevScrollY;
    private QuickReturnType mQuickReturnType;

    /* renamed from: com.gitrose.mobile.view.QuickReturnListViewOnScrollListener.1 */
    class C09491 implements Runnable {
        C09491() {
        }

        public void run() {
            QuickReturnListViewOnScrollListener.this.isHeaderStopWork = false;
        }
    }

    /* renamed from: com.gitrose.mobile.view.QuickReturnListViewOnScrollListener.2 */
    class C09502 implements Runnable {
        C09502() {
        }

        public void run() {
            QuickReturnListViewOnScrollListener.this.isFooterStopWork = false;
        }
    }

    protected abstract void onRealScroll(AbsListView absListView, int i, int i2, int i3);

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

    public QuickReturnListViewOnScrollListener(QuickReturnType quickReturnType, View headerView, int headerTranslation, View footerView, int footerTranslation) {
        this.mPrevScrollY = 0;
        this.mHeaderDiffTotal = 0;
        this.mFooterDiffTotal = 0;
        this.mCanSlideInIdleScrollState = false;
        this.isHeaderStopWork = false;
        this.isFooterStopWork = false;
        this.mQuickReturnType = quickReturnType;
        this.mHeader = headerView;
        this.mMinHeaderTranslation = headerTranslation;
        this.mFooter = footerView;
        this.mMinFooterTranslation = footerTranslation;
    }

    @SuppressLint({"NewApi"})
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (!TopicDetailPagerActivity.flag && VERSION.SDK_INT >= 11 && scrollState == 0 && this.mCanSlideInIdleScrollState) {
            int midHeader = (-this.mMinHeaderTranslation) / 2;
            int midFooter = this.mMinFooterTranslation / 2;
            ObjectAnimator anim;
            switch ($SWITCH_TABLE$com$handmark$pulltorefresh$library$QuickReturnType()[this.mQuickReturnType.ordinal()]) {
                case 1:
                    if (!this.isHeaderStopWork) {
                        if ((-this.mHeaderDiffTotal) > 0 && (-this.mHeaderDiffTotal) < midHeader) {
                            anim = ObjectAnimator.ofFloat(this.mHeader, "translationY", new float[]{this.mHeader.getTranslationY(), 0.0f});
                            anim.setDuration(100);
                            anim.start();
                            this.mHeaderDiffTotal = 0;
                        } else if ((-this.mHeaderDiffTotal) < (-this.mMinHeaderTranslation) && (-this.mHeaderDiffTotal) >= midHeader) {
                            anim = ObjectAnimator.ofFloat(this.mHeader, "translationY", new float[]{this.mHeader.getTranslationY(), (float) this.mMinHeaderTranslation});
                            anim.setDuration(100);
                            anim.start();
                            this.mHeaderDiffTotal = this.mMinHeaderTranslation;
                        }
                    }
                case 2:
                    if (!this.isFooterStopWork) {
                        if ((-this.mFooterDiffTotal) > 0 && (-this.mFooterDiffTotal) < midFooter) {
                            anim = ObjectAnimator.ofFloat(this.mFooter, "translationY", new float[]{this.mFooter.getTranslationY(), 0.0f});
                            anim.setDuration(100);
                            anim.start();
                            this.mFooterDiffTotal = 0;
                        } else if ((-this.mFooterDiffTotal) < this.mMinFooterTranslation && (-this.mFooterDiffTotal) >= midFooter) {
                            anim = ObjectAnimator.ofFloat(this.mFooter, "translationY", new float[]{this.mFooter.getTranslationY(), (float) this.mMinFooterTranslation});
                            anim.setDuration(100);
                            anim.start();
                            this.mFooterDiffTotal = -this.mMinFooterTranslation;
                        }
                    }
                case 3:
                    if (!this.isHeaderStopWork) {
                        if ((-this.mHeaderDiffTotal) > 0 && (-this.mHeaderDiffTotal) < midHeader) {
                            anim = ObjectAnimator.ofFloat(this.mHeader, "translationY", new float[]{this.mHeader.getTranslationY(), 0.0f});
                            anim.setDuration(100);
                            anim.start();
                            this.mHeaderDiffTotal = 0;
                        } else if ((-this.mHeaderDiffTotal) < (-this.mMinHeaderTranslation) && (-this.mHeaderDiffTotal) >= midHeader) {
                            anim = ObjectAnimator.ofFloat(this.mHeader, "translationY", new float[]{this.mHeader.getTranslationY(), (float) this.mMinHeaderTranslation});
                            anim.setDuration(100);
                            anim.start();
                            this.mHeaderDiffTotal = this.mMinHeaderTranslation;
                        }
                    }
                    if (!this.isFooterStopWork) {
                        if ((-this.mFooterDiffTotal) > 0 && (-this.mFooterDiffTotal) < midFooter) {
                            anim = ObjectAnimator.ofFloat(this.mFooter, "translationY", new float[]{this.mFooter.getTranslationY(), 0.0f});
                            anim.setDuration(100);
                            anim.start();
                            this.mFooterDiffTotal = 0;
                        } else if ((-this.mFooterDiffTotal) < this.mMinFooterTranslation && (-this.mFooterDiffTotal) >= midFooter) {
                            anim = ObjectAnimator.ofFloat(this.mFooter, "translationY", new float[]{this.mFooter.getTranslationY(), (float) this.mMinFooterTranslation});
                            anim.setDuration(100);
                            anim.start();
                            this.mFooterDiffTotal = -this.mMinFooterTranslation;
                        }
                    }
                case 5:
                    if (!this.isHeaderStopWork) {
                        if ((-this.mHeaderDiffTotal) > 0 && (-this.mHeaderDiffTotal) < midHeader) {
                            anim = ObjectAnimator.ofFloat(this.mHeader, "translationY", new float[]{this.mHeader.getTranslationY(), 0.0f});
                            anim.setDuration(100);
                            anim.start();
                            this.mHeaderDiffTotal = 0;
                        } else if ((-this.mHeaderDiffTotal) < (-this.mMinHeaderTranslation) && (-this.mHeaderDiffTotal) >= midHeader) {
                            anim = ObjectAnimator.ofFloat(this.mHeader, "translationY", new float[]{this.mHeader.getTranslationY(), (float) this.mMinHeaderTranslation});
                            anim.setDuration(100);
                            anim.start();
                            this.mHeaderDiffTotal = this.mMinHeaderTranslation;
                        }
                    }
                    if (!this.isFooterStopWork) {
                        if ((-this.mFooterDiffTotal) > 0 && (-this.mFooterDiffTotal) < midFooter) {
                            anim = ObjectAnimator.ofFloat(this.mFooter, "translationY", new float[]{this.mFooter.getTranslationY(), 0.0f});
                            anim.setDuration(100);
                            anim.start();
                            this.mFooterDiffTotal = 0;
                        } else if ((-this.mFooterDiffTotal) < this.mMinFooterTranslation && (-this.mFooterDiffTotal) >= midFooter) {
                            anim = ObjectAnimator.ofFloat(this.mFooter, "translationY", new float[]{this.mFooter.getTranslationY(), (float) this.mMinFooterTranslation});
                            anim.setDuration(100);
                            anim.start();
                            this.mFooterDiffTotal = -this.mMinFooterTranslation;
                        }
                    }
                default:
            }
        }
    }

    @SuppressLint({"NewApi"})
    public void onScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        onRealScroll(listview, firstVisibleItem, visibleItemCount, totalItemCount);
        if (VERSION.SDK_INT >= 11) {
            int scrollY = QuickReturnUtils.getScrollY(listview);
            int diff = this.mPrevScrollY - scrollY;
            if (diff != 0) {
                switch ($SWITCH_TABLE$com$handmark$pulltorefresh$library$QuickReturnType()[this.mQuickReturnType.ordinal()]) {
                    case 1:
                        if (!this.isHeaderStopWork) {
                            if (diff < 0) {
                                this.mHeaderDiffTotal = Math.max(this.mHeaderDiffTotal + diff, this.mMinHeaderTranslation);
                            } else {
                                this.mHeaderDiffTotal = Math.min(Math.max(this.mHeaderDiffTotal + diff, this.mMinHeaderTranslation), 0);
                            }
                            this.mHeader.setTranslationY((float) this.mHeaderDiffTotal);
                            break;
                        }
                        return;
                    case 2:
                        if (!this.isFooterStopWork) {
                            if (diff < 0) {
                                this.mFooterDiffTotal = Math.max(this.mFooterDiffTotal + diff, -this.mMinFooterTranslation);
                            } else {
                                this.mFooterDiffTotal = Math.min(Math.max(this.mFooterDiffTotal + diff, -this.mMinFooterTranslation), 0);
                            }
                            this.mFooter.setTranslationY((float) (-this.mFooterDiffTotal));
                            break;
                        }
                        return;
                    case 3:
                        if (diff < 0) {
                            this.mHeaderDiffTotal = Math.max(this.mHeaderDiffTotal + diff, this.mMinHeaderTranslation);
                            this.mFooterDiffTotal = Math.max(this.mFooterDiffTotal + diff, -this.mMinFooterTranslation);
                        } else {
                            this.mHeaderDiffTotal = Math.min(Math.max(this.mHeaderDiffTotal + diff, this.mMinHeaderTranslation), 0);
                            this.mFooterDiffTotal = Math.min(Math.max(this.mFooterDiffTotal + diff, -this.mMinFooterTranslation), 0);
                        }
                        if (!this.isHeaderStopWork) {
                            this.mHeader.setTranslationY((float) this.mHeaderDiffTotal);
                        }
                        if (!this.isFooterStopWork) {
                            this.mFooter.setTranslationY((float) (-this.mFooterDiffTotal));
                            break;
                        }
                        break;
                    case 5:
                        if (diff < 0) {
                            if (scrollY > (-this.mMinHeaderTranslation)) {
                                this.mHeaderDiffTotal = Math.max(this.mHeaderDiffTotal + diff, this.mMinHeaderTranslation);
                            }
                            if (scrollY > this.mMinFooterTranslation) {
                                this.mFooterDiffTotal = Math.max(this.mFooterDiffTotal + diff, -this.mMinFooterTranslation);
                            }
                        } else {
                            this.mHeaderDiffTotal = Math.min(Math.max(this.mHeaderDiffTotal + diff, this.mMinHeaderTranslation), 0);
                            this.mFooterDiffTotal = Math.min(Math.max(this.mFooterDiffTotal + diff, -this.mMinFooterTranslation), 0);
                        }
                        if (!this.isHeaderStopWork) {
                            this.mHeader.setTranslationY((float) this.mHeaderDiffTotal);
                        }
                        if (!this.isFooterStopWork) {
                            this.mFooter.setTranslationY((float) (-this.mFooterDiffTotal));
                            break;
                        }
                        break;
                }
            }
            this.mPrevScrollY = scrollY;
        }
    }

    public void setCanSlideInIdleScrollState(boolean canSlideInIdleScrollState) {
        this.mCanSlideInIdleScrollState = canSlideInIdleScrollState;
    }

    public void setHeaderStopWork(boolean isHeaderStopWork) {
        if (isHeaderStopWork) {
            this.isHeaderStopWork = true;
        } else if (this.mHeader != null && this.isHeaderStopWork) {
            this.mHeader.postDelayed(new C09491(), 300);
        }
    }

    public void setFooterStopWork(boolean isFooterStopWork) {
        if (isFooterStopWork) {
            this.isFooterStopWork = true;
        } else if (this.mFooter != null && this.isFooterStopWork) {
            this.mFooter.postDelayed(new C09502(), 300);
        }
    }
}
