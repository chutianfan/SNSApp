package com.gitrose.mobile.adapter;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.gitrose.mobile.R;
import com.gitrose.mobile.PhotoWallActivity;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.model.ImageFile;
import java.util.ArrayList;

public class PhotoWallAdapter extends BaseAdapter {
    private PhotoWallActivity context;
    private ArrayList<ImageFile> imageFileList;
    private ImageLoader imageLoader;
    DisplayImageOptions options;

    /* renamed from: com.gitrose.mobile.adapter.PhotoWallAdapter.1 */
    class C08541 implements OnClickListener {
        C08541() {
        }

        public void onClick(View v) {
            int pos = ((Integer) v.getTag()).intValue();
            RelativeLayout layout = (RelativeLayout) v;
            View backView = layout.getChildAt(1);
            ImageView statusIV = (ImageView) layout.getChildAt(2);
            ImageFile imageFile = (ImageFile) PhotoWallAdapter.this.imageFileList.get(pos);
            imageFile.setSelected(true);
            if (statusIV.getVisibility() != View.VISIBLE) {
                statusIV.setVisibility(View.VISIBLE);
                backView.setVisibility(View.VISIBLE);
            }
            PhotoWallAdapter.this.context.dealAdd2ButtomLayout(imageFile.getFilePath());
        }
    }

    private class ViewHolder {
        View backView;
        ImageView imageView;
        RelativeLayout relativeLayout;
        ImageView selectdStatusIV;

        private ViewHolder() {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.PhotoWallAdapter.2 */
    class C14402 implements ImageLoadingListener {
        private final /* synthetic */ String val$imagePath;
        private final /* synthetic */ ImageView val$imageView;

        C14402(String str, ImageView imageView) {
            this.val$imagePath = str;
            this.val$imageView = imageView;
        }

        public void onLoadingComplete(String imageUrl, View arg1, Bitmap bitmap) {
            if (imageUrl.equals(this.val$imagePath)) {
                this.val$imageView.setImageBitmap(bitmap);
            }
        }

        public void onLoadingCancelled(String arg0, View arg1) {
        }

        public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
            PhotoWallAdapter.this.imageLoader.loadImage(this.val$imagePath, PhotoWallAdapter.this.options, (ImageLoadingListener) this);
        }

        public void onLoadingStarted(String arg0, View arg1) {
        }
    }

    public PhotoWallAdapter(PhotoWallActivity context, ArrayList<ImageFile> imageFileList) {
        this.imageFileList = null;
        this.options = new Builder().imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.RGB_565).showImageOnLoading((int) R.drawable.default_img).showImageForEmptyUri((int) R.drawable.default_img).considerExifParams(true).cacheInMemory(MyApplication.getInstance().is_max_version).build();
        this.context = context;
        this.imageFileList = imageFileList;
        this.imageLoader = ImageLoader.getInstance();
    }

    public int getCount() {
        return this.imageFileList == null ? 0 : this.imageFileList.size();
    }

    public Object getItem(int position) {
        return this.imageFileList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.photo_wall_item, null);
            holder = new ViewHolder();
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.main_photo_wall_item_rl);
            holder.imageView = (ImageView) convertView.findViewById(R.id.photo_wall_item_photo);
            holder.backView = convertView.findViewById(R.id.selected_back_view);
            holder.selectdStatusIV = (ImageView) convertView.findViewById(R.id.selected_status_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.relativeLayout.setTag(Integer.valueOf(position));
        holder.relativeLayout.setOnClickListener(new C08541());
        ImageFile imageFile = (ImageFile) this.imageFileList.get(position);
        if (imageFile.isSelected()) {
            holder.backView.setVisibility(View.VISIBLE);
            holder.selectdStatusIV.setVisibility(View.VISIBLE);
        } else {
            holder.backView.setVisibility(View.GONE);
            holder.selectdStatusIV.setVisibility(View.GONE);
        }
        this.imageLoader.displayImage("file://" + imageFile.getFilePath(), holder.imageView, this.options);
        return convertView;
    }

    private void loadImage(String imagePath, ImageView imageView) {
        this.imageLoader.loadImage(imagePath, this.options, new C14402(imagePath, imageView));
    }
}
