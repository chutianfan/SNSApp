package com.gitrose.mobile.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
//import io.rong.message.BuildConfig;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseUploadFragment extends BaseFragment {
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
    private File mCropPhotoFile;
    private File mCurrentPhotoFile;
    protected OnClickListener mOptionOnClickListener;
    protected String[] mOptionTexts;
    protected String options_title;
    public boolean zoom;

    /* renamed from: com.gitrose.mobile.base.BaseUploadFragment.1 */
    class C03511 implements OnClickListener {
        C03511() {
        }

        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case BaseUploadFragment.REQUEST_PICK /*0*/:
                    BaseUploadFragment.this.getPicFromCapture();
                case BaseUploadFragment.REQUEST_CAPTRUE /*1*/:
                    BaseUploadFragment.this.getPicFromContent();
                default:
            }
        }
    }

    public interface IUploadCall {
        void gpuback(File file);
    }

    public BaseUploadFragment() {
        this.mCropPhotoFile = new File(Environment.getExternalStorageDirectory() + "/" + getPhotoFileName());
        this.PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
        this.zoom = true;
        this.isEditWithFilter = false;
        this.options_title = "\u53d1\u9001\u56fe\u7247";
        String[] strArr = new String[REQUEST_FILTER];
        strArr[REQUEST_PICK] = "\u62cd\u7167";
        strArr[REQUEST_CAPTRUE] = "\u4ece\u76f8\u518c\u9009\u62e9";
        strArr[REQUEST_CROP] = "\u53d6\u6d88";
        this.mOptionTexts = strArr;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mOptionOnClickListener = new C03511();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0 && resultCode == -1) {
            File file;
            switch (requestCode) {
                case REQUEST_PICK /*0*/:
                    if (data == null) {
                        return;
                    }
                    if (this.zoom) {
                        doCropPhoto(data.getData());
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
                        doCropPhoto(Uri.fromFile(this.mCurrentPhotoFile));
                    } else if (this.isEditWithFilter) {
                        goToFilter(Uri.fromFile(this.mCurrentPhotoFile));
                    } else if (this.mCurrentPhotoFile.exists() && this.call != null) {
                        this.call.gpuback(this.mCurrentPhotoFile);
                    }
                    break;
                case REQUEST_CROP /*2*/:
                    if (this.isEditWithFilter) {
                        goToFilter(Uri.fromFile(this.mCropPhotoFile));
                    } else {
                        this.call.gpuback(this.mCropPhotoFile);
                    }
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
            }
        }
    }

    public void modifyPicture(IUploadCall call) {
        this.call = call;
        new Builder(getActivity()).setTitle(this.options_title).setItems(this.mOptionTexts, this.mOptionOnClickListener).show();
    }

    public void modifyPicture(IUploadCall call, boolean zoom) {
        this.call = call;
        this.zoom = zoom;
        new Builder(getActivity()).setTitle(this.options_title).setItems(this.mOptionTexts, this.mOptionOnClickListener).show();
    }

    public void modifyPicturePop(IUploadCall call, boolean zoom) {
        this.call = call;
        this.zoom = zoom;
    }

    public void modifyPicture(IUploadCall call, boolean isCropPhoto, boolean isEditWithFilter) {
        this.call = call;
        this.zoom = isCropPhoto;
        this.isEditWithFilter = isEditWithFilter;
        new Builder(getActivity()).setTitle(this.options_title).setItems(this.mOptionTexts, this.mOptionOnClickListener).show();
    }

    protected void modifyPicture(String title, String[] texts, OnClickListener listener, IUploadCall call, boolean isCropPhoto, boolean isEditWithFilter) {
        this.call = call;
        this.zoom = isCropPhoto;
        this.isEditWithFilter = isEditWithFilter;
        new Builder(getActivity()).setTitle(title).setItems(texts, listener).show();
    }

    public String getPath(Uri uri) {
        String[] projection = new String[REQUEST_CAPTRUE];
        projection[REQUEST_PICK] = "_data";
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
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
        try {
            this.mCropPhotoFile = new File(Environment.getExternalStorageDirectory() + "/" + getPhotoFileName());
            Intent intent = new Intent("android.intent.action.PICK");
            intent.setType(IMAGE_UNSPECIFIED);
            startActivityForResult(intent, REQUEST_PICK);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "\u9519\u8bef", Toast.LENGTH_LONG).show();
        }
    }

    protected void getPicFromCapture() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.PHOTO_DIR.mkdir();
            this.mCurrentPhotoFile = new File(this.PHOTO_DIR, getPhotoFileName());
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("output", Uri.fromFile(this.mCurrentPhotoFile));
            startActivityForResult(intent, REQUEST_CAPTRUE);
            return;
        }
        Toast.makeText(getActivity(), "\u6ca1\u6709sd\u5361", Toast.LENGTH_LONG).show();
    }

    private void doCropPhoto(Uri uri) {
        try {
            startActivityForResult(getCropImageIntent(uri), REQUEST_CROP);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "\u5931\u8d25", Toast.LENGTH_LONG).show();
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
            intent.putExtra("scale", true);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("return-data", false);
            intent.putExtra("output", Uri.fromFile(this.mCropPhotoFile));
            intent.putExtra("outputFormat", CompressFormat.JPEG.toString());
            return intent;
        }
        Toast.makeText(getActivity(), "\u6ca1\u6709sd\u5361", Toast.LENGTH_LONG).show();
        return null;
    }

    @SuppressLint({"SimpleDateFormat"})
    protected String getPhotoFileName() {
        return new StringBuilder(String.valueOf(new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss").format(new Date(System.currentTimeMillis())))).append(".jpg").toString();
    }

    protected void goToCameraFilter() {
    }
}
