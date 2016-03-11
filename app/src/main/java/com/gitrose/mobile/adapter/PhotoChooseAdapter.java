package com.gitrose.mobile.adapter;

import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.gitrose.mobile.R;
import com.gitrose.mobile.PhotoWallActivity;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.model.ImageFile;
import java.util.ArrayList;

public class PhotoChooseAdapter extends BaseAdapter {
    private PhotoWallActivity context;
    private ArrayList<ImageFile> imageFileList;
    private ImageLoader imageLoader;
    DisplayImageOptions options;

    private class ViewHolder {
        View backView;
        ImageView imageView;
        RelativeLayout relativeLayout;
        ImageView selectdStatusIV;

        private ViewHolder() {
        }
    }

    public PhotoChooseAdapter(PhotoWallActivity context, ArrayList<ImageFile> imageFileList) {
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
        ImageFile imageFile = (ImageFile) this.imageFileList.get(position);
        if (imageFile.isSelected()) {
            holder.backView.setVisibility(View.VISIBLE);
            holder.selectdStatusIV.setVisibility(View.VISIBLE);
        } else {
            holder.backView.setVisibility(View.INVISIBLE);
            holder.selectdStatusIV.setVisibility(View.INVISIBLE);
        }
        this.imageLoader.displayImage("file://" + imageFile.getFilePath(), holder.imageView, this.options);
        return convertView;
    }
}
