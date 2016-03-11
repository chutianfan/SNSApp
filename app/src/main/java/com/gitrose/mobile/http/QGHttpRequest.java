package com.gitrose.mobile.http;

import android.content.Context;

import com.gitrose.mobile.model.UserInfoList;
import com.loopj.android.http.RequestParams;
//import com.tencent.android.tpush.common.MessageKey;
//import com.tencent.connect.share.QzoneShare;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.LoginActivity;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.avsdk.control.Util;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.BlockUserList;
import com.gitrose.mobile.model.CheckImport;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.model.CommentBox;
import com.gitrose.mobile.model.CommentEmotion;
import com.gitrose.mobile.model.CommentList;
import com.gitrose.mobile.model.FriendsList;
import com.gitrose.mobile.model.HotCommentList;
import com.gitrose.mobile.model.MesageConversationModel;
import com.gitrose.mobile.model.MessageNum;
import com.gitrose.mobile.model.NetContactsInfo;
import com.gitrose.mobile.model.NewCommentList;
import com.gitrose.mobile.model.Praise;
import com.gitrose.mobile.model.PrivateMessageList;
import com.gitrose.mobile.model.PublishLiveModel;
import com.gitrose.mobile.model.RecommendModel;
import com.gitrose.mobile.model.SessionList;
import com.gitrose.mobile.model.SquareHuati;
import com.gitrose.mobile.model.TipsList;
import com.gitrose.mobile.model.TokenModle;
import com.gitrose.mobile.model.TopicInfoList;
import com.gitrose.mobile.model.TopicReciveRedPacketModel;
import com.gitrose.mobile.model.TopicZanModel;
import com.gitrose.mobile.model.UserInfo;
//import com.umeng.socialize.net.utils.SocializeProtocolConstants;
//import io.rong.message.BuildConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import qalsdk.BaseConstants;

public class QGHttpRequest {
    private static QGHttpRequest mInstance;

    public static QGHttpRequest getInstance() {
        if (mInstance == null) {
            mInstance = new QGHttpRequest();
        }
        return mInstance;
    }

    public void getHomeListRequest(Context context, String startTopicId, String endTopicId, String len, String direction, String page, QGHttpHandler<TopicInfoList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("page", page);
        params.put("direction", direction);
        params.put("len", len);
        if (MyApplication.getInstance().getUserinfo() != null) {
            params.put("visituid", MyApplication.getInstance().getUserinfo().getUid());
        }
        params.put("endtopicid", endTopicId);
        params.put("starttopicid", startTopicId);
        QGClient.getInstance().get(context, ConstantURL.HOME_LIST, params, handler, true);
    }

