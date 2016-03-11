package com.gitrose.mobile.application;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Process;
import android.preference.PreferenceActivity;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;
//import com.baidu.location.BDGeofence;
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.GeofenceClient;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.location.LocationClientOption.LocationMode;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.sea_monster.core.exception.InternalException;
//import com.tencent.android.tpush.XGIOperateCallback;
//import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
//import com.tencent.connect.auth.QQAuth;
//import com.tencent.tauth.Tencent;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.C0306R;
import com.gitrose.mobile.auth.UserAuth;
import com.gitrose.mobile.http.QGClient;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.TokenModle;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.provider.RMprovider;
import com.gitrose.mobile.view.CustomImageDownaloder;
//import com.umeng.common.util.FileUtils;
//import io.rong.imlib.AnnotationNotFoundException;
//import io.rong.imlib.RongIMClient;
//import io.rong.message.BuildConfig;
//import io.rong.message.CommandNotificationMessage;
//import io.rong.message.ContactNotificationMessage;
//import io.rong.message.ImageMessage;
//import io.rong.message.ProfileNotificationMessage;
//import io.rong.message.RichContentMessage;
//import io.rong.message.TextMessage;
//import io.rong.message.VoiceMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
//import shouji.gexing.framework.utils.ContextUtils;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.StringUtils;

public class MyApplication extends Application {
    public static final String APP_KEY = "x4vkb1qpv62gk";
    private static MyApplication app;
    private double Latitude;
    private String LocationCity;
    private String LocationDistrict;
    private String LocationProvince;
    private double Longitude;
    public String cache_path;
    private boolean click_home;
    private CookieStore cookieStore;
    private List<Cookie> cookies;
    private int getRMTokenCount;
    private ImageLoaderConfiguration imageLoaderConfig;
    public boolean isFirstApp;
    private boolean isInit;
    private boolean isLogin;
    public boolean isPassAuth;
    private boolean isSendLocation;
    private boolean isSendSeccess;
    private boolean isfirstalert;
    public String login_uid;
    //public GeofenceClient mGeofenceClient;
    public DefaultHttpClient mHttpClient;
    //public LocationClient mLocationClient;
    //public MyLocationListener mMyLocationListener;
    //public QQAuth mQQAuth;
    private RequestQueue mRequestQueue;
    //public Tencent mTencent;
    private boolean need_cachePth;
    private int newsNum;
    private int privateNum;
    private boolean rc_is_connect;
    private boolean rc_is_error;
    public File themeFile;
    private TutuUsers userinfo;
    public boolean is_max_version;
    private ArrayList<TopicInfo> mPicList;

    /* renamed from: com.gitrose.mobile.application.MyApplication.2 */
//    class C05362 implements XGIOperateCallback {
//        C05362() {
//        }
//
//        public void onSuccess(Object arg0, int arg1) {
//            DebugUtils.error("XGPushManager onSuccess------");
//        }
//
//        public void onFail(Object arg0, int arg1, String arg2) {
//            DebugUtils.error("XGPushManager onFail------");
//        }
//    }

    /* renamed from: com.gitrose.mobile.application.MyApplication.3 */
//    class C05373 implements XGIOperateCallback {
//        C05373() {
//        }
//
//        public void onSuccess(Object arg0, int arg1) {
//            DebugUtils.error("XGPushManager onSuccess------setclient");
//        }
//
//        public void onFail(Object arg0, int arg1, String arg2) {
//            DebugUtils.error("XGPushManager onFail------");
//        }
//    }

