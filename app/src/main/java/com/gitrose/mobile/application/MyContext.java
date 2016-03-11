package com.gitrose.mobile.application;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
//import com.sea_monster.core.common.DiscardOldestPolicy;
//import com.sea_monster.core.network.ApiReqeust;
//import com.sea_monster.core.network.DefaultHttpHandler;
//import com.sea_monster.core.resource.io.FileSysHandler;
//import com.sea_monster.core.resource.io.IFileSysHandler;
//import com.sea_monster.core.resource.io.ResourceRemoteWrapper;
//import com.tencent.android.tpush.XGIOperateCallback;
//import com.tencent.android.tpush.XGPushManager;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.C0306R;
//import com.gitrose.mobile.HomeActivity;
import com.gitrose.mobile.LoginActivity;
import com.gitrose.mobile.MessageHistoryService;
//import com.gitrose.mobile.RCChatActivity;
//import com.gitrose.mobile.RCConversationActivity;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.auth.UserAuth;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.BaseDialog;
//import com.umeng.analytics.MobclickAgent;
//import io.rong.imlib.RongIMClient;
//import io.rong.imlib.RongIMClient.ConnectCallback;
//import io.rong.imlib.RongIMClient.ConnectCallback.ErrorCode;
//import io.rong.imlib.RongIMClient.ConnectionStatusListener;
//import io.rong.imlib.RongIMClient.ConnectionStatusListener.ConnectionStatus;
//import io.rong.imlib.RongIMClient.Message;
//import io.rong.imlib.RongIMClient.OnReceiveMessageListener;
//import io.rong.message.CommandNotificationMessage;
//import io.rong.message.ContactNotificationMessage;
//import io.rong.message.ImageMessage;
//import io.rong.message.ProfileNotificationMessage;
//import io.rong.message.TextMessage;
//import io.rong.message.VoiceMessage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
//import shouji.gexing.framework.utils.ContextUtils;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.FileUtils;
//import shouji.gexing.framework.utils.GetPopPackageName;
//import shouji.gexing.framework.utils.SpUtils;

public class MyContext {
    private static final String NOMEDIA = ".nomedia";
    private static final String TAG = "DemoContext";
    private static ThreadPoolExecutor sExecutor;
    private static MyContext self;
    private final int CONNECT_STATUE;
    private final int KICKED_OFFLINE;
    private final int REFESH_UI;
    private String RMToken;
//    private ConnectionStatusListener connectListener;
    private BaseDialog dialog;
    public boolean home_need_check;
    private String localPackagename;
    public Context mContext;
    ThreadPoolExecutor mExecutor;
//    private IFileSysHandler mFileSysHandler;
    private Handler mHandler;
    private String mResourceDir;
//    public RongIMClient mRongIMClient;
    private ThreadFactory mThreadFactory;
    private BlockingQueue<Runnable> mWorkQueue;
    private View more_view;
    private String popActivityName;
//    private Message rMessage;
    private SharedPreferences sharedPreferences;
    public String userId;

