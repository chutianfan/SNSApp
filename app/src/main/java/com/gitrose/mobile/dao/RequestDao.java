package com.gitrose.mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
//import com.tencent.android.tpush.common.MessageKey;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.db.DBHelper;
import com.gitrose.mobile.model.Comment;
//import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.ArrayList;
import java.util.List;
//import shouji.gexing.framework.utils.DebugUtils;

public class RequestDao {
    private DBHelper dbhelper;

    public java.util.List<TopicInfo> getAllLocalTopics(java.lang.String r9) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0059 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:58)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
        /*
        r8 = this;
        r2 = new java.util.ArrayList;
        r2.<init>();
        r0 = 0;
        r4 = r8.dbhelper;	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        r4 = r4.open();	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        if (r4 == 0) goto L_0x0039;	 Catch:{ Exception -> 0x004a, all -> 0x005f }
    L_0x000e:
        r4 = r8.dbhelper;	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        r4 = r4.sdb;	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        r5 = "select * from tab_topic where uid =?";	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        r6 = 1;	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        r6 = new java.lang.String[r6];	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        r7 = 0;	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        r6[r7] = r9;	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        r0 = r4.rawQuery(r5, r6);	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        if (r0 == 0) goto L_0x0039;	 Catch:{ Exception -> 0x004a, all -> 0x005f }
    L_0x0020:
        r4 = r0.moveToFirst();	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        if (r4 == 0) goto L_0x0039;	 Catch:{ Exception -> 0x004a, all -> 0x005f }
    L_0x0026:
        r3 = r8.parseLocalTopics(r0);	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        r4 = r2.contains(r3);	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        if (r4 != 0) goto L_0x0033;	 Catch:{ Exception -> 0x004a, all -> 0x005f }
    L_0x0030:
        r2.add(r3);	 Catch:{ Exception -> 0x004a, all -> 0x005f }
    L_0x0033:
        r4 = r0.moveToNext();	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        if (r4 != 0) goto L_0x0026;
    L_0x0039:
        if (r0 == 0) goto L_0x0044;
    L_0x003b:
        r4 = r0.isClosed();
        if (r4 != 0) goto L_0x0044;
    L_0x0041:
        r0.close();
    L_0x0044:
        r4 = r8.dbhelper;
        r4.close();
    L_0x0049:
        return r2;
    L_0x004a:
        r1 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r1);	 Catch:{ Exception -> 0x004a, all -> 0x005f }
        if (r0 == 0) goto L_0x0059;
    L_0x0050:
        r4 = r0.isClosed();
        if (r4 != 0) goto L_0x0059;
    L_0x0056:
        r0.close();
    L_0x0059:
        r4 = r8.dbhelper;
        r4.close();
        goto L_0x0049;
    L_0x005f:
        r4 = move-exception;
        if (r0 == 0) goto L_0x006b;
    L_0x0062:
        r5 = r0.isClosed();
        if (r5 != 0) goto L_0x006b;
    L_0x0068:
        r0.close();
    L_0x006b:
        r5 = r8.dbhelper;
        r5.close();
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.RequestDao.getAllLocalTopics(java.lang.String):java.util.List<com.gitrose.mobile.model.TopicInfo>");
    }

    public TopicInfo getTopic(java.lang.String r8) {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
        /*
        r7 = this;
        r2 = 0;
        r0 = 0;
        r3 = r7.dbhelper;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r3 = r3.open();	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        if (r3 == 0) goto L_0x0026;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
    L_0x000a:
        r3 = r7.dbhelper;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r3 = r3.sdb;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r4 = "select * from tab_topic where localTopicId=?";	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r5 = 1;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r5 = new java.lang.String[r5];	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r6 = 0;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r5[r6] = r8;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        r0 = r3.rawQuery(r4, r5);	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        if (r0 == 0) goto L_0x0026;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
    L_0x001c:
        r3 = r0.moveToFirst();	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        if (r3 == 0) goto L_0x0026;	 Catch:{ Exception -> 0x0037, all -> 0x004c }
    L_0x0022:
        r2 = r7.parseLocalTopics(r0);	 Catch:{ Exception -> 0x0037, all -> 0x004c }
    L_0x0026:
        if (r0 == 0) goto L_0x0031;
    L_0x0028:
        r3 = r0.isClosed();
        if (r3 != 0) goto L_0x0031;
    L_0x002e:
        r0.close();
    L_0x0031:
        r3 = r7.dbhelper;
        r3.close();
    L_0x0036:
        return r2;
    L_0x0037:
        r1 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r1);	 Catch:{ Exception -> 0x0037, all -> 0x004c }
        if (r0 == 0) goto L_0x0046;
    L_0x003d:
        r3 = r0.isClosed();
        if (r3 != 0) goto L_0x0046;
    L_0x0043:
        r0.close();
    L_0x0046:
        r3 = r7.dbhelper;
        r3.close();
        goto L_0x0036;
    L_0x004c:
        r3 = move-exception;
        if (r0 == 0) goto L_0x0058;
    L_0x004f:
        r4 = r0.isClosed();
        if (r4 != 0) goto L_0x0058;
    L_0x0055:
        r0.close();
    L_0x0058:
        r4 = r7.dbhelper;
        r4.close();
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.RequestDao.getTopic(java.lang.String):com.gitrose.mobile.model.TopicInfo");
    }

    public RequestDao(Context context) {
        this.dbhelper = DBHelper.getInstance(context);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addLocalTopic(TopicInfo r7) {
        /*
        r6 = this;
        r1 = r6.dbhelper;	 Catch:{ Exception -> 0x003d }
        r1 = r1.open();	 Catch:{ Exception -> 0x003d }
        if (r1 == 0) goto L_0x0037;
    L_0x0008:
        r1 = r6.dbhelper;	 Catch:{ Exception -> 0x003d }
        r1 = r1.sdb;	 Catch:{ Exception -> 0x003d }
        r2 = "insert into tab_topic(localTopicId,uid,content,addTime,avatarTime)values(?,?,?,?,?)";
        r3 = 5;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x003d }
        r4 = 0;
        r5 = r7.getLocalRequestId();	 Catch:{ Exception -> 0x003d }
        r3[r4] = r5;	 Catch:{ Exception -> 0x003d }
        r4 = 1;
        r5 = r7.getUid();	 Catch:{ Exception -> 0x003d }
        r3[r4] = r5;	 Catch:{ Exception -> 0x003d }
        r4 = 2;
        r5 = r7.getContent();	 Catch:{ Exception -> 0x003d }
        r3[r4] = r5;	 Catch:{ Exception -> 0x003d }
        r4 = 3;
        r5 = r7.getAddtime();	 Catch:{ Exception -> 0x003d }
        r3[r4] = r5;	 Catch:{ Exception -> 0x003d }
        r4 = 4;
        r5 = r7.getAvatartime();	 Catch:{ Exception -> 0x003d }
        r3[r4] = r5;	 Catch:{ Exception -> 0x003d }
        r1.execSQL(r2, r3);	 Catch:{ Exception -> 0x003d }
    L_0x0037:
        r1 = r6.dbhelper;
        r1.close();
    L_0x003c:
        return;
    L_0x003d:
        r0 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r0);	 Catch:{ all -> 0x0047 }
        r1 = r6.dbhelper;
        r1.close();
        goto L_0x003c;
    L_0x0047:
        r1 = move-exception;
        r2 = r6.dbhelper;
        r2.close();
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.RequestDao.addLocalTopic(com.gitrose.mobile.model.TopicInfo):void");
    }

    public void removeLocalTopic(String localTopicId) {
        try {
            if (this.dbhelper.open()) {
                this.dbhelper.sdb.execSQL("delete from tab_topic where localTopicId=?", new Object[]{localTopicId});
                this.dbhelper.close();
            }
        } catch (Exception e) {
            //DebugUtils.exception(e);
        } finally {
            this.dbhelper.close();
        }
    }

    private TopicInfo parseLocalTopics(Cursor cursor) {
        TopicInfo topicInfo = new TopicInfo();
//        //topicInfo.setUid(cursor.getString(cursor.getColumnIndex(SocializeProtocolConstants.PROTOCOL_KEY_UID)));
//        topicInfo.setLocalRequestId(cursor.getString(cursor.getColumnIndex("localTopicId")));
//        //topicInfo.setContent(cursor.getString(cursor.getColumnIndex(MessageKey.MSG_CONTENT)));
//        topicInfo.setAvatartime(cursor.getString(cursor.getColumnIndex(Constant.AVATAR_TIME_PERFERENCE)));
//        topicInfo.setAddtime(cursor.getString(cursor.getColumnIndex("addTime")));
        return topicInfo;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void insertComment(com.gitrose.mobile.model.Comment r6) {
        /*
        r5 = this;
        r2 = r5.dbhelper;	 Catch:{ Exception -> 0x006e }
        r2 = r2.open();	 Catch:{ Exception -> 0x006e }
        if (r2 == 0) goto L_0x0068;
    L_0x0008:
        r1 = new android.content.ContentValues;	 Catch:{ Exception -> 0x006e }
        r1.<init>();	 Catch:{ Exception -> 0x006e }
        r2 = "commentId";
        r3 = r6.getCommentid();	 Catch:{ Exception -> 0x006e }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x006e }
        r2 = "uid";
        r3 = r6.getUid();	 Catch:{ Exception -> 0x006e }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x006e }
        r2 = "xPoint";
        r3 = r6.getLocationx();	 Catch:{ Exception -> 0x006e }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x006e }
        r2 = "yPoint";
        r3 = r6.getLocationy();	 Catch:{ Exception -> 0x006e }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x006e }
        r2 = "content";
        r3 = r6.getContent();	 Catch:{ Exception -> 0x006e }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x006e }
        r2 = "input";
        r3 = r6.getTxtframe();	 Catch:{ Exception -> 0x006e }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x006e }
        r2 = "replyCommentId";
        r3 = r6.getReplyCommentId();	 Catch:{ Exception -> 0x006e }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x006e }
        r2 = "topicId";
        r3 = r6.getTopicId();	 Catch:{ Exception -> 0x006e }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x006e }
        r2 = "localTopicId";
        r3 = r6.getLocalTopicId();	 Catch:{ Exception -> 0x006e }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x006e }
        r2 = r5.dbhelper;	 Catch:{ Exception -> 0x006e }
        r2 = r2.sdb;	 Catch:{ Exception -> 0x006e }
        r3 = "tab_comment";
        r4 = 0;
        r2.insert(r3, r4, r1);	 Catch:{ Exception -> 0x006e }
    L_0x0068:
        r2 = r5.dbhelper;
        r2.close();
    L_0x006d:
        return;
    L_0x006e:
        r0 = move-exception;
        shouji.gexing.framework.utils.DebugUtils.exception(r0);	 Catch:{ all -> 0x0078 }
        r2 = r5.dbhelper;
        r2.close();
        goto L_0x006d;
    L_0x0078:
        r2 = move-exception;
        r3 = r5.dbhelper;
        r3.close();
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.dao.RequestDao.insertComment(com.gitrose.mobile.model.Comment):void");
    }

    public void deleteComment(String commentId) {
        try {
            if (this.dbhelper.open()) {
                this.dbhelper.sdb.delete(DBHelper.TAB_COMMENT, " commentId= ?", new String[]{commentId});
                this.dbhelper.close();
            }
        } catch (Exception e) {
            //DebugUtils.exception(e);
        } finally {
            this.dbhelper.close();
        }
    }

    public List<Comment> queryCommentByUid(String uid) {
        Exception e;
        Throwable th;
        List<Comment> list = null;
        Cursor cursor = null;
        try {
            if (this.dbhelper.open()) {
                cursor = this.dbhelper.sdb.query(DBHelper.TAB_COMMENT, null, "uid=?", new String[]{uid}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    List<Comment> commentList = new ArrayList();
                    do {
                        try {
                            Comment comment = parseComment(cursor);
                            if (!commentList.contains(comment)) {
                                commentList.add(comment);
                            }
                        } catch (Exception e2) {
                            e = e2;
                            list = commentList;
                        } catch (Throwable th2) {
                            th = th2;
                            list = commentList;
                        }
                    } while (cursor.moveToNext());
                    list = commentList;
                }
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            this.dbhelper.close();
        } catch (Exception e3) {
            e = e3;
            try {
                //DebugUtils.exception(e);
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                this.dbhelper.close();
                return list;
            } catch (Throwable th3) {
                th = th3;
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                this.dbhelper.close();
                //throw th;
            }
        }
        return list;
    }

    public void updateComment(String commentId, String topicId) {
        try {
            if (this.dbhelper.open()) {
                ContentValues values = new ContentValues();
                values.put("topicId", topicId);
                this.dbhelper.sdb.update(DBHelper.TAB_COMMENT, values, "commentId=? ", new String[]{commentId});
                this.dbhelper.close();
            }
        } catch (Exception e) {
            //DebugUtils.exception(e);
        } finally {
            this.dbhelper.close();
        }
    }

    public List<Comment> queryCommentByTopicId(String topicId) {
        Exception e;
        Throwable th;
        List<Comment> list = null;
        Cursor cursor = null;
        try {
            if (this.dbhelper.open()) {
                cursor = this.dbhelper.sdb.query(DBHelper.TAB_COMMENT, null, "topicId = ?", new String[]{topicId}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    List<Comment> commentList = new ArrayList();
                    do {
                        try {
                            Comment comment = parseComment(cursor);
                            if (!commentList.contains(comment)) {
                                commentList.add(comment);
                            }
                        } catch (Exception e2) {
                            e = e2;
                            list = commentList;
                        } catch (Throwable th2) {
                            th = th2;
                            list = commentList;
                        }
                    } while (cursor.moveToNext());
                    list = commentList;
                }
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            this.dbhelper.close();
        } catch (Exception e3) {
            e = e3;
            try {
                //DebugUtils.exception(e);
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                this.dbhelper.close();
                return list;
            } catch (Throwable th3) {
                th = th3;
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                this.dbhelper.close();
                //throw th;
            }
        }
        return list;
    }

    public List<Comment> queryCommentByLocalTopicId(String localTopicId) {
        Exception e;
        Throwable th;
        List<Comment> list = null;
        Cursor cursor = null;
        try {
            if (this.dbhelper.open()) {
                cursor = this.dbhelper.sdb.query(DBHelper.TAB_COMMENT, null, "localTopicId=?", new String[]{localTopicId}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    List<Comment> commentList = new ArrayList();
                    do {
                        try {
                            Comment comment = parseComment(cursor);
                            if (!commentList.contains(comment)) {
                                commentList.add(comment);
                            }
                        } catch (Exception e2) {
                            e = e2;
                            list = commentList;
                        } catch (Throwable th2) {
                            th = th2;
                            list = commentList;
                        }
                    } while (cursor.moveToNext());
                    list = commentList;
                }
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            this.dbhelper.close();
        } catch (Exception e3) {
            e = e3;
            try {
                //DebugUtils.exception(e);
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                this.dbhelper.close();
                return list;
            } catch (Throwable th3) {
                th = th3;
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                this.dbhelper.close();
                //throw th;
            }
        }
        return list;
    }

    private Comment parseComment(Cursor cursor) {
        Comment comment = new Comment();
//        comment.setCommentid(cursor.getString(cursor.getColumnIndex("commentId")));
//        //comment.setUid(cursor.getString(cursor.getColumnIndex(SocializeProtocolConstants.PROTOCOL_KEY_UID)));
//        comment.setLocationx(cursor.getString(cursor.getColumnIndex("xPoint")));
//        comment.setLocationy(cursor.getString(cursor.getColumnIndex("yPoint")));
//        //comment.setContent(cursor.getString(cursor.getColumnIndex(MessageKey.MSG_CONTENT)));
//        comment.setTxtframe(cursor.getString(cursor.getColumnIndex("input")));
//        comment.setReplyCommentId(cursor.getString(cursor.getColumnIndex("replyCommentId")));
//        comment.setTopicId(cursor.getString(cursor.getColumnIndex("topicId")));
//        comment.setLocalTopicId(cursor.getString(cursor.getColumnIndex("localTopicId")));
        return comment;
    }
}
