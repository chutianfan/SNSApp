package com.gitrose.mobile.avsdk.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
//import com.tencent.qalsdk.im_open.stat_reg.ReqBody;
//import com.tencent.qalsdk.im_open.stat_reg.RspBody;
//import com.gitrose.mobile.HomeFragment;
//import com.gitrose.mobile.PersonalNewActivity;
//import com.gitrose.mobile.PersonalWorksListActiviy;

public class Util {
    public static final String ACTION_CLOSE_CONTEXT_COMPLETE = "com.tencent.avsdk.ACTION_CLOSE_CONTEXT_COMPLETE";
    public static final String ACTION_CLOSE_MEMBER_VIDEOCHAT = "com.tencent.avsdk.ACTION_CLOSE_MEMBER_VIDEOCHAT";
    public static final String ACTION_CLOSE_ROOM_COMPLETE = "com.tencent.avsdk.ACTION_CLOSE_ROOM_COMPLETE";
    public static final String ACTION_CREATE_GROUP_ID_COMPLETE = "com.tencent.avsdk.ACTION_CREATE_GROUP_ID_COMPLETE";
    public static final String ACTION_CREATE_ROOM_NUM_COMPLETE = "com.tencent.avsdk.ACTION_CREATE_ROOM_NUM_COMPLETE";
    public static final String ACTION_ENABLE_CAMERA_COMPLETE = "com.tencent.avsdk.ACTION_ENABLE_CAMERA_COMPLETE";
    public static final String ACTION_ENABLE_EXTERNAL_CAPTURE_COMPLETE = "com.tencent.avsdk.ACTION_ENABLE_EXTERNAL_CAPTURE_COMPLETE";
    public static final String ACTION_INSERT_ROOM_TO_SERVER_COMPLETE = "com.tencent.avsdk.ACTION_INSERT_ROOM_TO_SERVER_COMPLETE";
    public static final String ACTION_INVITE_MEMBER_VIDEOCHAT = "com.tencent.avsdk.ACTION_INVITE_MEMBER_VIDEOCHAT";
    public static final String ACTION_MEMBER_CHANGE = "com.tencent.avsdk.ACTION_MEMBER_CHANGE";
    public static final String ACTION_MEMBER_VIDEO_SHOW = "com.tencent.avsdk.ACTION_MEMBER_VIDEO_SHOW";
    public static final String ACTION_OUTPUT_MODE_CHANGE = "com.tencent.avsdk.ACTION_OUTPUT_MODE_CHANGE";
    public static final String ACTION_REQUEST_MEMBER_VIEW = "com.tencent.avsdk.ACTION_REQUEST_MEMBER_VIEW";
    public static final String ACTION_ROOM_CREATE_COMPLETE = "com.tencent.avsdk.ACTION_ROOM_CREATE_COMPLETE";
    public static final String ACTION_SHOW_VIDEO_MEMBER_INFO = "com.tencent.avsdk.ACTION_SHOW_VIDEO_MEMBER_INFO";
    public static final String ACTION_START_CONTEXT_COMPLETE = "com.tencent.avsdk.ACTION_START_CONTEXT_COMPLETE";
    public static final String ACTION_SURFACE_CREATED = "com.tencent.avsdk.ACTION_SURFACE_CREATED";
    public static final String ACTION_SWITCH_CAMERA_COMPLETE = "com.tencent.avsdk.ACTION_SWITCH_CAMERA_COMPLETE";
    public static final String ACTION_VIDEO_CLOSE = "com.tencent.avsdk.ACTION_VIDEO_CLOSE";
    public static final String ACTION_VIDEO_SHOW = "com.tencent.avsdk.ACTION_VIDEO_SHOW";
    public static final int AUDIO_MEDIA_PLAYBACK = 2;
    public static final int AUDIO_MEDIA_PLAY_RECORD = 1;
    public static final int AUDIO_VOICE_CHAT_MODE = 0;
    public static final int EDIT_RESULT_CODE = 20000;
    public static final int ENV_FORMAL = 0;
    public static final int ENV_TEST = 1;
    public static final String EXTRA_ADDRESS = "address";
    public static final String EXTRA_AV_ERROR_RESULT = "av_error_result";
    public static final String EXTRA_CONSTELLATION = "constellation";
    public static final String EXTRA_GROUP_ID = "groupid";
    public static final String EXTRA_HEAD_IMAGE_PATH = "headimagepath";
    public static final String EXTRA_IDENTIFIER = "identifier";
    public static final String EXTRA_IDENTIFIER_LIST_INDEX = "QQIdentifier";
    public static final String EXTRA_IS_ASKFOR_MEMVIDEO = "askfor_memvideo";
    public static final String EXTRA_IS_ENABLE = "isEnable";
    public static final String EXTRA_IS_FRONT = "isFront";
    public static final String EXTRA_IS_VIDEO = "isVideo";
    public static final String EXTRA_LEAVE_MODE = "leave_mode";
    public static final String EXTRA_LIVEPHONE = "livephone";
    public static final String EXTRA_LIVE_TITLE = "livetitle";
    public static final String EXTRA_LIVE_VIDEO_INFO = "LiveVideoInfo";
    public static final String EXTRA_PASSWORD = "password";
    public static final String EXTRA_PRAISE_NUM = "praisenum";
    public static final String EXTRA_PROGRAM_ID = "programid";
    public static final String EXTRA_RECORDTIME = "duration";
    public static final String EXTRA_RELATION_ID = "relationId";
    public static final String EXTRA_REPLAYID = "replayid";
    public static final String EXTRA_ROOM_ID = "roomId";
    public static final String EXTRA_ROOM_NUM = "roomnum";
    public static final String EXTRA_SELF_IDENTIFIER = "selfIdentifier";
    public static final String EXTRA_SEX = "sex";
    public static final String EXTRA_SIGNATURE = "signature";
    public static final String EXTRA_SUBJECT = "subject";
    public static final String EXTRA_TOPICID = "topicid";
    public static final String EXTRA_USER_NAME = "username";
    public static final String EXTRA_USER_PHONE = "userphone";
    public static final String EXTRA_USER_SIG = "usersig";
    public static final String EXTRA_VIDEO_SRC_TYPE = "videoSrcType";
    public static final String EXTRA_VIEWER_NUM = "viewernum";
    public static final int INTEGERATE = 2;
    public static final String JSON_KEY_CODE = "code";
    public static final String JSON_KEY_DATA = "data";
    public static final String JSON_KEY_FORCE = "force";
    public static final String JSON_KEY_LOGIN_DATA = "logindata";
    public static final String JSON_KEY_USER_INFO = "userinfo";
    public static final String JSON_KEY_VERSION = "version";
    private static final String PACKAGE = "com.tencent.avsdk";
    public static final int SHOW_RESULT_CODE = 10000;
    private static final String TAG = "Util";
    public static final int TRUSTEESHIP = 1;
    public static final int VIEW_RESULT_CODE = 30000;

