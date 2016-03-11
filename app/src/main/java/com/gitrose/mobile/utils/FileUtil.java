package com.gitrose.mobile.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
//import com.sea_monster.core.exception.InternalException;
import com.gitrose.mobile.constant.Constant;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.FileUtils;

public class FileUtil {
    private static final String NOMEDIA = ".nomedia";
    private static final String TAG = "FileUtil";
    private static final String TEMP_FILE_DIR_NAME = "tutu";

    public static InputStream getFileInputStream(String path) {
        try {
            return new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String createDirectory(File storageDirectory) {
        if (!storageDirectory.exists()) {
            Log.d(TAG, "Trying to create storageDirectory: " + String.valueOf(storageDirectory.mkdirs()));
            Log.d(TAG, "Exists: " + storageDirectory + " " + String.valueOf(storageDirectory.exists()));
            Log.d(TAG, "State: " + Environment.getExternalStorageState());
            Log.d(TAG, "Isdir: " + storageDirectory + " " + String.valueOf(storageDirectory.isDirectory()));
            Log.d(TAG, "Readable: " + storageDirectory + " " + String.valueOf(storageDirectory.canRead()));
            Log.d(TAG, "Writable: " + storageDirectory + " " + String.valueOf(storageDirectory.canWrite()));
            File tmp = storageDirectory.getParentFile();
            Log.d(TAG, "Exists: " + tmp + " " + String.valueOf(tmp.exists()));
            Log.d(TAG, "Isdir: " + tmp + " " + String.valueOf(tmp.isDirectory()));
            Log.d(TAG, "Readable: " + tmp + " " + String.valueOf(tmp.canRead()));
            Log.d(TAG, "Writable: " + tmp + " " + String.valueOf(tmp.canWrite()));
            tmp = tmp.getParentFile();
            Log.d(TAG, "Exists: " + tmp + " " + String.valueOf(tmp.exists()));
            Log.d(TAG, "Isdir: " + tmp + " " + String.valueOf(tmp.isDirectory()));
            Log.d(TAG, "Readable: " + tmp + " " + String.valueOf(tmp.canRead()));
            Log.d(TAG, "Writable: " + tmp + " " + String.valueOf(tmp.canWrite()));
        }
        File nomediaFile = new File(storageDirectory, NOMEDIA);
        if (!nomediaFile.exists()) {
            try {
                Log.d(TAG, "Created file: " + nomediaFile + " " + String.valueOf(nomediaFile.createNewFile()));
            } catch (IOException e) {
                Log.d(TAG, "Unable to create .nomedia file for some reason.", e);
                throw new IllegalStateException("Unable to create nomedia file.");
            }
        }
        if (storageDirectory.isDirectory() && nomediaFile.exists()) {
            return storageDirectory.getPath();
        }
        throw new RuntimeException("Unable to create storage directory and nomedia file.");
    }

    public static byte[] getByteFromUri(Uri uri) {
        InputStream input = getFileInputStream(uri.getPath());
        int count = 0;
        while (count == 0) {
            //count = input.available();
        }
        try {
            byte[] bytes = new byte[count];
            input.read(bytes);
            if (input == null) {
                return bytes;
            }
            try {
                input.close();
                return bytes;
            } catch (IOException e) {
                return bytes;
            }
        } catch (Exception e2) {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e3) {
                }
            }
            return null;
        } catch (Throwable th) {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e4) {
                }
            }
        }

        return  null;
    }

    public static Uri writeByte(Uri uri, byte[] data) {
        new File(uri.getPath().substring(0, uri.getPath().lastIndexOf("/"))).mkdirs();
        File file = new File(uri.getPath());
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            os.write(data);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(file);
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        byte[] buffer = new byte[InternalException.DEF_SERVICE_CODE];
//        while (true) {
//            int n = input.read(buffer);
//            if (-1 == n) {
//                return output.toByteArray();
//            }
//            output.write(buffer, 0, n);
//        }

        return null;
    }

    public static void createFile(String name, String dir) {
        File nomediaFile = new File(dir, name);
        if (!nomediaFile.exists()) {
            try {
                Log.d(TAG, "Created file: " + nomediaFile + " " + String.valueOf(nomediaFile.createNewFile()));
            } catch (IOException e) {
                Log.d(TAG, "Unable to create .nomedia file for some reason.", e);
                throw new IllegalStateException("Unable to create nomedia file.");
            }
        }
    }

    public static String getSaveDir(Context context) {
        String dir = FileUtils.getSavePath(context, Constant.FILE_SIZE);
        if (dir == null || BaseConstants.ah.equals(dir)) {
            return null;
        }
        String filePath;
        if (dir.endsWith("/")) {
            filePath = new StringBuilder(String.valueOf(dir)).append(TEMP_FILE_DIR_NAME).toString();
        } else {
            filePath = new StringBuilder(String.valueOf(dir)).append("/").append(TEMP_FILE_DIR_NAME).toString();
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    public static String getPhotoFileName() {
        return new StringBuilder(String.valueOf(new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss").format(new Date(System.currentTimeMillis())))).append(Constant.IMAGE_EXTENSION).toString();
    }
}
