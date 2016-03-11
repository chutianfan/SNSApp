package com.gitrose.mobile.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.mobile.R;
import com.gitrose.mobile.http.ConstantURL;
import java.util.List;

public class EmotionItemView extends RelativeLayout {
    private IFaceClickBack call;
    private Context context;
    private List<String> emotions;
    private ScrollGridView gv;

    /* renamed from: com.gitrose.mobile.view.EmotionItemView.1 */
    class C09251 implements OnItemClickListener {
        C09251() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (position >= 0 && position < EmotionItemView.this.emotions.size()) {
                EmotionItemView.this.call.addFace((String) EmotionItemView.this.emotions.get(position));
            }
        }
    }

    class FaceAdapter extends BaseAdapter {
        FaceAdapter() {
        }

        public int getCount() {
            if (EmotionItemView.this.emotions == null) {
                return 0;
            }
            int count = EmotionItemView.this.emotions.size();
            if (count % 8 != 0) {
                return count + (8 - (count % 8));
            }
            return count;
        }

        public Object getItem(int position) {
            return EmotionItemView.this.emotions == null ? null : (String) EmotionItemView.this.emotions.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(EmotionItemView.this.context).inflate(R.layout.topic_emotion_grid_item, null);
                viewHolder.imface = (ImageView) convertView.findViewById(R.id.main_news_face_iv_faceimg);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (position < EmotionItemView.this.emotions.size()) {
                viewHolder.imface.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(new StringBuilder(ConstantURL.TOPIC_DOWNLOAD_EMOTION).append((String) EmotionItemView.this.emotions.get(position)).append(".png").toString(), viewHolder.imface);
            } else {
                viewHolder.imface.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }

    public interface IFaceClickBack {
        void addFace(String str);

        void delFace();
    }

    private class ViewHolder {
        ImageView imface;

        private ViewHolder() {
        }
    }

    public EmotionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmotionItemView(Context context, List<String> emotionNames, IFaceClickBack iFaceClickBack, int column) {
        super(context);
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.topic_emotion_item, this);
        this.context = context;
        this.emotions = emotionNames;
        this.call = iFaceClickBack;
        this.gv = (ScrollGridView) findViewById(R.id.grapeGridview);
        this.gv.setNumColumns(column);
        this.gv.setHorizontalSpacing(10);
        this.gv.setVerticalSpacing(10);
        this.gv.setAdapter(new FaceAdapter());
        this.gv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        RegisterListener();
    }

    private void RegisterListener() {
        this.gv.setOnItemClickListener(new C09251());
    }
}
