package com.gitrose.mobile;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceActivity;

import com.gitrose.greendao.ApplyInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.dao.ContactsDao;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.ContactsLocalInfo;
import com.gitrose.mobile.model.SyncFriendsList;
import com.gitrose.mobile.model.SyncSelfInfo;
import com.gitrose.mobile.model.SyncTopicList;
//import com.gitrose.mobile.utils.GreenDaoUtils;
import com.gitrose.mobile.view.CompatViewPager;
import java.util.ArrayList;
import java.util.List;
//import org.apache.http.Header;
import qalsdk.BaseConstants;
//import shouji.gexing.framework.utils.DebugUtils;

public class TutuService extends Service {
    private static final String TAG = "TutuService";
    private final int SYS_FRIENDS;
    private final int SYS_TOPIC;
    private int current_sys_topic_num;
//    private TutuBroadcastReceiver mBroadcastReceiver;
    private Handler mhandler;

//    private class DelayHandler extends Handler {
//        private DelayHandler() {
//        }
//
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1 /*1*/:
//                        TutuService.this.requestSysnTopic(GreenDaoUtils.getNewTimeTopicList(TutuService.this, null), GreenDaoUtils.getLastTimeTopicList(TutuService.this, null), GreenDaoUtils.getSyncTime(TutuService.this, "topic"), null);
//                    break;
//                case 2 /*2*/:
//                    TutuService.this.getSyncFriendsList(GreenDaoUtils.getNewTimeFriendsList(TutuService.this), GreenDaoUtils.getLastTimeFriendsList(TutuService.this), GreenDaoUtils.getSyncTime(TutuService.this, "friends"));
//                    break;
//                default:
//            }
//        }
//    }
//
//    private class TutuBroadcastReceiver extends BroadcastReceiver {
//        private TutuBroadcastReceiver() {
//        }
//
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action != null) {
//                if (Constant.ACTION_SYNC_SELF_ALL.equals(action)) {
//                    TutuService.this.getSyncCurrentUser(GreenDaoUtils.getSyncTime(TutuService.this, "self"));
//                    TutuService.this.getSyncTopicList(GreenDaoUtils.getNewTimeTopicList(TutuService.this, null), GreenDaoUtils.getLastTimeTopicList(TutuService.this, null), GreenDaoUtils.getSyncTime(TutuService.this, "topic"), null);
//                    TutuService.this.getSyncFriendsList(GreenDaoUtils.getNewTimeFriendsList(TutuService.this), GreenDaoUtils.getLastTimeFriendsList(TutuService.this), GreenDaoUtils.getSyncTime(TutuService.this, "friends"));
//                }
//                if (Constant.ACTION_SYNC_SELF_INFO.equals(action)) {
//                    TutuService.this.getSyncCurrentUser(GreenDaoUtils.getSyncTime(TutuService.this, "self"));
//                }
//                if (Constant.ACTION_SYNC_SELF_TOPIC.equals(action) || Constant.UPLOAD_TOPIC_FINISH_ACTION.equals(action) || Constant.ADD_FAV_TOPIC_ACTION.equals(action) || Constant.DEL_FAV_TOPIC_ACTION.equals(action) || Constant.REPOST_TOPIC_ACTION.equals(action) || Constant.DEL_REPOST_TOPIC_ACTION.equals(action) || Constant.DEL_TOPIC_ACTION.equals(action) || Constant.FOLLOW_TOPIC_ACTION.equals(action)) {
//                    DebugUtils.error("\u63a5\u6536\u5230 \u540c\u6b65\u4e2a\u4eba\u4fe1\u606f\u7684\u5e7f\u64ad\uff0d\uff0d\uff0d\uff0d");
//                    TutuService.this.getSyncTopicList(GreenDaoUtils.getNewTimeTopicList(TutuService.this, null), GreenDaoUtils.getLastTimeTopicList(TutuService.this, null), GreenDaoUtils.getSyncTime(TutuService.this, "topic"), null);
//                }
//                if (Constant.ACTION_SYNC_SELF_FRIENDS.equals(action) || Constant.FOLLOW_USER_ACTION.equals(action) || Constant.UNFOLLOW_USER_ACTION.equals(action)) {
//                    TutuService.this.getSyncFriendsList(GreenDaoUtils.getNewTimeFriendsList(TutuService.this), GreenDaoUtils.getLastTimeFriendsList(TutuService.this), GreenDaoUtils.getSyncTime(TutuService.this, "friends"));
//                    if (Constant.FOLLOW_USER_ACTION.equals(action) || Constant.UNFOLLOW_USER_ACTION.equals(action)) {
//                        String uid = intent.getStringExtra("to_uid");
//                        String relation = intent.getStringExtra("relation");
//                        try {
//                            ContactsLocalInfo localInfo = new ContactsDao(context).getContactsLocalInfo(uid);
//                            if (localInfo != null) {
//                                localInfo.setRelation(relation);
//                                new ContactsDao(context).updateContactsLocalInfo(localInfo);
//                            }
//                        } catch (Exception e) {
//                        }
//                    }
//                }
//                if (Constant.ACTION_READ_APPLY.equals(action)) {
//                    TutuService.this.setReadApply();
//                }
//            }
//        }
//    }
//
//    /* renamed from: com.gitrose.mobile.TutuService.1 */
//    class C17711 extends QGHttpHandler {
//        C17711(Context $anonymous0) {
//            super($anonymous0);
//        }
//
//        public void onFinish() {
//            super.onFinish();
//        }
//
//        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//
//        public void onGetDataSuccess(Object data) {
//            GreenDaoUtils.setMyApplyRead(TutuService.this);
//            MyApplication.getInstance().setApplyNum(0);
//        }
//    }
//
//    /* renamed from: com.gitrose.mobile.TutuService.2 */
//    class C17722 extends QGHttpHandler<SyncSelfInfo> {
//        C17722(Context $anonymous0, boolean $anonymous1) {
//            super($anonymous0, $anonymous1);
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            DebugUtils.error("sync-----user-------onFailure");
//        }
//
//        public void onFinish() {
//            DebugUtils.error("sync-----user-------finish");
//        }
//
//        public void onGetDataSuccess(SyncSelfInfo data) {
//            if (data != null) {
//                if (data.getUserinfo() != null) {
//                    MyApplication.getInstance().setUserinfo(data.getUserinfo());
//                    GreenDaoUtils.updateCurrentUser(TutuService.this, data.getUserinfo());
//                }
//                if (data.getUpdatetime() != null) {
//                    DebugUtils.error("sync-----user-------data.getUpdatetime()" + data.getUpdatetime());
//                    GreenDaoUtils.updateTablesTimeInfo(TutuService.this, data.getUpdatetime(), null, null);
//                }
//            }
//        }
//    }
//
//    /* renamed from: com.gitrose.mobile.TutuService.3 */
//    class C17733 extends QGHttpHandler<SyncTopicList> {
//        C17733(Context $anonymous0, boolean $anonymous1) {
//            super($anonymous0, $anonymous1);
//        }
//
//        public void onGetDataSuccess(SyncTopicList data) {
//            if (data != null) {
//                TutuService tutuService = TutuService.this;
//                tutuService.current_sys_topic_num = tutuService.current_sys_topic_num + 1;
//                if (data.getAddlist() != null && data.getAddlist().size() > 0) {
//                    GreenDaoUtils.updateTopicsInfo(TutuService.this, data.getAddlist());
//                }
//                if (data.getDellist() != null && data.getDellist().size() > 0) {
//                    GreenDaoUtils.delTopicInfo(TutuService.this, data.getDellist());
//                }
//                if (data.getUpdatetime() != null) {
//                    GreenDaoUtils.updateTablesTimeInfo(TutuService.this, null, data.getUpdatetime(), null);
//                }
//                DebugUtils.error(new StringBuilder(String.valueOf(Thread.currentThread().getName())).append("--success------currentThreadName").toString());
//                if (data.getAddlist().size() >= 21 && TutuService.this.current_sys_topic_num <= 3) {
//                    if (TutuService.this.mhandler == null) {
//                        TutuService.this.mhandler = new DelayHandler(null);
//                    }
//                    TutuService.this.mhandler.sendEmptyMessageDelayed(1, 1000);
//                }
//            }
//        }
//    }
//
//    /* renamed from: com.gitrose.mobile.TutuService.4 */
//    class C17744 extends QGHttpHandler<SyncFriendsList> {
//        C17744(Context $anonymous0, boolean $anonymous1) {
//            super($anonymous0, $anonymous1);
//        }
//
//        public void onFinish() {
//            super.onFinish();
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//
//        public void onGetDataSuccess(SyncFriendsList data) {
//            if (data != null) {
//                if (data.getAddlist() != null && data.getAddlist().size() > 0) {
//                    GreenDaoUtils.updateFriendInfo(TutuService.this, data.getAddlist(), data.getUpdatetime());
//                }
//                if (data.getDellist() != null && data.getDellist().size() > 0) {
//                    GreenDaoUtils.delFriendInfo(TutuService.this, data.getDellist());
//                }
//                if (data.getUpdatetime() != null) {
//                    GreenDaoUtils.updateTablesTimeInfo(TutuService.this, null, null, data.getUpdatetime());
//                }
//                if (data.getAddlist().size() == CompatViewPager.SNAP_VELOCITY) {
//                    if (TutuService.this.mhandler == null) {
//                        TutuService.this.mhandler = new DelayHandler(null);
//                    }
//                    TutuService.this.mhandler.sendEmptyMessageDelayed(2, 1000);
//                }
//            }
//        }
//    }

    public TutuService() {
        this.current_sys_topic_num = 0;
        this.SYS_TOPIC = 1;
        this.SYS_FRIENDS = 2;
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        registerBroadCast();
//        this.mhandler = new DelayHandler();
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
//        TutuUsers tutuUsers = MyApplication.getInstance().getUserinfo();
//        if (!(tutuUsers == null || tutuUsers.getUid() == null || tutuUsers.getUid().trim().equals(BaseConstants.ah))) {
//            getSyncFriendsList(GreenDaoUtils.getNewTimeFriendsList(this), GreenDaoUtils.getLastTimeFriendsList(this), GreenDaoUtils.getSyncTime(this, "friends"));
//            getSyncTopicList(GreenDaoUtils.getNewTimeTopicList(this, null), GreenDaoUtils.getLastTimeTopicList(this, null), GreenDaoUtils.getSyncTime(this, "topic"), null);
//            getSyncCurrentUser(GreenDaoUtils.getSyncTime(this, "self"));
//        }
        return flags;
    }

    private void setReadApply() {
//        String uid_list = null;
//        ArrayList arrayList = new ArrayList();
//        List<ApplyInfo> infos = GreenDaoUtils.getMyUnreadApply(this);
//        for (int i = 0; i < infos.size(); i++) {
//            if (uid_list == null) {
//                uid_list = ((ApplyInfo) infos.get(i)).getFrienduid();
//            } else {
//                uid_list = new StringBuilder(String.valueOf(uid_list)).append(",").append(((ApplyInfo) infos.get(i)).getFrienduid()).toString();
//            }
//        }
//        if (uid_list != null) {
//            QGHttpRequest.getInstance().readApply(this, uid_list, new C17711(this));
//        }
    }

    private void getSyncCurrentUser(String localupdatetime) {
//        QGHttpRequest.getInstance().getSyncSelfInfoHttpRequest(this, localupdatetime, new C17722(this, false));
    }

    private void getSyncTopicList(String localnewtime, String locallasttime, String localupdatetime, String locallisttype) {
        this.current_sys_topic_num = 0;
        requestSysnTopic(localnewtime, locallasttime, localupdatetime, locallisttype);
    }

    private void requestSysnTopic(String localnewtime, String locallasttime, String localupdatetime, String locallisttype) {
//        DebugUtils.error(new StringBuilder(String.valueOf(Thread.currentThread().getName())).append("--------currentThreadName").toString());
//        QGHttpRequest.getInstance().getSyncTopicHttpRequest(this, localnewtime, locallasttime, localupdatetime, locallisttype, new C17733(this, false));
    }

    private void getSyncFriendsList(String localnewtime, String locallasttime, String localupdatetime) {
        //QGHttpRequest.getInstance().getSyncFriendsListRequest(this, localnewtime, locallasttime, localupdatetime, new C17744(this, false));
    }

    private void registerBroadCast() {
        //this.mBroadcastReceiver = new TutuBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_SYNC_SELF_ALL);
        filter.addAction(Constant.ACTION_SYNC_SELF_FRIENDS);
        filter.addAction(Constant.ACTION_SYNC_SELF_INFO);
        filter.addAction(Constant.ACTION_SYNC_SELF_TOPIC);
        filter.addAction(Constant.UPLOAD_TOPIC_FINISH_ACTION);
        filter.addAction(Constant.DEL_FAV_TOPIC_ACTION);
        filter.addAction(Constant.ADD_FAV_TOPIC_ACTION);
        filter.addAction(Constant.DEL_TOPIC_ACTION);
        filter.addAction(Constant.FOLLOW_TOPIC_ACTION);
        filter.addAction(Constant.ACTION_READ_APPLY);
        filter.addAction(Constant.ACTION_SHARE_TO_SOCIAL);
        filter.addAction(Constant.REPOST_TOPIC_ACTION);
        filter.addAction(Constant.DEL_REPOST_TOPIC_ACTION);
        filter.addAction(Constant.FOLLOW_USER_ACTION);
        filter.addAction(Constant.UNFOLLOW_USER_ACTION);
        //registerReceiver(this.mBroadcastReceiver, filter);
    }
}
