package com.gitrose.mobile.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.mobile.R;
import com.gitrose.mobile.HomeFragmentActivity;
import com.gitrose.mobile.RecommendActivity;
import com.gitrose.mobile.SearchActivity;
import com.gitrose.mobile.WebViewActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.RecommendDomin;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.CircleImageView;
import java.util.List;
import org.apache.http.Header;

public class RecommendListAdapter extends BaseAdapter implements OnClickListener {
    public static final String THEME_PLAZA = "http://www.gitrose.com/hd/hothuatih5.php";
    private BaseDialog dialog;
    private boolean isHomeRecommand;
    private Context mContext;
    private ImageLoader mImageLoader;
    private List<RecommendDomin> mList;

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.1 */
    class C08581 implements OnPreDrawListener {
        private final /* synthetic */ TextView val$category_title_tv;
        private final /* synthetic */ View val$green_line_view;

        C08581(TextView textView, View view) {
            this.val$category_title_tv = textView;
            this.val$green_line_view = view;
        }

        public boolean onPreDraw() {
            this.val$green_line_view.setLayoutParams(new LayoutParams(this.val$category_title_tv.getWidth(), RecommendListAdapter.this.mContext.getResources().getDimensionPixelOffset(R.dimen.home_avator_broder_width)));
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.2 */
    class C08592 implements OnPreDrawListener {
        private final /* synthetic */ TextView val$category_title_tv;
        private final /* synthetic */ View val$green_line_view;

        C08592(TextView textView, View view) {
            this.val$category_title_tv = textView;
            this.val$green_line_view = view;
        }

        public boolean onPreDraw() {
            this.val$green_line_view.setLayoutParams(new LayoutParams(this.val$category_title_tv.getWidth(), RecommendListAdapter.this.mContext.getResources().getDimensionPixelOffset(R.dimen.home_avator_broder_width)));
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.3 */
    class C08603 implements OnClickListener {
        C08603() {
        }

        public void onClick(View v) {
            TextView textView = (TextView) v;
            RecommendDomin domin = (RecommendDomin) RecommendListAdapter.this.mList.get(((Integer) v.getTag()).intValue());
            if (domin.isSelected()) {
                RecommendListAdapter.this.showCancelFollowDialog(textView, domin);
            } else {
                RecommendListAdapter.this.addFollowTheme(textView, domin);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.4 */
    class C08614 implements OnClickListener {
        C08614() {
        }

        public void onClick(View v) {
            ImageView imageView = (ImageView) v;
            RecommendDomin domin = (RecommendDomin) RecommendListAdapter.this.mList.get(((Integer) v.getTag()).intValue());
            if (domin.isSelected()) {
                RecommendListAdapter.this.showCancelFollowDialog(imageView, domin);
            } else {
                RecommendListAdapter.this.followUser(imageView, domin);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.5 */
    class C08625 implements OnClickListener {
        C08625() {
        }

        public void onClick(View v) {
            RecommendDomin domin = (RecommendDomin) RecommendListAdapter.this.mList.get(((Integer) v.getTag()).intValue());
            int type = domin.getType();
            int header = domin.getHeader();
            if (type == 0 && header == 1) {
                RecommendListAdapter.this.mContext.startActivity(new Intent(RecommendListAdapter.this.mContext, SearchActivity.class));
                if (RecommendListAdapter.this.isHomeRecommand) {
                    ((HomeFragmentActivity) RecommendListAdapter.this.mContext).overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
                } else {
                    ((RecommendActivity) RecommendListAdapter.this.mContext).overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
                }
            } else if (type == 1 && header == 2) {
//                WebViewActivity.openWebView(RecommendListAdapter.this.mContext, RecommendListAdapter.THEME_PLAZA);
            } else if (type == 2 && header == 3) {
//                WebViewActivity.openWebView(RecommendListAdapter.this.mContext, RecommendListAdapter.THEME_PLAZA);
            }
        }
    }

    static class ViewHolder {
        ImageView _sex_icon_iv;
        TextView age_text_tv;
        RelativeLayout category_rl;
        TextView category_title_tv;
        TextView content_tv;
        View dividing_line_view;
        TextView follow_theme_tv;
        ImageView follow_user_iv;
        View green_line_view;
        ImageView iv_isauth;
        ImageView level_iv;
        TextView more_tv;
        ImageView poi_status_iv;
        CircleImageView round_avatar_iv;
        RelativeLayout sex_age_rl;
        ImageView square_avatar_iv;
        TextView title_tv;

        ViewHolder() {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.10 */
    class AnonymousClass10 extends QGHttpHandler<String> {
        private final /* synthetic */ RecommendDomin val$domin;
        private final /* synthetic */ TextView val$textView;

        AnonymousClass10(Context $anonymous0, RecommendDomin recommendDomin, TextView textView) {

            super($anonymous0);
            this.val$domin = recommendDomin;
            this.val$textView = textView;
            
        }

        public void onGetDataSuccess(String data) {
            this.val$domin.setSelected(false);
            this.val$textView.setText(RecommendListAdapter.this.mContext.getString(R.string.follow));
            this.val$textView.setTextColor(Color.parseColor("#ffffff"));
            this.val$textView.setBackgroundResource(R.drawable.follow_theme_bg);
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        }

        public void onFinish() {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.11 */
    class AnonymousClass11 extends QGHttpHandler<String> {
        private final /* synthetic */ RecommendDomin val$domin;
        private final /* synthetic */ TextView val$textView;

        AnonymousClass11(Context $anonymous0, RecommendDomin recommendDomin, TextView textView) {

            super($anonymous0);
            this.val$domin = recommendDomin;
            this.val$textView = textView;
            
        }

        public void onGetDataSuccess(String data) {
            this.val$domin.setSelected(false);
            this.val$textView.setText(RecommendListAdapter.this.mContext.getString(R.string.follow));
            this.val$textView.setTextColor(Color.parseColor("#ffffff"));
            this.val$textView.setBackgroundResource(R.drawable.follow_theme_bg);
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        }

        public void onFinish() {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.6 */
    class C17846 extends QGHttpHandler<String> {
        private final /* synthetic */ RecommendDomin val$domin;
        private final /* synthetic */ ImageView val$imageView;

        C17846(Context $anonymous0, ImageView imageView, RecommendDomin recommendDomin) {

            super($anonymous0);
            this.val$imageView = imageView;
            this.val$domin = recommendDomin;
            
        }

        public void onGetDataSuccess(String data) {
            this.val$imageView.setImageResource(R.drawable.has_follow_user_bg);
            this.val$domin.setSelected(true);
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.7 */
    class C17857 extends QGHttpHandler<String> {
        private final /* synthetic */ RecommendDomin val$domin;
        private final /* synthetic */ ImageView val$imageView;

        C17857(Context $anonymous0, ImageView imageView, RecommendDomin recommendDomin) {

            super($anonymous0);
            this.val$imageView = imageView;
            this.val$domin = recommendDomin;
            
        }

        public void onGetDataSuccess(String data) {
            this.val$imageView.setImageResource(R.drawable.follow_user);
            this.val$domin.setSelected(false);
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.8 */
    class C17868 extends QGHttpHandler<String> {
        private final /* synthetic */ RecommendDomin val$domin;
        private final /* synthetic */ TextView val$textView;

        C17868(Context $anonymous0, RecommendDomin recommendDomin, TextView textView) {

            super($anonymous0);
            this.val$domin = recommendDomin;
            this.val$textView = textView;
            
        }

        public void onGetDataSuccess(String data) {
            this.val$domin.setSelected(true);
            this.val$textView.setText(RecommendListAdapter.this.mContext.getString(R.string.hasfollow));
            this.val$textView.setTextColor(Color.parseColor("#cccccc"));
            this.val$textView.setBackgroundResource(R.drawable.cancle_follow_theme_bg);
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            Toast.makeText(RecommendListAdapter.this.mContext, responseString, Toast.LENGTH_LONG).show();
        }

        public void onFinish() {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.RecommendListAdapter.9 */
    class C17879 extends QGHttpHandler<String> {
        private final /* synthetic */ RecommendDomin val$domin;
        private final /* synthetic */ TextView val$textView;

        C17879(Context $anonymous0, RecommendDomin recommendDomin, TextView textView) {

            super($anonymous0);
            this.val$domin = recommendDomin;
            this.val$textView = textView;
            
        }

        public void onGetDataSuccess(String data) {
            this.val$domin.setSelected(true);
            this.val$textView.setText(RecommendListAdapter.this.mContext.getString(R.string.hasfollow));
            this.val$textView.setTextColor(Color.parseColor("#cccccc"));
            this.val$textView.setBackgroundResource(R.drawable.cancle_follow_theme_bg);
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            Toast.makeText(RecommendListAdapter.this.mContext, responseString, Toast.LENGTH_LONG).show();
        }

        public void onFinish() {
        }
    }

    public RecommendListAdapter(Context context, List<RecommendDomin> list, boolean isHomeRecommand) {
        this.dialog = null;
        this.mContext = context;
        this.mList = list;
        this.mImageLoader = ImageLoader.getInstance();
        this.isHomeRecommand = isHomeRecommand;
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int position) {
        return this.mList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout category_rl;
        TextView category_title_tv;
        TextView more_tv;
        ImageView round_avatar_iv;
        ImageView square_avatar_iv;
        TextView title_tv;
        RelativeLayout sex_age_rl;
        ImageView _sex_icon_iv;
        TextView age_text_tv;
        TextView content_tv;
        ImageView follow_user_iv;
        TextView follow_theme_tv;
        View green_line_view;
        View dividing_line_view;
        ImageView poi_status_iv;
        ImageView level_iv;
        ImageView iv_isauth;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.recommend_list_item, null);
            category_rl = (RelativeLayout) convertView.findViewById(R.id.category_rl);
            category_title_tv = (TextView) convertView.findViewById(R.id.category_title_tv);
            more_tv = (TextView) convertView.findViewById(R.id.more_tv);
            round_avatar_iv = (CircleImageView) convertView.findViewById(R.id.round_avatar_iv);
            square_avatar_iv = (ImageView) convertView.findViewById(R.id.square_avatar_iv);
            title_tv = (TextView) convertView.findViewById(R.id.title_tv);
            sex_age_rl = (RelativeLayout) convertView.findViewById(R.id.sex_age_rl);
            _sex_icon_iv = (ImageView) convertView.findViewById(R.id._sex_icon_iv);
            age_text_tv = (TextView) convertView.findViewById(R.id.age_text_tv);
            content_tv = (TextView) convertView.findViewById(R.id.content_tv);
            follow_user_iv = (ImageView) convertView.findViewById(R.id.follow_user_iv);
            follow_theme_tv = (TextView) convertView.findViewById(R.id.follow_theme_bt);
            green_line_view = convertView.findViewById(R.id.green_line_view);
            dividing_line_view = convertView.findViewById(R.id.dividing_line_view);
            poi_status_iv = (ImageView) convertView.findViewById(R.id.poi_status_iv);
            level_iv = (ImageView) convertView.findViewById(R.id.level_iv);
            iv_isauth = (ImageView) convertView.findViewById(R.id.iv_isauth);
            viewHolder = new ViewHolder();
            viewHolder._sex_icon_iv = _sex_icon_iv;
            viewHolder.age_text_tv = age_text_tv;
            viewHolder.category_rl = category_rl;
            viewHolder.category_title_tv = category_title_tv;
            viewHolder.content_tv = content_tv;
            viewHolder.follow_theme_tv = follow_theme_tv;
            viewHolder.follow_user_iv = follow_user_iv;
            viewHolder.green_line_view = green_line_view;
            viewHolder.more_tv = more_tv;
            viewHolder.round_avatar_iv = (CircleImageView) round_avatar_iv;
            viewHolder.sex_age_rl = sex_age_rl;
            viewHolder.square_avatar_iv = square_avatar_iv;
            viewHolder.title_tv = title_tv;
            viewHolder.dividing_line_view = dividing_line_view;
            viewHolder.poi_status_iv = poi_status_iv;
            viewHolder.level_iv = level_iv;
            viewHolder.iv_isauth = iv_isauth;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            _sex_icon_iv = viewHolder._sex_icon_iv;
            age_text_tv = viewHolder.age_text_tv;
            category_rl = viewHolder.category_rl;
            category_title_tv = viewHolder.category_title_tv;
            content_tv = viewHolder.content_tv;
            follow_theme_tv = viewHolder.follow_theme_tv;
            follow_user_iv = viewHolder.follow_user_iv;
            green_line_view = viewHolder.green_line_view;
            more_tv = viewHolder.more_tv;
            round_avatar_iv = viewHolder.round_avatar_iv;
            sex_age_rl = viewHolder.sex_age_rl;
            square_avatar_iv = viewHolder.square_avatar_iv;
            title_tv = viewHolder.title_tv;
            dividing_line_view = viewHolder.dividing_line_view;
            poi_status_iv = viewHolder.poi_status_iv;
            level_iv = viewHolder.level_iv;
            iv_isauth = viewHolder.iv_isauth;
        }
        RecommendDomin domin = (RecommendDomin) this.mList.get(position);
        int type = domin.getType();
        int header = domin.getHeader();
        if (type == 0) {
            dividing_line_view.setVisibility(View.GONE);
            if (header == 1) {
                category_rl.setVisibility(View.VISIBLE);
            } else {
                category_rl.setVisibility(View.GONE);
            }
            green_line_view.setVisibility(View.INVISIBLE);
            sex_age_rl.setVisibility(View.VISIBLE);
            follow_user_iv.setVisibility(View.VISIBLE);
            follow_theme_tv.setVisibility(View.INVISIBLE);
            round_avatar_iv.setVisibility(View.VISIBLE);
            iv_isauth.setVisibility(View.VISIBLE);
            square_avatar_iv.setVisibility(View.INVISIBLE);
            poi_status_iv.setVisibility(View.GONE);
            if (domin.getSex() == 1) {
                _sex_icon_iv.setImageResource(R.drawable.male);
                sex_age_rl.setBackgroundResource(R.drawable.personal_male_bg);
            } else {
                _sex_icon_iv.setImageResource(R.drawable.female);
                sex_age_rl.setBackgroundResource(R.drawable.personal_female_bg);
            }
            age_text_tv.setText(new StringBuilder(String.valueOf(domin.getAge())).toString());
            int level = domin.getHonorlevel();
            if (level <= 0) {
                level_iv.setVisibility(View.INVISIBLE);
            } else {
                level_iv.setVisibility(View.VISIBLE);
                String level_url = ImageUtils.getLevelUrl(new StringBuilder(String.valueOf(level)).toString());
                ImageLoader.getInstance().displayImage(level_url, level_iv, Constant.LEVEL_OPTIONS);
            }
            iv_isauth.setVisibility(View.VISIBLE);
            if (domin.getIsauth() != null && Integer.parseInt(domin.getIsauth()) == 1) {
                iv_isauth.setBackgroundResource(R.drawable.personal_isauth);
            } else if (domin.getIsauth() == null || Integer.parseInt(domin.getIsauth()) != 2) {
                iv_isauth.setVisibility(View.GONE);
            } else {
                iv_isauth.setBackgroundResource(R.drawable.personal_daren);
            }
            if (domin.isSelected()) {
                follow_user_iv.setImageResource(R.drawable.has_follow_user_bg);
            } else {
                follow_user_iv.setImageResource(R.drawable.follow_user);
            }
            if (header == 1) {
                category_title_tv.setText(this.mContext.getString(R.string.maybe_interested_user));
            }
            if (this.isHomeRecommand) {
                category_title_tv.getViewTreeObserver().addOnPreDrawListener(new C08581(category_title_tv, green_line_view));
            }
            this.mImageLoader.displayImage(domin.getImgURL(), round_avatar_iv, Constant.AVATAR_OPTIONS);
            content_tv.setText(domin.getContent());
        } else {
            sex_age_rl.setVisibility(View.INVISIBLE);
            follow_user_iv.setVisibility(View.INVISIBLE);
            follow_theme_tv.setVisibility(View.VISIBLE);
            round_avatar_iv.setVisibility(View.INVISIBLE);
            iv_isauth.setVisibility(View.INVISIBLE);
            square_avatar_iv.setVisibility(View.VISIBLE);
            level_iv.setVisibility(View.INVISIBLE);
            if (header != 0) {
                green_line_view.setVisibility(View.VISIBLE);
                category_rl.setVisibility(View.VISIBLE);
                if (this.isHomeRecommand) {
                    if (header == 2) {
                        category_title_tv.setText(this.mContext.getString(R.string.home_recomand_huati));
                    } else if (header == 3) {
                        category_title_tv.setText(this.mContext.getString(R.string.home_recomand_poi));
                    }
                } else if (header == 2) {
                    category_title_tv.setText(this.mContext.getString(R.string.hot_theme));
                } else if (header == 3) {
                    category_title_tv.setText(this.mContext.getString(R.string.hot_poi));
                }
                category_title_tv.getViewTreeObserver().addOnPreDrawListener(new C08592(category_title_tv, green_line_view));
            } else {
                green_line_view.setVisibility(View.INVISIBLE);
                category_rl.setVisibility(View.GONE);
            }
            if (domin.getType() == 2) {
                poi_status_iv.setVisibility(View.VISIBLE);
            } else {
                poi_status_iv.setVisibility(View.GONE);
            }
            if (domin.isSelected()) {
                follow_theme_tv.setText(this.mContext.getString(R.string.hasfollow));
                follow_theme_tv.setTextColor(Color.parseColor("#cccccc"));
                follow_theme_tv.setBackgroundResource(R.drawable.cancle_follow_theme_bg);
            } else {
                follow_theme_tv.setText(this.mContext.getString(R.string.follow));
                follow_theme_tv.setTextColor(Color.parseColor("#ffffff"));
                follow_theme_tv.setBackgroundResource(R.drawable.follow_theme_bg);
            }
            this.mImageLoader.displayImage(domin.getImgURL(), square_avatar_iv, Constant.AVATAR_OPTIONS);
            content_tv.setText(domin.getContent() + this.mContext.getString(R.string.dis_people));
        }
        if (header == 0) {
            dividing_line_view.setVisibility(View.GONE);
            category_rl.setVisibility(View.GONE);
            green_line_view.setVisibility(View.INVISIBLE);
        } else if (header == 1) {
            dividing_line_view.setVisibility(View.GONE);
        } else {
            dividing_line_view.setVisibility(View.VISIBLE);
        }
        if (type == 1) {
            title_tv.setText("#" + domin.getTitle());
        } else {
            title_tv.setText(domin.getTitle());
        }
        more_tv.setTag(Integer.valueOf(position));
        follow_theme_tv.setTag(Integer.valueOf(position));
        follow_user_iv.setTag(Integer.valueOf(position));
        follow_theme_tv.setOnClickListener(new C08603());
        follow_user_iv.setOnClickListener(new C08614());
        more_tv.setOnClickListener(new C08625());
        return convertView;
    }

    protected void followUser(ImageView imageView, RecommendDomin domin) {
        QGHttpRequest.getInstance().addFollowRequest(this.mContext, domin.getUserID(), new C17846(this.mContext, imageView, domin));
    }

    protected void unFollowUser(ImageView imageView, RecommendDomin domin) {
        QGHttpRequest.getInstance().delFollowRequest(this.mContext, domin.getUserID(), new C17857(this.mContext, imageView, domin));
    }

    protected void addFollowTheme(TextView textView, RecommendDomin domin) {
        boolean type = true;
        if (domin.getType() == 2) {
            type = false;
        }
        String htid = domin.getThemeID();
        String poiId = domin.getPoiID();
        if (type) {
            QGHttpRequest.getInstance().addHuatiFollow(this.mContext, htid, new C17868(this.mContext, domin, textView));
        } else {
            QGHttpRequest.getInstance().addPoiHuatiFollow(this.mContext, poiId, new C17879(this.mContext, domin, textView));
        }
    }

    protected void delFollowTheme(TextView textView, RecommendDomin domin) {
        boolean type = true;
        if (domin.getType() == 2) {
            type = false;
        }
        String htid = domin.getThemeID();
        String poiId = domin.getPoiID();
        if (type) {
            QGHttpRequest.getInstance().delHuatiFollow(this.mContext, htid, new AnonymousClass10(this.mContext, domin, textView));
        } else {
            QGHttpRequest.getInstance().delPoiHuatiFollow(this.mContext, poiId, new AnonymousClass11(this.mContext, domin, textView));
        }
    }

    public void showCancelFollowDialog(TextView textView, RecommendDomin domin) {
        View cancelFollowView = View.inflate(this.mContext, R.layout.dialog_base_layout, null);
        ((TextView) cancelFollowView.findViewById(R.id.tv_tip_title)).setText(this.mContext.getResources().getString(R.string.sure_cancel_follow));
        cancelFollowView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        View pop_tv_submit = cancelFollowView.findViewById(R.id.pop_tv_submit);
        pop_tv_submit.setOnClickListener(this);
        pop_tv_submit.setTag(R.id.pop_tv_cancel, textView);
        pop_tv_submit.setTag(R.id.pop_tv_submit, domin);
        pop_tv_submit.setTag(Integer.valueOf(2));
        this.dialog = new BaseDialog(this.mContext);
        this.dialog.show(cancelFollowView);
        this.dialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
            }
        });
    }

    public void showCancelFollowDialog(ImageView imageView, RecommendDomin domin) {
        View cancelFollowView = View.inflate(this.mContext, R.layout.dialog_base_layout, null);
        ((TextView) cancelFollowView.findViewById(R.id.tv_tip_title)).setText(this.mContext.getResources().getString(R.string.sure_cancel_follow));
        cancelFollowView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        View pop_tv_submit = cancelFollowView.findViewById(R.id.pop_tv_submit);
        pop_tv_submit.setOnClickListener(this);
        pop_tv_submit.setTag(R.id.pop_tv_cancel, imageView);
        pop_tv_submit.setTag(R.id.pop_tv_submit, domin);
        pop_tv_submit.setTag(Integer.valueOf(1));
        this.dialog = new BaseDialog(this.mContext);
        this.dialog.show(cancelFollowView);
        this.dialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_tv_submit:
                if (this.dialog != null) {
                    this.dialog.dismiss();
                }
                if (((Integer) v.getTag()).intValue() == 1) {
                    unFollowUser((ImageView) v.getTag(R.id.pop_tv_cancel), (RecommendDomin) v.getTag(R.id.pop_tv_submit));
                } else {
                    delFollowTheme((TextView) v.getTag(R.id.pop_tv_cancel), (RecommendDomin) v.getTag(R.id.pop_tv_submit));
                }
            case R.id.pop_tv_cancel:
                if (this.dialog != null) {
                    this.dialog.dismiss();
                }
            default:
        }
    }
}
