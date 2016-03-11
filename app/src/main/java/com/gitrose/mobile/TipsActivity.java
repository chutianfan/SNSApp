package com.gitrose.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.gitrose.mobile.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends BaseActivity {
    private int currentItem;
    private ViewPagerAdapter mAdapter;
    private GestureDetector mDetector;
    private TipsGestureListener mGestureListener;
    private List<Integer> mList;
    private ViewPager plugins_viewpager;

    /* renamed from: com.gitrose.mobile.TipsActivity.1 */
    class C03261 implements OnTouchListener {
        C03261() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (TipsActivity.this.currentItem == TipsActivity.this.mList.size() - 1) {
                return TipsActivity.this.mDetector.onTouchEvent(event);
            }
            return false;
        }
    }

    class TipsGestureListener extends SimpleOnGestureListener {
        TipsGestureListener() {
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (velocityX >= 0.0f) {
                return false;
            }
            TipsActivity.this.goTo();
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.TipsActivity.2 */
    class C05332 implements OnPageChangeListener {
        C05332() {
        }

        public void onPageSelected(int arg0) {
            TipsActivity.this.currentItem = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    }

    class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        /* renamed from: com.gitrose.mobile.TipsActivity.ViewPagerAdapter.1 */
        class C03271 implements OnClickListener {
            C03271() {
            }

            public void onClick(View v) {
                if (TipsActivity.this.currentItem == ViewPagerAdapter.this.getCount() - 1) {
                    TipsActivity.this.goTo();
                }
            }
        }

        ViewPagerAdapter() {
            this.inflater = TipsActivity.this.getLayoutInflater();
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        public void finishUpdate(View container) {
        }

        public int getCount() {
            return TipsActivity.this.mList == null ? 0 : TipsActivity.this.mList.size();
        }

        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = this.inflater.inflate(R.layout.plugins_tips_page, view, false);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.update_tips_image);
            imageView.setImageResource(((Integer) TipsActivity.this.mList.get(position)).intValue());
            imageView.setOnClickListener(new C03271());
            ((ViewPager) view).addView(imageLayout, 0);
            return imageLayout;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        public Parcelable saveState() {
            return null;
        }

        public void startUpdate(View container) {
        }
    }

    public TipsActivity() {
        this.mList = new ArrayList();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugins_tips);
        this.mList.add(Integer.valueOf(R.drawable.tips_1));
        this.mList.add(Integer.valueOf(R.drawable.tips_2));
        this.mList.add(Integer.valueOf(R.drawable.tips_3));
        this.mList.add(Integer.valueOf(R.drawable.tips_4));
        this.mAdapter = new ViewPagerAdapter();
        this.plugins_viewpager = (ViewPager) findViewById(R.id.plugins_viewpager);
        this.plugins_viewpager.setAdapter(this.mAdapter);
        this.mGestureListener = new TipsGestureListener();
        this.mDetector = new GestureDetector(this.mGestureListener);
        this.plugins_viewpager.setOnTouchListener(new C03261());
        this.plugins_viewpager.setOnPageChangeListener(new C05332());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goTo() {
        finish();
        startActivityForNew(new Intent(this, LoginActivity.class));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
