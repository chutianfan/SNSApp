package com.gitrose.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.gitrose.mobile.adapter.PhotoAlbumLVAdapter;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.ImageFileStatus;
import com.gitrose.mobile.model.PhotoAlbumLVItem;
import com.gitrose.mobile.utils.Utility;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ChooImg2VideoActivity extends Activity implements OnClickListener {
    public static ArrayList<ImageFileStatus> mImageFileStatusList;
    public static int mSelectedImageCount;
    public static HashMap<String, Integer> mSelectedImageFilesMap;
    private boolean isTakePhoto;
    private String mDefaultText;
    private Handler mHandler;

    /* renamed from: com.gitrose.mobile.ChooImg2VideoActivity.1 */
    class C06161 implements OnItemClickListener {
        private final /* synthetic */ ArrayList val$list;

        C06161(ArrayList arrayList) {
            this.val$list = arrayList;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent(ChooImg2VideoActivity.this, PhotoWallActivity.class);
            intent.putExtra("folderPath", ((PhotoAlbumLVItem) this.val$list.get(position)).getPathName());
            intent.putExtra("is_take_photo", ChooImg2VideoActivity.this.isTakePhoto);
            if (ChooImg2VideoActivity.this.mDefaultText != null) {
                intent.putExtra("mDefaultText", ChooImg2VideoActivity.this.mDefaultText);
            }
            ChooImg2VideoActivity.this.startActivity(intent);
            ChooImg2VideoActivity.this.overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
        }
    }

    /* renamed from: com.gitrose.mobile.ChooImg2VideoActivity.2 */
    class C06172 implements Runnable {
        C06172() {
        }

        public void run() {
            ChooImg2VideoActivity.this.finish();
        }
    }

    public ChooImg2VideoActivity() {
        this.mHandler = new Handler();
    }

    static {
        mImageFileStatusList = new ArrayList();
        mSelectedImageFilesMap = new HashMap();
        mSelectedImageCount = 0;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);
        if (Utility.isSDcardOK()) {
            this.isTakePhoto = getIntent().getBooleanExtra("is_take_photo", false);
            this.mDefaultText = getIntent().getStringExtra("mDefaultText");
            mImageFileStatusList.clear();
            mSelectedImageCount = 0;
            mSelectedImageFilesMap.clear();
            ListView listView = (ListView) findViewById(R.id.select_img_listView);
            ArrayList<PhotoAlbumLVItem> list = new ArrayList();
            list.addAll(getImagePathsByContentProvider());
            listView.setAdapter(new PhotoAlbumLVAdapter(this, list));
            listView.setOnItemClickListener(new C06161(list));
            return;
        }
        Utility.showToast((Context) this, getResources().getString(R.string.sd_disable));
    }

    private int getImageCount(File folder) {
        int i = 0;
        int count = 0;
        if (folder == null) {
            return 0;
        }
        File[] files = folder.listFiles();
        if (files == null) {
            return 0;
        }
        int length = files.length;
        while (i < length) {
            File file = files[i];
            if (file != null && Utility.isImage(file.getAbsolutePath(), this.isTakePhoto)) {
                count++;
            }
            i++;
        }
        return count;
    }

    private String getFirstImagePath(File folder) {
        if (folder == null) {
            return null;
        }
        File[] files = folder.listFiles();
        if (files == null) {
            return null;
        }
        for (int i = files.length - 1; i >= 0; i--) {
            File file = files[i];
            if (Utility.isImage(file.getAbsolutePath(), this.isTakePhoto)) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    private ArrayList<PhotoAlbumLVItem> getImagePathsByContentProvider() {
        Uri mImageUri = Media.EXTERNAL_CONTENT_URI;
        String key_MIME_TYPE = "mime_type";
        Cursor cursor = getContentResolver().query(mImageUri, new String[]{"_data"}, new StringBuilder(String.valueOf(key_MIME_TYPE)).append("=? or ").append(key_MIME_TYPE).append("=? or ").append(key_MIME_TYPE).append("=?").toString(), new String[]{"image/jpg", "image/jpeg", "image/png"}, "date_modified");
        ArrayList<PhotoAlbumLVItem> list = new ArrayList();
        if (cursor != null) {
            if (cursor.moveToLast()) {
                HashSet<String> cachePath = new HashSet();
                while (true) {
                    String imagePath = cursor.getString(0);
                    if (imagePath != null) {
                        File file = new File(imagePath);
                        if (file != null) {
                            File parentFile = file.getParentFile();
                            if (parentFile != null) {
                                String parentPath = parentFile.getAbsolutePath();
                                if (!cachePath.contains(parentPath)) {
                                    PhotoAlbumLVItem albumLVItem = new PhotoAlbumLVItem(parentPath, getImageCount(parentFile), getFirstImagePath(parentFile));
                                    if (!(albumLVItem.getFileCount() == 0 || albumLVItem.getPathName().contains(Constant.PREFENCES_NAME) || albumLVItem.getPathName().contains(Constant.APP_CACHE_DIR_NAME2))) {
                                        list.add(albumLVItem);
                                    }
                                    cachePath.add(parentPath);
                                }
                            }
                            if (!cursor.moveToPrevious()) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            cursor.close();
        }
        return list;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        backAction();
        return true;
    }

    private void backAction() {
        if (this.isTakePhoto) {
            finish();
        } else {
            Intent intent = new Intent(this, FFmpegRecorderActivity.class);
            if (this.mDefaultText != null) {
                intent.putExtra("mDefaultText", this.mDefaultText);
            }
            startActivity(intent);
            this.mHandler.postDelayed(new C06172(), 500);
        }
        overridePendingTransition(R.anim.main_translatexf100to0, R.anim.main_translatex0to100);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.album_back_iv:
                backAction();
            default:
        }
    }
}