    /* renamed from: com.gitrose.mobile.application.MyContext.1 */
//    class C03481 extends Handler {
//
//        /* renamed from: com.gitrose.mobile.application.MyContext.1.1 */
//        class C03461 implements OnClickListener {
//            private final /* synthetic */ Activity val$activity;
//
//            /* renamed from: com.gitrose.mobile.application.MyContext.1.1.1 */
//            class C05391 implements XGIOperateCallback {
//                C05391() {
//                }
//
//                public void onSuccess(Object arg0, int arg1) {
//                }
//
//                public void onFail(Object arg0, int arg1, String arg2) {
//                }
//            }
//
//            /* renamed from: com.gitrose.mobile.application.MyContext.1.1.2 */
//            class C07192 extends QGHttpHandler<String> {
//                C07192(Context $anonymous0) {
//                    super($anonymous0);
//                }
//
//                public void onGetDataSuccess(String data) {
//                }
//            }
//
//            C03461(Activity activity) {
//                this.val$activity = activity;
//            }
//
//            public void onClick(View v) {
//                XGPushManager.registerPush(MyContext.this.mContext, "*", new C05391());
//                if (!(MyApplication.getInstance().getUserinfo() == null || !"0".equals(MyApplication.getInstance().getUserinfo().getIsqqlogin()) || MyApplication.getInstance().mTencent == null)) {
//                    MyApplication.getInstance().mTencent.logout(MyApplication.getInstance());
//                }
//                QGHttpRequest.getInstance().logoutRequest(MyContext.this.mContext, new C07192(MyContext.this.mContext));
//                UserAuth.getInstance().invinvalidateUserIdentity(MyContext.this.mContext);
//                this.val$activity.startActivity(new Intent(this.val$activity, LoginActivity.class));
//                MyContext.this.dialog.dismiss();
//            }
//        }
//
//        /* renamed from: com.gitrose.mobile.application.MyContext.1.2 */
//        class C03472 implements OnClickListener {
//            C03472() {
//            }
//
//            public void onClick(View v) {
//                MyContext.this.connectRongyun();
//                MyContext.this.dialog.dismiss();
//            }
//        }
//
//        C03481() {
//        }
//
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
//                    if (MyContext.this.mContext != null) {
//                        MyContext.this.localPackagename = ContextUtils.getPackageName(MyContext.this.mContext);
//                        MyContext.this.popActivityName = GetPopPackageName.getTopActiivtyName(MyContext.this.mContext);
//                        if (MyContext.this.popActivityName.equals(new StringBuilder(String.valueOf(MyContext.this.localPackagename)).append(".RCChatActivity").toString())) {
//                            if (RCChatActivity.getObject() != null) {
//                                RCChatActivity.getObject().refresh(MyContext.this.rMessage);
//                            }
//                        } else if (MyContext.this.popActivityName.equals(new StringBuilder(String.valueOf(MyContext.this.localPackagename)).append(".RCConversationActivity").toString()) && RCConversationActivity.getInstance() != null) {
//                            RCConversationActivity.getInstance().refresh();
//                        }
//                    }
//                case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                    if (ActivityManager.getScreenManager() != null) {
//                        Activity activity = ActivityManager.getScreenManager().currentActivity();
//                        if (activity != null) {
//                            MyContext.this.dialog = new BaseDialog(activity, 16);
//                            MyContext.this.more_view = View.inflate(activity, C0306R.layout.dialog_offline, null);
//                            MyContext.this.more_view.findViewById(C0306R.id.offline_dialog_cancel_bt).setOnClickListener(new C03461(activity));
//                            MyContext.this.more_view.findViewById(C0306R.id.offline_dialog_login_bt).setOnClickListener(new C03472());
//                            MyContext.this.dialog.show(MyContext.this.more_view);
//                        }
//                    }
//                case ApiReqeust.PUT_METHOD /*3*/:
//                    if (RCChatActivity.getObject() != null) {
//                        RCChatActivity.getObject().refreshTitile();
//                    }
//                    if (RCConversationActivity.getInstance() != null) {
//                        RCConversationActivity.getInstance().refreshTitile();
//                    }
//                default:
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.application.MyContext.3 */
    class C03493 implements ThreadFactory {
        private final AtomicInteger mCount;

        C03493() {
            this.mCount = new AtomicInteger(1);
        }

        public Thread newThread(Runnable r) {
            return new Thread(r, "ConnectTask #" + this.mCount.getAndIncrement());
        }
    }

