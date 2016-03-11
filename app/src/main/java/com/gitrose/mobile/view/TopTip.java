package com.gitrose.mobile.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.mobile.R;
import com.gitrose.mobile.constant.Constant;
import qalsdk.BaseConstants;

public class TopTip {

    /* renamed from: com.gitrose.mobile.view.TopTip.1 */
    static class C09591 implements Runnable {
        private final /* synthetic */ View val$tipView;

        C09591(View view) {
            this.val$tipView = view;
        }

        public void run() {
            TopTip.outFlyAnim(this.val$tipView);
            this.val$tipView.setVisibility(View.INVISIBLE);
        }
    }

    public static void show(Context context, RelativeLayout containView, String tipTitle, String tipText, int color) {
        show(context, containView, tipTitle, tipText, color, context.getResources().getDimensionPixelOffset(R.dimen.top_tip_height));
    }

    public static void show(Context context, RelativeLayout containView, String tipTitle, String tipText, int color, int titlecolor, int textcolor) {
        show(context, containView, tipTitle, tipText, color, titlecolor, textcolor, context.getResources().getDimensionPixelOffset(R.dimen.top_tip_height));
    }

    public static void show(Context context, RelativeLayout containView, String tipTitle, String tipText, int color, int tipHeight) {
        View tipView = containView.findViewById(R.id.ll_top_tip);
        if (tipView == null) {
            tipView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_top_tip, null);
            containView.addView(tipView, new LayoutParams(-1, tipHeight));
        }
        tipView.setVisibility(View.VISIBLE);
        if (color != -1) {
            tipView.setBackgroundColor(color);
        }
        ((TextView) tipView.findViewById(R.id.tv_top_tip_title)).setText(tipTitle);
        if (tipText != null) {
            ((TextView) tipView.findViewById(R.id.tv_top_tip_text)).setText(tipText);
        } else {
            ((TextView) tipView.findViewById(R.id.tv_top_tip_text)).setVisibility(View.GONE);
        }
        initAnimation(tipView);
    }

    public static void showThemeSuccesss(Context context, RelativeLayout containView, String tipTitle, String tipText, String tipUrl, int tipHeight) {
        View tipView = containView.findViewById(R.id.ll_top_tip);
        if (tipView == null) {
            tipView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_home_top_tip, null);
            containView.addView(tipView, new LayoutParams(-1, tipHeight));
        }
        tipView.setVisibility(View.VISIBLE);
        ((TextView) tipView.findViewById(R.id.tv_top_tip_title)).setText(tipTitle);
        if (tipText == null || BaseConstants.ah.equals(tipText)) {
            ((TextView) tipView.findViewById(R.id.tv_top_tip_text)).setVisibility(View.GONE);
        } else {
            ((TextView) tipView.findViewById(R.id.tv_top_tip_text)).setText(tipText);
        }
        ImageLoader.getInstance().displayImage("file:/" + tipUrl, (ImageView) tipView.findViewById(R.id.iv_top_tip_pic), Constant.AVATAR_OPTIONS);
        initAnimation(tipView);
    }

    public static void show(Context context, RelativeLayout containView, String tipTitle, String tipText, int color, int titlecolor, int textcolor, int tipHeight) {
        View tipView = containView.findViewById(R.id.ll_top_tip);
        if (tipView == null) {
            tipView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_top_tip, null);
            containView.addView(tipView, new LayoutParams(-1, tipHeight));
        }
        tipView.setVisibility(View.VISIBLE);
        if (color != -1) {
            tipView.setBackgroundColor(color);
        }
        ((TextView) tipView.findViewById(R.id.tv_top_tip_title)).setText(tipTitle);
        ((TextView) tipView.findViewById(R.id.tv_top_tip_title)).setTextColor(titlecolor);
        if (tipText != null) {
            ((TextView) tipView.findViewById(R.id.tv_top_tip_text)).setText(tipText);
            ((TextView) tipView.findViewById(R.id.tv_top_tip_text)).setTextColor(textcolor);
        } else {
            ((TextView) tipView.findViewById(R.id.tv_top_tip_text)).setVisibility(View.GONE);
        }
        initAnimation(tipView);
    }

    public static void show(Context context, RelativeLayout containView, String tipTitle) {
        show(context, containView, tipTitle, null);
    }

    public static void show(Context context, RelativeLayout containView, String tipTitle, String tipText) {
        show(context, containView, tipTitle, tipText, -1);
    }

    private static void initAnimation(View tipView) {
        inFlyAnim(tipView);
        tipView.postDelayed(new C09591(tipView), 2000);
    }

    public static void inFlyAnim(View tipView) {
        TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -3.0f, 1, 0.0f);
        animation.setDuration(800);
        animation.setRepeatCount(0);
        tipView.startAnimation(animation);
    }

    public static void outFlyAnim(View tipView) {
        TranslateAnimation outanimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.0f, 1, -3.0f);
        outanimation.setDuration(800);
        outanimation.setRepeatCount(0);
        tipView.startAnimation(outanimation);
    }
}
