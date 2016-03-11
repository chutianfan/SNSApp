package com.gitrose.mobile.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
//import com.baidu.location.LocationClientOption;
//import com.tencent.android.tpush.common.MessageKey;
import com.gitrose.mobile.application.MyContext;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.HistoryConversation;
import com.gitrose.mobile.model.HistoryMessage;
import com.gitrose.mobile.model.HistoryStringMessage;
import com.gitrose.mobile.provider.RMprovider.PersonColumns;
//import io.rong.message.BuildConfig;
import java.util.List;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.TimeUtils;

public class RmDao {
    public static final String BasePath = "/data/data/com.gitrose.mobile/files";
    public static String RCT_CONVERSATION;
    public static String RCT_MESSAGE;
    public static String RCT_PATH;
    public static RmDao rm;
    public SQLiteDatabase sdb;

    public static RmDao getInstance() {
        String userid = MyContext.getInstance().getUserId();
        if (userid != null) {
            RCT_PATH = "/data/data/com.gitrose.mobile/files/x4vkb1qpv62gk/" + userid + "/storage";
            rm = new RmDao();
        }
        return rm;
    }

    public RmDao() {
        try {
            this.sdb = SQLiteDatabase.openOrCreateDatabase(RCT_PATH, null);
        } catch (Exception e) {
            Log.e("gaoyong", "RCT_PATH = " + RCT_PATH);
            e.printStackTrace();
        }
    }

    public void insertMessage(List<HistoryMessage> messageList) {
        //Long start = Long.valueOf(TimeUtils.getNow().getTime());
        Long start = Long.valueOf("0");
        this.sdb.beginTransaction();
        if (messageList != null && messageList.size() > 0) {
            for (int i = 0; i < messageList.size(); i++) {
                HistoryMessage hm = (HistoryMessage) messageList.get(i);
                ContentValues values = new ContentValues();
                if (hm.getTarget_id() != null) {
                    values.put("target_id", hm.getTarget_id());
                }
                if (hm.getCategory_id() != null) {
                    values.put("category_id", hm.getCategory_id());
                }
                if (hm.getMessage_direction() != null) {
                    values.put("message_direction", hm.getMessage_direction());
                }
                if (hm.getRead_status() != null) {
                    values.put("read_status", hm.getRead_status());
                }
                if (hm.getReceive_time() != null) {
                    values.put("receive_time", hm.getReceive_time());
                }
                if (hm.getSend_time() != null) {
                    values.put("send_time", hm.getSend_time());
                }
                if (hm.getClazz_name() != null) {
                    values.put("clazz_name", hm.getClazz_name());
                }
                if (hm.getContent() != null) {
                    //values.put(MessageKey.MSG_CONTENT, hm.getContent().toString());
                }
                if (hm.getSend_status() != null) {
                    values.put("send_status", hm.getSend_status());
                }
                if (hm.getSender_id() != null) {
                    values.put("sender_id", hm.getSender_id());
                }
                if (hm.getExtra_content() != null) {
                    values.put("extra_content", hm.getExtra_content().toString());
                }
                if (hm.getExtra_column1() != null) {
                    values.put("extra_column1", hm.getExtra_column1());
                }
                if (hm.getExtra_column2() != null) {
                    values.put("extra_column2", hm.getExtra_column2());
                } else {
                    values.put("extra_column2", Integer.valueOf(0));
                }
                if (hm.getExtra_column3() != null) {
                    values.put("extra_column3", hm.getExtra_column3());
                } else {
                    values.put("extra_column3", Integer.valueOf(0));
                }
                if (hm.getExtra_column4() != null) {
                    values.put("extra_column4", hm.getExtra_column4());
                }
                if (hm.getExtra_column5() != null) {
                    values.put("extra_column5", hm.getExtra_column5());
                }
                if (hm.getExtra_column6() != null) {
                    values.put("extra_column6", hm.getExtra_column6());
                }
                this.sdb.insert(PersonColumns.TABLE_NAME, null, values);
            }
        }
        this.sdb.setTransactionSuccessful();
        this.sdb.endTransaction();
        if (this.sdb.isOpen()) {
            this.sdb.close();
        }
        //DebugUtils.error(messageList.size() + "\u6761\u65f6\u95f4:" + (Long.valueOf(TimeUtils.getNow().getTime()).longValue() - start.longValue()));
    }