    /* renamed from: com.gitrose.mobile.application.MyApplication.5 */
//    class C05385 implements XGIOperateCallback {
//        C05385() {
//        }
//
//        public void onSuccess(Object arg0, int arg1) {
//            DebugUtils.error("XGPushManager onSuccess------setclient");
//        }
//
//        public void onFail(Object arg0, int arg1, String arg2) {
//            DebugUtils.error("XGPushManager onFail------");
//        }
//    }

//    public class MyLocationListener implements BDLocationListener {
//        public void onReceiveLocation(BDLocation location) {
//            StringBuffer sb = new StringBuffer(FileUtils.f1479b);
//            sb.append("time : ");
//            sb.append(location.getTime());
//            sb.append("\nerror code : ");
//            sb.append(location.getLocType());
//            sb.append("\nlatitude : ");
//            sb.append(location.getLatitude());
//            sb.append("\nlongitude : ");
//            sb.append(location.getLongitude());
//            sb.append("\nradius : ");
//            sb.append(location.getRadius());
//            if (location.getLocType() == 61) {
//                sb.append("\nspeed : ");
//                sb.append(location.getSpeed());
//                sb.append("\nsatellite : ");
//                sb.append(location.getSatelliteNumber());
//                sb.append("\ndirection : ");
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());
//                sb.append(location.getDirection());
//            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());
//                sb.append("\noperationers : ");
//                sb.append(location.getOperators());
//            }
//            DebugUtils.error(sb.toString());
//            if (MyApplication.this.isSendSeccess) {
//                MyApplication.this.mLocationClient.stop();
//            }
//            MyApplication.this.setLatitude(location.getLatitude());
//            MyApplication.this.setLongitude(location.getLongitude());
//            MyApplication.this.setLocationCity(location.getCity());
//            MyApplication.this.setLocationProvince(location.getProvince());
//            MyApplication.this.setLocationDistrict(location.getDistrict());
//            DebugUtils.error("------isSendLocation:" + MyApplication.this.isSendLocation + "     isSendSeccess" + MyApplication.this.isSendSeccess);
//            if (!MyApplication.this.isSendLocation && !MyApplication.this.isSendSeccess) {
//                MyApplication.this.isSendLocation = true;
//                MyApplication.this.sendLoacation(new StringBuilder(String.valueOf(location.getLatitude())).toString(), new StringBuilder(String.valueOf(location.getLongitude())).toString(), new StringBuilder(String.valueOf(location.getRadius())).toString(), location.getAddrStr(), new StringBuilder(String.valueOf(location.getOperators())).toString());
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.application.MyApplication.1 */
    class C07171 extends QGHttpHandler<String> {
        C07171(Context $anonymous0, boolean $anonymous1) {
            super($anonymous0, $anonymous1);
        }

        public void onGetDataSuccess(String data) {
//            MyApplication.this.mLocationClient.stop();
//            MyApplication.this.isSendLocation = false;
//            MyApplication.this.isSendSeccess = true;
        }

        public void onFinish() {
            super.onFinish();
            MyApplication.this.isSendLocation = false;
        }
    }

    /* renamed from: com.gitrose.mobile.application.MyApplication.4 */
    class C07184 extends QGHttpHandler<TokenModle> {
        C07184(Context $anonymous0) {
            super($anonymous0);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            MyApplication.this.getRMTokenRequest();
        }

        public void onGetDataSuccess(TokenModle data) {
//            DebugUtils.error(data.getToken());
            MyContext.getInstance().setRMToken(data.getToken());
            MyContext.getInstance().connectRongyun();
        }
    }

    public MyApplication() {
        this.isfirstalert = true;
        this.isLogin = false;
        this.isFirstApp = false;
        //this.login_uid = BuildConfig.FLAVOR;
        this.newsNum = 0;
        this.privateNum = 0;
        this.isPassAuth = false;
        this.isSendLocation = false;
        this.isSendSeccess = false;
        this.click_home = false;
        this.rc_is_connect = false;
        this.rc_is_error = false;
        this.need_cachePth = false;
        this.isInit = true;
        this.cache_path = "/data/data/com.gitrose.mobile";
        this.getRMTokenCount = 3;
        this.is_max_version = true;
    }

    public void onCreate() {
        super.onCreate();
//        DebugUtils.error("Myapplication oncreate------");
//        DebugUtils.error(getCurProcessName(this));
        if (getCurProcessName(this) == null || getCurProcessName(this).equals(RMprovider.AUTHORITY)) {
            app = this;
            if (this.mRequestQueue == null) {
                DefaultHttpClient mDefaultHttpClient = new DefaultHttpClient();
                ClientConnectionManager mClientConnectionManager = mDefaultHttpClient.getConnectionManager();
                HttpParams mHttpParams = mDefaultHttpClient.getParams();
                this.mHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(mHttpParams, mClientConnectionManager.getSchemeRegistry()), mHttpParams);
                this.mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HttpClientStack(this.mHttpClient));
            }
            this.imageLoaderConfig = initImageLoaderConfig(this);
            ImageLoader.getInstance().init(this.imageLoaderConfig);
            registerXg();
