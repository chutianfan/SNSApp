package com.gitrose.mobile.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.widget.Toast;
//import com.tencent.openqq.protocol.imsdk.im_common;
//import com.tencent.qalsdk.im_open.stat_reg.RspBody;
import com.gitrose.mobile.FFmpegRecorderActivity;
//import com.gitrose.mobile.PersonalNewActivity;
import com.gitrose.mobile.constant.Constant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import qalsdk.BaseConstants;
//import tencent.tls.tools.util;

public class Utility {
    public static boolean isSDcardOK() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String getSDcardRoot() {
        if (isSDcardOK()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int msgId) {
        Toast.makeText(context, msgId, Toast.LENGTH_LONG).show();
    }

    public static int countMatches(String res, String findString) {
        if (res == null) {
            return 0;
        }
        if (findString != null && findString.length() != 0) {
            return (res.length() - res.replace(findString, BaseConstants.ah).length()) / findString.length();
        }
        throw new IllegalArgumentException("The param findString cannot be null or 0 length.");
    }

    public static boolean isImage(String fileName, boolean isTakePhoto) {
        if (fileName.endsWith(Constant.IMAGE_EXTENSION) || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".JPG") || fileName.endsWith(".JPEG") || fileName.endsWith(".PNG")) {
            File file = new File(fileName);
            if (file != null && file.exists()) {
                return true;
            }
        }
        return false;
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        int heightRatio = Math.round(((float) height) / ((float) reqHeight));
        int widthRatio = Math.round(((float) width) / ((float) reqWidth));
        if (heightRatio < widthRatio) {
            return heightRatio;
        }
        return widthRatio;
    }

    public static String getSmallImgePath(Context context, String filePath) {
        Error e;
        Exception e2;
        if (filePath == null || filePath.length() == 0) {
            return BaseConstants.ah;
        }
        String endStr = filePath.substring(filePath.lastIndexOf("."), filePath.length());
        File file = null;
        try {
            int degree = readPictureDegree(filePath);
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            int inSampleSize = calculateInSampleSize(options, FFmpegRecorderActivity.RECODER_WIDTH, 800);
            if (inSampleSize >= 4) {
                inSampleSize++;
            }
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            Bitmap bm = BitmapFactory.decodeFile(filePath, options);
            if (degree != 0) {
                bm = rotaingImage(degree, bm);
            }
            if (bm != null) {
                bm = centerSquareScaleBitmap(bm, Math.min(bm.getWidth(), bm.getHeight()));
                File file2 = new File(context.getCacheDir() + "/temp" + System.currentTimeMillis() + endStr);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    if (endStr.equalsIgnoreCase(".png")) {
                        bm.compress(CompressFormat.PNG, 80, fileOutputStream);
                    } else {
                        bm.compress(CompressFormat.JPEG, 80, fileOutputStream);
                    }
                    fileOutputStream.close();
                    bm.recycle();
                    file = file2;
                } catch (Error e3) {
                    e = e3;
                    file = file2;
                    e.printStackTrace();
                    if (file != null) {
                        return null;
                    }
                    return file.getAbsolutePath();
                } catch (Exception e4) {
                    e2 = e4;
                    file = file2;
                    e2.printStackTrace();
                    if (file != null) {
                        return file.getAbsolutePath();
                    }
                    return null;
                }
            }
        } catch (Error e5) {
            e = e5;
            e.printStackTrace();
            if (file != null) {
                return null;
            }
            return file.getAbsolutePath();
        } catch (Exception e6) {
            e2 = e6;
            e2.printStackTrace();
            if (file != null) {
                return file.getAbsolutePath();
            }
            return null;
        }
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (bitmap == null || edgeLength <= 0) {
            return null;
        }
        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();
        if (widthOrg < edgeLength || heightOrg < edgeLength) {
            return result;
        }
        int scaledWidth;
        int scaledHeight;
        int longerEdge = (Math.max(widthOrg, heightOrg) * edgeLength) / Math.min(widthOrg, heightOrg);
        if (widthOrg > heightOrg) {
            scaledWidth = longerEdge;
        } else {
            scaledWidth = edgeLength;
        }
        if (widthOrg > heightOrg) {
            scaledHeight = edgeLength;
        } else {
            scaledHeight = longerEdge;
        }
        try {
            try {
                return Bitmap.createBitmap(Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true), (scaledWidth - edgeLength) / 2, (scaledHeight - edgeLength) / 2, edgeLength, edgeLength);
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e2) {
            return null;
        }
    }

    public static int readPictureDegree(String path) {
        try {
            switch (new ExifInterface(path).getAttributeInt("Orientation", 1)) {
                case 3:
                    return 180;
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Bitmap rotaingImage(int angle, Bitmap bitmap) {
        Bitmap resizedBitmap = bitmap;
        try {
            Matrix matrix = new Matrix();
            matrix.postRotate((float) angle);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return resizedBitmap;
        }
    }
}
