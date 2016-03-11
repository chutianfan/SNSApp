package com.gitrose.mobile.view;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.gitrose.mobile.R;
import com.gitrose.mobile.TopicDetailActivity;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.application.ActivityManager;
import com.gitrose.mobile.view.EmotionItemView.IFaceClickBack;
import java.util.ArrayList;
import java.util.List;
import qalsdk.BaseConstants;

public class TopicEmotionView extends LinearLayout {
    private int column;
    private Context context;
    private LinearLayout dotList;
    private List<String> emotionPages;
    private ArrayList<String> emotions;
    private int page;
    private int pagesize;
    private RelativeLayout rl_emotion_bottom;
    private int total;
    private ViewPager viewPager;

    /* renamed from: com.gitrose.mobile.view.TopicEmotionView.1 */
    class C14781 implements OnPageChangeListener {
        C14781() {
        }

        public void onPageSelected(int arg0) {
            TopicEmotionView.this.addDot(arg0);
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    }

    class ImagePagerAdapter extends PagerAdapter {

        /* renamed from: com.gitrose.mobile.view.TopicEmotionView.ImagePagerAdapter.1 */
        class C14791 implements IFaceClickBack {
            C14791() {
            }

            public void delFace() {
            }

            public void addFace(String face) {
                Activity activity = ActivityManager.getScreenManager().currentActivity();
                if (activity == null || !activity.getComponentName().getClassName().equals("com.gitrose.mobile.TopicDetailPagerActivity")) {
                    if (!(activity == null || !activity.getComponentName().getClassName().equals("com.gitrose.mobile.TopicDetailActivity") || TopicDetailActivity.getInstance() == null)) {
//                        TopicDetailActivity.getInstance().sendComment(face, BaseConstants.ah);
                    }
                } else if (TopicDetailPagerActivity.getInstance() != null) {
                    TopicDetailPagerActivity.getInstance().sendComment(face, BaseConstants.ah);
                }
                TopicEmotionView.this.rl_emotion_bottom.setVisibility(View.GONE);
            }
        }

        ImagePagerAdapter() {
        }

        public int getCount() {
            return TopicEmotionView.this.page;
        }

        public Object instantiateItem(View container, int position) {
            if (position < TopicEmotionView.this.page - 1) {
                TopicEmotionView.this.emotionPages = TopicEmotionView.this.emotions.subList(TopicEmotionView.this.pagesize * position, TopicEmotionView.this.pagesize * (position + 1));
            } else {
                TopicEmotionView.this.emotionPages = TopicEmotionView.this.emotions.subList(TopicEmotionView.this.pagesize * position, TopicEmotionView.this.total);
            }
            EmotionItemView emotionItemView = new EmotionItemView(TopicEmotionView.this.context, TopicEmotionView.this.emotionPages, new C14791(), TopicEmotionView.this.column);
            ((ViewPager) container).addView(emotionItemView);
            return emotionItemView;
        }

        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((EmotionItemView) object);
        }

        public void startUpdate(View container) {
        }

        public void finishUpdate(View container) {
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == ((EmotionItemView) object);
        }

        public Parcelable saveState() {
            return null;
        }

        public void restoreState(Parcelable state, ClassLoader loader) {
        }
    }

    public TopicEmotionView(Context context) {
        super(context);
        this.page = 0;
        this.column = 4;
        this.pagesize = this.column * 2;
        this.total = 0;
    }

    public TopicEmotionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.page = 0;
        this.column = 4;
        this.pagesize = this.column * 2;
        this.total = 0;
    }

    public TopicEmotionView(Context context, RelativeLayout rl_emotion_bottom, ArrayList<String> emotions) {
        super(context);
        this.page = 0;
        this.column = 4;
        this.pagesize = this.column * 2;
        this.total = 0;
        this.context = context;
        this.rl_emotion_bottom = rl_emotion_bottom;
        this.emotions = emotions;
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.topic_detail_emotion_list, this);
        initView();
        addDot(0);
        this.viewPager.setAdapter(new ImagePagerAdapter());
        this.viewPager.setOnPageChangeListener(new C14781());
    }

    private void initView() {
        this.dotList = (LinearLayout) findViewById(R.id.main_face_ll_dot);
        this.viewPager = (ViewPager) findViewById(R.id.faceViewpager);
        this.total = this.emotions.size();
        this.page = this.total % this.pagesize == 0 ? this.total / this.pagesize : (this.total / this.pagesize) + 1;
    }

    private void addDot(int p) {
        if (p < this.page) {
            this.dotList.removeAllViews();
            for (int i = 0; i < this.page; i++) {
                ImageView iv = new ImageView(this.context);
                LayoutParams lp = new LayoutParams(-2, -2);
                lp.setMargins(5, 5, 5, 5);
                iv.setLayoutParams(lp);
                if (p == i) {
                    iv.setImageResource(R.drawable.face_dot_color);
                } else {
                    iv.setImageResource(R.drawable.face_dot_gray);
                }
                this.dotList.addView(iv);
            }
        }
    }
}
