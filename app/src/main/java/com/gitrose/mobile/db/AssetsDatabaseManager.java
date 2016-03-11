package com.gitrose.mobile.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
//import com.sea_monster.core.exception.InternalException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class AssetsDatabaseManager {
    private static AssetsDatabaseManager mInstance;
    private static String tag;
    private Context context;
    private Map<String, SQLiteDatabase> databases;

    static {
        tag = "AssetsDatabase";
        mInstance = null;
    }

    public static void initManager(Context context) {
        if (mInstance == null) {
            mInstance = new AssetsDatabaseManager(context);
        }
    }

    public static AssetsDatabaseManager getManager() {
        return mInstance;
    }

    private AssetsDatabaseManager(Context context) {
        this.databases = new HashMap();
        this.context = null;
        this.context = context;
    }

    public SQLiteDatabase getDatabase(String dbfile) {
        if (this.databases.get(dbfile) != null) {
            Log.i(tag, String.format("Return a database copy of %s", new Object[]{dbfile}));
            return (SQLiteDatabase) this.databases.get(dbfile);
        } else if (this.context == null) {
            return null;
        } else {
            Log.i(tag, String.format("Create database %s", new Object[]{dbfile}));
            String spath = getDatabaseFilepath();
            String sfile = getDatabaseFile(dbfile);
            File file = new File(sfile);
            SharedPreferences dbs = this.context.getSharedPreferences(AssetsDatabaseManager.class.toString(), 0);
            if (!(dbs.getBoolean(dbfile, false) && file.exists())) {
                file = new File(spath);
                if (!file.exists() && !file.mkdirs()) {
                    Log.i(tag, "Create \"" + spath + "\" fail!");
                    return null;
                } else if (copyAssetsToFilesystem(dbfile, sfile)) {
                    dbs.edit().putBoolean(dbfile, true).commit();
                } else {
                    Log.i(tag, String.format("Copy %s to %s fail!", new Object[]{dbfile, sfile}));
                    return null;
                }
            }
            SQLiteDatabase db = SQLiteDatabase.openDatabase(sfile, null, 16);
            if (db != null) {
                this.databases.put(dbfile, db);
            }
            return db;
        }
    }

    private String getDatabaseFilepath() {
        String path;
        String databasepath = this.context.getFilesDir().getPath();
        int pos = databasepath.lastIndexOf("/");
        if (pos > 0) {
            path = databasepath.substring(0, pos) + "/databases";
        } else {
            path = new StringBuilder(String.valueOf(databasepath)).append("/databases").toString();
        }
        return String.format(path, new Object[]{this.context.getApplicationInfo().packageName});
    }

    private String getDatabaseFile(String dbfile) {
        return getDatabaseFilepath() + "/" + dbfile;
    }

    private boolean copyAssetsToFilesystem(String assetsSrc, String des) {
        Exception e;
        Log.i(tag, "Copy " + assetsSrc + " to " + des);
        InputStream istream = null;
        OutputStream ostream = null;
        try {
            istream = this.context.getAssets().open(assetsSrc);
            OutputStream ostream2 = new FileOutputStream(des);
            try {
//                byte[] buffer = new byte[InternalException.DEF_NETWORK_CODE];
//                while (true) {
//                    int length = istream.read(buffer);
//                    if (length <= 0) {
//                        istream.close();
//                        ostream2.close();
//                        ostream = ostream2;
//                        return true;
//                    }
//                    ostream2.write(buffer, 0, length);
//                }
            } catch (Exception e2) {
                e = e2;
                ostream = ostream2;
                e.printStackTrace();
                if (istream != null) {
                    try {
                        istream.close();
                    } catch (Exception ee) {
                        ee.printStackTrace();
                        return false;
                    }
                }
                if (ostream != null) {
                    return false;
                }
                ostream.close();
                return false;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            if (istream != null) {
                //istream.close();
            }
            if (ostream != null) {
                return false;
            }
            //ostream.close();
            return false;
        }

        return  false;
    }

    public boolean closeDatabase(String dbfile) {
        if (this.databases.get(dbfile) == null) {
            return false;
        }
        ((SQLiteDatabase) this.databases.get(dbfile)).close();
        this.databases.remove(dbfile);
        return true;
    }

    public static void closeAllDatabase() {
        Log.i(tag, "closeAllDatabase");
        if (mInstance != null) {
            for (int i = 0; i < mInstance.databases.size(); i++) {
                if (mInstance.databases.get(Integer.valueOf(i)) != null) {
                    ((SQLiteDatabase) mInstance.databases.get(Integer.valueOf(i))).close();
                }
            }
            mInstance.databases.clear();
        }
    }
}
