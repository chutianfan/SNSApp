package com.gitrose.mobile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.gexing.tutu.jni.MediaEditor;
//import com.tencent.connect.share.QzoneShare;
import com.gitrose.mobile.adapter.LocalMusicLVAdapter;
import com.gitrose.mobile.avsdk.control.Util;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.Audio;
import com.gitrose.mobile.utils.FileUtil;
import com.gitrose.mobile.view.MakeVideoDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import shouji.gexing.framework.utils.UiUtils;

public class AudioListActicity extends Activity implements OnClickListener {
    private boolean canSendToTarget;
    private Handler handle;
    private boolean hasPauseMusic;
    private boolean isFirstInit;
    private boolean isWorkingSubMusic;
    private int lastClickPosition;
    private View lastClickView;
    private LocalMusicLVAdapter mAdapter;
    private ArrayList<Audio> mAudioList;
    private MakeVideoDialog mDialog;
    private ListView mListView;
    private MediaEditor mMediaEditor;
    private int videoTime;

    /* renamed from: com.gitrose.mobile.AudioListActicity.1 */
    class C05971 extends Handler {
        C05971() {
        }

        public void handleMessage(Message msg) {
            if (AudioListActicity.this.canSendToTarget) {
                String musicPath = (String) msg.obj;
                Intent data = new Intent();
                data.putExtra("musicPath", musicPath);
                AudioListActicity.this.setResult(10000, data);
                AudioListActicity.this.finish();
                AudioListActicity.this.overridePendingTransition(R.anim.main_translatexf100to0, R.anim.main_translatex0to100);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.AudioListActicity.2 */
    class C05982 implements Runnable {
        C05982() {
        }

        public void run() {
            if (AudioListActicity.this.mAudioList.size() == 0) {
                Toast.makeText(AudioListActicity.this.getApplicationContext(), AudioListActicity.this.getResources().getString(R.string.not_found_music), Toast.LENGTH_LONG).show();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.AudioListActicity.3 */
    class C05993 implements OnItemClickListener {
        C05993() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (AudioListActicity.this.lastClickPosition != position) {
                if (AudioListActicity.this.mAdapter.getMediaPlay().isPlaying()) {
                    AudioListActicity.this.mAdapter.getMediaPlay().stop();
                }
                View hiddenView = view.findViewById(R.id.hidden_ll);
                if (!(hiddenView == null || hiddenView.getVisibility() == View.VISIBLE)) {
                    hiddenView.setVisibility(View.VISIBLE);
                }
                AudioListActicity.this.changeStatus(view, true);
                ((Audio) AudioListActicity.this.mAudioList.get(position)).setSelected(true);
                if (AudioListActicity.this.lastClickPosition != -1) {
                    ((Audio) AudioListActicity.this.mAudioList.get(AudioListActicity.this.lastClickPosition)).setSelected(false);
                }
                int first = AudioListActicity.this.mListView.getFirstVisiblePosition();
                int last = AudioListActicity.this.mListView.getLastVisiblePosition();
                if (AudioListActicity.this.lastClickPosition >= first && AudioListActicity.this.lastClickPosition <= last) {
                    View contentView = AudioListActicity.this.mListView.getChildAt(AudioListActicity.this.lastClickPosition - first);
                    if (contentView != null) {
                        contentView.findViewById(R.id.hidden_ll).setVisibility(View.GONE);
                        AudioListActicity.this.changeStatus(contentView, false);
                    }
                }
            }
            AudioListActicity.this.lastClickPosition = position;
        }
    }

    /* renamed from: com.gitrose.mobile.AudioListActicity.4 */
    class C06004 implements OnDismissListener {
        C06004() {
        }

        public void onDismiss(DialogInterface dialog) {
            AudioListActicity.this.canSendToTarget = false;
        }
    }

    /* renamed from: com.gitrose.mobile.AudioListActicity.5 */
    class C06025 implements Runnable {
        private final /* synthetic */ int val$endTime;
        private final /* synthetic */ String val$inMeidaFilePath;
        private final /* synthetic */ int val$startTime;

        /* renamed from: com.gitrose.mobile.AudioListActicity.5.1 */
        class C06011 implements Runnable {
            C06011() {
            }

            public void run() {
                Toast.makeText(AudioListActicity.this, "\u6682\u4e0d\u652f\u6301\u6b64\u97f3\u9891\u683c\u5f0f", Toast.LENGTH_LONG).show();
                AudioListActicity.this.mDialog.dismiss();
            }
        }

        C06025(String str, int i, int i2) {
            this.val$inMeidaFilePath = str;
            this.val$startTime = i;
            this.val$endTime = i2;
        }

        public void run() {
            if (AudioListActicity.this.mMediaEditor == null) {
                AudioListActicity.this.mMediaEditor = new MediaEditor();
            }
            String outMediaFilePath = new StringBuilder(String.valueOf(FileUtil.getSaveDir(AudioListActicity.this))).append("/").append(System.currentTimeMillis()).append(".adts").toString();
            int status = AudioListActicity.this.mMediaEditor.StartAudioClip(this.val$inMeidaFilePath, outMediaFilePath, (float) this.val$startTime, (float) this.val$endTime);
            File file = new File(outMediaFilePath);
            boolean isOK = false;
            if (file != null && file.exists() && file.length() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID > 8) {
                isOK = true;
            }
            if (isOK) {
                Message message = new Message();
                message.obj = outMediaFilePath;
                AudioListActicity.this.handle.sendMessage(message);
            } else {
                AudioListActicity.this.runOnUiThread(new C06011());
            }
            AudioListActicity.this.isWorkingSubMusic = false;
        }
    }

    public AudioListActicity() {
        this.lastClickPosition = -1;
        this.videoTime = 0;
        this.isWorkingSubMusic = false;
        this.canSendToTarget = true;
        this.isFirstInit = true;
        this.hasPauseMusic = false;
        this.handle = new C05971();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music_list);
        this.videoTime = getIntent().getIntExtra("videoTime", 0);
        this.mListView = (ListView) findViewById(R.id.music_list_lv);
        this.mAudioList = new ArrayList();
        this.mAudioList.addAll(getALLAudio());
        this.mAdapter = new LocalMusicLVAdapter(this, this.mAudioList);
        this.mListView.setAdapter(this.mAdapter);
        setOnListViewItemClickListener();
        this.handle.postDelayed(new C05982(), 500);
    }

    protected void onPause() {
        super.onPause();
        if (this.mAdapter == null) {
            return;
        }
        if (this.mAdapter.getMediaPlay().isPlaying()) {
            this.mAdapter.getMediaPlay().stop();
            this.hasPauseMusic = true;
            return;
        }
        this.hasPauseMusic = false;
    }

    protected void onResume() {
        super.onResume();
        if (!(this.isFirstInit || this.mAdapter == null || this.lastClickPosition == -1 || !this.hasPauseMusic)) {
            this.mAdapter.getMediaPlay();
            this.mAdapter.playMusic(this.lastClickPosition);
        }
        this.isFirstInit = false;
    }

    private void setOnListViewItemClickListener() {
        this.mListView.setOnItemClickListener(new C05993());
    }

    protected void changeStatus(View view, boolean b) {
        if (view != null) {
            TextView musicNameTV = (TextView) view.findViewById(R.id.music_name_tv);
            TextView musicArtistTV = (TextView) view.findViewById(R.id.music_artist_tv);
            TextView musicDuration = (TextView) view.findViewById(R.id.music_duration_tv);
            ImageView musicPlayIV = (ImageView) view.findViewById(R.id.music_play_iv);
            if (b) {
                musicNameTV.setTextColor(Color.parseColor("#53cbab"));
                musicArtistTV.setTextColor(Color.parseColor("#b6e6d7"));
                musicDuration.setTextColor(Color.parseColor("#b6e6d7"));
                musicPlayIV.setVisibility(View.VISIBLE);
                musicPlayIV.setImageResource(R.drawable.play_local_music_bg);
                musicPlayIV.setSelected(false);
                return;
            }
            musicNameTV.setTextColor(Color.parseColor("#333333"));
            musicArtistTV.setTextColor(Color.parseColor("#999999"));
            musicDuration.setTextColor(Color.parseColor("#999999"));
            musicPlayIV.setVisibility(View.GONE);
        }
    }

    private List<Audio> getALLAudio() {
        ArrayList<Audio> audios = new ArrayList();
        Cursor cursor = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, null, null, null, "title_key");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow("artist"));
                String url = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(Util.EXTRA_RECORDTIME));
                if (!(duration < 2000 || url == null || url.contains(Constant.PREFENCES_NAME) || url.contains("TuTu"))) {
                    audios.add(new Audio(title, artist, url, duration, false));
                }
            }
        }
        return audios;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_tv:
                beginSubMusic();
            case R.id.back_tv:
                backAction();
            default:
        }
    }

    private void showDialog() {
        this.mDialog = new MakeVideoDialog(this, 17);
        View view = LayoutInflater.from(this).inflate(R.layout.make_video_progress, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.make_video_progress_iv);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.mvlayout_ll);
        LayoutParams layoutParams = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.make_video_progress_width), getResources().getDimensionPixelOffset(R.dimen.make_video_progress_height));
        layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.action_bar_height) + ((UiUtils.getInstance(this).getmScreenWidth() - getResources().getDimensionPixelOffset(R.dimen.make_video_progress_height)) / 2);
        linearLayout.setLayoutParams(layoutParams);
        ((AnimationDrawable) imageView.getDrawable()).start();
        this.mDialog.setOnDismissListener(new C06004());
        this.mDialog.showFullScreen(view);
    }

    private void beginSubMusic() {
        if (this.lastClickPosition != -1) {
            Audio audio = (Audio) this.mAudioList.get(this.lastClickPosition);
            int startTime = audio.getStartTime();
            int musicDuration = audio.getDuration();
            if (musicDuration - startTime < 2000) {
                Toast.makeText(this, getResources().getString(R.string.choose_time_short), Toast.LENGTH_LONG).show();
                return;
            }
            int endTime = startTime + this.videoTime;
            if (endTime > musicDuration) {
                endTime = musicDuration;
            }
            if (!this.isWorkingSubMusic) {
                this.isWorkingSubMusic = true;
                this.canSendToTarget = true;
                showDialog();
                startSubMusic(audio.getUrl(), startTime, endTime);
            }
        } else if (this.mAudioList.size() == 0) {
            backAction();
        } else {
            Toast.makeText(this, getResources().getString(R.string.choose_music), Toast.LENGTH_LONG).show();
        }
    }

    private void startSubMusic(String inMeidaFilePath, int startTime, int endTime) {
        new Thread(new C06025(inMeidaFilePath, startTime, endTime)).start();
    }

    private void backAction() {
        finish();
        overridePendingTransition(R.anim.main_translatexf100to0, R.anim.main_translatex0to100);
    }

    public void onBackPressed() {
        backAction();
        super.onBackPressed();
    }

    protected void onDestroy() {
        MediaPlayer mediaPlayer = this.mAdapter.getMediaPlay();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
