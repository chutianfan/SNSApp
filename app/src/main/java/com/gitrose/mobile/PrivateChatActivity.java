package com.gitrose.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.sea_monster.core.network.ApiReqeust;
//import com.tencent.connect.share.QzoneShare;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.PrivateChatAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.application.MyContext;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
//import com.gitrose.mobile.http.QGHttpHandlerAsyn;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.PrivateMeassageModel;
import com.gitrose.mobile.model.PrivateMessageList;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.FaceView;
//import com.umeng.socialize.net.utils.SocializeProtocolConstants;
//import io.rong.imlib.RongIMClient;
//import io.rong.imlib.RongIMClient.Conversation;
//import io.rong.imlib.RongIMClient.ConversationType;
//import io.rong.imlib.RongIMClient.MessageContent;
//import io.rong.imlib.RongIMClient.SendMessageCallback;
//import io.rong.imlib.RongIMClient.SendMessageCallback.ErrorCode;
//import io.rong.message.BuildConfig;
//import io.rong.message.ImageMessage;
//import io.rong.message.TextMessage;
//import io.rong.message.VoiceMessage;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.StringUtils;
//import shouji.gexing.framework.utils.UiUtils;

public class PrivateChatActivity extends BaseActivity implements OnClickListener, OnTouchListener {
    private static final int CHAT_CAMERA = 0;
    private static final int CHAT_PHOTO = 1;
    private static PrivateChatActivity chatActivity;
    private static int[] volumeRes;
    private PrivateChatAdapter adapter;
    OnClickListener avatarClick;
    private TextView block_tv;
    private TextView btn_send_chat;
    private Rect chat_vioce_rect;
    private RelativeLayout container_rl;
    private EditText et_chat_content;
    private ImageView face_iv;
    private LinearLayout face_ll;
    private int isblock;
    private ImageView iv_chat_add;
    private ImageView iv_chat_voice;
    private ImageView iv_record_voice;
    private int len;
//    private PullToRefreshListView listview;
    public LinearLayout ll;
    private String mCurrentCameraPath;
    private InputMethodManager mInputMethodManager;
    private PopupWindow mPopupWindow;
    private ArrayList<PrivateMeassageModel> messageList;
    private int messageid;
    private String my_avatar_time;
    private String my_uid;
    private boolean needtoclear;
    private ShowVolumeHandler recordHandler;
    private ImageView right_iv;
    private RelativeLayout rl_chat_picture;
    private RelativeLayout rl_chat_voice;
    private String targetid;
    private String targetname;
    private VoiceVolumeThread threadVolume;
    private TextView title_tv;
    private TextView tv_chat_camera;
    private TextView tv_chat_photo;
    private TextView tv_record_tip;

    /* renamed from: com.gitrose.mobile.PrivateChatActivity.1 */
    class C03001 implements OnClickListener {
        C03001() {
        }

        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.private_chat_item_left_iv_avatar:
                case R.id.private_chat_item_right_iv_avatar:
                    PrivateMeassageModel messageModel = (PrivateMeassageModel) v.getTag();
//                    intent = new Intent(PrivateChatActivity.this, PersonalActivity.class);
//                    intent.putExtra(PersonalActivity.PERSONAL_USER_ID, messageModel.getUid());
//                    PrivateChatActivity.this.startActivityForNew(intent);
                case R.id.rl_media_left_look:
                case R.id.tv_media_left_look:
                case R.id.rl_media_right_look:
                case R.id.tv_media_right_look:
                case R.id.rl_system_look:
                case R.id.tv_system_look:
                    String btnUrl = (String) v.getTag();
                    if (btnUrl != null && !BuildConfig.FLAVOR.equals(btnUrl)) {
                        if (btnUrl.startsWith("http:")) {
                            intent = new Intent(PrivateChatActivity.this, WebViewActivity.class);
                            intent.putExtra("web_url", btnUrl);
                        } else if (btnUrl.startsWith("Tutu:")) {
                            String themeId = btnUrl.split("//")[PrivateChatActivity.CHAT_PHOTO].split("=")[PrivateChatActivity.CHAT_PHOTO];
                            intent = new Intent(PrivateChatActivity.this, TopicDetailActivity.class);
                            intent.putExtra(TopicDetailActivity.TOPIC_ID, themeId);
                        }
                        PrivateChatActivity.this.startActivityForNew(intent);
                    }
                default:
                    PrivateChatActivity.this.mInputMethodManager.hideSoftInputFromWindow(PrivateChatActivity.this.et_chat_content.getWindowToken(), PrivateChatActivity.CHAT_CAMERA);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateChatActivity.2 */
    class C03012 implements TextWatcher {
        C03012() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() > 0) {
//                PrivateChatActivity.this.btn_send_chat.setVisibility(PrivateChatActivity.CHAT_CAMERA);
//                PrivateChatActivity.this.iv_chat_voice.setVisibility(8);
                return;
            }
//            PrivateChatActivity.this.btn_send_chat.setVisibility(8);
//            PrivateChatActivity.this.iv_chat_voice.setVisibility(PrivateChatActivity.CHAT_CAMERA);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    private static class ShowVolumeHandler extends Handler {
        WeakReference<PrivateChatActivity> mActivity;