    public void insertMessageString(List<HistoryStringMessage> messageList) {
        //Long start = Long.valueOf(TimeUtils.getNow().getTime());
        Long start = Long.valueOf("0");
        this.sdb.beginTransaction();
        if (messageList != null && messageList.size() > 0) {
            for (int i = 0; i < messageList.size(); i++) {
                HistoryStringMessage hm = (HistoryStringMessage) messageList.get(i);
                ContentValues values = new ContentValues();
                if (hm.getTarget_id() != null) {
                    values.put("target_id", hm.getTarget_id());
                }
                if (hm.getCategory_id() != null) {
                    values.put("category_id", hm.getCategory_id());
                }
                if (hm.getMessage_direction() != null) {
                    values.put("message_direction", hm.getMessage_direction());
                }
                if (hm.getRead_status() != null) {
                    values.put("read_status", hm.getRead_status());
                }
                if (hm.getReceive_time() != null) {
                    values.put("receive_time", hm.getReceive_time());
                }
                if (hm.getSend_time() != null) {
                    values.put("send_time", hm.getSend_time());
                }
                if (hm.getClazz_name() != null) {
                    values.put("clazz_name", hm.getClazz_name());
                }
                if (hm.getContent() != null) {
                    //values.put(MessageKey.MSG_CONTENT, hm.getContent());
                }
                if (hm.getSend_status() != null) {
                    values.put("send_status", hm.getSend_status());
                }
                if (hm.getSender_id() != null) {
                    values.put("sender_id", hm.getSender_id());
                }
                if (hm.getExtra_content() != null) {
                    values.put("extra_content", hm.getExtra_content().toString());
                }
                if (hm.getExtra_column1() != null) {
                    values.put("extra_column1", hm.getExtra_column1());
                }
                if (hm.getExtra_column2() != null) {
                    values.put("extra_column2", hm.getExtra_column2());
                } else {
                    values.put("extra_column2", Integer.valueOf(0));
                }
                if (hm.getExtra_column3() != null) {
                    values.put("extra_column3", hm.getExtra_column3());
                } else {
                    values.put("extra_column3", Integer.valueOf(0));
                }
                if (hm.getExtra_column4() != null) {
                    values.put("extra_column4", hm.getExtra_column4());
                }
                if (hm.getExtra_column5() != null) {
                    values.put("extra_column5", hm.getExtra_column5());
                }
                if (hm.getExtra_column6() != null) {
                    values.put("extra_column6", hm.getExtra_column6());
                }
                this.sdb.insert(PersonColumns.TABLE_NAME, null, values);
            }
        }
        this.sdb.setTransactionSuccessful();
        this.sdb.endTransaction();
        if (this.sdb.isOpen()) {
            this.sdb.close();
        }
        //DebugUtils.error(messageList.size() + "\u6761\u65f6\u95f4:" + (Long.valueOf(TimeUtils.getNow().getTime()).longValue() - start.longValue()));
    }

    public void insertConversation(List<HistoryConversation> conversationList) {
        //Long start = Long.valueOf(TimeUtils.getNow().getTime());
        Long start = Long.valueOf("0");
        this.sdb.beginTransaction();
        if (conversationList != null && conversationList.size() > 0) {
            for (int i = 0; i < conversationList.size(); i++) {
                HistoryConversation hc = (HistoryConversation) conversationList.get(i);
                ContentValues values = new ContentValues();
                if (hc.getTarget_id() != null) {
                    values.put("target_id", hc.getTarget_id());
                }
                if (hc.getCategory_id() != null) {
                    values.put("category_id", hc.getCategory_id());
                }
                if (hc.getConversation_title() != null) {
                    values.put("conversation_title", hc.getConversation_title());
                }
                if (hc.getDraft_message() != null) {
                    values.put("draft_message", hc.getDraft_message());
                }
                if (hc.getIs_top() != null) {
                    values.put("is_top", hc.getIs_top());
                }
                if (hc.getLast_time() != null) {
                    values.put("last_time", hc.getLast_time());
                }
                if (hc.getTop_time() != null) {
                    values.put("top_time", hc.getTop_time());
                }
                if (hc.getExtra_column1() != null) {
                    values.put("extra_column1", hc.getExtra_column1());
                }
                if (hc.getExtra_column2() != null) {
                    values.put("extra_column2", hc.getExtra_column2());
                } else {
                    values.put("extra_column2", Integer.valueOf(0));
                }
                if (hc.getExtra_column3() != null) {
                    values.put("extra_column3", hc.getExtra_column3());
                } else {
                    values.put("extra_column3", Integer.valueOf(0));
                }
                if (hc.getExtra_column4() != null) {
                    values.put("extra_column4", hc.getExtra_column4());
                }
                if (hc.getExtra_column5() != null) {
                    values.put("extra_column5", hc.getExtra_column5());
                }
                if (hc.getExtra_column6() != null) {
                    values.put("extra_column6", hc.getExtra_column6());
                }
                this.sdb.insert("RCT_CONVERSATION", null, values);
            }
        }
        this.sdb.setTransactionSuccessful();
        this.sdb.endTransaction();
        if (this.sdb.isOpen()) {
            this.sdb.close();
        }
        //DebugUtils.error(conversationList.size() + "\u6761\u4f1a\u8bdd\u65f6\u95f4:" + (Long.valueOf(TimeUtils.getNow().getTime()).longValue() - start.longValue()));
    }

