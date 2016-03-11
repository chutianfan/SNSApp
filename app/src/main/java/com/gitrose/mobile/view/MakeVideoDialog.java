package com.gitrose.mobile.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import com.gitrose.mobile.R;
import shouji.gexing.framework.utils.UiUtils;

public class MakeVideoDialog extends Dialog {
    public MakeVideoDialog(Context context) {
        this(context, 17);
    }

    public MakeVideoDialog(Context context, int gravity) {
        super(context, R.style.bottom_dialog);
        getWindow().setGravity(gravity);
        setCanceledOnTouchOutside(false);
    }

    public void setContentView(View view) {
        LayoutParams params = getWindow().getAttributes();
        params.height = UiUtils.getInstance(getContext()).getmScreenHeight();
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
