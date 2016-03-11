package com.gitrose.mobile.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.gitrose.mobile.R;
import com.gitrose.mobile.utils.QuickReturnUtils;

public class TipPop {
    private LayoutParams dividerLayoutParams;
    int[] location;
    private Context mContext;
    private OnClickListener mOnClickListener;
    private PopupWindow mPop;
    private String[] mTips;
    private OnDismissListener onDismissListener;
    private int popupHeight;
    private int popupWidth;
    private LayoutParams textLayoutParams;
    private View toLeftView;

    /* renamed from: com.gitrose.mobile.view.TipPop.1 */
    class C09571 implements OnDismissListener {
        private final /* synthetic */ View val$aboveView;

        C09571(View view) {
            this.val$aboveView = view;
        }

        public void onDismiss() {
            this.val$aboveView.setSelected(false);
        }
    }

    /* renamed from: com.gitrose.mobile.view.TipPop.2 */
    class C09582 implements OnDismissListener {
        C09582() {
        }

        public void onDismiss() {
            if (TipPop.this.toLeftView != null) {
                TipPop.this.toLeftView.setSelected(false);
            }
        }
    }

    public TipPop() {
        this.location = new int[2];
    }

    public TipPop(Context context, String[] tips, OnClickListener onClickListener) {
        this.location = new int[2];
        this.mTips = tips;
        this.mOnClickListener = onClickListener;
        this.mContext = context;
        this.textLayoutParams = new LayoutParams(context.getResources().getDimensionPixelOffset(R.dimen.tip_pop_width), context.getResources().getDimensionPixelOffset(R.dimen.tip_pop_divider_height));
        this.dividerLayoutParams = new LayoutParams(QuickReturnUtils.dp2px(this.mContext, 1), context.getResources().getDimensionPixelOffset(R.dimen.tip_pop_divider_height));
    }

    public TipPop(Context context, String[] tips, OnClickListener onClickListener, OnDismissListener onDismissListener) {
        this(context, tips, onClickListener);
        this.onDismissListener = onDismissListener;
    }

    public void show(View aboveView) {
        show(aboveView, 0);
    }

    public void show(View aboveView, int reduceY) {
        LinearLayout tipView = new LinearLayout(this.mContext);
        if (this.mPop == null) {
            tipView.setBackgroundResource(R.drawable.delete_pop);
            tipView.setOrientation(LinearLayout.HORIZONTAL);
            ColorStateList textColor = this.mContext.getResources().getColorStateList(R.color.tip_pop_text_color);
            int dividingLineColor = this.mContext.getResources().getColor(R.color.tip_pop_divider_color);
            for (int i = 0; i < this.mTips.length; i++) {
                String tip = this.mTips[i];
                TextView textView = new TextView(this.mContext);
                textView.setText(tip);
                textView.setTextColor(textColor);
                textView.setGravity(17);
                textView.setTextSize(16.0f);
                textView.setTag(tip);
                textView.setOnClickListener(this.mOnClickListener);
                tipView.addView(textView, this.textLayoutParams);
                if (i != this.mTips.length - 1) {
                    View divider = new View(this.mContext);
                    divider.setBackgroundColor(dividingLineColor);
                    tipView.addView(divider, this.dividerLayoutParams);
                }
            }
            tipView.measure(0, 0);
            this.popupWidth = tipView.getMeasuredWidth();
            this.popupHeight = tipView.getMeasuredHeight();
            this.mPop = new PopupWindow(tipView, -2, -2);
            this.mPop.setBackgroundDrawable(new BitmapDrawable());
            this.mPop.setFocusable(true);
            this.mPop.setOutsideTouchable(true);
            if (this.onDismissListener == null) {
                this.onDismissListener = new C09571(aboveView);
            }
            this.mPop.setOnDismissListener(this.onDismissListener);
        }
        aboveView.getLocationOnScreen(this.location);
        int statusBarHeight = 0;
        try {
            Rect frame = new Rect();
            ((Activity) this.mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
        } catch (Exception e) {
        }
        if ((this.location[1] - this.popupHeight) + reduceY > statusBarHeight) {
            tipView.setBackgroundResource(R.drawable.delete_pop);
            this.mPop.showAtLocation(aboveView, 0, (this.location[0] + (aboveView.getWidth() >> 1)) - (this.popupWidth >> 1), (this.location[1] - this.popupHeight) + reduceY);
            return;
        }
        tipView.setBackgroundResource(R.drawable.delete_pop_top);
        this.mPop.showAtLocation(aboveView, 0, (this.location[0] + (aboveView.getWidth() >> 1)) - (this.popupWidth >> 1), (this.location[1] + aboveView.getHeight()) - reduceY);
    }

    public void dismiss() {
        if (this.mPop != null) {
            this.mPop.dismiss();
        }
    }

    public void showLeft(View contentView, View toLeftView, int interval) {
        showLeft(contentView, toLeftView, interval, R.style.CommentPopAnimation);
    }

    public void showLeft(View contentView, View toLeftView, int interval, int animationStyle) {
        this.toLeftView = toLeftView;
        toLeftView.setSelected(true);
        if (this.mPop == null) {
            contentView.measure(0, 0);
            this.popupWidth = contentView.getMeasuredWidth();
            this.popupHeight = contentView.getMeasuredHeight();
            this.mPop = new PopupWindow(contentView, -2, -2);
            this.mPop.setBackgroundDrawable(new BitmapDrawable());
            this.mPop.setFocusable(true);
            this.mPop.setOutsideTouchable(true);
            if (animationStyle != 0) {
                this.mPop.setAnimationStyle(animationStyle);
            }
            if (this.onDismissListener == null) {
                this.onDismissListener = new C09582();
            }
            this.mPop.setOnDismissListener(this.onDismissListener);
        }
        toLeftView.getLocationOnScreen(this.location);
        this.mPop.showAtLocation(toLeftView, 0, (this.location[0] - this.popupWidth) - interval, (this.location[1] + (toLeftView.getHeight() >> 1)) - (this.popupHeight >> 1));
    }
}
