package com.gitrose.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.view.BaseDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RegisterActivity2 extends BaseActivity implements View.OnClickListener {

    private ImageView m_ivPrev;

    private TextView m_txtNext;
    private TextView m_txtWelcome;

    private EditText m_etFullName;
    private EditText m_etPhoneNumber;

    private String m_strEmail;
    private String m_strPassword;
    private String m_strIconFile;

    private BaseDialog mDialogDown;

    private View mTakePicDialogView;

    private ImageView m_ivWall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        m_strEmail = getIntent().getStringExtra("email");
        m_strPassword = getIntent().getStringExtra("password");
        m_strIconFile = getIntent().getStringExtra("iconfile");

        InitView();

        if(m_strIconFile != null && !m_strIconFile.isEmpty())
            ImageLoader.getInstance().displayImage(m_strIconFile, this.m_ivWall, Constant.AVATAR_OPTIONS);


    }

    private void InitView()
    {
        m_ivPrev = (ImageView)findViewById(R.id.title_tv_left);

        m_txtNext = (TextView)findViewById(R.id.activity_register_get_code_next);

        m_etFullName = (EditText)findViewById(R.id.activity_register_get_code_et_phone);
        m_etPhoneNumber = (EditText)findViewById(R.id.activity_register_get_code_et_nickname);

        m_ivPrev.setOnClickListener(this);
        m_txtNext.setOnClickListener(this);
        m_etFullName.setOnClickListener(this);
        m_etPhoneNumber.setOnClickListener(this);

        m_ivWall = (ImageView)findViewById(R.id.img_wall);

        this.mDialogDown = new BaseDialog(this, R.style.Transparent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.title_tv_left:
                animationForOld();
                finish();
                break;

            case R.id.activity_register_get_code_next:
                RegisterDetailInfo();
                break;

            case R.id.take_photo_close:
            case R.id.activity_login_bt:
            {
                mDialogDown.dismiss();
                StartNewActivity();
            }
            break;
        }
    }

    private void StartNewActivity()
    {
        Intent intent = new Intent(RegisterActivity2.this, ReadyActivity.class);
        intent.putExtra("email", m_strEmail);
        intent.putExtra("password", m_strPassword);
        startActivity(intent);

        animationForNew();
        finish();
    }

    private void RegisterDetailInfo()
    {
        String strFullName = "";
        String strPhoneNumber = "";

        strFullName = m_etFullName.getText().toString();
        strPhoneNumber = m_etPhoneNumber.getText().toString();

        if(strFullName.isEmpty())
        {
            this.m_etFullName.setText("");
            this.m_etFullName.requestFocus();
            this.m_etFullName.setHint("전체이름을 입력하십시오.");
            this.m_etFullName.startAnimation(shakeAnimation(4));

            return;
        }

        if(strPhoneNumber.isEmpty())
        {
            this.m_etPhoneNumber.setText("");
            this.m_etPhoneNumber.requestFocus();
            this.m_etPhoneNumber.setHint("전화번호를 입력하십시오.");
            this.m_etPhoneNumber.startAnimation(shakeAnimation(4));

            return;
        }

        QGHttpRequest.getInstance().userInfoChangeRequest(this, m_strEmail,
                                    m_etFullName.getText().toString(), m_etPhoneNumber.getText().toString(), new UserInfoChangeHandler(this, false, null));
    }

    class UserInfoChangeHandler extends QGHttpHandler<String>{

        public UserInfoChangeHandler(Context context, boolean isShowLoading, ViewGroup container) {
            super(context, isShowLoading, container);
        }

        @Override
        public void onGetDataSuccess(String s) {

            ShowStart();

        }
    }

    private void ShowStart()
    {
        String strWelcomeText = m_strEmail + "님 회원가입을 축하합니다.";
        if (this.mTakePicDialogView == null) {
            this.mTakePicDialogView = View.inflate(this, R.layout.dialog_register_success, null);
            this.m_txtWelcome = (TextView)this.mTakePicDialogView.findViewById(R.id.txt_welcome_user);
            this.mTakePicDialogView.findViewById(R.id.take_photo_close).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.activity_login_bt).setOnClickListener(this);
        }

        m_txtWelcome.setText(strWelcomeText);

        this.mDialogDown.show(this.mTakePicDialogView);
    }
}
