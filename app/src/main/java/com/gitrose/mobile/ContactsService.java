package com.gitrose.mobile;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceActivity;

import com.google.gson.Gson;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.dao.ContactsDao;
import com.gitrose.mobile.dao.UsersDao;
import com.gitrose.mobile.http.QGHttpHandlerAsyn;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.ContactsLocalInfo;
import com.gitrose.mobile.model.ContactsUserInfo;
import com.gitrose.mobile.model.NetContactUserInfo;
import com.gitrose.mobile.model.NetContactsInfo;
import com.gitrose.mobile.utils.ContactsUtils;
//import io.rong.message.BuildConfig;
import java.util.List;
//import org.apache.http.Header;
import org.apache.http.Header;
import org.json.JSONException;
//import shouji.gexing.framework.utils.DebugUtils;
import shouji.gexing.framework.utils.SpUtils;
import shouji.gexing.framework.utils.TimeUtils;

public class ContactsService extends Service {
    private static final int SUB_LENGTH = 500;
    private CommandReceiver cmdReceiver;
    private ContactsDao contactsDao;
    private int count;
    private List<String> jsonList;
    private List<ContactsUserInfo> listContacts;
    private int mEnd;
    private Handler mHandler;
    private int mStart;
    String tutu_id;

    /* renamed from: com.gitrose.mobile.ContactsService.1 */
    class C02641 extends Handler {
        C02641() {
        }

        public void handleMessage(Message msg) {
            ContactsService.this.sendCantactsSublist();
        }
    }

    /* renamed from: com.gitrose.mobile.ContactsService.2 */
    class C02652 implements Runnable {
        C02652() {
        }

        public void run() {
            ContactsService.this.checkUserManager(ContactsService.this, ContactsService.this.tutu_id);
        }
    }

    /* renamed from: com.gitrose.mobile.ContactsService.3 */
    class C02663 implements Runnable {
        private final /* synthetic */ String val$tutu_id;

        C02663(String str) {
            this.val$tutu_id = str;
        }

