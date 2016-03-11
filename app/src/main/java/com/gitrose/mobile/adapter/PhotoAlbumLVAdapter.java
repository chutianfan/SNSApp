package com.gitrose.mobile.adapter;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.gitrose.mobile.R;
import com.gitrose.mobile.model.PhotoAlbumLVItem;
import java.io.File;
import java.util.ArrayList;

public class PhotoAlbumLVAdapter extends BaseAdapter {
    private Context context;
    private ImageLoader imageLoader;
    private ArrayList<PhotoAlbumLVItem> list;
    DisplayImageOptions options;

    private class ViewHolder {
        TextView fileCountTV;
        ImageView firstImageIV;
        TextView pathNameTV;

        private ViewHolder() {
        }
    }

    public PhotoAlbumLVAdapter(Context context, ArrayList<PhotoAlbumLVItem> list) {
        this.options = new Builder().imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.RGB_565).showImageOnLoading((int) R.drawable.default_img).showImageForEmptyUri((int) R.drawable.default_img).considerExifParams(true).cacheInMemory(false).build();
        this.context = context;
        this.list = list;
        this.imageLoader = ImageLoader.getInstance();
    }

    public int getCount() {
        return this.list == null ? 0 : this.list.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.photo_album_lv_item, null);
            holder = new ViewHolder();
            holder.firstImageIV = (ImageView) convertView.findViewById(R.id.select_img_gridView_img);
            holder.pathNameTV = (TextView) convertView.findViewById(R.id.select_img_gridView_path);
            holder.fileCountTV = (TextView) convertView.findViewById(R.id.file_count_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String filePath = ((PhotoAlbumLVItem) this.list.get(position)).getFirstImagePath();
        holder.firstImageIV.setTag(filePath);
        this.imageLoader.displayImage("file://" + filePath, holder.firstImageIV, this.options);
        holder.pathNameTV.setText(getPathNameToShow((PhotoAlbumLVItem) this.list.get(position)));
        holder.fileCountTV.setText(new StringBuilder(String.valueOf(((PhotoAlbumLVItem) this.list.get(position)).getFileCount())).toString());
        return convertView;
    }

    private String getPathNameToShow(PhotoAlbumLVItem item) {
        String absolutePath = item.getPathName();
        return absolutePath.substring(absolutePath.lastIndexOf(File.separator) + 1);
    }
}
