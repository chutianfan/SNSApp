package com.gitrose.mobile.xg.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
//import com.tencent.android.tpush.XGPushBaseReceiver;
//import com.tencent.android.tpush.XGPushClickedResult;
//import com.tencent.android.tpush.XGPushRegisterResult;
//import com.tencent.android.tpush.XGPushShowedResult;
//import com.tencent.android.tpush.XGPushTextMessage;
//import com.tencent.android.tpush.common.MessageKey;
import com.gitrose.mobile.C0306R;
import com.gitrose.mobile.FriendsActivity;
//import com.gitrose.mobile.HomeActivity;
import com.gitrose.mobile.NewsListActivty;
//import com.gitrose.mobile.PersonalActivity;
//import com.gitrose.mobile.RCChatActivity;
import com.gitrose.mobile.TopicDetailActivity;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.WebViewActivity;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.utils.PlaySound;
//import io.rong.message.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;
//import shouji.gexing.framework.utils.ContextUtils;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.GetPopPackageName;

//public class MessageReceiver extends XGPushBaseReceiver {
//    public static final String LogTag = "TPushReceiver";
//    private Intent intent;
//
//    public MessageReceiver() {
//        this.intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
//    }
//
//    private void show(Context context, String text) {
//    }
//
//    public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedRlt) {
//        DebugUtils.error(notifiShowedRlt.getTitle() + "onNotifactionShowedResult----");
//    }
//
//    public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
//        DebugUtils.error(message.getTitle() + "onNotifactionClickedResult");
//    }
//
//    public void onUnregisterResult(Context context, int errorCode) {
//        if (context != null) {
//            String text;
//            if (errorCode == 0) {
//                text = "\u53cd\u6ce8\u518c\u6210\u529f";
//            } else {
//                text = "\u53cd\u6ce8\u518c\u5931\u8d25" + errorCode;
//            }
//            show(context, text);
//        }
//    }
//
//    public void onSetTagResult(Context context, int errorCode, String tagName) {
//        if (context != null) {
//            String text;
//            if (errorCode == 0) {
//                text = "\"" + tagName + "\"\u8bbe\u7f6e\u6210\u529f";
//            } else {
//                text = "\"" + tagName + "\"\u8bbe\u7f6e\u5931\u8d25,\u9519\u8bef\u7801\uff1a" + errorCode;
//            }
//            show(context, text);
//        }
//    }
//
//    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
//        if (context != null) {
//            String text;
//            if (errorCode == 0) {
//                text = "\"" + tagName + "\"\u5220\u9664\u6210\u529f";
//            } else {
//                text = "\"" + tagName + "\"\u5220\u9664\u5931\u8d25,\u9519\u8bef\u7801\uff1a" + errorCode;
//            }
//            show(context, text);
//        }
//    }
//
//    public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult message) {
//        if (context != null && message != null) {
//            String text;
//            if (errorCode == 0) {
//                text = message + "\u6ce8\u518c\u6210\u529f";
//                message.getToken();
//            } else {
//                text = message + "\u6ce8\u518c\u5931\u8d25\uff0c\u9519\u8bef\u7801\uff1a" + errorCode;
//            }
//            show(context, text);
//        }
//    }
//
//    public void onTextMessage(Context context, XGPushTextMessage message) {
//        DebugUtils.error("\u6536\u5230\u6d88\u606f\uff1d\uff1d\uff1d\uff1d\uff1d\uff1d\uff1d\uff1d\uff1d\uff1d\uff1d\uff1d\uff1d:" + message.toString());
//        try {
//            String customContent = message.getCustomContent();
//            String popActivityName = GetPopPackageName.getTopActiivtyName(context);
//            String localPackagename = ContextUtils.getPackageName(context);
//            if (customContent != null && customContent.length() != 0) {
//                JSONObject jsonObject = new JSONObject(customContent);
//                String type = jsonObject.getString(MessageKey.MSG_ACTION);
//                int private_nums = jsonObject.getInt("msgcount");
//                int tips_nums = jsonObject.getInt("tipcount");
//                if (type.equals(Constant.XG_TYPE_ZAN)) {
//                    sendNotification(context, message);
//                    showPoint(0, private_nums, tips_nums);
//                } else if (type.equals(Constant.XG_TYPE_COMMENT)) {
//                    sendNotification(context, message);
//                    showPoint(0, private_nums, tips_nums);
//                } else if (type.equals(Constant.XG_TYPE_ADD_FRIENDS)) {
//                    if (popActivityName.equals(new StringBuilder(String.valueOf(localPackagename)).append(".FriendsActivity").toString())) {
//                        if (FriendsActivity.getObject() != null) {
//                            FriendsActivity.getObject().needToRefresh();
//                        }
//                        sendNotification(context, message);
//                    } else {
//                        sendNotification(context, message);
//                    }
//                    showPoint(0, private_nums, tips_nums);
//                } else if (type.equals(Constant.XG_TYPE_PRIVAT_CHAT)) {
//                    if (!popActivityName.equals(new StringBuilder(String.valueOf(localPackagename)).append(".RCChatActivity").toString())) {
//                        sendNotification(context, message);
//                    }
//                    showPoint(1, private_nums, tips_nums);
//                } else if (type.equals(Constant.XG_TYPE_SYS_TOPIC_DETAIL)) {
//                    sendNotification(context, message);
//                } else if (type.equals(Constant.XG_TYPE_SYS_HTTP)) {
//                    sendNotification(context, message);
//                } else if (type.equals(Constant.XG_TYPE_BLOCK)) {
//                    block_me_uid = jsonObject.getString("actionuid");
//                    if (RCChatActivity.getObject() != null) {
//                        RCChatActivity.getObject().refreshBlock(true, block_me_uid);
//                    }
//                } else if (type.equals(Constant.XG_TYPE_UNBLOCK)) {
//                    block_me_uid = jsonObject.getString("actionuid");
//                    if (RCChatActivity.getObject() != null) {
//                        RCChatActivity.getObject().refreshBlock(false, block_me_uid);
//                    }
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void showPoint(int tag, int private_nums, int tips_num) {
//        if (PersonalActivity.getObject() != null) {
//            if (tag == 0) {
//                PersonalActivity.getObject().showNewsPoint();
//            } else if (tag == 1) {
//                PersonalActivity.getObject().showChatsPoint();
//            }
//        }
//        if (tag == 0 && NewsListActivty.getObject() != null) {
//            NewsListActivty.getObject().torefesh();
//        }
//        if (HomeActivity.getIntance() != null) {
//            HomeActivity.getIntance().showPoint();
//        }
//        MyApplication.getInstance().setNewsNum(tips_num);
//    }
//
//    private void sendNotification(Context context, XGPushTextMessage message) {
//        try {
//            NotificationManager nm = (NotificationManager) context.getSystemService("notification");
//            String title = message.getTitle();
//            String content = message.getContent();
//            JSONObject jsonObject = new JSONObject(message.getCustomContent());
//            String type = jsonObject.getString(MessageKey.MSG_ACTION);
//            String routeid = jsonObject.getString("routeid");
//            String actionuid = jsonObject.getString("actionuid");
//            String actionid = jsonObject.getString("actionid");
//            String tipid = BuildConfig.FLAVOR;
//            if (jsonObject.has("tipid")) {
//                tipid = jsonObject.getString("tipid");
//            }
//            Notification notification = new Notification();
//            notification.when = System.currentTimeMillis();
//            notification.icon = C0306R.drawable.ic_launcher;
//            notification.flags = 16;
//            if (context.getSharedPreferences(Constant.PLAY_SOUND_PERFERENCE, 0).getBoolean(PlaySound.SOUND_OPEN, true)) {
//                notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + C0306R.raw.push);
//            }
//            Intent nIntent = new Intent();
//            if (tipid != null) {
//                if (!tipid.equals(BuildConfig.FLAVOR)) {
//                    nIntent.putExtra("tipid", tipid);
//                }
//            }
//            nIntent.putExtra("fromNotifi", true);
//            if (type.equals(Constant.XG_TYPE_ZAN)) {
//                nIntent.setClass(context, TopicDetailActivity.class);
//                nIntent.putExtra(TopicDetailActivity.TOPIC_ID, routeid);
//            } else if (type.equals(Constant.XG_TYPE_COMMENT)) {
//                nIntent.setClass(context, TopicDetailActivity.class);
//                nIntent.putExtra(TopicDetailActivity.TOPIC_ID, routeid);
//                nIntent.putExtra(TopicDetailPagerActivity.COMMENT_ID, actionid);
//            } else if (type.equals(Constant.XG_TYPE_ADD_FRIENDS)) {
//                nIntent.setClass(context, PersonalActivity.class);
//                nIntent.putExtra(PersonalActivity.PERSONAL_USER_ID, actionuid);
//            } else if (type.equals(Constant.XG_TYPE_PRIVAT_CHAT)) {
//                String name = content.split(" \u7ed9\u4f60")[0];
//                String avatartime = jsonObject.getString("at");
//                nIntent.setClass(context, RCChatActivity.class);
//                nIntent.putExtra("touid", actionuid);
//                nIntent.putExtra("nickname", name);
//                nIntent.putExtra("avatartime", avatartime);
//            } else if (type.equals(Constant.XG_TYPE_SYS_TOPIC_DETAIL)) {
//                nIntent.setClass(context, TopicDetailActivity.class);
//                nIntent.putExtra(TopicDetailActivity.TOPIC_ID, routeid);
//            } else if (type.equals(Constant.XG_TYPE_SYS_HTTP)) {
//                nIntent.setClass(context, WebViewActivity.class);
//                nIntent.putExtra("web_url", routeid);
//            }
//            nIntent.addFlags(67108864);
//            nIntent.addFlags(268435456);
//            notification.setLatestEventInfo(context, title, content, PendingIntent.getActivity(context, 0, nIntent, 1207959552));
//            int notify_id = -1;
//            if (type.equals(Constant.XG_TYPE_ZAN)) {
//                notify_id = 0;
//            } else if (type.equals(Constant.XG_TYPE_COMMENT)) {
//                notify_id = 1;
//            } else if (type.equals(Constant.XG_TYPE_ADD_FRIENDS)) {
//                notify_id = 2;
//            } else if (type.equals(Constant.XG_TYPE_PRIVAT_CHAT)) {
//                notify_id = 3;
//            }
//            nm.notify(notify_id, notification);
//        } catch (Exception e) {
//        }
//    }
//}
