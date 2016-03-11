package com.gitrose.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.gitrose.greendao.TopicInfo;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.weixin.controller.UMWXHandler;

public class ShareUtils {
    public static void shareApp(Activity activity, TopicInfo topicInfo, Bitmap bitmap) {
        UMImage umImage = null;
        if (bitmap != null) {
            umImage = new UMImage((Context) activity, bitmap);
        }
        Activity activity2 = activity;
        //setShare(activity2, Constant.PREFENCES_NAME, topicInfo.getContent(), umImage, UMServiceFactory.getUMSocialService("com.umeng.share"), Constant.PREFENCES_NAME, 0);
    }

    private static void setShare(Activity activity, String title, String targeturl, UMImage umImage, UMSocialService mController, String shareContent, int type) {
        mController.setShareContent(shareContent);
        String appID = "wx8fa99b405996914a";
        String AppSecret = "779948ea2c933e2e1a6d65b48b680763";
//        new UMWXHandler(activity, appID, AppSecret).addToSocialSDK();
//        UMWXHandler wxCircleHandler = new UMWXHandler(activity, appID, AppSecret);
//        wxCircleHandler.setToCircle(true);
//        wxCircleHandler.addToSocialSDK();
        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT, SHARE_MEDIA.EMAIL);
        mController.getConfig().setPlatformOrder(SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.SMS);
        mController.openShare(activity, false);
    }
}