    /* renamed from: com.gitrose.mobile.avsdk.control.Util.1 */
    class C08831 implements OnClickListener {
        C08831() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    public static ProgressDialog newProgressDialog(Context context, int titleId) {
        ProgressDialog result = new ProgressDialog(context);
        result.setTitle(titleId);
        result.setIndeterminate(true);
        result.setCancelable(false);
        return result;
    }

//    public static AlertDialog newErrorDialog(Context context, int titleId) {
//        return new Builder(context).setTitle(titleId).setMessage("\u9519\u8bef").setPositiveButton(17039370, new C08831()).create();
//    }

    public static void switchWaitingDialog(Context ctx, ProgressDialog waitingDialog, int dialogId, boolean isToShow) {
        if (isToShow) {
            if ((waitingDialog == null || !waitingDialog.isShowing()) && (ctx instanceof Activity) && !((Activity) ctx).isFinishing()) {
                ((Activity) ctx).showDialog(dialogId);
            }
        } else if (waitingDialog != null && waitingDialog.isShowing()) {
            waitingDialog.dismiss();
        }
    }

//    public static int getNetWorkType(Context context) {
//        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
//        if (networkInfo == null || !networkInfo.isConnected()) {
//            return ENV_FORMAL;
//        }
//        String type = networkInfo.getTypeName();
//        if (type.equalsIgnoreCase("WIFI")) {
//            return INTEGERATE;
//        }
//        if (!type.equalsIgnoreCase("MOBILE")) {
//            return ENV_FORMAL;
//        }
//        NetworkInfo mobileInfo = manager.getNetworkInfo(ENV_FORMAL);
//        if (mobileInfo == null) {
//            return ENV_FORMAL;
//        }
//        switch (mobileInfo.getType()) {
//            case ENV_FORMAL /*0*/:
//                switch (mobileInfo.getSubtype()) {
//                    case TRUSTEESHIP /*1*/:
//                    case INTEGERATE /*2*/:
//                    case PersonalNewActivity.WORK_LIST_9 /*4*/:
//                    case PersonalNewActivity.COLLECT_LIST_DETAIL /*7*/:
//                    case ReqBody.STR_DEV_TYPE_FIELD_NUMBER /*11*/:
//                        return 4;
//                    case PersonalNewActivity.FRAGMENT_FOURE /*3*/:
//                    case PersonalNewActivity.WORK_LIST_DETAIL /*5*/:
//                    case PersonalNewActivity.COLLECT_LIST_9 /*6*/:
//                    case RspBody.UINT32_HELLO_INTERVAL_FIELD_NUMBER /*8*/:
//                    case HomeFragment.ADD_COMMENT_RESULT /*9*/:
//                    case PersonalWorksListActiviy.ADD_TOPIC_RESULT /*10*/:
//                    case ReqBody.STR_OS_VER_FIELD_NUMBER /*12*/:
//                    case ReqBody.STR_FIRMWARE_VER_FIELD_NUMBER /*14*/:
//                    case PersonalWorksListActiviy.FAV_RESULT /*15*/:
//                        return 3;
//                    case ReqBody.STR_BUILD_VER_FIELD_NUMBER /*13*/:
//                        return 5;
//                    default:
//                        return ENV_FORMAL;
//                }
//            default:
//                return ENV_FORMAL;
//        }
//    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected() && info.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
