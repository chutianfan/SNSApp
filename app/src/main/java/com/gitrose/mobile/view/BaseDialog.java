package com.gitrose.mobile.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.gitrose.mobile.R;
import shouji.gexing.framework.utils.UiUtils;

public class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        this(context, 80);
    }

    public BaseDialog(Context context, int gravity) {
        super(context, R.style.bottom_dialog);
        Window window = getWindow();
        window.setGravity(gravity);
        window.setWindowAnimations(R.style.PopMenuAnimation);
        setCanceledOnTouchOutside(true);
    }

    public void setContentView(View view) {
        LayoutParams params = getWindow().getAttributes();
        params.height = -2;
        params.width = UiUtils.getInstance(getContext()).getmScreenWidth();
        getWindow().setAttributes(params);
        super.setContentView(view, params);
    }

    private void setFullContentView(View view) {
        LayoutParams params = getWindow().getAttributes();
        params.height = UiUtils.getInstance(getContext()).getmScreenHeight();
        params.width = UiUtils.getInstance(getContext()).getmScreenWidth();
        getWindow().setAttributes(params);
        super.setContentView(view, params);
    }

    public void show(View contentView) {
        show();
        setContentView(contentView);
    }

    public void showFullScreen(View contentView) {
        show();
        setFullContentView(contentView);
    }
}