    /* renamed from: com.gitrose.mobile.application.MyContext.2 */
//    class C05402 implements ConnectionStatusListener {
//        C05402() {
//        }
//
//        public void onChanged(ConnectionStatus status) {
//            if (status == ConnectionStatus.CONNECTED) {
//                DebugUtils.error("RM---------CONNECTED");
//            } else if (status == ConnectionStatus.CONNECTING) {
//                DebugUtils.error("RM---------CONNECTING");
//            } else if (status == ConnectionStatus.DISCONNECTED) {
//                DebugUtils.error("RM---------DISCONNECTED");
//            } else if (status == ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT) {
//                DebugUtils.error("RM---------KICKED_OFFLINE_BY_OTHER_CLIENT");
//                MyContext.this.mHandler.sendEmptyMessage(1);
//            } else if (status == ConnectionStatus.LOGIN_ON_WEB) {
//                DebugUtils.error("RM---------LOGIN_ON_WEB");
//            } else if (status == ConnectionStatus.NETWORK_UNAVAILABLE) {
//                DebugUtils.error("RM---------NETWORK_UNAVAILABLE");
//            } else if (status == ConnectionStatus.UNKNOWN) {
//                DebugUtils.error("RM---------UNKNOWN");
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.application.MyContext.4 */
//    class C05414 implements OnReceiveMessageListener {
//        C05414() {
//        }
//
//        public void onReceived(Message message, int left) {
//            DebugUtils.error("\u6536\u5230\u6d88\u606fRC---------------");
//            MyContext.this.rMessage = message;
//            MyContext.this.mHandler.sendEmptyMessage(0);
//            if (message.getContent() instanceof TextMessage) {
//                TextMessage textMessage = (TextMessage) message.getContent();
//            } else if (message.getContent() instanceof ImageMessage) {
//                ImageMessage imageMessage = (ImageMessage) message.getContent();
//            } else if (message.getContent() instanceof VoiceMessage) {
//                VoiceMessage voiceMessage = (VoiceMessage) message.getContent();
//            } else if (message.getContent() instanceof ContactNotificationMessage) {
//                ContactNotificationMessage contactNotificationMessage = (ContactNotificationMessage) message.getContent();
//            } else if (message.getContent() instanceof ProfileNotificationMessage) {
//                ProfileNotificationMessage profileNotificationMessage = (ProfileNotificationMessage) message.getContent();
//            } else if (message.getContent() instanceof CommandNotificationMessage) {
//                CommandNotificationMessage commandNotificationMessage = (CommandNotificationMessage) message.getContent();
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.application.MyContext.5 */
//    class C05425 implements ConnectCallback {
//        C05425() {
//        }
//
//        public void onSuccess(String userId) {
//            MyContext.getInstance().setUserId(userId);
//            MyApplication.getInstance().setRc_is_connect(true);
//            if (HomeActivity.getIntance() != null) {
//                HomeActivity.getIntance().checkNeedHistory();
//            } else {
//                MyContext.this.home_need_check = true;
//            }
//            MyContext.this.mHandler.sendEmptyMessage(3);
//            if (MyContext.this.mContext != null) {
//                MobclickAgent.onEvent(MyContext.this.mContext, "rong_connect_suc_num");
//            }
//        }
//
//        public void onError(ErrorCode errorCode) {
//            MyApplication.getInstance().setRc_is_error(true);
//            MyContext.this.mHandler.sendEmptyMessage(3);
//            if (MyContext.this.mContext != null) {
//                MobclickAgent.onEvent(MyContext.this.mContext, "rong_connect_err_" + errorCode.getValue());
//                MobclickAgent.onEvent(MyContext.this.mContext, "rong_connect_err_num");
//            }
//        }
//    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRMToken() {
        return this.RMToken;
    }

    public void setRMToken(String rMToken) {
        //DebugUtils.error("Debug", "token---" + rMToken);
        this.RMToken = rMToken;
    }

    public static MyContext getInstance() {
        if (self == null) {
            self = new MyContext();
        }
        return self;
    }

    public MyContext() {
        this.REFESH_UI = 0;
        this.KICKED_OFFLINE = 1;
        this.CONNECT_STATUE = 3;
//        this.mHandler = new C03481();
//        this.connectListener = new C05402();
    }

    public MyContext(Context context) {
        this.REFESH_UI = 0;
        this.KICKED_OFFLINE = 1;
        this.CONNECT_STATUE = 3;
//        this.mHandler = new C03481();
//        this.connectListener = new C05402();
        self = this;
    }

