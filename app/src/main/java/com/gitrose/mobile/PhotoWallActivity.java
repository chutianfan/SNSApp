package com.gitrose.mobile;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
//import com.baidu.location.LocationClientOption;
import com.gexing.tutu.jni.MediaEditor;
//import com.tencent.connect.share.QzoneShare;
import com.gitrose.mobile.adapter.HorizontalListViewAdapter;
import com.gitrose.mobile.adapter.PhotoChooseAdapter;
import com.gitrose.mobile.adapter.PhotoWallAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.model.ImageFile;
import com.gitrose.mobile.model.ImageFileStatus;
import com.gitrose.mobile.utils.FileUtil;
import com.gitrose.mobile.utils.Utility;
import com.gitrose.mobile.view.HorizontalListView;
import com.gitrose.mobile.view.MakeVideoDialog;
//import com.umeng.socialize.utils.Log;
import io.rong.common.ResourceUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.FileUtils;
import shouji.gexing.framework.utils.UiUtils;

public class PhotoWallActivity extends BaseActivity implements OnClickListener {
    public static final int EACH_COUNT = 10;
    public static final int MAX_COUNT = 30;
    public static final int TARGET_POS = 1;
    private ArrayList<ImageFile> ImageFileList;
    private PhotoWallAdapter adapter;
    public AtomicInteger count;
    private String currentFolder;
    private Executor executor;
    private Handler handler;
    private HorizontalListView horizontalListView;
    private HorizontalListViewAdapter horizontalListViewAdapter;
    private String imageLocalPath;
    private boolean isTakePhoto;
    private String mDefaultText;
    private MakeVideoDialog mDialog;
    private ArrayList<String> mImagePaths;
    private GridView mPhotoWall;
    private TextView mSelectedImgCountTv;
    private String[] paths;
    private Vector<String> pathsVector;
    private PhotoChooseAdapter photoChooseAdapter;
    private boolean sendToTargetFlag;
    private String strVideoPath;
    private int tempPos;
    private int videoTime;

