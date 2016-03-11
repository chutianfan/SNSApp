package com.gitrose.mobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import shouji.gexing.framework.utils.DebugUtils;

public class DBHelper {
    private static final String DATABASE_NAME = "tutu";
    private static final String DATABASE_SUFFIX = ".db";
    private static final String SQL_CREATE_COMMENT = "create table if not exists tab_comment(_id INTEGER PRIMARY KEY autoincrement,commentId VARCHAR, uid VARCHAR,xPoint VARCHAR,yPoint VARCHAR,content VARCHAR,input VARCHAR,replyCommentId VARCHAR,topicId VARCHAR,localTopicId VARCHAR);";
    private static final String SQL_CREATE_CONTACTS = "create table if not exists tab_contacts(_id INTEGER PRIMARY KEY autoincrement,devicesId VARCHAR,localid VARCHAR,my_tutu_id VARCHAR,phonenumber VARCHAR,tutuid VARCHAR,relation VARCHAR,updatetime VARCHAR);";
    private static final String SQL_CREATE_LOCAL_TOPIC = "create table if not exists tab_topic(_id INTEGER PRIMARY KEY autoincrement,localTopicId VARCHAR,uid VARCHAR,content VARCHAR,addTime VARCHAR,avatarTime VARCHAR);";
    private static final String SQL_CREATE_USERS = "create table if not exists tab_users(_id INTEGER PRIMARY KEY autoincrement,my_tutu_id VARCHAR,updatetime VERCHAR,type VERCHAR);";
    public static final String TAB_COMMENT = "tab_comment";
    public static final String TAB_CONTACTS = "tab_contacts";
    public static final String TAB_TOPIC = "tab_topic";
    public static final String TAB_USERS = "tab_users";
    private static DBHelper dbhelper = null;
    private static final int version = 1;
    private Context ctx;
    public boolean isDatabaseOprating;
    public SQLiteDatabase sdb;
    private SqliteHelper sqhelper;

    private static class SqliteHelper extends SQLiteOpenHelper {
        public SqliteHelper(Context paramContext) {
            super(paramContext, "tutu.db", null, DBHelper.version);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBHelper.SQL_CREATE_LOCAL_TOPIC);
            db.execSQL(DBHelper.SQL_CREATE_COMMENT);
            db.execSQL(DBHelper.SQL_CREATE_CONTACTS);
            db.execSQL(DBHelper.SQL_CREATE_USERS);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DBHelper.SQL_CREATE_LOCAL_TOPIC);
            db.execSQL(DBHelper.SQL_CREATE_COMMENT);
            db.execSQL(DBHelper.SQL_CREATE_CONTACTS);
            db.execSQL(DBHelper.SQL_CREATE_USERS);
        }
    }

    static {
        dbhelper = null;
    }

    public static DBHelper getInstance(Context context) {
        if (dbhelper == null) {
            dbhelper = new DBHelper(context);
        }
        return dbhelper;
    }

    private DBHelper(Context ctx) {
        this.ctx = null;
        this.isDatabaseOprating = false;
        this.ctx = ctx;
    }

    public boolean open() {
        if (this.isDatabaseOprating) {
            return false;
        }
        this.isDatabaseOprating = true;
        this.sqhelper = new SqliteHelper(this.ctx);
        this.sdb = this.sqhelper.getWritableDatabase();
        return true;
    }

    public void close() {
        try {
            this.sqhelper.close();
            this.isDatabaseOprating = false;
            this.sdb.close();
        } catch (Exception e) {
            if (this.sdb != null) {
                this.sdb.close();
            }
            //DebugUtils.exception(e);
        }
    }
}