//    private void sendNotification(Context context, Message message) {
//        try {
//            DebugUtils.error("RC  notifacation------");
//            NotificationManager nm = (NotificationManager) context.getSystemService("notification");
//            String title = "Tutu";
//            String content = "\u65b0\u79c1\u4fe1";
//            String to_uid = message.getSenderUserId();
//            if (message.getContent() instanceof TextMessage) {
//                content = ((TextMessage) message.getContent()).getContent();
//            } else if (message.getContent() instanceof ImageMessage) {
//                content = "[\u56fe\u7247]";
//            } else if (message.getContent() instanceof VoiceMessage) {
//                content = "[\u8bed\u97f3]";
//            }
//            Notification notification = new Notification();
//            notification.when = System.currentTimeMillis();
//            notification.icon = C0306R.drawable.ic_launcher;
//            notification.flags = 16;
//            if (context.getSharedPreferences(Constant.PLAY_SOUND_PERFERENCE, 0).getBoolean(PlaySound.SOUND_OPEN, true)) {
//                notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + C0306R.raw.push);
//            }
//            Intent nIntent = new Intent();
//            nIntent.setClass(this.mContext, RCChatActivity.class);
//            nIntent.putExtra("touid", to_uid);
//            nIntent.addFlags(67108864);
//            nIntent.addFlags(268435456);
//            notification.setLatestEventInfo(context, title, content, PendingIntent.getActivity(context, 0, nIntent, 1207959552));
//            nm.notify(3, notification);
//        } catch (Exception e) {
//        }
//    }

    public void init(Context context) {
        this.mContext = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mExecutor = new ThreadPoolExecutor(2, 3, 1, TimeUnit.SECONDS, new PriorityBlockingQueue(25), new C03493());
//        this.mExecutor.setRejectedExecutionHandler(new DiscardOldestPolicy());
//        this.mFileSysHandler = new FileSysHandler(this.mExecutor, getResourceDir(this.mContext), "file", "rong");
//        ResourceRemoteWrapper remoteWrapper = new ResourceRemoteWrapper(this.mContext, this.mFileSysHandler, new DefaultHttpHandler(this.mContext, this.mExecutor));
    }

    public String getFileSysDir(String dir) {
        if (!TextUtils.isEmpty(this.mResourceDir)) {
            return this.mResourceDir;
        }
        File environmentPath;
        if (Environment.getExternalStorageState().equals("mounted")) {
            environmentPath = Environment.getExternalStorageDirectory();
        } else {
            environmentPath = this.mContext.getFilesDir();
        }
        File baseDirectory = new File(environmentPath, dir);
        createDirectory(baseDirectory);
        String absolutePath = baseDirectory.getAbsolutePath();
        this.mResourceDir = absolutePath;
        return absolutePath;
    }

    private static final void createDirectory(File storageDirectory) {
        if (!storageDirectory.exists()) {
            Log.d(TAG, "Trying to create storageDirectory: " + String.valueOf(storageDirectory.mkdirs()));
            Log.d(TAG, "Exists: " + storageDirectory + " " + String.valueOf(storageDirectory.exists()));
            Log.d(TAG, "State: " + Environment.getExternalStorageState());
            Log.d(TAG, "Isdir: " + storageDirectory + " " + String.valueOf(storageDirectory.isDirectory()));
            Log.d(TAG, "Readable: " + storageDirectory + " " + String.valueOf(storageDirectory.canRead()));
            Log.d(TAG, "Writable: " + storageDirectory + " " + String.valueOf(storageDirectory.canWrite()));
            File tmp = storageDirectory.getParentFile();
            Log.d(TAG, "Exists: " + tmp + " " + String.valueOf(tmp.exists()));
            Log.d(TAG, "Isdir: " + tmp + " " + String.valueOf(tmp.isDirectory()));
            Log.d(TAG, "Readable: " + tmp + " " + String.valueOf(tmp.canRead()));
            Log.d(TAG, "Writable: " + tmp + " " + String.valueOf(tmp.canWrite()));
            tmp = tmp.getParentFile();
            Log.d(TAG, "Exists: " + tmp + " " + String.valueOf(tmp.exists()));
            Log.d(TAG, "Isdir: " + tmp + " " + String.valueOf(tmp.isDirectory()));
            Log.d(TAG, "Readable: " + tmp + " " + String.valueOf(tmp.canRead()));
            Log.d(TAG, "Writable: " + tmp + " " + String.valueOf(tmp.canWrite()));
            File nomediaFile = new File(storageDirectory, NOMEDIA);
            if (!nomediaFile.exists()) {
                try {
                    Log.d(TAG, "Created file: " + nomediaFile + " " + String.valueOf(nomediaFile.createNewFile()));
                } catch (IOException e) {
                    Log.d(TAG, "Unable to create .nomedia file for some reason.", e);
                    throw new IllegalStateException("Unable to create nomedia file.");
                }
            }
            if (!storageDirectory.isDirectory() || !nomediaFile.exists()) {
                throw new RuntimeException("Unable to create storage directory and nomedia file.");
            }
        }
    }

    public String getResourceDir() {
        return this.mResourceDir;
    }

    private final File getResourceDir(Context context) {
        File baseDirectory = null;
        if (MyApplication.getInstance().isInit()) {
            File environmentPath;
            if (Environment.getExternalStorageState().equals("mounted")) {
                environmentPath = new File(Environment.getExternalStorageDirectory(), "Android/data/" + context.getPackageName());
            } else {
                environmentPath = context.getFilesDir();
            }
            baseDirectory = new File(environmentPath, "RongCloud");
        } else if (MyApplication.getInstance().isNeed_cachePth()) {
            baseDirectory = new File(new StringBuilder(String.valueOf(MyApplication.getInstance().cache_path)).append("/Android/data/").toString(), "RongCloud");
        } else {
            //baseDirectory = new File(new StringBuilder(String.valueOf(FileUtils.getSavePath(this.mContext, 1048576))).append("/Android/data/").toString(), "RongCloud");
        }
//        if (!baseDirectory.exists()) {
//            baseDirectory.mkdirs();
//        }
//        this.mResourceDir = baseDirectory.getPath();
        return baseDirectory;
    }

