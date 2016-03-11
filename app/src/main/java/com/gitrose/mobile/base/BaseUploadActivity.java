package com.gitrose.mobile.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import com.gitrose.mobile.CropPicActivity;
//import io.rong.message.BuildConfig;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import shouji.gexing.framework.utils.FileUtils;
//import shouji.gexing.framework.utils.FileUtils;

public class BaseUploadActivity extends BaseActivity {
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int PHOTOHRAPH = 0;
    public static final int PHOTOZOOM = 2;
    private static final int PHOTO_PICKED_WITH_DATA = 3;
    private static final int REQUEST_CAMERA_FILTER = 4;
    private static final int REQUEST_CAPTRUE = 1;
    private static final int REQUEST_CROP = 2;
    private static final int REQUEST_FILTER = 3;
    private static final int REQUEST_PICK = 0;
    protected static final int REQUEST_PICK_IMAGE = 4;
    private File PHOTO_DIR;
    public IUploadCall call;
    public boolean isEditWithFilter;
    private int mCell;
    public File mCropPhotoFile;
    public File mCurrentPhotoFile;
    protected OnClickListener mOptionOnClickListener;
    protected String[] mOptionTexts;
    protected String options_title;
    public boolean zoom;

    /* renamed from: com.gitrose.mobile.base.BaseUploadActivity.1 */
    class C03501 implements OnClickListener {
        C03501() {
        }

        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case BaseUploadActivity.REQUEST_PICK /*0*/:
                    BaseUploadActivity.this.getPicFromCapture();
                case BaseUploadActivity.REQUEST_CAPTRUE /*1*/:
                    BaseUploadActivity.this.getPicFromContent();
                default:
            }
        }
    }

    public interface IUploadCall {
        void gpuback(File file);
    }

    public BaseUploadActivity() {
        this.mCell = 15;
        this.PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
        this.zoom = false;
        this.isEditWithFilter = false;
        this.options_title = "\u7f16\u8f91\u5934\u50cf";
        String[] strArr = new String[REQUEST_FILTER];
        strArr[REQUEST_PICK] = "\u62cd\u7167";
        strArr[REQUEST_CAPTRUE] = "\u4ece\u76f8\u518c\u9009\u62e9";
        strArr[REQUEST_CROP] = "\u53d6\u6d88";
        this.mOptionTexts = strArr;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCropPhotoFile = new File(Environment.getExternalStorageDirectory() + "/" + getPhotoFileName());
        this.mOptionOnClickListener = new C03501();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0 && resultCode == -1) {
            File file;
            switch (requestCode) {
                case REQUEST_PICK /*0*/:
                    if (data == null) {
                        return;
                    }
                    if (this.zoom) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            doCropPhotoDefine(data.getData(), getPath(uri));
                            return;
                        }
                        Toast.makeText(this, "\u7167\u7247\u83b7\u53d6\u5931\u8d25", Toast.LENGTH_LONG).show();
                    } else if (this.isEditWithFilter) {
                        goToFilter(data.getData());
                    } else if (data != null) {
                        file = new File(getPath(data.getData()));
                        if (file.exists() && this.call != null) {
                            this.call.gpuback(file);
                        }
                    }
                    break;
                case REQUEST_CAPTRUE /*1*/:
                    if (this.zoom) {
                        if (this.mCurrentPhotoFile != null) {
                            doCropPhotoDefine(Uri.fromFile(this.mCurrentPhotoFile), this.mCurrentPhotoFile.getAbsolutePath());
                        } else {
                            Toast.makeText(this, "\u7167\u7247\u5b58\u50a8\u5931\u8d25", Toast.LENGTH_LONG).show();
                        }
                    } else if (this.isEditWithFilter) {
                        goToFilter(Uri.fromFile(this.mCurrentPhotoFile));
                    } else if (this.mCurrentPhotoFile.exists() && this.call != null) {
                        this.call.gpuback(this.mCurrentPhotoFile);
                    }
                    break;
                case REQUEST_CROP /*2*/:
                    if (this.isEditWithFilter) {
                        goToFilter(Uri.fromFile(this.mCropPhotoFile));
                        return;
                    }
                    byte[] b = data.getByteArrayExtra("bitmap");
                    String filepath = FileUtils.getSavePath(this, (long) (b.length * 10));
                    if (filepath.endsWith("/")) {
                        filepath = new StringBuilder(String.valueOf(filepath)).append("DCIM/Camera").toString();
                    } else {
                        filepath = new StringBuilder(String.valueOf(filepath)).append("/DCIM/Camera").toString();
                    }
                    this.call.gpuback(FileUtils.getFile(this, b, filepath, getPhotoFileName()));
                    break;
                case REQUEST_FILTER /*3*/:
                    if (data != null) {
                        file = new File(getPath(data.getData()));
                        if (file.exists() && this.call != null) {
                            this.call.gpuback(file);
                        }
                    }
                    break;
                case REQUEST_PICK_IMAGE /*4*/:
                    if (data != null) {
                        File dataFile = new File(getPath(data.getData()));
                        if (dataFile.exists() && this.call != null) {
                            this.call.gpuback(dataFile);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @SuppressLint({"NewApi"})
    public void modifyPicture(IUploadCall call) {
        Builder builder;
        this.call = call;
        if (VERSION.SDK_INT > 10) {
            builder = new Builder(this, 5);
        } else {
            builder = new Builder(this);
        }
        builder.setTitle(this.options_title).setItems(this.mOptionTexts, this.mOptionOnClickListener).show();
    }

    public void modifyPicture(IUploadCall call, boolean zoom) {
        this.call = call;
        this.zoom = zoom;
    }

    public void modifyPicture(IUploadCall call, boolean isCropPhoto, boolean isEditWithFilter) {
        this.call = call;
        this.zoom = isCropPhoto;
        this.isEditWithFilter = isEditWithFilter;
        new Builder(this).setTitle(this.options_title).setItems(this.mOptionTexts, this.mOptionOnClickListener).show();
    }

    protected void modifyPicture(String title, String[] texts, OnClickListener listener, IUploadCall call, boolean isCropPhoto, boolean isEditWithFilter) {
        this.call = call;
        this.zoom = isCropPhoto;
        this.isEditWithFilter = isEditWithFilter;
        new Builder(this).setTitle(title).setItems(texts, listener).show();
    }

    public String getPath(Uri uri) {
        String[] projection = new String[REQUEST_CAPTRUE];
        projection[REQUEST_PICK] = "_data";
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        try {
            int column_index = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            //return BuildConfig.FLAVOR;
            return "";
        }
    }

    protected void getPicFromContent() {
        this.mCropPhotoFile = new File(Environment.getExternalStorageDirectory() + "/" + getPhotoFileName());
        try {
            Intent intent = new Intent("android.intent.action.PICK");
            intent.setType(IMAGE_UNSPECIFIED);
            startActivityForResult(intent, REQUEST_PICK);
        } catch (Exception e) {
            Toast.makeText(this, "\u9519\u8bef", Toast.LENGTH_LONG).show();
        }
    }

    protected void getPicFromCapture() {
        this.PHOTO_DIR = new File(FileUtils.getSavePath(this, 1048576) + "/DCIM/Camera");
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.PHOTO_DIR.mkdir();
            this.mCurrentPhotoFile = new File(this.PHOTO_DIR, getPhotoFileName());
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("output", Uri.fromFile(this.mCurrentPhotoFile));
            startActivityForResult(intent, REQUEST_CAPTRUE);
            return;
        }
        Toast.makeText(this, "\u6ca1\u6709sd\u5361", Toast.LENGTH_LONG).show();
    }

    private void doCropPhotoDefine(Uri uri, String filepath) {
        try {
            Intent intent = new Intent(this, CropPicActivity.class);
            intent.setData(uri);
            intent.putExtra("filepath", filepath);
            startActivityForResult(intent, REQUEST_CROP);
        } catch (Exception e) {
            Toast.makeText(this, "\u5931\u8d25", Toast.LENGTH_LONG).show();
        }
    }

    private void doCropPhoto(Uri uri) {
        try {
            startActivityForResult(getCropImageIntent(uri), REQUEST_CROP);
        } catch (Exception e) {
            Toast.makeText(this, "\u5931\u8d25", Toast.LENGTH_LONG).show();
        }
    }

    protected void goToFilter(Uri uri) {
    }

    public Intent getCropImageIntent(Uri photoUri) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(photoUri, IMAGE_UNSPECIFIED);
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", REQUEST_CAPTRUE);
            intent.putExtra("aspectY", REQUEST_CAPTRUE);
            intent.putExtra("outputX", 330);
            intent.putExtra("outputY", 330);
            intent.putExtra("scale", true);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("return-data", false);
            intent.putExtra("output", Uri.fromFile(this.mCropPhotoFile));
            intent.putExtra("outputFormat", CompressFormat.JPEG.toString());
            return intent;
        }
        Toast.makeText(this, "\u6ca1\u6709sd\u5361", Toast.LENGTH_LONG).show();
        return null;
    }

    private String getPhotoFileName() {
        return new StringBuilder(String.valueOf(new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss").format(new Date(System.currentTimeMillis())))).append(".jpg").toString();
    }

    protected void goToCameraFilter() {
    }
}
