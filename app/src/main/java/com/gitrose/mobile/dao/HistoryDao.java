package com.gitrose.mobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import com.google.gson.Gson;
//import com.tencent.android.tpush.common.MessageKey;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.application.MyContext;
import com.gitrose.mobile.model.HistoryContent;
import com.gitrose.mobile.model.HistoryConversation;
import com.gitrose.mobile.model.HistoryExtraContent;
import com.gitrose.mobile.model.HistoryMessage;
import com.gitrose.mobile.model.HistoryStringMessage;
import com.gitrose.mobile.model.UserInfo;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
import java.util.List;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.FileUtils;

public class HistoryDao {
    public static final String BasePath = "/data/data/com.gitrose.mobile/files";
    public static String HD_CONVERSATION;
    public static String HD_MESSAGE;
    public static String HD_PATH;
    public static HistoryDao hd;
    public SQLiteDatabase sdb;

    public static HistoryDao getInstance(Context context) {
        String userid = MyContext.getInstance().getUserId();
        if (userid != null) {
            //HD_PATH = FileUtils.getSavePath(context, 10485760) + "/private_data" + userid + ".db";
            hd = new HistoryDao();
        }
        return hd;
    }

    public String getHDPath(Context context) {
        TutuUsers user = MyApplication.getInstance().getUserinfo();
        if (user != null) {
//            HD_PATH = FileUtils.getSavePath(context, 10485760) + "/private_data" + user.getUid() + ".db";
//            DebugUtils.error("lixiangei", HD_PATH);
        }
        return HD_PATH;
    }