        public void run() {
            UsersDao ud = new UsersDao(ContactsService.this.getApplicationContext());
            String uploadTime = (String) SpUtils.getFromLocal(ContactsService.this, "upload_time", MyApplication.getInstance().getUserinfo().getUid(), BuildConfig.FLAVOR);
//            DebugUtils.error("debug", "----" + uploadTime);
            if (uploadTime.equals(BuildConfig.FLAVOR)) {
//                DebugUtils.error(this.val$tutu_id + "out");
                try {
                    ContactsService.this.listContacts = ContactsUtils.getContactInfo(ContactsService.this);
                    ContactsService.this.count = ContactsService.this.listContacts.size();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ContactsService.this.mHandler.sendEmptyMessage(0);
                return;
            }
            Long updatetime = Long.valueOf(Long.parseLong(uploadTime));
            Long nowtime = Long.valueOf(TimeUtils.getNow().getTime());
            if (updatetime.longValue() <= 0 || nowtime.longValue() - updatetime.longValue() <= 604800000) {
//                DebugUtils.error(this.val$tutu_id + "has in do extra");
                try {
                    ContactsService.this.listContacts = ContactsUtils.getExtraContactInfo(ContactsService.this);
                    if (ContactsService.this.listContacts == null) {
                        ContactsService.this.count = 0;
                    } else {
                        ContactsService.this.count = ContactsService.this.listContacts.size();
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                ContactsService.this.mHandler.sendEmptyMessage(0);
                return;
            }
//            DebugUtils.error(this.val$tutu_id + "has in do all");
            try {
                ContactsService.this.listContacts = ContactsUtils.getContactInfo(ContactsService.this);
                if (ContactsService.this.listContacts == null) {
                    ContactsService.this.count = 0;
                } else {
                    ContactsService.this.count = ContactsService.this.listContacts.size();
                }
            } catch (JSONException e22) {
                e22.printStackTrace();
            }
            ContactsService.this.mHandler.sendEmptyMessage(0);
        }
    }

    /* renamed from: com.gitrose.mobile.ContactsService.5 */
    class C02675 implements Runnable {
        private final /* synthetic */ NetContactsInfo val$data;

        C02675(NetContactsInfo netContactsInfo) {
            this.val$data = netContactsInfo;
        }

        public void run() {
            ContactsDao cd = new ContactsDao(ContactsService.this);
            if (MyApplication.getInstance().getUserinfo() != null && this.val$data.getDeviceid() != null) {
                for (int i = 0; i < this.val$data.getList().size(); i++) {
                    cd.updateContactsLocalInfo(ContactsService.this.exchangeNet2Local(this.val$data.getDeviceid(), MyApplication.getInstance().getUserinfo().getUid(), (NetContactUserInfo) this.val$data.getList().get(i)));
                }
            }
        }
    }

    private class CommandReceiver extends BroadcastReceiver {
        private CommandReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            //DebugUtils.error("intent------");
            if (intent != null) {
                Bundle bundle = intent.getExtras();
                String devicesId = bundle.getString("devicesId");
                String my_tutu_id = bundle.getString("my_tutu_id");
                String tutuid = bundle.getString("tutuid");
                String relation = bundle.getString("relation");
                ContactsLocalInfo cli = new ContactsLocalInfo();
                cli.setDevicesId(devicesId);
                cli.setMy_tutu_id(my_tutu_id);
                cli.setTutuid(tutuid);
                cli.setRelation(relation);
                ContactsService.this.contactsDao.updateContactsLocalInfo(cli);
                if (ContactsFriendsActivity.getContactsFriendsActivity() != null) {
                    ContactsFriendsActivity.getContactsFriendsActivity().updateView();
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.ContactsService.4 */
    class C07414 extends QGHttpHandlerAsyn<NetContactsInfo> {
        C07414(Context $anonymous0, boolean $anonymous1) {
            super($anonymous0, $anonymous1);
        }

        public void onFinish() {
            super.onFinish();
//            DebugUtils.error("onFinish");
            ContactsService.this.mStart = ContactsService.this.mEnd + 1;
            ContactsService.this.sendCantactsSublist();
        }

        public void onGetDataSuccess(NetContactsInfo data) {
//            DebugUtils.error("onGetDataSuccess:" + data.getList().size());
            if (data != null && data.getList() != null && data.getList().size() > 0) {
                ContactsService.this.updateContactsLocal(data);
            }
        }

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {

        }
    }

    public ContactsService() {
        this.tutu_id = BuildConfig.FLAVOR;
        this.mHandler = new C02641();
    }

    public void onCreate() {
        super.onCreate();
        this.contactsDao = new ContactsDao(this);
        this.cmdReceiver = new CommandReceiver();
//        DebugUtils.error("ContactsService create");
        IntentFilter filter = new IntentFilter();
        filter.addAction("action.update.contacts");
        registerReceiver(this.cmdReceiver, filter);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            this.tutu_id = intent.getExtras().getString("tutu_id");
            new Thread(new C02652()).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendCantactsSublist() {
//        DebugUtils.error("sendCantactsSublist---------------");
        if (this.mStart > this.count - 1) {
            SpUtils.saveToLocal(this, "upload_time", this.tutu_id, String.valueOf(TimeUtils.getNow().getTime()));
            return;
        }
        this.mEnd = this.mStart + SUB_LENGTH;
        if (this.mEnd >= this.count - 1) {
            this.mEnd = this.count;
        }
        Gson gson = new Gson();
        Object subCotacts = this.listContacts.subList(this.mStart, this.mEnd);
//        DebugUtils.error("sendCantactsSublist---------------" + this.mStart + ":" + this.mEnd);
        sendCantactsList(gson.toJson(subCotacts));
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkUserManager(Context context, String tutu_id) {
//        DebugUtils.error("ContactsService checkUserManager");
        new Thread(new C02663(tutu_id)).start();
    }

    private ContactsLocalInfo exchangeNet2Local(String devicesid, String my_tutu_id, NetContactUserInfo ncui) {
        ContactsLocalInfo cli = new ContactsLocalInfo();
        cli.setDevicesId(devicesid);
        cli.setMy_tutu_id(my_tutu_id);
        cli.setLocalid(ncui.getLocal_id());
        cli.setRelation(ncui.getRelation());
        cli.setTutuid(ncui.getTutu_uid());
        cli.setPhonenumber(ncui.getPhonenumber());
        cli.setUpdatetime(String.valueOf(TimeUtils.getNow().getTime()));
        return cli;
    }

    private void sendCantactsList(String json) {
//        DebugUtils.error("ContactsService sendCantactsList:" + ContactsUtils.getUDID(getApplicationContext()));
        QGHttpRequest.getInstance().sendContactsRequest(this, ContactsUtils.getUDID(getApplicationContext()), json, new C07414(getApplicationContext(), false));
    }

    private void updateContactsLocal(NetContactsInfo data) {
        new Thread(new C02675(data)).start();
    }
}
