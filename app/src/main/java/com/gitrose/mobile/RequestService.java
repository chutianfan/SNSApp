package com.gitrose.mobile;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.gitrose.greendao.TopicInfo;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.dao.RequestDao;
//import com.gitrose.mobile.http.QGHttpHandlerAsyn;
import com.gitrose.mobile.model.Comment;
//import io.rong.message.BuildConfig;
import java.io.File;
import java.util.List;
//import org.apache.http.Header;

public class RequestService extends Service {
    private RequestDao requestDao;

    /* renamed from: com.gitrose.mobile.RequestService.1 */
//    class C06871 extends QGHttpHandler<CommentList> {
//        private final /* synthetic */ String val$commentId;
//
//        C06871(Context $anonymous0, boolean $anonymous1, String str) {
//            this.val$commentId = str;
//            super($anonymous0, $anonymous1);
//        }
//
//        public void onGetDataSuccess(CommentList data) {
//            RequestService.this.requestDao.deleteComment(this.val$commentId);
//        }
//
//        public void onErrorResponse(VolleyError error) {
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//        }
//    }

    /* renamed from: com.gitrose.mobile.RequestService.2 */
//    class C07452 extends QGHttpHandlerAsyn<TopicInfo> {
//        private final /* synthetic */ File val$file;
//        private final /* synthetic */ TopicInfo val$topic;
//
//        /* renamed from: com.gitrose.mobile.RequestService.2.1 */
//        class C06881 extends QGHttpHandler<CommentList> {
//            private final /* synthetic */ Comment val$comment;
//            private final /* synthetic */ String val$realTopicId;
//
//            C06881(Context $anonymous0, boolean $anonymous1, Comment comment, String str) {
//                this.val$comment = comment;
//                this.val$realTopicId = str;
//                super($anonymous0, $anonymous1);
//            }
//
//            public void onGetDataSuccess(CommentList data) {
//                RequestService.this.requestDao.deleteComment(this.val$comment.getCommentid());
//            }
//
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                RequestService.this.requestDao.updateComment(this.val$comment.getCommentid(), this.val$realTopicId);
//            }
//        }
//
//        C07452(Context $anonymous0, TopicInfo topicInfo, File file) {
//            this.val$topic = topicInfo;
//            this.val$file = file;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(TopicInfo data) {
//            String localTopicId = this.val$topic.getLocalRequestId();
//            RequestService.this.requestDao.removeLocalTopic(localTopicId);
//            this.val$file.delete();
//            RequestService.sendUploadTopicSuccessBrocat(RequestService.this, localTopicId, data);
//            String realTopicId = data.getTopicid();
//            List<Comment> commentList = RequestService.this.requestDao.queryCommentByLocalTopicId(localTopicId);
//            if (commentList != null && commentList.size() > 0) {
//                for (int j = 0; j < commentList.size(); j++) {
//                    Comment comment = (Comment) commentList.get(j);
//                    QGHttpRequest.getInstance().addComment(RequestService.this, data.getTopicid(), comment.getContent(), comment.getReplyCommentId(), comment.getLocationx(), comment.getLocationy(), comment.getTxtframe(), new C06881(RequestService.this, false, comment, realTopicId));
//                }
//            }
//        }
//    }

    public void onCreate() {
        super.onCreate();
        this.requestDao = new RequestDao(this);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        int i;
        List<TopicInfo> topicList = this.requestDao.getAllLocalTopics(MyApplication.getInstance().login_uid);
        if (topicList != null && topicList.size() > 0) {
            for (i = 0; i < topicList.size(); i++) {
                uploadTopic((TopicInfo) topicList.get(i));
            }
        }
        List<Comment> commentList = this.requestDao.queryCommentByUid(MyApplication.getInstance().login_uid);
        if (commentList != null && commentList.size() > 0) {
            for (i = 0; i < commentList.size(); i++) {
                uploadComment((Comment) commentList.get(i));
            }
        }
        return 1;
    }

    private void uploadComment(Comment comment) {
        String topicId = comment.getTopicId();
        String commentId = comment.getCommentid();
        if (topicId != null && !BuildConfig.FLAVOR.equals(topicId)) {
//            QGHttpRequest.getInstance().addComment(this, comment.getTopicId(), comment.getContent(), comment.getReplyCommentId(), comment.getLocationx(), comment.getLocationy(), comment.getTxtframe(), new C06871(this, false, commentId));
        }
    }

    private void uploadTopic(TopicInfo topic) {
        String content = topic.getContent();
        if (content != null) {
            File file = new File(content.substring(7));
            if (file.exists()) {
//                QGHttpRequest.getInstance().addTopicRequest(this, file, null, null, null, null, new C07452(this, topic, file));
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public static final void sendUploadTopicSuccessBrocat(Context context, String localTopicId, TopicInfo topic) {
        Intent i = new Intent();
        i.setAction(Constant.UPLOAD_TOPIC_FINISH_ACTION);
        i.putExtra("topic", topic);
        i.putExtra("localTopicId", localTopicId);
        context.sendBroadcast(i);
    }
}