//            this.mLocationClient = new LocationClient(getApplicationContext());
//            this.mMyLocationListener = new MyLocationListener();
//            this.mLocationClient.registerLocationListener(this.mMyLocationListener);
//            RongIMClient.init(this, APP_KEY, C0306R.drawable.ic_launcher);
//            try {
//                RongIMClient.registerMessageType(TextMessage.class);
//                RongIMClient.registerMessageType(VoiceMessage.class);
//                RongIMClient.registerMessageType(ImageMessage.class);
//                RongIMClient.registerMessageType(RichContentMessage.class);
//                RongIMClient.registerMessageType(ContactNotificationMessage.class);
//                RongIMClient.registerMessageType(ProfileNotificationMessage.class);
//                RongIMClient.registerMessageType(CommandNotificationMessage.class);
//            } catch (AnnotationNotFoundException e) {
//                e.printStackTrace();
//            }
            MyContext.getInstance().init(app);
            try {
                this.isPassAuth = UserAuth.getInstance().authUser(this);
            } catch (Exception e2) {
            }
        }
    }

    public static MyApplication getInstance() {
        return app;
    }

    private String getCurProcessName(Context context) {
        int pid = Process.myPid();
//        for (RunningAppProcessInfo appProcess : ((ActivityManager) context.getSystemService(Constants.FLAG_ACTIVITY_NAME)).getRunningAppProcesses()) {
//            if (appProcess.pid == pid) {
//                return appProcess.processName;
//            }
//        }
        return null;
    }

    public void initLocation() {
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationMode.Hight_Accuracy);
//        option.setCoorType(BDGeofence.COORD_TYPE_GCJ);
//        option.setScanSpan(0);
//        option.setIsNeedAddress(true);
//        this.mLocationClient.setLocOption(option);
//        this.mLocationClient.start();
    }

    public void stopLocation() {
//        if (this.mLocationClient != null && this.mLocationClient.isStarted()) {
//            this.mLocationClient.stop();
//        }
    }

    private void sendLoacation(String latitude, String longitude, String radius, String addr, String operationers) {
        QGHttpRequest.getInstance().uploadLocationRequest(this, latitude, longitude, radius, addr, operationers, new C07171(getApplicationContext(), false));
    }

    private void registerXg() {
//        XGPushManager.setTag(this, ContextUtils.getVersionName(this));
//        XGPushManager.registerPush(getApplicationContext(), new C05362());
    }

    public ImageLoaderConfiguration initImageLoaderConfig(Context context) {

        int memoryCacheSize;
        File path = StorageUtils.getCacheDirectory(context);
        com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache discCache = new com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache(StorageUtils.getCacheDirectory(getApplicationContext()));
        if (VERSION.SDK_INT >= 11) {
            this.is_max_version = true;
            memoryCacheSize = ((((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 6) * InternalException.DEF_NETWORK_CODE) * InternalException.DEF_NETWORK_CODE;
        } else {
            this.is_max_version = false;
            memoryCacheSize = 4194304;
        }
        return new Builder(context).threadPoolSize(5).threadPriority(3).memoryCacheSize(memoryCacheSize).memoryCache(new LRULimitedMemoryCache(memoryCacheSize)).denyCacheImageMultipleSizesInMemory().discCache(discCache).discCacheFileNameGenerator(new Md5FileNameGenerator()).imageDownloader(new CustomImageDownaloder(this)).discCacheSize(52428800).tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(new DisplayImageOptions.Builder().showImageForEmptyUri(17170445).showImageOnFail(17170445).cacheInMemory(true).cacheOnDisc(true).build()).writeDebugLogs().build();

    }

    public RequestQueue getmRequestQueue() {
        return this.mRequestQueue;
    }

    public void setCookieFromToken(CookieStore cookieStore) {
        this.mHttpClient.setCookieStore(cookieStore);
        setClientCookieFromHttpClient();
    }

    public void setClientCookieFromHttpClient() {
        getRMTokenRequest();
        this.cookieStore = this.mHttpClient.getCookieStore();
        QGClient.getInstance().mClient.setCookieStore(this.cookieStore);
        this.cookies = this.cookieStore.getCookies();
        Log.e("cookies---", this.cookies.toString());
//        if (this.userinfo != null && !StringUtils.isEmpty(this.userinfo.getUid())) {
//            XGPushManager.registerPush(getApplicationContext(), this.userinfo.getUid(), new C05373());
//        }
    }

    public void removeClientCookieFromHttpClient() {
        this.mHttpClient.setCookieStore(null);
        QGClient.getInstance().mClient.setCookieStore(null);
    }

    public List<Cookie> getCookies() {
        return this.cookies;
    }

    public boolean isIsfirstalert() {
        return this.isfirstalert;
    }

    public void setIsfirstalert(boolean isfirstalert) {
        this.isfirstalert = isfirstalert;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return this.isLogin;
    }

    public TutuUsers getUserinfo() {
        return this.userinfo;
    }

    public void setUserinfo(TutuUsers userinfo) {
        this.userinfo = userinfo;
        if (userinfo != null) {
            this.login_uid = userinfo.getUid();
        }
    }

    public CookieStore getCookieStore() {
        return this.cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        if (this.userinfo != null) {
            this.login_uid = this.userinfo.getUid();
        }
    }

    public void getRMTokenRequest() {
        this.getRMTokenCount--;
        if (this.getRMTokenCount >= 0) {
            //DebugUtils.error("cookies", "lixiangwei--------\n" + this.mHttpClient.getCookieStore().toString());
            TutuUsers user = getInstance().getUserinfo();
            if (user != null && user.getUid() != null) {
                String token = user.getRctoken();
                if (token != null) {
                    MyContext.getInstance().setRMToken(token);
                    MyContext.getInstance().connectRongyun();
                } else if (getInstance().mHttpClient.getCookieStore() != null) {
                    QGHttpRequest.getInstance().getRongyunToken(this, new C07184(this));
                }
            }
        }
    }

    public void recoverData(CookieStore cookieStor, TutuUsers userinfo) {
        if (!(userinfo == null || cookieStor == null)) {
            try {
                this.cookieStore = cookieStor;
                this.mHttpClient.setCookieStore(cookieStor);
                QGClient.getInstance().mClient.setCookieStore(cookieStor);
                setUserinfo(userinfo);
                setIsLogin(true);
                this.cookies = cookieStor.getCookies();
                Log.e("cookies---", this.cookies.toString());
            } catch (Exception e) {
                return;
            }
        }
//        if (userinfo != null && !StringUtils.isEmpty(userinfo.getUid())) {
//            XGPushManager.registerPush(getApplicationContext(), userinfo.getUid(), new C05385());
//        }
    }

    public void existRongIMClient() {
//        if (MyContext.getInstance().getRongIMClient() != null) {
//            MyContext.getInstance().getRongIMClient().disconnect(false);
//            DebugUtils.error("RongIMClient().disconnect--------");
//        }
    }

    public int getNewsNum() {
        return this.newsNum;
    }

    public void setNewsNum(int newsNum) {
        this.newsNum = newsNum;
    }

    public int getPrivateNum() {
        return this.privateNum;
    }

    public void setPrivateNum(int privateNum) {
        this.privateNum = privateNum;
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public double getLongitude() {
        return this.Longitude;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
    }

    public boolean isClick_home() {
        return this.click_home;
    }

    public void setClick_home(boolean click_home) {
        this.click_home = click_home;
    }

    public String getLocationCity() {
        return this.LocationCity;
    }

    public void setLocationCity(String locationCity) {
        this.LocationCity = locationCity;
    }

    public String getLocationProvince() {
        return this.LocationProvince;
    }

    public void setLocationProvince(String locationProvince) {
        this.LocationProvince = locationProvince;
    }

    public String getLocationDistrict() {
        return this.LocationDistrict;
    }

    public void setLocationDistrict(String locationDistrict) {
        this.LocationDistrict = locationDistrict;
    }

    public boolean isRc_is_connect() {
        return this.rc_is_connect;
    }

    public void setRc_is_connect(boolean rc_is_connect) {
        this.rc_is_connect = rc_is_connect;
    }

    public boolean isRc_is_error() {
        return this.rc_is_error;
    }

    public void setRc_is_error(boolean rc_is_error) {
        this.rc_is_error = rc_is_error;
    }

    public boolean isNeed_cachePth() {
        return this.need_cachePth;
    }

    public void setNeed_cachePth(boolean need_cachePth) {
        this.need_cachePth = need_cachePth;
    }

    public boolean isInit() {
        return this.isInit;
    }

    public void setInit(boolean isInit) {
        this.isInit = isInit;
    }

    public ArrayList<TopicInfo> getmPicList() {
        return this.mPicList;
    }

    public void setmPicList(ArrayList<TopicInfo> mPicList) {
        this.mPicList = mPicList;
    }
}
