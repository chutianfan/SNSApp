package com.gitrose.mobile.view;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.gitrose.mobile.R;
import com.gitrose.mobile.view.FaceItemView.IFaceClickBack;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
import java.util.List;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.FaceUtils;

public class FaceView extends LinearLayout {
    private int column;
    private Context context;
    private LinearLayout dotList;
    private EditText edit;
    private ArrayList<Integer> faceIds;
    private ArrayList<String> faceNames;
    private int page;
    List<Integer> pageIds;
    List<String> pageNames;
    private int pagesize;
    private int total;
    private ViewPager viewPager;

    /* renamed from: com.gitrose.mobile.view.FaceView.1 */
    class C05511 implements OnPageChangeListener {
        C05511() {
        }

        public void onPageSelected(int arg0) {
            FaceView.this.addDot(arg0);
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    }

    class ImagePagerAdapter extends PagerAdapter {

        /* renamed from: com.gitrose.mobile.view.FaceView.ImagePagerAdapter.1 */
        class C05521 implements IFaceClickBack {
            C05521() {
            }

            public void delFace() {
                String msg = FaceView.this.edit.getText().toString();
                //DebugUtils.syso("\u5220\u9664\u4e8b\u4ef6##" + msg);
                if (msg != null && msg.length() > 0) {
                    int startIndex = msg.lastIndexOf("]");
                    int endIndex = msg.lastIndexOf("[");
                    if (endIndex == 0 && startIndex == msg.length() - 1) {
                        //msg = BuildConfig.FLAVOR;
                    } else if (startIndex <= 0 || endIndex <= 0 || startIndex <= endIndex || startIndex != msg.length() - 1) {
                        msg = msg.substring(0, msg.length() - 1);
                    } else {
                        msg = msg.substring(0, endIndex);
                    }
                    FaceView.this.edit.setText(msg);
                    Selection.setSelection(FaceView.this.edit.getText(), FaceView.this.edit.getText().length());
                }
            }

            public void addFace(String face) {
                //DebugUtils.syso(face);
                if (FaceView.this.edit != null) {
                    FaceView.this.edit.setText(FaceView.this.edit.getText() + face);
                    Selection.setSelection(FaceView.this.edit.getText(), FaceView.this.edit.getText().length());
                }
            }
        }

        ImagePagerAdapter() {
        }

        public int getCount() {
            return FaceView.this.page;
        }

        public Object instantiateItem(View container, int position) {
            if (position < FaceView.this.page - 1) {
                FaceView.this.pageIds = FaceView.this.faceIds.subList(FaceView.this.pagesize * position, FaceView.this.pagesize * (position + 1));
                FaceView.this.pageNames = FaceView.this.faceNames.subList(FaceView.this.pagesize * position, FaceView.this.pagesize * (position + 1));
            } else {
                FaceView.this.pageIds = FaceView.this.faceIds.subList(FaceView.this.pagesize * position, FaceView.this.total);
                FaceView.this.pageNames = FaceView.this.faceNames.subList(FaceView.this.pagesize * position, FaceView.this.total);
            }
            FaceItemView faceItemView = new FaceItemView(FaceView.this.context, FaceView.this.pageIds, FaceView.this.pageNames, new C05521(), FaceView.this.column);
            ((ViewPager) container).addView(faceItemView);
            return faceItemView;
        }

        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((FaceItemView) object);
        }

        public void startUpdate(View container) {
        }

        public void finishUpdate(View container) {
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == ((FaceItemView) object);
        }

        public Parcelable saveState() {
            return null;
        }

        public void restoreState(Parcelable state, ClassLoader loader) {
        }
    }

    public FaceView(Context context, EditText editText) {
        super(context);
        this.page = 0;
        this.column = 7;
        this.pagesize = (this.column * 4) - 1;
        this.total = 0;
        this.pageIds = null;
        this.pageNames = null;
        this.context = context;
        this.edit = editText;
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.main_face_list, this);
        initView();
        addDot(0);
        this.viewPager.setAdapter(new ImagePagerAdapter());
        this.viewPager.setOnPageChangeListener(new C05511());
    }

    private void initView() {
        this.dotList = (LinearLayout) findViewById(R.id.main_face_ll_dot);
        this.viewPager = (ViewPager) findViewById(R.id.faceViewpager);
//        this.faceIds = FaceUtils.getFaceResource();
//        this.faceNames = FaceUtils.getFaceNameResource();
        this.total = this.faceIds.size();
        this.page = this.total % this.pagesize == 0 ? this.total / this.pagesize : (this.total / this.pagesize) + 1;
    }

    public void setEditText(EditText editText) {
        this.edit = editText;
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
