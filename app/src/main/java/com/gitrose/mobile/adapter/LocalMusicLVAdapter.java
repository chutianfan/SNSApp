package com.gitrose.mobile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.gitrose.mobile.R;
import com.gitrose.mobile.model.Audio;
import java.util.ArrayList;
import qalsdk.BaseConstants;

public class LocalMusicLVAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Audio> list;
    private MediaPlayer mediaPlayer;

    /* renamed from: com.gitrose.mobile.adapter.LocalMusicLVAdapter.1 */
    class C08491 implements OnClickListener {
        C08491() {
        }

        public void onClick(View v) {
            int index = ((Integer) v.getTag()).intValue();
            Audio audio = (Audio) LocalMusicLVAdapter.this.list.get(index);
            ImageView imageView = (ImageView) v;
            if (imageView.isSelected()) {
                imageView.setImageResource(R.drawable.play_local_music_bg);
                imageView.setSelected(false);
                LocalMusicLVAdapter.this.stopMusic();
                return;
            }
            imageView.setImageResource(R.drawable.stop_local_music_bg);
            imageView.setSelected(true);
            LocalMusicLVAdapter.this.playMusic(index);
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.LocalMusicLVAdapter.2 */
    class C08502 implements OnSeekBarChangeListener {
        C08502() {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            int index = ((Integer) seekBar.getTag()).intValue();
            Audio audio = (Audio) LocalMusicLVAdapter.this.list.get(index);
            int beginTime = (audio.getDuration() * seekBar.getProgress()) / 100;
            audio.setStartTime(beginTime);
            LinearLayout layout = (LinearLayout) seekBar.getParent();
            ((TextView) ((RelativeLayout) layout.getChildAt(1)).getChildAt(0)).setText(LocalMusicLVAdapter.this.parseTime((long) beginTime));
            LocalMusicLVAdapter.this.playMusic(index);
            ImageView imageView = (ImageView) ((LinearLayout) layout.getParent()).findViewById(R.id.music_play_iv);
            imageView.setImageResource(R.drawable.stop_local_music_bg);
            imageView.setSelected(true);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Audio audio = (Audio) LocalMusicLVAdapter.this.list.get(((Integer) seekBar.getTag()).intValue());
            int beginTime = (audio.getDuration() * seekBar.getProgress()) / 100;
            audio.setStartTime(beginTime);
            ((TextView) ((RelativeLayout) ((LinearLayout) seekBar.getParent()).getChildAt(1)).getChildAt(0)).setText(LocalMusicLVAdapter.this.parseTime((long) beginTime));
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.LocalMusicLVAdapter.3 */
    class C08513 implements OnCompletionListener {
        private final /* synthetic */ int val$index;

        C08513(int i) {
            this.val$index = i;
        }

        public void onCompletion(MediaPlayer mp) {
            LocalMusicLVAdapter.this.playMusic(this.val$index);
        }
    }

    private class ViewHolder {
        TextView musicArtistTV;
        TextView musicDurationTV;
        TextView musicEndTV;
        LinearLayout musicHiddenLL;
        TextView musicNameTV;
        ImageView musicPlayIV;
        SeekBar musicSeekBar;
        TextView musicStartTV;

        private ViewHolder() {
        }
    }

    public LocalMusicLVAdapter(Context context, ArrayList<Audio> list) {
        this.context = context;
        this.list = list;
        this.mediaPlayer = new MediaPlayer();
    }

    public int getCount() {
        return this.list == null ? 0 : this.list.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.local_music_lv_item, null);
            holder = new ViewHolder();
            holder.musicNameTV = (TextView) convertView.findViewById(R.id.music_name_tv);
            holder.musicArtistTV = (TextView) convertView.findViewById(R.id.music_artist_tv);
            holder.musicDurationTV = (TextView) convertView.findViewById(R.id.music_duration_tv);
            holder.musicPlayIV = (ImageView) convertView.findViewById(R.id.music_play_iv);
            holder.musicStartTV = (TextView) convertView.findViewById(R.id.music_start_tv);
            holder.musicEndTV = (TextView) convertView.findViewById(R.id.music_end_tv);
            holder.musicHiddenLL = (LinearLayout) convertView.findViewById(R.id.hidden_ll);
            holder.musicSeekBar = (SeekBar) convertView.findViewById(R.id.music_seekbar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Audio audio = (Audio) this.list.get(position);
        holder.musicNameTV.setText(audio.getTitle());
        holder.musicArtistTV.setText(audio.getArtist());
        holder.musicDurationTV.setText(parseTime((long) audio.getDuration()));
        if (audio.isSelected()) {
            holder.musicHiddenLL.setVisibility(View.VISIBLE);
            holder.musicPlayIV.setVisibility(View.VISIBLE);
        } else {
            holder.musicHiddenLL.setVisibility(View.GONE);
            holder.musicPlayIV.setVisibility(View.INVISIBLE);
        }
        holder.musicStartTV.setText(parseTime((long) audio.getStartTime()));
        holder.musicEndTV.setText(parseTime((long) audio.getDuration()));
        holder.musicPlayIV.setTag(Integer.valueOf(position));
        if (holder.musicPlayIV.getVisibility() == View.INVISIBLE) {
            holder.musicNameTV.setTextColor(Color.parseColor("#333333"));
            holder.musicArtistTV.setTextColor(Color.parseColor("#999999"));
            holder.musicDurationTV.setTextColor(Color.parseColor("#999999"));
        } else {
            holder.musicNameTV.setTextColor(Color.parseColor("#53cbab"));
            holder.musicArtistTV.setTextColor(Color.parseColor("#b6e6d7"));
            holder.musicDurationTV.setTextColor(Color.parseColor("#b6e6d7"));
        }
        holder.musicPlayIV.setOnClickListener(new C08491());
        holder.musicSeekBar.setProgress((audio.getStartTime() * 100) / audio.getDuration());
        holder.musicSeekBar.setTag(Integer.valueOf(position));
        holder.musicSeekBar.setOnSeekBarChangeListener(new C08502());
        return convertView;
    }

    public void stopMusic() {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
        }
    }

    public void playMusic(int index) {
        Audio audio = (Audio) this.list.get(index);
        int beginTime = audio.getStartTime();
        String url = audio.getUrl();
        try {
            this.mediaPlayer.reset();
            this.mediaPlayer.setDataSource(url);
            this.mediaPlayer.prepare();
            this.mediaPlayer.seekTo(beginTime);
            this.mediaPlayer.start();
            this.mediaPlayer.setOnCompletionListener(new C08513(index));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String parseTime(long time) {
        if (time == 0) {
            return "00:00";
        }
        long hour = time / 3600000;
        long minute = (time - (((60 * hour) * 60) * 1000)) / 60000;
        long second = ((time - (((60 * hour) * 60) * 1000)) - ((60 * minute) * 1000)) / 1000;
        if (second >= 60) {
            second %= 60;
            minute += second / 60;
        }
        if (minute >= 60) {
            minute %= 60;
            hour += minute / 60;
        }
        String sh = " ";
        String sm = " ";
        String ss = " ";
        if (hour < 10) {
            sh = new StringBuilder(BaseConstants.f3860s).append(String.valueOf(hour)).toString();
        } else {
            sh = String.valueOf(hour);
        }
        if (minute < 10) {
            sm = new StringBuilder(BaseConstants.f3860s).append(String.valueOf(minute)).toString();
        } else {
            sm = String.valueOf(minute);
        }
        if (second < 10) {
            ss = new StringBuilder(BaseConstants.f3860s).append(String.valueOf(second)).toString();
        } else {
            ss = String.valueOf(second);
        }
        if ("00".equals(sh)) {
            return new StringBuilder(String.valueOf(sm)).append(":").append(ss).toString();
        }
        return new StringBuilder(String.valueOf(sh)).append(":").append(sm).append(":").append(ss).toString();
    }

    public MediaPlayer getMediaPlay() {
        return this.mediaPlayer;
    }
}
