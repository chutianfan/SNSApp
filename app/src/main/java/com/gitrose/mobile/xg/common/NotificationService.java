package com.gitrose.mobile.xg.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import com.tencent.android.tpush.common.Constants;
//import com.tencent.android.tpush.common.MessageKey;
//import com.tencent.connect.share.QzoneShare;
//import com.tencent.open.SocialConstants;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.xg.po.XGNotification;
import com.umeng.socialize.common.SocializeConstants;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private static NotificationService instance;
    private DBOpenHelper dbOpenHelper;

    static {
        instance = null;
    }

    public NotificationService(Context context) {
        this.dbOpenHelper = new DBOpenHelper(context);
    }

    public static synchronized NotificationService getInstance(Context ctx) {
        NotificationService notificationService;
        synchronized (NotificationService.class) {
            if (instance == null) {
                instance = new NotificationService(ctx);
            }
            notificationService = instance;
        }
        return notificationService;
    }

    public void save(XGNotification notification) {
        SQLiteDatabase db = this.dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("msg_id", notification.getMsg_id());
//        values.put(QzoneShare.SHARE_TO_QQ_TITLE, notification.getTitle());
//        values.put(MessageKey.MSG_CONTENT, notification.getContent());
//        values.put(Constants.FLAG_ACTIVITY_NAME, notification.getActivity());
//        values.put(SocialConstants.PARAM_URL, notification.getUrl());
        values.put("update_time", notification.getUpdate_time());
        db.insert("notification", null, values);
    }

    public void delete(Integer id) {
        this.dbOpenHelper.getWritableDatabase().delete("notification", "id=?", new String[]{id.toString()});
    }

    public void deleteAll() {
        //this.dbOpenHelper.getWritableDatabase().delete("notification", BuildConfig.FLAVOR, null);
    }

    public void update(XGNotification notification) {
        SQLiteDatabase db = this.dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("msg_id", notification.getMsg_id());
//        values.put(QzoneShare.SHARE_TO_QQ_TITLE, notification.getTitle());
//        values.put(MessageKey.MSG_CONTENT, notification.getContent());
//        values.put(Constants.FLAG_ACTIVITY_NAME, notification.getActivity());
//        values.put(SocialConstants.PARAM_URL, notification.getUrl());
        values.put("update_time", notification.getUpdate_time());
        db.update("notification", values, "id=?", new String[]{notification.getId().toString()});
    }

    public XGNotification find(Integer id) {
        Cursor cursor = this.dbOpenHelper.getReadableDatabase().query("notification", new String[]{"id,msg_id,title,content,activity,url,update_time"}, "id=?", new String[]{id.toString()}, null, null, null, Constant.SYSTEM_UID);
        try {
            if (cursor.moveToFirst()) {
//                XGNotification xGNotification = new XGNotification(Integer.valueOf(cursor.getInt(cursor.getColumnIndex(SocializeConstants.WEIBO_ID))), Long.valueOf(cursor.getLong(cursor.getColumnIndex("msg_id"))), cursor.getString(cursor.getColumnIndex(QzoneShare.SHARE_TO_QQ_TITLE)), cursor.getString(cursor.getColumnIndex(MessageKey.MSG_CONTENT)), cursor.getString(cursor.getColumnIndex(Constants.FLAG_ACTIVITY_NAME)), cursor.getString(cursor.getColumnIndex(SocialConstants.PARAM_URL)), cursor.getString(cursor.getColumnIndex("update_time")));
//                return xGNotification;
            }
            cursor.close();
            return null;
        } finally {
            cursor.close();
        }
    }

    public List<XGNotification> getScrollData(int currentPage, int lineSize, String msg_id) {
        List<XGNotification> notifications;
        String firstResult = String.valueOf((currentPage - 1) * lineSize);
        SQLiteDatabase db = this.dbOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        if (msg_id != null) {
            try {
//                if (!BuildConfig.FLAVOR.equals(msg_id)) {
//                    cursor = db.query("notification", new String[]{"id,msg_id,title,content,activity,url,update_time"}, "msg_id like ?", new String[]{new StringBuilder(String.valueOf(msg_id)).append("%").toString()}, null, null, "update_time DESC", new StringBuilder(String.valueOf(firstResult)).append(",").append(lineSize).toString());
//                    notifications = new ArrayList();
//                    while (cursor.moveToNext()) {
//                        //notifications.add(new XGNotification(Integer.valueOf(cursor.getInt(cursor.getColumnIndex(SocializeConstants.WEIBO_ID))), Long.valueOf(cursor.getLong(cursor.getColumnIndex("msg_id"))), cursor.getString(cursor.getColumnIndex(QzoneShare.SHARE_TO_QQ_TITLE)), cursor.getString(cursor.getColumnIndex(MessageKey.MSG_CONTENT)), cursor.getString(cursor.getColumnIndex(Constants.FLAG_ACTIVITY_NAME)), cursor.getString(cursor.getColumnIndex(SocialConstants.PARAM_URL)), cursor.getString(cursor.getColumnIndex("update_time"))));
//                    }
//                    cursor.close();
//                    return notifications;
//                }
            } catch (Throwable th) {
                cursor.close();
            }
        }
        cursor = db.query("notification", new String[]{"id,msg_id,title,content,activity,url,update_time"}, null, null, null, null, "update_time DESC", new StringBuilder(String.valueOf(firstResult)).append(",").append(lineSize).toString());
        notifications = new ArrayList();
        while (cursor.moveToNext()) {
            //notifications.add(new XGNotification(Integer.valueOf(cursor.getInt(cursor.getColumnIndex(SocializeConstants.WEIBO_ID))), Long.valueOf(cursor.getLong(cursor.getColumnIndex("msg_id"))), cursor.getString(cursor.getColumnIndex(QzoneShare.SHARE_TO_QQ_TITLE)), cursor.getString(cursor.getColumnIndex(MessageKey.MSG_CONTENT)), cursor.getString(cursor.getColumnIndex(Constants.FLAG_ACTIVITY_NAME)), cursor.getString(cursor.getColumnIndex(SocialConstants.PARAM_URL)), cursor.getString(cursor.getColumnIndex("update_time"))));
        }
        cursor.close();
        return notifications;
    }

    public int getCount() {
        Cursor cursor = this.dbOpenHelper.getReadableDatabase().rawQuery("select count(*) from notification", null);
        try {
            cursor.moveToFirst();
            int i = cursor.getInt(0);
            return i;
        } finally {
            cursor.close();
        }
    }
}