    public void getLikeTopicRequest(Context context, String topicId, QGHttpHandler<Praise> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("topicid", topicId);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.LIKE_TOPIC, params, handler);
    }

    public void getDislikeTopicRequest(Context context, String topicId, QGHttpHandler<Praise> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("topicid", topicId);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.DISLIKE_TOPIC, params, handler);
    }

    public void setUserInfo(Context context, String nickName, String gender, String birthday, String province, String city, String area, String sign, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
//        if (!(nickName == null || BuildConfig.FLAVOR.equals(nickName))) {
//            params.put("nickname", nickName);
//        }
//        if (!(gender == null || BuildConfig.FLAVOR.equals(gender))) {
//            params.put(SocializeProtocolConstants.PROTOCOL_KEY_GENDER, gender);
//        }
//        if (!(birthday == null || BuildConfig.FLAVOR.equals(birthday))) {
//            params.put(SocializeProtocolConstants.PROTOCOL_KEY_BIRTHDAY, birthday);
//        }
//        if (!(province == null || BuildConfig.FLAVOR.equals(province))) {
//            params.put("province", province);
//        }
//        if (!(city == null || BuildConfig.FLAVOR.equals(city))) {
//            params.put("city", city);
//        }
//        if (!(area == null || BuildConfig.FLAVOR.equals(area))) {
//            params.put("area", area);
//        }
//        if (!(sign == null || BuildConfig.FLAVOR.equals(sign))) {
//            params.put("sign", sign);
//        }
//        params.put("visituid", MyApplication.getInstance().login_uid);
//        QGClient.getInstance().get(context, ConstantURL.SET_USER_INFO, params, handler);
    }

    public void getUserInfoRequest(Context context, String uid, QGHttpHandler<UserInfo> handler) {
//        HashMap<String, String> params = new HashMap();
//        params.put(SocializeProtocolConstants.PROTOCOL_KEY_UID, uid);
//        params.put("visituid", MyApplication.getInstance().login_uid);
//        QGClient.getInstance().get(context, ConstantURL.GET_USER_INFO, params, handler);
    }

    public void setAvatarRequest(Context context, File picfile, QGHttpHandlerAsyn<UserInfo> handler) {
        try {
            RequestParams params = new RequestParams();
            params.put("avatarfile", picfile);
            params.put("visituid", MyApplication.getInstance().login_uid);
            QGClient.getInstance().postAsyn(context, ConstantURL.SET_AVATAR, params, handler);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void topicDetailRequest(Context context, String topicId, String commentId, QGHttpHandler<TopicInfoList> handler) {
        HashMap<String, String> params = new HashMap();
        if (topicId != null) {
            params.put("topicid", topicId);
        }
        if (commentId != null) {
            params.put("commentid", commentId);
        }
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.TOPIC_DETAIL, params, handler);
    }

    public void reportTopicRequest(Context context, String topicId, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("topicid", topicId);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.REPORT_TOPIC, params, handler);
    }

    public void delTopicRequest(Context context, String topicId, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("topicid", topicId);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.DEL_TOPIC, params, handler);
    }

    public void commentListRequest(Context context, String topicId, String commentId, String direction, QGHttpHandler<CommentList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("topicid", topicId);
        params.put("startcommentid", commentId);
        params.put("direction", direction);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.COMMENT_LIST, params, handler);
    }

    public void blockUserTopic(Context context, String blockUid, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("blockuid", blockUid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.BLOCK_USER_TOPIC, params, handler);
    }

    public void unblockUserTopic(Context context, String blockUid, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("blockuid", blockUid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.UNBLOCK_USER_TOPIC, params, handler);
    }

    public void blockUserTopicList(Context context, String startUid, int length, String direction, QGHttpHandler<BlockUserList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("startuid", startUid);
        params.put("len", new StringBuilder(String.valueOf(length)).toString());
        params.put("direction", direction);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.BOLCK_USER_TOPIC_LIST, params, handler);
    }

    public void setRemarkRequest(Context context, String uid, String remark, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("frienduid", uid);
        params.put("remark", remark);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.SET_REMARK, params, handler);
    }

    public void blockUserPrivateList(Context context, String startUid, int length, String direction, QGHttpHandler<BlockUserList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("startuid", startUid);
        params.put("len", new StringBuilder(String.valueOf(length)).toString());
        params.put("direction", direction);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.BOLCK_USER_MESSAGE_LIST, params, handler);
    }

    public void addFavoriteRequest(Context context, String topicId, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("topicid", topicId);
        QGClient.getInstance().get(context, ConstantURL.ADD_FAVORITE, params, handler);
    }

    public void delFavoriteRequest(Context context, String topicId, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("topicid", topicId);
        QGClient.getInstance().get(context, ConstantURL.DEL_FAVORITE, params, handler);
    }

    public void checkPhoneCanBindRequest(Context context, String phoneNumber, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("phonenumber", phoneNumber);
        QGClient.getInstance().get(context, ConstantURL.CHECK_PHONE_CAN_BIND, params, handler);
    }

    public void getPhoneBindVerifyCodeRequest(Context context, String phoneNumber, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("phonenumber", phoneNumber);
        QGClient.getInstance().get(context, ConstantURL.GET_PHONE_BIND_VERIFY_CODE, params, handler);
    }

    public void checkPhoneBindVerifyCodeRequest(Context context, String phoneNumber, String vcode, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("phonenumber", phoneNumber);
        params.put("vcode", vcode);
        QGClient.getInstance().get(context, ConstantURL.CHECK_PHONE_BIND_VERIFY_CODE, params, handler);
    }

    public void confirmBindPhoneRequest(Context context, String phoneNumber, String password, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("phonenumber", phoneNumber);
        params.put("password", password);
        QGClient.getInstance().get(context, ConstantURL.CONFIRM_BIND_PHONE, params, handler);
    }

    public void delCommentRequest(Context context, String topicId, String commentId, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("commentid", commentId);
        params.put("topicid", topicId);
        QGClient.getInstance().get(context, ConstantURL.DELETE_COMMENT, params, handler);
    }

    public void setUserInfo(Context context, String locationStatus, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("locationstatus", locationStatus);
        QGClient.getInstance().get(context, ConstantURL.SET_USER_INFO, params, handler);
    }

    public void readAllTipsRequest(Context context, QGHttpHandler<String> handler) {
        QGClient.getInstance().get(context, ConstantURL.SHARE_TOPIC_TO_MESSAGE, null, handler);
    }

    public void logoutRequest(Context context, QGHttpHandler<String> handler) {
        QGClient.getInstance().get(context, ConstantURL.LOGOUT, null, handler);
    }

    public void addTopicRequest(Context context, File picfile, String commentcontent, String locationX, String locationY, String commenttxtframe, QGHttpHandlerAsyn<TopicInfo> handler) {
//        try {
//            RequestParams params = new RequestParams();
//            params.put(MessageKey.MSG_CONTENT, picfile);
//            params.put("commentcontent", commentcontent);
//            params.put("commentlocationx", locationX);
//            params.put("commentlocationy", locationY);
//            params.put("commenttxtframe", commenttxtframe);
//            QGClient.getInstance().postAsyn(context, ConstantURL.SEND_TOPIC, params, handler);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public void sendMessageRequest(Context context, String message, String touid, QGHttpHandlerAsyn<String> handler) {
        RequestParams params = new RequestParams();
        params.put("message", message);
        params.put("touid", touid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().postAsyn(context, ConstantURL.SEND_MESSAGE, params, handler);
    }

    public void getMessageListRequest(Context context, String touid, String messageid, int len, String direction, QGHttpHandler<PrivateMessageList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("touid", touid);
        params.put("messageid", new StringBuilder(String.valueOf(messageid)).toString());
        params.put("len", new StringBuilder(String.valueOf(len)).toString());
        params.put("direction", direction);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.GET_MESSAGE_LIST, params, handler);
    }

    public void blockMessageRequest(Context context, String blockuid, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("blockuid", blockuid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.BLOCK_MESSAGE_LIST, params, handler);
    }

    public void unblockMessageRequest(Context context, String blockuid, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("blockuid", blockuid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.UNBLOCK_MESSAGE_LIST, params, handler);
    }

    public void clearSessionNewsRequest(Context context, String touid, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("touid", touid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.CLEAR_SESSION_NEWS, params, handler);
    }

    public void deleteSessionRequest(Context context, String touid, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("touid", touid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.DELETE_SESSION, params, handler);
    }

    public void getSessionListRequest(Context context, int page, int len, QGHttpHandler<SessionList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("page", new StringBuilder(String.valueOf(page)).toString());
        params.put("len", new StringBuilder(String.valueOf(len)).toString());
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.GET_SESSION_LIST, params, handler);
    }

    public void getTipsListRequest(Context context, String starttipid, int len, String direction, String type, QGHttpHandler<TipsList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("starttipid", starttipid);
        params.put("direction", direction);
        params.put("direction", direction);
        params.put("type", type);
        params.put("len", new StringBuilder(String.valueOf(len)).toString());
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.GET_TIPS_LIST, params, handler);
    }

    public void deleteTipRequest(Context context, String tipid, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("tipid", tipid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.DELETE_TIP, params, handler);
    }

    public void readTipRequest(Context context, String tipid, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("tipid", tipid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.READ_TIP, params, handler);
    }

    public void loginRequest(Context context, String phonenumber, String password, LoginActivity.LoginHandler handler) {
        HashMap<String, String> params = new HashMap();
        params.put("phonenumber", phonenumber);
        params.put("password", password);
        QGClient.getInstance().post(context, ConstantURL.LOGIN, params, handler);
    }

    public void qqLoginRequest(Context context, String openId, String accessToken, QGHttpHandler<UserInfo> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("accessToken", accessToken);
        params.put("openId", openId);
        QGClient.getInstance().post(context, ConstantURL.QQ_LOGIN, params, handler);
    }

    public void qqLoginRegRequest(Context context, String openId, String accessToken, String nickname, QGHttpHandler<TutuUsers> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("accessToken", accessToken);
        params.put("openId", openId);
        params.put(Constant.NICK_NAME_PERFERENCE, nickname);
        QGClient.getInstance().get(context, ConstantURL.QQ_LOGIN_REG, params, handler);
    }

    // 2015-01-03: GreenRose
    // 회원가입->이메일인증.
    public void getRegisterVerfityCodeRequest(Context context, String phonenumber, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("mb_id", phonenumber);
        params.put("mb_nick", "테스트");
        params.put("mb_password", "test");

        QGClient.getInstance().post(context, ConstantURL.GET_REGISTER_VERFITY_CODE, params, handler);
    }

    public void GetUsersInfoRequest(Context context, String strDirection, String strnStartID, String strLength, QGHttpHandler<UserInfoList> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("mb_id", "");

        QGClient.getInstance().post(context, ConstantURL.GET_USERS_INFO_URL, params, handler);
    }

    // 2015-01-03: GreenRose
    // 회원가입
    public void RegisterRequest(Context context, File fIcon, String strEmail, String NickName, String strPassword, QGHttpHandlerAsyn<String> handler) throws FileNotFoundException {
//        HashMap<String, String> params = new HashMap();
        RequestParams params = new RequestParams();
        params.put("mb_icon", fIcon);
        params.put("mb_id", strEmail);
        params.put("mb_nick", NickName);
        params.put("mb_password", strPassword);

        QGClient.getInstance().postAsyn(context, ConstantURL.GET_REGISTER_VERFITY_CODE, params, handler);
    }

    // 2015-01-03 : GreenRose
    // 이메일인증.
    public void checkVerfityCodeRequest(Context context, String phonenumber, String vcode, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("mb_id", phonenumber);
        params.put("certify_code", vcode);
        QGClient.getInstance().post(context, ConstantURL.CHECK_VERFITY_CODE, params, handler);
    }

    // 2015-01-03
    // 비밀번호변경
    public void newChangePWRequest(Context context, String strOldPW, String strNewPW, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("mb_password", strOldPW);
        params.put("new_mb_password", strNewPW);
        QGClient.getInstance().post(context, ConstantURL.RESET_PASSWORD, params, handler);
    }

    // 2015-01-03
    // 유저정보 변경.
    public void userInfoChangeRequest(Context context, String strNickName, String strName, String strHP, QGHttpHandler<String> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("mb_id", strNickName);
        params.put("mb_name", strName);
        params.put("mb_hp", strHP);
        QGClient.getInstance().post(context, ConstantURL.USERINFO_EDIT, params, handler);
    }

    // 2015-01-03
    // CheckEmail
    public void checkEmailRequest(Context context, String strEmailAddress, QGHttpHandler<String> handler )
    {
        HashMap<String, String> params = new HashMap();
        params.put("mb_id", strEmailAddress);
        QGClient.getInstance().post(context, ConstantURL.CHECK_EMAIL_URL, params, handler);
    }

    // 2015-01-03
    // 로그인
    public void LoginRequest(Context context, String strEmailAddress, String strPassword, QGHttpHandler<TutuUsers> handler)
    {
        HashMap<String, String> params = new HashMap();
        params.put("mb_id", strEmailAddress);
        params.put("mb_password", strPassword);
        QGClient.getInstance().post(context, ConstantURL.LOGIN_URL, params, handler);
    }

    public void registerRequest(Context context, String phonenumber, String password, String nickname, QGHttpHandler<TutuUsers> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("phonenumber", phonenumber);
        params.put("password", password);
        params.put("nickname", nickname);
        QGClient.getInstance().get(context, ConstantURL.REGISTER, params, handler);
    }

    public void getForgetVerfityCodeRequest(Context context, String phonenumber, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("phonenumber", phonenumber);
        QGClient.getInstance().get(context, ConstantURL.GET_FORGET_VERFITY_CODE, params, handler);
    }

    public void checkForgetVerfityCodeRequest(Context context, String phonenumber, String vcode, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("phonenumber", phonenumber);
        params.put("vcode", vcode);
        QGClient.getInstance().get(context, ConstantURL.CHECK_FORGET_VERFITY_CODE, params, handler);
    }

    public void resetPasswordRequest(Context context, String phonenumber, String newpassword, QGHttpHandler<TutuUsers> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("phonenumber", phonenumber);
        params.put("newpassword", newpassword);
        QGClient.getInstance().post(context, ConstantURL.RESET_PASSWORD, params, handler);
    }

    public void updatePasswordRequest(Context context, String newpassword, String oldpassword, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("newpassword", newpassword);
        params.put("oldpassword", oldpassword);
        QGClient.getInstance().post(context, ConstantURL.UPDATE_PASSWORD, params, handler);
    }

    public void uploadLocationRequest(Context context, String latitude, String longitude, String radius, String addr, String operationers, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("radius", radius);
        params.put("addr", addr);
        params.put("operationers", operationers);
        QGClient.getInstance().post(context, ConstantURL.UPLOAD_LOCATION, params, handler);
    }

    public void shareTopicToMessage(Context context, String toUid, String topicId, String title, String content, String message, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("touid", toUid);
        params.put("topicid", topicId);
//        params.put(QzoneShare.SHARE_TO_QQ_TITLE, title);
//        params.put(MessageKey.MSG_CONTENT, content);
        params.put("message", message);
        QGClient.getInstance().get(context, ConstantURL.SHARE_TOPIC_TO_MESSAGE, params, handler);
    }

    public void getMessageRelation(Context context, String uidlist, QGHttpHandler<MesageConversationModel> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("uidlist", uidlist);
        QGClient.getInstance().get(context, ConstantURL.MESSAGE_RELATION, params, handler);
    }

    public void getSelfInfoHttpRequest(Context context, String user_id, String gettopiclist, String len, QGHttpHandler<TutuUsers> handler) {
        HashMap<String, String> params = new HashMap();
//        params.put(SocializeProtocolConstants.PROTOCOL_KEY_UID, user_id);
        params.put("gettopiclist", gettopiclist);
        params.put("len", len);
        params.put("richtopicinfo", Constant.SYSTEM_UID);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.GET_SELF_INFO, params, handler);
    }

    public void getUserInfoHttpRequest(Context context, String userId, String user_id, String gettopiclist, String len, QGHttpHandler<TutuUsers> handler) {

//        HashMap<String, String> params = new HashMap();
//        params.put("uid", user_id);
//        params.put("gettopiclist", gettopiclist);
//        params.put("richtopicinfo", Constant.SYSTEM_UID);
//        params.put("len", len);
//        params.put("visituid", MyApplication.getInstance().login_uid);
//        QGClient.getInstance().get(context, ConstantURL.GET_USER_INFO, params, handler);

        HashMap<String, String> params = new HashMap();
        params.put("mb_id", userId);
        QGClient.getInstance().post(context, ConstantURL.GET_PERSONAL_INFO_URL, params, handler);
    }

    public void getPersonalWorksListRequest(Context context, String user_id, String startId, String len, String direction, QGHttpHandler<TopicInfoList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("uid", user_id);
        params.put("starttopicid", startId);
        params.put("len", len);
        params.put("richtopicinfo", Constant.SYSTEM_UID);
        params.put("direction", direction);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.GET_USER_WORKS, params, handler);
    }

    public void getPersonalCollectListRequest(Context context, String user_id, String startId, String len, String direction, QGHttpHandler<TopicInfoList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("starttopicid", startId);
        params.put("len", len);
        params.put("direction", direction);
        QGClient.getInstance().get(context, ConstantURL.GET_USER_COLLECT, params, handler);
    }

    public void getFriendsListRequest(Context context, String user_id, String startuid, String len, String direction, QGHttpHandler<FriendsList> handler) {
        HashMap<String, String> params = new HashMap();
//        params.put(SocializeProtocolConstants.PROTOCOL_KEY_UID, user_id);
        params.put("startuid", startuid);
        params.put("len", len);
        params.put("direction", direction);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.GET_FRIENDS, params, handler);
    }

    public void getSearchRequest(Context context, String keyword, String startuid, String len, String direction, QGHttpHandler<FriendsList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("keyword", keyword);
        params.put("startuid", startuid);
        params.put("len", len);
        params.put("direction", direction);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.SERACH_USERS, params, handler);
    }

    public void getAddFriendsRequest(Context context, String user_id, QGHttpHandler handler) {
        HashMap<String, String> params = new HashMap();
        params.put("frienduid", user_id);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.ADD_FRIENDS, params, handler);
    }

    public void getDelFriendsRequest(Context context, String user_id, QGHttpHandler handler) {
        HashMap<String, String> params = new HashMap();
        params.put("frienduid", user_id);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.DELETE_FRIENDS, params, handler);
    }

    public void sendContactsRequest(Context context, String devicesid, String content, QGHttpHandlerAsyn<NetContactsInfo> handler) {
        RequestParams params = new RequestParams();
        params.put("deviceid", devicesid);
//        params.put(MessageKey.MSG_CONTENT, content);
        QGClient.getInstance().postAsyn(context, ConstantURL.SERACH_CONTACTS_USERS, params, handler);
    }

    public void getMHConversation(Context context, QGHttpHandlerFileAsyn handler) {
        QGClient.getInstance().getfile(context, ConstantURL.GET_HISTORY_MESSAGE, new RequestParams(), handler);
    }

    public void getNeedHistory(Context context, QGHttpHandler<CheckImport> qgHttpHandler) {
        QGClient.getInstance().get(context, ConstantURL.CHECK_NEED_IMPORT, new HashMap(), qgHttpHandler);
    }

    public void getSameCityFriendsRequest(Context context, String latitude, String longitude, String province, String city, String startuid, String gender, String len, String direction, QGHttpHandler<FriendsList> handler) {
        HashMap<String, String> params = new HashMap();
        if (!(longitude == null || latitude == null)) {
            params.put("latitude", latitude);
            params.put("longitude", longitude);
        }
        if (!(province == null || city == null)) {
            params.put("province", province);
            params.put("city", city);
        }
        params.put("startuid", startuid);
        params.put("len", len);
        params.put("direction", direction);
        if (!(gender == null || gender.equals("01"))) {
//            params.put(SocializeProtocolConstants.PROTOCOL_KEY_GENDER, gender);
        }
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.SERACH_SAME_CITY_USERS, params, handler);
    }

    public void getRongyunToken(Context context, QGHttpHandler<TokenModle> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.RONGYUN_GET_TOKEN, params, handler);
    }

    public void addComment(Context context, String topicid, String content, String replycommentid, String txtframe, QGHttpHandler<Comment> handler) {
        HashMap<String, String> params = new HashMap();
        params.put(Util.EXTRA_TOPICID, topicid);
        params.put("content", content);
        params.put("replycommentid", replycommentid);
        params.put("txtframe", txtframe);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.ADD_COMMENT_URL, params, handler);
    }

    public void getUserMessageNum(Context context, QGHttpHandler<MessageNum> handler) {
        QGClient.getInstance().get(context, ConstantURL.USER_MESSAGE_NUM_URL, null, handler);
    }

    public void getLiveRoomTopicInfo(Context context, String topicid, QGHttpHandler<PublishLiveModel> handler) {
        HashMap<String, String> params = new HashMap();
        params.put(Util.EXTRA_TOPICID, topicid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().post(context, ConstantURL.GET_LIVE_ROOM_TOPICINFO, params, handler);
    }

    public void getHomeHotOrFriendList(Context context, String startTopicId, String endTopicId, String len, String direction, boolean isHot, QGHttpHandler<TopicInfoList> handler) {
//        HashMap<String, String> params = new HashMap();
//        params.put("starttopicid", startTopicId);
//        params.put("endtopicid", endTopicId);
//        params.put("direction", direction);
//        params.put("len", len);
//        if (MyApplication.getInstance().getUserinfo() != null) {
//            params.put("visituid", MyApplication.getInstance().getUserinfo().getUid());
//        }
//        String requestUrl = BaseConstants.ah;
//        if (isHot) {
//            requestUrl = ConstantURL.HOT_LIST;
//        } else {
//            requestUrl = ConstantURL.FOLLOW_LIST;
//        }
//        QGClient.getInstance().get(context, requestUrl, params, handler, true);

        HashMap<String, String> params = new HashMap();
        params.put("nStart", "10");
        params.put("nDirection", "10");

        QGClient.getInstance().post(context, ConstantURL.TOPIC_URL, params, handler);
    }

    public void squareSearchUsers(Context context, String keyword, String startuid, String len, String direction, QGHttpHandler<FriendsList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("keyword", keyword);
        params.put("startuid", startuid);
        params.put("len", len);
        params.put("direction", direction);
        QGClient.getInstance().get(context, ConstantURL.SQUARE_SEARCH_USERS, params, handler);
    }

    public void addFollowRequest(Context context, String userId, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("frienduid", userId);
        QGClient.getInstance().get(context, ConstantURL.ADD_FOLLOW_URL, params, handler);
    }

    public void delFollowRequest(Context context, String userId, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("frienduid", userId);
        QGClient.getInstance().get(context, ConstantURL.DEL_FOLLOW_URL, params, handler);
    }

    public void squareSearchHuati(Context context, String keyword, String starthtid, String len, String direction, QGHttpHandler<SquareHuati> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("keyword", keyword);
        params.put("starthtid", starthtid);
        params.put("len", len);
        params.put("direction", direction);
        QGClient.getInstance().get(context, ConstantURL.SQUARE_SEARCH_HUATI, params, handler);
    }

    public void checkCanLive(Context context, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().post(context, ConstantURL.CHECK_CAN_LIVE, params, handler);
    }

    public void addHuatiFollow(Context context, String htid, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("htid", htid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.ADD_HUATI_FOLLOW, params, handler);
    }

    public void addPoiHuatiFollow(Context context, String poiId, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put(ParamKey.POIID, poiId);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.ADD_POI_FOLLOW, params, handler);
    }

    public void delHuatiFollow(Context context, String htid, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("htid", htid);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.DEL_HUATI_FOLLOW, params, handler);
    }

    public static final class ParamKey {
        public static final String CARDID = "cardid";
        public static final String CONTENT = "content";
        public static final String COUNT = "count";
        public static final String EXTPARAM = "extparam";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String MBLOGID = "mblogid";
        public static final String NICK = "nick";
        public static final String OFFSET = "offset";
        public static final String PACKAGENAME = "packagename";
        public static final String PAGE = "page";
        public static final String PAGEID = "pageid";
        public static final String POIID = "poiid";
        public static final String POINAME = "poiname";
        public static final String SINAINTERNALBROWSER = "sinainternalbrowser";
        public static final String TITLE = "title";
        public static final String UID = "uid";
        public static final String URL = "url";
    }

    public void delPoiHuatiFollow(Context context, String poiId, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put(ParamKey.POIID, poiId);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.DEL_POI_FOLLOW, params, handler);
    }

    public void getRecommendList(Context context, QGHttpHandler<RecommendModel> handler) {
        HashMap<String, String> params = new HashMap();
        if (MyApplication.getInstance().getUserinfo() != null) {
            params.put("visituid", MyApplication.getInstance().getUserinfo().getUid());
        }
        params.put(ParamKey.LATITUDE, String.valueOf(MyApplication.getInstance().getLatitude()));
        params.put(ParamKey.LONGITUDE, String.valueOf(MyApplication.getInstance().getLongitude()));
        QGClient.getInstance().get(context, ConstantURL.GET_RECOMMEND_LIST_URL, params, handler);
    }

    public void getTopicEmotion(Context context, QGHttpHandler<ArrayList<CommentEmotion>> handler) {
        QGClient.getInstance().get(context, ConstantURL.TOPIC_EMOTION_URL, null, handler);
    }

    public void reportCommentRequest(Context context, String commentId, QGHttpHandler<String> handler) {
        HashMap<String, String> params = new HashMap();
        params.put("commentid", commentId);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.REPORT_COMMENT, params, handler);
    }

    public void newCommentListRequest(Context context, String topicId, String startCommentId, int len, String direction, QGHttpHandler<NewCommentList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put(Util.EXTRA_TOPICID, topicId);
        params.put("startcommentid", startCommentId);
        params.put("len", new StringBuilder(String.valueOf(len)).toString());
        params.put("direction", direction);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.NEW_COMMENT_LIST, params, handler);
    }

    public void getTopicReciveList(Context context, String topicid, String startitemflag, String direction, String len, QGHttpHandler<TopicReciveRedPacketModel> handler) {
        HashMap<String, String> params = new HashMap();
        params.put(Util.EXTRA_TOPICID, topicid);
        params.put("startitemflag", startitemflag);
        params.put("direction", direction);
        params.put("len", len);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().post(context, ConstantURL.GET_TOPIC_RECIVE_LIST, params, handler);
    }

    public void getTopicLikeList(Context context, String topicid, String startlikeflag, String direction, String len, QGHttpHandler<TopicZanModel> handler) {
        HashMap<String, String> params = new HashMap();
        params.put(Util.EXTRA_TOPICID, topicid);
        params.put("startlikeflag", startlikeflag);
        params.put("direction", direction);
        params.put("len", len);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().post(context, ConstantURL.GET_TOPIC_LIKE_LIST, params, handler);
    }

    public void getStickers(Context context, QGHttpHandler<ArrayList<CommentEmotion>> handler) {
        QGClient.getInstance().get(context, ConstantURL.STICKER_LIST_URL, null, handler);
    }

    public void getCommentBox(Context context, QGHttpHandler<CommentBox> handler) {
        QGClient.getInstance().get(context, ConstantURL.COMMENT_BOX_URL, null, handler);
    }

    public void hotCommentRequest(Context context, String topicId, String startCommentId, String direction, QGHttpHandler<HotCommentList> handler) {
        HashMap<String, String> params = new HashMap();
        params.put(Util.EXTRA_TOPICID, topicId);
        params.put("startcommentid", startCommentId);
        params.put("direction", direction);
        params.put("visituid", MyApplication.getInstance().login_uid);
        QGClient.getInstance().get(context, ConstantURL.NEW_COMMENT_LIST, params, handler);
    }


}
