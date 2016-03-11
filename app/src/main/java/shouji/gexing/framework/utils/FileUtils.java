package shouji.gexing.framework.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.tencent.TIMGroupManager;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
//import org.apache.http.util.EncodingUtils;
import qalsdk.BaseConstants;

public class FileUtils {
    public static String SDPATH() {
        return Environment.getExternalStorageDirectory() + "/";
    }

//    public static String openAssetFile(Context ctx, String file_path) {
//        String res = BaseConstants.ah;
//        try {
//            InputStream in = ctx.getResources().getAssets().open(file_path);
//            byte[] buffer = new byte[in.available()];
//            in.read(buffer);
//            res = EncodingUtils.getString(buffer, AsyncHttpResponseHandler.DEFAULT_CHARSET);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return res;
//    }

    public static void copyFile(File src, File dst) throws IOException {
//        InputStream in = new FileInputStream(src);
//        OutputStream out = new FileOutputStream(dst);
//        byte[] buf = new byte[TIMGroupManager.TIM_GET_GROUP_BASE_INFO_FLAG_NOTIFICATION];
//        while (true) {
//            int len = in.read(buf);
//            if (len <= 0) {
//                in.close();
//                out.close();
//                return;
//            }
//            out.write(buf, 0, len);
//        }
    }

    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] files = file.listFiles();
        for (File deleteFile : files) {
            deleteFile(deleteFile);
        }
        return false;
    }

    public static File createSDDir(String dirName) {
        File dir = new File(SDPATH() + dirName);
        return !dir.mkdirs() ? dir : null;
    }

    public static boolean saveFileOnDeviceInternalStorage(Context context, String filename, String string) {
        try {
            FileOutputStream outputStream = context.openFileOutput(filename, 0);
            outputStream.write(string.getBytes());
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static String readFileOnDeviceInternalStorage(Context context, String filename) {
        String content = BaseConstants.ah;
        try {
            FileInputStream inputStream = context.openFileInput(filename);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
//            content = EncodingUtils.getString(buffer, AsyncHttpResponseHandler.DEFAULT_CHARSET);
            inputStream.close();
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] readInputStream(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[TIMGroupManager.TIM_GET_GROUP_BASE_INFO_FLAG_NOTIFICATION];
//        while (true) {
//            try {
//                int len = inputStream.read(buffer);
//                if (len == -1) {
//                    break;
//                }
//                outputStream.write(buffer, 0, len);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        inputStream.close();
        return outputStream.toByteArray();
    }

    public static File getFile(Context context, byte[] bfile, String filePath, String fileName) {
        Exception e;
        Throwable th;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            FileOutputStream fos2;
            BufferedOutputStream bos2;
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file2 = new File(new StringBuilder(String.valueOf(filePath)).append("/").append(fileName).toString());
            try {
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                fos2 = new FileOutputStream(file2);
            } catch (Exception e2) {
                e = e2;
                file = file2;
                try {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (fos == null) {
                        return file;
                    }
                    try {
                        fos.close();
                        return file;
                    } catch (IOException e12) {
                        e12.printStackTrace();
                        return file;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e122) {
                            e122.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e1222) {
                            e1222.printStackTrace();
                        }
                    }
                    throw th2;
                }
            } catch (Throwable th3) {
                th = th3;
                file = file2;
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                throw th3;
            }
            try {
                bos2 = new BufferedOutputStream(fos2);
            } catch (Exception e3) {
                e = e3;
                file = file2;
                fos = fos2;
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                if (bos != null) {
                    bos.close();
                }
                if (fos == null) {
                    return file;
                }
                fos.close();
                return file;
            } catch (Throwable th4) {
                th = th4;
                file = file2;
                fos = fos2;
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                throw th4;
            }
            try {
                bos2.write(bfile);
                if (bos2 != null) {
                    try {
                        bos2.close();
                    } catch (IOException e12222) {
                        e12222.printStackTrace();
                    }
                }
                if (fos2 != null) {
                    try {
                        fos2.close();
                        fos = fos2;
                        bos = bos2;
                        return file2;
                    } catch (IOException e122222) {
                        e122222.printStackTrace();
                    }
                }
                fos = fos2;
                bos = bos2;
                return file2;
            } catch (Exception e4) {
                e = e4;
                file = file2;
                fos = fos2;
                bos = bos2;
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                if (bos != null) {
                    bos.close();
                }
                if (fos == null) {
                    return file;
                }
                fos.close();
                return file;
            } catch (Throwable th5) {
                th = th5;
                file = file2;
                fos = fos2;
                bos = bos2;
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                throw th5;
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            if (bos != null) {
//                bos.close();
            }
            if (fos == null) {
                return file;
            }
//            fos.close();
            return file;
        }
    }

    public static String getFormatSize(double size) {
        double kiloByte = size / 1024.0d;
        if (kiloByte < 1.0d) {
            return new StringBuilder(String.valueOf(size)).append("KB").toString();
        }
        double megaByte = kiloByte / 1024.0d;
        if (megaByte < 1.0d) {
            return new StringBuilder(String.valueOf(new BigDecimal(Double.toString(kiloByte)).setScale(2, 4).toPlainString())).append("KB").toString();
        }
        double gigaByte = megaByte / 1024.0d;
        if (gigaByte < 1.0d) {
            return new StringBuilder(String.valueOf(new BigDecimal(Double.toString(megaByte)).setScale(2, 4).toPlainString())).append("MB").toString();
        }
        double teraBytes = gigaByte / 1024.0d;
        if (teraBytes < 1.0d) {
            return new StringBuilder(String.valueOf(new BigDecimal(Double.toString(gigaByte)).setScale(2, 4).toPlainString())).append("GB").toString();
        }
        return new StringBuilder(String.valueOf(new BigDecimal(teraBytes).setScale(2, 4).toPlainString())).append("TB").toString();
    }

    public static String getSavePath(Context context, long saveSize) {
        FilePathUtil.init(context);
        String savePath;
        File saveFile;
        if (FilePathUtil.getExternaltStorageAvailableSpace() > saveSize) {
            savePath = FilePathUtil.getExternalStorageDirectory();
            saveFile = new File(savePath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
                return savePath;
            } else if (saveFile.isDirectory()) {
                return savePath;
            } else {
                saveFile.delete();
                saveFile.mkdirs();
                return savePath;
            }
        } else if (FilePathUtil.getSdcard2StorageAvailableSpace() > saveSize) {
            savePath = FilePathUtil.getSdcard2StorageDirectory();
            saveFile = new File(savePath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
                return savePath;
            } else if (saveFile.isDirectory()) {
                return savePath;
            } else {
                saveFile.delete();
                saveFile.mkdirs();
                return savePath;
            }
        } else if (FilePathUtil.getEmmcStorageAvailableSpace() > saveSize) {
            savePath = FilePathUtil.getEmmcStorageDirectory();
            saveFile = new File(savePath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
                return savePath;
            } else if (saveFile.isDirectory()) {
                return savePath;
            } else {
                saveFile.delete();
                saveFile.mkdirs();
                return savePath;
            }
        } else if (FilePathUtil.getOtherExternaltStorageAvailableSpace() > saveSize) {
            savePath = FilePathUtil.getOtherExternalStorageDirectory();
            saveFile = new File(savePath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
                return savePath;
            } else if (saveFile.isDirectory()) {
                return savePath;
            } else {
                saveFile.delete();
                saveFile.mkdirs();
                return savePath;
            }
        } else if (FilePathUtil.getInternalStorageAvailableSpace() > saveSize) {
            return FilePathUtil.getInternalStorageDirectory() + File.separator;
        } else {
            return context.getCacheDir().getPath();
        }
    }
}
