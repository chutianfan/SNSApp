package com.gitrose.mobile.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v4.media.TransportMediator;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
//import com.sea_monster.core.network.ApiReqeust;
//import com.tencent.android.tpush.XGPushClickedResult;
//import com.tencent.android.tpush.common.MessageKey;
//import com.gitrose.mobile.PersonalActivity;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
//import com.umeng.update.UpdateStatus;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapUtils {
    public static String getBase64FromBitmap(Bitmap bitmap) {
        IOException e;
        Throwable th;
        String base64Str = null;
        ByteArrayOutputStream baos = null;
        if (bitmap != null) {
            try {
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                try {
                    bitmap.compress(CompressFormat.JPEG, 100, baos2);
                    base64Str = Base64.encodeToString(baos2.toByteArray(), 2);
                    Log.d("base64Str", base64Str);
                    baos2.flush();
                    baos2.close();
                    baos = baos2;
                } catch (IOException e2) {
                    e = e2;
                    baos = baos2;
                    try {
                        e.printStackTrace();
                        if (baos != null) {
                            try {
                                baos.flush();
                                baos.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        return base64Str;
                    } catch (Throwable th2) {
                        th = th2;
                        if (baos != null) {
                            try {
                                baos.flush();
                                baos.close();
                            } catch (IOException e32) {
                                e32.printStackTrace();
                            }
                        }
                        //throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    baos = baos2;
                    if (baos != null) {
                        baos.flush();
                        baos.close();
                    }
                    //throw th;
                }
            } catch (IOException e4) {
//                e32 = e4;
//                e32.printStackTrace();
//                if (baos != null) {
//                    baos.flush();
//                    baos.close();
//                }
                return base64Str;
            }
        }
        if (baos != null) {
            try {
                baos.flush();
                baos.close();
            } catch (IOException e322) {
                e322.printStackTrace();
            }
        }
        return base64Str;
    }

    public static Bitmap getBitmapFromBase64(String base64Str) {
        if (TextUtils.isEmpty(base64Str)) {
            return null;
        }
        byte[] bytes = Base64.decode(base64Str, 2);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap getFixSizeBitmap(int byteLength, Bitmap bitmap) {
        int bitmapSize = bitmap.getRowBytes() * bitmap.getHeight();
        if (bitmap == null || byteLength == 0 || bitmapSize <= byteLength) {
            return bitmap;
        }
        if (bitmap != null) {
            while (bitmap.getRowBytes() * bitmap.getHeight() > byteLength) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                //Log.d("BasePhtotActivity---getThumbnailBitmap--", "width:" + width + "----" + "height:" + height);
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, width / 2, height / 2);
            }
        }
        return bitmap;
    }

    public static Bitmap getResizedBitmap(Context context, Uri uri, int widthLimit, int heightLimit) throws IOException {
        String path;
        int sampleSize;
//        Bitmap bitmap;
//        if (uri.getScheme().equals("file")) {
//            path = uri.getPath();
//        } else if (!uri.getScheme().equals(MessageKey.MSG_CONTENT)) {
//            return null;
//        } else {
//            Cursor cursor = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
//            cursor.moveToFirst();
//            path = cursor.getString(0);
//            cursor.close();
//        }
//        ExifInterface exifInterface = new ExifInterface(path);
//        Options options = new Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(path, options);
//        int orientation = exifInterface.getAttributeInt("Orientation", 0);
//        if (orientation == 6 || orientation == 8 || orientation == 5 || orientation == 7) {
//            int tmp = widthLimit;
//            widthLimit = heightLimit;
//            heightLimit = tmp;
//        }
//        int width = options.outWidth;
//        int height = options.outHeight;
//        int sampleW = 1;
//        int sampleH = 1;
//        while (width / 2 > widthLimit) {
//            width /= 2;
//            sampleW <<= 1;
//        }
//        while (height / 2 > heightLimit) {
//            height /= 2;
//            sampleH <<= 1;
//        }
//        options = new Options();
//        if (widthLimit == Integer.MAX_VALUE || heightLimit == Integer.MAX_VALUE) {
//            sampleSize = Math.max(sampleW, sampleH);
//        } else {
//            sampleSize = Math.max(sampleW, sampleH);
//        }
//        options.inSampleSize = sampleSize;
//        try {
//            bitmap = BitmapFactory.decodeFile(path, options);
//        } catch (OutOfMemoryError e) {
//            e.printStackTrace();
//            options.inSampleSize <<= 1;
//            bitmap = BitmapFactory.decodeFile(path, options);
//        }
//        Matrix matrix = new Matrix();
//        if (bitmap == null) {
//            return null;
//        }
//        int w = bitmap.getWidth();
//        int h = bitmap.getHeight();
//        if (orientation == 6 || orientation == 8 || orientation == 5 || orientation == 7) {
//            tmp = w;
//            w = h;
//            h = tmp;
//        }
//        switch (orientation) {
//            case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
//                matrix.preScale(-1.0f, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//                break;
//            case ApiReqeust.PUT_METHOD /*3*/:
//                matrix.setRotate(180.0f, ((float) w) / 2.0f, ((float) h) / 2.0f);
//                break;
//            case PersonalActivity.TOPIC_DETAIL_RESULT /*4*/:
//                matrix.preScale(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, -1.0f);
//                break;
//            case TopicDetailPagerActivity.FRAGMENT_SIZE /*5*/:
//                matrix.setRotate(90.0f, ((float) w) / 2.0f, ((float) h) / 2.0f);
//                matrix.preScale(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, -1.0f);
//                break;
//            case XGPushClickedResult.NOTIFACTION_DOWNLOAD_CANCEL_TYPE /*6*/:
//                matrix.setRotate(90.0f, ((float) w) / 2.0f, ((float) h) / 2.0f);
//                break;
//            case UpdateStatus.Ignore /*7*/:
//                matrix.setRotate(270.0f, ((float) w) / 2.0f, ((float) h) / 2.0f);
//                matrix.preScale(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, -1.0f);
//                break;
//            case TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE /*8*/:
//                matrix.setRotate(270.0f, ((float) w) / 2.0f, ((float) h) / 2.0f);
//                break;
//        }
//        float xS = ((float) widthLimit) / ((float) bitmap.getWidth());
//        float yS = ((float) heightLimit) / ((float) bitmap.getHeight());
//        matrix.postScale(Math.min(xS, yS), Math.min(xS, yS));
//        try {
//            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        } catch (OutOfMemoryError e2) {
//            e2.printStackTrace();
//            Log.d("ResourceCompressHandler", "OOMHeight:" + bitmap.getHeight() + "Width:" + bitmap.getHeight() + "matrix:" + xS + " " + yS);
//            return null;
//        }

        return  null;
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int widthLimit, int heightLimit) {
        Matrix matrix = new Matrix();
        if (bitmap == null) {
            return null;
        }
        float xS = ((float) widthLimit) / ((float) bitmap.getWidth());
        float yS = ((float) heightLimit) / ((float) bitmap.getHeight());
        matrix.postScale(Math.min(xS, yS), Math.min(xS, yS));
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            Log.d("ResourceCompressHandler", "OOMHeight:" + bitmap.getHeight() + "Width:" + bitmap.getHeight() + "matrix:" + xS + " " + yS);
            return null;
        }
    }

    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
    }

    public static Bitmap getRectRotateBitmap(Bitmap b, float rotateDegree) {
        Bitmap bm = getRotateBitmap(b, rotateDegree);
        return Utility.centerSquareScaleBitmap(bm, Math.min(bm.getWidth(), bm.getHeight()));
    }
}