        public ShowVolumeHandler(PrivateChatActivity activity) {
            this.mActivity = new WeakReference(activity);
        }

        public void handleMessage(Message msg) {
            PrivateChatActivity theActivity = (PrivateChatActivity) this.mActivity.get();
            if (theActivity != null) {
                try {
                    theActivity.iv_record_voice.setImageResource(PrivateChatActivity.volumeRes[msg.what]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class VoiceVolumeThread extends Thread {
        private int mFack;
        private volatile boolean running;

        private VoiceVolumeThread() {
            this.running = true;
            this.mFack = PrivateChatActivity.CHAT_CAMERA;
        }

        public void exit() {
//            PrivateChatActivity.this.iv_record_voice.setImageResource(R.drawable.chat_start_voice_bg);
            this.running = false;
        }

        public void run() {
            while (this.running) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = this.mFack + PrivateChatActivity.CHAT_PHOTO;
                this.mFack = i;
                if (i > 2) {
                    this.mFack = PrivateChatActivity.CHAT_CAMERA;
                }
                PrivateChatActivity.this.recordHandler.sendEmptyMessage(this.mFack);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateChatActivity.11 */
//    class AnonymousClass11 implements SendMessageCallback {
//        private final /* synthetic */ MessageContent val$msg;
//
//        AnonymousClass11(MessageContent messageContent) {
//            this.val$msg = messageContent;
//        }
//
//        public void onError(int arg0, ErrorCode arg1) {
//        }
//
//        public void onProgress(int arg0, int arg1) {
//        }
//
//        public void onSuccess(int messageid) {
//            if (this.val$msg instanceof TextMessage) {
//                Log.d("sendMessage", "TextMessage--\u53d1\u9001\u4e86\u4e00\u6761\u3010\u6587\u5b57\u6d88\u606f\u3011--" + this.val$msg.getContent());
//            } else if (this.val$msg instanceof ImageMessage) {
//                Log.d("sendMessage", "ImageMessage-\u53d1\u9001\u4e86\u4e00\u6761\u3010\u56fe\u7247\u6d88\u606f\u3011--uri---" + this.val$msg.getThumUri());
//            } else {
//                boolean z = this.val$msg instanceof VoiceMessage;
//            }
//        }
//    }
//
//    /* renamed from: com.gitrose.mobile.PrivateChatActivity.3 */
//    class C05213 implements OnRefreshListener<ListView> {
//        C05213() {
//        }
//
//        public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
//            if (PrivateChatActivity.this.listview.getCurrentMode() == Mode.PULL_FROM_START && PrivateChatActivity.this.messageList != null && PrivateChatActivity.this.messageList.size() > 0) {
//                PlaySound.getInstance(PrivateChatActivity.this).toPlay(R.raw.refresh);
//                PrivateChatActivity.this.getData(((PrivateMeassageModel) PrivateChatActivity.this.messageList.get(PrivateChatActivity.CHAT_CAMERA)).getMessageid(), HomeActivity.UP, false);
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.PrivateChatActivity.10 */
    class AnonymousClass10 extends QGHttpHandler<String> {
        AnonymousClass10(Context $anonymous0, boolean $anonymous1) {
            super($anonymous0, $anonymous1);
        }

        public void onGetDataSuccess(String data) {
            try {
                MyApplication.getInstance().setPrivateNum(new JSONObject(data).getInt("newmessagecount"));
                PrivateListActivity.getObject().torefresh();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateChatActivity.4 */
    class C06684 extends QGHttpHandler<TutuUsers> {
        C06684(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(TutuUsers data) {
            data.setUid(PrivateChatActivity.this.my_uid);
            MyApplication.getInstance().setUserinfo(data);
            PrivateChatActivity.this.my_avatar_time = data.getAvatartime();
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateChatActivity.5 */
//    class C06695 extends QGHttpHandler<PrivateMessageList> {
//        private final /* synthetic */ String val$direction;
//
//        C06695(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {
//            this.val$direction = str;
//            super($anonymous0, $anonymous1, $anonymous2);
//        }
//
//        public void onGetDataSuccess(PrivateMessageList data) {
//            if (data != null && data.getMessagelist().size() > 0) {
//                PrivateChatActivity.this.isblock = data.getIsblock();
//                if (this.val$direction.equals(HomeActivity.UP)) {
//                    boolean to_down = true;
//                    if (PrivateChatActivity.this.messageList.size() > 0) {
//                        to_down = false;
//                    }
//                    PrivateChatActivity.this.messageList.addAll(PrivateChatActivity.CHAT_CAMERA, data.getMessagelist());
//                    PrivateChatActivity.this.adapter.setList(PrivateChatActivity.this.messageList);
//                    if (to_down) {
//                        ((ListView) PrivateChatActivity.this.listview.getRefreshableView()).setSelection(PrivateChatActivity.this.messageList.size());
//                    }
//                } else if (this.val$direction.equals(HomeActivity.DOWN)) {
//                    PrivateChatActivity.this.messageList.addAll(PrivateChatActivity.this.messageList.size(), data.getMessagelist());
//                    PrivateChatActivity.this.adapter.setList(PrivateChatActivity.this.messageList);
//                    ((ListView) PrivateChatActivity.this.listview.getRefreshableView()).setSelection(PrivateChatActivity.this.messageList.size());
//                }
//            }
//        }
//
//        public void onFinish() {
//            super.onFinish();
//            PrivateChatActivity.this.listview.onRefreshComplete();
//        }
//    }

    /* renamed from: com.gitrose.mobile.PrivateChatActivity.7 */
    class C06707 extends QGHttpHandler<String> {
        C06707(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(String data) {
            PrivateChatActivity.this.messageList = null;
            PrivateChatActivity.this.adapter.setList(PrivateChatActivity.this.messageList);
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateChatActivity.8 */
    class C06718 extends QGHttpHandler<String> {
        C06718(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(String data) {
            PrivateChatActivity.this.isblock = PrivateChatActivity.CHAT_PHOTO;
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateChatActivity.9 */
    class C06729 extends QGHttpHandler<String> {
        C06729(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(String data) {
            PrivateChatActivity.this.isblock = PrivateChatActivity.CHAT_CAMERA;
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateChatActivity.6 */
//    class C07446 extends QGHttpHandlerAsyn<String> {
//        private final /* synthetic */ String val$message;
//
//        C07446(Context $anonymous0, boolean $anonymous1, String str) {
//            this.val$message = str;
//            super($anonymous0, $anonymous1);
//        }
//
//        public void onGetDataSuccess(String data) {
//            try {
//                if (!StringUtils.isEmpty(data)) {
//                    JSONObject jsonObject = new JSONObject(data);
//                    String messageid = jsonObject.getString("messageid");
//                    String type = jsonObject.getString(SocializeProtocolConstants.PROTOCOL_SHARE_TYPE);
//                    PrivateMeassageModel mode = new PrivateMeassageModel();
//                    mode.setContent(this.val$message);
//                    mode.setUid(PrivateChatActivity.this.my_uid);
//                    mode.setAvatartime(PrivateChatActivity.this.my_avatar_time);
//                    mode.setMessageid(messageid);
//                    mode.setType(type);
//                    PrivateChatActivity.this.messageList.add(mode);
//                    PrivateChatActivity.this.adapter.setList(PrivateChatActivity.this.messageList);
//                    ((ListView) PrivateChatActivity.this.listview.getRefreshableView()).smoothScrollToPosition(PrivateChatActivity.this.messageList.size());
//                }
//            } catch (Exception e) {
//            }
//        }
//    }

    public PrivateChatActivity() {
        this.messageList = new ArrayList();
        this.messageid = CHAT_CAMERA;
        this.len = 100;
        this.isblock = CHAT_CAMERA;
        this.needtoclear = false;
        this.chat_vioce_rect = null;
        this.avatarClick = new C03001();
    }

    static {
        volumeRes = new int[]{R.drawable.chat_voice_stop_1, R.drawable.chat_voice_stop_2, R.drawable.chat_voice_stop_3};
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);
        this.targetid = getIntent().getStringExtra("touid");
        this.targetname = getIntent().getStringExtra("nickname");
        this.needtoclear = getIntent().getBooleanExtra("needtoclear", false);
        if (MyApplication.getInstance().getUserinfo() != null) {
            this.my_uid = MyApplication.getInstance().getUserinfo().getUid();
            this.my_avatar_time = MyApplication.getInstance().getUserinfo().getAvatartime();
        } else {
            finish();
        }
        initUI();
//        this.mInputMethodManager = (InputMethodManager) getSystemService("input_method");
//        getConversationList();
//        if (this.my_avatar_time == null || this.my_avatar_time.equals(BuildConfig.FLAVOR)) {
//            getUserInfo();
//        }
//        chatActivity = this;
    }

    private void initUI() {
        this.iv_chat_add = (ImageView) findViewById(R.id.iv_chat_add);
        this.iv_chat_add.setOnClickListener(this);
        this.iv_chat_voice = (ImageView) findViewById(R.id.iv_chat_voice);
        this.iv_chat_voice.setOnClickListener(this);
        this.rl_chat_picture = (RelativeLayout) findViewById(R.id.rl_chat_picture);
        this.tv_chat_camera = (TextView) findViewById(R.id.tv_chat_camera);
        this.tv_chat_camera.setOnClickListener(this);
        this.tv_chat_photo = (TextView) findViewById(R.id.tv_chat_photo);
        this.tv_chat_photo.setOnClickListener(this);
        this.rl_chat_voice = (RelativeLayout) findViewById(R.id.rl_chat_voice);
        this.iv_record_voice = (ImageView) findViewById(R.id.iv_record_voice);
        this.iv_record_voice.setOnTouchListener(this);
        this.tv_record_tip = (TextView) findViewById(R.id.tv_record_tip);
        this.title_tv = (TextView) findViewById(R.id.title_tv_text);
        findViewById(R.id.title_tv_left).setOnClickListener(this);
        this.right_iv = (ImageView) findViewById(R.id.title_right_iv);
        this.right_iv.setOnClickListener(this);
        this.right_iv.setVisibility(View.VISIBLE);
        this.title_tv.setText(this.targetname);
        this.container_rl = (RelativeLayout) findViewById(R.id.container_rl);
        this.face_iv = (ImageView) findViewById(R.id.private_chat_iv_face);
        this.face_ll = (LinearLayout) findViewById(R.id.ll_face_page);
        this.et_chat_content = (EditText) findViewById(R.id.et_chat_content);
        this.et_chat_content.setOnClickListener(this);
        this.et_chat_content.addTextChangedListener(new C03012());
        this.face_ll.addView(new FaceView(this, this.et_chat_content));
//        this.listview = (PullToRefreshListView) findViewById(R.id.private_chat_listview);
        this.btn_send_chat = (TextView) findViewById(R.id.btn_send_chat);
        this.ll = (LinearLayout) View.inflate(this, R.layout.private_chat_pop, null);
        this.block_tv = (TextView) this.ll.findViewById(R.id.chat_pop_tv_close);
        this.block_tv.setOnClickListener(this);
        this.ll.findViewById(R.id.chat_pop_tv_delete).setOnClickListener(this);
        this.btn_send_chat.setOnClickListener(this);
        this.face_iv.setOnClickListener(this);
        this.adapter = new PrivateChatAdapter(this, this.avatarClick);
//        this.listview.setAdapter(this.adapter);
//        this.listview.setMode(Mode.PULL_FROM_START);
//        this.listview.setOnRefreshListener(new C05213());
        this.adapter.setList(this.messageList);
        this.recordHandler = new ShowVolumeHandler(this);
    }

    public static PrivateChatActivity getObject() {
        return chatActivity;
    }

    public void needToRefresh() {
        if (this.messageList == null || this.messageList.size() <= 0) {
//            getData(new StringBuilder(String.valueOf(this.messageid)).toString(), HomeActivity.UP, true);
        } else {
//            getData(((PrivateMeassageModel) this.messageList.get(this.messageList.size() - 1)).getMessageid(), HomeActivity.DOWN, false);
        }
    }

    private void getUserInfo() {
        if (this.my_uid != null) {
            QGHttpRequest.getInstance().getSelfInfoHttpRequest(this, this.my_uid, "0", "0", new C06684(this));
        }
    }

    private void getData(String messageid, String direction, boolean isloading) {
//        QGHttpRequest.getInstance().getMessageListRequest(this, this.targetid, messageid, this.len, direction, new C06695(this, isloading, this.container_rl, direction));
    }

    private void send(String message, String to_uid) {
//        QGHttpRequest.getInstance().sendMessageRequest(this, message, to_uid, new C07446(this, false, message));
    }

    private void deleteSession(String to_uid) {
        QGHttpRequest.getInstance().deleteSessionRequest(this, to_uid, new C06707(this, true, this.container_rl));
    }

    private void blockMessage(String to_uid) {
        QGHttpRequest.getInstance().blockMessageRequest(this, to_uid, new C06718(this, true, this.container_rl));
    }

    private void unblockMessage(String to_uid) {
        QGHttpRequest.getInstance().unblockMessageRequest(this, to_uid, new C06729(this, true, this.container_rl));
    }

    private void clearSeesionNews(boolean isneedtoclear, String to_uid) {
        QGHttpRequest.getInstance().clearSessionNewsRequest(MyApplication.getInstance(), to_uid, new AnonymousClass10(MyApplication.getInstance(), false));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case CHAT_CAMERA /*0*/:
                    if (this.mCurrentCameraPath != null && !BuildConfig.FLAVOR.equals(this.mCurrentCameraPath)) {
                        Log.e("jinkuan", "cameraPath,,," + this.mCurrentCameraPath);
                    }
                case CHAT_PHOTO /*1*/:
                    Uri uri1 = data.getData();
                    String[] filePath = new String[CHAT_PHOTO];
                    filePath[CHAT_CAMERA] = "_data";
                    Cursor cursor = getContentResolver().query(uri1, filePath, null, null, null);
                    cursor.moveToFirst();
                    Log.e("jinkuan", "photoPath,,," + cursor.getString(cursor.getColumnIndex(filePath[CHAT_CAMERA])));
                default:
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.title_tv_left:
//                if (this.face_ll.getVisibility() == 0) {
//                    this.face_ll.setVisibility(8);
//                    this.face_iv.setImageDrawable(getResources().getDrawable(R.drawable.chat_emotion_bg));
//                    return;
//                }
//                finish();
//            case R.id.private_chat_iv_face:
//                this.iv_chat_add.setImageResource(R.drawable.chat_add_bg);
//                this.iv_chat_voice.setImageResource(R.drawable.chat_voice_bg);
//                if (this.face_ll.getVisibility() == 8) {
//                    this.face_ll.setVisibility(CHAT_CAMERA);
//                    this.mInputMethodManager.hideSoftInputFromWindow(this.et_chat_content.getWindowToken(), CHAT_CAMERA);
//                    return;
//                }
//                this.face_ll.setVisibility(8);
//            case R.id.title_right_iv:
//                int[] xy = new int[2];
//                this.right_iv.getLocationOnScreen(xy);
//                showPopWindow(this.ll, this.right_iv, xy);
//            case R.id.iv_chat_add:
//                this.mInputMethodManager.hideSoftInputFromWindow(this.et_chat_content.getWindowToken(), CHAT_CAMERA);
//                this.iv_chat_add.setImageResource(R.drawable.chat_keybord_bg);
//                this.iv_chat_voice.setImageResource(R.drawable.chat_voice_bg);
//                this.rl_chat_picture.setVisibility(CHAT_CAMERA);
//                this.rl_chat_voice.setVisibility(8);
//                this.face_ll.setVisibility(8);
//            case R.id.et_chat_content:
//                if (this.rl_chat_picture.getVisibility() == 0) {
//                    this.rl_chat_picture.setVisibility(8);
//                    this.iv_chat_add.setImageResource(R.drawable.chat_add_bg);
//                }
//                if (this.face_ll.getVisibility() == 0) {
//                    this.face_ll.setVisibility(8);
//                    this.face_iv.setImageResource(R.drawable.chat_emotion_bg);
//                }
//                if (this.rl_chat_voice.getVisibility() == 0) {
//                    this.rl_chat_voice.setVisibility(8);
//                    this.iv_chat_voice.setImageResource(R.drawable.chat_voice_bg);
//                }
//                this.mInputMethodManager.showSoftInput(this.et_chat_content, CHAT_CAMERA);
//            case R.id.btn_send_chat:
//                if (MyApplication.getInstance().getUserinfo() == null) {
//                    startActivity(new Intent(this, LoginActivity.class));
//                    return;
//                }
//                String sendContent = this.et_chat_content.getText().toString();
//                if (!sendContent.equals(BuildConfig.FLAVOR)) {
//                    PlaySound.getInstance(this).toPlay(R.raw.send_message);
//                    sendMessage(MyContext.getInstance().getRongIMClient(), this.targetid, TextMessage.obtain(sendContent));
//                    this.et_chat_content.setText(BuildConfig.FLAVOR);
//                    this.face_ll.setVisibility(8);
//                    this.face_iv.setImageDrawable(getResources().getDrawable(R.drawable.chat_emotion_bg));
//                    this.mInputMethodManager.hideSoftInputFromWindow(this.et_chat_content.getWindowToken(), CHAT_CAMERA);
//                }
//            case R.id.iv_chat_voice:
//                this.mInputMethodManager.hideSoftInputFromWindow(this.et_chat_content.getWindowToken(), CHAT_CAMERA);
//                this.iv_chat_add.setImageResource(R.drawable.chat_add_bg);
//                this.iv_chat_voice.setImageResource(R.drawable.chat_keybord_bg);
//                this.iv_record_voice.setImageResource(R.drawable.chat_start_voice_bg);
//                this.rl_chat_voice.setVisibility(CHAT_CAMERA);
//                this.rl_chat_picture.setVisibility(8);
//                this.face_ll.setVisibility(8);
//            case R.id.tv_chat_camera:
//                if (Environment.getExternalStorageState().equals("mounted")) {
//                    this.iv_chat_add.setImageResource(R.drawable.chat_add_bg);
//                    this.rl_chat_picture.setVisibility(8);
//                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                    String filename = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss").format(new Date(System.currentTimeMillis()));
//                    ContentValues values = new ContentValues();
//                    values.put(QzoneShare.SHARE_TO_QQ_TITLE, filename);
//                    Uri photoUri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
//                    intent.putExtra("output", photoUri);
//                    String[] proj = new String[CHAT_PHOTO];
//                    proj[CHAT_CAMERA] = "_data";
//                    Cursor cursor = getContentResolver().query(photoUri, proj, null, null, null);
//                    int column_index = cursor.getColumnIndexOrThrow("_data");
//                    cursor.moveToFirst();
//                    this.mCurrentCameraPath = cursor.getString(column_index);
//                    Log.e("jinkuan", "tv_chat_camera,,," + this.mCurrentCameraPath);
//                    cursor.close();
//                    startActivityForResult(intent, CHAT_CAMERA);
//                    return;
//                }
//                Toast.makeText(this, "sd\u5361\u4e0d\u53ef\u7528", CHAT_CAMERA).show();
//            case R.id.tv_chat_photo:
//                if (Environment.getExternalStorageState().equals("mounted")) {
//                    this.iv_chat_add.setImageResource(R.drawable.chat_add_bg);
//                    this.rl_chat_picture.setVisibility(8);
//                    startActivityForResult(new Intent("android.intent.action.PICK", Media.INTERNAL_CONTENT_URI), CHAT_PHOTO);
//                    return;
//                }
//                Toast.makeText(this, "sd\u5361\u4e0d\u53ef\u7528", CHAT_CAMERA).show();
//            case R.id.chat_pop_tv_delete:
//                this.mPopupWindow.dismiss();
//                deleteSession(this.targetid);
//            case R.id.chat_pop_tv_close:
//                this.mPopupWindow.dismiss();
//                if (this.isblock == CHAT_PHOTO) {
//                    unblockMessage(this.targetid);
//                } else {
//                    blockMessage(this.targetid);
//                }
            default:
        }
    }

    public void showPopWindow(View contentView, View view, int[] arrayOfInt) {
//        if (this.isblock == CHAT_PHOTO) {
//            this.block_tv.setText("\u89e3\u9664\u5c4f\u853d\u79c1\u4fe1");
//        } else {
//            this.block_tv.setText("\u5c4f\u853d\u79c1\u4fe1");
//        }
//        if (this.mPopupWindow == null || !this.mPopupWindow.isShowing()) {
//            this.mPopupWindow = new PopupWindow(contentView, -2, -2);
//            this.mPopupWindow.setFocusable(true);
//            this.mPopupWindow.setOutsideTouchable(true);
//            this.mPopupWindow.update();
//            this.mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.private_chat_pop_bg));
//            this.mPopupWindow.setSoftInputMode(36);
//            this.mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
//            arrayOfInt[CHAT_PHOTO] = arrayOfInt[CHAT_PHOTO] + CHAT_PHOTO;
//            this.mPopupWindow.update();
//            this.mPopupWindow.showAtLocation(view, CHAT_CAMERA, arrayOfInt[CHAT_CAMERA] - UiUtils.getInstance(this).DipToPixels(100.0f), arrayOfInt[CHAT_PHOTO] + UiUtils.getInstance(this).DipToPixels(30.0f));
//            return;
//        }
//        this.mPopupWindow.dismiss();
//        this.mPopupWindow = null;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (4 != event.getKeyCode() || this.face_ll.getVisibility() != 0) {
//            return super.onKeyDown(keyCode, event);
//        }
//        this.face_ll.setVisibility(8);
//        this.face_iv.setImageDrawable(getResources().getDrawable(R.drawable.chat_emotion_bg));
        return true;
    }

    public void finish() {
        super.finish();
        clearSeesionNews(this.needtoclear, this.targetid);
    }

    public boolean onTouch(View v, MotionEvent event) {
//        if (this.chat_vioce_rect == null) {
//            this.chat_vioce_rect = new Rect(this.iv_record_voice.getLeft(), this.iv_record_voice.getTop(), this.iv_record_voice.getRight(), this.iv_record_voice.getBottom());
//            int[] ps = new int[2];
//            this.iv_record_voice.getLocationOnScreen(ps);
//            this.chat_vioce_rect.offsetTo(ps[CHAT_CAMERA], ps[CHAT_PHOTO]);
//            Rect rect = this.chat_vioce_rect;
//            rect.top -= this.chat_vioce_rect.height() / 2;
//            rect = this.chat_vioce_rect;
//            rect.bottom += this.chat_vioce_rect.height() / 2;
//        }
//        float x = event.getRawX();
//        float y = event.getRawY();
//        switch (event.getAction()) {
//            case CHAT_CAMERA /*0*/:
//                initViewAndStartRecord();
//                break;
//            case CHAT_PHOTO /*1*/:
//                stopRecording();
//                break;
//            case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
//                if (!this.chat_vioce_rect.contains((int) x, (int) y)) {
//                    stopRecording();
//                    break;
//                }
//                break;
//            case ApiReqeust.PUT_METHOD /*3*/:
//                stopRecording();
//                break;
//        }
        return true;
    }

    private void initViewAndStartRecord() {
        this.tv_record_tip.setText(R.string.chat_end_voice_tip);
        this.tv_record_tip.setTextColor(getResources().getColor(R.color.chat_voice_color));
        this.threadVolume = new VoiceVolumeThread();
        this.threadVolume.start();
    }

    private void stopRecording() {
        this.tv_record_tip.setText(R.string.chat_start_voice_tip);
        this.tv_record_tip.setTextColor(getResources().getColor(R.color.comment_tip_txt_color));
        if (this.threadVolume != null) {
            this.threadVolume.exit();
            this.threadVolume = null;
        }
    }

//    private void sendMessage(RongIMClient client, String targetid, MessageContent msg) {
//        if (targetid == null) {
//            DebugUtils.error("targetid");
//        } else if (msg == null) {
//            DebugUtils.error(SocializeProtocolConstants.PROTOCOL_KEY_MSG);
//        } else if (client == null || targetid == null || msg == null) {
//            Toast.makeText(this, "\u53d1\u9001\u5931\u8d25", CHAT_PHOTO).show();
//        } else {
//            client.sendMessage(ConversationType.PRIVATE, targetid, msg, new AnonymousClass11(msg));
//        }
//    }
//
//    private void getConversationList() {
//        List<Conversation> mConversationList = MyContext.getInstance().mRongIMClient.getConversationList();
//        Log.e("jinkuan", new StringBuilder(String.valueOf(mConversationList.size())).toString());
//        for (int i = mConversationList.size() - 1; i >= 0; i--) {
//            Conversation conversation = (Conversation) mConversationList.get(i);
//            MessageContent msg = conversation.getLatestMessage();
//            if (msg instanceof TextMessage) {
//                TextMessage textMessage = (TextMessage) msg;
//            } else if (msg instanceof ImageMessage) {
//                ImageMessage imageMessage = (ImageMessage) msg;
//            } else if (msg instanceof VoiceMessage) {
//                VoiceMessage voiceMessage = (VoiceMessage) msg;
//            }
//            PrivateMeassageModel mode = new PrivateMeassageModel();
//            mode.setContent(((TextMessage) msg).getContent());
//            mode.setUid(conversation.getSenderUserId());
//            mode.setAvatartime(new StringBuilder(String.valueOf(conversation.getSentTime())).toString());
//            mode.setMessageid(new StringBuilder(String.valueOf(conversation.getLatestMessageId())).toString());
//            mode.setType(Constant.SYSTEM_UID);
//            this.messageList.add(mode);
//            this.adapter.setList(this.messageList);
//            MessageContent msgContent = conversation.getLatestMessage();
//            Log.e("jinkuan", ("senderId:" + conversation.getSenderUserId() + "sendername:" + conversation.getSenderUserName() + "time:" + conversation.getSentTime() + "draft:" + conversation.getDraft() + "targetid:" + conversation.getTargetId() + "message:" + new String(conversation.getLatestMessage().encode())));
//        }
//    }
}