    public HistoryDao() {
        try {
            this.sdb = SQLiteDatabase.openOrCreateDatabase(HD_PATH, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<HistoryStringMessage> getHistoryMessageString() {
        List<HistoryStringMessage> messageList = new ArrayList();
        HistoryStringMessage message = new HistoryStringMessage();
        Cursor cursor = this.sdb.rawQuery("select * from RCT_MESSAGE", null);
        while (cursor != null && cursor.moveToNext()) {
            messageList.add(parseHMessageString(cursor));
        }
        if (!(cursor == null || cursor.isClosed())) {
            cursor.close();
        }
        return messageList;
    }

    public List<HistoryMessage> getHistoryMessage() {
        List<HistoryMessage> messageList = new ArrayList();
        HistoryMessage message = new HistoryMessage();
        Cursor cursor = this.sdb.rawQuery("select * from RCT_MESSAGE", null);
        while (cursor != null && cursor.moveToNext()) {
            messageList.add(parseHMessage(cursor));
        }
        if (!(cursor == null || cursor.isClosed())) {
            cursor.close();
        }
        return messageList;
    }

    public List<HistoryConversation> getHistoryConversation() {
        List<HistoryConversation> conversationList = new ArrayList();
        HistoryConversation conversation = new HistoryConversation();
        Cursor cursor = this.sdb.rawQuery("select * from RCT_CONVERSATION", null);
        while (cursor != null && cursor.moveToNext()) {
            conversationList.add(parseHConversation(cursor));
        }
        if (!(cursor == null || cursor.isClosed())) {
            cursor.close();
        }
        return conversationList;
    }

    public HistoryConversation parseHConversation(Cursor cursor) {
        HistoryConversation hc = new HistoryConversation();
        hc.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
        hc.setTarget_id(cursor.getString(cursor.getColumnIndex("target_id")));
        hc.setConversation_title(cursor.getString(cursor.getColumnIndex("conversation_title")));
        hc.setDraft_message(cursor.getString(cursor.getColumnIndex("draft_message")));
        hc.setIs_top(cursor.getString(cursor.getColumnIndex("is_top")));
        hc.setLast_time(cursor.getString(cursor.getColumnIndex("last_time")));
        hc.setTop_time(cursor.getString(cursor.getColumnIndex("top_time")));
        hc.setExtra_column1(cursor.getString(cursor.getColumnIndex("extra_column1")));
        hc.setExtra_column2(cursor.getString(cursor.getColumnIndex("extra_column2")));
        hc.setExtra_column3(cursor.getString(cursor.getColumnIndex("extra_column3")));
        hc.setExtra_column4(cursor.getString(cursor.getColumnIndex("extra_column4")));
        hc.setExtra_column5(cursor.getString(cursor.getColumnIndex("extra_column5")));
        hc.setExtra_column6(cursor.getString(cursor.getColumnIndex("extra_column6")));
        return hc;
    }

    public HistoryMessage parseHMessage(Cursor cursor) {
        HistoryMessage hm = new HistoryMessage();
        HistoryContent content = new HistoryContent();
        hm.setTarget_id(cursor.getString(cursor.getColumnIndex("target_id")));
        hm.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
        hm.setMessage_direction(cursor.getString(cursor.getColumnIndex("message_direction")));
        hm.setRead_status(cursor.getString(cursor.getColumnIndex("read_status")));
        hm.setReceive_time(cursor.getString(cursor.getColumnIndex("receive_time")));
        hm.setSend_time(cursor.getString(cursor.getColumnIndex("send_time")));
        hm.setClazz_name(cursor.getString(cursor.getColumnIndex("clazz_name")));
//        String content_string = cursor.getString(cursor.getColumnIndex(MessageKey.MSG_CONTENT));
//        DebugUtils.error("debug", "content_string:" + content_string);
//        hm.setContent((HistoryContent) new Gson().fromJson(content_string, HistoryContent.class));
        hm.setSend_status(cursor.getString(cursor.getColumnIndex("send_status")));
        hm.setSender_id(cursor.getString(cursor.getColumnIndex("sender_id")));
        hm.setExtra_content(null);
        hm.setExtra_column1(cursor.getString(cursor.getColumnIndex("extra_column1")));
        hm.setExtra_column2(cursor.getString(cursor.getColumnIndex("extra_column2")));
        hm.setExtra_column3(cursor.getString(cursor.getColumnIndex("extra_column3")));
        hm.setExtra_column4(cursor.getString(cursor.getColumnIndex("extra_column4")));
        hm.setExtra_column5(cursor.getString(cursor.getColumnIndex("extra_column5")));
        hm.setExtra_column6(cursor.getString(cursor.getColumnIndex("extra_column6")));
        return hm;
    }

    public HistoryStringMessage parseHMessageString(Cursor cursor) {
        HistoryStringMessage hm = new HistoryStringMessage();
        hm.setTarget_id(cursor.getString(cursor.getColumnIndex("target_id")));
        hm.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
        hm.setMessage_direction(cursor.getString(cursor.getColumnIndex("message_direction")));
        hm.setRead_status(cursor.getString(cursor.getColumnIndex("read_status")));
        hm.setReceive_time(cursor.getString(cursor.getColumnIndex("receive_time")));
        hm.setSend_time(cursor.getString(cursor.getColumnIndex("send_time")));
        hm.setClazz_name(cursor.getString(cursor.getColumnIndex("clazz_name")));
        //hm.setContent(cursor.getString(cursor.getColumnIndex(MessageKey.MSG_CONTENT)));
        hm.setSend_status(cursor.getString(cursor.getColumnIndex("send_status")));
        hm.setSender_id(cursor.getString(cursor.getColumnIndex("sender_id")));
        hm.setExtra_content(null);
        hm.setExtra_column1(cursor.getString(cursor.getColumnIndex("extra_column1")));
        hm.setExtra_column2(cursor.getString(cursor.getColumnIndex("extra_column2")));
        hm.setExtra_column3(cursor.getString(cursor.getColumnIndex("extra_column3")));
        hm.setExtra_column4(cursor.getString(cursor.getColumnIndex("extra_column4")));
        hm.setExtra_column5(cursor.getString(cursor.getColumnIndex("extra_column5")));
        hm.setExtra_column6(cursor.getString(cursor.getColumnIndex("extra_column6")));
        return hm;
    }

    public HistoryExtraContent exchangeExtra(String content) {
        //DebugUtils.error("debug", "content:" + content);
        HistoryExtraContent hec = new HistoryExtraContent();
        String[] extra_string = content.split("####");
//        DebugUtils.error("debug", "extra_string:" + extra_string.length);
//        if (extra_string.length > 1) {
//            hec.setButtonlink(extra_string[2].replace("buttonlink=", BuildConfig.FLAVOR));
//            hec.setButtonText(extra_string[1].replace("buttonText=", BuildConfig.FLAVOR));
//            hec.setContentLink(extra_string[0].replace("contentLink=", BuildConfig.FLAVOR));
//        } else {
//            hec.setButtonlink(BuildConfig.FLAVOR);
//            hec.setButtonText(BuildConfig.FLAVOR);
//            hec.setContentLink(BuildConfig.FLAVOR);
//        }
        return hec;
    }
}
