package com.gitrose.mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.gitrose.mobile.db.DBHelper;
import com.gitrose.mobile.model.ContactsLocalInfo;
//import io.rong.message.BuildConfig;
//import shouji.gexing.framework.utils.DebugUtils;

public class ContactsDao {
    private DBHelper dbhelper;

    public java.util.List<com.gitrose.mobile.model.ContactsLocalInfo> getAllLocalInfo(java.lang.String r9, java.lang.String r10) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0050 in list []
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
        r8 = this;
        r1 = new java.util.ArrayList;
        r1.<init>();
        r0 = 0;
        r2 = 0;
        r4 = r8.dbhelper;	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r4 = r4.open();	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        if (r4 == 0) goto L_0x0028;	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
    L_0x000f:
        r4 = r8.dbhelper;	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r4 = r4.sdb;	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r5 = "select * from tab_contacts where my_tutu_id=? and devicesId=?";	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r6 = 2;	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r6 = new java.lang.String[r6];	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r7 = 0;	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r6[r7] = r10;	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r7 = 1;	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r6[r7] = r9;	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r2 = r4.rawQuery(r5, r6);	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
    L_0x0022:
        r4 = r2.moveToNext();	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        if (r4 != 0) goto L_0x0039;
    L_0x0028:
        if (r2 == 0) goto L_0x0033;
    L_0x002a:
        r4 = r2.isClosed();
        if (r4 != 0) goto L_0x0033;
    L_0x0030:
        r2.close();
    L_0x0033:
        r4 = r8.dbhelper;
        r4.close();
    L_0x0038:
        return r1;
    L_0x0039:
        r0 = r8.parseLocalContacts(r2);	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        r1.add(r0);	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        goto L_0x0022;
    L_0x0041:
        r3 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r3);	 Catch:{ Exception -> 0x0041, all -> 0x0056 }
        if (r2 == 0) goto L_0x0050;
    L_0x0047:
        r4 = r2.isClosed();
        if (r4 != 0) goto L_0x0050;
    L_0x004d:
        r2.close();
    L_0x0050:
        r4 = r8.dbhelper;
        r4.close();
        goto L_0x0038;
    L_0x0056:
        r4 = move-exception;
        if (r2 == 0) goto L_0x0062;
    L_0x0059:
        r5 = r2.isClosed();
        if (r5 != 0) goto L_0x0062;
    L_0x005f:
        r2.close();
    L_0x0062:
        r5 = r8.dbhelper;
        r5.close();
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.ContactsDao.getAllLocalInfo(java.lang.String, java.lang.String):java.util.List<com.gitrose.mobile.model.ContactsLocalInfo>");
    }

    public com.gitrose.mobile.model.ContactsLocalInfo getContactsLocalInfo(java.lang.String r10, java.lang.String r11) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0056 in list []
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
        r9 = this;
        r0 = 0;
        r1 = 0;
        r3 = r9.dbhelper;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r3 = r3.open();	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        if (r3 == 0) goto L_0x0036;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
    L_0x000a:
        r3 = r9.dbhelper;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r3 = r3.sdb;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r4 = "select * from tab_contacts where my_tutu_id=? and phonenumber=?";	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r5 = 2;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r5 = new java.lang.String[r5];	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r6 = 0;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r5[r6] = r10;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r6 = 1;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r7 = " ";	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r8 = "";	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r7 = r11.replace(r7, r8);	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r5[r6] = r7;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r1 = r3.rawQuery(r4, r5);	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        if (r1 == 0) goto L_0x0036;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
    L_0x0027:
        r3 = r1.moveToFirst();	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        if (r3 == 0) goto L_0x0036;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
    L_0x002d:
        r3 = "cursor != null";	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        shouji.gexing.framework.utils.DebugUtils.error(r3);	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r0 = r9.parseLocalContacts(r1);	 Catch:{ Exception -> 0x0047, all -> 0x005c }
    L_0x0036:
        if (r1 == 0) goto L_0x0041;
    L_0x0038:
        r3 = r1.isClosed();
        if (r3 != 0) goto L_0x0041;
    L_0x003e:
        r1.close();
    L_0x0041:
        r3 = r9.dbhelper;
        r3.close();
    L_0x0046:
        return r0;
    L_0x0047:
        r2 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r2);	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        if (r1 == 0) goto L_0x0056;
    L_0x004d:
        r3 = r1.isClosed();
        if (r3 != 0) goto L_0x0056;
    L_0x0053:
        r1.close();
    L_0x0056:
        r3 = r9.dbhelper;
        r3.close();
        goto L_0x0046;
    L_0x005c:
        r3 = move-exception;
        if (r1 == 0) goto L_0x0068;
    L_0x005f:
        r4 = r1.isClosed();
        if (r4 != 0) goto L_0x0068;
    L_0x0065:
        r1.close();
    L_0x0068:
        r4 = r9.dbhelper;
        r4.close();
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.ContactsDao.getContactsLocalInfo(java.lang.String, java.lang.String):com.gitrose.mobile.model.ContactsLocalInfo");
    }

    public com.gitrose.mobile.model.ContactsLocalInfo getContactsLocalRelationInfo(java.lang.String r10, java.lang.String r11) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0056 in list []
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
        r9 = this;
        r0 = 0;
        r1 = 0;
        r3 = r9.dbhelper;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r3 = r3.open();	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        if (r3 == 0) goto L_0x0036;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
    L_0x000a:
        r3 = r9.dbhelper;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r3 = r3.sdb;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r4 = "select relation,tutuid from tab_contacts where my_tutu_id=? and phonenumber=?";	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r5 = 2;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r5 = new java.lang.String[r5];	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r6 = 0;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r5[r6] = r10;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r6 = 1;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r7 = " ";	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r8 = "";	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r7 = r11.replace(r7, r8);	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r5[r6] = r7;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r1 = r3.rawQuery(r4, r5);	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        if (r1 == 0) goto L_0x0036;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
    L_0x0027:
        r3 = r1.moveToFirst();	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        if (r3 == 0) goto L_0x0036;	 Catch:{ Exception -> 0x0047, all -> 0x005c }
    L_0x002d:
        r3 = "cursor != null";	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        shouji.gexing.framework.utils.DebugUtils.error(r3);	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        r0 = r9.parseLocalContacts(r1);	 Catch:{ Exception -> 0x0047, all -> 0x005c }
    L_0x0036:
        if (r1 == 0) goto L_0x0041;
    L_0x0038:
        r3 = r1.isClosed();
        if (r3 != 0) goto L_0x0041;
    L_0x003e:
        r1.close();
    L_0x0041:
        r3 = r9.dbhelper;
        r3.close();
    L_0x0046:
        return r0;
    L_0x0047:
        r2 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r2);	 Catch:{ Exception -> 0x0047, all -> 0x005c }
        if (r1 == 0) goto L_0x0056;
    L_0x004d:
        r3 = r1.isClosed();
        if (r3 != 0) goto L_0x0056;
    L_0x0053:
        r1.close();
    L_0x0056:
        r3 = r9.dbhelper;
        r3.close();
        goto L_0x0046;
    L_0x005c:
        r3 = move-exception;
        if (r1 == 0) goto L_0x0068;
    L_0x005f:
        r4 = r1.isClosed();
        if (r4 != 0) goto L_0x0068;
    L_0x0065:
        r1.close();
    L_0x0068:
        r4 = r9.dbhelper;
        r4.close();
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.ContactsDao.getContactsLocalRelationInfo(java.lang.String, java.lang.String):com.gitrose.mobile.model.ContactsLocalInfo");
    }

    public java.lang.String getPhoneNumber(java.lang.String r8, java.lang.String r9) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0053 in list []
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
        r2 = 0;
        r0 = 0;
        r3 = r7.dbhelper;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r3 = r3.open();	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        if (r3 == 0) goto L_0x002f;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
    L_0x000a:
        r3 = r7.dbhelper;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r3 = r3.sdb;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r4 = "select phonenumber from tab_contacts where my_tutu_id=? and localid=?";	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r5 = 2;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r5 = new java.lang.String[r5];	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r6 = 0;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r5[r6] = r8;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r6 = 1;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r5[r6] = r9;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r0 = r3.rawQuery(r4, r5);	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        if (r0 == 0) goto L_0x002f;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
    L_0x001f:
        r3 = r0.moveToFirst();	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        if (r3 == 0) goto L_0x002f;	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
    L_0x0025:
        r3 = "phonenumber";	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r3 = r0.getColumnIndex(r3);	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        r2 = r0.getString(r3);	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
    L_0x002f:
        if (r0 == 0) goto L_0x003a;
    L_0x0031:
        r3 = r0.isClosed();
        if (r3 != 0) goto L_0x003a;
    L_0x0037:
        r0.close();
    L_0x003a:
        r3 = r7.dbhelper;
        r3.close();
    L_0x003f:
        r3 = r2.trim();
        return r3;
    L_0x0044:
        r1 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r1);	 Catch:{ Exception -> 0x0044, all -> 0x0059 }
        if (r0 == 0) goto L_0x0053;
    L_0x004a:
        r3 = r0.isClosed();
        if (r3 != 0) goto L_0x0053;
    L_0x0050:
        r0.close();
    L_0x0053:
        r3 = r7.dbhelper;
        r3.close();
        goto L_0x003f;
    L_0x0059:
        r3 = move-exception;
        if (r0 == 0) goto L_0x0065;
    L_0x005c:
        r4 = r0.isClosed();
        if (r4 != 0) goto L_0x0065;
    L_0x0062:
        r0.close();
    L_0x0065:
        r4 = r7.dbhelper;
        r4.close();
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.ContactsDao.getPhoneNumber(java.lang.String, java.lang.String):java.lang.String");
    }

    public java.lang.String getPhoneNumber(java.lang.String r8, java.lang.String r9, java.lang.String r10) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005a in list []
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
        r2 = 0;
        r0 = 0;
        r3 = r7.dbhelper;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r3 = r3.open();	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        if (r3 == 0) goto L_0x003a;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
    L_0x000a:
        r3 = r7.dbhelper;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r3 = r3.sdb;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r4 = "select phonenumber from tab_contacts where devicesId=? and my_tutu_id=? and tutuid=?";	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r5 = 3;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r5 = new java.lang.String[r5];	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r6 = 0;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r5[r6] = r8;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r6 = 1;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r5[r6] = r9;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r6 = 2;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r5[r6] = r10;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r0 = r3.rawQuery(r4, r5);	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        if (r0 == 0) goto L_0x003a;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
    L_0x0022:
        r3 = r0.moveToFirst();	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        if (r3 == 0) goto L_0x003a;	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
    L_0x0028:
        r3 = "phonenumber";	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r3 = r0.getColumnIndex(r3);	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r3 = r0.getString(r3);	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r4 = " ";	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r5 = "";	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        r2 = r3.replace(r4, r5);	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
    L_0x003a:
        if (r0 == 0) goto L_0x0045;
    L_0x003c:
        r3 = r0.isClosed();
        if (r3 != 0) goto L_0x0045;
    L_0x0042:
        r0.close();
    L_0x0045:
        r3 = r7.dbhelper;
        r3.close();
    L_0x004a:
        return r2;
    L_0x004b:
        r1 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r1);	 Catch:{ Exception -> 0x004b, all -> 0x0060 }
        if (r0 == 0) goto L_0x005a;
    L_0x0051:
        r3 = r0.isClosed();
        if (r3 != 0) goto L_0x005a;
    L_0x0057:
        r0.close();
    L_0x005a:
        r3 = r7.dbhelper;
        r3.close();
        goto L_0x004a;
    L_0x0060:
        r3 = move-exception;
        if (r0 == 0) goto L_0x006c;
    L_0x0063:
        r4 = r0.isClosed();
        if (r4 != 0) goto L_0x006c;
    L_0x0069:
        r0.close();
    L_0x006c:
        r4 = r7.dbhelper;
        r4.close();
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.ContactsDao.getPhoneNumber(java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    public ContactsDao(Context context) {
        this.dbhelper = DBHelper.getInstance(context);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addContactInfo(com.gitrose.mobile.model.ContactsLocalInfo r4) {
        /*
        r3 = this;
        r1 = r3.dbhelper;	 Catch:{ Exception -> 0x0011 }
        r1 = r1.open();	 Catch:{ Exception -> 0x0011 }
        if (r1 == 0) goto L_0x000b;
    L_0x0008:
        r3.insertInfo(r4);	 Catch:{ Exception -> 0x0011 }
    L_0x000b:
        r1 = r3.dbhelper;
        r1.close();
    L_0x0010:
        return;
    L_0x0011:
        r0 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r0);	 Catch:{ all -> 0x001b }
        r1 = r3.dbhelper;
        r1.close();
        goto L_0x0010;
    L_0x001b:
        r1 = move-exception;
        r2 = r3.dbhelper;
        r2.close();
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.ContactsDao.addContactInfo(com.gitrose.mobile.model.ContactsLocalInfo):void");
    }

    private void insertInfo(ContactsLocalInfo contact) {
        this.dbhelper.sdb.execSQL("insert into tab_contacts(devicesId,localid,my_tutu_id,phonenumber,tutuid,relation,updatetime)values(?,?,?,?,?,?,?)", new Object[]{contact.getDevicesId(), contact.getLocalid(), contact.getMy_tutu_id(), contact.getPhonenumber(), contact.getTutuid(), contact.getRelation(), contact.getUpdatetime()});
    }

    public void delContactInfo(String devicesId, String localTopicId) {
        try {
            if (this.dbhelper.open()) {
                this.dbhelper.sdb.execSQL("delete from tab_contacts where localTopicId=? and devicesId=?", new Object[]{localTopicId, devicesId});
                this.dbhelper.close();
            }
        } catch (Exception e) {
            //DebugUtils.exception(e);
        } finally {
            this.dbhelper.close();
        }
    }

    public boolean checkContactsLocalIsIn(String devicesid, String my_tutu_id, String tutu_id) {
        if (getContactsLocalInfo(devicesid, my_tutu_id, tutu_id) == null) {
            return false;
        }
        return true;
    }

    public ContactsLocalInfo getContactsLocalInfo(String devicesId, String my_tutu_id, String tutu_id) {
        ContactsLocalInfo contactInfo = null;
        Cursor cursor = null;
        try {
            cursor = this.dbhelper.sdb.rawQuery("select * from tab_contacts where devicesId=? and my_tutu_id=? and tutuid=?", new String[]{devicesId, my_tutu_id, tutu_id});
            cursor.getCount();
            if (cursor != null && cursor.moveToFirst()) {
                contactInfo = parseLocalContacts(cursor);
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Exception e) {
            //DebugUtils.exception(e);
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
        return contactInfo;
    }

    private ContactsLocalInfo parseLocalContacts(Cursor cursor) {
        ContactsLocalInfo contactInfo = new ContactsLocalInfo();
        contactInfo.setDevicesId(cursor.getString(cursor.getColumnIndex("devicesId")));
        contactInfo.setLocalid(cursor.getString(cursor.getColumnIndex("localid")));
        contactInfo.setMy_tutu_id(cursor.getString(cursor.getColumnIndex("my_tutu_id")));
        contactInfo.setPhonenumber(cursor.getString(cursor.getColumnIndex("phonenumber")));
        contactInfo.setTutuid(cursor.getString(cursor.getColumnIndex("tutuid")));
        contactInfo.setRelation(cursor.getString(cursor.getColumnIndex("relation")));
        contactInfo.setUpdatetime(cursor.getString(cursor.getColumnIndex("updatetime")));
        return contactInfo;
    }

    public void updateContactsLocalInfo(ContactsLocalInfo contactslocalinfo) {
        try {
            if (this.dbhelper.open()) {
                if (checkContactsLocalIsIn(contactslocalinfo.getDevicesId(), contactslocalinfo.getMy_tutu_id(), contactslocalinfo.getTutuid())) {
                    ContentValues values = new ContentValues();
                    values.put("relation", contactslocalinfo.getRelation());
                    values.put("localid", contactslocalinfo.getLocalid());
                    if (contactslocalinfo.getPhonenumber() != null) {
                        //values.put("phonenumber", contactslocalinfo.getPhonenumber().replace(" ", BuildConfig.FLAVOR));
                    }
                    //DebugUtils.error("updateContactsLocalInfo--update  bef");
                    this.dbhelper.sdb.update(DBHelper.TAB_CONTACTS, values, "my_tutu_id=? and tutuid=?", new String[]{contactslocalinfo.getMy_tutu_id(), contactslocalinfo.getTutuid()});
                    //DebugUtils.error("updateContactsLocalInfo--update");
                } else {
                    //DebugUtils.error("updateContactsLocalInfo--add  bef");
                    insertInfo(contactslocalinfo);
                    //DebugUtils.error("updateContactsLocalInfo--add");
                }
                this.dbhelper.close();
            }
        } catch (Exception e) {
            //DebugUtils.exception(e);
        } finally {
            this.dbhelper.close();
        }
    }
}