//    public void setRongIMClient(RongIMClient rongIMClient) {
//        this.mRongIMClient = rongIMClient;
//    }
//
//    public RongIMClient getRongIMClient() {
//        return this.mRongIMClient;
//    }
//
//    public void registerMessage() {
//        this.mRongIMClient.setOnReceiveMessageListener(new C05414());
//    }

    public void registerConnect() {
//        DebugUtils.error("RM---------registerConnect");
//        RongIMClient.setConnectionStatusListener(this.connectListener);
    }

    public void connectRongyun() {
        try {
//            if (this.mContext != null) {
//                MobclickAgent.onEvent(this.mContext, "rong_connect_total_num");
//            }
//            this.mRongIMClient = RongIMClient.connect(getInstance().getRMToken(), new C05425());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        getInstance().setRongIMClient(this.mRongIMClient);
//        getInstance().registerMessage();
        getInstance().registerConnect();
    }

    public void checkNeedHistory() {
        TutuUsers user = MyApplication.getInstance().getUserinfo();
//        if (user != null && user.getUid() != null) {
//            String need = (String) SpUtils.getFromLocal(this.mContext, "import_history", new StringBuilder(String.valueOf(user.getUid())).toString(), "0");
//            DebugUtils.error("debug", "---------------------checkNeedHistory:need:" + need);
//            if (need == null || !need.equals(Constant.SYSTEM_UID)) {
//                startGetHistory();
//            }
//        }
    }

    private void startGetHistory() {
        TutuUsers user = MyApplication.getInstance().getUserinfo();
        if (user != null && user.getUid() != null) {
//            String need = (String) SpUtils.getFromLocal(this.mContext, "import_history", new StringBuilder(String.valueOf(user.getUid())).toString(), "0");
//            DebugUtils.error("debug", "---------------------checkNeedHistory:need:" + need);
//            if (need == null || !need.equals(Constant.SYSTEM_UID)) {
//                DebugUtils.error("debug", "---------------------startGetHistory");
//                Intent intent = new Intent(this.mContext, MessageHistoryService.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("tutu_id", user.getUid());
//                intent.putExtras(bundle);
//                this.mContext.startService(intent);
//            }
        }
    }
}
