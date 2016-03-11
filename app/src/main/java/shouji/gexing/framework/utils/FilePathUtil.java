package shouji.gexing.framework.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilePathUtil {
    static Context context;
    private static String externalStoragePrivateDirectory;
    static FstabReader fsReader;
    private static String internalStorageDirectory;
    private static int kOtherExternalStorageStateIdle;
    private static int kOtherExternalStorageStateUnable;
    private static int kOtherExternalStorageStateUnknow;
    private static String otherExternalStorageDirectory;
    private static int otherExternalStorageState;

    public static class FstabReader {
        final List<StorageInfo> storages;

        public FstabReader() {
            this.storages = new ArrayList();
            init();
        }

        public int size() {
            return this.storages == null ? 0 : this.storages.size();
        }

        public List<StorageInfo> getStorages() {
            return this.storages;
        }

        public void init() {
            Exception e;
            Throwable th;
            File file = new File("/system/etc/vold.fstab");
            if (file.exists()) {
                FileReader fr = null;
                BufferedReader br = null;
                try {
                    FileReader fr2 = new FileReader(file);
                    if (fr2 != null) {
                        try {
                            BufferedReader br2 = new BufferedReader(fr2);
                            try {
                                for (String s = br2.readLine(); s != null; s = br2.readLine()) {
                                    if (s.startsWith("dev_mount")) {
                                        String path = s.split("\\s")[2];
                                        StatFs stat = new StatFs(path);
                                        if (stat != null && stat.getAvailableBlocks() > 0) {
                                            StorageInfo storage = new StorageInfo(path, (long) (stat.getAvailableBlocks() * stat.getBlockSize()), (long) (stat.getBlockCount() * stat.getBlockSize()));
                                            this.storages.add(storage);
                                        }
                                    }
                                }
                                br = br2;
                            } catch (Exception e2) {
                                e = e2;
                                br = br2;
                                fr = fr2;
                            } catch (Throwable th2) {
                                th = th2;
                                br = br2;
                                fr = fr2;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            fr = fr2;
                            try {
                                e.printStackTrace();
                                if (fr != null) {
                                    try {
                                        fr.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                if (br != null) {
                                    try {
                                        br.close();
                                    } catch (IOException e42) {
                                        e42.printStackTrace();
                                        return;
                                    }
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                if (fr != null) {
                                    try {
                                        fr.close();
                                    } catch (IOException e422) {
                                        e422.printStackTrace();
                                    }
                                }
                                if (br != null) {
                                    try {
                                        br.close();
                                    } catch (IOException e4222) {
                                        e4222.printStackTrace();
                                    }
                                }
                                throw th3;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            fr = fr2;
                            if (fr != null) {
                                fr.close();
                            }
                            if (br != null) {
                                br.close();
                            }
                            throw th4;
                        }
                    }
                    if (fr2 != null) {
                        try {
                            fr2.close();
                        } catch (IOException e42222) {
                            e42222.printStackTrace();
                        }
                    }
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e422222) {
                            e422222.printStackTrace();
                        }
                    }
                } catch (Exception e5) {
                    e = e5;
                    e.printStackTrace();
                    if (fr != null) {
//                        fr.close();
                    }
                    if (br != null) {
//                        br.close();
                    }
                }
            }
        }
    }

    private static class ShellThread extends Thread {
        private String cmd;
        private boolean isReturn;
        private boolean isSuccess;

        public boolean isReturn() {
            return this.isReturn;
        }

        public boolean isSuccess() {
            return this.isSuccess;
        }

        public ShellThread(String cmd) {
            this.cmd = cmd;
        }

        public void run() {
            try {
                try {
                    this.isSuccess = Runtime.getRuntime().exec(this.cmd).waitFor() == 0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.isSuccess = true;
            } catch (InterruptedException e2) {
            }
            this.isReturn = true;
        }
    }

    static class StorageInfo implements Comparable<StorageInfo> {
        private long availableSpace;
        private String path;
        private long totalSpace;

        StorageInfo(String path, long availableSpace, long totalSpace) {
            this.path = path;
            this.availableSpace = availableSpace;
            this.totalSpace = totalSpace;
        }

        public int compareTo(StorageInfo another) {
            if (another != null && this.totalSpace - another.totalSpace <= 0) {
                return -1;
            }
            return 1;
        }

        long getAvailableSpace() {
            return this.availableSpace;
        }

        long getTotalSpace() {
            return this.totalSpace;
        }

        String getPath() {
            return this.path;
        }
    }

    static void init(Context cxt) {
        context = cxt;
    }

    public static long getExternaltStorageAvailableSpace() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return 0;
        }
        StatFs statfs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) statfs.getBlockSize()) * ((long) statfs.getAvailableBlocks());
    }

    public static long getExternaltStorageTotalSpace() {
        StatFs statfs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) statfs.getBlockSize()) * ((long) statfs.getBlockCount());
    }

    public static long getSdcard2StorageAvailableSpace() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return 0;
        }
        String path = getSdcard2StorageDirectory();
        if (!new File(path).exists()) {
            return 0;
        }
        StatFs statfs = new StatFs(path);
        return ((long) statfs.getBlockSize()) * ((long) statfs.getAvailableBlocks());
    }

    public static long getSdcard2StorageTotalSpace() {
        String path = getSdcard2StorageDirectory();
        if (!new File(path).exists()) {
            return 0;
        }
        StatFs statfs = new StatFs(path);
        return ((long) statfs.getBlockSize()) * ((long) statfs.getAvailableBlocks());
    }

    public static long getEmmcStorageAvailableSpace() {
        String path = getEmmcStorageDirectory();
        if (!new File(path).exists()) {
            return 0;
        }
        StatFs statfs = new StatFs(path);
        return ((long) statfs.getBlockSize()) * ((long) statfs.getAvailableBlocks());
    }

    public static long getEmmcStorageTotalSpace() {
        String path = getEmmcStorageDirectory();
        if (!new File(path).exists()) {
            return 0;
        }
        StatFs statfs = new StatFs(path);
        return ((long) statfs.getBlockSize()) * ((long) statfs.getBlockCount());
    }

    static {
        fsReader = null;
        otherExternalStorageDirectory = null;
        kOtherExternalStorageStateUnknow = -1;
        kOtherExternalStorageStateUnable = 0;
        kOtherExternalStorageStateIdle = 1;
        otherExternalStorageState = kOtherExternalStorageStateUnknow;
        internalStorageDirectory = "/mnt/internal";
    }

    public static long getOtherExternaltStorageAvailableSpace() {
        if (!Environment.getExternalStorageState().equals("mounted") || otherExternalStorageState == kOtherExternalStorageStateUnable) {
            return 0;
        }
        if (otherExternalStorageDirectory == null) {
            getOtherExternalStorageDirectory();
        }
        if (otherExternalStorageDirectory == null) {
            return 0;
        }
        StatFs statfs = new StatFs(otherExternalStorageDirectory);
        return ((long) statfs.getBlockSize()) * ((long) statfs.getAvailableBlocks());
    }

    public static String getOtherExternalStorageDirectory() {
        if (otherExternalStorageState == kOtherExternalStorageStateUnable) {
            return null;
        }
        if (otherExternalStorageState == kOtherExternalStorageStateUnknow) {
            FstabReader fsReader = new FstabReader();
            if (fsReader.size() <= 0) {
                otherExternalStorageState = kOtherExternalStorageStateUnable;
                return null;
            }
            List<StorageInfo> storages = fsReader.getStorages();
            long availableSpace = 104857600;
            String path = null;
            for (int i = 0; i < storages.size(); i++) {
                StorageInfo info = (StorageInfo) storages.get(i);
                if (info.getAvailableSpace() > availableSpace) {
                    availableSpace = info.getAvailableSpace();
                    path = info.getPath();
                }
            }
            otherExternalStorageDirectory = path;
            if (otherExternalStorageDirectory != null) {
                otherExternalStorageState = kOtherExternalStorageStateIdle;
            } else {
                otherExternalStorageState = kOtherExternalStorageStateUnable;
            }
        }
        return otherExternalStorageDirectory;
    }

    public static long getInternalStorageAvailableSpace() {
        long blockSize = 0;
        long availableBlocks = 0;
        try {
            StatFs stat = new StatFs(getInternalStorageDirectory());
            blockSize = (long) stat.getBlockSize();
            availableBlocks = (long) stat.getAvailableBlocks();
        } catch (Exception e) {
        }
        return blockSize * availableBlocks;
    }

    public static long getInternalStorageTotalSpace() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) stat.getBlockCount()) * ((long) stat.getBlockSize());
    }

    public static final String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory() + File.separator;
    }

    public static final String getExternalStoragePublicDirectory(String type) {
        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
    }

    public static final String getSdcard2StorageDirectory() {
        return "/mnt/sdcard2";
    }

    public static final String getEmmcStorageDirectory() {
        return "/mnt/emmc";
    }

    static String getExternalPrivateFilesDirectory() {
        if (externalStoragePrivateDirectory == null) {
            externalStoragePrivateDirectory = context.getExternalFilesDir(null).getAbsolutePath();
        }
        return externalStoragePrivateDirectory;
    }

    public static final String getInternalStorageDirectory() {
        try {
            if (TextUtils.isEmpty(internalStorageDirectory)) {
                File file = context.getFilesDir();
                if (file != null) {
                    internalStorageDirectory = file.getAbsolutePath();
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    runShellScriptForWait("chmod 705 " + internalStorageDirectory);
                }
            }
        } catch (Exception e) {
        }
        return internalStorageDirectory;
    }

    public static boolean isInternalStoragePath(String path) {
        String rootPath = getInternalStorageDirectory();
        if (path == null || !path.startsWith(rootPath)) {
            return false;
        }
        return true;
    }

    public static String getFileName(String file) {
        if (file == null) {
            return null;
        }
        return file.substring(file.lastIndexOf("/") + 1);
    }

    public static boolean runShellScriptForWait(String cmd) throws SecurityException {
        ShellThread thread = new ShellThread(cmd);
        thread.setDaemon(true);
        thread.start();
        int k = 0;
        while (!thread.isReturn()) {
            int k2 = k + 1;
            if (k >= 20) {
                k = k2;
                break;
            }
            try {
                Thread.sleep(50);
                k = k2;
            } catch (InterruptedException e) {
                e.printStackTrace();
                k = k2;
            }
        }
        if (k >= 20) {
            thread.interrupt();
        }
        return thread.isSuccess();
    }
}
