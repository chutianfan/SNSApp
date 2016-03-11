package com.gitrose.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.base.BaseUploadActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpHandlerAsyn;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

public class RegisterActivity1 extends BaseUploadActivity implements View.OnClickListener, BaseUploadActivity.IUploadCall {

    private EditText m_etEmailAddress;
    private EditText m_etNickName;
    private EditText m_etPassword;

    private ImageView m_deleteNickname;
    private ImageView m_deletePassword;
    private ImageView m_ivPrev;
    private ImageView m_ivWall;

    private TextView m_txtNext;

    private CircleImageView m_ivTakePhoto;

    private String m_strEmail;
    private String m_strIconFile;

    private BaseDialog mDialog;
    private BaseDialog mDialogDown;

    private View mTakePicDialogView;

    private File m_fIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        m_strEmail = getIntent().getStringExtra("email");
        InitView();
        modifyPicture(this, true);
    }

    private void InitView()
    {
        m_etEmailAddress = (EditText)findViewById(R.id.activity_login_et_phone);
        m_etNickName = (EditText)findViewById(R.id.activity_login_et_nickname);
        m_etPassword = (EditText)findViewById(R.id.activity_login_et_password);

        m_deleteNickname = (ImageView)findViewById(R.id.activity_login_nickname_iv_delete);
        m_deletePassword = (ImageView)findViewById(R.id.activity_login_pass_iv_delete);
        m_ivPrev = (ImageView)findViewById(R.id.title_tv_left);
        m_ivTakePhoto = (CircleImageView)findViewById(R.id.personal_info_head);
        m_ivWall = (ImageView)findViewById(R.id.img_wall);

        m_txtNext = (TextView)findViewById(R.id.activity_register_get_code_next);

        m_etEmailAddress.setText(m_strEmail);
        m_etEmailAddress.setEnabled(false);

        m_deleteNickname.setOnClickListener(this);
        m_deletePassword.setOnClickListener(this);
        m_ivPrev.setOnClickListener(this);
        m_ivTakePhoto.setOnClickListener(this);

        m_etNickName.requestFocus();

        this.m_etNickName.setOnFocusChangeListener(new nickNameFocusChange());
        this.m_etPassword.setOnFocusChangeListener(new passFocusChange());
        this.m_etNickName.addTextChangedListener(new nickNameTextChange());
        this.m_etPassword.addTextChangedListener(new passTextChange());

        m_txtNext.setOnClickListener(this);

        initDialog();
    }

    private void initDialog()
    {
        this.mDialog = new BaseDialog(this);
        this.mDialogDown = new BaseDialog(this, R.style.Transparent);
    }

    class nickNameTextChange implements TextWatcher {
        nickNameTextChange() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (m_etNickName.getText().toString().length() == 0) {
                m_deleteNickname.setVisibility(View.GONE);
            } else if (m_etNickName.getText().toString().length() > 0 && m_etNickName.hasFocus()) {
                m_deleteNickname.setVisibility(View.VISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class passTextChange implements TextWatcher {
        passTextChange() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (m_etPassword.getText().toString().length() == 0) {
                m_deletePassword.setVisibility(View.GONE);
            } else if (m_etPassword.getText().toString().length() > 0 && m_etPassword.hasFocus()) {
                m_deletePassword.setVisibility(View.VISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class nickNameFocusChange implements View.OnFocusChangeListener {
        nickNameFocusChange() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && m_etNickName.length() > 0) {
                m_deleteNickname.setVisibility(View.VISIBLE);
            } else if (!hasFocus || m_etNickName.length() == 0) {
                m_deleteNickname.setVisibility(View.GONE);
            }
        }
    }

    class passFocusChange implements View.OnFocusChangeListener {
        passFocusChange() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && m_etPassword.length() > 0) {
                m_deletePassword.setVisibility(View.VISIBLE);
            } else if (!hasFocus || m_etPassword.length() == 0) {
                m_deletePassword.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_login_nickname_iv_delete:
                this.m_etNickName.setText("");
                break;

            case R.id.activity_login_pass_iv_delete:
                this.m_etPassword.setText("");
                break;

            case R.id.title_tv_left:
                finish();
                animationForOld();
                break;

            case R.id.activity_register_get_code_next:
                try {
                    RegisterUser();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.personal_info_head:
                showPublishTopicDialog();
                break;

            case R.id.take_photo_close:
                mDialogDown.dismiss();
                break;

            case R.id.share_tv_block:   // 사진삭제버튼 클릭
                break;

            case R.id.share_tv_report:  // 사진찍기
                mDialogDown.dismiss();
                getPicFromCapture();
                break;

            case R.id.share_tv_fav:     // 라이브러리에서 사진선택.
                mDialogDown.dismiss();
                getPicFromContent();
                break;
        }
    }

    public void gpuback(File file) {
        ImageLoader.getInstance().displayImage("file://" + file.getAbsolutePath(), this.m_ivWall, Constant.AVATAR_OPTIONS);
        m_strIconFile = "file://" + file.getAbsolutePath();
        m_fIcon = file;
    }

    private void showPublishTopicDialog()
    {
        if (this.mTakePicDialogView == null) {
            this.mTakePicDialogView = View.inflate(this, R.layout.dialog_photo, null);

            this.mTakePicDialogView.findViewById(R.id.take_photo_close).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.share_tv_block).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.share_tv_report).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.share_tv_fav).setOnClickListener(this);
        }

        this.mDialogDown.show(this.mTakePicDialogView);
    }

    private void RegisterUser() throws FileNotFoundException {
        if(m_etNickName.getText().toString().isEmpty())
        {
            this.m_etNickName.requestFocus();
            this.m_etNickName.startAnimation(shakeAnimation(4));

            return;
        }

        if(m_etPassword.getText().toString().isEmpty())
        {
            this.m_etPassword.requestFocus();
            this.m_etPassword.startAnimation(shakeAnimation(4));

            return;
        }

        QGHttpRequest.getInstance().RegisterRequest(this, m_fIcon, m_strEmail, m_etNickName.getText().toString(), m_etPassword.getText().toString(), new RegisterUserHandler(this));
    }

    class RegisterUserHandler extends QGHttpHandlerAsyn<String> {

        public RegisterUserHandler(Context context) {
            super(context);
        }

        public void onGetDataSuccess(String s) {

            StartRegisterDetailActivity(s);

        }

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            super.onFailure(i, headers, s, throwable);
        }

        public void onFinish()
        {
            super.onFinish();
        }
    }

    private void StartRegisterDetailActivity(String strEmail)
    {
        Intent intent = new Intent(RegisterActivity1.this, RegisterActivity2.class);
        intent.putExtra("email", this.m_etEmailAddress.getText().toString());
        intent.putExtra("password", this.m_etPassword.getText().toString());
        intent.putExtra("iconfile", m_strIconFile);
        startActivity(intent);

        animationForNew();
    }
}