    /* renamed from: com.gitrose.mobile.PhotoWallActivity.1 */
    class C07381 extends Handler {
        C07381() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == PhotoWallActivity.EACH_COUNT) {
                if (!PhotoWallActivity.this.sendToTargetFlag) {
                    PhotoWallActivity.this.sendToTargetFlag = true;
                    if (PhotoWallActivity.this.strVideoPath != null && new File(PhotoWallActivity.this.strVideoPath).exists()) {
                        new File(PhotoWallActivity.this.strVideoPath).delete();
                    }
                } else if (PhotoWallActivity.this.strVideoPath != null && new File(PhotoWallActivity.this.strVideoPath).exists()) {
                    ChooImg2VideoActivity.mImageFileStatusList.clear();
                    ChooImg2VideoActivity.mSelectedImageFilesMap.clear();
                    ChooImg2VideoActivity.mSelectedImageCount = 0;
                    Intent intent = new Intent(PhotoWallActivity.this, FFmpegPreviewActivity.class);
                    intent.putExtra("videoPath", PhotoWallActivity.this.strVideoPath);
                    intent.putExtra(ResourceUtils.id, PhotoWallActivity.TARGET_POS);
                    intent.putExtra("videoTime", PhotoWallActivity.this.videoTime);
                    intent.putExtra("pathArray", PhotoWallActivity.this.getAllSelectedImagePath());
                    if (PhotoWallActivity.this.mDefaultText != null) {
                        intent.putExtra("mDefaultText", PhotoWallActivity.this.mDefaultText);
                    }
                    PhotoWallActivity.this.startActivity(intent);
                    PhotoWallActivity.this.overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
                    PhotoWallActivity.this.finish();
                }
            } else if (msg.what == PhotoWallActivity.TARGET_POS) {
                int pos = Integer.valueOf(msg.arg1).intValue();
                if (pos >= 0 && pos < PhotoWallActivity.this.ImageFileList.size()) {
                    ((ImageFile) PhotoWallActivity.this.ImageFileList.get(pos)).setSelected(false);
                    int firstPos = PhotoWallActivity.this.mPhotoWall.getFirstVisiblePosition();
                    if (pos >= firstPos) {
                        ViewGroup covertView = (ViewGroup) PhotoWallActivity.this.mPhotoWall.getChildAt(pos - firstPos);
                        if (covertView != null) {
                            covertView.findViewById(R.id.selected_back_view);
                            ((ViewGroup) covertView.getChildAt(0)).getChildAt(PhotoWallActivity.TARGET_POS).setVisibility(View.INVISIBLE);
                            ((ViewGroup) covertView.getChildAt(0)).getChildAt(2).setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PhotoWallActivity.2 */
    class C07392 implements OnItemClickListener {
        C07392() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            ImageFile imageFile = (ImageFile) PhotoWallActivity.this.ImageFileList.get(position);
            View convertView = PhotoWallActivity.this.mPhotoWall.getChildAt(position - PhotoWallActivity.this.mPhotoWall.getFirstVisiblePosition());
            ImageView fileStatusIv = (ImageView) convertView.findViewById(R.id.selected_status_iv);
            View selectBackView = convertView.findViewById(R.id.selected_back_view);
            if (PhotoWallActivity.this.tempPos != position) {
                imageFile.setSelected(true);
                fileStatusIv.setVisibility(View.VISIBLE);
                selectBackView.setVisibility(View.VISIBLE);
                PhotoWallActivity.this.imageLocalPath = imageFile.getFilePath();
                if (PhotoWallActivity.this.tempPos >= 0 && PhotoWallActivity.this.tempPos < PhotoWallActivity.this.ImageFileList.size()) {
                    ((ImageFile) PhotoWallActivity.this.ImageFileList.get(PhotoWallActivity.this.tempPos)).setSelected(false);
                    View perConvertView = PhotoWallActivity.this.mPhotoWall.getChildAt(PhotoWallActivity.this.tempPos - PhotoWallActivity.this.mPhotoWall.getFirstVisiblePosition());
                    if (perConvertView != null) {
                        ImageView perFileStatusIv = (ImageView) perConvertView.findViewById(R.id.selected_status_iv);
                        View perSelectBackView = perConvertView.findViewById(R.id.selected_back_view);
                        perFileStatusIv.setVisibility(View.INVISIBLE);
                        perSelectBackView.setVisibility(View.INVISIBLE);
                    }
                }
            } else if (imageFile.isSelected()) {
                imageFile.setSelected(false);
                fileStatusIv.setVisibility(View.INVISIBLE);
                selectBackView.setVisibility(View.INVISIBLE);
                PhotoWallActivity.this.imageLocalPath = null;
            } else {
                PhotoWallActivity.this.imageLocalPath = imageFile.getFilePath();
                imageFile.setSelected(true);
                fileStatusIv.setVisibility(View.VISIBLE);
                selectBackView.setVisibility(View.VISIBLE);
            }
            PhotoWallActivity.this.tempPos = position;
        }
    }

    /* renamed from: com.gitrose.mobile.PhotoWallActivity.3 */
    class C07403 implements OnItemClickListener {
        C07403() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            ImageFileStatus fileStatus = (ImageFileStatus) ChooImg2VideoActivity.mImageFileStatusList.get(position);
            String filePath = fileStatus.getFilePath();
            int count = fileStatus.getSelectedCount();
            ChooImg2VideoActivity.mSelectedImageCount -= count;
            ChooImg2VideoActivity.mImageFileStatusList.remove(position);
            PhotoWallActivity.this.horizontalListViewAdapter.notifyDataSetChanged();
            PhotoWallActivity.this.mSelectedImgCountTv.setText(new StringBuilder(String.valueOf(PhotoWallActivity.this.getResources().getString(R.string.current_select))).append(ChooImg2VideoActivity.mSelectedImageCount).append(PhotoWallActivity.this.getResources().getString(R.string.max_30)).toString());
            if (ChooImg2VideoActivity.mSelectedImageFilesMap.containsKey(filePath)) {
                int lastCount = ((Integer) ChooImg2VideoActivity.mSelectedImageFilesMap.get(filePath)).intValue() - count;
                if (lastCount <= 0) {
                    ChooImg2VideoActivity.mSelectedImageFilesMap.remove(filePath);
                    PhotoWallActivity.this.toClearThisImageStatus(filePath);
                    return;
                }
                ChooImg2VideoActivity.mSelectedImageFilesMap.put(filePath, Integer.valueOf(lastCount));
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PhotoWallActivity.4 */
    class C07414 implements Runnable {
        private final /* synthetic */ String val$filePath;

        C07414(String str) {
            this.val$filePath = str;
        }

        public void run() {
            for (int i = 0; i < PhotoWallActivity.this.ImageFileList.size(); i += PhotoWallActivity.TARGET_POS) {
                if (((ImageFile) PhotoWallActivity.this.ImageFileList.get(i)).getFilePath().equals(this.val$filePath)) {
                    Message msg = new Message();
                    msg.what = PhotoWallActivity.TARGET_POS;
                    msg.arg1 = i;
                    PhotoWallActivity.this.handler.sendMessage(msg);
                    return;
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PhotoWallActivity.5 */
    class C07435 implements Runnable {
        private final /* synthetic */ String val$folderPath;

        /* renamed from: com.gitrose.mobile.PhotoWallActivity.5.1 */
        class C07421 implements Runnable {
            private final /* synthetic */ ArrayList val$list;

            C07421(ArrayList arrayList) {
                this.val$list = arrayList;
            }

            public void run() {
                PhotoWallActivity.this.ImageFileList.addAll(this.val$list);
                if (PhotoWallActivity.this.isTakePhoto) {
                    PhotoWallActivity.this.photoChooseAdapter.notifyDataSetChanged();
                } else {
                    PhotoWallActivity.this.adapter.notifyDataSetChanged();
                }
                if (PhotoWallActivity.this.mDialog != null) {
                    PhotoWallActivity.this.mDialog.dismiss();
                }
            }
        }

        C07435(String str) {
            this.val$folderPath = str;
        }

        public void run() {
            File folder = new File(this.val$folderPath);
            String[] allFileNames = folder.list();
            if (allFileNames != null && allFileNames.length != 0) {
                int i;
                File[] subFile = folder.listFiles();
                for (i = PhotoWallActivity.TARGET_POS; i < subFile.length; i += PhotoWallActivity.TARGET_POS) {
                    File temp = subFile[i];
                    int j = i - 1;
                    while (j >= 0 && subFile[j].lastModified() > temp.lastModified()) {
                        subFile[j + PhotoWallActivity.TARGET_POS] = subFile[j];
                        j--;
                    }
                    subFile[j + PhotoWallActivity.TARGET_POS] = temp;
                }
                ArrayList<ImageFile> list = new ArrayList();
                for (i = subFile.length - 1; i >= 0; i--) {
                    String filePath = subFile[i].getAbsolutePath();
                    if (Utility.isImage(filePath, PhotoWallActivity.this.isTakePhoto)) {
                        if (ChooImg2VideoActivity.mSelectedImageFilesMap.containsKey(filePath)) {
                            list.add(new ImageFile(filePath, true));
                        } else {
                            list.add(new ImageFile(filePath, false));
                        }
                    }
                }
                PhotoWallActivity.this.runOnUiThread(new C07421(list));
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PhotoWallActivity.6 */
    class C07446 implements Runnable {
        C07446() {
        }

        public void run() {
            if (PhotoWallActivity.this.mImagePaths.size() < PhotoWallActivity.EACH_COUNT && PhotoWallActivity.this.mImagePaths.size() != 0) {
                int count = PhotoWallActivity.EACH_COUNT / PhotoWallActivity.this.mImagePaths.size();
                ArrayList<String> tempStrings = new ArrayList();
                for (int i = 0; i < count; i += PhotoWallActivity.TARGET_POS) {
                    for (int j = 0; j < PhotoWallActivity.this.mImagePaths.size(); j += PhotoWallActivity.TARGET_POS) {
                        tempStrings.add((String) PhotoWallActivity.this.mImagePaths.get(j));
                    }
                }
                PhotoWallActivity.this.mImagePaths.addAll(tempStrings);
            }
            PhotoWallActivity.this.pathsVector = new Vector();
            Iterator it = PhotoWallActivity.this.mImagePaths.iterator();
            while (it.hasNext()) {
                String imagePath = Utility.getSmallImgePath(PhotoWallActivity.this, (String) it.next());
                if (!(imagePath == null || imagePath.length() == 0)) {
                    PhotoWallActivity.this.pathsVector.add(imagePath);
                }
            }
            int pathsSize = PhotoWallActivity.this.pathsVector.size();
            if (pathsSize <= PhotoWallActivity.EACH_COUNT) {
                PhotoWallActivity.this.videoTime = 3000;
            } else if (pathsSize <= 20) {
                PhotoWallActivity.this.videoTime = pathsSize * 2000;
            } else {
                PhotoWallActivity.this.videoTime = pathsSize * BaseConstants.f3843b;
            }
//            Log.m4068i("yesongsong", "videoTime = " + PhotoWallActivity.this.videoTime);
            MediaEditor mediaEditor = new MediaEditor();
//            mediaEditor.addPictureList(PhotoWallActivity.this.pathsVector);
            PhotoWallActivity.this.strVideoPath = new StringBuilder(String.valueOf(FileUtil.getSaveDir(PhotoWallActivity.this))).append("/").append(System.currentTimeMillis()).append(FFmpegPreviewActivity.END_FORMAT).toString();
//            if (mediaEditor.startPicturesToVideo(PhotoWallActivity.this.strVideoPath, FFmpegRecorderActivity.RECODER_WIDTH, FFmpegRecorderActivity.RECODER_WIDTH, 2000000, (float) PhotoWallActivity.this.videoTime) == 0) {
//                PhotoWallActivity.this.handler.sendEmptyMessage(PhotoWallActivity.EACH_COUNT);
//            }
        }
    }

    /* renamed from: com.gitrose.mobile.PhotoWallActivity.7 */
    class C07457 implements OnDismissListener {
        private final /* synthetic */ boolean val$isSend;

        C07457(boolean z) {
            this.val$isSend = z;
        }

        public void onDismiss(DialogInterface dialog) {
            if (this.val$isSend) {
                PhotoWallActivity.this.sendToTargetFlag = false;
            }
        }
    }

    public static class ModifiedTimeComparator implements Comparator<File> {
        public int compare(File lhs, File rhs) {
            return (int) (lhs.lastModified() - rhs.lastModified());
        }
    }

    public PhotoWallActivity() {
        this.count = new AtomicInteger(0);
        this.paths = new String[EACH_COUNT];
        this.executor = Executors.newFixedThreadPool(5);
        this.currentFolder = null;
        this.tempPos = -1;
        this.sendToTargetFlag = true;
        this.handler = new C07381();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);
        this.isTakePhoto = getIntent().getBooleanExtra("is_take_photo", false);
        this.mDefaultText = getIntent().getStringExtra("mDefaultText");
        this.mPhotoWall = (GridView) findViewById(R.id.photo_wall_grid);
        LinearLayout ll_phonto_bootom = (LinearLayout) findViewById(R.id.ll_phonto_bootom);
        this.mSelectedImgCountTv = (TextView) findViewById(R.id.selected_img_count_tv);
        this.mSelectedImgCountTv.setText(new StringBuilder(String.valueOf(getResources().getString(R.string.current_select))).append(ChooImg2VideoActivity.mSelectedImageCount).append(getResources().getString(R.string.max_30)).toString());
        this.horizontalListView = (HorizontalListView) findViewById(R.id.horizontalListView);
        this.ImageFileList = new ArrayList();
        this.currentFolder = getIntent().getStringExtra("folderPath");
        getAllImageFilePathsByFolder(this.currentFolder);
        createSelectdImgView();
        if (this.isTakePhoto) {
            ll_phonto_bootom.setVisibility(View.GONE);
            this.photoChooseAdapter = new PhotoChooseAdapter(this, this.ImageFileList);
            this.mPhotoWall.setAdapter(this.photoChooseAdapter);
            this.mPhotoWall.setOnItemClickListener(new C07392());
            return;
        }
        ll_phonto_bootom.setVisibility(View.VISIBLE);
        this.adapter = new PhotoWallAdapter(this, this.ImageFileList);
        this.mPhotoWall.setAdapter(this.adapter);
    }

    protected String[] getAllSelectedImagePath() {
        if (this.pathsVector == null || this.pathsVector.size() <= 0) {
            return null;
        }
        String[] array = new String[this.pathsVector.size()];
        for (int i = 0; i < this.pathsVector.size(); i += TARGET_POS) {
            array[i] = (String) this.pathsVector.get(i);
        }
        return array;
    }

    private void createSelectdImgView() {
        this.horizontalListViewAdapter = new HorizontalListViewAdapter(this, ChooImg2VideoActivity.mImageFileStatusList);
        this.horizontalListView.setAdapter(this.horizontalListViewAdapter);
        this.horizontalListView.setOnItemClickListener(new C07403());
    }

    protected void toClearThisImageStatus(String filePath) {
        new Thread(new C07414(filePath)).start();
    }

    public void dealAdd2ButtomLayout(String filePath) {
        if (ChooImg2VideoActivity.mSelectedImageCount >= MAX_COUNT) {
            Toast.makeText(this, getResources().getString(R.string.pic_count_on_add), Toast.LENGTH_LONG).show();
            return;
        }
        int count = TARGET_POS;
        if (ChooImg2VideoActivity.mSelectedImageFilesMap.containsKey(filePath)) {
            count = ((Integer) ChooImg2VideoActivity.mSelectedImageFilesMap.get(filePath)).intValue() + TARGET_POS;
        }
        ChooImg2VideoActivity.mSelectedImageFilesMap.put(filePath, Integer.valueOf(count));
        if (ChooImg2VideoActivity.mImageFileStatusList.size() == 0) {
            ChooImg2VideoActivity.mImageFileStatusList.add(new ImageFileStatus(filePath, TARGET_POS));
            this.horizontalListView.scrollTo(ChooImg2VideoActivity.mImageFileStatusList.size() * getResources().getDimensionPixelOffset(R.dimen.selectd_img_item_width));
        } else {
            ImageFileStatus status = (ImageFileStatus) ChooImg2VideoActivity.mImageFileStatusList.get(ChooImg2VideoActivity.mImageFileStatusList.size() - 1);
            if (status.getFilePath().equals(filePath)) {
                status.setSelectedCount(status.getSelectedCount() + TARGET_POS);
            } else {
                ChooImg2VideoActivity.mImageFileStatusList.add(new ImageFileStatus(filePath, TARGET_POS));
                this.horizontalListView.scrollTo(ChooImg2VideoActivity.mImageFileStatusList.size() * getResources().getDimensionPixelOffset(R.dimen.selectd_img_item_width));
            }
        }
        ChooImg2VideoActivity.mSelectedImageCount += TARGET_POS;
        this.horizontalListViewAdapter.notifyDataSetChanged();
        this.mSelectedImgCountTv.setText(new StringBuilder(String.valueOf(getResources().getString(R.string.current_select))).append(ChooImg2VideoActivity.mSelectedImageCount).append(getResources().getString(R.string.max_30)).toString());
    }

    private void backAction() {
        finish();
        overridePendingTransition(R.anim.main_translatexf100to0, R.anim.main_translatex0to100);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        backAction();
        return true;
    }

    private void getAllImageFilePathsByFolder(String folderPath) {
        showDialog(false);
        new Thread(new C07435(folderPath)).start();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_wall_back_iv:
                backAction();
            case R.id.photo_wall_ok_tv:
                if (!this.isTakePhoto) {
                    makeVideo();
                } else if (this.imageLocalPath == null || BaseConstants.ah.equals(this.imageLocalPath)) {
                    Toast.makeText(this, getResources().getString(R.string.please_select_one_pic), Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(this, CropPicActivity.class);
                    intent.setData(Uri.parse("file://" + this.imageLocalPath));
                    intent.putExtra("filepath", this.imageLocalPath);
                    startActivityForResult(intent, EACH_COUNT);
                }
            default:
        }
    }

    public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == EACH_COUNT) {
//            byte[] b = data.getByteArrayExtra("bitmap");
//            String filepath = FileUtils.getSavePath(this, (long) (b.length * EACH_COUNT));
//            if (filepath.endsWith("/")) {
//                filepath = new StringBuilder(String.valueOf(filepath)).append("DCIM/Camera").toString();
//            } else {
//                filepath = new StringBuilder(String.valueOf(filepath)).append("/DCIM/Camera").toString();
//            }
//            MyApplication.getInstance().themeFile = FileUtils.getFile(this, b, filepath, FileUtil.getPhotoFileName());
//            Intent intent = new Intent(this, FilterAndStickerActivity.class);
//            intent.putExtra(SHARE_TO_QQ_IMAGE_URL, MyApplication.getInstance().themeFile.getAbsolutePath());
//            if (this.mDefaultText != null) {
//                intent.putExtra("mDefaultText", this.mDefaultText);
//            }
//            startActivityForNew(intent);
        }
    }

    private void makeVideo() {
        int count = ChooImg2VideoActivity.mImageFileStatusList.size();
        if (count == 0) {
            Toast.makeText(this, getResources().getString(R.string.no_selected_pic), Toast.LENGTH_LONG).show();
            return;
        }
        showDialog(true);
        this.mImagePaths = new ArrayList();
        for (int i = 0; i < count; i += TARGET_POS) {
            ImageFileStatus fileStatus = (ImageFileStatus) ChooImg2VideoActivity.mImageFileStatusList.get(i);
            String path = fileStatus.getFilePath();
            int selectedCount = fileStatus.getSelectedCount();
            for (int j = 0; j < selectedCount; j += TARGET_POS) {
                this.mImagePaths.add(path);
            }
        }
        if (!this.sendToTargetFlag) {
            this.sendToTargetFlag = true;
            if (this.strVideoPath != null && new File(this.strVideoPath).exists()) {
                new File(this.strVideoPath).delete();
            }
        }
        startMakeVideo();
    }

    private void startMakeVideo() {
        new Thread(new C07446()).start();
    }

    private void showDialog(boolean isSend) {
        this.mDialog = new MakeVideoDialog(this, 17);
        View view = LayoutInflater.from(this).inflate(R.layout.make_video_progress, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.make_video_progress_iv);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.mvlayout_ll);
        LayoutParams layoutParams = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.make_video_progress_width), getResources().getDimensionPixelOffset(R.dimen.make_video_progress_height));
        layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.action_bar_height) + ((UiUtils.getInstance(this).getmScreenWidth() - getResources().getDimensionPixelOffset(R.dimen.make_video_progress_height)) / 2);
        linearLayout.setLayoutParams(layoutParams);
        imageView.setBackgroundResource(R.drawable.make_video_progress_dialog);
        try {
            ((AnimationDrawable) imageView.getBackground()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mDialog.setOnDismissListener(new C07457(isSend));
        this.mDialog.showFullScreen(view);
    }

    public void onRecoderOver() {
    }
}
