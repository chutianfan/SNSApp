package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.XListView.IXListViewListener;
import com.gitrose.mobile.adapter.NewsListAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseFragment;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.TipsList;
import com.gitrose.mobile.model.TipsModel;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.TipPop;
import java.util.ArrayList;
import org.apache.http.Header;
import org.litepal.util.Const.TableSchema;
import qalsdk.BaseConstants;

public class NewsListFragment extends BaseFragment implements OnClickListener, IXListViewListener {
    private static NewsListFragment newsListFrgment;
    private NewsListAdapter adapter;
    private RelativeLayout container_rl;
    private TipPop copyPop;
    private boolean isnews;
    OnClickListener itemClick;
    private int len;
    private SwipeMenuListView listview;
    private View mEmptyView;
    OnLongClickListener mOnLongClickListener;
    private int newcount;
    private PlaySound palySound;
    private ArrayList<TipsModel> tips_list;
    private TextView title_tv;
    private String type;

    /* renamed from: com.gitrose.mobile.NewsListFragment.1 */
    class C07131 implements OnLongClickListener {
        C07131() {
        }

        public boolean onLongClick(View v) {
            int pos = ((Integer) v.getTag()).intValue();
            NewsListFragment.this.showCopyPop(v, BaseConstants.ah, new String[]{NewsListFragment.this.getResources().getString(R.string.delete)}, pos);
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.NewsListFragment.2 */
    class C07142 implements OnClickListener {
        C07142() {
        }

        public void onClick(View v) {
            int pos = ((Integer) v.getTag()).intValue();
            Intent intent = null;
            switch (v.getId()) {
                case R.id.private_list_item_ll:
                    int position = pos;
                    if (NewsListFragment.this.tips_list != null && NewsListFragment.this.tips_list.size() > 0 && position < NewsListFragment.this.tips_list.size()) {
                        if (((TipsModel) NewsListFragment.this.tips_list.get(position)).getRead() == 0) {
                            ((TipsModel) NewsListFragment.this.tips_list.get(position)).setRead(1);
                            View itemview = NewsListFragment.this.listview.getChildAt((position - NewsListFragment.this.listview.getFirstVisiblePosition()) + 1);
                            if (itemview != null) {
                                ImageView imageview_news = (ImageView) itemview.findViewById(R.id.tips_list_item_iv_news);
                                if (imageview_news != null) {
                                    imageview_news.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        String action = ((TipsModel) NewsListFragment.this.tips_list.get(position)).getAction();
                        if (action != null) {
                            if (action.equals(Constant.XG_TYPE_COMMENT) || action.equals(Constant.XG_TYPE_REPLAY_COMMENT)) {
                                intent = new Intent(NewsListFragment.this.getActivity(), TopicDetailActivity.class);
                                intent.putExtra(TopicDetailActivity.TOPIC_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getRouteid());
                                intent.putExtra(TopicDetailPagerActivity.COMMENT_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getActionid());
                            } else if (action.equals(Constant.XG_TYPE_ZAN)) {
                                intent = new Intent(NewsListFragment.this.getActivity(), TopicDetailActivity.class);
                                intent.putExtra(TopicDetailActivity.TOPIC_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getRouteid());
                            } else if (action.equals(Constant.XG_TYPE_FOLLOW_ME) || action.equals(Constant.XG_TYPE_ADD_FRIENDS)) {
//                                intent = new Intent(NewsListFragment.this.getActivity(), PersonalNewActivity.class);
//                                intent.putExtra(PersonalNewActivity.PERSONAL_USER_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getActionuid());
                            } else if (action.equals(Constant.XG_TYPE_LIKE_USER)) {
//                                intent = new Intent(NewsListFragment.this.getActivity(), PersonalNewActivity.class);
//                                intent.putExtra(PersonalNewActivity.PERSONAL_USER_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getActionuid());
                            } else if (action.equals(Constant.XG_TYPE_LIKE_COMMENT)) {
                                intent = new Intent(NewsListFragment.this.getActivity(), TopicDetailActivity.class);
                                intent.putExtra(TopicDetailActivity.TOPIC_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getRouteid());
                                intent.putExtra(TopicDetailPagerActivity.COMMENT_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getActionid());
                            } else if (action.equals(Constant.XG_TYPE_REPOST_TOPIC)) {
                                intent = new Intent(NewsListFragment.this.getActivity(), TopicDetailActivity.class);
                                intent.putExtra(TopicDetailActivity.TOPIC_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getRouteid());
                            } else if (action.equals(Constant.XG_TYPE_TOPIC_AT)) {
                                intent = new Intent(NewsListFragment.this.getActivity(), TopicDetailActivity.class);
                                intent.putExtra(TopicDetailActivity.TOPIC_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getRouteid());
                            } else if (action.equals(Constant.XG_TYPE_COMMENT_AT)) {
                                intent = new Intent(NewsListFragment.this.getActivity(), TopicDetailActivity.class);
                                intent.putExtra(TopicDetailActivity.TOPIC_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getRouteid());
                                intent.putExtra(TopicDetailPagerActivity.COMMENT_ID, ((TipsModel) NewsListFragment.this.tips_list.get(position)).getActionid());
                            }
                            if (intent != null) {
                                NewsListFragment.this.startActivity(intent);
                                NewsListFragment.this.animationForNew();
                            }
                        }
                    }
                case R.id.tips_list_item_iv_avatar:
//                    intent = new Intent(NewsListFragment.this.getActivity(), PersonalNewActivity.class);
//                    intent.putExtra(PersonalNewActivity.PERSONAL_USER_ID, ((TipsModel) NewsListFragment.this.tips_list.get(pos)).getActionuid());
//                    NewsListFragment.this.startActivity(intent);
//                    NewsListFragment.this.animationForNew();
                case R.id.tips_list_item_follow_iv:
                    if (pos > -1) {
//                        int relation = ((TipsModel) NewsListFragment.this.tips_list.get(pos)).getRelation();
//                        switch (relation) {
//                            case PersonalNewActivity.FRAGMENT_ONE /*0*/:
//                            case PersonalNewActivity.FRAGMENT_TWO /*1*/:
//                                NewsListFragment.this.addFollow(pos, relation, v);
//                            case PersonalNewActivity.REMARK_NAME_RESULT /*2*/:
//                            case PersonalNewActivity.FRAGMENT_FOURE /*3*/:
//                                NewsListFragment.this.delFollow(pos, relation, v);
//                            default:
//                        }
                    }
                case R.id.rl_empty_click_tip:
                    NewsListFragment.this.getData(BaseConstants.ah, NewsListFragment.this.len, SearchActivity.UP, true);
                default:
            }
        }
    }

    /* renamed from: com.gitrose.mobile.NewsListFragment.3 */
    class C07153 implements OnClickListener {
        private final /* synthetic */ int val$postion;

        C07153(int i) {
            this.val$postion = i;
        }

        public void onClick(View v) {
            NewsListFragment.this.copyPop.dismiss();
//            NewsListFragment.this.palySound.toPlay(R.raw.delete);
            if (this.val$postion >= 0 && this.val$postion < NewsListFragment.this.tips_list.size()) {
                NewsListFragment.this.deleteTip(this.val$postion, ((TipsModel) NewsListFragment.this.tips_list.get(this.val$postion)).getTipid());
            }
        }
    }

    /* renamed from: com.gitrose.mobile.NewsListFragment.4 */
    class C16724 extends QGHttpHandler<TipsList> {
        private final /* synthetic */ String val$driection;

        C16724(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$driection = str;

        }

        public void onGetDataSuccess(TipsList data) {
            if (data != null) {
                ArrayList<TipsModel> newsList;
                ArrayList<TipsModel> historysList;
                if (this.val$driection.equals(SearchActivity.UP)) {
                    newsList = data.getNewtiplist();
                    if (newsList != null && newsList.size() > 0) {
                        newsList.add(0, new TipsModel(0));
                        NewsListFragment.this.isnews = true;
                    }
                    historysList = data.getHistorytiplist();
                    if (historysList != null && historysList.size() > 0) {
                        historysList.add(0, new TipsModel(1));
                    }
                    if (historysList.size() > 0 && NewsListFragment.this.tips_list.size() > NewsListFragment.this.newcount) {
                        NewsListFragment.this.tips_list.remove(NewsListFragment.this.newcount);
                    }
                    if (newsList.size() > 0 && NewsListFragment.this.newcount > 0) {
                        NewsListFragment.this.tips_list.remove(0);
                    }
                    NewsListFragment newsListFragment = NewsListFragment.this;
                    newsListFragment.newcount = newsListFragment.newcount + newsList.size();
                    if (!(newsList == null || newsList.size() == 0)) {
                        NewsListFragment.this.tips_list.addAll(0, newsList);
                    }
                    if (!(historysList == null || historysList.size() == 0)) {
                        NewsListFragment.this.tips_list.addAll(NewsListFragment.this.newcount, historysList);
                    }
                } else {
                    newsList = data.getNewtiplist();
                    if (newsList != null && newsList.size() > 0) {
                        ((TipsModel) newsList.get(0)).setIsHead(0);
                        NewsListFragment.this.isnews = true;
                    }
                    historysList = data.getHistorytiplist();
                    if (historysList != null && historysList.size() > 0) {
                        ((TipsModel) historysList.get(0)).setIsHead(0);
                    }
                    NewsListFragment.this.tips_list.addAll(data.getNewtiplist());
                    NewsListFragment.this.tips_list.addAll(data.getHistorytiplist());
                }
            }
            NewsListFragment.this.notifyViewUpdate();
        }

        public void onFinish() {
            super.onFinish();
            NewsListFragment.this.onRefreshComplete();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            if (NewsListFragment.this.tips_list.size() == 0) {
                NewsListFragment.this.showEmtpyTip(NewsListFragment.this.getResources().getString(R.string.empty_news_tips_net));
            }
        }
    }

    /* renamed from: com.gitrose.mobile.NewsListFragment.5 */
    class C16735 extends QGHttpHandler<String> {
        private final /* synthetic */ int val$pos;

        C16735(Context $anonymous0, boolean $anonymous1, int i) {

            super($anonymous0, $anonymous1);
            this.val$pos = i;

        }

        public void onGetDataSuccess(String data) {
            int news_num = MyApplication.getInstance().getNewsNum();
            if (((TipsModel) NewsListFragment.this.tips_list.get(this.val$pos)).getRead() == 0 && news_num > 0) {
                MyApplication.getInstance().setNewsNum(news_num - 1);
            }
            if ((this.val$pos == NewsListFragment.this.tips_list.size() - 1 || ((TipsModel) NewsListFragment.this.tips_list.get(this.val$pos + 1)).getIsHead() != ((TipsModel) NewsListFragment.this.tips_list.get(this.val$pos)).getIsHead()) && this.val$pos - 1 > -1 && ((TipsModel) NewsListFragment.this.tips_list.get(this.val$pos - 1)).getIsHead() > 0) {
                NewsListFragment.this.tips_list.remove(this.val$pos);
                NewsListFragment.this.tips_list.remove(this.val$pos - 1);
            } else {
                NewsListFragment.this.tips_list.remove(this.val$pos);
            }
            NewsListFragment.this.notifyViewUpdate();
        }
    }

    /* renamed from: com.gitrose.mobile.NewsListFragment.6 */
    class C16746 extends QGHttpHandler<String> {
        private final /* synthetic */ int val$pos;
        private final /* synthetic */ int val$relation;
        private final /* synthetic */ View val$v;

        C16746(Context $anonymous0, boolean $anonymous1, int i, int i2, View view) {

            super($anonymous0, $anonymous1);
            this.val$pos = i;
            this.val$relation = i2;
            this.val$v = view;

        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        public void onFinish() {
            super.onFinish();
        }

        public void onGetDataSuccess(String data) {
            if (!(data == null || data.equals(BaseConstants.ah))) {
                ((TipsModel) NewsListFragment.this.tips_list.get(this.val$pos)).setRelation(Integer.parseInt(data));
            }
            if (this.val$relation == 0) {
                this.val$v.setBackgroundResource(R.drawable.fans_i_follow_button);
            } else if (this.val$relation == 1) {
                this.val$v.setBackgroundResource(R.drawable.fans_2_follow_button);
            }
            NewsListFragment.sendFollowBroadcast(NewsListFragment.this.getActivity(), ((TipsModel) NewsListFragment.this.tips_list.get(this.val$pos)).getActionuid(), data, Constant.FOLLOW_USER_ACTION);
        }
    }

    /* renamed from: com.gitrose.mobile.NewsListFragment.7 */
    class C16757 extends QGHttpHandler<String> {
        private final /* synthetic */ int val$pos;
        private final /* synthetic */ int val$relation;
        private final /* synthetic */ View val$v;

        C16757(Context $anonymous0, boolean $anonymous1, int i, int i2, View view) {

            super($anonymous0, $anonymous1);
            this.val$pos = i;
            this.val$relation = i2;
            this.val$v = view;

        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        public void onFinish() {
            super.onFinish();
        }

        public void onGetDataSuccess(String data) {
            if (!(data == null || data.equals(BaseConstants.ah))) {
                ((TipsModel) NewsListFragment.this.tips_list.get(this.val$pos)).setRelation(Integer.parseInt(data));
            }
            if (this.val$relation != 2 && this.val$relation == 3) {
                this.val$v.setBackgroundResource(R.drawable.fans_follow_me_button);
            }
            NewsListFragment.sendFollowBroadcast(NewsListFragment.this.getActivity(), ((TipsModel) NewsListFragment.this.tips_list.get(this.val$pos)).getActionuid(), data, Constant.UNFOLLOW_USER_ACTION);
        }
    }

    public NewsListFragment() {
        this.len = 50;
        this.isnews = false;
        this.type = BaseConstants.ah;
        this.mOnLongClickListener = new C07131();
        this.newcount = 0;
        this.itemClick = new C07142();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news_list, null);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.type = bundle.getString(TableSchema.COLUMN_TYPE);
        }
        initUI(view);
        if (!MyApplication.getInstance().isLogin()) {
            getActivity().finish();
        }
        this.tips_list = new ArrayList();
        getData(BaseConstants.ah, this.len, SearchActivity.UP, true);
        newsListFrgment = this;
        return view;
    }

    public static NewsListFragment getObject() {
        return newsListFrgment;
    }

    public void torefesh() {
        if (this.tips_list != null && this.tips_list.size() > 0) {
            String tipsid = BaseConstants.ah;
            for (int i = 0; i < this.tips_list.size(); i++) {
                if (((TipsModel) this.tips_list.get(i)).getTipid() != null) {
                    tipsid = ((TipsModel) this.tips_list.get(i)).getTipid();
                    break;
                }
            }
            getData(tipsid, this.len, SearchActivity.UP, false);
        }
    }

    private void initUI(View view) {
        view.findViewById(R.id.title).setVisibility(View.GONE);
        this.container_rl = (RelativeLayout) view.findViewById(R.id.container_rl);
        this.title_tv = (TextView) view.findViewById(R.id.title_tv_text);
        this.title_tv.setText(getResources().getString(R.string.news));
        view.findViewById(R.id.title_tv_left).setOnClickListener(this);
        this.listview = (SwipeMenuListView) view.findViewById(R.id.activity_news_list_lv);
        this.mEmptyView = view.findViewById(R.id.rl_empty_tip);
        this.mEmptyView.setVisibility(View.INVISIBLE);
        this.adapter = new NewsListAdapter(this.getContext(), this.itemClick, this.mOnLongClickListener);
        this.listview.setAdapter(this.adapter);
//        this.palySound = PlaySound.getInstance(getActivity());
        this.listview.setPullLoadEnable(true);
        this.listview.setPullRefreshEnable(true);
        this.listview.setXListViewListener(this);
    }

    private void showCopyPop(View view, String content, String[] menu, int postion) {
        this.copyPop = new TipPop(getActivity(), menu, new C07153(postion));
        this.copyPop.show(view);
    }

    final void onRefreshComplete() {
        this.listview.stopRefresh();
        this.listview.stopLoadMore();
    }

    public void onRefresh() {
        String tipsid = BaseConstants.ah;
        if (this.tips_list != null && this.tips_list.size() > 0) {
            for (int i = 0; i < this.tips_list.size(); i++) {
                if (((TipsModel) this.tips_list.get(i)).getTipid() != null) {
                    tipsid = ((TipsModel) this.tips_list.get(i)).getTipid();
                    break;
                }
            }
        }
        getData(tipsid, this.len, SearchActivity.UP, false);
    }

    public void onLoadMore() {
        String tipsid = BaseConstants.ah;
        if (this.tips_list != null && this.tips_list.size() > 0) {
            tipsid = ((TipsModel) this.tips_list.get(this.tips_list.size() - 1)).getTipid();
        }
        getData(tipsid, this.len, SearchActivity.DOWN, false);
    }

    private void getData(String tipsid, int len, String driection, boolean isloading) {
        QGHttpRequest.getInstance().getTipsListRequest(getContext(), tipsid, len, driection, this.type, new C16724(getActivity(), isloading, ((NewsListActivty) getActivity()).contianer_rl, driection));
    }

    private void notifyViewUpdate() {
        if (this.tips_list.size() > 0) {
            this.adapter.setList(this.tips_list);
            return;
        }
        int empty_str_id = R.string.empty_news_tips;
        if (!(this.type == null || this.type.equals(BaseConstants.ah))) {
            if (this.type.equals(Constant.LIVE_CUSTOM_TYPE_LIKE)) {
                empty_str_id = R.string.empty_news_zan_tips;
            } else if (this.type.equals("comment")) {
                empty_str_id = R.string.empty_news_comment_tips;
            } else if (this.type.equals("at")) {
                empty_str_id = R.string.empty_news_at_tips;
            } else if (this.type.equals(Constant.LIVE_CUSTOM_TYPE_FOLLOW)) {
                empty_str_id = R.string.empty_news_follow_tips;
            }
        }
        showEmtpyTip(getResources().getString(empty_str_id));
    }

    private void showEmtpyTip(String tip) {
        this.listview.setVisibility(View.INVISIBLE);
        this.mEmptyView.setVisibility(View.VISIBLE);
        ((TextView) this.mEmptyView.findViewById(R.id.tv_emtpy_tip)).setText(tip);
    }

    private void deleteTip(int pos, String tip_id) {
        QGHttpRequest.getInstance().deleteTipRequest(getActivity(), tip_id, new C16735(getActivity(), false, pos));
    }

    private void addFollow(int pos, int relation, View v) {
        QGHttpRequest.getInstance().addFollowRequest(getActivity(), ((TipsModel) this.tips_list.get(pos)).getActionuid(), new C16746(getActivity(), false, pos, relation, v));
    }

    private void delFollow(int pos, int relation, View v) {
        QGHttpRequest.getInstance().delFollowRequest(getActivity(), ((TipsModel) this.tips_list.get(pos)).getActionuid(), new C16757(getActivity(), false, pos, relation, v));
    }

    private static void sendFollowBroadcast(Context mContext, String userId, String relation, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("to_uid", userId);
        intent.putExtra("relation", relation);
        mContext.sendBroadcast(intent);
    }

    public void onReciverBroadcast(String uid, String relation) {
        boolean need_refresh = false;
        if (this.tips_list != null) {
            int i = 0;
            while (i < this.tips_list.size()) {
                if (uid.equals(((TipsModel) this.tips_list.get(i)).getActionuid()) && ((TipsModel) this.tips_list.get(i)).getRelation() != Integer.parseInt(relation) && ((TipsModel) this.tips_list.get(i)).getAction().equals(Constant.XG_TYPE_FOLLOW_ME)) {
                    ((TipsModel) this.tips_list.get(i)).setRelation(Integer.parseInt(relation));
                    need_refresh = true;
                }
                i++;
            }
            if (need_refresh) {
                notifyViewUpdate();
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                getActivity().finish();
            default:
        }
    }

    public void clearNews() {
        if (this.tips_list != null && this.tips_list.size() > 0 && this.isnews) {
            int pos = 0;
            for (int i = 0; i < this.tips_list.size(); i++) {
                if (i == 0) {
                    ((TipsModel) this.tips_list.get(i)).setIsHead(2);
                } else {
                    if (((TipsModel) this.tips_list.get(i)).getIsHead() != 0) {
                        pos = i;
                    }
                    ((TipsModel) this.tips_list.get(i)).setIsHead(0);
                }
                ((TipsModel) this.tips_list.get(i)).setRead(1);
            }
            if (pos != 0) {
                this.tips_list.remove(pos);
            }
            this.isnews = false;
            this.newcount = 0;
            notifyViewUpdate();
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
