package com.gitrose.mobile.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//import com.tencent.openqq.protocol.imsdk.im_common;
import com.gitrose.mobile.R;
import com.gitrose.mobile.FFmpegPreviewActivity;
//import com.gitrose.mobile.PersonalNewActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.SavedFrames;
import com.gitrose.mobile.view.AutoCoverFlowHelper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import qalsdk.BaseConstants;
//import tencent.tls.tools.util;

public class RecoderUtil {

    /* renamed from: com.gitrose.mobile.utils.RecoderUtil.1 */
    static class C09081 implements Runnable {
        private final /* synthetic */ String val$dirPath;

        C09081(String str) {
            this.val$dirPath = str;
        }

        public void run() {
            File file = new File(this.val$dirPath);
            if (file != null && file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    file2.delete();
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.utils.RecoderUtil.2 */
    static class C09092 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;
        private final /* synthetic */ Handler val$handler;

        C09092(Handler handler, Dialog dialog) {
            this.val$handler = handler;
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            if (this.val$handler != null) {
                Message msg = this.val$handler.obtainMessage();
                msg.what = 1;
                this.val$handler.sendMessage(msg);
            }
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.gitrose.mobile.utils.RecoderUtil.3 */
    static class C09103 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;
        private final /* synthetic */ Handler val$handler;

        C09103(Handler handler, Dialog dialog) {
            this.val$handler = handler;
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            if (this.val$handler != null) {
                Message msg = this.val$handler.obtainMessage();
                msg.what = 0;
                this.val$handler.sendMessage(msg);
            }
            this.val$dialog.dismiss();
        }
    }

    public static class ResolutionComparator implements Comparator<Size> {
        public int compare(Size size1, Size size2) {
            if (size1.height != size2.height) {
                return size1.height - size2.height;
            }
            return size1.width - size2.width;
        }
    }

    public static String getRecordingTimeFromMillis(long millis) {
        String strRecordingTime;
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        if (hours < 0 || hours >= 10) {
            strRecordingTime = new StringBuilder(String.valueOf(hours)).append(":").toString();
        } else {
            strRecordingTime = new StringBuilder(BaseConstants.f3860s).append(hours).append(":").toString();
        }
        if (hours > 0) {
            minutes %= 60;
        }
        if (minutes < 0 || minutes >= 10) {
            strRecordingTime = new StringBuilder(String.valueOf(strRecordingTime)).append(minutes).append(":").toString();
        } else {
            strRecordingTime = new StringBuilder(String.valueOf(strRecordingTime)).append(BaseConstants.f3860s).append(minutes).append(":").toString();
        }
        seconds %= 60;
        if (seconds < 0 || seconds >= 10) {
            return new StringBuilder(String.valueOf(strRecordingTime)).append(seconds).toString();
        }
        return new StringBuilder(String.valueOf(strRecordingTime)).append(BaseConstants.f3860s).append(seconds).toString();
    }

    public static int determineDisplayOrientation(Activity activity, int defaultCameraId) {
        if (VERSION.SDK_INT <= 8) {
            return 0;
        }
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(defaultCameraId, cameraInfo);
        int degrees = getRotationAngle(activity);
        if (cameraInfo.facing == 1) {
            return (360 - ((cameraInfo.orientation + degrees) % 360)) % 360;
        }
        return ((cameraInfo.orientation - degrees) + 360) % 360;
    }

    public static int getRotationAngle(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
//            case PersonalNewActivity.FRAGMENT_ONE /*0*/:
//                return 0;
//            case PersonalNewActivity.FRAGMENT_TWO /*1*/:
//                return 90;
//            case PersonalNewActivity.REMARK_NAME_RESULT /*2*/:
//                return util.S_ROLL_BACK;
//            case PersonalNewActivity.FRAGMENT_FOURE /*3*/:
//                return im_common.WPA_QZONE;
            default:
                return 0;
        }
    }

    public static int getRotationAngle(int rotation) {
        switch (rotation) {
//            case PersonalNewActivity.FRAGMENT_ONE /*0*/:
//                return 0;
//            case PersonalNewActivity.FRAGMENT_TWO /*1*/:
//                return 90;
//            case PersonalNewActivity.REMARK_NAME_RESULT /*2*/:
//                return util.S_ROLL_BACK;
//            case PersonalNewActivity.FRAGMENT_FOURE /*3*/:
//                return im_common.WPA_QZONE;
            default:
                return 0;
        }
    }

    public static String createFinalPath(Context context) {
        return FileUtil.getSaveDir(context) + "/" + new StringBuilder(String.valueOf(new StringBuilder(Constant.FILE_START_NAME).append(System.currentTimeMillis()).toString())).append(FFmpegPreviewActivity.END_FORMAT).toString();
    }

    public static void deleteTempVideo(Context context) {
        new Thread(new C09081(Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/video")).start();
    }

    private static String genrateFilePath(Context context, String uniqueId, boolean isFinalPath, File tempFolderPath) {
        String fileName = new StringBuilder(Constant.FILE_START_NAME).append(uniqueId).append(FFmpegPreviewActivity.END_FORMAT).toString();
        String dirPath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/video";
        if (isFinalPath) {
            File file = new File(dirPath);
            if (!(file.exists() && file.isDirectory())) {
                file.mkdirs();
            }
        } else {
            dirPath = tempFolderPath.getAbsolutePath();
        }
        return new StringBuilder(String.valueOf(dirPath)).append("/").append(fileName).toString();
    }

    public static String createTempPath(Context context, File tempFolderPath) {
        return genrateFilePath(context, String.valueOf(System.currentTimeMillis()), false, tempFolderPath);
    }

    public static List<Size> getResolutionList(Camera camera) {
        return camera.getParameters().getSupportedPreviewSizes();
    }

//    public static RecorderParameters getRecorderParameter(int currentResolution) {
//        RecorderParameters parameters = new RecorderParameters();
//        if (currentResolution == 2) {
//            parameters.setAudioBitrate(128000);
//            parameters.setVideoQuality(0);
//        } else if (currentResolution == 1) {
//            parameters.setAudioBitrate(96000);
//            parameters.setVideoQuality(4);
//        } else if (currentResolution == 0) {
//            parameters.setAudioBitrate(96000);
//            parameters.setVideoQuality(20);
//        }
//        return parameters;
//    }

    public static int calculateMargin(int previewWidth, int screenWidth) {
//        if (previewWidth <= util.S_ROLL_BACK) {
//            return (int) (((double) screenWidth) * 0.12d);
//        }
//        if (previewWidth > util.S_ROLL_BACK && previewWidth <= AutoCoverFlowHelper.RAPID_DELAY_TIME) {
//            return (int) (((double) screenWidth) * 0.08d);
//        }
//        if (previewWidth <= AutoCoverFlowHelper.RAPID_DELAY_TIME || previewWidth > Constant.RESOLUTION_HIGH) {
//            return 0;
//        }
        return (int) (((double) screenWidth) * 0.08d);
    }

    public static int setSelectedResolution(int previewHeight) {
//        if (previewHeight <= util.S_ROLL_BACK) {
//            return 0;
//        }
//        if (previewHeight > util.S_ROLL_BACK && previewHeight <= AutoCoverFlowHelper.RAPID_DELAY_TIME) {
//            return 1;
//        }
//        if (previewHeight <= AutoCoverFlowHelper.RAPID_DELAY_TIME || previewHeight > Constant.RESOLUTION_HIGH) {
//            return 0;
//        }
        return 2;
    }

    public static void concatenateMultipleFiles(String inpath, String outpath) {
//        Writer out;
//        IOException e;
//        Reader reader;
//        Writer writer;
//        File[] files = new File(inpath).listFiles();
//        if (files.length > 0) {
//            for (File fileReader : files) {
//                try {
//                    Reader in = new FileReader(fileReader);
//                    try {
//                        out = new FileWriter(outpath, true);
//                    } catch (IOException e2) {
//                        e = e2;
//                        reader = in;
//                        e.printStackTrace();
//                    }
//                    try {
//                        in.close();
//                        out.close();
//                        writer = out;
//                        reader = in;
//                    } catch (IOException e3) {
//                        e = e3;
//                        writer = out;
//                        reader = in;
//                        e.printStackTrace();
//                    }
//                } catch (IOException e4) {
//                    e = e4;
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    public static String getEncodingLibraryPath(Context paramContext) {
        return new StringBuilder(String.valueOf(paramContext.getApplicationInfo().nativeLibraryDir)).append("/libencoding.so").toString();
    }

    private static HashMap<String, String> getMetaData() {
        HashMap<String, String> localHashMap = new HashMap();
        localHashMap.put("creation_time", new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSSZ").format(new Date()));
        return localHashMap;
    }

    public static int getTimeStampInNsFromSampleCounted(int paramInt) {
        return (int) (((double) paramInt) / 0.0441d);
    }

//    public static void saveReceivedFrame(SavedFrames frame) {
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(frame.getCachePath())));
//            if (bos != null) {
//                bos.write(frame.getFrameBytesData());
//                bos.flush();
//                bos.close();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
//    }

    public static Toast showToast(Context context, String textMessage, int timeDuration) {
        if (context == null) {
            return null;
        }
        Toast t = Toast.makeText(context, textMessage == null ? "Oops! " : textMessage.trim(), timeDuration);
        t.show();
        return t;
    }

    public static void showDialog(Context context, String title, String content, int type, Handler handler) {
        Dialog dialog = new Dialog(context, R.style.Dialog_loading);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.global_dialog_tpl, null);
        Button confirmButton = (Button) view.findViewById(R.id.setting_account_bind_confirm);
        Button cancelButton = (Button) view.findViewById(R.id.setting_account_bind_cancel);
        TextView dialogTitle = (TextView) view.findViewById(R.id.global_dialog_title);
        View line_hori_center = view.findViewById(R.id.line_hori_center);
        confirmButton.setVisibility(View.GONE);
        line_hori_center.setVisibility(View.GONE);
        TextView textView = (TextView) view.findViewById(R.id.setting_account_bind_text);
        Window dialogWindow = dialog.getWindow();
        LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (context.getResources().getDisplayMetrics().density * 288.0f);
        dialogWindow.setAttributes(lp);
        if (!(type == 1 || type == 2)) {
            type = 1;
        }
        dialogTitle.setText(title);
        textView.setText(content);
        if (type == 1 || type == 2) {
            confirmButton.setVisibility(View.VISIBLE);
            confirmButton.setOnClickListener(new C09092(handler, dialog));
        }
        if (type == 2) {
            cancelButton.setVisibility(View.VISIBLE);
            line_hori_center.setVisibility(View.VISIBLE);
            cancelButton.setOnClickListener(new C09103(handler, dialog));
        }
        dialog.addContentView(view, new LinearLayout.LayoutParams(-1, -1));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
