package com.gitrose.mobile.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.gitrose.mobile.R;
import com.gitrose.mobile.model.ImageFileStatus;
import com.gitrose.mobile.view.RoundedImageView;
import java.util.ArrayList;

public class HorizontalListViewAdapter extends BaseAdapter {
    public static final int CORNER_RADIUSE = 4;
    private Context context;
    private ArrayList<ImageFileStatus> fileStatusList;
    private ImageLoader imageLoader;
    DisplayImageOptions options;

    static class ViewHolder {
        TextView mCountTextView;
        RoundedImageView mImageView;
        RelativeLayout relativeLayout;

        ViewHolder() {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.HorizontalListViewAdapter.1 */
    class C14391 implements ImageLoadingListener {
        private final /* synthetic */ String val$imagePath;
        private final /* synthetic */ RoundedImageView val$mImageView;

        C14391(String str, RoundedImageView roundedImageView) {
            this.val$imagePath = str;
            this.val$mImageView = roundedImageView;
        }

        public void onLoadingComplete(String imageUrl, View arg1, Bitmap bitmap) {
            if (imageUrl.equals(this.val$imagePath)) {
                this.val$mImageView.setImageBitmap(bitmap);
            }
        }

        public void onLoadingCancelled(String arg0, View arg1) {
        }

        public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
            HorizontalListViewAdapter.this.imageLoader.loadImage(this.val$imagePath, HorizontalListViewAdapter.this.options, (ImageLoadingListener) this);
        }

        public void onLoadingStarted(String arg0, View arg1) {
        }
    }

    public HorizontalListViewAdapter(Context context, ArrayList<ImageFileStatus> fileStatus) {
        this.options = new Builder().imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.RGB_565).considerExifParams(true).cacheInMemory(true).build();
        this.context = context;
        this.fileStatusList = fileStatus;
        this.imageLoader = ImageLoader.getInstance();
    }

    public int getCount() {
        return this.fileStatusList.size();
    }

    public Object getItem(int position) {
        return this.fileStatusList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public static final float MAX_SCALE = 4.0f;

    public View getView(int position, View convertView, ViewGroup parent) {
        RoundedImageView mImageView;
        TextView mCountTextView;
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.image_gallery_adaper, null);
            mImageView = (RoundedImageView) convertView.findViewById(R.id.gallery_adapter_iv);
            mCountTextView = (TextView) convertView.findViewById(R.id.gallery_adapter_tv);
            holder = new ViewHolder();
            holder.mCountTextView = mCountTextView;
            holder.mImageView = mImageView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            mImageView = holder.mImageView;
            mCountTextView = holder.mCountTextView;
        }
        ImageFileStatus fileStatus = (ImageFileStatus) this.fileStatusList.get(position);
        String imagePath = "file://" + fileStatus.getFilePath();
        mCountTextView.setText(new StringBuilder(String.valueOf(fileStatus.getSelectedCount())).toString());
        mImageView.setCornerRadiusesDP(MAX_SCALE, MAX_SCALE, MAX_SCALE, MAX_SCALE);
        mImageView.setTag(imagePath);
        loadImage(imagePath, mImageView);
        return convertView;
    }

    private void loadImage(String imagePath, RoundedImageView mImageView) {
        this.imageLoader.loadImage(imagePath, this.options, new C14391(imagePath, mImageView));
    }
}
