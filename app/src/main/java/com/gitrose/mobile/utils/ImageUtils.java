package com.gitrose.mobile.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import java.io.File;

public class ImageUtils {
    public static final String getImageFileNameByUrl(Context ctx, String imgUrl) {
        File path = StorageUtils.getCacheDirectory(ctx);
        return path.getAbsolutePath() + File.separator + new Md5FileNameGenerator().generate(imgUrl);
    }

    public static final boolean renameAndMoveFile(Context ctx, File srcFile, String imgUrl) {
        return srcFile.renameTo(new File(getImageFileNameByUrl(ctx, imgUrl)));
    }

    public static final String getAvatarUrl(String uid, String avatarTime) {
        return new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(uid).append("/").append(avatarTime).append(Constant.HEAD_BIG_SIZE).toString();
    }

    public static final String getUserIconUrl(String strIcon)
    {
        return new StringBuffer(ConstantURL.USER_ICON_URL).append(strIcon).toString();
    }

    public static final String getAvatarUrl(String uid, String avatarTime, String size) {
        return new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(uid).append("/").append(avatarTime).append(size).toString();
    }

    public static Bitmap readLocalResourcesBitMap(Context context, int resId) {
        Options opt = new Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return BitmapFactory.decodeStream(context.getResources().openRawResource(resId), null, opt);
    }

    public static final String getLevelUrl(String level) {
        return new StringBuilder(ConstantURL.LEVEL_DOMAIN).append(level).append(".png").toString();
    }
}
