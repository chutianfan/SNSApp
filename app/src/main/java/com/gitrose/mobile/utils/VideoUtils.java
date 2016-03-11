package com.gitrose.mobile.utils;

import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.gitrose.mobile.FFmpegPreviewActivity;
import java.io.File;

public class VideoUtils {
    public static Md5FileNameGenerator mGenerator;

    static {
        mGenerator = new Md5FileNameGenerator();
    }

    public static boolean renameAndMoveFile(Context context, File srcFile, String videoUrl) {
        return srcFile.renameTo(new File(getVideoAbsoluteFileNameByUrl(context, videoUrl)));
    }

    public static final String getVideoAbsoluteFileNameByUrl(Context context, String videoUrl) {
        return getVideoFilePathByName(context, mGenerator.generate(videoUrl));
    }

    public static final String getVideoFilePathByName(Context context, String fileName) {
        return FileUtil.getSaveDir(context) + File.separator + fileName + FFmpegPreviewActivity.END_FORMAT;
    }

    public static final String generateByUrl(String videoUrl) {
        return mGenerator.generate(videoUrl);
    }
}