    public void insertConversation() {
        //Long start = Long.valueOf(TimeUtils.getNow().getTime());
        Long start = Long.valueOf("0");
        this.sdb.beginTransaction();
//        for (int i = 0; i < LocationClientOption.MIN_SCAN_SPAN; i++) {
//            ContentValues values = new ContentValues();
//            values.put("target_id", Integer.valueOf(i * 13));
//            values.put("category_id", Constant.SYSTEM_UID);
//            values.put("conversation_title", "10057");
//            values.put("draft_message", BuildConfig.FLAVOR);
//            values.put("is_top", "0");
//            values.put("last_time", "1234865");
//            values.put("top_time", "155464");
//            values.put("extra_column1", "0");
//            values.put("extra_column2", "0");
//            values.put("extra_column3", "0");
//            values.put("extra_column4", BuildConfig.FLAVOR);
//            values.put("extra_column5", BuildConfig.FLAVOR);
//            values.put("extra_column6", BuildConfig.FLAVOR);
//            this.sdb.insert("RCT_CONVERSATION", null, values);
//        }
        this.sdb.setTransactionSuccessful();
        this.sdb.endTransaction();
        if (this.sdb.isOpen()) {
            this.sdb.close();
        }
        //DebugUtils.error(LocationClientOption.MIN_SCAN_SPAN + "\u6761\u4f1a\u8bdd\u65f6\u95f4:" + (Long.valueOf(TimeUtils.getNow().getTime()).longValue() - start.longValue()));
    }

    public void insertMessage() {
//        Long start = Long.valueOf(TimeUtils.getNow().getTime());
//        this.sdb.beginTransaction();
//        for (int i = 0; i < LocationClientOption.MIN_SCAN_SPAN; i++) {
//            ContentValues values = new ContentValues();
//            values.put("target_id", "10057");
//            values.put("category_id", Constant.SYSTEM_UID);
//            values.put("message_direction", Constant.SYSTEM_UID);
//            values.put("read_status", "0");
//            values.put("receive_time", "161611616");
//            values.put("send_time", "1561611");
//            values.put("clazz_name", "10056");
//            values.put(MessageKey.MSG_CONTENT, "youxi");
//            values.put("send_status", "0");
//            values.put("sender_id", "10056");
//            values.put("extra_content", "hehehehehh");
//            values.put("extra_column1", "1111");
//            values.put("extra_column2", BuildConfig.FLAVOR);
//            values.put("extra_column3", Constant.SYSTEM_UID);
//            values.put("extra_column4", "10056");
//            values.put("extra_column5", BuildConfig.FLAVOR);
//            values.put("extra_column6", BuildConfig.FLAVOR);
//            this.sdb.insert(PersonColumns.TABLE_NAME, null, values);
//        }
        this.sdb.setTransactionSuccessful();
        this.sdb.endTransaction();
        if (this.sdb.isOpen()) {
            this.sdb.close();
        }
//        DebugUtils.error(LocationClientOption.MIN_SCAN_SPAN + "\u6761\u65f6\u95f4:" + (Long.valueOf(TimeUtils.getNow().getTime()).longValue() - start.longValue()));
    }
}
