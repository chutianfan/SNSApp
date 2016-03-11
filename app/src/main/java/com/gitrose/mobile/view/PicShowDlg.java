package com.gitrose.mobile.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.gitrose.mobile.R;
import com.gitrose.mobile.constant.Constant;

public class PicShowDlg {
    private static DisplayImageOptions displayImageOptions;
    private static boolean loadfailed;

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.11 */
    class AnonymousClass11 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;

        AnonymousClass11(Dialog dialog) {
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            this.val$dialog.cancel();
        }
    }

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.12 */
    class AnonymousClass12 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;

        AnonymousClass12(Dialog dialog) {
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            this.val$dialog.cancel();
        }
    }

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.1 */
    class C03661 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;

        C03661(Dialog dialog) {
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            this.val$dialog.cancel();
        }
    }

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.2 */
    class C03672 implements OnClickListener {
        C03672() {
        }

        public void onClick(View v) {
        }
    }

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.5 */
    class C03685 implements OnClickListener {
        C03685() {
        }

        public void onClick(View v) {
        }
    }

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.6 */
    class C03696 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;
        private final /* synthetic */ View val$dialog_view;
        private final /* synthetic */ boolean val$is_avatar;
        private final /* synthetic */ RoundProgressBar val$mProgressBar;
        private final /* synthetic */ String val$url;

        C03696(String str, boolean z, View view, RoundProgressBar roundProgressBar, Dialog dialog) {
            this.val$url = str;
            this.val$is_avatar = z;
            this.val$dialog_view = view;
            this.val$mProgressBar = roundProgressBar;
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            if (PicShowDlg.loadfailed) {
                PicShowDlg.loadImage(this.val$url, this.val$is_avatar, this.val$dialog_view, this.val$mProgressBar);
            } else {
                this.val$dialog.cancel();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.7 */
    class C03707 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;

        C03707(Dialog dialog) {
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            this.val$dialog.cancel();
        }
    }

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.4 */
    class C05534 implements ImageLoadingProgressListener {
        private final /* synthetic */ RoundProgressBar val$mProgressBar;

        C05534(RoundProgressBar roundProgressBar) {
            this.val$mProgressBar = roundProgressBar;
        }

        public void onProgressUpdate(String imageUri, View view, int current, int total) {
            this.val$mProgressBar.setProgress((current * 100) / total);
        }
    }

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.9 */
    class C05549 implements ImageLoadingProgressListener {
        private final /* synthetic */ RoundProgressBar val$mProgressBar;

        C05549(RoundProgressBar roundProgressBar) {
            this.val$mProgressBar = roundProgressBar;
        }

        public void onProgressUpdate(String imageUri, View view, int current, int total) {
            this.val$mProgressBar.setProgress((current * 100) / total);
        }
    }

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.3 */
    class C07253 extends SimpleImageLoadingListener {
        private final /* synthetic */ RoundProgressBar val$mProgressBar;

        C07253(RoundProgressBar roundProgressBar) {
            this.val$mProgressBar = roundProgressBar;
        }

        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
            this.val$mProgressBar.setVisibility(View.INVISIBLE);
        }

        public void onLoadingStarted(String imageUri, View view) {
            this.val$mProgressBar.setVisibility(View.VISIBLE);
            super.onLoadingStarted(imageUri, view);
        }

        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            this.val$mProgressBar.setProgress(-1);
            super.onLoadingFailed(imageUri, view, failReason);
        }
    }

    /* renamed from: com.gitrose.mobile.view.PicShowDlg.8 */
    class C07268 extends SimpleImageLoadingListener {
        private final /* synthetic */ RoundProgressBar val$mProgressBar;

        C07268(RoundProgressBar roundProgressBar) {
            this.val$mProgressBar = roundProgressBar;
        }

        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
            this.val$mProgressBar.setVisibility(View.INVISIBLE);
        }

        public void onLoadingStarted(String imageUri, View view) {
            this.val$mProgressBar.setVisibility(View.VISIBLE);
            super.onLoadingStarted(imageUri, view);
        }

        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            this.val$mProgressBar.setProgress(-1);
            PicShowDlg.loadfailed = true;
            super.onLoadingFailed(imageUri, view, failReason);
        }
    }

    static {
        displayImageOptions = new Builder().cacheInMemory(true).showStubImage(R.drawable.default_avatar).showImageForEmptyUri((int) R.drawable.default_avatar).showImageOnFail((int) R.drawable.default_avatar).cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
        loadfailed = false;
    }

    public static void showPortrait(Activity context, String url) {
        View dialog_view = context.getLayoutInflater().inflate(R.layout.dlg_photo_zoom, null);
//        dialog_view.findViewById(R.id.layout_image_zoom_bottom).setVisibility(View.GONE);
        Dialog dialog = new Dialog(context, R.style.custom_dialog_pic_zoom);
        dialog.setContentView(dialog_view);
        dialog.show();
        LayoutParams params = dialog.getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = dm.widthPixels;
        params.height = dm.heightPixels;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setGravity(17);
//        dialog_view.findViewById(R.id.zoomin).setOnClickListener(new C03661(dialog));
//        dialog_view.findViewById(R.id.save).setOnClickListener(new C03672());
        RoundProgressBar mProgressBar = (RoundProgressBar) dialog_view.findViewById(R.id.pb_loading);
        //ImageLoader.getInstance().displayImage(url, (ImageView) dialog_view.findViewById(R.id.zoomin), displayImageOptions, new C07253(mProgressBar), new C05534(mProgressBar));
    }

    public static void showPic(Activity activity, String subject, String detail, String url, boolean is_avatar) {
        View dialog_view = activity.getLayoutInflater().inflate(R.layout.dlg_photo_zoom, null);
//        TextView pic_detail = (TextView) dialog_view.findViewById(R.id.pic_detail);
        RoundProgressBar mProgressBar = (RoundProgressBar) dialog_view.findViewById(R.id.pb_loading);
//        ((TextView) dialog_view.findViewById(R.id.subject)).setText(subject);
//        pic_detail.setText(new StringBuilder(String.valueOf(detail)).append("\r\n").toString());
//        pic_detail.setMovementMethod(ScrollingMovementMethod.getInstance());
        Dialog dialog = new Dialog(activity, R.style.custom_dialog_pic_zoom);
        dialog.setContentView(dialog_view);
        dialog.show();
//        dialog_view.findViewById(R.id.save).setOnClickListener(new C03685());
        LayoutParams params = dialog.getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = dm.widthPixels;
        params.height = dm.heightPixels;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setGravity(17);
//        dialog_view.findViewById(R.id.zoomin).setOnClickListener(new C03696(url, is_avatar, dialog_view, mProgressBar, dialog));
//        dialog_view.findViewById(R.id.layout_image_zoom_bottom).setOnClickListener(new C03707(dialog));
        loadImage(url, is_avatar, dialog_view, mProgressBar);
    }

    private static void loadImage(String url, boolean is_avatar, View dialog_view, RoundProgressBar mProgressBar) {
        DisplayImageOptions option;
        if (is_avatar) {
            option = Constant.BIG_PICTURE_OPTIONS;
        } else {
            option = Constant.BIG_PICTURE_OPTIONS;
        }
//        ImageLoader.getInstance().displayImage(url, (ImageView) dialog_view.findViewById(R.id.zoomin), option, new C07268(mProgressBar), new C05549(mProgressBar));
    }

    public static void showLocalPic(Activity activity, String subject, String detail, Bitmap imgbitmap) {
        View dialog_view = activity.getLayoutInflater().inflate(R.layout.dlg_photo_zoom, null);
//        TextView pic_detail = (TextView) dialog_view.findViewById(R.id.pic_detail);
//        ((TextView) dialog_view.findViewById(R.id.subject)).setText(subject);
//        pic_detail.setText(new StringBuilder(String.valueOf(detail)).append("\r\n").toString());
//        pic_detail.setMovementMethod(ScrollingMovementMethod.getInstance());
        Dialog dialog = new Dialog(activity, R.style.custom_dialog_pic_zoom);
        dialog.setContentView(dialog_view);
        dialog.show();
        dialog_view.findViewById(R.id.save).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
        });
        LayoutParams params = dialog.getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = dm.widthPixels;
        params.height = dm.heightPixels;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setGravity(17);
//        dialog_view.findViewById(R.id.zoomin).setOnClickListener(new AnonymousClass11(dialog));
//        dialog_view.findViewById(R.id.layout_image_zoom_bottom).setOnClickListener(new AnonymousClass12(dialog));
        ((ImageView) dialog_view.findViewById(R.id.zoomin)).setImageBitmap(imgbitmap);
    }
}
