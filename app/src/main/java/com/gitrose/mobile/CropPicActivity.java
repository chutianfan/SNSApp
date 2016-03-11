package com.gitrose.mobile;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.view.crop.ClipImageLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import shouji.gexing.framework.utils.UiUtils;
//import shouji.gexing.framework.utils.UiUtils;

public class CropPicActivity extends BaseActivity implements OnClickListener {
    private static CropPicActivity cropPicActivity;
    private ImageView crop_change_bt;
    private String filepath;
    boolean is_full_small;
    private ClipImageLayout mClipImageLayout;

    public CropPicActivity() {
        this.is_full_small = true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_crop_pic);
        Uri uri = getIntent().getData();
        this.filepath = getIntent().getStringExtra("filepath");
        this.mClipImageLayout = (ClipImageLayout) findViewById(R.id.activity_crop_pic_clipImageLayout);
        handleImage(uri);
        findViewById(R.id.activity_crop_pic_cancel_iv).setOnClickListener(this);
        findViewById(R.id.activity_crop_pic_rotate_iv).setOnClickListener(this);
        findViewById(R.id.activity_crop_pic_sure_iv).setOnClickListener(this);
        this.crop_change_bt = (ImageView) findViewById(R.id.activity_crop_pic_change_iv);
        this.crop_change_bt.setOnClickListener(this);
        cropPicActivity = this;
    }

    public static CropPicActivity getObject() {
        return cropPicActivity;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == 2) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void handleImage(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            Options newOpts = new Options();
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri), null, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            int hh = UiUtils.getInstance(this).getmScreenHeight();
            int ww = UiUtils.getInstance(this).getmScreenWidth();
            int be = 1;
            if (w > h && w > ww) {
                be = newOpts.outWidth / ww;
            } else if (w < h && h > hh) {
                be = newOpts.outHeight / hh;
            }
            if (be <= 0) {
                be = 1;
            }
            newOpts.inSampleSize = be;
            newOpts.inPreferredConfig = Config.RGB_565;
            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri), null, newOpts);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 50, baos);
            bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()), null, newOpts);
            if (bitmap != null) {
                this.mClipImageLayout.setImageBitmap(bitmap, this.filepath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_crop_pic_rotate_iv:
                this.mClipImageLayout.mZoomImageView.mScaleMatrix.postRotate(90.0f, (float) (this.mClipImageLayout.mZoomImageView.getWidth() / 2), (float) (this.mClipImageLayout.mZoomImageView.getHeight() / 2));
                this.mClipImageLayout.mZoomImageView.setImageMatrix(this.mClipImageLayout.mZoomImageView.mScaleMatrix);
                break;
            case R.id.activity_crop_pic_change_iv:
                this.mClipImageLayout.toFullSmall(this.is_full_small);
                initChangeBt(this.is_full_small);
                break;
            case R.id.activity_crop_pic_cancel_iv:
                finish();
                break;
            case R.id.activity_crop_pic_sure_iv:
                if (Environment.getExternalStorageState().equals("mounted")) {
                    Bitmap bitmap = this.mClipImageLayout.clip();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(CompressFormat.JPEG, 90, baos);
                    byte[] datas = baos.toByteArray();
                    Intent intent = new Intent();
                    intent.putExtra("bitmap", datas);
                    setResult(-1, intent);
                    finish();
                    return;
                }
                break;
//                Toast.makeText(this, "sd\u5361\u5df2\u6ee1\u65e0\u6cd5\u5b8c\u6210\u88c1\u5207", Toast.LENGTH_LONG).show();
            default:
                break;
        }
    }

    public void initChangeBt(boolean full_small) {
        if (full_small) {
            this.crop_change_bt.setImageResource(R.drawable.crop_big_button);
        } else {
            this.crop_change_bt.setImageResource(R.drawable.crop_full_button);
        }
        this.is_full_small = !full_small;
    }
}
