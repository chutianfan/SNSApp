package com.gitrose.mobile.voice;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import shouji.gexing.framework.utils.DebugUtils;

public class VoicePlayManager {
    private static MediaPlayer mMp;

    /* renamed from: com.gitrose.mobile.voice.VoicePlayManager.1 */
    class C03741 implements OnPreparedListener {
        C03741() {
        }

        public void onPrepared(MediaPlayer mp) {
            VoicePlayManager.mMp.start();
        }
    }

    /* renamed from: com.gitrose.mobile.voice.VoicePlayManager.2 */
    class C03752 implements OnErrorListener {
        C03752() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            VoicePlayManager.mMp.release();
            VoicePlayManager.mMp = null;
            return false;
        }
    }

    /* renamed from: com.gitrose.mobile.voice.VoicePlayManager.3 */
    class C03763 implements OnCompletionListener {
        private final /* synthetic */ Context val$context;

        C03763(Context context) {
            this.val$context = context;
        }

        public void onCompletion(MediaPlayer mp) {
            VoicePlayManager.mMp.release();
            ((AudioManager) this.val$context.getSystemService(Context.AUDIO_SERVICE)).setMode(0);
        }
    }

    static {
        mMp = null;
    }

    public static boolean isPlay() {
        boolean z = false;
        try {
            if (mMp != null) {
                z = mMp.isPlaying();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            mMp = null;
        }
        return z;
    }

    public static void voiceStop() {
        try {
            mMp.stop();
            mMp.release();
        } catch (Exception e) {
        }
        mMp = null;
    }

    public static void voicePause() {
        if (mMp != null) {
            try {
                mMp.pause();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                mMp.release();
                mMp = null;
            }
        }
    }

    public static void voiceResume() {
        if (mMp != null) {
            try {
                mMp.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                mMp.release();
                mMp = null;
            }
        }
    }

    public static void voicePlay(Context context, Uri uri) {
        if (mMp != null) {
            mMp.release();
        }
        mMp = new MediaPlayer();
        mMp.setAudioStreamType(3);
//        mMp.setOnPreparedListener(new C03741());
//        mMp.setOnErrorListener(new C03752());
//        mMp.setOnCompletionListener(new C03763(context));
        boolean b = false;
        try {
            if (delEx(uri) != null) {
                mMp.setDataSource(new FileInputStream(new File(delEx(uri))).getFD());
            }
            b = true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        if (b) {
            mMp.prepareAsync();
            return;
        }
        mMp.release();
        mMp = null;
    }

    public static String delEx(Uri uri) {
        if (uri == null) {
            return null;
        }
        String path = uri.toString().substring(7);
        //DebugUtils.error("debug", path);
        return path;
    }
}
