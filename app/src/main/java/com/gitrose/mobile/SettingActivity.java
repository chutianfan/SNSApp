package com.gitrose.mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.sea_monster.core.network.ApiReqeust;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.http.QGClient;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.SwitchButton;
import com.gitrose.mobile.view.SwitchButton.OnCheckedChangeListener;
//import com.umeng.fb.FeedbackAgent;
//import com.umeng.update.UmengUpdateAgent;
//import com.umeng.update.UmengUpdateListener;
//import com.umeng.update.UpdateResponse;
import java.io.File;
//import shouji.gexing.framework.utils.FileUtils;

public class SettingActivity extends BaseActivity implements OnClickListener {
    private TextView mCacheSizeTv;
    private BaseDialog mClearCacheDialog;
    private View mClearCacheView;
    private TextView mDialogCacheSizeTv;
    private TextView mVersionTv;

    private class GetCacheSizeTask extends AsyncTask<Void, Void, Double> {
        private GetCacheSizeTask() {
        }

        protected Double doInBackground(Void... params) {
            Double totalLength = Double.valueOf(0.0d);
            File cacheFile = ImageLoader.getInstance().getDiskCache().getDirectory();
            if (cacheFile.isDirectory()) {
                for (File file : cacheFile.listFiles()) {
                    totalLength = Double.valueOf(totalLength.doubleValue() + ((double) file.length()));
                }
            }
            return totalLength;
        }

        protected void onPostExecute(Double result) {
            super.onPostExecute(result);
//            String cacheSize = FileUtils.getFormatSize(result.doubleValue());
//            SettingActivity.this.mCacheSizeTv.setText(cacheSize);
//            SettingActivity.this.mDialogCacheSizeTv.setText("\u6e05\u9664\u5b58\u50a8\u7a7a\u95f4 ( " + cacheSize + " )");
        }
    }

    /* renamed from: com.gitrose.mobile.SettingActivity.1 */
    class C05291 implements OnCheckedChangeListener {
        C05291() {
        }

        public void onCheckedChanged(SwitchButton view, boolean isChecked) {
            PlaySound.getInstance(SettingActivity.this).setPalyedState(SettingActivity.this, isChecked);
        }
    }

    /* renamed from: com.gitrose.mobile.SettingActivity.2 */
//    class C05302 implements UmengUpdateListener {
//        C05302() {
//        }
//
//        public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
//            switch (updateStatus) {
//                case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
//                    UmengUpdateAgent.showUpdateDialog(SettingActivity.this, updateInfo);
//                    Toast.makeText(SettingActivity.this, "\u6b63\u5728\u68c0\u67e5\u66f4\u65b0...", 0).show();
//                case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                    Toast.makeText(SettingActivity.this, "\u5df2\u7ecf\u662f\u6700\u65b0\u7248\u672c", 0).show();
//                case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
//                    Toast.makeText(SettingActivity.this, "\u6ca1\u6709wifi\u8fde\u63a5\uff0c \u53ea\u5728wifi\u4e0b\u66f4\u65b0", 0).show();
//                case ApiReqeust.PUT_METHOD /*3*/:
//                    Toast.makeText(SettingActivity.this, "\u8d85\u65f6", 0).show();
//                default:
//            }
//        }
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ImageView backImg = (ImageView) getViewById(R.id.title_tv_left);
        SwitchButton mSoundSwitchBtton = (SwitchButton) getViewById(R.id.sbtn_sound);
        this.mVersionTv = (TextView) getViewById(R.id.tv_version);
        this.mCacheSizeTv = (TextView) getViewById(R.id.tv_cache_size);
        mSoundSwitchBtton.setChecked(PlaySound.getInstance(this).getPalyedState());
        mSoundSwitchBtton.setOnCheckedChangeListener(new C05291());
        getCurrentVersion();
        initPop();
        backImg.setOnClickListener(this);
        new GetCacheSizeTask().execute(new Void[0]);
    }

    private void initPop() {
        this.mClearCacheDialog = new BaseDialog(this);
        this.mClearCacheView = getLayoutInflater().inflate(R.layout.dialog_base_layout, null);
        this.mClearCacheView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        this.mClearCacheView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
        this.mDialogCacheSizeTv = (TextView) this.mClearCacheView.findViewById(R.id.tv_tip_title);
    }

    private void getCurrentVersion() {
        try {
            this.mVersionTv.setText(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
            case R.id.rl_account_manger:
                startOtherActivity(AccountManagerActivity.class);
            case R.id.rl_bolck_private_letter:
                startBlockActivity("private");
            case R.id.rl_bolck_topic:
                startBlockActivity("topic");
            case R.id.rl_hiden_mode:
                startOtherActivity(ChoiseHiddenModeActivity.class);
            case R.id.rl_agreement:
                Intent i = new Intent(this, WebViewActivity.class);
                i.putExtra("web_url", ConstantURL.AGREEMENT_URL);
                startActivityForNew(i);
            case R.id.rl_function:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("isFunction", true);
                startActivityForNew(intent);
//            case R.id.rl_helper:
//                new FeedbackAgent(this).sync();
//                startOtherActivity(FeedBackActivity.class);
            case R.id.rl_clear_cache:
                this.mClearCacheDialog.show(this.mClearCacheView);
            case R.id.rl_version:
                checkVersion();
            case R.id.pop_tv_submit:
                clearCache();
                this.mClearCacheDialog.dismiss();
            case R.id.pop_tv_cancel:
                this.mClearCacheDialog.dismiss();
            default:
        }
    }

    private void checkVersion() {
//        UmengUpdateAgent.setUpdateAutoPopup(false);
//        UmengUpdateAgent.setUpdateOnlyWifi(false);
//        UmengUpdateAgent.setUpdateListener(new C05302());
//        UmengUpdateAgent.update(this);
    }

    private void startOtherActivity(Class<? extends Activity> clazz) {
        startActivityForNew(new Intent(this, clazz));
    }

    private void startBlockActivity(String status) {
        Intent i = new Intent(this, BlockListActivty.class);
        i.putExtra("status", status);
        startActivityForNew(i);
    }

    public void clearCache() {
//        Toast.makeText(this, "\u6e05\u7406\u7f13\u5b58\u4e2d...", 0).show();
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
        QGClient.getInstance().clearCache();
        this.mCacheSizeTv.setText("0KB");
    }
}
