package com.gitrose.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.media.SoundPool;
import com.android.volley.DefaultRetryPolicy;
import com.gitrose.mobile.C0306R;
import com.gitrose.mobile.constant.Constant;
import java.util.HashMap;
import java.util.Map;

public class PlaySound {
    public static final String SOUND_OPEN = "sound_open";
    public static final String IS_WIFI_PLAY_VIDEO = "isWifiPlayVideo";
    private static boolean mIsPaly;
    private static PlaySound playSound;
    private static Map<Integer, Integer> soundMap;
    private static SoundPool soundPool;

    public static PlaySound getInstance(Context context) {
        if (playSound == null) {
            playSound = new PlaySound();
            soundPool = new SoundPool(10, 1, 5);
            soundMap = new HashMap();
            soundMap.put(Integer.valueOf(C0306R.raw.camera), Integer.valueOf(soundPool.load(context, C0306R.raw.camera, 1)));
            soundMap.put(Integer.valueOf(C0306R.raw.comment), Integer.valueOf(soundPool.load(context, C0306R.raw.comment, 1)));
            soundMap.put(Integer.valueOf(C0306R.raw.delete), Integer.valueOf(soundPool.load(context, C0306R.raw.delete, 1)));
            soundMap.put(Integer.valueOf(C0306R.raw.open), Integer.valueOf(soundPool.load(context, C0306R.raw.open, 1)));
            soundMap.put(Integer.valueOf(C0306R.raw.push), Integer.valueOf(soundPool.load(context, C0306R.raw.push, 1)));
            soundMap.put(Integer.valueOf(C0306R.raw.refresh), Integer.valueOf(soundPool.load(context, C0306R.raw.refresh, 1)));
            soundMap.put(Integer.valueOf(C0306R.raw.send_message), Integer.valueOf(soundPool.load(context, C0306R.raw.send_message, 1)));
            soundMap.put(Integer.valueOf(C0306R.raw.send), Integer.valueOf(soundPool.load(context, C0306R.raw.send, 1)));
            mIsPaly = context.getSharedPreferences(Constant.PLAY_SOUND_PERFERENCE, 0).getBoolean(SOUND_OPEN, true);
        }
        return playSound;
    }

    public void toPlay(int soundId) {
        if (soundId != 0) {
            try {
                if (mIsPaly) {
                    soundPool.play(((Integer) soundMap.get(Integer.valueOf(soundId))).intValue(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                }
            } catch (Exception e) {
            }
        }
    }

    public void setPalyedState(Context context, boolean isOpen) {
        mIsPaly = isOpen;
        if (context != null) {
            Editor editor = context.getSharedPreferences(Constant.PLAY_SOUND_PERFERENCE, 0).edit();
            editor.putBoolean(SOUND_OPEN, isOpen);
            editor.commit();
        }
    }

    public boolean getPalyedState() {
        return mIsPaly;
    }
}
