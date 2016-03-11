package com.gitrose.mobile;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.dao.HistoryDao;
import com.gitrose.mobile.dao.RmDao;
//import com.gitrose.mobile.http.QGHttpHandlerFileAsyn;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.HistoryConversation;
import com.gitrose.mobile.model.HistoryMessage;
import com.gitrose.mobile.model.HistoryStringMessage;
import com.gitrose.mobile.model.UserInfo;
//import io.rong.message.BuildConfig;
import java.io.File;
import java.util.List;
//import org.apache.http.Header;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.FileUtils;
//import shouji.gexing.framework.utils.SpUtils;

public class MessageHistoryService extends Service {
    public static final String ACTION_DOWN_HISTROY = "action.service.message.down";
    private int icount;
    boolean isDown;

    /* renamed from: com.gitrose.mobile.MessageHistoryService.1 */
    class C02881 implements Runnable {
        private final /* synthetic */ List val$hc;
        private final /* synthetic */ List val$hm;

        C02881(List list, List list2) {
            this.val$hc = list;
            this.val$hm = list2;
        }

        public void run() {
            if (!(RmDao.getInstance() == null || this.val$hc == null || this.val$hc.size() <= 0)) {
                RmDao.getInstance().insertConversation(this.val$hc);
            }
            if (RmDao.getInstance() != null && this.val$hm != null && this.val$hm.size() > 0) {
                RmDao.getInstance().insertMessage(this.val$hm);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.MessageHistoryService.2 */
    class C02892 implements Runnable {
        private final /* synthetic */ List val$hc;
        private final /* synthetic */ List val$hm;

        C02892(List list, List list2) {
            this.val$hc = list;
            this.val$hm = list2;
        }

        public void run() {
            if (!(RmDao.getInstance() == null || this.val$hc == null || this.val$hc.size() <= 0)) {
                RmDao.getInstance().insertConversation(this.val$hc);
            }
            if (RmDao.getInstance() != null && this.val$hm != null && this.val$hm.size() > 0) {
                RmDao.getInstance().insertMessageString(this.val$hm);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.MessageHistoryService.3 */
//    class C07433 extends QGHttpHandlerFileAsyn {
//        C07433(Context $anonymous0, File $anonymous1) {
//            super($anonymous0, $anonymous1);
//        }
//
//        public void onStart() {
//            super.onStart();
//            MessageHistoryService.this.isDown = true;
//        }
//
//        public void onProgress(int bytesWritten, int totalSize) {
//            super.onProgress(bytesWritten, totalSize);
//            MessageHistoryService messageHistoryService = MessageHistoryService.this;
//            messageHistoryService.icount = messageHistoryService.icount + 1;
//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            double bytes = ((double) bytesWritten) * 1.0d;
//            double total = ((double) totalSize) * 1.0d;
//            if (((bytes / total) * 100.0d) + 1.0d < 99.0d) {
//                bundle.putInt(HomeActivity.DOWN, (int) (((bytes / total) * 100.0d) + 1.0d));
//            } else {
//                bundle.putInt(HomeActivity.DOWN, 99);
//            }
//            intent.putExtras(bundle);
//            intent.setAction(MessageHistoryService.ACTION_DOWN_HISTROY);
//            MessageHistoryService.this.sendBroadcast(intent);
//        }
//
//        public void onSuccess(int arg0, Header[] arg1, File file) {
//            super.onSuccess(arg0, arg1, file);
//            DebugUtils.error("QGHttpRequest.getInstance().getMHConversation--------onSuccess");
//            try {
//                MessageHistoryService.this.insertMessageHistoryString(HistoryDao.getInstance(MessageHistoryService.this).getHistoryConversation(), HistoryDao.getInstance(MessageHistoryService.this).getHistoryMessageString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            UserInfo user = MyApplication.getInstance().getUserinfo();
//            if (user != null && user.getUid() != null) {
//                SpUtils.saveToLocal(MessageHistoryService.this, "import_history", new StringBuilder(String.valueOf(user.getUid())).toString(), Constant.SYSTEM_UID);
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putInt(HomeActivity.DOWN, 100);
//                intent.putExtras(bundle);
//                intent.setAction(MessageHistoryService.ACTION_DOWN_HISTROY);
//                MessageHistoryService.this.sendBroadcast(intent);
//            }
//        }
//
//        public void onFailure(int arg0, Header[] arg1, Throwable throwable, File arg3) {
//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putInt(HomeActivity.DOWN, -100);
//            intent.putExtras(bundle);
//            intent.setAction(MessageHistoryService.ACTION_DOWN_HISTROY);
//            MessageHistoryService.this.sendBroadcast(intent);
//        }
//
//        public void onFinish() {
//            super.onFinish();
//            MessageHistoryService.this.isDown = false;
//        }
//    }

    public MessageHistoryService() {
        this.icount = 0;
        this.isDown = false;
    }

    public void onCreate() {
        super.onCreate();
//        DebugUtils.error("Message--------------------------");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        getMHistoryRequest();
        return super.onStartCommand(intent, flags, startId);
    }

    private void insertMessageHistory(List<HistoryConversation> hc, List<HistoryMessage> hm) throws Exception {
        new Thread(new C02881(hc, hm)).start();
    }

    private void insertMessageHistoryString(List<HistoryConversation> hc, List<HistoryStringMessage> hm) throws Exception {
        new Thread(new C02892(hc, hm)).start();
    }

    private void getMHistoryRequest() {
        if (!this.isDown) {
            String uid = BuildConfig.FLAVOR;
            if (MyApplication.getInstance().getUserinfo() != null) {
                uid = MyApplication.getInstance().getUserinfo().getUid();
            }
//            File file = new File(FileUtils.getSavePath(this, 10485760), "private_data" + uid + ".db");
//            DebugUtils.error("debug", file.getAbsolutePath());
//            UserInfo user = MyApplication.getInstance().getUserinfo();
//            if (!(user == null || user.getUid() == null)) {
//                String need = (String) SpUtils.getFromLocal(this, "import_history", new StringBuilder(String.valueOf(user.getUid())).toString(), "0");
//                DebugUtils.error("debug", "---------------------checkNeedHistory:need:" + need);
//                if (need != null && need.equals(Constant.SYSTEM_UID)) {
//                    return;
//                }
//            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
//            bundle.putInt(HomeActivity.DOWN, 0);
            intent.putExtras(bundle);
            intent.setAction(ACTION_DOWN_HISTROY);
            sendBroadcast(intent);
//            QGHttpRequest.getInstance().getMHConversation(this, new C07433(this, file));
        }
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
