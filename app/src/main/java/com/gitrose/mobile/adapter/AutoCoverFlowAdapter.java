package com.gitrose.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.R;
//import com.gitrose.mobile.avsdk.control.GLVideoView;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.view.CircleImageView;
import java.util.List;

public class AutoCoverFlowAdapter extends BaseAdapter {
    public static final int AVATAR_TYPE = 1;
    public static final int COMMENT_TYPE = 0;
    public static final int PROGRESS_TYPE = 2;
    private static int mNormalCricleSize;
    public static LayoutParams mNormalLayoutParams;
    public static LayoutParams mProgressLayoutParams;
    private static int mSelectedCricleSize;
    public static LayoutParams mSelectedLayoutParams;
    public static float scale;
    private LayoutInflater inflater;
    private boolean isDown;
    private boolean isUp;
    private int mCommentCount;
    private List<Comment> mComments;
    private Context mContext;

//    static {
//        scale = GLVideoView.MIN_SCALE;
//    }

    public AutoCoverFlowAdapter(Context context, List<Comment> comments) {
        this.isUp = true;
        this.isDown = true;
        this.mContext = context;
        this.mComments = comments;
        if (mSelectedCricleSize == 0 || mNormalCricleSize == 0) {
            int avatarSize = (int) context.getResources().getDimension(R.dimen.item_avatar_size);
            mSelectedCricleSize = avatarSize;
            mNormalCricleSize = (int) (((float) avatarSize) * scale);
        }
        if (mSelectedLayoutParams == null || mProgressLayoutParams == null || mNormalLayoutParams == null) {
            mSelectedLayoutParams = new LayoutParams(mSelectedCricleSize, mSelectedCricleSize);
            mNormalLayoutParams = new LayoutParams(mNormalCricleSize, mNormalCricleSize);
            mProgressLayoutParams = new LayoutParams(-2, -1);
        }
        this.inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if (this.mComments != null) {
            return this.mComments.size() + PROGRESS_TYPE;
        }
        return PROGRESS_TYPE;
    }

    public Object getItem(int position) {
        if (this.mComments == null) {
            return Integer.valueOf(COMMENT_TYPE);
        }
        if (position == 0) {
            return Integer.valueOf(this.mCommentCount);
        }
        if (position == this.mComments.size() + AVATAR_TYPE) {
            return Integer.valueOf(AVATAR_TYPE);
        }
        return this.mComments.get(position - 1);
    }

    public int getItemViewType(int position) {
        if (this.mComments == null || position == 0) {
            return COMMENT_TYPE;
        }
        if (position == this.mComments.size() + AVATAR_TYPE) {
            return PROGRESS_TYPE;
        }
        return AVATAR_TYPE;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int position, View reuseableView, ViewGroup viewGroup) {
        int tyle = getItemViewType(position);
        if (tyle == AVATAR_TYPE) {
            ImageView imageView = null;
            if (reuseableView == null) {
                imageView = new CircleImageView(this.mContext);
            } else {
                CircleImageView imageView2 = (CircleImageView) reuseableView;
            }
//            imageView.setBorderWidth(COMMENT_TYPE);
//            imageView.setHasSelected(false);
            imageView.setLayoutParams(mNormalLayoutParams);
            if (this.mComments == null || this.mComments.size() <= 0) {
                return imageView;
            }
            Comment comment = this.mComments.size() >= position ? (Comment) this.mComments.get(position - 1) : (Comment) this.mComments.get(COMMENT_TYPE);
            if (comment == null) {
                return imageView;
            }
            TutuUsers user = comment.getUserinfo();
            if (comment.getIskana() == 0) {
                ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(user.getUid(), user.getAvatartime()), imageView, Constant.AVATAR_OPTIONS);
                return imageView;
            }
            imageView.setImageResource(R.drawable.topic_no_name);
            return imageView;
        } else if (tyle == 0) {
            if (reuseableView == null) {
                reuseableView = this.inflater.inflate(R.layout.item_comment, null);
                reuseableView.setLayoutParams(mProgressLayoutParams);
            }
            return reuseableView;
        } else {
            ImageView progressImg;
            if (reuseableView == null) {
                progressImg = new ImageView(this.mContext);
            } else {
                progressImg = (ImageView) reuseableView;
            }
            progressImg.setVisibility(View.GONE);
            progressImg.setLayoutParams(mProgressLayoutParams);
            return progressImg;
        }
    }

    public boolean isUp() {
        return this.isUp;
    }

    public void setUp(boolean isUp) {
        this.isUp = isUp;
    }

    public boolean isDown() {
        return this.isDown;
    }

    public void setDown(boolean isDown) {
        this.isDown = isDown;
    }

    public int getmCommentCount() {
        return this.mCommentCount;
    }

    public void setmCommentCount(int commentCount) {
        this.mCommentCount = commentCount;
    }

    public void setmCommentCount() {
        if (this.mComments != null) {
            this.mCommentCount = this.mComments.size();
        }
    }
}
