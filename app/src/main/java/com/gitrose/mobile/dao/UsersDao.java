package com.gitrose.mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.gitrose.mobile.db.DBHelper;
import com.gitrose.mobile.model.ContactsManagerInfo;
//import com.umeng.socialize.net.utils.SocializeProtocolConstants;
//import shouji.gexing.framework.utils.DebugUtils;

public class UsersDao {
    private DBHelper dbhelper;

    public com.gitrose.mobile.model.ContactsManagerInfo getManagerInfo(java.lang.String r8) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0046 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:58)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
        /*
        r7 = this;
        r0 = 0;
        r1 = 0;
        r3 = r7.dbhelper;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r3 = r3.open();	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        if (r3 == 0) goto L_0x0026;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
    L_0x000a:
        r3 = r7.dbhelper;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r3 = r3.sdb;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r4 = "select * from tab_users where my_tutu_id=?";	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r5 = 1;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r5 = new java.lang.String[r5];	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r6 = 0;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r5[r6] = r8;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r1 = r3.rawQuery(r4, r5);	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        if (r1 == 0) goto L_0x0026;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
    L_0x001c:
        r3 = r1.moveToFirst();	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        if (r3 == 0) goto L_0x0026;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
    L_0x0022:
        r0 = r7.parseLocalTopics(r1);	 Catch:{ Exception -> 0x0037, all -> 0x004c }
    L_0x0026:
        if (r1 == 0) goto L_0x0031;
    L_0x0028:
        r3 = r1.isClosed();
        if (r3 != 0) goto L_0x0031;
    L_0x002e:
        r1.close();
    L_0x0031:
        r3 = r7.dbhelper;
        r3.close();
    L_0x0036:
        return r0;
    L_0x0037:
        r2 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r2);	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        if (r1 == 0) goto L_0x0046;
    L_0x003d:
        r3 = r1.isClosed();
        if (r3 != 0) goto L_0x0046;
    L_0x0043:
        r1.close();
    L_0x0046:
        r3 = r7.dbhelper;
        r3.close();
        goto L_0x0036;
    L_0x004c:
        r3 = move-exception;
        if (r1 == 0) goto L_0x0058;
    L_0x004f:
        r4 = r1.isClosed();
        if (r4 != 0) goto L_0x0058;
    L_0x0055:
        r1.close();
    L_0x0058:
        r4 = r7.dbhelper;
        r4.close();
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.UsersDao.getManagerInfo(java.lang.String):com.gitrose.mobile.model.ContactsManagerInfo");
    }

    public UsersDao(Context context) {
        this.dbhelper = DBHelper.getInstance(context);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addManagerInfo(com.gitrose.mobile.model.ContactsManagerInfo r7) {
        /*
        r6 = this;
        r1 = r6.dbhelper;	 Catch:{ Exception -> 0x002f }
        r1 = r1.open();	 Catch:{ Exception -> 0x002f }
        if (r1 == 0) goto L_0x0029;
    L_0x0008:
        r1 = r6.dbhelper;	 Catch:{ Exception -> 0x002f }
        r1 = r1.sdb;	 Catch:{ Exception -> 0x002f }
        r2 = "insert into tab_users(my_tutu_id,updatetime,type)values(?,?,?)";
        r3 = 3;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x002f }
        r4 = 0;
        r5 = r7.getMy_tutu_id();	 Catch:{ Exception -> 0x002f }
        r3[r4] = r5;	 Catch:{ Exception -> 0x002f }
        r4 = 1;
        r5 = r7.getUpdatetime();	 Catch:{ Exception -> 0x002f }
        r3[r4] = r5;	 Catch:{ Exception -> 0x002f }
        r4 = 2;
        r5 = r7.getType();	 Catch:{ Exception -> 0x002f }
        r3[r4] = r5;	 Catch:{ Exception -> 0x002f }
        r1.execSQL(r2, r3);	 Catch:{ Exception -> 0x002f }
    L_0x0029:
        r1 = r6.dbhelper;
        r1.close();
    L_0x002e:
        return;
    L_0x002f:
        r0 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r0);	 Catch:{ all -> 0x0039 }
        r1 = r6.dbhelper;
        r1.close();
        goto L_0x002e;
    L_0x0039:
        r1 = move-exception;
        r2 = r6.dbhelper;
        r2.close();
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.UsersDao.addManagerInfo(com.gitrose.mobile.model.ContactsManagerInfo):void");
    }

    public void delManagerInfo(String tutu_id) {
        try {
            if (this.dbhelper.open()) {
                this.dbhelper.sdb.execSQL("delete from tab_users where my_tutu_id=?", new Object[]{tutu_id});
                this.dbhelper.close();
            }
        } catch (Exception e) {
            //DebugUtils.exception(e);
        } finally {
            this.dbhelper.close();
        }
    }

    public boolean checkUserIsExist(String tutu_id) {
        ContactsManagerInfo contactInfo = getManagerInfo(tutu_id);
        if (contactInfo == null || contactInfo.getMy_tutu_id() == null) {
            return false;
        }
        return true;
    }

    private ContactsManagerInfo parseLocalTopics(Cursor cursor) {
        ContactsManagerInfo contactInfo = new ContactsManagerInfo();
        contactInfo.setMy_tutu_id(cursor.getString(cursor.getColumnIndex("my_tutu_id")));
        //contactInfo.setType(cursor.getString(cursor.getColumnIndex(SocializeProtocolConstants.PROTOCOL_SHARE_TYPE)));
        contactInfo.setUpdatetime(cursor.getString(cursor.getColumnIndex("updatetime")));
        return contactInfo;
    }

    public void updateContactsLocalInfo(String tutu_id, String phonenumber, String type, String updatetime) {
        try {
            if (this.dbhelper.open()) {
                ContentValues values = new ContentValues();
                //values.put(SocializeProtocolConstants.PROTOCOL_SHARE_TYPE, type);
                values.put("updatetime", updatetime);
                this.dbhelper.sdb.update(DBHelper.TAB_USERS, values, "my_tutu_id=?", new String[]{tutu_id});
                this.dbhelper.close();
            }
        } catch (Exception e) {
            //DebugUtils.exception(e);
        } finally {
            this.dbhelper.close();
        }
    }
}
