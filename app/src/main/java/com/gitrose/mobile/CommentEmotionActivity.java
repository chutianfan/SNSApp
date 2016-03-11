package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import com.gitrose.mobile.adapter.CommentPicAdapter;
import com.gitrose.mobile.adapter.CommentWordAdapter;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.CommentEmotion;
import com.gitrose.mobile.view.SquareLayout;
import java.util.ArrayList;
import java.util.List;

public class CommentEmotionActivity extends BaseActivity implements OnClickListener {
    private CommentPicAdapter commentPic;
    private CommentWordAdapter commentWord;
    private OnItemClickListener emotionClick;
    private ArrayList<CommentEmotion> emotions;
    private GridView gv_comment_pic;
    private GridView gv_emotion;
    private OnItemClickListener picTitleClick;
    private int selectPos;

    /* renamed from: com.gitrose.mobile.CommentEmotionActivity.1 */
    class C06271 implements OnItemClickListener {
        C06271() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            CommentEmotionActivity.this.selectPos = position;
            CommentEmotion commentEmotion = (CommentEmotion) CommentEmotionActivity.this.emotions.get(position);
            CommentEmotionActivity.this.commentWord.setSelectedPosition(position);
            CommentEmotionActivity.this.commentWord.notifyDataSetChanged();
            CommentEmotionActivity.this.commentPic = new CommentPicAdapter(CommentEmotionActivity.this);
            CommentEmotionActivity.this.gv_comment_pic.setAdapter(CommentEmotionActivity.this.commentPic);
            CommentEmotionActivity.this.commentPic.setList(commentEmotion.getList());
        }
    }

    /* renamed from: com.gitrose.mobile.CommentEmotionActivity.2 */
    class C06282 implements OnItemClickListener {
        C06282() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent data = new Intent();
            ImageView imageView = (ImageView) ((SquareLayout) view).getChildAt(1);
            if (imageView.getDrawable() instanceof BitmapDrawable) {
                data.putExtra("reslutImage", ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                CommentEmotionActivity.this.setResult(-1, data);
                CommentEmotionActivity.this.finish();
                CommentEmotionActivity.this.animationForOTop();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.CommentEmotionActivity.3 */
    class C16283 extends QGHttpHandler<ArrayList<CommentEmotion>> {
        C16283(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(ArrayList<CommentEmotion> data) {
            CommentEmotionActivity.this.emotions = data;
            if (data != null && data.size() > 0) {
                int count = data.size();
                int width = CommentEmotionActivity.this.getResources().getInteger(R.integer.personal_follow_item_pic_widt_gv);
                CommentEmotionActivity.this.gv_emotion.setLayoutParams(new LayoutParams(count * width, CommentEmotionActivity.this.getResources().getDimensionPixelSize(R.dimen.action_bar_height)));
                CommentEmotionActivity.this.gv_emotion.setColumnWidth(width);
                CommentEmotionActivity.this.gv_emotion.setHorizontalSpacing(10);
                CommentEmotionActivity.this.gv_emotion.setVerticalSpacing(10);
                CommentEmotionActivity.this.gv_emotion.setStretchMode(GridView.STRETCH_SPACING);
                CommentEmotionActivity.this.gv_emotion.setNumColumns(count);
                CommentEmotionActivity.this.commentWord.setList((List) data);
                if (CommentEmotionActivity.this.emotions != null && CommentEmotionActivity.this.emotions.size() > 0) {
                    CommentEmotionActivity.this.commentPic.setList(((CommentEmotion) CommentEmotionActivity.this.emotions.get(0)).getList());
                }
            }
        }
    }

    public CommentEmotionActivity() {
        this.selectPos = 0;
        this.picTitleClick = new C06271();
        this.emotionClick = new C06282();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_emotion_page);
        initView();
        initAdapter();
        initData();
    }

    private void initView() {
        findViewById(R.id.iv_close_emotion).setOnClickListener(this);
        this.gv_emotion = (GridView) findViewById(R.id.gv_emotion);
        this.gv_emotion.setSelector(new ColorDrawable(0));
        this.gv_emotion.setOnItemClickListener(this.picTitleClick);
        this.gv_comment_pic = (GridView) findViewById(R.id.gv_comment_pic);
        this.gv_comment_pic.setOnItemClickListener(this.emotionClick);
    }

    private void initAdapter() {
        this.commentWord = new CommentWordAdapter(this);
        this.commentWord.setSelectedPosition(0);
        this.gv_emotion.setAdapter(this.commentWord);
        this.commentPic = new CommentPicAdapter(this);
        this.gv_comment_pic.setAdapter(this.commentPic);
    }

    private void initData() {
        QGHttpRequest.getInstance().getStickers(this, new C16283(this));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_emotion:
                finish();
                animationForOTop();
            default:
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        animationForOTop();
        return true;
    }
}
