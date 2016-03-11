package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.gitrose.mobile.adapter.CommentInputAdapter;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.CommentBox;
import java.util.ArrayList;

public class CommentInputActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
    private CommentInputAdapter commentInput;
    private GridView inputGrid;
    private ArrayList<String> inputList;

    /* renamed from: com.gitrose.mobile.CommentInputActivity.1 */
    class C16291 extends QGHttpHandler<CommentBox> {
        C16291(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(CommentBox data) {
            CommentInputActivity.this.inputList = data.getList();
            CommentInputActivity.this.commentInput.setList(data.getList());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_input_page);
        initView();
        initAdapter();
        initData();
    }

    private void initView() {
        findViewById(R.id.iv_close_input).setOnClickListener(this);
        this.inputGrid = (GridView) findViewById(R.id.gv_comment_input);
        this.inputGrid.setOnItemClickListener(this);
    }

    private void initData() {
        QGHttpRequest.getInstance().getCommentBox(this, new C16291(this));
    }

    private void initAdapter() {
        this.commentInput = new CommentInputAdapter(this);
        this.inputGrid.setAdapter(this.commentInput);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (this.inputList != null && this.inputList.size() > 0) {
            Intent data = new Intent();
            data.putExtra("emotionNAME", (String) this.inputList.get(position));
            setResult(-1, data);
            finish();
            animationForOTop();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_input:
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
